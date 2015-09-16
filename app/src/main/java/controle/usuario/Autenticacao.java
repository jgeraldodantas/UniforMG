package controle.usuario;

import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import controle.transicao.Principal;
import com.example.uniformg.uniformg.R;

import modelo.SQLiteUsuario;
import objeto.Usuario;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;

import modelo.MySQLiteHelper;
import modelo.WebService;

public class Autenticacao extends AppCompatActivity {

    WebService web;
  //  MySQLiteHelper banco = new MySQLiteHelper(this);
    SQLiteUsuario banco = new SQLiteUsuario(this);
    Button btCancelar,btEnviar;
    EditText etMatricula,etSenha;
    TextView tvErro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_autenticacao);

        etMatricula = (EditText) findViewById(R.id.etCodMatriculaLogin);
        etSenha = (EditText) findViewById(R.id.etSenhaLogin);
        btCancelar = (Button) findViewById(R.id.btCancelar);
        btEnviar = (Button) findViewById(R.id.btAutenticar);
    //    tvErro = (TextView) findViewById(R.id.tvErro);

        btCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(autentica(etMatricula.getText().toString(),etSenha.getText().toString(),0) != null){
                    finish();
                    telaPrincipal(v);
                }
            }
        });
    }

    public Usuario autentica(String matricula, String senha, int acao){
        Usuario usuario = null;

        try {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);

            Log.i("logar", "entrou no evento");
            String urlPost = web.URL+web.AUTENTICACAO;
            String urlGet  = web.URL+web.AUTENTICACAO+"?matricula=" + Integer.parseInt(matricula) + "&senha=" + senha;
            ArrayList<NameValuePair> parametrosPost = new ArrayList<NameValuePair>();
            parametrosPost.add(new BasicNameValuePair("matricula", matricula));
            parametrosPost.add(new BasicNameValuePair("senha", senha));
            String respostaRetornada = null;
            Log.i("logar", "vai entrar no try");
            try {
                respostaRetornada = WebService.executaHttpPost(urlPost, parametrosPost);
                //    respostaRetornada = WebService.executaHttpGet(urlGet);
                String resposta = respostaRetornada.toString();
                //    Log.i("logar", "resposta = " + resposta);
                //    resposta = resposta.replaceAll("\\s+", "");

                if (resposta.contains("true")) {
                    //    tvErro.setText(resposta.toString());
                    //    Toast.makeText(Autenticacao.this, "Usuario válido", Toast.LENGTH_LONG).show();
                    usuario = new Usuario();
                    String[] user = resposta.split("-");
                    usuario.setCurso(user[0]);
                    usuario.setCodigoMatricula(user[1]);
                    usuario.setNome(user[2]);
                    usuario.setSenha(user[3]);
                    usuario.setId(0);
                    if(acao == 0){banco.addUsuario(usuario);}
                } else {
                    usuario = null;
                    Toast.makeText(Autenticacao.this, "Acesso negado.\nVerfique seus dados de acesso.", Toast.LENGTH_LONG).show();
                }
            } catch (Exception erro) {
                tvErro.setText(erro.toString());
                Log.i("erro", "erro = "+erro);
                Toast.makeText(Autenticacao.this, "Falha de autenticação\nVerifique sua conexão de internet e dados de acesso.", Toast.LENGTH_LONG).show();
            }
        }
        catch(Exception erro){
            Toast.makeText(Autenticacao.this, "Falha no servidor.", Toast.LENGTH_LONG).show();
        }
        return usuario;
    }

    public void telaPrincipal(View view){
        Intent it_principal = new Intent(this,Principal.class);
        startActivity(it_principal);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_autenticacao, menu);
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
