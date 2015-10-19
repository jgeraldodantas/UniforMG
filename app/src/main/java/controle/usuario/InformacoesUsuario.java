package controle.usuario;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.example.uniformg.uniformg.R;

import modelo.SQLiteUsuario;
import objeto.Usuario;

import modelo.MySQLiteHelper;

public class InformacoesUsuario extends AppCompatActivity {

 //   Button btExcluir;
    TextView tvNome,tvCurso,tvMatricula,tvSenha;
 //   MySQLiteHelper banco = new MySQLiteHelper(this);
    SQLiteUsuario banco = new SQLiteUsuario(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_informacoes_usuario);

        mostraUsuario();
    /*
        btExcluir = (Button) findViewById(R.id.btExcluirUsuario);
        btExcluir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                excluirUsuario();
                finish();
            }
        });
    */
    }

    public ArrayList<Usuario> retornaUsuario(){
        ArrayList<Usuario> listaUsuarios = banco.getAllUsers();
        return listaUsuarios;
    }

    public void mostraUsuario(){
        ArrayList<Usuario> usuario = new ArrayList<Usuario>();
        usuario = banco.getAllUsers();

        tvNome = (TextView) findViewById(R.id.tvNome);
        tvCurso = (TextView) findViewById(R.id.tvCurso);
        tvMatricula = (TextView) findViewById(R.id.tvMatricula);
        tvSenha = (TextView) findViewById(R.id.tvSenha);

        if(!usuario.isEmpty()){
            tvNome.setText("Nome: " + usuario.get(0).getNome());
            tvCurso.setText("Curso: " + usuario.get(0).getCurso());
            tvMatricula.setText("Código de matricula: " + usuario.get(0).getCodigoMatricula());
        //    tvSenha.setText("Senha: " + usuario.get(0).getSenha());
        }
        else{
            tvNome.setText("");
            tvCurso.setText("");
            tvMatricula.setText("");
            Toast.makeText(InformacoesUsuario.this, "Nenhum usuário encontrado ", Toast.LENGTH_SHORT).show();
        }
    }

    public void excluirUsuario(){
        List<Usuario> usuario = banco.getAllUsers();
        if(!usuario.isEmpty()){
            Usuario user = usuario.get(0);
            banco.deleteUsuario(user);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_informacoes_usuario, menu);
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

