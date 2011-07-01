package projetosd.android.domain;

import projetosd.android.R;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseManager extends SQLiteOpenHelper {
	private static final String DATABASE_NAME = "pesquisa.db";
	private static final int DATABASE_VERSION = 1;
	private static final String TABLE_DADOS = "tbl_Dados";
	private static final String TABLE_DADOS_ID = "id";
	private static final String TABLE_DADOS_RESULTADO = "resultado";
	public static final String TABLE_DADOS_FICHA_ID = "fichaId";
	public static final String TABLE_DADOS_CREATED_DATE = "date_created";
	public static final String TABLE_DADOS_NAME = "name";
	private final Context mContext;

	public DatabaseManager(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		this.mContext = context;
	}

	public static String getTableDados() {
		return TABLE_DADOS;
	}

	public static String getTableDadosResultado() {
		return TABLE_DADOS_RESULTADO;
	}
	public static String getTableDadosFichaId() {
		return TABLE_DADOS_FICHA_ID;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		String sql = mContext.getString(R.string.sql_CreateTable);
		db.execSQL(sql);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS tbl_Dados");
		onCreate(db);
	}

}
