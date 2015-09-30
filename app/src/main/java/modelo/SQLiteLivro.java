package modelo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

import objeto.Livro;
import objeto.Material;

/**
 * Created by - on 14/09/2015.
 */
public class SQLiteLivro {

    private MySQLiteHelper banco;
    private static final String TABLE_LIVRO = "livro";
    private static final String KEY_AUTOR = "autor";
    private static final String KEY_CIDADE = "cidade";
    private static final String KEY_ID_MATERIAL = "codmaterial";
    private static final String KEY_CUTTER = "cutter";
    private static final String KEY_ID = "id";
    private static final String KEY_NUMERO_TOMBO = "numerotombo";
    private static final String KEY_QUANTIDADE = "quantidade";
    private static final String[] COLUMNS = {KEY_ID, KEY_AUTOR, KEY_CIDADE, KEY_ID_MATERIAL, KEY_CUTTER, KEY_NUMERO_TOMBO, KEY_QUANTIDADE};
    private Context con;
    public SQLiteLivro(Context context){
        con = context;
        banco = new MySQLiteHelper(context);
    }

    public void reiniciaTabelaMensagem(SQLiteDatabase db){

        String DELETE_TABLE = "DELETE FROM livro";
        String DELETE_INDEX_TABLE = " DELETE FROM SQLITE_SEQUENCE WHERE NAME = 'livro'";

        db.execSQL(DELETE_TABLE);
        db.execSQL(DELETE_INDEX_TABLE);
        banco.onCreate(db);
    }

    public long addLivro(Livro livro){
        Log.d("addLivro", livro.getClassificacao());
        // 1. get reference to writable DB

        SQLiteMaterial dbMaterial = new SQLiteMaterial(con);
        SQLiteDatabase db = banco.getWritableDatabase();
        ContentValues values = new ContentValues();
        Material material = new Material();

        material.setAno(livro.getAno());
        material.setClassificacao(livro.getClassificacao());
        material.setEditora(livro.getEditora());
        material.setLocal(livro.getLocal());
        material.setPagina(livro.getPagina());
        material.setReferencia(livro.getReferencia());
        material.setUnitermo(livro.getUnitermo());
        material.setVolume(livro.getVolume());
        material.setUrl(livro.getUrl());
        dbMaterial.addMaterial(material);

        values.put(KEY_AUTOR, livro.getAutor());
        values.put(KEY_CIDADE, livro.getCidade());
        values.put(KEY_CUTTER, livro.getCutter());
        values.put(KEY_NUMERO_TOMBO, livro.getNumeroTombo());
        values.put(KEY_QUANTIDADE, livro.getQuantidade());

        // 3. insert
        long i = 0;
        i = db.insert(TABLE_LIVRO, // table
                null, //nullColumnHack
                values); // key/value -> keys = column names/ values = column values

        // 4. close
        db.close();
        return i;
    }

    public Livro getLivro(int id){

        // 1. get reference to readable DB
        Livro livro = new Livro();
        SQLiteMaterial dbMaterial = new SQLiteMaterial(con);
        SQLiteDatabase db = banco.getReadableDatabase();

        // 2. build query
        Cursor cursor =
                db.query(TABLE_LIVRO, // a. table
                        COLUMNS, // b. column names
                        " id = ?", // c. selections
                        new String[] { String.valueOf(id) }, // d. selections args
                        null, // e. group by
                        null, // f. having
                        null, // g. order by
                        null); // h. limit

        // 3. if we got results get the first one
        if (cursor != null)
            cursor.moveToFirst();

        // 4. build book object
        livro.setCodigoMaterial(Integer.parseInt(cursor.getString(0)));
        livro.setAno(cursor.getString(1));
        livro.setClassificacao(cursor.getString(2));
        livro.setEditora(cursor.getString(3));
        livro.setLocal(cursor.getString(4));
        livro.setPagina(cursor.getString(5));
        livro.setReferencia(cursor.getString(6));
        livro.setUnitermo(cursor.getString(7));
        livro.setVolume(cursor.getString(8));
        livro.setUrl(cursor.getString(9));

        livro.setCodigoLivro(Integer.parseInt(cursor.getString(10)));
        livro.setAutor(cursor.getString(11));
        livro.setCidade(cursor.getString(12));
        livro.setCutter(cursor.getString(13));
        livro.setNumeroTombo(cursor.getString(14));
        livro.setQuantidade(cursor.getString(15));

        Log.d("getLivro("+id+")", livro.toString());
        return livro;
    }

    public ArrayList<Livro> getAllLivro() {

        ArrayList<Livro> listaLivros = new ArrayList<Livro>();

        // 1. build the query
        String query = "SELECT  * FROM " + TABLE_LIVRO;

        // 2. get reference to writable DB
        SQLiteDatabase db = banco.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        // 3. go over each row, build book and add it to list
        Livro livro = null;
        if (cursor.moveToFirst()) {
            do {
                livro = new Livro();
                livro.setCodigoMaterial(Integer.parseInt(cursor.getString(0)));
                livro.setAno(cursor.getString(1));
                livro.setClassificacao(cursor.getString(2));
                livro.setEditora(cursor.getString(3));
                livro.setLocal(cursor.getString(4));
                livro.setPagina(cursor.getString(5));
                livro.setReferencia(cursor.getString(6));
                livro.setUnitermo(cursor.getString(7));
                livro.setVolume(cursor.getString(8));
                livro.setUrl(cursor.getString(9));

                livro.setCodigoLivro(Integer.parseInt(cursor.getString(10)));
                livro.setAutor(cursor.getString(11));
                livro.setCidade(cursor.getString(12));
                livro.setCutter(cursor.getString(13));
                livro.setNumeroTombo(cursor.getString(14));
                livro.setQuantidade(cursor.getString(15));

                listaLivros.add(livro);
            } while (cursor.moveToNext());
        }

        Log.d("getAllLivro()", listaLivros.toString());
        return listaLivros;
    }
}
