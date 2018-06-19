package fr.pmk_updater.launcher;

import java.io.File;

import fr.pmk_updater.MainUpdater;
import fr.pmk_updater.utils.Utils;
import fr.pmk_updater.utils.VersionData;
import fr.pmk_updater.utils.XmlChecker;

public class LauncherUtils {

	public static boolean download(VersionData version) {
		// TODO Auto-generated method stub
		return Utils.downloadFile(version.getUlr(), XmlChecker.getCurrentFolder() + File.separator + MainUpdater.JAR_NAME);
	}

	public static boolean checkFile(VersionData version, File file) {
		// TODO Auto-generated method stub
		
		
		
		return false;
	}

}
