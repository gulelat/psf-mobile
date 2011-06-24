package projetosd.android.view;

import java.io.FileNotFoundException;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import projetosd.android.R;
import projetosd.android.parser.XMLFormParser;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

public class MainActivity extends Activity{
	
	
	/*TODO: Criar tela para exibir questionários já realizados
	 *TODO:	Criar botão para mandar dados para o servidor.
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		Button createFormButton = (Button) findViewById(R.id.createFormButton);
		createFormButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				try {
					createForm();
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
	
	private void createForm() throws FileNotFoundException, ParserConfigurationException, SAXException, IOException{
		//TODO:Trocar por URI, quando servidor estiver funcionando;
		Intent createForm = new Intent(this,DynamicForm.class);
		EditText urlInput = (EditText) findViewById(R.id.editTextURL);
		DynamicForm.parser = new XMLFormParser(getAssets().open(urlInput.getText().toString()));///trocar por uri
    	startActivity(createForm);
    	finish();
	}
}
