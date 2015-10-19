package modelo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

import objeto.Material;
import objeto.Mensagem;

/**
 * Created by - on 14/09/2015.
 */
public class SQLiteMaterial {

    private MySQLiteHelper banco;
    public static final String TABLE_MATERIAL = "material";
    public static final String KEY_ID = "id";
    public static final String KEY_ANO = "ano";
    public static final String KEY_CLASSIFICACAO = "classificacao";
    public static final String KEY_EDITORA = "editora";
    public static final String KEY_LOCAL = "local";
    public static final String KEY_REFERENCIA = "referencia";
    public static final String KEY_TITULO = "titulo";
    public static final String KEY_UNITERMO = "unitermo";
    public static final String KEY_VOLUME = "volume";
    private static final String[] COLUMNS = {KEY_ID, KEY_ANO, KEY_CLASSIFICACAO, KEY_EDITORA, KEY_LOCAL, KEY_REFERENCIA, KEY_TITULO, KEY_UNITERMO, KEY_VOLUME};

/*
    material.setAno(texto[0]);
    material.setClassificacao(texto[2]);
    material.setEditora(texto[4]);
    material.setLocal(texto[6]);
    material.setReferencia(texto[8]);
    material.setTitulo(texto[9]);
    material.setUnitermo(texto[10]);
    material.setVolume(texto[11]);
*/

    public SQLiteMaterial(Context context){
        banco = new MySQLiteHelper(context);
    }

    public void reiniciaTabelaMensagem(SQLiteDatabase db){

        String DELETE_TABLE = "DELETE FROM material";
        String DELETE_INDEX_TABLE = " DELETE FROM SQLITE_SEQUENCE WHERE NAME = 'material'";

        db.execSQL(DELETE_TABLE);
        db.execSQL(DELETE_INDEX_TABLE);
        banco.onCreate(db);
    }

    public long addMaterial(Material material){
        Log.d("addMaterial", material.getClassificacao());
        // 1. get reference to writable DB

        SQLiteDatabase db = banco.getWritableDatabase();
        ContentValues values = new ContentValues();

//        values.put(KEY_ID, material.getCodigoMaterial());
        values.put(KEY_ANO, material.getAno());
        values.put(KEY_CLASSIFICACAO, material.getClassificacao());
        values.put(KEY_EDITORA, material.getEditora());
        values.put(KEY_LOCAL, material.getLocal());
        values.put(KEY_REFERENCIA, material.getReferencia());
        values.put(KEY_TITULO, material.getTitulo());
        values.put(KEY_UNITERMO, material.getUnitermo());
        values.put(KEY_VOLUME, material.getVolume());

        // 3. insert
        long i = 0;
        i = db.insert(TABLE_MATERIAL, // table
                null, //nullColumnHack
                values); // key/value -> keys = column names/ values = column values

        // 4. close
        db.close();
        return i;
    }
    /*
    public Material getMaterial(int id){

        // 1. get reference to readable DB
        SQLiteDatabase db = banco.getReadableDatabase();

        // 2. build query
        Cursor cursor =
                db.query(TABLE_MATERIAL, // a. table
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
        Material material = new Material();
        material.setCodigoMaterial(Integer.parseInt(cursor.getString(0)));
        material.setAno(cursor.getString(1));
        material.setClassificacao(cursor.getString(2));
        material.setEditora(cursor.getString(3));
        material.setLocal(cursor.getString(4));
        material.setReferencia(cursor.getString(5));
        material.setTitulo(cursor.getString(6));
        material.setUnitermo(cursor.getString(7));
        material.setVolume(cursor.getString(8));

        Log.d("getMaterial(" + id + ")", material.toString());
        return material;
    }
    */
    public Material getUltimoMaterial(){

        // 1. get reference to readable DB
        SQLiteDatabase db = banco.getReadableDatabase();
        String sql = "SELECT * FROM " + TABLE_MATERIAL + " WHERE ID = (SELECT MAX(ID) FROM " + TABLE_MATERIAL + ")";

        // 2. build query
        Cursor cursor =
                db.query(TABLE_MATERIAL, // a. table
                        COLUMNS, // b. column names
                        null, // c. selections
                        null, // d. selections args
                        null, // e. group by
                        null, // f. having
                        null, // g. order by
                        null); // h. limit

        // 3. if we got results get the first one
        if (cursor != null)
            cursor.moveToFirst();

            // 4. build book object
        Material material = new Material();
        material.setCodigoMaterial(Integer.parseInt(cursor.getString(0)));
        material.setAno(cursor.getString(1));
        material.setClassificacao(cursor.getString(2));
        material.setEditora(cursor.getString(3));
        material.setLocal(cursor.getString(4));
        material.setReferencia(cursor.getString(5));
        material.setTitulo(cursor.getString(6));
        material.setUnitermo(cursor.getString(7));
        material.setVolume(cursor.getString(8));

        Log.d("getMaterial(" + material.getCodigoMaterial() + ")", material.toString());
        return material;
    }



    /*
    public ArrayList<Material> getAllMaterial() {

        ArrayList<Material> listaMateriais = new ArrayList<Material>();

        // 1. build the query
        String query = "SELECT  * FROM " + TABLE_MATERIAL;

        // 2. get reference to writable DB
        SQLiteDatabase db = banco.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        // 3. go over each row, build book and add it to list
        Material material = null;
        if (cursor.moveToFirst()) {
            do {
                material = new Material();
                material.setCodigoMaterial(Integer.parseInt(cursor.getString(0)));
                material.setAno(cursor.getString(1));
                material.setClassificacao(cursor.getString(2));
                material.setEditora(cursor.getString(3));
                material.setLocal(cursor.getString(4));
                material.setReferencia(cursor.getString(5));
                material.setTitulo(cursor.getString(6));
                material.setUnitermo(cursor.getString(7));
                material.setVolume(cursor.getString(8));

                listaMateriais.add(material);
            } while (cursor.moveToNext());
        }

        Log.d("getAllMSG()", listaMateriais.toString());
        return listaMateriais;
    }
    */
}
