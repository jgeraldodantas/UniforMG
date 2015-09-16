package controle.transicao;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.uniformg.uniformg.R;

import controle.usuario.AlterarSenha;
import controle.usuario.InformacoesUsuario;
import controle.usuario.TrocarUsuario;

public class Configuracoes extends AppCompatActivity {

    Button btAlteraSenha,btTrocarUsuario,btInfoUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuracoes);

        btAlteraSenha = (Button) findViewById(R.id.btAlteraSenha);
        btInfoUsuario = (Button) findViewById(R.id.btInfoUsuario);
        btTrocarUsuario = (Button) findViewById(R.id.btTrocarUsuario);

        btAlteraSenha.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                telaAlteraSenha(v);
            }
        });

        btInfoUsuario.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                telaInfoUsuario(v);
            }
        });

        btTrocarUsuario.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                telaTrocarUsuario(v);
            }
        });
    }

    public void telaAlteraSenha(View view){
        Intent it_config = new Intent(this,AlterarSenha.class);
        startActivity(it_config);
    }

    public void telaInfoUsuario(View view){
        Intent it_config = new Intent(this,InformacoesUsuario.class);
        startActivity(it_config);
    }

    public void telaTrocarUsuario(View view){
        Intent it_config = new Intent(this,TrocarUsuario.class);
        startActivity(it_config);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_configuracoes, menu);
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
