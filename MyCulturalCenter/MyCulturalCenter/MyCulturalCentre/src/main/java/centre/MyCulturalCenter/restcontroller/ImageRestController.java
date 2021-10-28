package centre.MyCulturalCenter.restcontroller;

import java.awt.Image;
import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import centre.MyCulturalCenter.model.User;
import centre.MyCulturalCenter.service.ImageService;
import centre.MyCulturalCenter.service.UserService;

@RestController
public class ImageRestController {

	interface UserDetail extends User.Basic, User.Details{	
	}
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private ImageService imageService;
	
	
	@PostMapping("/api/image")
	public ResponseEntity <ImageUrl> postImage(@RequestParam MultipartFile file, Principal principal){
		if (principal == null)
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		User u = userService.getUser(principal.getName());
		
		if(!imageService.isValidImage(file))
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		
		return new ResponseEntity<>(new ImageUrl(imageService.uploadImage(file)),HttpStatus.OK);
		
		
	}
	
	public class ImageUrl{
		public String url;
		
		public ImageUrl(String image) {
			this.url = image;
		}
	}
	
}
