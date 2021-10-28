package centre.MyCulturalCenter.service;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
	
	public Course findCourse(long id) {
		return courseRepository.findById(id);
	}
	
	public void save(Course course) {
		courseRepository.save(course);
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

	public boolean delete(long id) {
			Course c = courseRepository.findById(id);
			if (c != null) {
				courseRepository.delete(c);
				return true;
			}
			return false;
		}
	
	public Course updateCourse(long id, Course course){
		Course courseToEdit = courseRepository.findById(id);
		if (course.getCategory()!=null){
			courseToEdit.setCategory(course.getCategory());
		}
		if(course.getDescription()!= null){
			courseToEdit.setDescription(course.getDescription());
		}
		if(course.getName()!=null){
			courseToEdit.setName(course.getName());
		}
		if(course.getSrc()!=null){
			courseToEdit.setSrc(course.getSrc());
		}
		if(course.getSchedules()!=null){
			courseToEdit.setSchedules(course.getSchedules());
		}
		courseRepository.save(courseToEdit);

		return courseToEdit;

	}
		
	}

