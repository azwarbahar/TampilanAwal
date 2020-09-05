package belajar.project.tampilanawal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class User extends AppCompatActivity {

    //Session
    public final static String KEY = "KEY";
    String key;
    SharedPreferences sharedpreferences;
    public static final String my_shared_preferences = "my_shared_preferences";
    //Session

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        user();
    }

    private void user() {

        final TextView tvnama, tvemail, tvnohp, tvplat, tvkendaraan;

        tvnama = findViewById(R.id.user_nama);
        tvemail = findViewById(R.id.user_email);
        tvnohp = findViewById(R.id.user_nohp);
        tvplat = findViewById(R.id.user_plat);
        tvkendaraan = findViewById(R.id.user_kendaraan);

        sharedpreferences = getSharedPreferences(my_shared_preferences, Context.MODE_PRIVATE);
        key = sharedpreferences.getString(KEY, null);

        //Atur URL file Login di Apache Server
        String URL_LOGIN = "http://10.2.3.17/etoll/user.php";


                //String Request dengan menggunakan method POST
                StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_LOGIN,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        try {
                            //Mendapatkan Data JSON
                            JSONObject jsonResponse = new JSONObject(response);

                            String nama, email, nohp, plat, kendaraan;
                            int status;

                            nama = jsonResponse.getString("nama");
                            email = jsonResponse.getString("email");
                            nohp = jsonResponse.getString("nohp");
                            plat = jsonResponse.getString("plat");
                            kendaraan = jsonResponse.getString("kendaraan");
                            status = jsonResponse.getInt("status");

                            //Kondisi Yang Terjadi Berdasarkan Data JSON Yang Diterima
                            if(status == 1){
                                tvnama.setText(nama);
                                tvemail.setText(email);
                                tvnohp.setText(nohp);
                                tvplat.setText(plat);
                                tvkendaraan.setText(kendaraan);
                            }
                            else{
                                //Login Gagal
                                Toast.makeText(User.this, "Gagal Menampilkan Data User", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(User.this, "Try Login Error = " + e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(User.this, "Error Listener = " + error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();
                //POST data yang dikirim
                params.put("key", key);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}
