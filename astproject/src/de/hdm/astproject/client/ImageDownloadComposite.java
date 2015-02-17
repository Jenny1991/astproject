package de.hdm.astproject.client;

import java.util.Date;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.FormPanel.SubmitCompleteEvent;
import com.google.gwt.user.client.ui.VerticalPanel;
/**
 * Diese Klasse wird um die abstrakte Klasse (@link Composite) erweitert.
 * Sie ist f�r den Download einer Datei zust�ndig. Die Klasse Composite
 * b�ndelt mehrere Widgets, welche nach au�en als Einheit (einzlenes Widget)
 * auftreten. 
 *
 *@author Jennifer, Verena
 */

public class ImageDownloadComposite extends Composite{

	/** 
	 * Hier werden die GWT Widgets instantiiert
	 */
	final FormPanel form = new FormPanel();
	VerticalPanel vPanel = new VerticalPanel();
	
	/**
	 * Referenz auf ein Image-Objekt f�r den Methodenzugriff
	 */
	Image img = null;
	
	/**
	 * No Argument-Konstruktor
	 */
	public ImageDownloadComposite(){		
		
		// Verbindet die FormPanel-Instanz mit diesem (this) Composite-Objekt
		initWidget(form);
		
		/*
		 * Hinzuf�gen eines SubmitCompleteHandlers um darauf ze Reagieren, 
		 * wenn die Antwort (Response) vom Server empfangen wurde. 
		 */
		form.addSubmitCompleteHandler(new FormPanel.SubmitCompleteHandler() {

			/*
			 * Wenn die Antwort (Bilddatei) vom Server empfangen wurde,
			 * dann wird ein evtl. schon angezeigtes Bild zuerst entfernt
			 * und ein neues Bild bzw. eine neue Image-Instanz erzeugt.
			 * Dem Konstruktor der neuen Image-Instanz wird als Parameter 
			 * der relative Pfad des Servlets als Argument �bergeben und 
			 * steht synonym f�r die Adresse/Pfad des Bildes. Diesem Argument
			 * wird als Postfix die aktuelle Zeit in Millisekunden angeh�ngt,
			 * weil der Browser ansonsten das erste geladene Bild cached und
			 * kein neues Bild mehr anzeigt, "da ja der Pfad des Bildes ansonsten
			 * immer gleich bleibt und der Browser immer "denkt" dass es sich
			 * hier um das bereits geladene Bild handelt.
			 */
			@Override
			public void onSubmitComplete(SubmitCompleteEvent event) {
				if (img != null) {
					vPanel.remove(img);
					img = null;
				}
				Date date = new Date();
				img = new Image("/astproject/download?"+ date.getTime());
				img.setPixelSize(200, 200);
				vPanel.add(img);
								
			}
	    	
	    });
	}
	
	/**
	 * Parametrisierung des FormPanel, sowie abschicken des Requests
	 */
	public void loadImg() {		
		form.setMethod(FormPanel.METHOD_GET);
		form.setAction("/astproject/download"); 
	    form.setWidget(vPanel); 
		form.submit();
	   
	}
}