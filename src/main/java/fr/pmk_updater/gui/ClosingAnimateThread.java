package fr.pmk_updater.gui;

public class ClosingAnimateThread extends Thread{

	private UpdaterFrame frame;
	
	public ClosingAnimateThread(UpdaterFrame f) {
		
		this.frame = f;
		
	}
	
	public void run() {
		
		try {
			sleep(1500);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		for (int i = 3; i >= 1; i--) {
			
			frame.setUpdaterState("Fermeture dans " + i + " secondes");
			try {
				sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		frame.dispose();
		
	}
	
}
