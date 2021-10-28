package centre.MyCulturalCenter.restcontroller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;

import centre.MyCulturalCenter.component.UserComponent;
import centre.MyCulturalCenter.model.User;
import centre.MyCulturalCenter.service.UserService;



@RestController
@RequestMapping("/api/user")
public class UserRestController {

	interface UserDetail extends User.Basic, User.Details {
	}

	@Autowired
	private UserService userService;

	@Autowired
	private UserComponent userComponent;

	public interface UserDetails extends User.Basic, User.Details{}
	
	@GetMapping(value = "/")
	@JsonView(UserDetails.class)
	public ResponseEntity<List<User>> getUsers() {
		List<User> users = userService.getUsers();
		if (users != null) {
			return new ResponseEntity<>(users, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	
	@JsonView(UserDetail.class)
	@GetMapping(value = "/{id:.*}")
	public ResponseEntity<User> getSingleUser(@PathVariable String id) {
		boolean isEmail = false;
		long idd = 0;
		try {
			idd = Long.parseLong(id);
		} catch (NumberFormatException e) {
			isEmail = true;
		}
		User u = isEmail ? userService.findOne(id) : userService.getUserbyID(idd);
		if (u != null) {
			return new ResponseEntity<>(u, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	
	@RequestMapping(value = "/{id}", method = RequestMethod.PATCH)
	public ResponseEntity<User> editUserProfile(@PathVariable long id, @RequestBody User user) {
		if (userComponent.isLoggedUser() == true) {
			User updatedUser = userService.getUserbyID(id);
			User userLogged = userService.getUserbyID(userComponent.getLoggedUser().getId());
			if (updatedUser == userLogged) {
				if (updatedUser != null) {
					updatedUser = userService.updateUserInfo(id, user);
					return new ResponseEntity<>(updatedUser, HttpStatus.OK);
				} else {
					return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
				}
			} else {
				return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
			}
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

}