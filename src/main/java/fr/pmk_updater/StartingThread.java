package fr.pmk_updater;

import fr.pmk_updater.utils.Utils;

public class StartingThread extends Thread {
	
	public void run() {
		
		try {
			sleep(4000);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		try {
			
			Process proc = Runtime.getRuntime().exec("java -jar launcher.jar");
			
		} catch (Exception e) {
			
			Utils.addException(e);
			Utils.pushException();
			
		}
		
		interrupt();
		
	}

}
