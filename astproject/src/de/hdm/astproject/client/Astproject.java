package de.hdm.astproject.client;

import de.hdm.astproject.shared.FieldVerifier;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Astproject implements EntryPoint {
	
	ImageDownloadComposite idc = null;
	ImageUploadComposite iuc = null;
	
	public void onModuleLoad() {
		iuc = new ImageUploadComposite();
		idc = new ImageDownloadComposite();
		iuc.setIdc(idc);
		
		Button button = new Button("hochladen");
		button.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				iuc.submitForm();
				
			}
			
		});
		
		RootPanel.get().add(iuc);
		RootPanel.get().add(button);
		RootPanel.get().add(idc);
	}
}
