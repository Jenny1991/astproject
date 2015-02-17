package de.hdm.astproject.server;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Konkretisierung eines HttpServlets, welches eine Bilddatei dem Aufrufenden
 * Client als Response übermittelt. Das Bild wurde zuvor als statische Variable
 * durch eine Instanz von ImageUploadImpl gesetzt.
 */
@SuppressWarnings("serial")
public class ImageDownloadImpl extends HttpServlet{
	
	/**
	 * Byte-Array welches Bilddaten vorhält
	 */
	public static byte[] bbb = null;
	
	/**
	 * Methode die beim empfangen eines Requests vom Client aufgerufen wird
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response) {
        
		response.setContentType("image/png");
         try {
        	 // Erstellen des Response mit den Bilddaten
			response.getOutputStream().write(bbb,0,bbb.length);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Statische Methode um ein Byte-Array dieser Klasse zu setzen
	 * @param bbb
	 */
	public static void setBbb(byte[] bbb) {
		ImageDownloadImpl.bbb = bbb;
	}
	
	
	
}
