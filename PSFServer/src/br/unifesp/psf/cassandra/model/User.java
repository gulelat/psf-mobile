package br.unifesp.psf.cassandra.model;

public class User {
	public Long key;
	private String xml;
	
	public Long getKey() {
		return key;
	}

	public void setKey(Long key) {
		this.key = key;
	}


	public String getXml() {
		return xml;
	}

	public void setXml(String xml) {
		this.xml = xml;
	}
	
}
