package de.hdm.astproject.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * Diese Klasse implementiert das Interface <code>EntryPoint</code> 
 * um beinhaltet die Methode <code>public void onModuleLoad()</code>.
 *
 *@author Jennifer, Verena
 */

public class Astproject implements EntryPoint {
		
	/**
	 * Referenz auf ein ImageDownloadComposite-Objekt und auf ein 
	 * ImageUploadComposite-Objekt für den Methodenzugriff
	 */
	ImageDownloadComposite idc = null;
	ImageUploadComposite iuc = null;
	
	/**
	 * Wir benötigen die Methode <code>public void onModuleLoad()</code>, die
	 * durch das EntryPoint Interface vorgegeben ist. Diese ist das GWT-Pendant
	 * der <code>main()</code>-Methode normaler Java-Applikationen.
	 */
	public void onModuleLoad() {
		iuc = new ImageUploadComposite();
		idc = new ImageDownloadComposite();
		iuc.setIdc(idc);
		
		/**
		 * Erzeugen eines Buttons und hinzufügen eines ClickHandlers
		 * um den Umwandel-Prozess mit dem Upload der PDF-Datei zu starten
		 */
		Button button = new Button("hochladen");
		button.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				iuc.submitForm();
				
			}
			
		});
		
		/**
		 * Wir weisen dem Root Panel durch die Methode 
		 * <code>add()</code> die Instanzen der Klassen {@link ImageDownloadComposite}, {@link ImageUploadComposite}
		 * und {@link Button} zu.
		 */
		RootPanel.get().add(iuc);
		RootPanel.get().add(button);
		RootPanel.get().add(idc);
	}
}
