package com.utb.seminarapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class LoginActivity extends AppCompatActivity {

    // Hardcode user untuk login sederhana
    private static final String VALID_USERNAME = "admin";
    private static final String VALID_PASSWORD = "admin123";

    private TextInputLayout tilUsername, tilPassword;
    private TextInputEditText etUsername, etPassword;
    private MaterialButton btnLogin, btnGoToRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initViews();
        setupListeners();
    }

    private void initViews() {
        tilUsername    = findViewById(R.id.tilUsername);
        tilPassword    = findViewById(R.id.tilPassword);
        etUsername     = findViewById(R.id.etUsername);
        etPassword     = findViewById(R.id.etPassword);
        btnLogin       = findViewById(R.id.btnLogin);
        btnGoToRegister = findViewById(R.id.btnGoToRegister);
    }

    private void setupListeners() {
        // Real-time validation username
        etUsername.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) tilUsername.setError(null);
            }
            @Override public void afterTextChanged(Editable s) {}
        });

        // Real-time validation password
        etPassword.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) tilPassword.setError(null);
            }
            @Override public void afterTextChanged(Editable s) {}
        });

        btnLogin.setOnClickListener(v -> doLogin());
        btnGoToRegister.setOnClickListener(v -> {
            startActivity(new Intent(this, RegisterActivity.class));
        });
    }

    private void doLogin() {
        String username = etUsername.getText() != null ? etUsername.getText().toString().trim() : "";
        String password = etPassword.getText() != null ? etPassword.getText().toString().trim() : "";

        boolean valid = true;

        if (username.isEmpty()) {
            tilUsername.setError("Username tidak boleh kosong");
            valid = false;
        }

        if (password.isEmpty()) {
            tilPassword.setError("Password tidak boleh kosong");
            valid = false;
        }

        if (!valid) return;

        // Cek kredensial
        if (username.equals(VALID_USERNAME) && password.equals(VALID_PASSWORD)) {
            Toast.makeText(this, "Login berhasil! Selamat datang 👋", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("USERNAME", username);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        } else {
            tilPassword.setError("Username atau password salah");
            Toast.makeText(this, "Login gagal. Periksa kembali username/password", Toast.LENGTH_LONG).show();
        }
    }
}