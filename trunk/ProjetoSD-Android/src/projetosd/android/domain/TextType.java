package projetosd.android.domain;

public enum TextType {
	NUMBER("numerico"),ALPHA("alfanumerico");
	
	private String value;

	private TextType(String value) {
		this.setValue(value);
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
}
