package centre.MyCulturalCenter.controller;


import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import centre.MyCulturalCenter.component.UserComponent;
import centre.MyCulturalCenter.model.User;
import centre.MyCulturalCenter.repository.UserRepository;


@Controller
public class MainController {

	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private UserComponent userComponent;


	@RequestMapping(value = "/")
	public String getIndex(Model model, HttpServletRequest request) {
		// New
		// Check if a user is logged
		if ((userComponent.isLoggedUser())) {
			long userLogged_id = userComponent.getLoggedUser().getId();
			User userLogged = userRepository.findById(userLogged_id);
			model.addAttribute("user", userLogged);
			if (userComponent.getLoggedUser().getId() == userLogged.getId()) {
				model.addAttribute("logged", true);
			}
			// Check if is an Admin
			model.addAttribute("admin", request.isUserInRole("ROLE_ADMIN"));
			return "index";
		} else {
			return "index";
		}
	}

}
