package centre.MyCulturalCenter.model;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonView;

import centre.MyCulturalCenter.model.Course.Basic;

import java.util.*;

@Entity
public class Schedule {
	public interface Basic{}
	public interface Details{}
	public interface DontShow{}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@JsonView(Basic.class)
	private long idSchedule;
	
	@JsonView(Basic.class)
	private String schedule;
	
	@JsonView(Details.class)
	private int capacity;

	public int getCapacity() {
		return capacity;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

	@ManyToMany
	private Set<User> listUsers = new HashSet<>();

	@ManyToOne(cascade = CascadeType.ALL)
	Course course;

	protected Schedule() {

	}

	public String getSchedule() {
		return schedule;
	}

	public void setSchedule(String schedule) {
		this.schedule = schedule;
	}

	public Schedule(String schedule) {
		super();
		this.schedule = schedule;
	}

	public void addUser(User user) {
		if(!listUsers.contains(user)) {
			this.listUsers.add(user);
			this.capacity = getCapacity() + 1;
		}
	}

	public void deleteUser(User user) {
		if(listUsers.contains(user)) {
			this.listUsers.remove(user);
			this.capacity = getCapacity() - 1;
		}
	}

	public List<User> getUser() {

		return new ArrayList<>(listUsers);
	}

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		Schedule schedule = (Schedule) o;
		return idSchedule == schedule.idSchedule;
	}

	@Override
	public int hashCode() {

		return Objects.hash(idSchedule);
	}
}
