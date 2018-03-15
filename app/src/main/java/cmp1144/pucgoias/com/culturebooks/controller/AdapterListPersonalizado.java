package cmp1144.pucgoias.com.culturebooks.controller;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import cmp1144.pucgoias.com.culturebooks.AquisicaoActivity;
import cmp1144.pucgoias.com.culturebooks.R;
import cmp1144.pucgoias.com.culturebooks.model.Livro;

public class AdapterListPersonalizado extends BaseAdapter {

    private ArrayList<Livro> livrosList;
    private final Activity act;
    private Context context;

    public AdapterListPersonalizado(ArrayList<Livro> livros, Activity act, Context context) {
        this.livrosList = livros;
        this.act = act;
        this.context = context;
    }

    @Override
    public int getCount() {
        return livrosList.size();
    }

    @Override
    public Object getItem(int position) {
        return livrosList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = act.getLayoutInflater()
                .inflate(R.layout.list_com_remove, parent, false);
        Livro livro = this.livrosList.get(position);

        //pegando as referências das Views
        TextView titulo = (TextView)
                view.findViewById(R.id.tituloLivro);
        TextView quantidade = (TextView)
                view.findViewById(R.id.quantidade);
        TextView total = (TextView) view.findViewById(R.id.valorTotal);
        ImageView imagemProduto = (ImageView) view.findViewById(R.id.imagem_produto);
        ImageView imagem = (ImageView)
                view.findViewById(R.id.lista_curso_personalizada_imagem);

        final int positionFinal = position;


        imagem.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                livrosList.remove(positionFinal);
                notifyDataSetChanged();
                AquisicaoActivity.carrinho.calculaValorCompra();

                EditText valorTotal = (EditText) ((Activity)context).findViewById(R.id.textTotalCompra);
                Button btnFinalizar = (Button) ((Activity) context).findViewById(R.id.btnFinalizarCompra);
                valorTotal.setText("R$"+String.valueOf(AquisicaoActivity.carrinho.getTotal()));

                if(livrosList.size() == 0){
                    btnFinalizar.setEnabled(false);
                }

            }
        });


        //populando as Views
        String tituloLivro = livro.getTitulo();
        if(tituloLivro.length() > 15){
            tituloLivro = tituloLivro.substring(0, 14) + "...";
        }
        titulo.setText(tituloLivro);
        quantidade.setText(String.valueOf(livro.getQuantidade()));
        total.setText("R$"+ String.valueOf(livro.getValorTotal()));
        Auxiliar.procurarDrawable(imagemProduto,livro.getImagem());

        return view ;
    }


}
