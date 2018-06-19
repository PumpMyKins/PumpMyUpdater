package fr.pmk_updater.utils;

public class VersionData {

	private String url;
	private String version;
	
	public VersionData(String u, String v) {
		this.url = u;
		this.version = v;
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
	
}
