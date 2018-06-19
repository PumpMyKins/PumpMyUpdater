package fr.pmk_updater.exception;

import java.util.ArrayList;

import javax.swing.JOptionPane;

public class ExceptionManager {

	private static ArrayList<String> errorList = new ArrayList<String>();
	private static String title = "";
	private static int dialogueType;
	
	private static void resetValue() {
		
		errorList = new ArrayList<String>();
		title = "";
		dialogueType = 0;
		
	}
	
	public static void show() {
		
		String msg = "";
		
		for (String string : errorList) {
			msg += string + "\n";
		}
		
		// ajout du message en boite de dialogue
		JOptionPane.showMessageDialog(null, msg , getTitle(), getDialogueType());
		
		resetValue();
	}
	
	public static void addError(String error) {
		errorList.add(error);
	}

	public static ArrayList<String> getErrorList() {
		return errorList;
	}

	public static void setErrorList(ArrayList<String> errorList) {
		ExceptionManager.errorList = errorList;
	}

	private static String getTitle() {
		return title;
	}

	public static void setTitle(String t) {
		ExceptionManager.title = t;
	}

	private static int getDialogueType() {
		return dialogueType;
	}

	public static void setDialogueType(int dialogueType) {
		ExceptionManager.dialogueType = dialogueType;
	}
	
}
