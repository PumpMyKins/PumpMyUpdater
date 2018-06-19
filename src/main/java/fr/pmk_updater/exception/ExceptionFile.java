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
			System.out.println(fileName);
			
		} catch (ParseException e) {
			
			e.printStackTrace();
			
		}
		
		try {
			
            FileWriter fileWriter = new FileWriter(fileName);

            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            bufferedWriter.write("============================  Crash report  ======================================\n\n");
            
            for (String string : errorList) {
				
            	bufferedWriter.write(string);
            	bufferedWriter.write("\n========================================================================\n");
            	
			}

            bufferedWriter.close();
        }
        catch(IOException ex) {
        	
        	JOptionPane.showMessageDialog(null, " Erreur d'ecriture du fichier de crash \n" + ex.getMessage() , "Exception" , JOptionPane.ERROR_MESSAGE);
        	
        }
		
		resetValue();
		
	}
	
	private static String generateFileName() throws ParseException {
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("_yyyy-MM-dd_HH-mm-ss");
		Date date = new Date();
		
		return "crash_logs" + dateFormat.format(date);
		
	}
	
}
