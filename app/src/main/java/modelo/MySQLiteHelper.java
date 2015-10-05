package modelo;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MySQLiteHelper extends SQLiteOpenHelper {


	private static final int DATABASE_VERSION = 2;
	private static final String DATABASE_NAME = "scab";

	private static final String CREATE_MATERIAL_TABLE = "CREATE TABLE material ( " +
			"id INTEGER PRIMARY KEY AUTOINCREMENT, " +
			"ano TEXT, "+
			"classificacao TEXT, " +
			"editora TEXT, " +
			"local TEXT, " +
			"referencia TEXT, " +
			"titulo TEXT, " +
			"unitermo TEXT, " +
			"volume TEXT)";

	private static final String CREATE_LIVRO_TABLE = "CREATE TABLE livro ( " +
			"id INTEGER PRIMARY KEY AUTOINCREMENT, " +
			"autor TEXT, "+
			"cutter TEXT, " +
			"numerotombo TEXT)";

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
		db.execSQL(CREATE_LIVRO_TABLE);
		db.execSQL(CREATE_MATERIAL_TABLE);
		db.execSQL(CREATE_MENSAGEM_TABLE);
		db.execSQL(CREATE_USUARIO_TABLE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// Drop older user table if existed
		db.execSQL("DROP TABLE IF EXISTS livro");
		db.execSQL("DROP TABLE IF EXISTS material");
		db.execSQL("DROP TABLE IF EXISTS mensagem");
		db.execSQL("DROP TABLE IF EXISTS usuario");

		// create fresh user table
		this.onCreate(db);
	}

}
