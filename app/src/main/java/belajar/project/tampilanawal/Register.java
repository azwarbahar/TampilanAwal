package belajar.project.tampilanawal;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity {

    //Inisialisasi tipe variable XML
    private EditText et_username, et_email, et_pass, et_c_pass;
    private Button btn_register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //Menghubungkan XML dengan Varibale yang telah diinisialisasikan berdasarkan ID yang ada di XML
        et_username = findViewById(R.id.et_register_username);
        et_email = findViewById(R.id.et_register_email);
        et_pass = findViewById(R.id.et_register_pass);
        et_c_pass = findViewById(R.id.et_register_c_pass);
        btn_register = findViewById(R.id.btn_register_register);

        //Method Agar Tombol Register Dapat Ditekan
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Inisialisasi Varible berdasarkan Tipe String
                String username, email, pass, c_pass;

                //Memasukkan Data String yang ditangkap ke Variable yang telah diinisialisasikan
                username = et_username.getText().toString();
                email = et_email.getText().toString();
                pass = et_pass.getText().toString();
                c_pass = et_c_pass.getText().toString();

                //Kondisi pengecekan tiap input saat menekan tombol register
                if(username.equals("")){
                    Toast.makeText(Register.this, "Username Required", Toast.LENGTH_SHORT).show();
                }
                else if(email.equals("")){
                    Toast.makeText(Register.this, "Email Required", Toast.LENGTH_SHORT).show();
                }
                else if(pass.equals("")){
                    Toast.makeText(Register.this, "Password Required", Toast.LENGTH_SHORT).show();
                }
                else if(!c_pass.equals(pass)){
                    Toast.makeText(Register.this, "Invalid Confirm Password", Toast.LENGTH_SHORT).show();
                }
                else{
                    //Jika Seluruh Kondisi diatas telah diverifikasi, maka fungsi Register akan jalan dan mengirimkan data ke server
                    Register(username,email,pass);
                }
            }
        });
    }

    //Disini fungsi Register diatur
    private void Register(final String username, final String email, final String pass) {

        //Atur URL file Register di Apache Server
        String URL_REGISTER = "http://10.2.3.17/etoll/register.php";

        //String Request dengan menggunakan method POST
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_REGISTER,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        try {
                            //Mendapatkan Data JSON
                            JSONObject jsonResponse = new JSONObject(response);
                            int status = jsonResponse.getInt("status");
                            String msg = jsonResponse.getString("msg");

                            //Kondisi Yang Terjadi Berdasarkan Data JSON Yang Diterima
                            if(status == 1){
                                //Registrasi Berhasil
                                Toast.makeText(Register.this, msg, Toast.LENGTH_SHORT).show();
                                //Pindah ke halaman Login
                                Intent direct = new Intent(Register.this, Login.class);
                                Register.this.startActivity(direct);
                            }
                            else{
                                //Registrasi Gagal
                                Toast.makeText(Register.this, msg, Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(Register.this, "Try Sign Up Error = " + e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Register.this, "Error Listener Sign Up = " + error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();
                //POST data yang dikirim
                params.put("u_un", username);
                params.put("u_email", email);
                params.put("u_pass", pass);

                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }
}
