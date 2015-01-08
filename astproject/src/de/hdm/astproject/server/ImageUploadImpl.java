package de.hdm.astproject.server;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
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
				
				
			} catch (Exception e) {
				throw new RuntimeException(e);
		}

	}
	
	public String fetch(String postdata) throws IOException {

	    String surl = "http://130.211.101.46";

	    URLFetchService urlFetchService = URLFetchServiceFactory.getURLFetchService();

	    URL url = new URL(surl);

	    FetchOptions fetchOptions = FetchOptions.Builder.withDefaults();
	    fetchOptions.doNotValidateCertificate();
	    fetchOptions.setDeadline(60D);

	    HTTPRequest request = new HTTPRequest(url, HTTPMethod.POST, fetchOptions);
	    request.setPayload(postdata.getBytes());
	    
	    HTTPResponse httpResponse = urlFetchService.fetch(request);
	    String response = "";
	    if (httpResponse.getResponseCode() == HttpURLConnection.HTTP_OK) {
	      response = new String(httpResponse.getContent());
	    } 

	    return response;
	  }
	
}
