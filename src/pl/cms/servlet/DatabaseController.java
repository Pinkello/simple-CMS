package pl.cms.servlet;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.mysql.jdbc.ResultSetMetaData;

public class DatabaseController {

	// Logowanie u¿ytkownika
	public static boolean loginUser(String login, String password) {
		if(!loadDriver()) {
			return false;
		}
		Connection connection = connectToDatabase();
		String query = "SELECT password FROM user WHERE login = '" + login + "'";
		String fetchedPassword = "";
		try {
			Statement s = connection.createStatement();
			ResultSet r = s.executeQuery(query);
			if(r.next())
				fetchedPassword = r.getString("password");
			s.close();
			connection.close();

			if (fetchedPassword != null && password.equals(fetchedPassword))
				return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return false;
	}
	
	// Rejestracja u¿ytkownika
	public static boolean registerUser(String firstName, String lastName, String email, String password, String login) {
		if(!loadDriver()) {
			return false;
		}
		Connection connection = connectToDatabase();
		String query = "INSERT INTO user(FIRST_NAME, LAST_NAME, EMAIL, LOGIN, PASSWORD, PROFILEIMG) "
				+ "VALUES('" + firstName +"', '" + lastName + "', '" + email + "', '" + login + "', '" + password + "', 'null')";
		try {
			Statement s = connection.createStatement();
			s.executeUpdate(query);
			s.close();
			connection.close();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	// Pobranie danych u¿ytkownika
	public static User getUser(String login) {
		if(!loadDriver()) {
			return null;
		}
		Connection connection = connectToDatabase();
		String query = "SELECT FIRST_NAME, LAST_NAME, EMAIL, LOGIN, PROFILEIMG FROM user WHERE LOGIN = '" + login + "'";
		try {
			Statement s = connection.createStatement();
			ResultSet r = s.executeQuery(query);
			User user = new User();
			if (r.next()) {
				user.setFirstName(r.getString("FIRST_NAME"));
				user.setLastName(r.getString("LAST_NAME"));
				user.setEmail(r.getString("EMAIL"));
				user.setLogin(r.getString("LOGIN"));
				user.setProfileImg(r.getString("PROFILEIMG"));
			}
			s.close();
			connection.close();
			return user;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	// Edytowanie danych zalogowanego u¿ytkownika
	public static boolean updateUser(User user, String password) {
		if(!loadDriver()) {
			return false;
		}
		Connection connection = connectToDatabase();
		String query = "UPDATE user SET " + "FIRST_NAME = '" + user.getFirstName() + "', " + "LAST_NAME='" + user.getLastName()
				+ "', " + "EMAIL='" + user.getEmail() + "', " + "PASSWORD='" + password + "' "
				+ "WHERE login = '" + user.getLogin() + "'";
		try {
			Statement s = connection.createStatement();
			s.executeUpdate(query);
			s.close();
			connection.close();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	// Edytowanie obrazka zalogowanego u¿ytkownika
	public static boolean updateProfileImg(User user) {
		if(!loadDriver()) {
			return false;
		}
		Connection connection = connectToDatabase();
		String query = "UPDATE user SET " + "PROFILEIMG = '" + user.getProfileImg() + "' WHERE login = '" + user.getLogin() + "'";
		try {
			Statement s = connection.createStatement();
			s.executeUpdate(query);
			s.close();
			connection.close();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	// £adowanie sterownika bazy danych
	private static boolean loadDriver() {
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            return true;
        } catch (Exception e) {
            System.out.println("Blad przy ladowaniu sterownika bazy");
            return false;
        }
    }
	
	// Nazwi¹zywanie po³¹czenia z baz¹ danych
	private static Connection connectToDatabase() {
        try {
            return DriverManager.getConnection("jdbc:mysql://localhost:3306/ticms", "root", "");
        } catch (SQLException e) {
            System.out.println("Blad przy nawiazywaniu po³¹czenia z baza ");
            return null;
        }
    }
	
	// Pobieranie elementu z bazy
	public static Element downloadElementFromDb(String name) {
		if(!loadDriver()) {
			return null;
		}
		Connection connection = connectToDatabase();
		String query = "SELECT * FROM elements WHERE NAME = '" + name + "'";
		try {
			Statement s = connection.createStatement();
			ResultSet r = s.executeQuery(query);
			Element element = new Element();
			if (r.next()) {
				element.setName(r.getString("NAME"));
				element.setFontSize(r.getString("FONT_SIZE"));
				element.setFontColor(r.getString("FONT_COLOR"));
				element.setFontWeight(r.getString("FONT_WEIGHT"));
				element.setHtml(r.getString("HTML"));
				element.setBackground(r.getString("BACKGROUND"));
			}
			s.close();
			connection.close();
			return element;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	// Edytowanie elementu z bazy
	public static boolean updateElement(Element element) {
		if(!loadDriver()) {
			return false;
		}
		Connection connection = connectToDatabase();
		String query = "UPDATE elements SET BACKGROUND = '" + element.getBackground() + "', FONT_COLOR='" + element.getFontColor()
		+ "', FONT_SIZE='" + element.getFontSize() + "', FONT_WEIGHT='" + element.getFontWeight() + "', HTML='" + element.getHtml() + "' "
		+ "WHERE name = '" + element.getName() + "'";
		try {
			Statement s = connection.createStatement();
			s.executeUpdate(query);
			s.close();
			connection.close();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	// Pobieranie wszystkich elementów z bazy
	public static ArrayList<Element> getAllElements() {
		if(!loadDriver()) {
			return null;
		}
		Connection connection = connectToDatabase();
		String query = "SELECT * FROM elements";
		try {
			Statement s = connection.createStatement();
			ResultSet r = s.executeQuery(query);
			ArrayList<Element> elements = new ArrayList<Element>();
			while (r.next()) {
				Element el = new Element();
				el.setName(r.getString("NAME"));
				el.setFontSize(r.getString("FONT_SIZE"));
				el.setFontColor(r.getString("FONT_COLOR"));
				el.setFontWeight(r.getString("FONT_WEIGHT"));
				el.setHtml(r.getString("HTML"));
				el.setBackground(r.getString("BACKGROUND"));
				elements.add(el);
			}
			s.close();
			connection.close();
			return elements;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	// Dodawanie linku do bazy
	public static boolean addLink(String displayName, String href, String name) {
		if(!loadDriver()) {
			return false;
		}
		Connection connection = connectToDatabase();
		String query = "INSERT INTO link(HREF, DISPLAY_NAME, NAME) "
				+ "VALUES('" + href +"', '" + displayName + "', '" + name + "')";
		try {
			Statement s = connection.createStatement();
			s.executeUpdate(query);
			s.close();
			connection.close();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	// Pobieranie wszystkich linków z bazy
	public static ArrayList<Link> getAllLinks() {
		if(!loadDriver()) {
			return null;
		}
		Connection connection = connectToDatabase();
		String query = "SELECT * FROM link";
		try {
			Statement s = connection.createStatement();
			ResultSet r = s.executeQuery(query);
			ArrayList<Link> links = new ArrayList<Link>();
			while (r.next()) {
				Link li = new Link();
				li.setHref(r.getString("HREF"));
				li.setDisplayName(r.getString("DISPLAY_NAME"));
				li.setName(r.getString("NAME"));
				links.add(li);
			}
			s.close();
			connection.close();
			return links;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	// Edytowanie linku z bazy
	public static boolean updateLink(Link link) {
		if(!loadDriver()) {
			return false;
		}
		Connection connection = connectToDatabase();
		String query = "UPDATE link SET HREF = '" + link.getHref() + "', DISPLAY_NAME='" + link.getDisplayName() +"' "
		+ "WHERE name = '" + link.getName() + "'";
		try {
			Statement s = connection.createStatement();
			s.executeUpdate(query);
			s.close();
			connection.close();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
}
