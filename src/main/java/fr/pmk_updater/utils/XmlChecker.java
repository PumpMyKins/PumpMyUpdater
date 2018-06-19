package fr.pmk_updater.utils;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import org.jdom2.Document;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import fr.pmk_updater.exception.ExceptionFile;
import fr.pmk_updater.exception.ExceptionManager;

public class XmlChecker {

	private static String url = "";
	
	public static Document buildXmlDocument(String url) {
		
		Document doc = null;
		
		try {
			
			URL xmlUrl = new URL(url);
			
			//On ouvre une connections ur la page
			URLConnection urlConnection = xmlUrl.openConnection();
			urlConnection.setUseCaches(false);
			
			//On se connecte sur cette page
			urlConnection.connect();
			
			//On récupère le fichier XML sous forme de flux
			InputStream stream = urlConnection.getInputStream();
						
			SAXBuilder sxb = new SAXBuilder();
						
			//On crée le document xml avec son flux
			try {
				doc = sxb.build(stream);
			} catch (JDOMException e) {
				ExceptionManager.addError(e.getMessage());
				ExceptionFile.addError(Utils.getStringByStackTrace(e.getStackTrace()));				
				e.printStackTrace();
			} catch (IOException e) {
				ExceptionManager.addError(e.getMessage());
				ExceptionFile.addError(Utils.getStringByStackTrace(e.getStackTrace()));
				e.printStackTrace();
			}
		
		} catch (MalformedURLException e) {
			ExceptionManager.addError(e.getMessage());
			ExceptionFile.addError(Utils.getStringByStackTrace(e.getStackTrace()));
			e.printStackTrace();
		} catch (IOException e) {
			ExceptionManager.addError(e.getMessage());
			ExceptionFile.addError(Utils.getStringByStackTrace(e.getStackTrace()));
			e.printStackTrace();
		}
		
		return doc;
		
	}

	public static String getUrl() {
		return url;
	}

	public static void setUrl(String url) {
		XmlChecker.url = url;
	}
	
	private Document xmlDoc;
	
	public XmlChecker() {
		
		xmlDoc = buildXmlDocument(url);
		
		if(xmlDoc == null) {
			
			ExceptionManager.show();
			ExceptionFile.pushFile();
			return;
		}else {
			
			// récupération methods réussie
			
		}
		
	}

	
	
	public Document getXmlDoc() {
		return xmlDoc;
	}

	public void setXmlDoc(Document xmlDoc) {
		this.xmlDoc = xmlDoc;
	}
	
}
