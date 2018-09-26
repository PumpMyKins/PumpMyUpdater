package fr.pmk_updater;

import java.io.File;

import javax.swing.JOptionPane;

import fr.pmk_updater.gui.UpdaterFrame;
import fr.pmk_updater.launcher.LauncherUtils;
import fr.pmk_updater.utils.Utils;
import fr.pmk_updater.utils.VersionData;
import fr.pmk_updater.utils.XmlChecker;

public class UpdaterThread extends Thread {

	private UpdaterFrame uf;
	
	public UpdaterThread(UpdaterFrame f) {
		// TODO Auto-generated constructor stub
		this.uf = f;
	}
	
	@Override
	public void interrupt() {
		// TODO Auto-generated method stub
		System.out.println("Updater Thread interrup !");
		MainUpdater.closeFrame();
		super.interrupt();
	}
	
	private void sleep500() {
		
		try {
			sleep(500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	private void sleep250() {
		
		try {
			sleep(250);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void run() {
		
		uf.setUpdaterState("Getting XLM information ...");
		
		XmlChecker xmlChecker = new XmlChecker();
		
		if(!xmlChecker.isInit) {
			
			Utils.pushException();
			this.interrupt();
			return;
			
		}
		
		sleep500();
		
		uf.setUpdaterState("Getting lastest version ...");
		
		VersionData version = xmlChecker.getLastVersionData(MainUpdater.DEV_MODE);
		
		System.out.println("Versions à téléchager : \n" + version);
		
		sleep250();
		
		uf.setUpdaterState("Checkin your jar file ...");
		
		File file = new File("launcher.jar");
		
		if(!file.exists()) {
			// launcher non existant
			
			sleep250();
			
			uf.setUpdaterState("Getting lastest launcher...");
			
			boolean isOk = LauncherUtils.download(version);
				
			if(!isOk) {
					
				Utils.pushException();
				this.interrupt();
				return;
					
			}
			
			sleep250();
			
			uf.setUpdaterState("Calcultating checksum...");
			
			// check de la file
			System.out.println("Checksum du fichier téléchargé : " + LauncherUtils.getChecksum(file));
				
			isOk = LauncherUtils.checkFile(version,file);
				
			if(!isOk) {
					
				Utils.pushException();
				this.interrupt();
				return;
					
			}
			
			sleep250();
			
			uf.setUpdaterState("Terminé !");
			
			sleep500();
			
			// lancement du .jar			
			new StartingThread().start();
			interrupt();
			
		}else {
			
			//launcher trouvé
			
			sleep250();
			
			uf.setUpdaterState("Calcultating checksum...");
			
			boolean isOk = LauncherUtils.checkFile(version,file);
			
			if(!isOk) {
				
				int retour = JOptionPane.showConfirmDialog(null, "Le launcher trouvé est non à jour ou corrompu !\nVoulez vous lancer le téléchargement d'un nouveau launcher launcher ? \nCette opération est très rapide." , "Attente de confirmation" , JOptionPane.OK_CANCEL_OPTION );
				
				if(retour == JOptionPane.OK_OPTION) {
					
					uf.setUpdaterState("Getting lastest launcher...");
					
					boolean isOk1 = LauncherUtils.download(version);
					
					if(!isOk1) {
						
						Utils.pushException();
						return;
						
					}
					
					sleep250();
					
					uf.setUpdaterState("Calcultating checksum...");
					
					// check de la file
					System.out.println("Checksum du fichier téléchargé : " + LauncherUtils.getChecksum(file));
					
					isOk1 = LauncherUtils.checkFile(version,file);
					
					if(!isOk1) {
						
						Utils.pushException();
						return;
						
					}
					
					sleep250();
					
					uf.setUpdaterState("Terminé !");
					
					sleep500();
					
					// lancement du .jar
					new StartingThread().start();
					interrupt();
					
					
				}else {
					
					JOptionPane.showMessageDialog(null, "Attention ! Ignoré cette avertissement pourrait entrainer des bugs", "Attention", JOptionPane.INFORMATION_MESSAGE);
					
					sleep250();
					
					uf.setUpdaterState("Terminé !");
					
					sleep500();
					
					// lancement du .jar
					new StartingThread().start();
					interrupt();
					
				}
				
			}else {
				
				sleep250();
				
				uf.setUpdaterState("Terminé !");
				
				sleep500();
				
				// lancement du .jar
				new StartingThread().start();
				interrupt();
				
			}
			
		}
		
	}
	
}
