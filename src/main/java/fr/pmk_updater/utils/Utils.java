package fr.pmk_updater.utils;

import fr.pmk_updater.exception.ExceptionFile;
import fr.pmk_updater.exception.ExceptionManager;

public class Utils {

	public static String getStringByStackTrace(StackTraceElement[] s) {
		
		String trace = "";
		
		for (StackTraceElement stackTraceElement : s) {
			trace += stackTraceElement.toString() + "\n";
		}
		
		return trace;
		
	}
	
	public static void AddException(Exception e) {
		
		ExceptionManager.addError("[ " + e.getClass().toString() + " ] " + e.getMessage());
		ExceptionFile.addError("[ " + e.getClass().toString() + " ] " + e.getMessage() + "\n" +Utils.getStringByStackTrace(e.getStackTrace()));
		
	}
	
	public static void pushException() {
		
		ExceptionManager.addError("\n Allez voir le fichier de logs pour plus d'informations");
		ExceptionManager.show();
		ExceptionFile.pushFile();
		
	}
	
}
