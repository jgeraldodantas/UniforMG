package controle.material;

import android.app.ListActivity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;

import com.example.uniformg.uniformg.R;

import java.util.ArrayList;

import modelo.SQLiteLivro;
import modelo.SQLiteRevista;
import objeto.Livro;
import objeto.Revista;

public class ListaMaterial extends ListActivity {

    ArrayAdapter<String> lista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ArrayList<Integer> codigo;
        String termo = "";
        String exemplares = "";
        String palavraChave = "";
        String[] index = null;
        Intent intent = getIntent();
        Bundle params = intent.getExtras();

        if(params != null){
            termo = params.getString("termo");

            if (termo.contains("Livro")){

                SQLiteLivro banco = new SQLiteLivro(this);
                ArrayList<Livro> listaLivros = new ArrayList<Livro>();

                codigo = new ArrayList<Integer>();
                palavraChave = termo.replace("(Livro) ", "");
                listaLivros = banco.getPesqLivro(palavraChave);

                for (int i = 0; i < listaLivros.size(); i++) {
                    if (codigo.contains(listaLivros.get(i).getCodigoMaterial())){ continue; }
                    else {
                        codigo.add(listaLivros.get(i).getCodigoMaterial());
                        exemplares += "Título: " + listaLivros.get(i).getTitulo()
                                + "\nAutor: " + listaLivros.get(i).getAutor()
                                + "\nEditora: " + listaLivros.get(i).getEditora()
                                + "\nClassificação: " + listaLivros.get(i).getClassificacao() +" "+ listaLivros.get(i).getCutter()
                                + "#";
                    }
                }
                index = exemplares.split("#");
            }
            else{
                SQLiteRevista banco = new SQLiteRevista(this);
                ArrayList<Revista> listaRevistas = new ArrayList<Revista>();

                codigo = new ArrayList<Integer>();
                palavraChave = termo.replace("(Revista) ", "");
                listaRevistas = banco.getPesqRevista(palavraChave);

                for (int i = 0; i < listaRevistas.size(); i++) {
                    if (codigo.contains(listaRevistas.get(i).getCodigoMaterial())){ continue; }
                    else {
                        codigo.add(listaRevistas.get(i).getCodigoMaterial());
                        exemplares += "Título: " + listaRevistas.get(i).getTitulo()
                                + "\nNúmero: " + listaRevistas.get(i).getNumero()
                                + "\nEditora: " + listaRevistas.get(i).getEditora()
                                + "\nClassificação: " + listaRevistas.get(i).getClassificacao()
                                + "#";
                    }
                }
                index = exemplares.split("#");
            }


            this.lista = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, index);
            setListAdapter(this.lista);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_lista_material, menu);
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
