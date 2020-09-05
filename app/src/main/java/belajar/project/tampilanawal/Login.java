package belajar.project.tampilanawal;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import belajar.project.tampilanawal.Service.Address;

public class Login extends AppCompatActivity {

    //Session
    public final static String KEY = "KEY";
    public final static String ID = "user_id";
    public final static String SALDO = "saldo";
    SharedPreferences sharedpreferences;
    public static final String my_shared_preferences = "my_shared_preferences";
    //Session

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Inisialisasi variable XML
        final EditText et_username, et_pass;
        Button btn_login;
        TextView btn_register;

        //Connect XML dengan variable berdasarkan ID
        et_username = findViewById(R.id.et_login_username);
        et_pass = findViewById(R.id.et_login_pass);
        btn_login = findViewById(R.id.btn_login_login);
        btn_register = findViewById(R.id.btn_login_register);

        //Pengaturan Agar Tombol Register Berfungsi
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login.this,Register.class);
                Login.this.startActivity(intent);
            }
        });

        //Pengaturan Agar Tombol Login Berfungsi
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Inisialisasi Variable dengan tipe String
                String username, pass;

                //Memasukkan Data String yang ditangkap ke Variable yang telah diinisialisasikan
                username = et_username.getText().toString();
                pass = et_pass.getText().toString();

                //Kondisi pengecekan tiap input saat menekan tombol login
                if(username.equals("")){
                    Toast.makeText(Login.this, "Username Required", Toast.LENGTH_SHORT).show();
                }
                else if(pass.equals("")){
                    Toast.makeText(Login.this, "Password Required", Toast.LENGTH_SHORT).show();
                }
                else{
                    //Jika Seluruh Kondisi diatas telah diverifikasi, maka fungsi Login akan jalan dan mengirimkan data ke server
                    Login(username, pass);
                }
            }
        });
    }

    private String md5(String s) {
        try {
            // Create MD5 Hash
            MessageDigest digest = java.security.MessageDigest.getInstance("MD5");
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();

            // Create Hex String
            StringBuffer hexString = new StringBuffer();
            for (int i=0; i<messageDigest.length; i++)
                hexString.append(Integer.toHexString(0xFF & messageDigest[i]));

            return hexString.toString();
        }catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

    private void Login(final String username, final String pass) {
        //Atur URL file Login di Apache Server
        String URL_LOGIN = Address.ip+"etoll/login.php";

        sharedpreferences = getSharedPreferences(my_shared_preferences, Context.MODE_PRIVATE);

        //String Request dengan menggunakan method POST
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_LOGIN,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        try {
                            //Mendapatkan Data JSON
                            JSONObject jsonResponse = new JSONObject(response);
                            int status = jsonResponse.getInt("status");
                            String msg = jsonResponse.getString("msg");
                            String key = jsonResponse.getString("key");
                            String saldo = jsonResponse.getString("saldo");
                            int id = jsonResponse.getInt("id");

                            //Menyimpan Session
                            SharedPreferences.Editor editor = sharedpreferences.edit();
                            editor.putString(KEY, key);
                            editor.commit();

                            SharedPreferences.Editor editor2 = sharedpreferences.edit();
                            editor2.putInt(ID, id);
                            editor2.commit();

                            SharedPreferences.Editor editor3 = sharedpreferences.edit();
                            editor3.putString(SALDO, saldo);
                            editor3.commit();
                            //Menyimpan Session

                            //Kondisi Yang Terjadi Berdasarkan Data JSON Yang Diterima
                            if(status == 1){
                                //Login Berhasil
                                Toast.makeText(Login.this, msg, Toast.LENGTH_SHORT).show();
                                Bundle ePzl= new Bundle();
                                ePzl.putString("saldo", saldo);
                                //Pindah ke halaman Login
                                Intent direct = new Intent(Login.this, MainActivity.class);
                                direct.putExtras(ePzl);
                                Login.this.startActivity(direct);
                            }
                            else{
                                //Login Gagal
                                Toast.makeText(Login.this, msg, Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(Login.this, "Try Login Error = " + e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Login.this, "Error Listener Login = " + error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();
                //POST data yang dikirim
                params.put("u_un", username);
                params.put("u_pass", pass);

                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}