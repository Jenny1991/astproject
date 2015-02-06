package de.hdm.astproject.server;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Vector;

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
 * The server-side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class ImageUploadImpl extends HttpServlet {
	
	byte[] buffer;
	int imgID;
	ImageDownloadImpl idi = new ImageDownloadImpl();
	byte[] responseBytes;
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ServletFileUpload upload = new ServletFileUpload();
		try {
			FileItemIterator iter = upload.getItemIterator(request);

				while (iter.hasNext()) {
					FileItemStream item = iter.next();
					InputStream stream = item.openStream();	
					
					if (item.isFormField()) {
						byte[] str = new byte[stream.available()];
			            stream.read(str);
			            String pFieldValue = new String(str,"UTF8");
			            imgID = new Integer(pFieldValue);
					}
					else {
						byte[] bb = IOUtils.toByteArray(stream);						
						buffer = bb;
						idi.setBbb(buffer);
						
					}
					
				}
				getGCEString();
				System.out.println(responseBytes.length);
				
				ImageDownloadImpl.setBbb(responseBytes);
				
//				response.setContentType("image/jpg");
//				response.getOutputStream().write(responseBytes,0,responseBytes.length);
				
			} catch (Exception e) {
				throw new RuntimeException(e);
		}
		// Set response content type
		 //  response.setContentType("text/html");
		
		   // Actual logic goes here.
		 //  PrintWriter out = response.getWriter();
		 //  out.println(getGCEString());
		
		

	}
	
	public void getGCEString() {

	    String surl = "http://104.155.31.96:8080/servlet/test";

	    String response = "";
	    try {	      
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
	         System.out.println("http success!");
	      }else{
	         System.out.println("http failed:"+connection.getResponseCode());
	      }

	      int respCode = connection.getResponseCode();
	      int httpconCode = HttpURLConnection.HTTP_OK;
	      
	      if (respCode == httpconCode) {
//	        String inputLine;
//	        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
//	        while ((inputLine = reader.readLine()) != null) {
//	          response += inputLine;
//	        }
//	        reader.close();
	        
	        responseBytes = IOUtils.toByteArray(connection.getInputStream());
	      } 
	    } catch (MalformedURLException e) {
	      e.printStackTrace();
	    } catch (IOException e) {
	      e.printStackTrace();
	    } 
//	    return response;
	}
	

	
}
