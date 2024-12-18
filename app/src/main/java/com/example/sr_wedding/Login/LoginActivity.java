package com.example.sr_wedding.Login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.sr_wedding.Activity.MainActivity;
import com.example.sr_wedding.Db_Contract;
import com.example.sr_wedding.R;
import com.example.sr_wedding.Register.RegisterActivity;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class LoginActivity extends AppCompatActivity {

    EditText etemail, etPassword;
    Button btnLogin, btnRegister;

    TextView btnDaftar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        etemail = findViewById(R.id.edt_email_login);
        etPassword = findViewById(R.id.edt_password_login);
        btnLogin = findViewById(R.id.btn_login_login);
        btnDaftar = findViewById(R.id.btn_daftar_login);
        btnDaftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent daftar = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(daftar);
                finish();
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email = etemail.getText().toString();
                String password = etPassword.getText().toString();

                if (! (email.isEmpty() || password.isEmpty())){

                    RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

                    StringRequest stringRequest = new StringRequest(Request.Method.GET, Db_Contract.urlLogin + "?email=" + email + "&password=" + password, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            if (response.equals("Selamat Datang")){
                                Toast.makeText(getApplicationContext(), "Login Berhasil", Toast.LENGTH_SHORT).show();

                                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                            }else{
                                Toast.makeText(getApplicationContext(), "Login Gagal", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
                        }
                    });
                    requestQueue.add(stringRequest);
                }else{
                    Toast.makeText(getApplicationContext(), "Password Atau email Salah", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}