package controle.material;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.StrictMode;
import android.support.v4.text.TextUtilsCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.StringBuilderPrinter;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.uniformg.uniformg.R;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;

import modelo.SQLiteRevista;
import modelo.SQLiteLivro;
import modelo.SQLiteUsuario;
import modelo.WebService;
import objeto.Livro;
import objeto.Revista;
import objeto.Usuario;

public class ListaIndexacao extends ListActivity {

//    SQLiteUsuario bancoUsuario = new SQLiteUsuario(this);
    SQLiteLivro bancoLivro = new SQLiteLivro(this);
    ArrayAdapter<String> lista;
    ArrayAdapter<String> listaLivros;
    WebService web;
    String texto = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ArrayList<String> lista = new ArrayList<String>();
        String termos = "";
        String[] index = {};
        String[] indexLivro = {};
        String[] indexRevista = {};
        Intent intent = getIntent();
        Bundle params = intent.getExtras();

        if(params != null){
            String palavra = params.getString("mensagem");
            String checkbox = params.getString("checkbox");

            if(checkbox.contains("livro")){ indexLivro = buscaIndexLivro(this,palavra); }
            if(checkbox.contains("revista")){ indexRevista = buscaIndexRevista(this,palavra); }

            for(int i=0; i<indexLivro.length;i++ ){ lista.add("(Livro) "+indexLivro[i]); }
            for(int i=0; i<indexRevista.length;i++) { lista.add("(Revista) " + indexRevista[i]); }
            for(int i=0; i<lista.size();i++ ){ termos += lista.get(i)+"#"; }

            index = termos.split("#");
            listaIndex(index);
        }

    }

    public void listaIndex(String[] index){
    //    if((listaUnitermos == null) || (listaUnitermos[0].toString().length() < 3)){
        if((index == null) || (index.length < 1)){
            Toast.makeText(ListaIndexacao.this, "Nenhuma palavra-chave encontrada.: "+index.length, Toast.LENGTH_LONG).show(); finish();
        }
        else {
            this.lista = new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, index);
            setListAdapter(this.lista);
        }
    }

    public ArrayList<Livro> buscaLivrosWebService(Context context){

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        SQLiteUsuario bancoUsuario = new SQLiteUsuario(context);

        ArrayList<NameValuePair> parametrosPost = new ArrayList<NameValuePair>();
        ArrayList<Usuario> usuario = new ArrayList<Usuario>();
        ArrayList<Livro> acervo = new ArrayList<Livro>();
    //    SQLiteLivro banco = new SQLiteLivro(context);
        Livro livro = new Livro();

        usuario = bancoUsuario.getAllUsers();
        String[] livros;
        String[] texto;

        Log.i("logar", "entrou no evento " + usuario.get(0).getCurso());
        String url = web.URL+web.IMPORTA_LIVRO_SIAB;
        parametrosPost.add(new BasicNameValuePair("curso", usuario.get(0).getCurso()));

        String respostaRetornada;
        Log.i("logar", "vai entrar no try livro");
        try {
            respostaRetornada = web.executaHttpPost(url, parametrosPost);
            String resposta = respostaRetornada.toString();
            Log.i("livro teste 45", "" + resposta);

            livros = resposta.split("#");
            for(int i=0;i<livros.length-1;i++){
                livro = new Livro();
                texto = livros[i].split("-");

                livro.setAno(texto[0]);
                livro.setAutor(texto[1]);
                livro.setClassificacao(texto[2]);
                livro.setCutter(texto[3]);
                livro.setEditora(texto[4]);
                livro.setIsbn(texto[5]);
                livro.setLocal(texto[6]);
                livro.setNumeroTombo(texto[7]);
                livro.setReferencia(texto[8]);
                livro.setTitulo(texto[9]);
                livro.setUnitermo(texto[10]);
                livro.setVolume(texto[11]);

                acervo.add(livro);
                //    banco.addLivro(livro);
            }
        }
        catch(Exception erro){ Log.i("erroLivro", "erroLivro = "+erro); }
        return acervo;
    }


    public ArrayList<Revista> buscaRevistasWebService(Context context){

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        SQLiteUsuario bancoUsuario = new SQLiteUsuario(context);

        ArrayList<NameValuePair> parametrosPost = new ArrayList<NameValuePair>();
        ArrayList<Usuario> usuario = new ArrayList<Usuario>();
        ArrayList<Revista> acervo = new ArrayList<Revista>();
        //  SQLiteLivro banco = new SQLiteRevista(context);
        Revista revista = new Revista();

        usuario = bancoUsuario.getAllUsers();
        String[] revistas;
        String[] texto;

        Log.i("logar", "entrou no evento " + usuario.get(0).getCurso());
        String url = web.URL+web.IMPORTA_REVISTA_SIAB;
        parametrosPost.add(new BasicNameValuePair("curso", usuario.get(0).getCurso()));

        String respostaRetornada;
        Log.i("logar", "vai entrar no try revista");
        try {
            respostaRetornada = web.executaHttpPost(url, parametrosPost);
            String resposta = respostaRetornada.toString();
            Log.i("revista teste 45", "" + resposta);

            revistas = resposta.split("#");
            for(int i=0;i < revistas.length-1;i++){
                revista = new Revista();
                texto = revistas[i].split("=");

                revista.setAno(texto[0]);
                revista.setAnoFim(texto[1]);
                revista.setAnoInicio(texto[2]);
                revista.setClassificacao(texto[3]);
                revista.setColecao(texto[4]);
                revista.setCorrente(texto[5]);
                revista.setEditora(texto[6]);
                revista.setIssn(texto[7]);
                revista.setLocal(texto[8]);
                revista.setMesFim(texto[9]);
                revista.setMesInicio(texto[10]);
                revista.setNumero(texto[11]);
                revista.setPeriodicidade(texto[12]);
                revista.setReferencia(texto[13]);
                revista.setTitulo(texto[14]);
                revista.setUnitermo(texto[15]);
                revista.setVolume(texto[16]);

                acervo.add(revista);
                //    banco.addLivro(livro);
            }
        }
        catch(Exception erro){ Log.i("erroRevista", "erroRevista = "+erro); }
        return acervo;
    }


    public String[] buscaIndexLivro(Context context,String palavra) {
        //    SQLiteLivro banco = new SQLiteLivro(context);
        //    ArrayList<Livro> acervo = banco.getAllLivro();
        ArrayList<Livro> acervo = buscaLivrosWebService(context);
        String[] unitermos;
        String[] listaUnitermos;

        if(acervo.isEmpty()){
            finish(); }
        //    Toast.makeText(ListaIndexacao.this, "Não há acervo para ser consultado.", Toast.LENGTH_LONG).show(); }
        else {

            for (int i = 0; i <= acervo.size() - 1; i++) {
                if((acervo.get(i).getUnitermo().toLowerCase().contains(palavra))){
                    unitermos = acervo.get(i).getUnitermo().split("%");

                    for(int j=0; j < unitermos.length;j++){
                        if(unitermos[j].toLowerCase().contains(palavra)){
                            texto += unitermos[j] + "#";
                        }
                    }
                }
                else{unitermos = null; }
                unitermos = null;
            }
        }
        listaUnitermos = texto.split("#");
        return listaUnitermos;
    }


    public String[] buscaIndexRevista(Context context,String palavra) {
        //    SQLiteRevista banco = new SQLiteRevista(context);
        //    ArrayList<Revista> acervo = banco.getAllRevista();
        ArrayList<Revista> acervo = buscaRevistasWebService(context);
        String[] unitermos;
        String[] listaUnitermos;

        if(acervo.isEmpty()){
            finish(); }
        //    Toast.makeText(ListaIndexacao.this, "Não há acervo para ser consultado.", Toast.LENGTH_LONG).show(); }
        else {

            for (int i = 0; i <= acervo.size() - 1; i++) {
                if((acervo.get(i).getUnitermo().toLowerCase().contains(palavra))){
                    unitermos = acervo.get(i).getUnitermo().split("%");

                    for(int j=0; j < unitermos.length;j++){
                        if(unitermos[j].toLowerCase().contains(palavra)){
                            texto += unitermos[j] + "#";
                        }
                    }
                }
                else{unitermos = null; }
                unitermos = null;
            }
        }
        listaUnitermos = texto.split("#");
        return listaUnitermos;
    }

/*
    public void listarUnitermos(Context context,String palavra, String checkbox) {
        //    SQLiteLivro banco = new SQLiteLivro(context);
        //    ArrayList<Livro> acervo = banco.getAllLivro();
        PesquisaIndexSIAB pesquisa = new PesquisaIndexSIAB();
        ArrayList<Livro> acervo = buscaLivrosWebService(context);
        String[] unitermos;
        String[] listaUnitermos = null;

        if(acervo.isEmpty()){
            finish();
            Toast.makeText(ListaIndexacao.this, "Não há acervo para ser consultado listarUnitermos.", Toast.LENGTH_LONG).show(); }
        else {

            for (int i = 0; i <= acervo.size() - 1; i++) {
                if((acervo.get(i).getUnitermo().toLowerCase().contains(palavra))){
                    unitermos = acervo.get(i).getUnitermo().split("%");

                    for(int j=0; j < unitermos.length;j++){
                        if(unitermos[j].toLowerCase().contains(palavra)){
                            texto += unitermos[j] + "#";
                        }
                    }
                }
                else{unitermos = null; }
                unitermos = null;
            }
        }

        listaUnitermos = texto.split("#");
        if((listaUnitermos == null) || (listaUnitermos[0].toString().length() < 3)){
            Toast.makeText(ListaIndexacao.this, "Nenhuma palavra-chave encontrada.: "+listaUnitermos[0].toString(), Toast.LENGTH_LONG).show(); finish();
        }
        else {
            this.listaLivros = new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, listaUnitermos);
            setListAdapter(this.listaLivros);
        }
    }
*/

    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        Object objetoSelecionado = this.getListAdapter().getItem(position);
        String index = objetoSelecionado.toString();
        exibirIndex(index);
    }

    public void exibirIndex(String index){

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_lista_indexacao, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
