package controle.usuario;

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

import com.example.uniformg.uniformg.R;

import modelo.SQLiteUsuario;
import objeto.Usuario;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;

import modelo.WebService;

public class AlterarSenha extends AppCompatActivity {

    WebService web;
 //   MySQLiteHelper banco = new MySQLiteHelper(this);
    SQLiteUsuario banco = new SQLiteUsuario(this);
    EditText etSenhaAtual,etNovaSenha,etConfirmaSenha;
    TextView tvErro;
    Button btAlterar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alterar_senha);

    //    tvErro = (TextView) findViewById(R.id.error);
        btAlterar = (Button)findViewById(R.id.btAlterarSenha);
        etSenhaAtual = (EditText)findViewById(R.id.etSenhaAtual);
        etNovaSenha = (EditText)findViewById(R.id.etNovaSenha);
        etConfirmaSenha = (EditText)findViewById(R.id.etConfirmaSenha);

        btAlterar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

              //  StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
              //  StrictMode.setThreadPolicy(policy);

                ArrayList<Usuario> user = new ArrayList<Usuario>();
                user = banco.getAllUsers();
                Usuario usuario = new Usuario();
                Autenticacao login = new Autenticacao();
            //    tvErro.setText("");
                try {
                    if ((!etSenhaAtual.getText().toString().isEmpty()) &&
                            (!etNovaSenha.getText().toString().isEmpty()) &&
                            (!etConfirmaSenha.getText().toString().isEmpty())) {
                        if (etNovaSenha.getText().toString().equals(etConfirmaSenha.getText().toString()) &&
                            etSenhaAtual.getText().toString().equals(user.get(0).getSenha().toString()) &&
                           (!(etNovaSenha.getText().toString().equals(etSenhaAtual.getText().toString())))) {
                        //    if(etNovaSenha.length() <= 8) {
                                usuario = new Usuario();
                                usuario = login.autentica(user.get(0).getCodigoMatricula().toString(),etSenhaAtual.getText().toString(),1);
                           //     usuario = importaUsuarioGnuteca(user.get(0).getCodigoMatricula().toString());
                                if (alteraSenha(usuario)) {Toast.makeText(AlterarSenha.this, "Senha alterada", Toast.LENGTH_SHORT).show();}
                                else {Toast.makeText(AlterarSenha.this, "Evento 1 \nFalha ao alterar a senha.\nVerifique sua senha e tente novamente.", Toast.LENGTH_SHORT).show(); }

                        //    }else { Toast.makeText(AlterarSenha.this, "Evento 4 \nA senha deve conter no máximo 08 dígitos", Toast.LENGTH_SHORT).show();}
                        } else {Toast.makeText(AlterarSenha.this, "Evento 2 \nFalha ao alterar a senha.\nVerifique sua senha e tente novamente.", Toast.LENGTH_SHORT).show();}
                    } else { Toast.makeText(AlterarSenha.this, "Evento 3 \nInforme todos os campos", Toast.LENGTH_SHORT).show(); }
                }catch(Exception erro){Toast.makeText(AlterarSenha.this, "Evento 4 \nFalha ao alterar a senha.\nVerifique os dados informados e sua conexao de internet.", Toast.LENGTH_SHORT).show();}
            /*
                tvErro.setText(   tvErro.getText()+
                                "\nAtual: " + etSenhaAtual.getText().toString() +
                                "\nSQLite: " + user.get(0).getSenha().toString() +
                                "\nNova Senha: " + etNovaSenha.getText().toString() +
                                "\nConfirma: " + etConfirmaSenha.getText().toString() +
                                "\nMatricula: " + user.get(0).getCodigoMatricula().toString());
            */
                etNovaSenha.setText("");
                etSenhaAtual.setText("");
                etConfirmaSenha.setText("");
            }
        });
    }

    public Usuario importaUsuarioGnuteca(String matricula){
        Usuario usuario = new Usuario();
        //importa a senha do banco gnuteca para conferir se está de acordo entre o banco SQLite e o informado pelo usuário
        try {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);

            Log.i("logar", "entrou no evento");
            String urlPost = web.URL +web.AUTENTICACAO;
            String urlGet  = web.URL +web.AUTENTICACAO+"?matricula=" + matricula + "&senha=" + etSenhaAtual.getText().toString();
            ArrayList<NameValuePair> parametrosPost = new ArrayList<NameValuePair>();
            parametrosPost.add(new BasicNameValuePair("matricula", matricula.toString()));
            parametrosPost.add(new BasicNameValuePair("senha", etSenhaAtual.getText().toString()));
            String respostaRetornada = null;
            Log.i("logar", "vai entrar no try");
            try {
                respostaRetornada = WebService.executaHttpPost(urlPost, parametrosPost);
                //respostaRetornada = WebService.executaHttpGet(urlGet);
            //    tvErro.setText(respostaRetornada);
                String resposta = respostaRetornada.toString();
                Log.i("logar", "resposta = " + resposta);
                //    resposta = resposta.replaceAll("\\s+", "");

                if (resposta.contains("true")) {
                    usuario = new Usuario();
                    String[] retorno = resposta.split("-");
                    usuario.setCurso(retorno[0]);
                    usuario.setCodigoMatricula(retorno[1]);
                    usuario.setNome(retorno[2]);
                    usuario.setSenha(retorno[3]);
                    usuario.setId(0);

                    //    compara a senha do gnuteca com o informado pelo usuario
                    if(etSenhaAtual.getText().toString().equals(usuario.getSenha())){
                        return usuario;
                    }
                    else{Toast.makeText(AlterarSenha.this, "Importar 1\nFalha ao alterar a senha.\nVerifique sua senha e tente novamente.", Toast.LENGTH_SHORT).show();}
                } else { Toast.makeText(AlterarSenha.this, "Importar 2\nAcesso negado.anVerfique seus dados de acesso.", Toast.LENGTH_LONG).show(); }
            } catch (Exception erro) {
                Log.i("erro", "erro = "+erro);
                Toast.makeText(AlterarSenha.this, "Importar 3\nFalha de autenticação\nVerifique sua conexão de internet e dados de acesso.", Toast.LENGTH_LONG).show();
            }
        }catch(Exception erro){ Toast.makeText(AlterarSenha.this, "Importar 4\nFalha de autenticação\nVerifique sua conexão de internet e dados de acesso.", Toast.LENGTH_LONG).show(); }
        return usuario;
    }


    public boolean alteraSenha(Usuario user){
        //altera a senha no gnuteca
        boolean alteracao = false;
        try {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            String resp = "teste";
            Log.i("logar", "entrou no evento");
            String urlPost = web.URL + web.ALTERA_SENHA;
            String urlGet  = web.URL + web.ALTERA_SENHA+"?matricula=" + Integer.parseInt(user.getCodigoMatricula().toString()) + "&senha=" + etNovaSenha.getText().toString() + "&nome=" + user.getNome().toString();
            ArrayList<NameValuePair> parametrosPost = new ArrayList<NameValuePair>();
            parametrosPost.add(new BasicNameValuePair("matricula", user.getCodigoMatricula().toString()));
            parametrosPost.add(new BasicNameValuePair("senha", etNovaSenha.getText().toString()));
            parametrosPost.add(new BasicNameValuePair("nome", user.getNome().toString()));
            String respostaRetornada = null;
            Log.i("logar", "vai entrar no try");
            try {
                respostaRetornada = WebService.executaHttpPost(urlPost, parametrosPost);
                //respostaRetornada = WebService.executaHttpGet(urlGet);
                String resposta = respostaRetornada.toString();
            //    resp = resposta;
              //  Log.i("logar", "resposta = " + resposta);
                int salvaSQLite = 0;
                if (resposta.contains("true")) {
                    Usuario usuario = new Usuario();
                    String[] retorno = resposta.split("-");
                    usuario.setId(0);
                    usuario.setCurso(retorno[0]);
                    usuario.setCodigoMatricula(retorno[1]);
                    usuario.setNome(retorno[2]);
                    usuario.setSenha(retorno[3]);
                    String salvaPostgre = retorno[4];
                    salvaSQLite = banco.updateUsuario(usuario);

                    if ((salvaSQLite > 0) && (salvaPostgre.contains("true"))) { alteracao = true;}
                    else { alteracao = false;}
                } else {Toast.makeText(AlterarSenha.this, "Altera 1 \nAcesso negado.\nVerfique seus dados de acesso.", Toast.LENGTH_LONG).show();}

            } catch (Exception erro) {
                Log.i("erro", "erro = "+erro);
                Toast.makeText(AlterarSenha.this, "Altera 2 \nFalha de autenticação\nVerifique sua conexão de internet e dados de acesso.\n" + urlGet, Toast.LENGTH_LONG).show();
            }
        }
        catch(Exception erro){
            Toast.makeText(AlterarSenha.this, "Altera 3 \nFalha de autenticação\nVerifique sua conexão de internet e dados de acesso.", Toast.LENGTH_LONG).show();
        }
        return alteracao;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_alterar_senha, menu);
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
