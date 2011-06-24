package projetosd.android.domain;

import java.util.ArrayList;
import java.util.List;

import android.view.View;

//subeclipse configurado boa noite!!!!!!!!!!!11
public class Form {

	private String id;
	private Boolean required;
	private String question;
	private Class answerComponent;
	private String help;
	private List<Option> answerOptions = new ArrayList<Option>();
	private String defaultValue;
	private TextType textType;
	private Integer length;
	public String getQuestion() {
		return question;
	}
	public void setQuestion(String question) {
		this.question = question;
	}
	public Class getAnswerComponent() {
		return answerComponent;
	}
	public void setAnswerComponent(Class answerComponent) {
		this.answerComponent = answerComponent;
	}
	public String getHelp() {
		return help;
	}
	public void setHelp(String help) {
		this.help = help;
	}
	public List<Option> getAnswerOptions() {
		return answerOptions;
	}
	public void setAnswerOptions(List<Option> answerOptions) {
		this.answerOptions = answerOptions;
	}
	public void setRequired(Boolean required) {
		this.required = required;
	}
	public Boolean getRequired() {
		return required;
	}
	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}
	public String getDefaultValue() {
		return defaultValue;
	}
	public void setTextType(TextType textType) {
		this.textType = textType;
	}
	public TextType getTextType() {
		return textType;
	}
	public void setLength(Integer length) {
		this.length = length;
	}
	public Integer getLength() {
		return length;
	}
	
	
}
