package denis.paim.formapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class CadastroActivity extends AppCompatActivity {

    private EditText etNome;
    private EditText etSobrenome;
    private EditText etTelefone;
    private EditText etEmail;
    private Button btnSalvar;
    private String acao;
    private Dados dado;
    private ConstraintLayout fundoTela;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        etNome = findViewById(R.id.etNome);
        etSobrenome = findViewById(R.id.etSobrenome);
        etTelefone = findViewById(R.id.etTelefone);
        etEmail = findViewById(R.id.etEmail);
        btnSalvar = findViewById(R.id.btnSalvar);
        fundoTela = findViewById(R.id.fundoTela);

        acao = getIntent().getStringExtra("acao");
        if (acao.equals("editar")) {
            carregarCadastro();
        }

        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                salvar();
            }
        });

        fundoTela.setOnTouchListener(new OnSwipeTouchListener(this) {
            @Override
            public void onSwipeTop() {
                super.onSwipeTop();
                Intent intent = new Intent(CadastroActivity.this, MainActivity.class);
                intent.putExtra("acao", "editar");
                startActivity(intent);
            }
        });
    }

    private void carregarCadastro() {
        int id = getIntent().getIntExtra("idDado", 0);

        dado = DadoDAO.getDadoById(this, id);
        etNome.setText(dado.getNome());
        etSobrenome.setText(dado.getSobrenome());
        etTelefone.setText(dado.getTelefone());
        etEmail.setText(dado.getEmail());
    }

    private void salvar() {
        String nome = etNome.getText().toString();
        String sobrenome = etSobrenome.getText().toString();
        String telefone = etTelefone.getText().toString();
        String email = etEmail.getText().toString();

        if (nome.isEmpty() || sobrenome.isEmpty() || telefone.isEmpty() || email.isEmpty()) {
            Toast.makeText(this, getResources().getString(R.string.alerta), Toast.LENGTH_LONG).show();
        } else {
            if (acao.equals("inserir")) {
                dado = new Dados();
            }
            dado.setNome(nome);
            dado.setSobrenome(sobrenome);
            dado.setTelefone(telefone);
            dado.setEmail(email);

            if (acao.equals("inserir")) {
                DadoDAO.inserir(this, dado);

                etNome.setText("");
                etSobrenome.setText("");
                etTelefone.setText("");
                etEmail.setText("");
            } else {
                DadoDAO.editar(this, dado);
                finish();
            }
        }
    }
}