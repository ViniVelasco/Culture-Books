package cmp1144.pucgoias.com.culturebooks;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import com.google.gson.Gson;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import cmp1144.pucgoias.com.culturebooks.controller.AdapterListPersonalizado;
import cmp1144.pucgoias.com.culturebooks.controller.Auxiliar;
import cmp1144.pucgoias.com.culturebooks.model.Carrinho;
import cmp1144.pucgoias.com.culturebooks.model.Livro;

public class ConfirmacaoActivity extends AppCompatActivity {

    private ListView listCarrinho;
    private Spinner spinnerBandeira;
    private Button btnConfirmar, btnVoltar, btnSelecionarData;
    private EditText textNome, textCCV, textValidade, textValorTotal, textNumeroCartao;

    private String[] bandeiras = {"Mastercard", "Visa", "Elo"};

    private Carrinho carrinho;

    private int ano, mes, dia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmacao);

        bindElements();
        configurarSelecionarData();



        btnConfirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String nome = textNome.getText().toString();
                String CCV = textCCV.getText().toString();
                String validade = textValidade.getText().toString();
                String valorTotal = textValorTotal.getText().toString();
                String numeroCartao = textNumeroCartao.getText().toString();

                if(nome.isEmpty() || CCV.isEmpty() || validade.isEmpty() || valorTotal.isEmpty() || numeroCartao.isEmpty()){
                    Auxiliar.emitirToast("Por favor, preencha todos os campos", getApplicationContext());
                } else if(!validarNumeroCartaoCCV(numeroCartao, CCV)){
                    Auxiliar.emitirToast("O número do cartão deve conter 4 grupos de 4 numeros. O CCV só pode conter números.", getApplicationContext());
                } else if(!validarDataValidadeCartao(validade)){
                    //cartão vencido, redirecionar para tela de erro
                    Intent intent = new Intent(getApplicationContext(), FalhaActivity.class);
                    intent.putExtra("motivo", "O seu cartão está vencido");
                    startActivity(intent);
                } else {
                    //tela confirmação
                    Intent intent = new Intent(getApplicationContext(), SucessoActivity.class);
                    startActivity(intent);
                }

            }
        });

        btnVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AquisicaoActivity.class);
                intent.putExtra("pessoaLogada", new Gson().toJson(AquisicaoActivity.carrinho.pessoa));
                startActivity(intent);
            }
        });





    }

    public boolean validarNumeroCartaoCCV(String numeroCartao, String CCV){
        if(numeroCartao.length() != 16){
            return false;
        } else if(!numeroCartao.matches("[0-9]+") || !CCV.matches("[0-9]+")){
            return false;
        } else if(CCV.length() != 3){
            return false;
        }

        return true;
    }

//    public boolean validarDataValidadeCartao(String dataValidade)  {
//        Date currentTime = Calendar.getInstance().getTime();
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM");
//
//        Date dataValidadeDate = null;
//        Calendar c = Calendar.getInstance();
//        String dateAtual = sdf.format(c.getTime());
//        try {
//            dataValidadeDate = sdf.parse(dataValidade);
//            Date dataAtual = sdf.parse(dateAtual);
//            if(dataValidadeDate.before(dataAtual)){
//                return false;
//            }
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//
//
//
//        return true;
//
//    }

    public boolean validarDataValidadeCartao(String dataValidade)  {

        SimpleDateFormat sdfAno = new SimpleDateFormat("yyyy");
        SimpleDateFormat sdfMes = new SimpleDateFormat("MM");
        Calendar c = Calendar.getInstance();
        String anoAtual = sdfAno.format(c.getTime());
        String mesAtual = sdfMes.format(c.getTime());


        System.out.println("Mês atual " + mesAtual);
        System.out.println("Ano Atual " +  Integer.parseInt(anoAtual));
        System.out.println("Mês validade " + mes);

        if(mes < Integer.parseInt(mesAtual) && ano <= Integer.parseInt(anoAtual)){
            return false;
        }

        return true;

    }


    private void bindElements(){
        listCarrinho = (ListView) findViewById(R.id.listaCarrinho);
        spinnerBandeira = (Spinner) findViewById(R.id.spinnerBandeira);
        btnConfirmar = (Button) findViewById(R.id.btnConfirmarPagamento);
        btnVoltar = (Button) findViewById(R.id.btnVoltar);
        btnSelecionarData = (Button) findViewById(R.id.btnSelecionarData);
        textNome = (EditText) findViewById(R.id.textNomeCompleto);
        textCCV = (EditText) findViewById(R.id.textCCV);
        textValidade = (EditText) findViewById(R.id.textValidade);
        textValorTotal = (EditText) findViewById(R.id.textTotal);
        textNumeroCartao = (EditText) findViewById(R.id.textNumeroCartao);

        textValorTotal.setEnabled(true);
        carrinho = obtemObjetoCarrinho();

        AdapterListPersonalizado adapterList = new AdapterListPersonalizado(carrinho.getLivrosNoCarrinho(), ConfirmacaoActivity.this, this);
        listCarrinho.setAdapter(adapterList);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_xml, bandeiras);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        spinnerBandeira.setAdapter(adapter);

        textValorTotal.setText("R$" + Auxiliar.formatarPreco(carrinho.getTotal()));
        textValidade.setEnabled(false);
    }

    private void configurarSelecionarData(){

        btnSelecionarData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                ano = c.get(Calendar.YEAR);
                mes = c.get(Calendar.MONTH);

                DatePickerDialog datePickerDialog  = new DatePickerDialog(ConfirmacaoActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        textValidade.setText((month + 1) + "/" + String.valueOf(year).substring(2, 4));
                        ano = year;
                        mes = month + 1;
                    }
                }, ano, mes, dia);
                //datePickerDialog.setTitle("Selecione mês e ano, não se preocupe com o dia.");
                datePickerDialog.show();
            }
        });



    }

    private Carrinho obtemObjetoCarrinho() {

        String jsonCarrinho = "";
        Bundle extras = getIntent().getExtras();
        if(extras != null){
            jsonCarrinho = extras.getString("carrinho");
        }
        Carrinho car = new Gson().fromJson(jsonCarrinho, Carrinho.class);

        return car;

    }
}
