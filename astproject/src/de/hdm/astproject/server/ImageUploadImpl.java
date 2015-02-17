package de.hdm.astproject.server;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.IOUtils;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ByteArrayBody;
import org.apache.http.entity.mime.content.ContentBody;

import com.google.appengine.api.urlfetch.FetchOptions;
import com.google.appengine.api.urlfetch.HTTPMethod;
import com.google.appengine.api.urlfetch.HTTPRequest;
import com.google.appengine.api.urlfetch.HTTPResponse;
import com.google.appengine.api.urlfetch.URLFetchService;
import com.google.appengine.api.urlfetch.URLFetchServiceFactory;

/**
 * Konkretisierung eines HttpServlets, welches die hochgeladene PDF-Datei
 * entgegen nimmt, diese an das Servlet auf der GoogleComputeEngine weiterleitet
 * und als Antwort das daraus generierte Bild in Empfang nimmt. Das Empfangene
 * Bild anschließend als statische Variable der Klasse ImageDownloadImpl gesetzt.
 */
@SuppressWarnings("serial")
public class ImageUploadImpl extends HttpServlet {
	
	/**
	 *  Variable welche Dateien in Form von einem Byte-Array referenzieren kann,
	 *  in diesem konkreten Fall das hochgeladene PDF-File
	 */
	byte[] buffer;
	
	/**
	 *  Variable welche Dateien in Form von einem Byte-Array referenzieren kann,
	 *  in diesem konkreten Fall das von der GCE empfangene Bild
	 */
	byte[] responseBytes;
	
	/**
	 * Methode die beim empfangen eines Requests vom Client aufgerufen wird
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ServletFileUpload upload = new ServletFileUpload();
		try {
			
			/*
			 *  Referenz auf einen ItemIterator um über die einzelnen
			 *  hochgelanden Elemente (meherer Dateien, Textinfors etc.)
			 *  mittels Schleife referenzieren zu können.
			 */			
			FileItemIterator iter = upload.getItemIterator(request);

			while (iter.hasNext()) {
				FileItemStream item = iter.next();
				InputStream stream = item.openStream();	
				
				// Falls das Element im Request ein Formfeld als Ursprung hat...
				if (item.isFormField()) {
				}
				// Für alle anderen Fälle bzw. wenn es sich bei dem Element um eine Datei handelt...
				else {
					// Einlesen der Datei in ein Byte-Array
					byte[] bb = IOUtils.toByteArray(stream);						
					buffer = bb;
					
				}
				
			}
			// Starten der Übertragung/ Weierleitung der erhaltenen Datei zur GoogleComputeEngine
			getGCEString();
			
			// Seten der erhaltenen Bildinformationen als statische Variable in das Servlet "ImageDownloadImpl"
			ImageDownloadImpl.setBbb(responseBytes);
				
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		
		

	}
	
	/**
	 * Erstellung eines Requests an die ComuputeEngine um das erhaltene PDF-File
	 * zu übermitteln  und "dort" die Typumwandlung anzustoßen. Das Empfangene Bild
	 * wird ebenfalls temporär als Byte-Array vorgehalten.
	 */
	public void getGCEString() {

		// Adresse des GCE-Servlets
	    String surl = "http://104.155.31.96:8080/servlet/test";

	    
	    try {
	    // Aufbau und Parametrisierung einer Verbindung zum GCE-Servlet
	      MultipartEntity mpEntity  = new MultipartEntity();
	      ContentBody cbFile = new ByteArrayBody(buffer, "application/pdf", "w.jpg");
	      mpEntity.addPart("source", cbFile);

	      URL url = new URL(surl);                   
	      HttpURLConnection connection = (HttpURLConnection) url.openConnection();
	      connection.setDoOutput(true);
	      connection.setConnectTimeout(30000);
	      connection.setReadTimeout(30000);
	      connection.setRequestMethod("POST");
	      connection.addRequestProperty("Content-length", mpEntity.getContentLength()+"");
	      connection.addRequestProperty(mpEntity.getContentType().getName(), mpEntity.getContentType().getValue());

	      mpEntity.writeTo(connection.getOutputStream());

	      if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
	         System.out.println("erfolgreich");
	      }else{
	         System.out.println("fehler:"+connection.getResponseCode());
	      }

	      int respCode = connection.getResponseCode();
	      int httpconCode = HttpURLConnection.HTTP_OK;
	      
	      if (respCode == httpconCode) {
	        // Ablegen des enpfangenen Bildes in ein Byte-Array
	        responseBytes = IOUtils.toByteArray(connection.getInputStream());
	      } 
	    } catch (MalformedURLException e) {
	      e.printStackTrace();
	    } catch (IOException e) {
	      e.printStackTrace();
	    } 
	}
	

	
}
