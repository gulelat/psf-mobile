package projetosd.android.view;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import android.content.Context;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class FormView extends LinearLayout {
	
	private List<View> answerComponent = new ArrayList<View>();
	public FormView(Context context) {
		super(context);
	}
	public void addAnswerComponent(View view){
		answerComponent.add(view);
		super.addView(view);
	}
	public String getAnswer() {
		String answer = "";
		if(getAnswerComponent()==null||getAnswerComponent().isEmpty())
			return "";
		if(getAnswerComponent().get(0) instanceof CheckBox){
			for (Iterator iterator = getAnswerComponent().iterator(); iterator.hasNext();) {
				CheckBox checkBox = (CheckBox) iterator.next();
				if(checkBox.isChecked()){
					//TODO:Padronizar com outro grupo o formato da resposta de perguntas com checkbox
					answer += ";" +checkBox.getTag().toString();
				}
			}
			return answer;
		}else if(getAnswerComponent().get(0) instanceof EditText){
			return ((EditText)getAnswerComponent().get(0)).getText().toString();
		}else{
			RadioGroup radioG = ((RadioGroup)getAnswerComponent().get(0));
			RadioButton rButton = (RadioButton)findViewById(radioG.getCheckedRadioButtonId());
			if(rButton!=null)
				return rButton.getTag().toString();
			else
				return "";
		}
	}
	public void setAnswerComponent(List<View> answerComponent) {
		this.answerComponent = answerComponent;
	}
	public List<View> getAnswerComponent() {
		return answerComponent;
	}

}
