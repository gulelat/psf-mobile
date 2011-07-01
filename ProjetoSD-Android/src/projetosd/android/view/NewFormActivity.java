package projetosd.android.view;

import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.xml.sax.SAXException;

import projetosd.android.R;
import projetosd.android.parser.XMLFormParser;
import android.app.Activity;
import android.content.Intent;
import android.net.ParseException;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class NewFormActivity extends Activity {
		
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.new_form);
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
		Intent createForm = new Intent(this,DynamicForm.class);
		
		EditText urlInput = (EditText) findViewById(R.id.editTextURL);
		//TODO Pegar fichaID da URL
		//TODO: Pegar XML com get da url passada.
		int fichaID = 0;
		try{
			String id = getID(urlInput.getText().toString());
			if(id!=""){
					fichaID=Integer.valueOf(id);
			}
			}
		catch(Exception e){
			return;
		}
		DynamicForm.fichaIdToSave = fichaID;
		InputStream xml = getAssets().open(urlInput.getText().toString());

		//xml = new ByteArrayInputStream(getXML(urlInput.getText().toString()).getBytes("UTF-8"));
		DynamicForm.parser = new XMLFormParser(xml);///trocar por uri
    	startActivity(createForm);
    	finish();
	}
	
	private String getID(String url){
		if(url.contains("ID=")){
			return url.substring(url.indexOf("ID=\""),url.length());
		}
		return "";
	}
	
	private String getXML(String url) throws ClientProtocolException, IOException{
		 HttpClient client = new DefaultHttpClient();  
         HttpGet get = new HttpGet(url);
         HttpResponse responseGet = client.execute(get);
         HttpEntity resEntityGet = responseGet.getEntity();  
         String sourceString="";
         if (resEntityGet != null) {                 
             try {
                 sourceString= new String(EntityUtils.toString(resEntityGet));
                 Log.i("Resposta do GET",EntityUtils.toString(resEntityGet));
             } catch (ParseException exc) {
                 exc.printStackTrace();
             } catch (IllegalStateException exc) {
                 exc.printStackTrace();
             }
         }
         return sourceString;
	}
}