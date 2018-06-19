package fr.pmk_updater.utils;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.jdom2.Document;
import org.jdom2.Element;
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
			
			//On récupère le fichier XML sous forme de flux
			InputStream stream = urlConnection.getInputStream();
						
			SAXBuilder sxb = new SAXBuilder();
						
			//On crée le document xml avec son flux
			try {
				doc = sxb.build(stream);
			} catch (JDOMException e) {
				Utils.AddException(e);		
				e.printStackTrace();
			} catch (IOException e) {
				Utils.AddException(e);
				e.printStackTrace();
			}
		
		} catch (MalformedURLException e) {
			Utils.AddException(e);
			e.printStackTrace();
		} catch (IOException e) {
			Utils.AddException(e);
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
	
	public boolean isInit = false;
	
	private Document xmlDoc;
	private ArrayList<VersionData> versionsList;
	
	private VersionData lastStableData;
	private String lastStableVersion;
	
	private VersionData lastDevData;
	private String lastDevVersion;
	
	public XmlChecker() {
		
		xmlDoc = buildXmlDocument(url);
		
		if(xmlDoc != null) {
			
			isInit = true;
			// récupération methods réussie
			lastDevVersion = getXmlLastDevVersion();
			lastStableVersion = getXmlLastStableVersion();
			
			versionsList = getXmlVersions();
			
		}
		
	}
	
	public VersionData getLastVersionData(boolean b) {
		
		if(b) {
			return getLastDevData();
		}else {
			return getLastStableData();
		}
		
	}
	
	private ArrayList<VersionData> getXmlVersions(){
		
		ArrayList<VersionData> versions = new ArrayList<VersionData>();
			
		//On récupère la racine
		Element racine = xmlDoc.getRootElement();
		Element vRacine = racine.getChild("versions");
			
		//On liste toutes les versions
		List<Element> listVersions = vRacine.getChildren("version");
		Iterator<Element> iteratorVersions = listVersions.iterator();
			
		//On parcourt toutes les versions
		while(iteratorVersions.hasNext()){
			
			Element version = (Element)iteratorVersions.next();
				
			Element elementNom = version.getChild("nom");
			Element elementUrl = version.getChild("url");
			
			String versionName = elementNom.getText();
			VersionData vData = new VersionData(versionName, elementUrl.getText());
			
			if(versionName.equals(lastDevVersion)) {
				
				//System.out.println("set dev " + vData);
				setLastDevData(vData);
				
			}
			
			if(versionName.equals(lastStableVersion)) {
				
				//System.out.println("set stable " + vData);
				setLastStableData(vData);
				
			}
			
			versions.add(vData);
			
		}
		
		return versions;
	}
	
	private String getXmlLastDevVersion() {
		
		String v = "";
		
		Element racine = xmlDoc.getRootElement();
		Element elementV = racine.getChild("last-dev");
		
		v = elementV.getText();
		
		//System.out.println("getXmlLastDevVersion() " + v);
		return v;
		
	}
	
	private String getXmlLastStableVersion() {
		
		String v = "";
		
		Element racine = xmlDoc.getRootElement();
		Element elementV = racine.getChild("last-stable");
		
		v = elementV.getText();
		
		//System.out.println("getXmlLastStableVersion() " + v);
		return v;
		
	}
	
	
	
	public Document getXmlDoc() {
		return xmlDoc;
	}

	public void setXmlDoc(Document xmlDoc) {
		this.xmlDoc = xmlDoc;
	}

	public VersionData getLastStableData() {
		return lastStableData;
	}

	public void setLastStableData(VersionData lastStableData) {
		this.lastStableData = lastStableData;
	}

	public VersionData getLastDevData() {
		return lastDevData;
	}

	public void setLastDevData(VersionData lastDevData) {
		this.lastDevData = lastDevData;
	}

	public ArrayList<VersionData> getVersionsList() {
		return versionsList;
	}

	public void setVersionsList(ArrayList<VersionData> versionsList) {
		this.versionsList = versionsList;
	}

	public String getLastDevVersion() {
		return lastDevVersion;
	}

	public void setLastDevVersion(String lastDevVersion) {
		this.lastDevVersion = lastDevVersion;
	}

	public String getLastStableVersion() {
		return lastStableVersion;
	}

	public void setLastStableVersion(String lastStableVersion) {
		this.lastStableVersion = lastStableVersion;
	}
	
	
	
}
