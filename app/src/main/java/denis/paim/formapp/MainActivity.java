package denis.paim.formapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private ListView lvDados;
    private ArrayAdapter adapter;
    private List<Dados> listaDeDados;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lvDados = findViewById(R.id.lvDados);
        carregarDados();

        FloatingActionButton fab = findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, CadastroActivity.class);
                intent.putExtra("acao", "inserir");
                startActivity(intent);
            }
        });

        lvDados.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int idDado = listaDeDados.get(position).getId();
                Intent intent = new Intent(MainActivity.this, CadastroActivity.class);
                intent.putExtra("acao", "editar");
                intent.putExtra("idDado", idDado);
                startActivity(intent);
            }
        });

        lvDados.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                excluir(position);
                return true;
            }
        });
    }

    private void excluir(int posicao) {
        Dados dad = listaDeDados.get(posicao);
        AlertDialog.Builder alerta = new AlertDialog.Builder(this);
        alerta.setTitle(getResources().getString(R.string.excluir));
        alerta.setIcon(android.R.drawable.ic_delete);
        alerta.setMessage(getResources().getString(R.string.mensagem) + " " + dad.getNome() + "?");
        alerta.setNeutralButton(getResources().getString(R.string.cancelar), null);

        alerta.setPositiveButton(getResources().getString(R.string.confirmar), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                DadoDAO.excluir(MainActivity.this, dad.getId());
                carregarDados();
            }
        });
        alerta.show();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        carregarDados();
    }

    private void carregarDados() {
        listaDeDados = DadoDAO.getDados(this);
        if (listaDeDados.size() == 0) {
            Dados fake = new Dados(getResources().getString(R.string.semDados), "", "", "");
            listaDeDados.add(fake);
            lvDados.setEnabled(false);
        } else {
            lvDados.setEnabled(true);
        }

        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, listaDeDados);
        lvDados.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}