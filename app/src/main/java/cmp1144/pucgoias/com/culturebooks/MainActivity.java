package cmp1144.pucgoias.com.culturebooks;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;

import org.w3c.dom.Text;

import java.util.ArrayList;

import cmp1144.pucgoias.com.culturebooks.controller.Auxiliar;
import cmp1144.pucgoias.com.culturebooks.model.Pessoa;

public class MainActivity extends AppCompatActivity {

    private EditText textUsuario;
    private EditText textSenha;
    private Button btnLogin;
    private TextView esqueciSenha;

    private ArrayList<Pessoa> pessoas = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        criarPessoas();

        textSenha = (EditText) findViewById(R.id.textSenha);
        textUsuario = (EditText) findViewById(R.id.textUsuario);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        esqueciSenha = (TextView) findViewById(R.id.esqueciSenha);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String valorSenha = textSenha.getText().toString();
                String valorUsuario = textUsuario.getText().toString();

                if(valorSenha.isEmpty() || valorUsuario.isEmpty()){
                    Auxiliar.emitirToast("Por favor, insira um usuário e uma senha", getApplicationContext());
                } else {
                    Pessoa pessoaLogada = new Pessoa();
                    for(Pessoa p : pessoas){
                        if(p.getUsuario().equalsIgnoreCase(valorUsuario) && p.getSenha().equals(valorSenha)){
                            pessoaLogada = p;
                            break;
                        }
                    }

                    if(pessoaLogada.getNome() == null){
                        Auxiliar.emitirToast("Usuário ou senha inválidos", getApplicationContext());
                    } else {
                        Intent intent = new Intent(getApplicationContext(), AquisicaoActivity.class);
                        intent.putExtra("pessoaLogada", new Gson().toJson(pessoaLogada));
                        startActivity(intent);

                    }
                }

            }
        });

        esqueciSenha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String valorUsuario = textUsuario.getText().toString();
                if(valorUsuario.isEmpty()){
                    Auxiliar.emitirToast("Por favor, digite um usuário para poder receber a dica de sua senha", getApplicationContext());
                } else {
                    Pessoa pessoaDica = new Pessoa();
                    for(Pessoa p: pessoas){
                        if(p.getUsuario().equals(valorUsuario)){
                            pessoaDica = p;
                            break;
                        }
                    }

                    if(pessoaDica.getNome() != null){
                        Auxiliar.emitirToast(pessoaDica.getDicaSenha(), getApplicationContext());
                    } else {
                        Auxiliar.emitirToast("Usuário não encontrado na base de dados", getApplicationContext());
                    }
                }
            }
        });
    }

    private void criarPessoas(){
        Pessoa p = new Pessoa();
        p.setId(Long.parseLong("1"));
        p.setDicaSenha("Seu time de futebol favorito");
        p.setSenha("Real Madrid");
        p.setUsuario("Roberto");
        p.setNome("Roberto da Mata");

        Pessoa p2 = new Pessoa();
        p2.setNome("Vinicius Velasco");
        p2.setId(Long.parseLong("2"));
        p2.setSenha("AldousHuxley");
        p2.setDicaSenha("Seu autor favorito");
        p2.setUsuario("Velasco");

        Pessoa p3 = new Pessoa();
        p3.setNome("Vinicius Velasco.");
        p3.setId(Long.parseLong("3"));
        p3.setSenha("1");
        p3.setDicaSenha("Seu autor favorito");
        p3.setUsuario("V");



        pessoas.add(p);
        pessoas.add(p2);
        pessoas.add(p3);

    }
}
