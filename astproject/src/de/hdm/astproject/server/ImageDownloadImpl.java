package de.hdm.astproject.server;

import java.io.IOException;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
public class ImageDownloadImpl extends HttpServlet{
	
	public static byte[] bbb = null;
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) {
        
		response.setContentType("image/png");
         try {
			response.getOutputStream().write(bbb,0,bbb.length);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void setBbb(byte[] bbb) {
		ImageDownloadImpl.bbb = bbb;
	}
	
	
	
}
