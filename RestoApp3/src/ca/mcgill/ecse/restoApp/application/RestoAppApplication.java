package ca.mcgill.ecse.restoApp.application;

import ca.mcgill.ecse.restoApp.persistence.PersistenceObjectStream;
import ca.mcgill.ecse.restoApp.view.RestoAppPage;
import ca.mcgill.ecse223.resto.model.RestoApp;

public class RestoAppApplication {
	
	private static RestoApp restoApp;
	private static String filename = "menu.resto";
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// start UI
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
            	try {
					RestoAppPage window = new RestoAppPage();
					window.frame.setVisible(true);
					new RestoAppPage().setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
            }
        });     
	}

	public static RestoApp getRestoApp() {
		if (restoApp == null) {
			// load model
			restoApp = load();
		}
 		return restoApp;
	}
	
	public static void save() {
		PersistenceObjectStream.serialize(restoApp);
	}
	
	public static RestoApp load() {
		PersistenceObjectStream.setFilename(filename);
		restoApp = (RestoApp) PersistenceObjectStream.deserialize();
		// model cannot be loaded 
		if (restoApp == null) {
			restoApp = new RestoApp();
		}
		else {
			restoApp.reinitialize();
		}
		return restoApp;
	}
	
	public static void setFilename(String newFilename) {
		filename = newFilename;
	}
}
