package modelo;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MySQLiteHelper extends SQLiteOpenHelper {

	private static SQLiteMaterial sqlLiteMaterial;
	private static SQLiteLivro sqlLiteLivro;

	private static final int DATABASE_VERSION = 2;
	private static final String DATABASE_NAME = "scab";

	private static final String CREATE_MATERIAL_TABLE = "CREATE TABLE " +sqlLiteMaterial.TABLE_MATERIAL+ " ( " +
			sqlLiteMaterial.KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
			sqlLiteMaterial.KEY_ANO + " TEXT, "+
			sqlLiteMaterial.KEY_CLASSIFICACAO + " TEXT, " +
			sqlLiteMaterial.KEY_EDITORA + " TEXT, " +
			sqlLiteMaterial.KEY_LOCAL + " TEXT, " +
			sqlLiteMaterial.KEY_REFERENCIA + " TEXT, " +
			sqlLiteMaterial.KEY_TITULO + " TEXT, " +
			sqlLiteMaterial.KEY_UNITERMO + " TEXT, " +
			sqlLiteMaterial.KEY_VOLUME + " TEXT)";

	private static final String CREATE_LIVRO_TABLE = "CREATE TABLE livro ( " +
			"id INTEGER PRIMARY KEY AUTOINCREMENT, " +
			"codmaterial INTEGER, " +
			"autor TEXT, "+
			"cutter TEXT, " +
			"isbn TEXT, " +
			"numerotombo TEXT)";

	private static final String CREATE_MENSAGEM_TABLE = "CREATE TABLE mensagem ( " +
			"id INTEGER PRIMARY KEY AUTOINCREMENT, " +
			"mensagem TEXT, " +
			"datainicio TEXT, "+
			"datafim TEXT, "+
			"visibilidade TEXT, "+
			"tipo TEXT, " +
			"horario TEXT)";

	private static final String CREATE_REVISTA_TABLE = "CREATE TABLE revista ( " +
			"id INTEGER PRIMARY KEY AUTOINCREMENT, " +
			"codmaterial INTEGER, " +
			"anofim TEXT, " +
			"anoinicio TEXT, "+
			"colecao TEXT, "+
			"corrente TEXT, "+
			"issn TEXT, " +
			"mesfim TEXT, " +
			"mesinicio TEXT, " +
			"numero TEXT, " +
			"periodicidade TEXT)";

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
		db.execSQL(CREATE_REVISTA_TABLE);
		db.execSQL(CREATE_USUARIO_TABLE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// Drop older user table if existed
		db.execSQL("DROP TABLE IF EXISTS livro");
		db.execSQL("DROP TABLE IF EXISTS material");
		db.execSQL("DROP TABLE IF EXISTS mensagem");
		db.execSQL("DROP TABLE IF EXISTS revista");
		db.execSQL("DROP TABLE IF EXISTS usuario");

		// create fresh user table
		this.onCreate(db);
	}
}
