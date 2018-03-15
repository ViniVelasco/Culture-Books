package cmp1144.pucgoias.com.culturebooks;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class FalhaActivity extends AppCompatActivity {

    TextView textMotivo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_falha);

        textMotivo = (TextView) findViewById(R.id.textViewPedidoRejeitado);

        String motivo = "";
        Bundle extras = getIntent().getExtras();
        if(extras != null){
            motivo = extras.getString("motivo");
        }

        textMotivo.setText(motivo);
    }
}
