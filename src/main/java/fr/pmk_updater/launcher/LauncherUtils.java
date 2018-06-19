package fr.pmk_updater.launcher;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.commons.codec.digest.DigestUtils;

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
	
	public static String getChecksum(File f) {
		
		String md5 = null;
		
		FileInputStream fis;
		try {
			fis = new FileInputStream(f);
			md5 = DigestUtils.md5Hex(fis);
			fis.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			Utils.AddException(e);
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			Utils.AddException(e);
			e.printStackTrace();
		}
		
		return md5;
		
	}

}
