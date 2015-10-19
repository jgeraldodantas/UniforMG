package controle.material;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.StrictMode;
import android.support.v4.text.TextUtilsCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.StringBuilderPrinter;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.uniformg.uniformg.R;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.StringTokenizer;

import modelo.SQLiteRevista;
import modelo.SQLiteLivro;
import modelo.SQLiteUsuario;
import modelo.WebService;
import objeto.Livro;
import objeto.Revista;
import objeto.Usuario;

public class ListaIndexacao extends ListActivity {

    ArrayAdapter<String> lista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String termos = "";
        String[] index = null;
        Intent intent = getIntent();
        Bundle params = intent.getExtras();

        if(params != null){
                termos = params.getString("termos");
                index = termos.split("#");

                this.lista = new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, index);
                setListAdapter(this.lista);
            }
        }

    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        Object objetoSelecionado = this.getListAdapter().getItem(position);
        String index = objetoSelecionado.toString();

        Intent intent = new Intent(this, ListaMaterial.class);
        Bundle params = new Bundle();

        params.putString("termo", index);
        intent.putExtras(params);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_lista_indexacao, menu);
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
