package centre.MyCulturalCenter.service;



import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import centre.MyCulturalCenter.model.Category;
import centre.MyCulturalCenter.model.Course;
import centre.MyCulturalCenter.model.Schedule;
import centre.MyCulturalCenter.model.User;
import centre.MyCulturalCenter.repository.CourseRepository;

@Service
public class CourseService {
	@Autowired
	private CourseRepository courseRepository;

	@Autowired
	private ImageService imageService;

	public List<Course> getAllCourses() {
		return courseRepository.findAll();
	}
	
	public Page<Course> getPageCourses() {
		Pageable p = PageRequest.of(0, 6);
		return courseRepository.findAll(p);
	}
	public Optional<Course> findCourse(long id) {
		return courseRepository.findById(id);
	}

	public void follow(User user, Course course) {
		if (user.getCourseList().contains(course)) {
			user.removeCourse(course);
		} else {
			user.addCourse(course);
		}
	}

	public Course createNewCourse(String name, Category category, String description, MultipartFile file,
			List<Schedule> schedules) {
		Course course = new Course(name, category, description, schedules);
		
		if (!file.isEmpty()) {
			course.setSrc(imageService.uploadImage(file));
		} else {
			course.setSrc("/uploads/img/default");
		}
		for(Schedule schedule:schedules) {
			schedule.setCourse(course);
		}
		courseRepository.save(course);
		return course;
	}

	public void delete(Course course) {
		
	}
}
