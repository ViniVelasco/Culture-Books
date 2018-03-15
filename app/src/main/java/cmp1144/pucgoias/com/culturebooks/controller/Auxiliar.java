package cmp1144.pucgoias.com.culturebooks.controller;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.text.DecimalFormat;

import cmp1144.pucgoias.com.culturebooks.model.Pessoa;

/**
 * Created by vinic on 23/09/2017.
 */

public class Auxiliar {

    public static void emitirToast(String msg, Context context){
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

    public static void procurarDrawable(ImageView imageView, String nomeImagem){
        Context context = imageView.getContext();
        int id = context.getResources().getIdentifier(nomeImagem, "drawable", context.getPackageName());
        imageView.setImageResource(id);
    }

    public static Pessoa obtemObjetoPessoa(Intent intent) {

        String jsonPessoa = "";
        Bundle extras = intent.getExtras();
        if(extras != null){
            jsonPessoa = extras.getString("pessoaLogada");
        }
        Pessoa p = new Gson().fromJson(jsonPessoa, Pessoa.class);
        return p;

    }

    public static String formatarPreco(Double preco){
        DecimalFormat df = new DecimalFormat("#.00");
        return df.format(preco);

    }
}
