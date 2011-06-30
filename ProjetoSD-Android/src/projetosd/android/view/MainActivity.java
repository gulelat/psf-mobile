package projetosd.android.view;

import java.io.FileNotFoundException;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import projetosd.android.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		Button newFormButton = (Button) findViewById(R.id.btn_newForm);
		newFormButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				try {
					newForm();
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (ParserConfigurationException e) {
					e.printStackTrace();
				} catch (SAXException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
		
		
		Button viewDataButton = (Button) findViewById(R.id.btn_viewData);
		viewDataButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				try {
					viewData();
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (ParserConfigurationException e) {
					e.printStackTrace();
				} catch (SAXException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	private void newForm() throws FileNotFoundException, ParserConfigurationException, SAXException, IOException{
		//TODO:Trocar por URI, quando servidor estiver funcionando;
		Intent newForm = new Intent(this,NewFormActivity.class);
    	startActivity(newForm);
    	finish();
	}
	
	private void viewData() throws FileNotFoundException, ParserConfigurationException, SAXException, IOException{
		Intent viewData = new Intent(this,NewFormActivity.class);  // Trocar para classe ViewData ao ser criada
    	startActivity(viewData);
    	finish();
	}

	
}
