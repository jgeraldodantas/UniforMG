package controle.usuario;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.uniformg.uniformg.R;

import modelo.SQLiteUsuario;
import objeto.Usuario;

import java.util.ArrayList;

import modelo.MySQLiteHelper;
import modelo.WebService;

public class TrocarUsuario extends AppCompatActivity {

    WebService web;
    Button btTrocarUsuario;
    EditText etCodigoMatricula,etSenha;
  //  MySQLiteHelper banco = new MySQLiteHelper(this);
    SQLiteUsuario banco = new SQLiteUsuario(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trocar_usuario);

        btTrocarUsuario = (Button) findViewById(R.id.btAlterarUsuario);
        etCodigoMatricula = (EditText) findViewById(R.id.etCodigoMatricula);
        etSenha = (EditText) findViewById(R.id.etSenha);

        btTrocarUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ArrayList<Usuario> user = new ArrayList<Usuario>();
                user = banco.getAllUsers();
                Usuario usuario= new Usuario();
                Autenticacao login = new Autenticacao();

                if((!etCodigoMatricula.getText().toString().isEmpty()) &&
                   (!etSenha.getText().toString().isEmpty())){
                    if ((!etSenha.getText().toString().equals(user.get(0).getSenha())) &&
                        (!etCodigoMatricula.getText().toString().equals(user.get(0).getCodigoMatricula()))) {

                        usuario = login.autentica(etCodigoMatricula.getText().toString(),etSenha.getText().toString(),1);
                        if((banco.deleteUsuario(user.get(0)) != 0) && (banco.addUsuario(usuario) != 0)){
                            Toast.makeText(TrocarUsuario.this, "Usuario substituído.", Toast.LENGTH_LONG).show();
                            System.exit(0);
                        }
                        else {Toast.makeText(TrocarUsuario.this, "1Acesso negado.anVerfique seus dados de acesso.", Toast.LENGTH_LONG).show();}

                    } else {Toast.makeText(TrocarUsuario.this, "O usuário não foi alterado. \nVerifique seus dados e tente novamente.", Toast.LENGTH_SHORT).show();}
                }
                else{ Toast.makeText(TrocarUsuario.this, "Informe todos os campos", Toast.LENGTH_SHORT).show(); }

                etCodigoMatricula.setText("");
                etSenha.setText("");
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_trocar_usuario, menu);
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
