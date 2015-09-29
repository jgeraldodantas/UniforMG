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
    private static final String TABLE_MATERIAL = "material";
    private static final String KEY_ANO = "ano";
    private static final String KEY_CLASSIFICACAO = "classificacao";
    private static final String KEY_EDITORA = "editora";
    private static final String KEY_ID = "id";
    private static final String KEY_LOCAL = "local";
    private static final String KEY_PAGINA = "pagina";
    private static final String KEY_REFERENCIA = "referencia";
    private static final String KEY_VOLUME = "volume";
    private static final String KEY_UNITERMO = "UNITERMO";
    private static final String KEY_URL = "url";
    private static final String KEY_CODIGO_MATERIAL = "codmaterial";
    private static final String[] COLUMNS = {KEY_ID, KEY_ANO, KEY_CLASSIFICACAO, KEY_EDITORA, KEY_LOCAL, KEY_PAGINA, KEY_REFERENCIA, KEY_VOLUME, KEY_UNITERMO, KEY_URL, KEY_CODIGO_MATERIAL};


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


        values.put(KEY_ID, material.getCodigoLivro());
        values.put(KEY_ANO, material.getAno());
        values.put(KEY_CLASSIFICACAO, material.getClassificacao());
        values.put(KEY_EDITORA, material.getEditora());
        values.put(KEY_LOCAL, material.getLocal());
        values.put(KEY_PAGINA, material.getPagina());
        values.put(KEY_REFERENCIA, material.getReferencia());
        values.put(KEY_UNITERMO, material.getUnitermo());
        values.put(KEY_VOLUME, material.getVolume());
        values.put(KEY_URL, material.getUrl());

        /**
         private static final String KEY_ANO = "ano";
         private static final String KEY_CLASSIFICACAO = "classificacao";
         private static final String KEY_EDITORA = "editora";
         private static final String KEY_ID = "id";
         private static final String KEY_LOCAL = "local";
         private static final String KEY_PAGINA = "pagina";
         private static final String KEY_REFERENCIA = "referencia";
         private static final String KEY_VOLUME = "volume";
         private static final String KEY_UNITERMO = "UNITERMO";
         private static final String KEY_URL = "url";
         private static final String KEY_CODIGO_MATERIAL = "codmaterial";

         */
        /

        // 3. insert
        long i = 0;
        i = db.insert(TABLE_MATERIAL, // table
                null, //nullColumnHack
                values); // key/value -> keys = column names/ values = column values

        // 4. close
        db.close();
        return i;
    }

    public Mensagem getMensagem(int id){

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
        Mensagem msg = new Mensagem();
        msg.setCodigo(Integer.parseInt(cursor.getString(0)));
        msg.setMensagem(cursor.getString(1));
        msg.setDataInicio(cursor.getString(2));
        msg.setDataFim(cursor.getString(3));
        msg.setVisivel(cursor.getString(4));
        msg.setTipo(cursor.getString(5));
        msg.setHorario(cursor.getString(6));

        Log.d("getMensagem("+id+")", msg.toString());
        return msg;
    }

    public ArrayList<Mensagem> getAllMensagens() {
        //List<Usuario> usuarios = new LinkedList<Usuario>();
        ArrayList<Mensagem> listaMSG = new ArrayList<Mensagem>();

        // 1. build the query
        String query = "SELECT  * FROM " + TABLE_MATERIAL;

        // 2. get reference to writable DB
        SQLiteDatabase db = banco.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        // 3. go over each row, build book and add it to list
        Mensagem msg = null;
        if (cursor.moveToFirst()) {
            do {
                msg = new Mensagem();
                msg.setCodigo(Integer.parseInt(cursor.getString(0)));
                msg.setMensagem(cursor.getString(1));
                msg.setDataInicio(cursor.getString(2));
                msg.setDataFim(cursor.getString(3));
                msg.setVisivel(cursor.getString(4));
                msg.setTipo(cursor.getString(5));
                msg.setHorario(cursor.getString(6));

                listaMSG.add(msg);
            } while (cursor.moveToNext());
        }

        Log.d("getAllMSG()", listaMSG.toString());
        return listaMSG;
    }

    // Updating single mensagem
    public int updateMensagem(Mensagem msg) {

        // 1. get reference to writable DB
        SQLiteDatabase db = banco.getWritableDatabase();

        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.put("mensagem", msg.getMensagem());
        values.put("dataInicio", msg.getDataInicio());
        values.put("dataFim", msg.getDataFim());
        values.put("visibilidade", msg.getVisivel());
        values.put("tipo", msg.getTipo());
        values.put("horario", msg.getHorario());

        // 3. updating row
        int i = db.update(TABLE_MATERIAL, //table
                values, // column/value
                KEY_ID+" = ?", // selections
                new String[] { String.valueOf(msg.getCodigo()) }); //selection args

        // 4. close
        db.close();
        return i;
    }

    // Deleting single book
    public long deleteMensagem(Mensagem msg) {

        // 1. get reference to writable DB
        SQLiteDatabase db = banco.getWritableDatabase();

        // 2. delete
        long i = 0;
        i = db.delete(TABLE_MATERIAL,
                KEY_ID+" = ?",
                new String[] { String.valueOf(msg.getCodigo()) });

        // 3. close
        db.close();
        Log.d("deleteMensagem", msg.toString());
        return i;
    }

}
