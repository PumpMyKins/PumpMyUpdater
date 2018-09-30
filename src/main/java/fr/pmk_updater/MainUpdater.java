package fr.pmk_updater;

import java.io.File;

import javax.swing.JOptionPane;

import fr.pmk_updater.exception.ExceptionManager;
import fr.pmk_updater.gui.UpdaterFrame;
import fr.pmk_updater.launcher.LauncherUtils;
import fr.pmk_updater.utils.Utils;
import fr.pmk_updater.utils.VersionData;
import fr.pmk_updater.utils.XmlChecker;

public class MainUpdater {

	public static boolean DEV_MODE;
	public static String JAR_NAME = "launcher.jar";
	
	private static UpdaterThread updaterThread;
	private static UpdaterFrame updateFrame;
	
	public static UpdaterFrame getFrame() {
		return updateFrame;
	}
	
	private static void setFrame(UpdaterFrame f) {
		updateFrame = f;
	}
	
	public static void main(String[] args) throws InterruptedException {
		
		ExceptionManager.setTitle(" Error box ");
		
		try {
			MainUpdater.setFrame(new UpdaterFrame());
		} catch (Exception e) {
				// TODO Auto-generated catch block
				Utils.addException(e);
				Utils.pushException();
				closeFrame();
				return;
		}
					
		while(updateFrame == null) {
			
		}
		
		updaterThread = new UpdaterThread(updateFrame);
		
		if(args.length != 1) {
			DEV_MODE = false;
		}else {
			if(args[0].equals("-dev") ) {
				DEV_MODE = true;
			}else {
				DEV_MODE = false;
			}
		}
		
		System.out.println("Etat du mode developpeur : " + DEV_MODE);		
		
		getFrame().setUpdaterState("Getting XLM information ...");
		
		XmlChecker xmlChecker = new XmlChecker();
		
		if(!xmlChecker.isInit) {
			
			Utils.pushException();
			closeFrame();
			return;
			
		}
		
		Thread.sleep(250);
		
		getFrame().setUpdaterState("Getting lastest version ...");
		
		VersionData version = xmlChecker.getLastVersionData(MainUpdater.DEV_MODE);
		
		System.out.println("Versions à téléchager : \n" + version);
		
		Thread.sleep(250);
		
		getFrame().setUpdaterState("Checkin your jar file ...");
		
		File file = new File("launcher.jar");
		
		if(!file.exists()) {
			// launcher non existant
			
			Thread.sleep(250);
			
			getFrame().setUpdaterState("Getting lastest launcher...");
			
			boolean isOk = LauncherUtils.download(version);
				
			if(!isOk) {
					
				Utils.pushException();
				closeFrame();
				return;
					
			}
			
			Thread.sleep(250);
			
			getFrame().setUpdaterState("Calcultating checksum...");
			
			// check de la file
			System.out.println("Checksum du fichier téléchargé : " + LauncherUtils.getChecksum(file));
				
			isOk = LauncherUtils.checkFile(version,file);
				
			if(!isOk) {
					
				Utils.pushException();
				closeFrame();
				return;
					
			}
			
			Thread.sleep(250);
			
			getFrame().setUpdaterState("Terminé !");
			
			Thread.sleep(250);
			
			// lancement du .jar			
			new StartingThread().start();
			closeFrame();
			
		}else {
			
			//launcher trouvé
			
			Thread.sleep(500);
			
			getFrame().setUpdaterState("Calcultating checksum...");
			
			boolean isOk = LauncherUtils.checkFile(version,file);
			
			if(!isOk) {
				
				int retour = JOptionPane.showConfirmDialog(null, "Le launcher trouvé est non à jour ou corrompu !\nVoulez vous lancer le téléchargement d'un nouveau launcher launcher ? \nCette opération est très rapide." , "Attente de confirmation" , JOptionPane.OK_CANCEL_OPTION );
				
				if(retour == JOptionPane.OK_OPTION) {
					
					getFrame().setUpdaterState("Getting lastest launcher...");
					
					boolean isOk1 = LauncherUtils.download(version);
					
					if(!isOk1) {
						
						Utils.pushException();
						return;
						
					}
					
					Thread.sleep(250);
					
					getFrame().setUpdaterState("Calcultating checksum...");
					
					// check de la file
					System.out.println("Checksum du fichier téléchargé : " + LauncherUtils.getChecksum(file));
					
					isOk1 = LauncherUtils.checkFile(version,file);
					
					if(!isOk1) {
						
						Utils.pushException();
						closeFrame();
						return;
						
					}
					
					Thread.sleep(250);
					
					getFrame().setUpdaterState("Terminé !");
					
					Thread.sleep(500);
					
					// lancement du .jar
					new StartingThread().start();
					closeFrame();
					
					
				}else {
					
					JOptionPane.showMessageDialog(null, "Attention ! Ignoré cette avertissement pourrait entrainer des bugs", "Attention", JOptionPane.INFORMATION_MESSAGE);
					
					Thread.sleep(250);
					
					getFrame().setUpdaterState("Terminé !");
					
					Thread.sleep(500);
					
					// lancement du .jar
					new StartingThread().start();
					closeFrame();
					
				}
				
			}else {
				
				Thread.sleep(250);
				
				getFrame().setUpdaterState("Terminé !");
				
				Thread.sleep(500);
				
				// lancement du .jar
				new StartingThread().start();
				closeFrame();
				
			}
			
		}
			
	}

	public static void closeThread() {
		// TODO Auto-generated method stub
		//close thread and update procedure		
		updaterThread.interrupt();	
	}
	
	public static void closeFrame() {
		
		updateFrame.close();
		
	}
	
}
