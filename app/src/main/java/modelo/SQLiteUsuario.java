package modelo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import java.util.ArrayList;
import objeto.Usuario;

/**
 * Created by - on 14/09/2015.
 */
public class SQLiteUsuario {

    private MySQLiteHelper banco;
    private static final String TABLE_USUARIO = "usuario";
    private static final String KEY_ID = "id";
    private static final String KEY_MATRICULA = "matricula";
    private static final String KEY_NOME = "nome";
    private static final String KEY_CURSO = "curso";
    private static final String KEY_SENHA = "senha";
    private static final String[] COLUMNS = {KEY_ID,KEY_MATRICULA,KEY_NOME,KEY_CURSO,KEY_SENHA};

    public SQLiteUsuario(Context context){
        banco = new MySQLiteHelper(context);
    }

    public void reiniciaTabelaUsuario(SQLiteDatabase db){

        String DELETE_TABLE = "DELETE FROM usuario";
        String DELETE_INDEX_TABLE = " DELETE FROM SQLITE_SEQUENCE WHERE NAME = 'usuario'";

        db.execSQL(DELETE_TABLE);
        db.execSQL(DELETE_INDEX_TABLE);
        banco.onCreate(db);
    }

    public long addUsuario(Usuario usuario){
        Log.d("addUsuario", usuario.toString());
        // 1. get reference to writable DB
        SQLiteDatabase db = banco.getWritableDatabase();

        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.put(KEY_MATRICULA, usuario.getCodigoMatricula());
        values.put(KEY_NOME, usuario.getNome());
        values.put(KEY_CURSO, usuario.getCurso());
        values.put(KEY_SENHA, usuario.getSenha());

        // 3. insert
        long i = 0;
        i = db.insert(TABLE_USUARIO, // table
                null, //nullColumnHack
                values); // key/value -> keys = column names/ values = column values

        // 4. close
        db.close();
        return i;
    }

    public Usuario getUsuario(int id){

        // 1. get reference to readable DB
        SQLiteDatabase db = banco.getReadableDatabase();

        // 2. build query
        Cursor cursor =
                db.query(TABLE_USUARIO, // a. table
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
        Usuario usuario = new Usuario();
        usuario.setId(Integer.parseInt(cursor.getString(0)));
        usuario.setCodigoMatricula(cursor.getString(1));
        usuario.setNome(cursor.getString(2));
        usuario.setCurso(cursor.getString(3));
        usuario.setSenha(cursor.getString(4));

        Log.d("getUser("+id+")", usuario.toString());
        return usuario;
    }

    public ArrayList<Usuario> getAllUsers() {
        //List<Usuario> usuarios = new LinkedList<Usuario>();
        ArrayList<Usuario> usuarios = new ArrayList<Usuario>();

        // 1. build the query
        String query = "SELECT  * FROM " + TABLE_USUARIO;

        // 2. get reference to writable DB
        SQLiteDatabase db = banco.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        // 3. go over each row, build book and add it to list
        Usuario usuario = null;
        if (cursor.moveToFirst()) {
            do {
                usuario = new Usuario();
                usuario.setId(Integer.parseInt(cursor.getString(0)));
                usuario.setCodigoMatricula(cursor.getString(1));
                usuario.setNome(cursor.getString(2));
                usuario.setCurso(cursor.getString(3));
                usuario.setSenha(cursor.getString(4));

                usuarios.add(usuario);
            } while (cursor.moveToNext());
        }

        Log.d("getAllUsers()", usuarios.toString());

        // return books
        return usuarios;
    }

    // Updating single user
    public int updateUsuario(Usuario usuario) {

        // 1. get reference to writable DB
        SQLiteDatabase db = banco.getWritableDatabase();

        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.put("matricula", usuario.getCodigoMatricula().toString());
        values.put("nome", usuario.getNome().toString());
        values.put("curso", usuario.getCurso().toString());
        values.put("senha", usuario.getSenha().toString());

        // 3. updating row
        int i = db.update(TABLE_USUARIO, //table
                values, // column/value
                KEY_MATRICULA+" = ?", // selections
                new String[] { String.valueOf(usuario.getCodigoMatricula()) }); //selection args

        // 4. close
        db.close();
        return i;
    }

    // Deleting single book
    public long deleteUsuario(Usuario usuario) {

        // 1. get reference to writable DB
        SQLiteDatabase db = banco.getWritableDatabase();

        // 2. delete
        long i = 0;
        i = db.delete(TABLE_USUARIO,
                KEY_ID+" = ?",
                new String[] { String.valueOf(usuario.getId()) });

        // 3. close
        db.close();
        Log.d("deleteUser", usuario.toString());
        return i;
    }

}
