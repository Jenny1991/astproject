package de.hdm.astproject.client;



import java.util.Date;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.TimeZone;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.FormPanel.SubmitCompleteEvent;

public class ImageDownloadComposite extends Composite{

	final FormPanel form = new FormPanel();
	VerticalPanel vPanel = new VerticalPanel();
	Image img = null;
	 
	public ImageDownloadComposite(){
		
		initWidget(form);
		
		
		form.addSubmitCompleteHandler(new FormPanel.SubmitCompleteHandler() {

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
	
	public void loadImg() {
		
		form.setMethod(FormPanel.METHOD_GET);
		form.setAction("/astproject/download"); 
	    form.setWidget(vPanel); 
		form.submit();
	   
	}
}