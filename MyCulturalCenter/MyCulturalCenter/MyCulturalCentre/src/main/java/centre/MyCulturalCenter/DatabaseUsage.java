package centre.MyCulturalCenter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Controller;

import centre.MyCulturalCenter.model.Category;
import centre.MyCulturalCenter.model.Course;
import centre.MyCulturalCenter.model.Schedule;
import centre.MyCulturalCenter.model.User;
import centre.MyCulturalCenter.repository.CourseRepository;
import centre.MyCulturalCenter.repository.UserRepository;

@Controller
public class DatabaseUsage implements CommandLineRunner {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private CourseRepository courseRepository;


	@Override
	public void run(String... arg0) {
		
		//DEFAULT USERS

		User user1 = new User("Pedro", "Picapiedra", 25, "pass", "pedrito@gmail.com", "Pedrito47", "ROLE_USER");
		user1.setImgSrc("/uploads/img/profile");
		User user2 = new User("Juan", "Calero", 29, "pass", "juan@gmail.com", "Juanito21", "ROLE_USER");
		user2.setImgSrc("/uploads/img/profile");
		User user3 = new User("Manolo", "Eldelbombo", 28, "pass", "manolito@gmail.com", "Manolito1", "ROLE_USER");
		user3.setImgSrc("/uploads/img/profile");
		// Admin user 
		
		User user4 = new User("Admin", "Admin", 25, "pass", "admin@gmail.com", "admin","ROLE_USER", "ROLE_ADMIN");
		user4.setImgSrc("/uploads/img/profile.png");
		
		Schedule schedule1 = new Schedule ("10:00-11:00");
		Schedule schedule2 = new Schedule ("11:00-12:00");
		Schedule schedule3 = new Schedule ("12:00-13:00");
        Schedule schedule4 = new Schedule ("17:00-18:00");
        Schedule schedule9 = new Schedule ("17:00-18:00");
        Schedule schedule10 = new Schedule ("18:00-19:00");
        Schedule schedule11 = new Schedule ("10:00-11:00");
		Schedule schedule12 = new Schedule ("11:00-12:00");
		Schedule schedule13 = new Schedule ("12:00-13:00");
        Schedule schedule14 = new Schedule ("17:00-18:00");
        Schedule schedule17 = new Schedule ("11:00-12:00");
        Schedule schedule18 = new Schedule ("12:00-13:00");
        

        Course course1 = new Course("Teatro", Category.ARTES, "Tenemos talleres de todo tipo de obras y niveles, ideales para gente que no haya hecho teatro nunca y quiera probarlo", schedule1, schedule2);
        schedule1.setCourse(course1);
        schedule2.setCourse(course1);
        course1.setSrc("/uploads/img/img-20201606-041539-655");

		Course course2 = new Course("Fotografía", Category.ARTES, "Vas de vacaciones y nunca sabes hacer fotos, ¿Quieres remediarlo?, nosotros te enseñamos", schedule3, schedule4);
		schedule3.setCourse(course2);
		schedule4.setCourse(course2);
		course2.setSrc("/uploads/img/img-20201606-051042-655");

		
		Course course12 = new Course("Dibujo", Category.ARTES, "Todos sabemos lo relajante y didáctico que puede ser dibujar, ven a este taller de dibujo para adultos a redescubrirte a ti mismo, también disponible para niños.", schedule9, schedule10);
		schedule9.setCourse(course12);
		schedule10.setCourse(course12);
		course12.setSrc("/uploads/img/img-20201606-051342-655");
		
		Course course3 = new Course("Ajedrez", Category.JUEGOS, "Talleres para aprender a jugar a uno de los juegos más famosos del mundo, competitivos y emocionantes a partes iguales", schedule13, schedule14);
		schedule13.setCourse(course3);
		schedule14.setCourse(course3);
		course3.setSrc("/uploads/img/img-20201606-040910-655");
		
	
		Course course5 = new Course("Idiomas", Category.APRENDIZAJE, "La importancia de aprender idiomas actualmente es altísima, disponibles talleres de Inglés de todos los niveles y precios", schedule17, schedule18);
		schedule17.setCourse(course5);
		schedule18.setCourse(course5);
		course5.setSrc("/uploads/img/img-20201606-051242-655");
			
		
		Course course9 = new Course("Mindfulness", Category.MIND, "Mindfulness puede definirse sencillamente como la capacidad de prestar atención de manera conciente a la experiencia del momento presente con interés, curiosidad y aceptación, con estos talleres aprenderás a relajarte y a rebajar tu estrés", schedule11, schedule12);
		schedule11.setCourse(course9);
		schedule12.setCourse(course9);
		course9.setSrc("/uploads/img/img-20201606-051142-655");
		
		
		courseRepository.save(course1);
		courseRepository.save(course2);
		courseRepository.save(course3);
		courseRepository.save(course5);
		courseRepository.save(course9);
		courseRepository.save(course12);
	

	/*	user2.addCourse(course1);*/
		schedule1.addUser(user1);
		schedule1.addUser(user2);
		schedule1.addUser(user3);
		schedule2.addUser(user2);



		userRepository.save(user1);
		userRepository.save(user2);
		userRepository.save(user3);
		// Admin save 
		userRepository.save(user4);


	}

}
