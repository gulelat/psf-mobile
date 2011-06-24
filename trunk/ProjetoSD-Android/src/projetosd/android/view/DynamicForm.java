package projetosd.android.view;

import java.util.HashMap;

import projetosd.android.R;
import projetosd.android.domain.Form;
import projetosd.android.domain.TextType;
import projetosd.android.factory.FormFactory;
import projetosd.android.parser.XMLFormParser;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;


public class DynamicForm extends Activity {
    /** Called when the activity is first created. */
	
	private static HashMap<Integer, Form> formMap = new HashMap<Integer, Form>();
	public static HashMap<Integer, FormView> formViewMap = new HashMap<Integer, FormView>();
	public static XMLFormParser parser;
	private FormView thisFormView;
	private LinearLayout layout ;
	private int pageIndex =0;
    @Override
    public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.form);
	    layout = (LinearLayout)findViewById(R.id.dynamicView);
	    reset();
		Form form = createForm(0);
		thisFormView = createFormView(0,form);
	    layout.addView(thisFormView,0);


	    ImageButton forwardButton = (ImageButton)findViewById(R.id.forwardButton);
	    forwardButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				goForward();
				
			}
		});
	    
	    ImageButton backButton = (ImageButton)findViewById(R.id.backButton);
	    backButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				goBack();
			}
		});
	    
	    ImageButton helpButton = (ImageButton)findViewById(R.id.helpButton);
	    helpButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				showHelp();
			}
		});

    }
    
    private void showHelp(){
    	AlertDialog alertDialog = new AlertDialog.Builder(this).create();

	    alertDialog.setTitle("Informação");

	    alertDialog.setMessage(formMap.get(pageIndex).getHelp()==null?"Não informado":formMap.get(pageIndex).getHelp());

	    alertDialog.setButton2("OK", new DialogInterface.OnClickListener() {
		      public void onClick(DialogInterface dialog, int which) {
		        return;
		    }});
	    alertDialog.show();
    }
    
    private void reset(){
    	pageIndex=0;
    	formMap = new HashMap<Integer, Form>();
    	formViewMap = new HashMap<Integer, FormView>();
    }
    
    private void goBack(){
    	if(pageIndex==0){
    		AlertDialog alertDialog = new AlertDialog.Builder(this).create();

		    alertDialog.setTitle("Voltar para tela inicial");

		    alertDialog.setMessage("As respostas serão perdidas. Deseja voltar para a tela inicial?");

		    alertDialog.setButton("Sim", new DialogInterface.OnClickListener() {
		      public void onClick(DialogInterface dialog, int which) {
		    	  backToMain();
		    } }); 

		    alertDialog.setButton2("Não", new DialogInterface.OnClickListener() {

		      public void onClick(DialogInterface dialog, int which) {

		        return;

		    }});
		    alertDialog.show();
    	}else{
    		pageIndex--;
	    	layout.removeView(thisFormView);
	    	Form form = createForm(pageIndex);
    		thisFormView = createFormView(pageIndex,form);
    	    layout.addView(thisFormView,0);
    	}
    }
    private void backToMain(){
    	//TODO: Achar outra forma voltar para tela principal pois tá feio pra kcete.
    	 Intent mainIntent = new Intent(this,MainActivity.class);
	    	startActivity(mainIntent);
	    	layout.removeView(thisFormView);
	    	finish();
    }
    
    
    private void goForward(){
    
    	if(parser.getFormCount()==pageIndex+1){
    		AlertDialog alertDialog = new AlertDialog.Builder(this).create();

		    alertDialog.setTitle("Fim da Pesquisa");

		    alertDialog.setMessage("As respostas não poderão ser alteradas. Deseja continuar?");

		    alertDialog.setButton("Sim", new DialogInterface.OnClickListener() {
		      public void onClick(DialogInterface dialog, int which) {
		    	  goToLastPage();
		    } }); 

		    alertDialog.setButton2("Não", new DialogInterface.OnClickListener() {

		      public void onClick(DialogInterface dialog, int which) {

		        return;

		    }});
		    alertDialog.show();
    	}else{
    		if(formMap.get(pageIndex).getRequired() && "".equals(this.thisFormView.getAnswer())){
    			AlertDialog alertRequired = new AlertDialog.Builder(this).create();
    			alertRequired.setTitle("Resposta obrigatória");
    			alertRequired.setMessage("Responda a questão para avançar.");
    			alertRequired.setButton2("OK", new DialogInterface.OnClickListener() {
    		    	public void onClick(DialogInterface dialog, int which) {
    			    	  return;
    			    }});
    			alertRequired.show();
    			return;
    		}
    		pageIndex++;
    		Form form = createForm(pageIndex);
    		layout.removeView(thisFormView);
    		thisFormView = createFormView(pageIndex,form);
    	    layout.addView(thisFormView,0);
    	}
    }
    private void goToLastPage(){
    	 Intent intent = new Intent(this,LastPageActivity.class);
	     startActivity(intent);
	     finish();
    }
    
    private FormView createFormView(int index,Form form){
    	if(formViewMap.containsKey(index)){
    		return formViewMap.get(index);
    	}
    	FormView formView = FormFactory.createForm(this, form);
    	formViewMap.put(index, formView);
	    return formView;
    }
    
    private Form createForm(int index){
    	if(formMap.containsKey(index)){
    		return formMap.get(index);
    	}
    	
    	Form form = new Form();  
	    form.setQuestion(parser.getUniqueNodeValue(index, "rotulo"));
	    form.setHelp(parser.getUniqueNodeValue(index, "ajuda"));
	    form.setAnswerComponent(parser.getAnswerComponent(index));
	    form.setAnswerOptions(parser.getAnswerOptions(index));
	    form.setRequired("sim".equals(parser.getAttributeFromNode(index,XMLFormParser.XML_FORM,"obrigatorio",0)));
	    form.setDefaultValue(parser.getUniqueNodeValue(index, "valor_padrao"));
	    String strTam = parser.getUniqueNodeValue(index,"tamanho");
	    form.setLength(strTam!=null?Integer.parseInt(strTam):null);
	    form.setTextType(TextType.ALPHA.getValue().equals(parser.getUniqueNodeValue(index, "tipo_dados"))?TextType.ALPHA:TextType.NUMBER);
	    formMap.put(index,form);
	    return form;
    }
    
}