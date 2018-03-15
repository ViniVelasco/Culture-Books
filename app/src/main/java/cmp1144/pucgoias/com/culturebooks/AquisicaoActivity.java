package cmp1144.pucgoias.com.culturebooks;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.ArrayList;

import cmp1144.pucgoias.com.culturebooks.controller.AdapterListPersonalizado;
import cmp1144.pucgoias.com.culturebooks.controller.Auxiliar;
import cmp1144.pucgoias.com.culturebooks.model.Carrinho;
import cmp1144.pucgoias.com.culturebooks.model.Livro;
import cmp1144.pucgoias.com.culturebooks.model.Pessoa;

public class AquisicaoActivity extends AppCompatActivity {

    private TextView identificacaoUsuario;
    private ImageView imageProduto;
    private Spinner spinnerProdutos;
    private ListView listCarrinho;
    private EditText textQuantidade, textValorUnitario, textTotalItem, textValorTotal;
    private Button btnComprar, btnFinalizarCompra;

    private ArrayList<Livro> livros = new ArrayList<>();
    private Pessoa pessoaLogada;
    private Livro livroSelecionado;
    private Integer indexSelecionado;
    public static Carrinho carrinho = new Carrinho();

    final AdapterListPersonalizado adapter = new AdapterListPersonalizado(carrinho.getLivrosNoCarrinho(), AquisicaoActivity.this, this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aquisicao);
        bindElements();
        criarLivros();
        definirLogin();
        configuraCalculosAutomaticos();
        listCarrinho.setAdapter(adapter);

        btnComprar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                carrinho.setLivrosNoCarrinho(new Livro(livroSelecionado));
                carrinho.calculaValorCompra();

                if(indexSelecionado != 0){
                    spinnerProdutos.setSelection(0);
                } else {
                    spinnerProdutos.setSelection(1);
                }

                textValorTotal.setText("R$"+Auxiliar.formatarPreco(carrinho.getTotal()));
                btnFinalizarCompra.setEnabled(true);

            }
        });

        listCarrinho.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });

        btnFinalizarCompra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ConfirmacaoActivity.class);
                intent.putExtra("carrinho", new Gson().toJson(carrinho));
                startActivity(intent);

            }
        });
    }

    private void definirLogin(){
        pessoaLogada =  Auxiliar.obtemObjetoPessoa(getIntent());
        carrinho.pessoa = pessoaLogada;
        identificacaoUsuario.setText(pessoaLogada.getNome() + ", seu identificador é: " + pessoaLogada.getId());
    }

    private void criarLivros(){
        Livro livro1 = new Livro();
        livro1.setImagem("img_1984");
        livro1.setTitulo("1984");
        livro1.setQuantidade(1);
        livro1.setValorUnitario(40.50);
        livro1.setValorTotal(40.50);

        Livro livro2 = new Livro();
        livro2.setTitulo("Admirável Mundo Novo");
        livro2.setImagem("img_huxley");
        livro2.setQuantidade(1);
        livro2.setValorUnitario(35.50);
        livro2.setValorTotal(35.50);

        Livro livro3 = new Livro();
        livro3.setTitulo("Todo Bob Cuspe");
        livro3.setImagem("img_bobcuspe");
        livro3.setQuantidade(1);
        livro3.setValorUnitario(50.90);
        livro3.setValorTotal(50.90);


        Livro livro4 = new Livro();
        livro4.setTitulo("O Livro do Desassossego");
        livro4.setImagem("img_desassossego");
        livro4.setQuantidade(1);
        livro4.setValorUnitario(25.90);
        livro4.setValorTotal(25.90);

        Livro livro5 = new Livro();
        livro5.setImagem("img_google");
        livro5.setTitulo("Google: Android");
        livro5.setQuantidade(1);
        livro5.setValorUnitario(70.80);
        livro5.setValorTotal(70.80);

        Livro livro6 = new Livro();
        livro6.setTitulo("Fernando Pessoa: Uma Quase Autobiografia");
        livro6.setImagem("img_pessoa");
        livro6.setQuantidade(1);
        livro6.setValorUnitario(80.99);
        livro6.setValorTotal(80.99);

        Livro livro7 = new Livro();
        livro7.setTitulo("Neuromancer");
        livro7.setImagem("img_neuromancer");
        livro7.setQuantidade(1);
        livro7.setValorTotal(99.99);
        livro7.setValorUnitario(99.99);

        livros.add(livro1);
        livros.add(livro2);
        livros.add(livro3);
        livros.add(livro4);
        livros.add(livro5);
        livros.add(livro6);
        livros.add(livro7);

        definirSpinnerProdutos();
    }

    private void definirSpinnerProdutos(){
        ArrayAdapter<Livro> adapter =
                new ArrayAdapter<Livro>(getApplicationContext(), R.layout.spinner_xml, livros);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        spinnerProdutos.setAdapter(adapter);

        spinnerProdutos.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                livroSelecionado = (Livro) spinnerProdutos.getSelectedItem();
                indexSelecionado = spinnerProdutos.getSelectedItemPosition();

                Auxiliar.procurarDrawable(imageProduto,livroSelecionado.getImagem());



                String totalItem = "R$"+Auxiliar.formatarPreco(livroSelecionado.getValorTotal());
                String totalValorunitario = "R$"+Auxiliar.formatarPreco(livroSelecionado.getValorUnitario());

                textQuantidade.setText(String.valueOf(livroSelecionado.getQuantidade()));
                textTotalItem.setText(totalItem);
                textValorUnitario.setText(totalValorunitario);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void bindElements(){
        identificacaoUsuario = (TextView) findViewById(R.id.identificacaoUsuario);
        spinnerProdutos = (Spinner) findViewById(R.id.spinnerProdutos);
        listCarrinho = (ListView) findViewById(R.id.listCarrinho);
        textQuantidade = (EditText) findViewById(R.id.textQuantidade);
        textValorUnitario = (EditText) findViewById(R.id.textValorUnitario);
        textTotalItem = (EditText) findViewById(R.id.textValorTotalItem);
        textValorTotal = (EditText) findViewById(R.id.textTotalCompra);
        btnComprar = (Button) findViewById(R.id.btnComprar);
        btnFinalizarCompra = (Button) findViewById(R.id.btnFinalizarCompra);
        imageProduto = (ImageView) findViewById(R.id.imageProduto);

        btnFinalizarCompra.setEnabled(false);
        textValorTotal.setEnabled(false);
        textTotalItem.setEnabled(false);
        textValorUnitario.setEnabled(false);
    }

    private void calcularValorProduto(Livro livro){
        Double  valorTotal = livro.getValorUnitario() * livro.getQuantidade();
        livroSelecionado.setValorTotal(valorTotal);
        textTotalItem.setText("R$"+ Auxiliar.formatarPreco(valorTotal));
    }

    private void configuraCalculosAutomaticos(){
        textQuantidade.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(!textQuantidade.getText().toString().isEmpty()) {
                    livroSelecionado.setQuantidade(Integer.parseInt(textQuantidade.getText().toString()));
                    calcularValorProduto(livroSelecionado);
                }
            }
        });
    }
}
