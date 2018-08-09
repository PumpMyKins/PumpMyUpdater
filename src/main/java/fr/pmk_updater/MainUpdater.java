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
		
		System.out.println("Versions � t�l�chager : \n" + version);
		
		File file = new File("launcher.jar");
		
		if(!file.exists()) {
			// launcher non existant
			
			int retour = JOptionPane.showConfirmDialog(null, "Aucun launcher trouv� !\nVoulez vous lancer le t�l�chargement du launcher ? \nCette op�ration est tr�s rapide." , "Attente de confirmation" , JOptionPane.OK_CANCEL_OPTION );
			
			if(retour == JOptionPane.OK_OPTION) {
				
				boolean isOk = LauncherUtils.download(version);
				
				if(!isOk) {
					
					Utils.pushException();
					return;
					
				}
				
				// check de la file
				System.out.println("Checksum du fichier t�l�charg� : " + LauncherUtils.getChecksum(file));
				
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
			
			//launcher trouv�
			
			boolean isOk = LauncherUtils.checkFile(version,file);
			
			if(!isOk) {
				
				int retour = JOptionPane.showConfirmDialog(null, "Le launcher trouv� est non � jour ou corrompu !\nVoulez vous lancer le t�l�chargement d'un nouveau launcher launcher ? \nCette op�ration est tr�s rapide." , "Attente de confirmation" , JOptionPane.OK_CANCEL_OPTION );
				
				if(retour == JOptionPane.OK_OPTION) {
					
					boolean isOk1 = LauncherUtils.download(version);
					
					if(!isOk1) {
						
						Utils.pushException();
						return;
						
					}
					
					// check de la file
					System.out.println("Checksum du fichier t�l�charg� : " + LauncherUtils.getChecksum(file));
					
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
					
					JOptionPane.showMessageDialog(null, "Attention ! Ignor� cette avertissement pourrait entrainer des bugs", "Attention", JOptionPane.INFORMATION_MESSAGE);
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
