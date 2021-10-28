package centre.MyCulturalCenter.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import centre.MyCulturalCenter.model.Category;
import centre.MyCulturalCenter.model.Course;

import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
	
    public List<Course> findByCategory(Category category);
    
    public List<Course> findByName(String name);

    Course findById(long id);
}
