package modelo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

import objeto.Material;
import objeto.Revista;

/**
 * Created by - on 14/09/2015.
 */
public class SQLiteRevista {

    private Context con;
    private MySQLiteHelper banco;
    public static SQLiteMaterial sqlLiteMaterial;
    public static final String TABLE_REVISTA = "revista";
    public static final String KEY_ID = "id";
    public static final String KEY_ID_MATERIAL = "codmaterial";
    public static final String KEY_ANO_FIM = "anofim";
    public static final String KEY_ANO_INICIO = "anoinicio";
    public static final String KEY_COLECAO = "colecao";
    public static final String KEY_CORRENTE = "corrente";
    public static final String KEY_ISSN = "issn";
    public static final String KEY_MES_FIM = "mesfim";
    public static final String KEY_MES_INICIO = "mesinicio";
    public static final String KEY_NUMERO = "numero";
    public static final String KEY_PERIODICIDADE = "periodicidade";
    private static final String[] COLUMNS = {KEY_ID,KEY_ID_MATERIAL,KEY_ANO_FIM,KEY_ANO_INICIO, KEY_COLECAO,KEY_CORRENTE,KEY_ISSN,KEY_MES_FIM,KEY_MES_INICIO,KEY_NUMERO,KEY_PERIODICIDADE};

    private int idRevista;
    private int anoFim;
    private int anoInicio;
    private int colecao;
    private int corrente;
    private int issn;
    private int mesFim;
    private int meInicio;
    private int numero;
    private int periodicidade;

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
            TABLE_REVISTA + "." +  KEY_ID + ", "+
            TABLE_REVISTA + "." +  KEY_ANO_FIM + ", "+
            TABLE_REVISTA + "." +  KEY_ANO_INICIO + ", "+
            TABLE_REVISTA + "." +  KEY_COLECAO + ", "+
            TABLE_REVISTA + "." +  KEY_CORRENTE + ", "+
            TABLE_REVISTA + "." +  KEY_ISSN + ", "+
            TABLE_REVISTA + "." +  KEY_MES_FIM + ", "+
            TABLE_REVISTA + "." +  KEY_MES_INICIO + ", "+
            TABLE_REVISTA + "." +  KEY_NUMERO + ", "+
            TABLE_REVISTA + "." +  KEY_PERIODICIDADE +
            " FROM " + sqlLiteMaterial.TABLE_MATERIAL + "," + TABLE_REVISTA +
            " WHERE " + sqlLiteMaterial.TABLE_MATERIAL + "." + sqlLiteMaterial.KEY_ID +" = " + TABLE_REVISTA+"."+KEY_ID_MATERIAL;

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
            TABLE_REVISTA + "." +  KEY_ID + ", "+
            TABLE_REVISTA + "." +  KEY_ANO_FIM + ", "+
            TABLE_REVISTA + "." +  KEY_ANO_INICIO + ", "+
            TABLE_REVISTA + "." +  KEY_COLECAO + ", "+
            TABLE_REVISTA + "." +  KEY_CORRENTE + ", "+
            TABLE_REVISTA + "." +  KEY_ISSN + ", "+
            TABLE_REVISTA + "." +  KEY_MES_FIM + ", "+
            TABLE_REVISTA + "." +  KEY_MES_INICIO + ", "+
            TABLE_REVISTA + "." +  KEY_NUMERO + ", "+
            TABLE_REVISTA + "." +  KEY_PERIODICIDADE+";";

/*
    revista.setAnoFim(texto[1]);
    revista.setAnoInicio(texto[2]);
    revista.setColecao(texto[4]);
    revista.setCorrente(texto[5]);
    revista.setIssn(texto[7]);
    revista.setMesFim(texto[9]);
    revista.setMesInicio(texto[10]);
    revista.setNumero(texto[11]);
    revista.setPeriodicidade(texto[12]);
*/

    public SQLiteRevista(Context context){
        con = context;
        banco = new MySQLiteHelper(context);
    }

    public void reiniciaTabelaMensagem(SQLiteDatabase db){

        String DELETE_TABLE = "DELETE FROM revista";
        String DELETE_INDEX_TABLE = " DELETE FROM SQLITE_SEQUENCE WHERE NAME = 'revista'";

        db.execSQL(DELETE_TABLE);
        db.execSQL(DELETE_INDEX_TABLE);
        banco.onCreate(db);
    }

    public long addRevista(Revista revista){
        Log.d("addRevista", revista.getClassificacao());
        // 1. get reference to writable DB

        SQLiteMaterial dbMaterial = new SQLiteMaterial(con);
        SQLiteDatabase db = banco.getWritableDatabase();
        ContentValues values = new ContentValues();
        Material material = new Material();
        long i = 0;

        material.setAno(revista.getAno());
        material.setClassificacao(revista.getClassificacao());
        material.setEditora(revista.getEditora());
        material.setLocal(revista.getLocal());
        material.setReferencia(revista.getReferencia());
        material.setTitulo(revista.getTitulo());
        material.setUnitermo(revista.getUnitermo());
        material.setVolume(revista.getVolume());

        if (dbMaterial.addMaterial(material) > 0){

            material = new Material();
            material = dbMaterial.getUltimoMaterial();
            if(material.getCodigoMaterial() > 0) {

                values.put(KEY_ID_MATERIAL, material.getCodigoMaterial());
                values.put(KEY_ANO_FIM, revista.getAnoFim());
                values.put(KEY_ANO_INICIO, revista.getAnoInicio());
                values.put(KEY_COLECAO, revista.getColecao());
                values.put(KEY_CORRENTE, revista.getCorrente());
                values.put(KEY_ISSN, revista.getIssn());
                values.put(KEY_MES_FIM, revista.getMesFim());
                values.put(KEY_MES_INICIO, revista.getMesInicio());
                values.put(KEY_NUMERO, revista.getNumero());
                values.put(KEY_PERIODICIDADE, revista.getPeriodicidade());

                // 3. insert
                i = db.insert(TABLE_REVISTA, // table
                        null, //nullColumnHack
                        values); // key/value -> keys = column names/ values = column values

                // 4. close
                db.close();
            }
        }
        return i;
    }

    public Revista getRevista(int id){

        // 1. get reference to readable DB
        Revista revista = new Revista();
        SQLiteMaterial dbMaterial = new SQLiteMaterial(con);
        SQLiteDatabase db = banco.getReadableDatabase();

        // 2. build query
        Cursor cursor =
                db.query(TABLE_REVISTA, // a. table
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

        idRevista = cursor.getColumnIndex(KEY_ID);
        anoFim = cursor.getColumnIndex(KEY_ANO_FIM);
        anoInicio = cursor.getColumnIndex(KEY_ANO_FIM);
        colecao = cursor.getColumnIndex(KEY_COLECAO);
        corrente = cursor.getColumnIndex(KEY_CORRENTE);
        issn = cursor.getColumnIndex(KEY_ISSN);
        mesFim = cursor.getColumnIndex(KEY_MES_FIM);
        meInicio = cursor.getColumnIndex(KEY_MES_INICIO);
        numero = cursor.getColumnIndex(KEY_NUMERO);
        periodicidade = cursor.getColumnIndex(KEY_PERIODICIDADE);

        idMaterial = cursor.getColumnIndex(sqlLiteMaterial.KEY_ID);
        ano = cursor.getColumnIndex(sqlLiteMaterial.KEY_ANO);
        classificacao = cursor.getColumnIndex(sqlLiteMaterial.KEY_CLASSIFICACAO);
        editora = cursor.getColumnIndex(sqlLiteMaterial.KEY_EDITORA);
        local = cursor.getColumnIndex(sqlLiteMaterial.KEY_LOCAL);
        referencia = cursor.getColumnIndex(sqlLiteMaterial.KEY_REFERENCIA);
        titulo = cursor.getColumnIndex(sqlLiteMaterial.KEY_TITULO);
        unitermo = cursor.getColumnIndex(sqlLiteMaterial.KEY_UNITERMO);
        volume = cursor.getColumnIndex(sqlLiteMaterial.KEY_VOLUME);

        // 4. build book object
        revista.setCodigoMaterial(Integer.parseInt(cursor.getString(idMaterial)));
        revista.setAno(cursor.getString(ano));
        revista.setClassificacao(cursor.getString(classificacao));
        revista.setEditora(cursor.getString(editora));
        revista.setLocal(cursor.getString(local));
        revista.setReferencia(cursor.getString(referencia));
        revista.setTitulo(cursor.getString(titulo));
        revista.setUnitermo(cursor.getString(unitermo));
        revista.setVolume(cursor.getString(volume));

        revista.setAnoFim(cursor.getString(anoFim));
        revista.setAnoInicio(cursor.getString(anoInicio));
        revista.setColecao(cursor.getString(colecao));
        revista.setCorrente(cursor.getString(corrente));
        revista.setIssn(cursor.getString(issn));
        revista.setMesFim(cursor.getString(mesFim));
        revista.setMesInicio(cursor.getString(meInicio));
        revista.setNumero(cursor.getString(numero));
        revista.setPeriodicidade(cursor.getString(periodicidade));

        Log.d("getRevista("+id+")", revista.toString());
        return revista;
    }

    public ArrayList<Revista> getPesqRevista(String termo) {

        ArrayList<Revista> listaRevistas = new ArrayList<Revista>();
        SQLiteMaterial sqlMaterial;// = new SQLiteMaterial(con);
        // 1. build the query

        String query = SQL_SELECT + " and material.unitermo like '%" + termo + "%'" + SQL_GROUP_BY;

        // 2. get reference to writable DB
        SQLiteDatabase db = banco.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        // 3. go over each row, build book and add it to list
        Revista revista = null;
        if (cursor.moveToFirst()) {
            do {
                idRevista = cursor.getColumnIndex(KEY_ID);
                anoFim = cursor.getColumnIndex(KEY_ANO_FIM);
                anoInicio = cursor.getColumnIndex(KEY_ANO_FIM);
                colecao = cursor.getColumnIndex(KEY_COLECAO);
                corrente = cursor.getColumnIndex(KEY_CORRENTE);
                issn = cursor.getColumnIndex(KEY_ISSN);
                mesFim = cursor.getColumnIndex(KEY_MES_FIM);
                meInicio = cursor.getColumnIndex(KEY_MES_INICIO);
                numero = cursor.getColumnIndex(KEY_NUMERO);
                periodicidade = cursor.getColumnIndex(KEY_PERIODICIDADE);

                idMaterial = cursor.getColumnIndex(sqlLiteMaterial.KEY_ID);
                ano = cursor.getColumnIndex(sqlLiteMaterial.KEY_ANO);
                classificacao = cursor.getColumnIndex(sqlLiteMaterial.KEY_CLASSIFICACAO);
                editora = cursor.getColumnIndex(sqlLiteMaterial.KEY_EDITORA);
                local = cursor.getColumnIndex(sqlLiteMaterial.KEY_LOCAL);
                referencia = cursor.getColumnIndex(sqlLiteMaterial.KEY_REFERENCIA);
                titulo = cursor.getColumnIndex(sqlLiteMaterial.KEY_TITULO);
                unitermo = cursor.getColumnIndex(sqlLiteMaterial.KEY_UNITERMO);
                volume = cursor.getColumnIndex(sqlLiteMaterial.KEY_VOLUME);

                // 4. build book object
                revista = new Revista();
                revista.setCodigoMaterial(Integer.parseInt(cursor.getString(idMaterial)));
                revista.setAno(cursor.getString(ano));
                revista.setClassificacao(cursor.getString(classificacao));
                revista.setEditora(cursor.getString(editora));
                revista.setLocal(cursor.getString(local));
                revista.setReferencia(cursor.getString(referencia));
                revista.setTitulo(cursor.getString(titulo));
                revista.setUnitermo(cursor.getString(unitermo));
                revista.setVolume(cursor.getString(volume));

                revista.setAnoFim(cursor.getString(anoFim));
                revista.setAnoInicio(cursor.getString(anoInicio));
                revista.setColecao(cursor.getString(colecao));
                revista.setCorrente(cursor.getString(corrente));
                revista.setIssn(cursor.getString(issn));
                revista.setMesFim(cursor.getString(mesFim));
                revista.setMesInicio(cursor.getString(meInicio));
                revista.setNumero(cursor.getString(numero));
                revista.setPeriodicidade(cursor.getString(periodicidade));

                listaRevistas.add(revista);
            } while (cursor.moveToNext());
        }

        Log.d("getAllRevistas()", listaRevistas.toString());
        return listaRevistas;
    }


    public ArrayList<Revista> getAllRevista() {

        ArrayList<Revista> listaRevistas = new ArrayList<Revista>();
        SQLiteMaterial sqlMaterial;// = new SQLiteMaterial(con);
        // 1. build the query

        String query = SQL_SELECT + SQL_GROUP_BY;

                // 2. get reference to writable DB
        SQLiteDatabase db = banco.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        // 3. go over each row, build book and add it to list
        Revista revista = null;
        if (cursor.moveToFirst()) {
            do {
                idRevista = cursor.getColumnIndex(KEY_ID);
                anoFim = cursor.getColumnIndex(KEY_ANO_FIM);
                anoInicio = cursor.getColumnIndex(KEY_ANO_FIM);
                colecao = cursor.getColumnIndex(KEY_COLECAO);
                corrente = cursor.getColumnIndex(KEY_CORRENTE);
                issn = cursor.getColumnIndex(KEY_ISSN);
                mesFim = cursor.getColumnIndex(KEY_MES_FIM);
                meInicio = cursor.getColumnIndex(KEY_MES_INICIO);
                numero = cursor.getColumnIndex(KEY_NUMERO);
                periodicidade = cursor.getColumnIndex(KEY_PERIODICIDADE);

                idMaterial = cursor.getColumnIndex(sqlLiteMaterial.KEY_ID);
                ano = cursor.getColumnIndex(sqlLiteMaterial.KEY_ANO);
                classificacao = cursor.getColumnIndex(sqlLiteMaterial.KEY_CLASSIFICACAO);
                editora = cursor.getColumnIndex(sqlLiteMaterial.KEY_EDITORA);
                local = cursor.getColumnIndex(sqlLiteMaterial.KEY_LOCAL);
                referencia = cursor.getColumnIndex(sqlLiteMaterial.KEY_REFERENCIA);
                titulo = cursor.getColumnIndex(sqlLiteMaterial.KEY_TITULO);
                unitermo = cursor.getColumnIndex(sqlLiteMaterial.KEY_UNITERMO);
                volume = cursor.getColumnIndex(sqlLiteMaterial.KEY_VOLUME);

                // 4. build book object
                revista = new Revista();
                revista.setCodigoMaterial(Integer.parseInt(cursor.getString(idMaterial)));
                revista.setAno(cursor.getString(ano));
                revista.setClassificacao(cursor.getString(classificacao));
                revista.setEditora(cursor.getString(editora));
                revista.setLocal(cursor.getString(local));
                revista.setReferencia(cursor.getString(referencia));
                revista.setTitulo(cursor.getString(titulo));
                revista.setUnitermo(cursor.getString(unitermo));
                revista.setVolume(cursor.getString(volume));

                revista.setAnoFim(cursor.getString(anoFim));
                revista.setAnoInicio(cursor.getString(anoInicio));
                revista.setColecao(cursor.getString(colecao));
                revista.setCorrente(cursor.getString(corrente));
                revista.setIssn(cursor.getString(issn));
                revista.setMesFim(cursor.getString(mesFim));
                revista.setMesInicio(cursor.getString(meInicio));
                revista.setNumero(cursor.getString(numero));
                revista.setPeriodicidade(cursor.getString(periodicidade));

                listaRevistas.add(revista);
            } while (cursor.moveToNext());
        }

        Log.d("getAllRevistas()", listaRevistas.toString());
        return listaRevistas;
    }

}
