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
	
	public List<String> getAnswers(int idPergunta) {
		 List<String> answer = new ArrayList<String>();
		if(getAnswerComponent()==null||getAnswerComponent().isEmpty())
			return answer;
		if(getAnswerComponent().get(0) instanceof CheckBox){
			int cont=0;
			for (Iterator iterator = getAnswerComponent().iterator(); iterator.hasNext();) {
				CheckBox checkBox = (CheckBox) iterator.next();
				if(checkBox.isChecked()){
					answer.add(checkBox.getTag().toString());
					cont++;
				}
			}
			return answer;
		}else if(getAnswerComponent().get(0) instanceof EditText){
			answer.add(((EditText)getAnswerComponent().get(0)).getText().toString());
		}else{
			RadioGroup radioG = ((RadioGroup)getAnswerComponent().get(0));
			RadioButton rButton = (RadioButton)findViewById(radioG.getCheckedRadioButtonId());
			if(rButton!=null)
				answer.add(rButton.getTag().toString());
		}
		
		return answer;
	}
	public void setAnswerComponent(List<View> answerComponent) {
		this.answerComponent = answerComponent;
	}
	public List<View> getAnswerComponent() {
		return answerComponent;
	}

}
