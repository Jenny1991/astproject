package de.hdm.astproject.client;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FileUpload;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.FormPanel.SubmitCompleteEvent;
import com.google.gwt.user.client.ui.FormPanel.SubmitEvent;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
/**
 * Diese Klasse wird um die abstrakte Klasse (@link Composite) erweitert.
 * Sie ist für den Upload einer Datei zuständig. Die Klasse Composite
 * bündelt mehrere Widgets, welche nach außen als Einheit (einzlenes Widget)
 * auftreten. 
 *
 *@author Jennifer, Verena
 */

public class ImageUploadComposite extends Composite{
	
	/** 
	 * Hier werden die GWT Widgets instantiiert
	 */
	final FormPanel form = new FormPanel();
	VerticalPanel vPanel = new VerticalPanel(); 
	FileUpload fileUpload = new FileUpload();
	Label imgFormats = new Label("Nur PDF zulässig");
		
	/**
	 * Referenz auf ein ImageDownloadComposite-Objekt für den Methodenzugriff
	 */
	ImageDownloadComposite idc = null;
	
	/**
	 * No Argument-Konstruktor
	 */ 
	public ImageUploadComposite(){
		
		// Parametriesierung des FormPanels
		form.setMethod(FormPanel.METHOD_POST);
		// Multipart-Encoding muss für eine Dateiübertragung "eingestellt" werden
		form.setEncoding(FormPanel.ENCODING_MULTIPART);
		form.setAction("/astproject/upload"); 
	    form.setWidget(vPanel);
	    
	    // Registrierung von Elementen für die Datenübertragung am FormPanel
	    fileUpload.setName("uploader");  
	    vPanel.add(fileUpload); 
	    vPanel.add(imgFormats);   
		
	    // Verbindet die FormPanel-Instanz mit diesem (this) Composite-Objekt
	 	initWidget(form);
	 		
 		/*
 		 * Hinzufügen eines SubmitCompleteHandlers um darauf ze Reagieren, 
 		 * wenn die Antwort (Response) vom Server empfangen wurde. 
 		 */
	    form.addSubmitCompleteHandler(new FormPanel.SubmitCompleteHandler() {

	    	/*
	    	 * Mit Eintreffen der Antwort vom Server wird "onSubmitComplete" aufegrufen
	    	 * und der Umwandel-Prozess ist damit beendet
	    	 * Der Server hält nun das neu erzeugte jpg-Bild vor. Dieses wird anschließend
	    	 * mittels einer Instanz von ImageDownloadComposite abgerufen und angezeigt
	    	 * in dem "dort" ein neuer Request erzeugt wird. Angestoßen wird dies durch
	    	 * den Befehl "loadImg()". 
	    	 * 
	    	 * -> Grund für einen zweiten Request ist der, dass der Browser das Bild nicht
	    	 * anzeigt, würde man alles innerhalb eines Requests abhandeln. Warum dies so ist,
	    	 * kann nicht genau begründet werden. Mittels Debugging und Analyse des Traffics
	    	 * zwischen Browser und Server, lässt darauf schließen, dass der Browser einen
	    	 * zweiten Request (Ursache ist nicht bekannt) ohne Nutzdaten an das Servlet sendet
	    	 * und das Servlet dann aber zum Zeitpunkt des zweiten Requests die Bilddaten aber
	    	 * nicht mehr vorhält. Antwort dieses zweiten Requests ist dann eben "kein Bild"
	    	 * und damit verschwindet das zuvor geladene Bild wieder. Hin und wieder sah man
	    	 * es für einen Sekundenbruchteil und verschwand dann wieder. Gelöst wurde dieses 
	    	 * Problem in dem das Bilddaten statisch in der Klasse "ImageDownloadImpl" vorgehalten
	    	 * und dann mittels zweiten Requests abgerufen werden. Für die Analyse des Traffic 
	    	 * zwischen Brower und Server wurde das Plugin "Tamperdata" verwendet. Bei dem Phänomen
	    	 * des "leeren Requests" wird eine versuchte "Validierung" seitens des Browsers
	    	 * vermutet.
	    	 */
			@Override
			public void onSubmitComplete(SubmitCompleteEvent event) {
				
				idc.loadImg();
									
			}
	    	
	    });
	    
	    form.addSubmitHandler(new FormPanel.SubmitHandler() {
	        public void onSubmit(SubmitEvent event) {
	        	StringBuffer fileNameBuffer = new StringBuffer(fileUpload.getFilename());
	        	if (fileNameBuffer.length() < 1) {
	        		event.cancel();
	        		return;
	        	}
	        	if (fileNameBuffer.lastIndexOf(".") == -1) {
	        		event.cancel();
	        		Window.alert("Die Bild-Datei konnte nicht übernommen werden, bitte überprüfen Sie den Dateipfad");
	        		return;
	        	}
	        	String fileName = fileNameBuffer.substring(fileNameBuffer.lastIndexOf("."));
	        	if (!fileName.equals(".pdf")) {
	        		event.cancel();
	        		Window.alert("Nur PDF-Format zulässig");
	        		
	        	}
	        }
	      });
	
	}	
		
	/**
	 * Hier wird eine Referenz auf {@link ImageDownloadComposite}
	 * gesetzt um das erzeugte Bild mittels dieser Instanz vom
	 * Server abzurufen
	 * 
	 * @param idc Instanz der Klasse {@link ImageDownloadComposite} 
	 * 
	 */
	public void setIdc(ImageDownloadComposite idc) {
		this.idc = idc;
	}

	/**
	 * Übermittlung/Upload der PDF-Datei an der Server
	 */
	public void submitForm() {
		form.submit();
		
	}
}