package fr.pmk_updater.utils;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import javax.swing.JOptionPane;

import org.jdom2.Document;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

public class XmlChecker {

	private static String url = "http://launcher.pumpmykins.eu/launcher-jar-updater/pumpmyupdater.xml";
	
	public static Document buildXmlDocument(String url) {
		
		Document doc = null;
		
		try {
			URL xmlUrl = new URL(url);
			
			//On ouvre une connections ur la page
			URLConnection urlConnection = xmlUrl.openConnection();
			urlConnection.setUseCaches(false);
			
			//On se connecte sur cette page
			urlConnection.connect();
			
			//On r�cup�re le fichier XML sous forme de flux
			InputStream stream = urlConnection.getInputStream();
						
			SAXBuilder sxb = new SAXBuilder();
						
			//On cr�e le document xml avec son flux
			try {doc = sxb.build(stream);
			} catch (JDOMException e) {e.printStackTrace();
			} catch (IOException e) {e.printStackTrace();}
		
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
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
			JOptionPane.showMessageDialog(null,"","",JOptionPane.ERROR_MESSAGE);
			return;
		}
		
	}

	
	
	public Document getXmlDoc() {
		return xmlDoc;
	}

	public void setXmlDoc(Document xmlDoc) {
		this.xmlDoc = xmlDoc;
	}
	
}
