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
import modelo.SQLiteUsuario;
import modelo.WebService;
import objeto.Livro;
import objeto.Mensagem;
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

                String checkbox = "";
                if(cbLivro.isChecked()){ checkbox += "livro"; }
                if(cbRevista.isChecked()){ checkbox += "revista"; }

                String palavra = "";
                if(etBuscaIndexacao.getText().toString().isEmpty()){palavra = "";}
                else{palavra = etBuscaIndexacao.getText().toString().toLowerCase();}

                Intent intent = new Intent(v.getContext(), ListaIndexacao.class);
                Bundle params = new Bundle();

                params.putString("mensagem", palavra);
                params.putString("checkbox", checkbox);
                intent.putExtras(params);

                if(checkbox.isEmpty()){
                    Toast.makeText(PesquisaIndexSIAB.this, "Marque uma opção de material de consulta.", Toast.LENGTH_LONG).show();
                }
                else{startActivity(intent);}

            }
        });

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
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
