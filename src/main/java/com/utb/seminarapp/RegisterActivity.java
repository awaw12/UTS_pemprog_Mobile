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

public class RegisterActivity extends AppCompatActivity {

    private TextInputLayout tilNamaReg, tilUsernameReg, tilEmailReg, tilPasswordReg, tilConfirmPassword;
    private TextInputEditText etNamaReg, etUsernameReg, etEmailReg, etPasswordReg, etConfirmPassword;
    private MaterialButton btnRegister, btnBackToLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initViews();
        setupRealTimeValidation();
        setupListeners();
    }

    private void initViews() {
        tilNamaReg        = findViewById(R.id.tilNamaReg);
        tilUsernameReg    = findViewById(R.id.tilUsernameReg);
        tilEmailReg       = findViewById(R.id.tilEmailReg);
        tilPasswordReg    = findViewById(R.id.tilPasswordReg);
        tilConfirmPassword = findViewById(R.id.tilConfirmPassword);

        etNamaReg        = findViewById(R.id.etNamaReg);
        etUsernameReg    = findViewById(R.id.etUsernameReg);
        etEmailReg       = findViewById(R.id.etEmailReg);
        etPasswordReg    = findViewById(R.id.etPasswordReg);
        etConfirmPassword = findViewById(R.id.etConfirmPassword);

        btnRegister    = findViewById(R.id.btnRegister);
        btnBackToLogin = findViewById(R.id.btnBackToLogin);
    }

    private void setupRealTimeValidation() {
        // Nama
        etNamaReg.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) tilNamaReg.setError(null);
                else tilNamaReg.setError("Nama tidak boleh kosong");
            }
            @Override public void afterTextChanged(Editable s) {}
        });

        // Username
        etUsernameReg.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) tilUsernameReg.setError(null);
                else tilUsernameReg.setError("Username tidak boleh kosong");
            }
            @Override public void afterTextChanged(Editable s) {}
        });

        // Email real-time
        etEmailReg.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
                String email = s.toString().trim();
                if (email.isEmpty()) tilEmailReg.setError("Email tidak boleh kosong");
                else if (!email.contains("@")) tilEmailReg.setError("Email harus mengandung '@'");
                else tilEmailReg.setError(null);
            }
            @Override public void afterTextChanged(Editable s) {}
        });

        // Password
        etPasswordReg.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) tilPasswordReg.setError(null);
                else tilPasswordReg.setError("Password tidak boleh kosong");
            }
            @Override public void afterTextChanged(Editable s) {}
        });

        // Confirm password
        etConfirmPassword.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
                String pass = etPasswordReg.getText() != null ? etPasswordReg.getText().toString() : "";
                if (!s.toString().equals(pass)) tilConfirmPassword.setError("Password tidak cocok");
                else tilConfirmPassword.setError(null);
            }
            @Override public void afterTextChanged(Editable s) {}
        });
    }

    private void setupListeners() {
        btnRegister.setOnClickListener(v -> doRegister());
        btnBackToLogin.setOnClickListener(v -> {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        });
    }

    private void doRegister() {
        String nama    = etNamaReg.getText() != null ? etNamaReg.getText().toString().trim() : "";
        String username = etUsernameReg.getText() != null ? etUsernameReg.getText().toString().trim() : "";
        String email   = etEmailReg.getText() != null ? etEmailReg.getText().toString().trim() : "";
        String pass    = etPasswordReg.getText() != null ? etPasswordReg.getText().toString().trim() : "";
        String confirm = etConfirmPassword.getText() != null ? etConfirmPassword.getText().toString().trim() : "";

        boolean valid = true;

        if (nama.isEmpty()) { tilNamaReg.setError("Nama tidak boleh kosong"); valid = false; }
        if (username.isEmpty()) { tilUsernameReg.setError("Username tidak boleh kosong"); valid = false; }
        if (email.isEmpty()) { tilEmailReg.setError("Email tidak boleh kosong"); valid = false; }
        else if (!email.contains("@")) { tilEmailReg.setError("Email harus mengandung '@'"); valid = false; }
        if (pass.isEmpty()) { tilPasswordReg.setError("Password tidak boleh kosong"); valid = false; }
        if (!pass.equals(confirm)) { tilConfirmPassword.setError("Password tidak cocok"); valid = false; }

        if (!valid) return;

        Toast.makeText(this, "Registrasi berhasil! Silakan login 🎉", Toast.LENGTH_LONG).show();
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }
}