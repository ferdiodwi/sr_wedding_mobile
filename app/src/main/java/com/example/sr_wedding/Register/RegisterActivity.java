package com.example.sr_wedding.Register;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.sr_wedding.Db_Contract;
import com.example.sr_wedding.Login.LoginActivity;
import com.example.sr_wedding.R;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {

    EditText etNama, etPassword, etAlamat, etEmail, etno; // Tambahkan nomor telepon
    Button btnRegister;
    TextView btnLogin;
    Spinner spinnerGender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Inisialisasi Views
        btnLogin = findViewById(R.id.btn_login_register);
        etNama = findViewById(R.id.edt_fullname_register);
        etPassword = findViewById(R.id.edt_password_register);
        etAlamat = findViewById(R.id.edt_address_register);
        etEmail = findViewById(R.id.edt_email_register);
        etno = findViewById(R.id.edt_notelp_register); // Tambahkan input nomor telepon
        spinnerGender = findViewById(R.id.spinnerGender);
        btnRegister = findViewById(R.id.btn_register_register);

        // Spinner Gender
        String[] genderOptions = {"Pilih Gender", "Laki-laki", "Perempuan"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, genderOptions);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerGender.setAdapter(adapter);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent login = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(login);
                finish();
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Ambil input dari form
                String nama = etNama.getText().toString();
                String password = etPassword.getText().toString();
                String alamat = etAlamat.getText().toString();
                String email = etEmail.getText().toString();
                String no = etno.getText().toString();
                String gender = spinnerGender.getSelectedItem().toString();

                // Validasi input
                if (nama.isEmpty() || password.isEmpty() || alamat.isEmpty() || email.isEmpty() || no.isEmpty() || gender.equals("Pilih Gender")) {
                    Toast.makeText(getApplicationContext(), "Ada Data Yang Masih Kosong", Toast.LENGTH_SHORT).show();
                } else {
                    // Kirim data ke server
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, Db_Contract.urlRegister, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Toast.makeText(getApplicationContext(), response, Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }) {
                        @Override
                        protected HashMap<String, String> getParams() throws AuthFailureError {
                            HashMap<String, String> params = new HashMap<>();
                            params.put("nama", nama);
                            params.put("password", password);
                            params.put("alamat", alamat);
                            params.put("email", email);
                            params.put("no", no); // Kirim nomor telepon ke server
                            params.put("gender", gender); // Kirim gender ke server
                            return params;
                        }
                    };

                    RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                    requestQueue.add(stringRequest);
                }
            }
        });
    }
}
