package fr.pmk_updater.utils;

public class Utils {

	public static String getStringByStackTrace(StackTraceElement[] s) {
		
		String trace = "";
		
		for (StackTraceElement stackTraceElement : s) {
			trace += stackTraceElement.toString() + "\n";
		}
		
		return trace;
		
	}
	
}
