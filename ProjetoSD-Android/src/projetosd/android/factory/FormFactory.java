package projetosd.android.factory;

import java.security.InvalidParameterException;
import java.util.Iterator;

import projetosd.android.domain.Form;
import projetosd.android.domain.Option;
import projetosd.android.domain.TextType;
import projetosd.android.view.FormView;
import android.content.Context;
import android.text.InputFilter;
import android.text.InputType;
import android.text.Spanned;
import android.text.method.DigitsKeyListener;
import android.text.method.NumberKeyListener;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class FormFactory{
	
	/**
	 * 
	 * Cria tela com pergunta e resposta de acordo com dados passados em @form 
	 *
	 */
	static public FormView createForm(Context context,Form form){
		FormView layout = new FormView(context);
		   layout.setOrientation(1);
		 
		TextView question = new TextView(context);
		question.setText(form.getQuestion());

		layout.addView(question);
		Class answerComponent = form.getAnswerComponent();
		if(answerComponent.equals(RadioButton.class)||answerComponent.equals(CheckBox.class)){
			RadioGroup radioGroup = new RadioGroup(context);
			if(form.getAnswerOptions().isEmpty()){
				throw new InvalidParameterException("Não foi dados as opções de escolha.");
			}
			for (Iterator<Option> iterator = form.getAnswerOptions().iterator(); iterator.hasNext();) {
				Option option = (Option) iterator.next();
				if(answerComponent.equals(RadioButton.class)){
					RadioButton radio = new RadioButton(context);
					radio.setTag(option.getValue());
					radio.setText(option.getLabel());
					radioGroup.addView(radio, option.getId());
					if(option.getValue().equals(form.getDefaultValue())){
						radioGroup.check(radio.getId());
					}
				}else if(answerComponent.equals(CheckBox.class)){
					CheckBox check = new CheckBox(context);
					check.setTag(option.getValue());
					check.setText(option.getLabel());
					if(option.getValue().equals(form.getDefaultValue()))
						check.setChecked(true);
					layout.addAnswerComponent(check);
				}
			}
			if(answerComponent.equals(RadioButton.class)){
				layout.addAnswerComponent(radioGroup);
			}
		}else if(answerComponent.equals(EditText.class)){
			EditText edt = new EditText(context);
			InputFilter[] filters = new InputFilter[1];
			filters[0] = new InputFilter.LengthFilter(form.getLength());
			if(form.getTextType().equals(TextType.NUMBER))
				edt.setInputType(InputType.TYPE_CLASS_NUMBER);
			edt.setFilters(filters);
			edt.setText(form.getDefaultValue());
			layout.addAnswerComponent(edt);
		}
		return layout;
	}

}
