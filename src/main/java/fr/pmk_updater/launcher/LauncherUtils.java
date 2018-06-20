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

	public static boolean checkFile(VersionData v, File f) {
		// TODO Auto-generated method stub
		
		String md5 = getChecksum(f);
		
		if(md5 == null) {
			return false;
		}
		
		if(md5.equals(v.getChecksum())) {
			
			return true;
			
		}else {
			
			Utils.addException("Mauvais checksum trouvé ( " + md5 + " )\nXml checksum : " + v.getChecksum());
			return false;
			
		}
		
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
			Utils.addException(e);
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			Utils.addException(e);
			e.printStackTrace();
		}
		
		return md5;
		
	}

}
