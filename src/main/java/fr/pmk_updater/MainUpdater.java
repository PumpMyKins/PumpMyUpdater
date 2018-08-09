package fr.pmk_updater;

import java.io.File;
import java.io.IOException;
import javax.swing.JOptionPane;

import fr.pmk_updater.exception.ExceptionManager;
import fr.pmk_updater.launcher.LauncherUtils;
import fr.pmk_updater.utils.Utils;
import fr.pmk_updater.utils.VersionData;
import fr.pmk_updater.utils.XmlChecker;

//Modification 1

public class MainUpdater {

	public static boolean DEV_MODE;
	public static String JAR_NAME = "launcher.jar";
	
	public static void main(String[] args) {
		
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
		
		ExceptionManager.setTitle(" Error box ");
		
		XmlChecker xmlChecker = new XmlChecker();
		
		if(!xmlChecker.isInit) {
			
			Utils.pushException();
			return;
			
		}
		
		VersionData version = xmlChecker.getLastVersionData(DEV_MODE);
		
		System.out.println("Versions à téléchager : \n" + version);
		
		File file = new File("launcher.jar");
		
		if(!file.exists()) {
			// launcher non existant
			
			int retour = JOptionPane.showConfirmDialog(null, "Aucun launcher trouvé !\nVoulez vous lancer le téléchargement du launcher ? \nCette opération est très rapide." , "Attente de confirmation" , JOptionPane.OK_CANCEL_OPTION );
			
			if(retour == JOptionPane.OK_OPTION) {
				
				boolean isOk = LauncherUtils.download(version);
				
				if(!isOk) {
					
					Utils.pushException();
					return;
					
				}
				
				// check de la file
				System.out.println("Checksum du fichier téléchargé : " + LauncherUtils.getChecksum(file));
				
				isOk = LauncherUtils.checkFile(version,file);
				
				if(!isOk) {
					
					Utils.pushException();
					return;
					
				}
				
				// lancement du .jar
				try {
					
					Process proc = Runtime.getRuntime().exec("java -jar launcher.jar");
					
				} catch (IOException e) {
					
					Utils.addException(e);
					Utils.pushException();
					e.printStackTrace();
					
				}
				
			}else {
				return;
			}
			
		}else {
			
			//launcher trouvé
			
			boolean isOk = LauncherUtils.checkFile(version,file);
			
			if(!isOk) {
				
				int retour = JOptionPane.showConfirmDialog(null, "Le launcher trouvé est non à jour ou corrompu !\nVoulez vous lancer le téléchargement d'un nouveau launcher launcher ? \nCette opération est très rapide." , "Attente de confirmation" , JOptionPane.OK_CANCEL_OPTION );
				
				if(retour == JOptionPane.OK_OPTION) {
					
					boolean isOk1 = LauncherUtils.download(version);
					
					if(!isOk1) {
						
						Utils.pushException();
						return;
						
					}
					
					// check de la file
					System.out.println("Checksum du fichier téléchargé : " + LauncherUtils.getChecksum(file));
					
					isOk1 = LauncherUtils.checkFile(version,file);
					
					if(!isOk1) {
						
						Utils.pushException();
						return;
						
					}
					
					// lancement du .jar
					try {
						
						Process proc = Runtime.getRuntime().exec("java -jar launcher.jar");
						
					} catch (IOException e) {
						
						Utils.addException(e);
						Utils.pushException();
						e.printStackTrace();
						
					}
				}else {
					
					JOptionPane.showMessageDialog(null, "Attention ! Ignoré cette avertissement pourrait entrainer des bugs", "Attention", JOptionPane.INFORMATION_MESSAGE);
					// lancement du .jar
					try {
						
						Process proc = Runtime.getRuntime().exec("java -jar launcher.jar");
						
					} catch (IOException e) {
						
						Utils.addException(e);
						Utils.pushException();
						e.printStackTrace();
						
					}
					
				}
				
			}else {
				
				// lancement du .jar
				try {
					
					Process proc = Runtime.getRuntime().exec("java -jar launcher.jar");
					
				} catch (IOException e) {
					
					Utils.addException(e);
					Utils.pushException();
					e.printStackTrace();
					
				}
				
			}
			
		}
		
	}
	
}
