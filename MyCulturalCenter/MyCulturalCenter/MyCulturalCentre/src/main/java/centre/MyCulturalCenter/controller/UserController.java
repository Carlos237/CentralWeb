package centre.MyCulturalCenter.controller;

import java.security.Principal;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import centre.MyCulturalCenter.component.UserComponent;
import centre.MyCulturalCenter.model.User;
import centre.MyCulturalCenter.repository.ScheduleRepository;
import centre.MyCulturalCenter.service.UserService;

@Controller
@RequestMapping(value = "/user")
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private UserComponent userComponent;
	
	@Autowired
	private ScheduleRepository scheduleRepository;

	@RequestMapping("/profile")
	public String userProfile(Model model) {

		if(!userComponent.isLoggedUser()){
			return "redirect:/403.html";
		}
		User userLogged = userComponent.getLoggedUser(); // Check if the user is logged
		

		model.addAttribute("userPage", userLogged);
		model.addAttribute("recommendations", userService.getRecommendedCoursesForUser(userLogged));
		model.addAttribute("schedules", scheduleRepository.findByListUsersContains(userLogged)); 
		
		model.addAttribute("editSection", userLogged.getRoles().contains("ROLE_ADMIN")); // Return a true o false 
		return "user";
	}

	@RequestMapping("/")
	public String userRedirectToProfile(Principal p) {
		return "redirect:/user/profile";
	}

	@PostMapping("/register")
	public String registerUser(@RequestParam String nickname,@RequestParam String name,@RequestParam String surname,@RequestParam String email,@RequestParam String password,@RequestParam String age) {
		User user = userService.createNewUser(nickname,name,surname,email,password,age);
		userComponent.setLoggedUser(user);
		return "redirect:/" ;
	}

	@RequestMapping("/newUser")
	public String newUser(Model model, User user, @RequestParam String password) {
		userService.createNewUser(user, password);
		return "redirect:/";
	}

	@PostMapping("/imageUpload")
	public String imgUpload(@RequestParam MultipartFile file) {
		User u = userComponent.getLoggedUser(); // The user itself
		userService.setImage(u, file);
		return "redirect:/user/profile";
	}

	@RequestMapping(value = "/editUser")
	public String editUserProfile(Model model) {
		if(!userComponent.isLoggedUser())
		    return "redirect:/403.html";

		User loggedUser = userComponent.getLoggedUser();
		model.addAttribute("userPage", loggedUser); // Variable that i use // Data access
		return "profile_settings";
	}

	@PostMapping(value = "/editedUser")
	public String userProfileEdit(@RequestParam Map<String, String> params) {
 		User loggedUser = userComponent.getLoggedUser();
		userService.editUser(loggedUser, params);

		return "redirect:/user/profile";
	}

}
