package centre.MyCulturalCenter.controller;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import centre.MyCulturalCenter.component.UserComponent;
import centre.MyCulturalCenter.model.Course;
import centre.MyCulturalCenter.model.Schedule;
import centre.MyCulturalCenter.model.User;
import centre.MyCulturalCenter.repository.ScheduleRepository;
import centre.MyCulturalCenter.service.CourseService;
import centre.MyCulturalCenter.service.UserService;

@Controller
@RequestMapping(value = "/courses")
public class CourseController {

	@Autowired
	private CourseService courseService;
	@Autowired
	private UserService userService;
	@Autowired
	private UserComponent userComponent;

	@Autowired
	ScheduleRepository scheduleRepository;

	@RequestMapping(value = "")
	public String getCourses(Model model, Principal principal) {
		boolean isLogged = principal != null;
		User visitor = (isLogged) ? userService.findByEmail(principal.getName()) : null;
		List<Course> courses = courseService.getAllCourses();
		model.addAttribute("courses", courses);
		model.addAttribute("visitor", visitor);
		return "courses";
	}

	
	@GetMapping("/{idSchedule}/add")
	public String addCourse(@PathVariable long idSchedule) throws InterruptedException {

		if(!userComponent.isLoggedUser())
			return "redirect:/403.html";

		User user =  userComponent.getLoggedUser();

		Optional<Schedule> sch = scheduleRepository.findById(idSchedule);
		if(!sch.isPresent()) {
			return "redirect:/404.html";
		}
		else {
		Schedule sh = sch.get();	
		sh.addUser(user);

		
		scheduleRepository.save(sh);
		
		Thread.sleep(2000);
		return "redirect:/user/profile";
		}
	}
	@GetMapping("/{idSchedule}/delete")
	public String deleteCourse(@PathVariable long idSchedule) throws InterruptedException {

		if(!userComponent.isLoggedUser())
			return "redirect:/403.html";

		User user =  userComponent.getLoggedUser();

		Optional<Schedule> sch = scheduleRepository.findById(idSchedule);
		if(!sch.isPresent())
			return "redirect:/404.html";

		Schedule sh = sch.get();
		sh.deleteUser(user);

		scheduleRepository.save(sh);
		
		Thread.sleep(2000);
		return "redirect:/user/profile";
	}
	
	
}
