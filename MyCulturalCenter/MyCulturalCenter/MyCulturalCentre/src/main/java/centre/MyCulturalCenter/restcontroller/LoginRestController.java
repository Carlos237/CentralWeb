package centre.MyCulturalCenter.restcontroller;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;

import centre.MyCulturalCenter.component.UserComponent;
import centre.MyCulturalCenter.controller.LoginController;
import centre.MyCulturalCenter.model.User;

@RestController
public class LoginRestController {
private static final Logger log = LoggerFactory.getLogger(LoginController.class);
	
	@Autowired
	private UserComponent userComponent;
	
	interface UserDetails{
	}
	
	@JsonView(UserDetails.class)
	@RequestMapping("/api/login")
	public ResponseEntity<User> logIn(){
		if(!userComponent.isLoggedUser()){
			log.info("Not user logged");
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}else{
			 User loggedUser = userComponent.getLoggedUser();
			log.info("Logged as " + loggedUser.getNickname());
			return new ResponseEntity<>(loggedUser,HttpStatus.OK); /*Realiza la comprobación de si el usuario está logueado o no por consola*/
		}
	}

	@JsonView(UserDetails.class)
	@RequestMapping("/api/logout")
	public ResponseEntity<Boolean> logOut(HttpSession session){
		if(!userComponent.isLoggedUser()){
			log.info("No user logged");
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}else{
			session.invalidate();
			log.info("Logged out");
			return new ResponseEntity<>(true,HttpStatus.OK);
		}
	}
}


