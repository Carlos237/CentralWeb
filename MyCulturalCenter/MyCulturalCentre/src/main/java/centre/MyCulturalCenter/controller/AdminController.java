package centre.MyCulturalCenter.controller;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import centre.MyCulturalCenter.model.Category;
import centre.MyCulturalCenter.model.Course;
import centre.MyCulturalCenter.model.Schedule;
import centre.MyCulturalCenter.model.User;
import centre.MyCulturalCenter.repository.CourseRepository;
import centre.MyCulturalCenter.repository.UserRepository;
import centre.MyCulturalCenter.service.CourseService;
import centre.MyCulturalCenter.service.UserService;

@Controller
public class AdminController {

	@Autowired
	private UserRepository usersRepository;

	@Autowired
	private CourseRepository courseRepository;

	@Autowired
	private UserService userService;

	@Autowired
	private CourseService courseService;

	@RequestMapping("/adminPage")
	public String adminRoot(Model model) {
		model.addAttribute("editSection", true);
		return "admin";
	}

	@RequestMapping("/adminPage/manageUsers")
	public String manageUsers(Model model, String action) {
		model.addAttribute("manageUsersSection", true);
		List<User> users = usersRepository.findAll();
		model.addAttribute("users", users);
		return "admin";
	}

	@RequestMapping("/adminPage/manageUsers/delete/{id}")
	public String manageUsersDelete(@PathVariable long id) {
		Optional<User> user = usersRepository.findById(id);
		if (user.isPresent()){
			userService.delete(user.get());	
			return "redirect:/adminPage/manageUsers";
		}
		else {
			return null;
		}
	}

	@RequestMapping("/adminPage/manageCourses")
	public String manageGroups(Model model, String action) {
		model.addAttribute("manageCoursesSection", true); // For the template
		List<Course> courses = courseRepository.findAll();
		model.addAttribute("courses", courses);
		return "admin";
	}

	@RequestMapping("/adminPage/manageCourses/delete/{id}")
	public String manageGroupsDelete(@PathVariable long id) {
		Optional<Course> course = courseRepository.findById(id);
		if (course.isPresent()){
		courseService.delete(course.get());
		return "redirect:/adminPage/manageCourses";
	}
		else {
			return null;
		}
	}

	@RequestMapping("/adminPage/manageCourses/addCourse")
	public String addCourset(Model model) {
		model.addAttribute("addCourse", true);
		EnumSet<Category> categories = EnumSet.allOf(Category.class);
		model.addAttribute("categories", categories);
		return "addCourse";
	}

	@PostMapping("/adminPage/manageCourses/addCourse")
	public String registerCourse(@RequestParam String name, @RequestParam Category category,
			@RequestParam String description, @RequestParam MultipartFile file, @RequestParam String schedules) {
		String[] schedule = schedules.split(" ");
		List<Schedule> listSchedule = new ArrayList<Schedule>();
		for (String item : schedule) {
			Schedule subSchedule = new Schedule(item);
			listSchedule.add(subSchedule);
		}

		courseService.createNewCourse(name, category, description, file, listSchedule);
		return "redirect:/adminPage/manageCourses";
	}
}
