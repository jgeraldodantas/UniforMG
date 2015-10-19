package controle.transicao;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.uniformg.uniformg.R;

import controle.material.ListaIndexacao;
import controle.material.PesquisaIndexSIAB;
import controle.mensagem.ListaMensagem;
import modelo.SQLiteLivro;
import modelo.SQLiteMensagem;
import modelo.SQLiteRevista;
import modelo.SQLiteUsuario;
import modelo.WebService;
import objeto.Mensagem;
import objeto.Usuario;

import java.util.ArrayList;

import controle.usuario.Autenticacao;
import controle.usuario.CadastroUsuario;
import controle.material.PesquisaAcervoGnuteca;
import modelo.MySQLiteHelper;

public class Principal extends AppCompatActivity {

    TextView tvUsuario;
    Button ibtConfiguracao,ibtAddUser,ibtPesqAcervoGnuteca,ibtPesqIndexSIAB,ibtMensagens;

    Context context = this;
    WebService web;

    SQLiteUsuario bancoUSER = new SQLiteUsuario(context);
    SQLiteMensagem bancoMSG = new SQLiteMensagem(context);
    SQLiteLivro bancoLivro = new SQLiteLivro(context);
    SQLiteRevista bancoRevista = new SQLiteRevista(context);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        tvUsuario = (TextView) findViewById(R.id.tvUsuario);

        if(verificaUsuario()){

            //    ibtAddUser = (ImageButton) findViewById(R.id.ibtAddUsuario);
            ibtConfiguracao = (Button) findViewById(R.id.ibtConfiguracao);
        //    ibtPesqAcervoGnuteca = (Button) findViewById(R.id.ibtPesquisaAcervoGnuteca);
            ibtPesqIndexSIAB = (Button) findViewById(R.id.ibtPesquisaIndexSIAB);
            ibtMensagens = (Button) findViewById(R.id.ibtMensagens);

            ListaMensagem msg = new ListaMensagem();
            PesquisaIndexSIAB indexLivro = new PesquisaIndexSIAB();
            PesquisaIndexSIAB indexRevista = new PesquisaIndexSIAB();

            if (bancoMSG.getAllMensagens().isEmpty()){ msg.buscaMensagensWebService(context); }
            if (bancoLivro.getAllLivro().isEmpty()){ indexLivro.buscaLivrosWebService(context); }
            if (bancoRevista.getAllRevista().isEmpty()){indexRevista.buscaRevistasWebService(context); }

            ArrayList<Mensagem> listaMSG = bancoMSG.getAllMensagens();
            if(!listaMSG.isEmpty()){
                String texto = new String();
                for (int i=0; i<listaMSG.size();i++){
                    texto = new String();
                    texto = listaMSG.get(i).getCodigo().toString() +" "+ listaMSG.get(i).getMensagem();
                    exibirMensagem(texto);
                }
            }

        /*
            ibtAddUser.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    telaAddUsuarios(v);
                }
            });
        */
            ibtConfiguracao.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    telaConfiguracoes(v);
                }

            });
    /*
            ibtPesqAcervoGnuteca.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    telaPesqAcervo(v);
                }

            });
    */
            ibtPesqIndexSIAB.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    telaPesqIndexSIAB(v);
                }

            });

            ibtMensagens.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(context,ListaMensagem.class));
                }

            });

        }
        else{
            View v = new View(this);
            telaAutenticacao(v);
        }

    }

    public void exibirMensagem(final String mensagem){

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Atenção!");
        builder.setMessage(mensagem)
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                })
                .setNegativeButton("", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                }).show();

    }

    public void telaAutenticacao(View view){
        Intent it_principal = new Intent(context,Autenticacao.class);
        startActivity(it_principal);
        finish();
    }

    public void telaAddUsuarios(View view){
        Intent it_principal = new Intent(context,CadastroUsuario.class);
        startActivity(it_principal);
    }

    public void telaConfiguracoes(View view){
        Intent it_principal = new Intent(context,Configuracoes.class);
        startActivity(it_principal);
    }

    public void telaListaMensagem(View view){
        Intent it_principal = new Intent(context,ListaMensagem.class);
        startActivity(it_principal);
    }

    public void telaPesqAcervo(View view){
        Intent it_principal = new Intent(context,PesquisaAcervoGnuteca.class);
        startActivity(it_principal);
    }

    public void telaPesqIndexSIAB(View view){
        Intent it_principal = new Intent(context,PesquisaIndexSIAB.class);
        startActivity(it_principal);
    }

    public boolean verificaUsuario(){
        ArrayList<Usuario> usuario = new ArrayList<Usuario>();
        usuario = bancoUSER.getAllUsers();

        if(!usuario.isEmpty()){
            //    Toast.makeText(Principal.this, "Bem vindo ! \n"+usuario.get(0).getNome(), Toast.LENGTH_SHORT).show();
            tvUsuario.setText(usuario.get(0).getNome());
            return true;
        }
        else{return false;}
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_principal, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        return super.onOptionsItemSelected(item);
    }
}
