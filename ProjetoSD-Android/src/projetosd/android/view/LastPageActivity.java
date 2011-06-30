package projetosd.android.view;

import java.util.Iterator;

import projetosd.android.R;
import projetosd.android.domain.DatabaseManager;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class LastPageActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.last_page);
		Button saveButton = (Button) findViewById(R.id.saveButton);
		saveButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				saveAction();
			}
		});
	}

	public void saveAction() {
		String respostas = "";
		int idPergunta = 0;
		for (Iterator iterator = DynamicForm.formViewMap.values().iterator(); iterator
				.hasNext();) {
			FormView formView = (FormView) iterator.next();
			// TODO: Padronizar com outro grupo o formato das respostas
			respostas += formView.getAnswer(idPergunta++);
		}

		gravarResultado(respostas);
		
		// TODO:Salvar respostas localmente ao inves de exibir alerta.
		AlertDialog alertDialog = new AlertDialog.Builder(this).create();

		alertDialog.setTitle("TesteSave");

		alertDialog.setMessage("As respostas s�o " + respostas);

		alertDialog.setButton("Ok", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				backToMain();
			}
		});

		alertDialog.show();
	}

	private void backToMain() {
		Intent mainIntent = new Intent(this, MainActivity.class);
		startActivity(mainIntent);
		finish();
	}

	private void gravarResultado(String respostas) {
		DatabaseManager dbm = new DatabaseManager(this);
		SQLiteDatabase db = dbm.getWritableDatabase();

		ContentValues cv = new ContentValues();

		cv.put(DatabaseManager.getTableDadosResultado(), respostas);

		db.insert(DatabaseManager.getTableDados(),
				DatabaseManager.getTableDadosResultado(), cv);
		db.close();
	}

}