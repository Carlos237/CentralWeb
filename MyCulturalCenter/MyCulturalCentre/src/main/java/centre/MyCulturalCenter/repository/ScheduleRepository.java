
package centre.MyCulturalCenter.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import centre.MyCulturalCenter.model.Schedule;
import centre.MyCulturalCenter.model.User;

import java.util.List;

@Repository
public interface ScheduleRepository extends JpaRepository <Schedule, Long>{

	public List<Schedule> findByListUsersContains(User u);


}
