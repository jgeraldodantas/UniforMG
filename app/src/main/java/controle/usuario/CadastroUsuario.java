package controle.usuario;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.uniformg.uniformg.R;

import modelo.SQLiteUsuario;
import objeto.Usuario;

import modelo.MySQLiteHelper;

public class CadastroUsuario extends AppCompatActivity {

    Button btCadUsuario;
    EditText etCodMatricula,etNome,etSenha,etCurso;
 //   MySQLiteHelper banco = new MySQLiteHelper(this);
 SQLiteUsuario banco = new SQLiteUsuario(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_usuario);

        btCadUsuario = (Button) findViewById(R.id.btCadUsuario);
        etNome = (EditText) findViewById(R.id.etNome);
        etCodMatricula = (EditText) findViewById(R.id.etCodMatricula);
        etSenha = (EditText) findViewById(R.id.etSenha);
        etCurso = (EditText) findViewById(R.id.etCurso);

        btCadUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            Usuario usuario = new Usuario();

            usuario.setCodigoMatricula(String.valueOf(etCodMatricula.getText().toString()));
            usuario.setNome(etNome.getText().toString());
            usuario.setSenha(etSenha.getText().toString());
            usuario.setCurso(etCurso.getText().toString());
            usuario.setId(0);

            banco.addUsuario(usuario);
            finish();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_cadastro_usuario, menu);
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
