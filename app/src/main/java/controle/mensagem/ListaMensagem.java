package controle.mensagem;

import android.app.Activity;
import android.app.Dialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.StrictMode;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.uniformg.uniformg.R;

import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.StringTokenizer;

import modelo.MySQLiteHelper;
import modelo.SQLiteMensagem;
import modelo.WebService;
import objeto.Mensagem;

public class ListaMensagem extends ListActivity {

    TextView tvHorario,tvMensagem,tvData,tvVisivel,tvTipo;
    SQLiteMensagem banco = new SQLiteMensagem(this);
    ArrayAdapter<String> listaMensagens;
    WebService web;
    int posicao=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        listarMensagens(this);
    }

    //    public ArrayList<Mensagem> buscaMensagens(){
    public void buscaMensagensWebService(Context context){

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        SQLiteMensagem banco = new SQLiteMensagem(context);
        ArrayList<Mensagem> listaMSG = new ArrayList<Mensagem>();
        Mensagem msg = new Mensagem();
        String [] mensagens;
        String [] texto;

        Log.i("logar", "entrou no evento");
        String url = web.URL+web.IMPORTA_MSG_SIAB;
        String respostaRetornada = null;
        Log.i("logar", "vai entrar no try");
        try {
            respostaRetornada = web.executaHttpGet(url);
            String resposta = respostaRetornada.toString();
            Log.i("mensagem teste 45", "" + resposta);

            mensagens = resposta.split("#");
            for(int i=0;i<mensagens.length-1;i++){
                msg = new Mensagem();
                texto = mensagens[i].toString().split("%");

                msg.setMensagem(texto[0]);
                msg.setDataInicio(texto[1]);
                msg.setDataFim(texto[2]);
                msg.setVisivel(texto[3]);
                msg.setTipo(texto[4]);
                msg.setHorario(texto[5]);

                banco.addMensagem(msg);
                //    listaMSG.add(msg);
            }
        }
        catch(Exception erro){ Log.i("erro", "erro = "+erro); }
        //    return listaMSG;
    }

    public void listarMensagens(Context context) {
        SQLiteMensagem banco = new SQLiteMensagem(context);
        ArrayList<Mensagem> listaMSG = banco.getAllMensagens();

        if(listaMSG.isEmpty()){
            finish();
            Toast.makeText( ListaMensagem.this, "Não há mensagens para serem exibidas.", Toast.LENGTH_LONG).show(); }
        else {
            String texto = new String();
            String[] mensagens;

            for (int i = 0; i <= listaMSG.size() - 1; i++) {
                texto += listaMSG.get(i).getCodigo()+" "+listaMSG.get(i).getMensagem() + "#";
            }

            mensagens = texto.split("#");
            listaMensagens = new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, mensagens);
            setListAdapter(this.listaMensagens);
        }
    }

    public void exibirMensagem(final String mensagem){

        View checkBoxView = View.inflate(this, R.layout.checkbox, null);
        final CheckBox checkBox = (CheckBox) checkBoxView.findViewById(R.id.checkbox);
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                // Save to shared preferences
            }
        });
        checkBox.setText("Não exibir novamente.");

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Atenção!");
        builder.setMessage(mensagem)
                .setView(checkBoxView)
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        if (checkBox.isChecked()) {

                            StringTokenizer vetor = new StringTokenizer(mensagem);
                            int codigo = Integer.parseInt(vetor.nextToken());
                            Mensagem msg = banco.getMensagem(codigo);
                            banco.deleteMensagem(msg);
                            listarMensagens(ListaMensagem.this);

                        }
                    }
                })
                .setNegativeButton("", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                }).show();
    }

    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        Object objetoSelecionado = this.getListAdapter().getItem(position);
        String mensagem = objetoSelecionado.toString();
        exibirMensagem(mensagem);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_lista_mensagem, menu);
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
