package controle.material;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.uniformg.uniformg.R;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;

import controle.mensagem.ListaMensagem;
import modelo.SQLiteLivro;
import modelo.SQLiteMensagem;
import modelo.SQLiteRevista;
import modelo.SQLiteUsuario;
import modelo.WebService;
import objeto.Livro;
import objeto.Mensagem;
import objeto.Revista;
import objeto.Usuario;

public class PesquisaIndexSIAB extends AppCompatActivity {

 //   SQLiteUsuario bancoUsuario = new SQLiteUsuario(this);
 //   SQLiteLivro bancoLivro = new SQLiteLivro(this);
    ArrayAdapter<String> listaLivros;
    WebService web;
    String texto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pesquisa_index_siab);

        final Context context = this;
        final EditText etBuscaIndexacao = (EditText) findViewById(R.id.etBuscaIndexacao);
        final CheckBox cbLivro = (CheckBox) findViewById(R.id.cbLivro);
        final CheckBox cbRevista = (CheckBox) findViewById(R.id.cbRevista);
        ImageButton ibBusca = (ImageButton) findViewById(R.id.ibBusca);

        ibBusca.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String termos = "";
                String[] indexLivro;
                String[] indexRevista;
                ArrayList<String> listaIndex = new ArrayList<String>();

                if (cbLivro.isChecked() && (!etBuscaIndexacao.getText().toString().isEmpty())) {
                    indexLivro = buscaIndexLivro(PesquisaIndexSIAB.this, etBuscaIndexacao.getText().toString());

                    if (indexLivro != null) {
                        for (int i = 0; i < indexLivro.length; i++) {
                            listaIndex.add("(Livro) " + indexLivro[i]);
                        }
                    }
                }

                if (cbRevista.isChecked() && (!etBuscaIndexacao.getText().toString().isEmpty())) {
                    indexRevista = buscaIndexRevista(PesquisaIndexSIAB.this, etBuscaIndexacao.getText().toString());

                    if (indexRevista != null) {
                        for (int i = 0; i < indexRevista.length; i++) {
                            listaIndex.add("(Revista) " + indexRevista[i]);
                        }
                    }
                }

                if (listaIndex.size() > 0) {
                    for (int i = 0; i < listaIndex.size(); i++) {
                        termos += listaIndex.get(i) + "#";
                    }

                    Intent intent = new Intent(v.getContext(), ListaIndexacao.class);
                    Bundle params = new Bundle();

                    params.putString("termos", termos);
                    intent.putExtras(params);
                    startActivity(intent);
                } else {
                    Toast.makeText(PesquisaIndexSIAB.this, "Material não encontrado.", Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    public void buscaLivrosWebService(Context context){

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        WebService web = new WebService();
        SQLiteUsuario bancoUsuario = new SQLiteUsuario(context);
        SQLiteLivro banco = new SQLiteLivro(context);
        ArrayList<NameValuePair> parametrosPost = new ArrayList<NameValuePair>();
        ArrayList<Usuario> usuario = new ArrayList<Usuario>();
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

            int cont = 0;
            livros = resposta.split("#");
            for(int i=0;i<livros.length-1;i++){
                livro = new Livro();
                texto = livros[i].split("-");

                livro.setAno(texto[0]);
                livro.setAutor(texto[1]);           //livro
                livro.setClassificacao(texto[2]);
                livro.setCutter(texto[3]);          //livro
                livro.setEditora(texto[4]);
                livro.setIsbn(texto[5]);            //livro
                livro.setLocal(texto[6]);
                livro.setNumeroTombo(texto[7]);     //livro
                livro.setReferencia(texto[8]);
                livro.setTitulo(texto[9]);
                livro.setUnitermo(texto[10]);
                livro.setVolume(texto[11]);

                banco.addLivro(livro);
            }
        }
        catch(Exception erro){ Log.i("erroLivro", "erroLivro = "+erro); }
    }

    public void buscaRevistasWebService(Context context){

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        WebService web = new WebService();
        SQLiteUsuario bancoUsuario = new SQLiteUsuario(context);
        SQLiteRevista banco = new SQLiteRevista(context);

        ArrayList<NameValuePair> parametrosPost = new ArrayList<NameValuePair>();
        ArrayList<Usuario> usuario = new ArrayList<Usuario>();
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

            int cont = 0;
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

                banco.addRevista(revista);
                cont++;
            }
        }
        catch(Exception erro){ Log.i("erroRevista", "erroRevista = "+erro); }
    }

    public String[] buscaIndexLivro(Context context,String palavra) {
        SQLiteLivro banco = new SQLiteLivro(context);
        ArrayList<Livro> acervo = banco.getPesqLivro(palavra);

        String texto = "";
        String[] unitermos = null;
        String[] listaUnitermos = null;
        ArrayList<Integer> codigo = new ArrayList<Integer>();

        if(acervo.isEmpty()){ finish(); }
        else {

            for (int i = 0; i < acervo.size(); i++) {
                if((acervo.get(i).getUnitermo().toLowerCase().contains(palavra.toLowerCase()))){
                    unitermos = acervo.get(i).getUnitermo().split("%");
                    if (codigo.contains(acervo.get(i).getCodigoMaterial())){ continue; }
                    else {

                        codigo.add(acervo.get(i).getCodigoMaterial());
                        for (int j = 0; j < unitermos.length; j++) {
                            if (unitermos[j].toLowerCase().contains(palavra.toLowerCase())) {
                                texto += unitermos[j] + "#";
                            }
                        }
                        unitermos = null;
                    }
                }
                else{ unitermos = null; }
            }
        }
        if(!texto.isEmpty()){listaUnitermos = texto.split("#");}
        else {listaUnitermos = null;}
        return listaUnitermos;
    }


    public String[] buscaIndexRevista(Context context,String palavra) {
        SQLiteRevista banco = new SQLiteRevista(context);
        ArrayList<Revista> acervo = banco.getPesqRevista(palavra);

        String texto = "";
        String[] unitermos = null;
        String[] listaUnitermos = null;
        ArrayList<Integer> codigo = new ArrayList<Integer>();

        if(acervo.isEmpty()){ finish(); }
        else {
            codigo = new ArrayList<Integer>();
            for (int i = 0; i < acervo.size(); i++) {
                if((acervo.get(i).getUnitermo().toLowerCase().contains(palavra.toLowerCase()))){
                    unitermos = acervo.get(i).getUnitermo().split("%");
                    if (codigo.contains(acervo.get(i).getCodigoMaterial())){ continue; }
                    else {
                        codigo.add(acervo.get(i).getCodigoMaterial());
                        for (int j = 0; j < unitermos.length; j++) {
                            if (unitermos[j].toLowerCase().contains(palavra.toLowerCase())) {
                                texto += unitermos[j] + "#";
                            }
                        }
                        unitermos = null;
                    }
                }
                else{ unitermos = null; }
            }
        }

        if(!texto.isEmpty()){listaUnitermos = texto.split("#");}
        else {listaUnitermos = null;}
        return listaUnitermos;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_pesquisa_index, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement


        return super.onOptionsItemSelected(item);
    }


}
