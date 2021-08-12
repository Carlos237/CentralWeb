package centre.MyCulturalCenter.controller;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import centre.MyCulturalCenter.component.UserComponent;
import centre.MyCulturalCenter.model.Course;
import centre.MyCulturalCenter.model.User;
import centre.MyCulturalCenter.repository.UserRepository;
import centre.MyCulturalCenter.service.CourseService;


@Controller
public class MainController {

	@Autowired
	private CourseService courseService;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private UserComponent userComponent;


	@RequestMapping(value = "/")
	public String getIndex(Model model, HttpServletRequest request) {
		Page<Course> coursesIndex = courseService.getPageCourses();
		model.addAttribute("courses", coursesIndex);
		if ((userComponent.isLoggedUser())) {
			long userLogged_id = userComponent.getLoggedUser().getId();
			Optional<User> userLogged = userRepository.findById(userLogged_id);
			if (userLogged.isPresent()) {
				User us = userLogged.get();
			model.addAttribute("user", us);
			if (userComponent.getLoggedUser().getId() == us.getId()) {
				model.addAttribute("logged", true);
			}
			model.addAttribute("admin", request.isUserInRole("ROLE_ADMIN"));
			return "index";
			}
		} else {
			return "index";
		}
		return null;
	}

}
