package fr.pmk_updater;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;

import fr.pmk_updater.exception.ExceptionManager;
import fr.pmk_updater.launcher.LauncherUtils;
import fr.pmk_updater.utils.Utils;
import fr.pmk_updater.utils.VersionData;
import fr.pmk_updater.utils.XmlChecker;

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
		
		System.out.println("Versions � t�l�chager : " + version);
		
		File file = new File("launcher.jar");
		
		if(!file.exists()) {
			// launcher non existant
			boolean isOk = LauncherUtils.download(version);
			
			if(!isOk) {
				
				Utils.pushException();
				return;
				
			}
			
			// check de la file
			System.out.println("Checksum : " + LauncherUtils.getChecksum(file));
			
			isOk = LauncherUtils.checkFile(version,file);
			
			if(!isOk) {
				
				Utils.pushException();
				return;
				
			}
			
			// lancement du .jar
			try {
				Desktop.getDesktop().open(file);
			} catch (IOException e) {
				
				Utils.AddException(e);
				Utils.pushException();
				e.printStackTrace();
				return;
			}
			
		}else {
			
			
			
		}
		
	}

	private static boolean checkLauncherIsValid(File l) {
		return DEV_MODE;
		
	}
	
}
