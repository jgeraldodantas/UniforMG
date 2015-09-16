package controle.transicao;

import android.content.Context;
import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.uniformg.uniformg.R;

import controle.material.PesquisaIndexSIAB;
import controle.mensagem.ListaMensagem;
import modelo.SQLiteMensagem;
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
    Context context;
    TextView tvUsuario;
    ImageButton ibtConfiguracao,ibtAddUser,ibtPesqAcervoGnuteca,ibtPesqIndexSIAB,ibtMensagens;
 //   MySQLiteHelper banco = new MySQLiteHelper(this);
    SQLiteUsuario bancoUSER = new SQLiteUsuario(this);
    SQLiteMensagem bancoMSG = new SQLiteMensagem(this);
    WebService web;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        tvUsuario = (TextView) findViewById(R.id.tvUsuario);
        if(verificaUsuario()){

        //    ibtAddUser = (ImageButton) findViewById(R.id.ibtAddUsuario);
            ibtConfiguracao = (ImageButton) findViewById(R.id.ibtConfiguracao);
            ibtPesqAcervoGnuteca = (ImageButton) findViewById(R.id.ibtPesquisaAcervoGnuteca);
            ibtPesqIndexSIAB = (ImageButton) findViewById(R.id.ibtPesquisaIndexSIAB);
            ibtMensagens = (ImageButton) findViewById(R.id.ibtMensagens);

            if (bancoMSG.getAllMensagens().isEmpty()){
                ListaMensagem msg = new ListaMensagem();
                msg.buscaMensagens(this);
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

            ibtPesqAcervoGnuteca.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    telaPesqAcervo(v);
                }

            });

            ibtPesqIndexSIAB.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    telaPesqIndexSIAB(v);
                }

            });

            ibtMensagens.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(Principal.this,ListaMensagem.class));
                }

            });

        }
        else{
            View v = new View(this);
            telaAutenticacao(v);
        }

    }

    public void telaAutenticacao(View view){
        Intent it_principal = new Intent(this,Autenticacao.class);
        startActivity(it_principal);
        finish();
    }

    public void telaAddUsuarios(View view){
        Intent it_principal = new Intent(this,CadastroUsuario.class);
        startActivity(it_principal);
    }

    public void telaConfiguracoes(View view){
        Intent it_principal = new Intent(this,Configuracoes.class);
        startActivity(it_principal);
    }

    public void telaListaMensagem(View view){
        Intent it_principal = new Intent(this,ListaMensagem.class);
        startActivity(it_principal);
    }

    public void telaPesqAcervo(View view){
        Intent it_principal = new Intent(this,PesquisaAcervoGnuteca.class);
        startActivity(it_principal);
    }

    public void telaPesqIndexSIAB(View view){
        Intent it_principal = new Intent(this,PesquisaIndexSIAB.class);
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

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
