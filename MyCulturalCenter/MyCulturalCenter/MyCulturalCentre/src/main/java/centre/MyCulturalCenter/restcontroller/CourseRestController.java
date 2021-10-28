package centre.MyCulturalCenter.restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;

import java.util.List;
import centre.MyCulturalCenter.component.UserComponent;
import centre.MyCulturalCenter.model.Course;
import centre.MyCulturalCenter.model.Schedule;
import centre.MyCulturalCenter.model.User;
import centre.MyCulturalCenter.service.CourseService;
import centre.MyCulturalCenter.service.UserService;

@RestController
@RequestMapping("/api/courses")
public class CourseRestController {

	interface CourseDetail extends Course.Basic, Course.Details, Schedule.Basic, Schedule.Details {
	
	}
	
	@Autowired
	private CourseService courseService;
	
	@Autowired
	private UserService userService;
	
	@Autowired 
	private UserComponent userComponent;

@GetMapping(value = "/")
@ResponseStatus(HttpStatus.OK)
@JsonView(Course.Basic.class)
public ResponseEntity<List<Course>> getCourses() {
		List<Course> courses = courseService.getAllCourses();
		if (courses != null) {
			return new ResponseEntity<>(courses, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

@GetMapping(value = "/{id}")
@ResponseStatus(HttpStatus.OK)
@JsonView(CourseDetail.class)
public ResponseEntity<Course> getCourseId(@PathVariable long id){
	Course course = courseService.findCourse(id);
	if (course != null) {
		return new ResponseEntity<>(course, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
}

@PostMapping(value = "/add")
@ResponseStatus(HttpStatus.CREATED)
@PreAuthorize("hasRole('ROLE_ADMIN')")
@JsonView(CourseDetail.class)
public ResponseEntity<Course> postCourse(@RequestBody Course course){
	User userlogged = userService.findOne(userComponent.getLoggedUser().getId());
	if (userlogged != null) {
		courseService.save(course);
		return new ResponseEntity<>(course, HttpStatus.OK);
	}else {
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			
		}
	
}

@PutMapping(value = "/edit/{id}")
@PreAuthorize("hasRole('ROLE_ADMIN')")
public ResponseEntity<Course> editCourseId(@PathVariable long id, @RequestBody Course editableCourse){
	Course course = courseService.findCourse(id);
	User userlogged = userService.findOne(userComponent.getLoggedUser().getId());
	if (userlogged != null) {
	if (course != null && editableCourse != null) {
		editableCourse.setId(id);
		courseService.save(editableCourse);
		return new ResponseEntity<>(editableCourse, HttpStatus.ACCEPTED);
	} else {
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
} else {
	return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
}

}



@DeleteMapping(value = "/delete/{id}")
@ResponseStatus(HttpStatus.OK)
@PreAuthorize("hasRole('ROLE_ADMIN')")
@JsonView(CourseDetail.class)
public ResponseEntity<Course> DeleteCourseId(@PathVariable long id , @RequestBody Course deletedCourse){
	Course course = courseService.findCourse(id);
	if (course != null && deletedCourse != null) {
		courseService.delete(id);
		return new ResponseEntity<>(deletedCourse, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
}



}
