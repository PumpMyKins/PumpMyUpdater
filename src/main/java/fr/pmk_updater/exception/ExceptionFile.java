package fr.pmk_updater.exception;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.JOptionPane;

public class ExceptionFile {

	private static ArrayList<String> errorList = new ArrayList<String>();

	public static ArrayList<String> getErrorList() {
		return errorList;
	}

	public static void setErrorList(ArrayList<String> errorList) {
		ExceptionFile.errorList = errorList;
	}
	
	public static void addError(String e) {
		
		errorList.add(e);
		
	}
	
	private static void resetValue() {
		
		errorList = new ArrayList<String>();
		
	}
	
	public static void pushFile() {
		
		String fileName = "default-crash";		
		
		try {
			
			fileName = generateFileName();
			
		} catch (ParseException e) {
			
			e.printStackTrace();
			
		}
		
		try {
            // Assume default encoding.
            FileWriter fileWriter = new FileWriter(fileName);

            // Always wrap FileWriter in BufferedWriter.
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            // Note that write() does not automatically
            // append a newline character.
            bufferedWriter.write("============================  Crash report  ======================================\n\n");
            
            for (String string : errorList) {
				
            	bufferedWriter.write(string);
            	bufferedWriter.write("\n========================================================================\n");
            	
			}

            // Always close files.
            bufferedWriter.close();
        }
        catch(IOException ex) {
        	
        	JOptionPane.showMessageDialog(null, " Erreur d'ecriture du fichier de crash \n" + ex.getMessage() , "Exception" , JOptionPane.ERROR_MESSAGE);
        	
        }
		
		resetValue();
		
	}
	
	private static String generateFileName() throws ParseException {
		
		SimpleDateFormat sdf = new SimpleDateFormat("_dd-M-yyyy_hh-mm-ss_");
		String dateInString = "31-08-1982 10:20:56";
		Date date = sdf.parse(dateInString);
		
		return "crash_logs" + date;
		
	}
	
}
