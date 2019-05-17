package pl.cms.servlet;
import java.io.IOException;
import javax.servlet.ServletContext;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class DownloadContent {
	
	// content - tresc do zamiany
	public static String download(String content, ServletContext context, String server_message) {
		String page = null;
		try {
			page = getFile("main.html", context);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		page = replaceString(page, "[[HEADER]]", "header.html", context);
		// wstawianie wiadomoœci error w przypadku b³êdu, lub pustej wiadomoœci w przypadku gdy nie ma b³êdu
		if (server_message.length() != 0) {
			page = insertMessage(page, "[[SERVER_MESSAGE]]", server_message);
		} else {
			page = insertMessage(page, "[[SERVER_MESSAGE]]", "");
		}
		page = replaceString(page, "[[MAIN]]", content + ".html", context);
		page = replaceString(page, "[[FOOTER]]", "footer.html", context);
		
		Element headerDb = DatabaseController.downloadElementFromDb("naglowek");
		page = insertStyles(page, headerDb, "[[HEADER_STYLE]]");
		page = insertMessage(page, "[[naglowek]]", headerDb.getHtml());
		
		Element footerDb = DatabaseController.downloadElementFromDb("stopka");
		page = insertStyles(page, footerDb, "[[FOOTER_STYLE]]");
		page = insertMessage(page, "[[stopka]]", footerDb.getHtml());
		
		Element htmlDb = DatabaseController.downloadElementFromDb("html");
		page = insertStyles(page, htmlDb, "[[HTML_STYLE]]");

		return page;
	}
	
	// page - strona html w postaci string na której bêdziemy zamieniaæ
	// stringToReplace - string do zamiany, np: [[HEADER]]
	// fileName - nazwa pliku do wstawienia za stringToReplace
	public static String replaceString(String page, String stringToReplace, String fileName, ServletContext context) {
		try {
			String file = getFile(fileName, context);
			return page.replace(stringToReplace, file);
		} catch (IOException e) {
			e.printStackTrace();
			return page;
		}
	}

	// fileName - nazwa pliku
	public static String getFile(String fileName, ServletContext context) throws IOException {
		String filePath = "WEB-INF/pages/" + fileName;

		InputStream is = context.getResourceAsStream(filePath);
		if (is != null) {
			String output = null;
			InputStreamReader isr = new InputStreamReader(is);
			BufferedReader reader = new BufferedReader(isr);
			String text;

			while ((text = reader.readLine()) != null) {
				if (output == null)
					output = text;
				else
					output += text;
			}
			return output;
		}
		return null;
	}
	
	public static String insertMessage(String page, String stringToReplace, String message) {
		return page.replace(stringToReplace, message);
	}
	
	public static String insertStyles(String page, Element element, String stylesName) {
		
		String styles = "style='color:" + element.getFontColor() + "; font-size: " + 
	element.getFontSize() + "; font-weight: " + element.getFontWeight() + "; background: " + 
				element.getBackground() + "'";
		return page.replace(stylesName, styles);
	}

}
