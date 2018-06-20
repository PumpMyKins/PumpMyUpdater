package fr.pmk_updater.utils;

public class VersionData {

	private String url;
	private String version;
	private String checksum;
	
	public VersionData(String u, String v, String c) {
		this.url = u;
		this.version = v;
		this.checksum = c;
	}
	
	public String toString() {
		return "URL : " + url + "\n version : " + version + "\n Checksum : " + checksum;
		
	}
	
	public String getVersion() {
		return version;
	}
	public void setVersion(String v) {
		this.version = v;
	}
	
	public String getUlr() {
		return url;
	}
	public void setUlr(String u) {
		this.url = u;
	}

	public String getChecksum() {
		return checksum;
	}

	public void setChecksum(String c){
		this.checksum = c;
	}
	
}
