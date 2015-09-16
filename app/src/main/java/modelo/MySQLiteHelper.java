package modelo;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MySQLiteHelper extends SQLiteOpenHelper {


    private static final int DATABASE_VERSION = 2;
    private static final String DATABASE_NAME = "scab";

	private static final String CREATE_MENSAGEM_TABLE = "CREATE TABLE mensagem ( " +
			"id INTEGER PRIMARY KEY AUTOINCREMENT, " +
			"mensagem TEXT, " +
			"dataInicio TEXT, "+
			"dataFim TEXT, "+
			"visibilidade TEXT, "+
			"tipo TEXT, " +
			"horario TEXT)";

	private static final String CREATE_USUARIO_TABLE = "CREATE TABLE usuario ( " +
			"id INTEGER PRIMARY KEY AUTOINCREMENT, " +
			"matricula TEXT, " +
			"nome TEXT, "+
			"curso TEXT, "+
			"senha TEXT )";

	public MySQLiteHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);	
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CREATE_USUARIO_TABLE);
		db.execSQL(CREATE_MENSAGEM_TABLE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// Drop older user table if existed
        db.execSQL("DROP TABLE IF EXISTS usuario");
		db.execSQL("DROP TABLE IF EXISTS mensagem");
        
        // create fresh user table
        this.onCreate(db);
	}

}
