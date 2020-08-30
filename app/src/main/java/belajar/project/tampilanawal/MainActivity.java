package belajar.project.tampilanawal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private CardView infoCv,userCv,paymentCv,historyCv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // definisikan cards nya bosku !!!

        Bundle p = getIntent().getExtras();
        String saldos = p.getString("saldo");
        setContentView(R.layout.activity_main);
        TextView saldo = findViewById(R.id.saldo);
        infoCv = (CardView) findViewById(R.id.info_cv);
        userCv = (CardView) findViewById(R.id.user_cv);
        paymentCv = (CardView) findViewById(R.id.payment_cv);
        historyCv = (CardView) findViewById(R.id.history_cv);

        // tambah Click listener pada cards nya bosku !!!
        if(saldo.length() ==0 || saldo == null){
            SharedPreferences userDetails = getApplicationContext().getSharedPreferences("my_shared_preferences", MODE_PRIVATE);
            saldos = userDetails.getString("saldo", "");
        }
        saldo.setText("Saldo : "+saldos);
        infoCv.setOnClickListener(this);
        userCv.setOnClickListener(this);
        paymentCv.setOnClickListener(this);
        historyCv.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent i ;

        switch (view.getId() ) {
            case R.id.info_cv : i = new Intent(this, info.class);startActivity(i); break;
            case R.id.user_cv : i =   new Intent(this, User.class);startActivity(i); break;
            case R.id.payment_cv : i =   new Intent(this, Payment.class);startActivity(i); break;
            case R.id.history_cv : i =   new Intent(this, History.class);startActivity(i); break;
            default:break;

        }
        }



}
