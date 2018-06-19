package fr.pmk_updater;

import fr.pmk_updater.exception.ExceptionManager;
import fr.pmk_updater.utils.XmlChecker;

public class MainUpdater {

	public static boolean DEV_MODE;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		if(args.length != 1) {
			DEV_MODE = false;
		}else {
			if(args[0] == "-dev" ) {
				DEV_MODE = true;
			}else {
				DEV_MODE = false;
			}
		}
		
		ExceptionManager.setTitle(" Error box ");
		
		XmlChecker xmlChecker = new XmlChecker();
		
		
		
	}

}
