package de.hdm.astproject.client;


import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FileUpload;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.FormPanel.SubmitCompleteEvent;
import com.google.gwt.user.client.ui.FormPanel.SubmitEvent;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;


public class ImageUploadComposite extends Composite{
		
	final FormPanel form = new FormPanel();
	VerticalPanel vPanel = new VerticalPanel(); 
	FileUpload fileUpload = new FileUpload();
	Label imgFormats = new Label("Nur PDF zul‰ssig");
		
	/**
	 * Referenz auf ein ImageDownloadComposite-Objekt f√ºr den Methodenzugriff
	 */
	ImageDownloadComposite idc = null;
	
	 
	public ImageUploadComposite(){
				
		form.setMethod(FormPanel.METHOD_POST);
		form.setEncoding(FormPanel.ENCODING_MULTIPART); //  multipart MIME encoding
		form.setAction("/astproject/upload"); // The servlet FileUploadGreeting
	    form.setWidget(vPanel);
	    fileUpload.setName("uploader"); // Very important    
	    vPanel.add(fileUpload); 
	    vPanel.add(imgFormats);
	    
		
	    initWidget(form);
	    
	    form.addSubmitCompleteHandler(new FormPanel.SubmitCompleteHandler() {

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
	        		Window.alert("Die Bild-Datei konnte nicht ¸bernommen werden, bitte ¸berpr¸fen Sie den Dateipfad");
	        		return;
	        	}
	        	String fileName = fileNameBuffer.substring(fileNameBuffer.lastIndexOf("."));
	        	//if (!fileName.equals(".jpg") && !fileName.equals(".png")) {
	        	//	event.cancel();
	        	//	Window.alert("Nur Bildformate nach .jpg/.png zul√§ssig!\nIhre Frage wurde ohne Bild gespeichert");
	        		
	        	//}
	        }
	      });
	
	}
	
	
		
	public void setIdc(ImageDownloadComposite idc) {
		this.idc = idc;
	}



	public void submitForm() {
		form.submit();
		
	}
}