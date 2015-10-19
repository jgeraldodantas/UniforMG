package modelo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import java.util.ArrayList;
import objeto.Mensagem;

/**
 * Created by - on 14/09/2015.
 */
public class SQLiteMensagem {

    private MySQLiteHelper banco;
    private static final String TABLE_MENSAGEM = "mensagem";
    private static final String KEY_ID = "id";
    private static final String KEY_MENSAGEM = "mensagem";
    private static final String KEY_DATA_INICIO = "datainicio";
    private static final String KEY_DATA_FIM = "datafim";
    private static final String KEY_VISIBILIDADE = "visibilidade";
    private static final String KEY_TIPO = "tipo";
    private static final String KEY_HORARIO = "horario";
    private static final String[] COLUMNS = {KEY_ID,KEY_MENSAGEM,KEY_DATA_INICIO,KEY_DATA_FIM,KEY_VISIBILIDADE,KEY_TIPO,KEY_HORARIO};

    public SQLiteMensagem(Context context){
        banco = new MySQLiteHelper(context);
    }

    public void reiniciaTabelaMensagem(SQLiteDatabase db){

        String DELETE_TABLE = "DELETE FROM mensagem";
        String DELETE_INDEX_TABLE = " DELETE FROM SQLITE_SEQUENCE WHERE NAME = 'mensagem'";

        db.execSQL(DELETE_TABLE);
        db.execSQL(DELETE_INDEX_TABLE);
        banco.onCreate(db);
    }

    public long addMensagem(Mensagem msg){
        Log.d("addMensagem", msg.getDataInicio());
        // 1. get reference to writable DB

        SQLiteDatabase db = banco.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(KEY_MENSAGEM, msg.getMensagem());
        values.put(KEY_DATA_INICIO, msg.getDataInicio());
        values.put(KEY_DATA_FIM, msg.getDataFim());
        values.put(KEY_VISIBILIDADE, msg.getVisivel());
        values.put(KEY_TIPO, msg.getTipo());
        values.put(KEY_HORARIO, msg.getHorario());

        // 3. insert
        long i = 0;
        i = db.insert(TABLE_MENSAGEM, // table
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
                db.query(TABLE_MENSAGEM, // a. table
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
        String query = "SELECT  * FROM " + TABLE_MENSAGEM;

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
        int i = db.update(TABLE_MENSAGEM, //table
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
        i = db.delete(TABLE_MENSAGEM,
                KEY_ID+" = ?",
                new String[] { String.valueOf(msg.getCodigo()) });

        // 3. close
        db.close();
        Log.d("deleteMensagem", msg.toString());
        return i;
    }

}
