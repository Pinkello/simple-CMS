package pl.cms.servlet;

public class ValidatorController {
	
	public static String validateLogin(String login, String password) {
		String message = "";
		if(login.length() == 0 || login == null) {
			message += "Login nie mo¿e byæ pusty ";
		}
		if(password.length() == 0 || password == null) {
			message += "Has³o nie mo¿e byæ puste ";
		}
		return message;
	}
	
	public static String validateRegister(String firstName, String lastName, String email, String password, String login) {
		String message = "";
		if(firstName.length() == 0 || firstName == null) {
			message += "Imie nie mo¿e byæ puste </br>";
		}
		if(lastName.length() == 0 || lastName == null) {
			message += "Nazwisko nie mo¿e byæ puste. </br>";
		}
		if(email.length() == 0 || email == null) {
			message += "Email nie mo¿e byæ pusty </br>";
		}
		if(password.length() == 0 || password == null) {
			message += "Has³o nie mo¿e byæ puste </br>";
		}
		if(login.length() == 0 || login == null) {
			message += "Login nie mo¿e byæ pusty </br>";
		}
		return message;
	}
	
	public static String validateEditProfile(String firstName, String lastName, String email, String password) {
		String message = "";
		if(firstName.length() == 0 || firstName == null) {
			message += "Imie nie mo¿e byæ puste </br>";
		}
		if(lastName.length() == 0 || lastName == null) {
			message += "Nazwisko nie mo¿e byæ puste. </br>";
		}
		if(email.length() == 0 || email == null) {
			message += "Email nie mo¿e byæ pusty </br>";
		}
		if(password.length() == 0 || password == null) {
			message += "Has³o nie mo¿e byæ puste </br>";
		}
		return message;
	}
}
