package centre.MyCulturalCenter.restcontroller;

import java.security.Principal;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;

import centre.MyCulturalCenter.component.UserComponent;
import centre.MyCulturalCenter.model.Course;
import centre.MyCulturalCenter.model.User;
import centre.MyCulturalCenter.restcontroller.CourseRestController.CourseDetail;
import centre.MyCulturalCenter.service.CourseService;
import centre.MyCulturalCenter.service.UserService;



@RestController
@RequestMapping("/api/admin")
public class AdminRestController {

	interface UserDetail extends User.Basic, User.Details {
	}
	@Autowired
	private UserService userService;
	
	@Autowired
	private CourseService courseService;
	
	@Autowired
	private UserComponent userComponent;
	
	
	
	@GetMapping(value ="/")
	@ResponseStatus(HttpStatus.OK)
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<List<User>>getUsers() {
		List <User> users = userService.getUsers();
		if (users != null) {
			return new ResponseEntity<>(users, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	 @GetMapping(value = "/{id}")
	    public ResponseEntity<User> getUser(@PathVariable long id) {
	        User user = userService.getUserbyID(id);
	        if (user != null) {
	            return new ResponseEntity<>(user, HttpStatus.OK);
	        } else {
	            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	        }
	    }
	 
	 @DeleteMapping(value = "/user/delete/{id}")
	    @PreAuthorize("hasRole('ROLE_ADMIN')")
	    public ResponseEntity<?> deleteUser(@PathVariable long id) {
	    	User userLogged = userService.findOne(userComponent.getLoggedUser().getId());
	    	if(userLogged.isAdmin()== true) {
		        if (userService.delete(id)) {
		            return new ResponseEntity<>(HttpStatus.OK);
		        }
		        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	    	}
	    	return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	    }

	    @PatchMapping(value = "/user/edit/{id}")
	    public ResponseEntity<?> editUser(@PathVariable long id, @RequestBody User user) {
	        User userLogged = userService.findOne(userComponent.getLoggedUser().getId());

	        if (userLogged.isAdmin() == true) {
	            User userUpdated = userService.getUserbyID(id);
	            if (userUpdated != null) {
	                userUpdated = userService.updateUserInfo(id, user);
	                return new ResponseEntity<>(userUpdated, HttpStatus.OK);
	            } else {
	                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	            }
	        } else {
	            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	        }
	    }


	    /****************** COURSES **************/

	    @RequestMapping(value = "/courses", method = RequestMethod.GET)
	    @ResponseStatus(HttpStatus.OK)
	    public ResponseEntity<List<Course>> getCourses(Principal principal) {
	    	User userLogged = userService.findOne(principal.getName());
	        List<Course> courses = courseService.getAllCourses();
	    	if(userLogged.isAdmin()) {
		        if (courses != null) {
		            return new ResponseEntity<>(courses, HttpStatus.OK);
		        } else {
		            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		        }
	    	} else {
	    		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	    	}
	    }

		@RequestMapping(value = "/course/{id}", method = RequestMethod.GET)
		@ResponseStatus(HttpStatus.OK)
		@JsonView(CourseDetail.class)
		public ResponseEntity<Course> getCourseId(@PathVariable long id) {
			User userLogged = userService.findOne(userComponent.getLoggedUser().getId());
			Course course = courseService.findCourse(id);
			if(userLogged.isAdmin()) {
				if (course != null) {
					return new ResponseEntity<>(course, HttpStatus.OK);
				} else {
					return new ResponseEntity<>(HttpStatus.NOT_FOUND);
				}
			} else {
				return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
			}
		}

		private interface CourseDetails extends Course.Details, Course.Basic{}
	    @JsonView(CourseDetails.class)
	    @RequestMapping(value = "/course/add", method = RequestMethod.POST)
	    @ResponseStatus(HttpStatus.CREATED)

	    public ResponseEntity<Course> addCourse(@RequestBody Course course) {

	        if (userComponent.isLoggedUser()) {
	            courseService.save(course);
	            return new ResponseEntity<>(course, HttpStatus.OK);
	        } else {
	            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	        }
	    }

	    /**
	     * Delete user by id
	     *
	     * @param id
	     * @return
	     */

	    @DeleteMapping(value = "/courses/delete/{id}")
	    @PreAuthorize("hasRole('ROLE_ADMIN')")
	    public ResponseEntity<?> deleteCourse(@PathVariable long id) {
	    	User userLogged = userService.findOne(userComponent.getLoggedUser().getId());
	    	if(userLogged.isAdmin()) {
		        if (courseService.delete(id)) {
		            return new ResponseEntity<>(HttpStatus.OK);
		        }
		        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	    	}else {
	    		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	    	}
	    }

	    @PatchMapping(value = "/course/edit/{id}")
	    public ResponseEntity<Course> updateCourse(@PathVariable long id, @RequestBody Course course) {
	        User userLogged = userService.findOne(userComponent.getLoggedUser().getId());

	        if (userLogged.isAdmin()) {
	            Course courseUpdated = courseService.findCourse(id);
	            if (courseUpdated != null) {
	                courseUpdated = courseService.updateCourse(id, course);
	                return new ResponseEntity<>(courseUpdated, HttpStatus.OK);
	            } else {
	                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	            }
	        } else {
	            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	        }
	    }

	}

	