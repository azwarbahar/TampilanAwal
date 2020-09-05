package belajar.project.tampilanawal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private CardView infoCv,userCv,paymentCv,historyCv;
    public String saldos, user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // definisikan cards nya bosku !!!

        Bundle p = getIntent().getExtras();
        saldos = p.getString("saldo");
        user = p.getString("username");
        setContentView(R.layout.activity_main);
        TextView saldo = findViewById(R.id.saldo);
        infoCv = (CardView) findViewById(R.id.info_cv);
        userCv = (CardView) findViewById(R.id.user_cv);
        paymentCv = (CardView) findViewById(R.id.payment_cv);
        historyCv = (CardView) findViewById(R.id.history_cv);

        // tambah Click listener pada cards nya bosku !!!
        SharedPreferences userDetails = getApplicationContext().getSharedPreferences("my_shared_preferences", MODE_PRIVATE);
        saldos = userDetails.getString("saldo", "");
        saldo.setText("Saldo : "+saldos);
        infoCv.setOnClickListener(this);
        userCv.setOnClickListener(this);
        paymentCv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Integer.parseInt(saldos)>=2000){
                    Intent i = new Intent(getApplicationContext(), Payment.class);
                    startActivity(i);
                } else {
                    Toast.makeText(getApplicationContext(), "Saldo tidak cukup", Toast.LENGTH_LONG).show();
                }
            }
        });
        historyCv.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent i ;

        switch (view.getId() ) {
            case R.id.info_cv : i = new Intent(this, info.class);startActivity(i); break;
            case R.id.user_cv : i =   new Intent(this, User.class);startActivity(i); break;

            case R.id.history_cv : i =   new Intent(this, History.class);startActivity(i); break;
            default:break;

        }
        }



}
