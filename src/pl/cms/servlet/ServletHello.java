	package pl.cms.servlet;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/ServletHello")
public class ServletHello extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public ServletHello() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
    	response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");
		ServletContext context = getServletContext();
		User user;	// do przechowywania zalogowanego uzytkownika
		
    	String action;	// do wyswietlania odpowiedniej strony
    	String content = null; // do przechowywania odpowiedniej strony
    		
		String loginCookie = getCookie(request); // pobierz ciasteczko z loginem
		
		// jak nie ma parametru content, to wyswietlam logowanie
		if (request.getParameter("content") != null) {
			action = request.getParameter("content");
		} else {
			action = "loginContent";
		}
		
		switch(action) {
		case "loginContent":
			if(loginCookie.length() <= 0) {
				content = DownloadContent.download("login", context, "");
				content = fillData(content, "[[LOGIN_STYLE]]", "logowanie");
			} else {	// jeœli jestem ju¿ zalogowany to nie pokazuje logowania
				content = DownloadContent.download("profil", context, "");
				content = fillData(content, "[[PROFIL_STYLE]]", "profil");
			}
			break;
		case "registerContent":
			if(loginCookie.length() <= 0) {
				content = DownloadContent.download("register", context, "");
				content = fillData(content, "[[REGISTER_STYLE]]", "rejestracja");
			} else {	// jeœli jestem ju¿ zalogowany to nie pokazuje rejestracji
				content = DownloadContent.download("profil", context, "");
				content = fillData(content, "[[PROFIL_STYLE]]", "profil");
			}
			break;
		
		case "profileContent":
			if(loginCookie.length() > 0) { 
				content = DownloadContent.download("profil", context, "");
				content = fillData(content, "[[PROFIL_STYLE]]", "profil");
				user = DatabaseController.getUser(loginCookie);
				content = fillUserData(content, user);
				String styles = "background: url('profileimg/" + user.getProfileImg() + "') !important; background-size: cover !important";
				content = DownloadContent.insertMessage(content, "[[PROFILEIMG_STYLE]]", styles);
			} else {
				content = DownloadContent.download("login", context, "Potrzebujesz byæ zalogowany");
				content = fillData(content, "[[LOGIN_STYLE]]", "logowanie");
			}
			break;
		case "linksContent":
			if (loginCookie.length() > 0) {
				content = DownloadContent.download("links", context, "");
				content = fillData(content, "[[LINKS_STYLE]]", "strony");
				ArrayList<Link> links = DatabaseController.getAllLinks();
				content = displayLinks(content, links);
				break;
			} else {
				content = DownloadContent.download("login", context, "Potrzebujesz byæ zalogowany");
				content = fillData(content, "[[LOGIN_STYLE]]", "logowanie");
			}
			break;
		case "addLinkContent":
			if (loginCookie.length() > 0) {
				content = DownloadContent.download("linksadd", context, "");
				content = fillData(content, "[[LINKSADD_STYLE]]", "dodanieStron");
				break;
			} else {
				content = DownloadContent.download("login", context, "Potrzebujesz byæ zalogowany");
				content = fillData(content, "[[LOGIN_STYLE]]", "logowanie");
			}
			break;
		case "editLinkContent":
			if (loginCookie.length() > 0) {
				ArrayList<Link> links = DatabaseController.getAllLinks();
				content = DownloadContent.download("linksedit", context, "");
				content = fillData(content, "[[LINKSEDIT_STYLE]]", "edycjaStron");
				content = fillLinksForm(content, links);
				break;
			} else {
				content = DownloadContent.download("login", context, "Potrzebujesz byæ zalogowany");
				content = fillData(content, "[[LOGIN_STYLE]]", "logowanie");
			}
			break;
		case "setColorContent":
			if (loginCookie.length() > 0) {
				ArrayList<Element> elements = DatabaseController.getAllElements();
				content = DownloadContent.download("colors", context, "");
				content = fillColorsForm(content, elements);
				break;
			} else {
				content = DownloadContent.download("login", context, "Potrzebujesz byc zalogowany ");
				content = fillData(content, "[[LOGIN_STYLE]]", "logowanie");
			}
			break;
		case "editContent":
			if (loginCookie.length() > 0) {
				content = DownloadContent.download("editprofil", context, "");
				content = fillData(content, "[[EDITPROFIL_STYLE]]", "editProfil");
				user = DatabaseController.getUser(loginCookie);
				content = fillUserData(content, user);
				String styles = "background: url('profileimg/" + user.getProfileImg() + "') !important; background-size: cover !important";
				content = DownloadContent.insertMessage(content, "[[PROFILEIMG_STYLE]]", styles);
			} else {
				content = DownloadContent.download("login", context, "Potrzebujesz byc zalogowany ");
				content = fillData(content, "[[LOGIN_STYLE]]", "logowanie");
			}
			break;
		}
		if(loginCookie.length() > 0) {
			content = DownloadContent.insertMessage(content, "[[USER.LOGIN]]", loginCookie);
		}
		response.getWriter().println(content);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");
		ServletContext context = getServletContext();
		
		String action;	// do wyswietlania odpowiedniej strony
    	String content = null; // do przechowywania odpowiedniej strony
		String msg = "";	// do wyswietlania wiadomosci o bledzie
		User user;
		
		String loginCookie = getCookie(request); // pobierz ciasteczko z loginem
    	
		// jak nie ma parametru content, to wyswietlam logowanie
		if (request.getParameter("content") != null) {
			action = request.getParameter("content");
		} else {
			action = "loginContent";
		}
		
		switch(action) {
		case "loginContent":
			msg = ValidatorController.validateLogin(request.getParameter("login"), request.getParameter("password"));
			if(msg.length() != 0) {	// pole login lub haslo nie zosta³o uzupelnione
				content = DownloadContent.download("login", context, msg);
				content = fillData(content, "[[LOGIN_STYLE]]", "logowanie");
				break;
			}
			else if(DatabaseController.loginUser(request.getParameter("login"), request.getParameter("password"))) {
				content = DownloadContent.download("profil", context, "");
				content = fillData(content, "[[PROFIL_STYLE]]", "profil");
				response.addCookie(new Cookie("login", request.getParameter("login")));
				loginCookie = request.getParameter("login");
				
				user = DatabaseController.getUser(loginCookie);
				content = fillUserData(content, user);
				String styles = "background: url('profileimg/" + user.getProfileImg() + "') !important; background-size: contain !important;";
				content = DownloadContent.insertMessage(content, "[[PROFILEIMG_STYLE]]", styles);
				break;
			} else {
				content = DownloadContent.download("login", context, "B³êdne dane logowania");
				content = fillData(content, "[[LOGIN_STYLE]]", "logowanie");
				break;
			}
		case "registerContent":
			if(request.getParameter("registerSubmit") != null) {
				msg = ValidatorController.validateRegister(request.getParameter("firstName"), 
														   request.getParameter("lastName"), 
														   request.getParameter("email"),
														   request.getParameter("password"), 
														   request.getParameter("login"));
				if(msg.length() != 0) { // jakies pole rejestacji nie zosalo uzupe³nione
					content = DownloadContent.download("register", context, msg);
					content = fillData(content, "[[REGISTER_STYLE]]", "rejestracja");
					break;
				}	
				else if(DatabaseController.registerUser(request.getParameter("firstName"), 
														request.getParameter("lastName"), 
														request.getParameter("email"),
														request.getParameter("password"), 
														request.getParameter("login"))) {
					content = DownloadContent.download("login", context, "");
					content = fillData(content, "[[LOGIN_STYLE]]", "logowanie");
					break;
				}
			}
			break;
		case "logoutContent":
			Cookie[] cookies = request.getCookies();	// czyszczenie cookiesow
			if (cookies != null) {
				for (Cookie cookie : cookies) {
					cookie.setValue("");
					cookie.setPath("/cms");
					cookie.setMaxAge(0);
					response.addCookie(cookie);
				}
			}
			content = DownloadContent.download("login", context, "");
			content = fillData(content, "[[LOGIN_STYLE]]", "logowanie");
			break;
		case "editContent":	
			if (loginCookie != null) {
				if(request.getParameter("editSubmit") != null) {	// wys³anie formularza z edytowanymi danymi
					msg = ValidatorController.validateEditProfile(request.getParameter("firstName"), 
							   									  request.getParameter("lastName"), 
							   									  request.getParameter("email"),
							   									  request.getParameter("password"));
					if (msg.length() != 0 ) {
						user = DatabaseController.getUser(loginCookie);
						content = DownloadContent.download("editprofil", context, msg);
						content = fillData(content, "[[EDITPROFIL_STYLE]]", "editProfil");
						String styles = "background: url('profileimg/" + user.getProfileImg() + "') !important; background-size: cover !important";
						content = DownloadContent.insertMessage(content, "[[PROFILEIMG_STYLE]]", styles);
						content = fillUserData(content, user);
						break;
					} else {
						user = new User();
						user.setLogin(loginCookie);
						user.setFirstName(request.getParameter("firstName"));
						user.setLastName(request.getParameter("lastName"));
						user.setEmail(request.getParameter("email"));
						DatabaseController.updateUser(user, request.getParameter("password"));
						content = DownloadContent.download("profil", context, "");
						content = fillData(content, "[[PROFIL_STYLE]]", "profil");
						user = DatabaseController.getUser(loginCookie);
						content = fillUserData(content, user);
						String styles = "background: url('profileimg/" + user.getProfileImg() + "') !important; background-size: cover !important";
						content = DownloadContent.insertMessage(content, "[[PROFILEIMG_STYLE]]", styles);
						break;
					}
				}
				break;
			}
			break;
		case "setColorContent":
			if (loginCookie != null) {
				ArrayList<Element> elements = DatabaseController.getAllElements();
				if(request.getParameter("editColors") != null) {
					updateElements(elements, request);
				}
				content = DownloadContent.download("colors", context, "");
				content = fillColorsForm(content, elements);
				break;
			}
			break;
		case "addLinkContent":
			if (loginCookie != null) {
				if(request.getParameter("linksaddSubmit") != null) {
					DatabaseController.addLink(request.getParameter("displayName"), request.getParameter("href"), request.getParameter("name"));
				}
				content = DownloadContent.download("linksadd", context, "");
				content = fillData(content, "[[LINKSADD_STYLE]]", "dodanieStron");
				break;
			}
			break;
		case "editLinkContent":
			if (loginCookie != null) {
				ArrayList<Link> links = DatabaseController.getAllLinks();
				if(request.getParameter("editLinks") != null) {
					updateLinks(links, request);
				}
				content = DownloadContent.download("linksedit", context, "");
				content = fillData(content, "[[LINKSEDIT_STYLE]]", "edycjaStron");
				content = fillLinksForm(content, links);
				break;
			}
			break;
		case "profileImg":
			user = new User();
			user.setLogin(loginCookie);
			user.setProfileImg(request.getParameter("profileImg"));
			DatabaseController.updateProfileImg(user);
			content = DownloadContent.download("profil", context, "");
			String styles = "background: url('profileimg/" + request.getParameter("profileImg") + "') !important; background-size: cover !important";
			content = DownloadContent.insertMessage(content, "[[PROFILEIMG_STYLE]]", styles);
			content = fillData(content, "[[PROFIL_STYLE]]", "profil");
			user = DatabaseController.getUser(loginCookie);
			content = fillUserData(content, user);
			break;
		}
		if(loginCookie.length() > 0) {
			content = DownloadContent.insertMessage(content, "[[USER.LOGIN]]", loginCookie);
		}
		
		response.getWriter().println(content);
	}
	
	// uzupe³nij dane na stronie danymi z bazy: style oraz treœæ
	private static String fillData(String page, String styles, String name) {
		Element element = DatabaseController.downloadElementFromDb(name);
		page = DownloadContent.insertStyles(page, element, styles);
		page = DownloadContent.insertMessage(page, "[[" + name + "]]", element.getHtml());
		return page;
	}
	
	// uzupe³nij dane zalogowanego u¿ytkownika na stronie
	private static String fillUserData(String page, User user) {
		page = DownloadContent.insertMessage(page, "[[USER.FIRST_NAME]]", user.getFirstName());
		page = DownloadContent.insertMessage(page, "[[USER.LAST_NAME]]", user.getLastName());
		page = DownloadContent.insertMessage(page, "[[USER.EMAIL]]", user.getEmail());
		page = DownloadContent.insertMessage(page, "[[USER.LOGIN]]", user.getLogin());
		return page;
	}
	
	// pobierz ciasteczko 'login'
	private static String getCookie(HttpServletRequest request) {
		Cookie[] cookies = request.getCookies();
		String loginCookie = "";
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals("login")) {
					loginCookie = cookie.getValue();
				}
			}
		}
		return loginCookie;
	}
	
	// generuj formularz do zmieniania kolorów i treœci elementów
	private static String fillColorsForm(String page, ArrayList<Element> elements) {
		String colorsForm = "";
		for(Element el: elements) {
			if(el.getName().equals("html")) {
				colorsForm += "<tr>";
				colorsForm += "<td>" + el.getName() + " - kolor t³a</td>";
				colorsForm += "<td><input type='text' value='" + el.getBackground() + "' name='" + el.getName() + ".BACKGROUND'></td>";
				colorsForm += "</tr>";
			}else {
			colorsForm += "<tr>";
			colorsForm += "<td>" + el.getName() + " - kolor czcionki </td>";
			colorsForm += "<td><input type='text' value='" + el.getFontColor() + "' name='" + el.getName() + ".FONT_COLOR'></td>";
			
			colorsForm += "<td>" + el.getName() + " - rozmiar czcionki </td>";
			colorsForm += "<td><input type='text' value='" + el.getFontSize() + "' name='" + el.getName() + ".FONT_SIZE'></td>";
			colorsForm += "</tr>";
			
			colorsForm += "<tr>";
			colorsForm += "<td>" + el.getName() + " - waga czcionki </td>";
			colorsForm += "<td><input type='text' value='" + el.getFontWeight() + "' name='" + el.getName() + ".FONT_WEIGHT'></td>";
			
			colorsForm += "<td>" + el.getName() + " - kolor t³a</td>";
			colorsForm += "<td><input type='text' value='" + el.getBackground() + "' name='" + el.getName() + ".BACKGROUND'></td>";
			colorsForm += "</tr>";
			
			colorsForm += "<tr>";
			colorsForm += "<td>" + el.getName() + " Tekst: </td>";
			colorsForm += "<td><textarea form='colorsForm' name='" + el.getName() + ".HTML'>" + el.getHtml() + "</textarea></td>";
			colorsForm += "</tr>";
			}
		}
		page = DownloadContent.insertMessage(page, "[[COLORS_FORM]]", colorsForm);
		return page;
	}
	
	// updatuj elementy w bazie danymi z formularza kolorów
	private static void updateElements(ArrayList<Element> elements, HttpServletRequest request) {
		for(Element el: elements) {
			el.setBackground(request.getParameter(el.getName()+".BACKGROUND"));
			el.setFontColor(request.getParameter(el.getName()+".FONT_COLOR"));
			el.setFontSize(request.getParameter(el.getName()+".FONT_SIZE"));
			el.setFontWeight(request.getParameter(el.getName()+".FONT_WEIGHT"));
			el.setHtml(request.getParameter(el.getName()+".HTML"));
			DatabaseController.updateElement(el);
		}
	}
	
	// wyswietl linki z bazy
	private static String displayLinks(String page, ArrayList<Link> links) {
		String linksList = "";
		for(Link li: links) {
			linksList += "<br/><a style=color:black; href='https://" + li.getHref() + "'>" + li.getDisplayName() + "</a>";
		}
		page = DownloadContent.insertMessage(page, "[[strony_lista]]", linksList);
		return page;
	}
	
	// generuj formularz do edycji linków
	private static String fillLinksForm(String page, ArrayList<Link> links) {
		String linksForm = "";
		for(Link li: links) {
			linksForm += "<tr>";
			linksForm += "<td>" + li.getName() + " - wyœwietlaj jako: </td>";
			linksForm += "<td><input type='text' value='" + li.getDisplayName() + "' name='" + li.getName() + ".DISPLAY_NAME'></td>";
			
			linksForm += "<td>" + li.getName() + " - Adres docelowy: </td>";
			linksForm += "<td><input type='text' value='" + li.getHref() + "' name='" + li.getName() + ".HREF'></td>";
			linksForm += "</tr>";
			
		}
		page = DownloadContent.insertMessage(page, "[[LINKS_FORM]]", linksForm);
		return page;
	}
	
	// updatuj linki w bazie danymi z formularza linków
	private static void updateLinks(ArrayList<Link> links, HttpServletRequest request) {
		for(Link li: links) {
			li.setDisplayName(request.getParameter(li.getName()+".DISPLAY_NAME"));
			li.setHref(request.getParameter(li.getName()+".HREF"));
			DatabaseController.updateLink(li);
		}
	}
}
