package modelo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

import objeto.Livro;
import objeto.Material;

/**
 * Created by - on 14/09/2015.
 */
public class SQLiteLivro {

    private Context con;
    private MySQLiteHelper banco;
    public static SQLiteMaterial sqlLiteMaterial;
    public static final String TABLE_LIVRO = "livro";
    public static final String KEY_ID = "id";
    public static final String KEY_ID_MATERIAL = "codmaterial";
    public static final String KEY_AUTOR = "autor";
    public static final String KEY_CUTTER = "cutter";
    public static final String KEY_ISBN = "isbn";
    public static final String KEY_NUMERO_TOMBO = "numerotombo";
    private static final String[] COLUMNS = {KEY_ID, KEY_ID_MATERIAL, KEY_AUTOR, KEY_CUTTER, KEY_ISBN, KEY_NUMERO_TOMBO};

    private int idLivro;
    private int autor;
    private int cutter;
    private int isbn;
    private int numeroTombo;

    private int idMaterial;
    private int ano;
    private int classificacao;
    private int editora;
    private int local;
    private int referencia;
    private int titulo;
    private int unitermo;
    private int volume;

    private static final String SQL_SELECT = "SELECT DISTINCT ("+ sqlLiteMaterial.TABLE_MATERIAL + "." + sqlLiteMaterial.KEY_ID +"), " +
            sqlLiteMaterial.TABLE_MATERIAL + "." + sqlLiteMaterial.KEY_ID + ", " +
            sqlLiteMaterial.TABLE_MATERIAL + "." + sqlLiteMaterial.KEY_ANO + ", "+
            sqlLiteMaterial.TABLE_MATERIAL + "." + sqlLiteMaterial.KEY_CLASSIFICACAO + ", " +
            sqlLiteMaterial.TABLE_MATERIAL + "." + sqlLiteMaterial.KEY_EDITORA + ", " +
            sqlLiteMaterial.TABLE_MATERIAL + "." + sqlLiteMaterial.KEY_LOCAL + ", " +
            sqlLiteMaterial.TABLE_MATERIAL + "." + sqlLiteMaterial.KEY_REFERENCIA + ", " +
            sqlLiteMaterial.TABLE_MATERIAL + "." + sqlLiteMaterial.KEY_TITULO + ", " +
            sqlLiteMaterial.TABLE_MATERIAL + "." + sqlLiteMaterial.KEY_UNITERMO + ", " +
            sqlLiteMaterial.TABLE_MATERIAL + "." + sqlLiteMaterial.KEY_VOLUME + "," +
            TABLE_LIVRO + "." +  KEY_AUTOR + ", "+
            TABLE_LIVRO + "." +  KEY_CUTTER + ", "+
            TABLE_LIVRO + "." +  KEY_ISBN + ", "+
            TABLE_LIVRO + "." +  KEY_NUMERO_TOMBO +
            " FROM " + sqlLiteMaterial.TABLE_MATERIAL + "," + TABLE_LIVRO +
            " WHERE " + sqlLiteMaterial.TABLE_MATERIAL + "." + sqlLiteMaterial.KEY_ID +" = " + TABLE_LIVRO+"."+KEY_ID_MATERIAL;

    private static final String SQL_GROUP_BY = " GROUP BY " +
            sqlLiteMaterial.TABLE_MATERIAL + "." + sqlLiteMaterial.KEY_ID + ", " +
            sqlLiteMaterial.TABLE_MATERIAL + "." + sqlLiteMaterial.KEY_ANO + ", "+
            sqlLiteMaterial.TABLE_MATERIAL + "." + sqlLiteMaterial.KEY_CLASSIFICACAO + ", " +
            sqlLiteMaterial.TABLE_MATERIAL + "." + sqlLiteMaterial.KEY_EDITORA + ", " +
            sqlLiteMaterial.TABLE_MATERIAL + "." + sqlLiteMaterial.KEY_LOCAL + ", " +
            sqlLiteMaterial.TABLE_MATERIAL + "." + sqlLiteMaterial.KEY_REFERENCIA + ", " +
            sqlLiteMaterial.TABLE_MATERIAL + "." + sqlLiteMaterial.KEY_TITULO + ", " +
            sqlLiteMaterial.TABLE_MATERIAL + "." + sqlLiteMaterial.KEY_UNITERMO + ", " +
            sqlLiteMaterial.TABLE_MATERIAL + "." + sqlLiteMaterial.KEY_VOLUME + "," +
            TABLE_LIVRO + "." +  KEY_AUTOR + ", "+
            TABLE_LIVRO + "." +  KEY_CUTTER + ", "+
            TABLE_LIVRO + "." +  KEY_ISBN + ", "+
            TABLE_LIVRO + "." +  KEY_NUMERO_TOMBO +";";

    /*
        livro.setAutor(texto[1]);
        livro.setCutter(texto[3]);
        livro.setIsbn(texto[5]);
        livro.setNumeroTombo(texto[7]);
    */

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
        long i = 0;

        material.setAno(livro.getAno());
        material.setClassificacao(livro.getClassificacao());
        material.setEditora(livro.getEditora());
        material.setLocal(livro.getLocal());
        material.setReferencia(livro.getReferencia());
        material.setTitulo(livro.getTitulo());
        material.setUnitermo(livro.getUnitermo());
        material.setVolume(livro.getVolume());

        if (dbMaterial.addMaterial(material) > 0){

            material = new Material();
            material = dbMaterial.getUltimoMaterial();
            if(material.getCodigoMaterial() > 0) {

                values.put(KEY_ID_MATERIAL, material.getCodigoMaterial());
                values.put(KEY_AUTOR, livro.getAutor());
                values.put(KEY_CUTTER, livro.getCutter());
                values.put(KEY_ISBN, livro.getIsbn());
                values.put(KEY_NUMERO_TOMBO, livro.getNumeroTombo());

                // 3. insert
                i = db.insert(TABLE_LIVRO, // table
                        null, //nullColumnHack
                        values); // key/value -> keys = column names/ values = column values

                // 4. close
                db.close();
            }
        }
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

        idLivro = cursor.getColumnIndex(KEY_ID);
        autor = cursor.getColumnIndex(KEY_AUTOR);
        cutter = cursor.getColumnIndex(KEY_CUTTER);
        isbn = cursor.getColumnIndex(KEY_ISBN);
        numeroTombo = cursor.getColumnIndex(KEY_NUMERO_TOMBO);

        idMaterial = cursor.getColumnIndex(sqlLiteMaterial.KEY_ID);
        ano = cursor.getColumnIndex(sqlLiteMaterial.KEY_ANO);
        classificacao = cursor.getColumnIndex(sqlLiteMaterial.KEY_CLASSIFICACAO);
        editora = cursor.getColumnIndex(sqlLiteMaterial.KEY_EDITORA);
        local = cursor.getColumnIndex(sqlLiteMaterial.KEY_LOCAL);
        referencia = cursor.getColumnIndex(sqlLiteMaterial.KEY_REFERENCIA);
        titulo = cursor.getColumnIndex(sqlLiteMaterial.KEY_TITULO);
        unitermo = cursor.getColumnIndex(sqlLiteMaterial.KEY_UNITERMO);
        volume = cursor.getColumnIndex(sqlLiteMaterial.KEY_VOLUME);

        // 4. tabela material
        livro = new Livro();
        livro.setCodigoMaterial(Integer.parseInt(cursor.getString(idMaterial)));
        livro.setAno(cursor.getString(ano));
        livro.setClassificacao(cursor.getString(classificacao));
        livro.setEditora(cursor.getString(editora));
        livro.setLocal(cursor.getString(local));
        livro.setReferencia(cursor.getString(referencia));
        livro.setTitulo(cursor.getString(titulo));
        livro.setUnitermo(cursor.getString(unitermo));
        livro.setVolume(cursor.getString(volume));

        livro.setCodigoLivro(Integer.parseInt(cursor.getString(idLivro)));
        livro.setAutor(cursor.getString(autor));
        livro.setCutter(cursor.getString(cutter));
        livro.setIsbn(cursor.getString(isbn));
        livro.setNumeroTombo(cursor.getString(numeroTombo));

        Log.d("getLivro(" + id + ")", livro.toString());
        return livro;
    }

    public ArrayList<Livro> getPesqLivro(String termo) {

        ArrayList<Livro> listaLivros = new ArrayList<Livro>();
        SQLiteMaterial sqlMaterial;// = new SQLiteMaterial(con);
        // 1. build the query//

        String query = SQL_SELECT + " and material.unitermo like '%" + termo + "%'" + SQL_GROUP_BY;

        // 2. get reference to writable DB
        SQLiteDatabase db = banco.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        // 3. go over each row, build book and add it to list
        Livro livro = null;
        if (cursor.moveToFirst()) {
            do {

                idLivro = cursor.getColumnIndex(KEY_ID);
                autor = cursor.getColumnIndex(KEY_AUTOR);
                cutter = cursor.getColumnIndex(KEY_CUTTER);
                isbn = cursor.getColumnIndex(KEY_ISBN);
                numeroTombo = cursor.getColumnIndex(KEY_NUMERO_TOMBO);

                idMaterial = cursor.getColumnIndex(sqlLiteMaterial.KEY_ID);
                ano = cursor.getColumnIndex(sqlLiteMaterial.KEY_ANO);
                classificacao = cursor.getColumnIndex(sqlLiteMaterial.KEY_CLASSIFICACAO);
                editora = cursor.getColumnIndex(sqlLiteMaterial.KEY_EDITORA);
                local = cursor.getColumnIndex(sqlLiteMaterial.KEY_LOCAL);
                referencia = cursor.getColumnIndex(sqlLiteMaterial.KEY_REFERENCIA);
                titulo = cursor.getColumnIndex(sqlLiteMaterial.KEY_TITULO);
                unitermo = cursor.getColumnIndex(sqlLiteMaterial.KEY_UNITERMO);
                volume = cursor.getColumnIndex(sqlLiteMaterial.KEY_VOLUME);

                // 4. tabela material
                livro = new Livro();
                livro.setCodigoMaterial(Integer.parseInt(cursor.getString(idMaterial)));
                livro.setAno(cursor.getString(ano));
                livro.setClassificacao(cursor.getString(classificacao));
                livro.setEditora(cursor.getString(editora));
                livro.setLocal(cursor.getString(local));
                livro.setReferencia(cursor.getString(referencia));
                livro.setTitulo(cursor.getString(titulo));
                livro.setUnitermo(cursor.getString(unitermo));
                livro.setVolume(cursor.getString(volume));

                livro.setCodigoLivro(Integer.parseInt(cursor.getString(idLivro)));
                livro.setAutor(cursor.getString(autor));
                livro.setCutter(cursor.getString(cutter));
                livro.setIsbn(cursor.getString(isbn));
                livro.setNumeroTombo(cursor.getString(numeroTombo));

                listaLivros.add(livro);
            } while (cursor.moveToNext());
        }

        Log.d("getAllLivro()", listaLivros.toString());
        return listaLivros;
    }


    public ArrayList<Livro> getAllLivro() {

        ArrayList<Livro> listaLivros = new ArrayList<Livro>();
        SQLiteMaterial sqlMaterial;// = new SQLiteMaterial(con);
        // 1. build the query//

        String query = SQL_SELECT + SQL_GROUP_BY;

        // 2. get reference to writable DB
        SQLiteDatabase db = banco.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        // 3. go over each row, build book and add it to list
        Livro livro = null;
        if (cursor.moveToFirst()) {
            do {

                livro = new Livro();
                idLivro = cursor.getColumnIndex(KEY_ID);
                autor = cursor.getColumnIndex(KEY_AUTOR);
                cutter = cursor.getColumnIndex(KEY_CUTTER);
                isbn = cursor.getColumnIndex(KEY_ISBN);
                numeroTombo = cursor.getColumnIndex(KEY_NUMERO_TOMBO);

                idMaterial = cursor.getColumnIndex(sqlLiteMaterial.KEY_ID);
                ano = cursor.getColumnIndex(sqlLiteMaterial.KEY_ANO);
                classificacao = cursor.getColumnIndex(sqlLiteMaterial.KEY_CLASSIFICACAO);
                editora = cursor.getColumnIndex(sqlLiteMaterial.KEY_EDITORA);
                local = cursor.getColumnIndex(sqlLiteMaterial.KEY_LOCAL);
                referencia = cursor.getColumnIndex(sqlLiteMaterial.KEY_REFERENCIA);
                titulo = cursor.getColumnIndex(sqlLiteMaterial.KEY_TITULO);
                unitermo = cursor.getColumnIndex(sqlLiteMaterial.KEY_UNITERMO);
                volume = cursor.getColumnIndex(sqlLiteMaterial.KEY_VOLUME);

                // 4. tabela material
                livro.setCodigoMaterial(Integer.parseInt(cursor.getString(idMaterial)));
                livro.setAno(cursor.getString(ano));
                livro.setClassificacao(cursor.getString(classificacao));
                livro.setEditora(cursor.getString(editora));
                livro.setLocal(cursor.getString(local));
                livro.setReferencia(cursor.getString(referencia));
                livro.setTitulo(cursor.getString(titulo));
                livro.setUnitermo(cursor.getString(unitermo));
                livro.setVolume(cursor.getString(volume));

                livro.setCodigoLivro(Integer.parseInt(cursor.getString(idLivro)));
                livro.setAutor(cursor.getString(autor));
                livro.setCutter(cursor.getString(cutter));
                livro.setIsbn(cursor.getString(isbn));
                livro.setNumeroTombo(cursor.getString(numeroTombo));

                listaLivros.add(livro);
            } while (cursor.moveToNext());
        }

        Log.d("getAllLivro()", listaLivros.toString());
        return listaLivros;
    }

}
