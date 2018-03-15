package cmp1144.pucgoias.com.culturebooks;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.Random;

public class SucessoActivity extends AppCompatActivity {

    TextView textPedido;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sucesso);

        textPedido = (TextView) findViewById(R.id.textViewPedidoAprovado);

        Random rand = new Random();
        int pedido = rand.nextInt((2559 - 250) + 1) + 250;
        textPedido.setText("Seu pedido #" + pedido + " foi aprovado!");
    }
}
