package fr.pmk_updater.utils;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

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
	
	
	/***
	 * @author Nicolas CYNOBER
	 * @url https://cynober.developpez.com/tutoriel/java/xml/jdom/
	 * @param filePath
	 * @param destination
	 */
	public static boolean downloadFile(String filePath, String destination) { 
		
		boolean isOk = true;
		
		URLConnection connection = null;
		InputStream is = null;
		FileOutputStream destinationFile = null;
		
		try { 
			
			//On crée l'URL
	        URL url = new URL(filePath);

			//On crée une connection vers cet URL
			connection = url.openConnection( );
	        
			//On récupère la taille du fichier
			int length = connection.getContentLength();

			//Si le fichier est inexistant, on lance une exception
			if(length == -1){
				throw new IOException("Fichier vide");
	       	}

			//On récupère le stream du fichier
			is = new BufferedInputStream(connection.getInputStream());

			//On prépare le tableau de bits pour les données du fichier
			byte[] data = new byte[length];

			//On déclare les variables pour se retrouver dans la lecture du fichier
			int currentBit = 0;
			int deplacement = 0;
			
			//Tant que l'on n'est pas à la fin du fichier, on récupère des données
			while(deplacement < length){
				currentBit = is.read(data, deplacement, data.length-deplacement);	
				if(currentBit == -1)break;	
				deplacement += currentBit;
			}

			//Si on est pas arrivé à la fin du fichier, on lance une exception
			if(deplacement != length){
				throw new IOException("Le fichier n'a pas été lu en entier (seulement " 
					+ deplacement + " sur " + length + ")");
			}		
		
			//On crée un stream sortant vers la destination
			destinationFile = new FileOutputStream(destination); 

			//On écrit les données du fichier dans ce stream
			destinationFile.write(data);

			//On vide le tampon et on ferme le stream
			destinationFile.flush();

		} catch (MalformedURLException e) { 
	    	  
			isOk = false;
			Utils.AddException(e);
	    	System.err.println("Problème avec l'URL : " + filePath); 
	    	  
	    } catch (IOException e) { 
	    	  
	    	isOk = false;
	    	Utils.AddException(e);
	    	e.printStackTrace();
	        
	    } finally{
	    	  
	    	try {
	    		  
	    		is.close();
				destinationFile.close();
				  
	    	} catch (IOException e) {
	    		  
	    		isOk = false;
	    		Utils.AddException(e);
	    		e.printStackTrace();
	    		  
	    	}
	    }
		
		return isOk;
	}
	
}
