package com.utb.seminarapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;

public class MainActivity extends AppCompatActivity {

    private TextView tvWelcomeUser;
    private MaterialButton btnDaftarSeminar, btnLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
        loadUserInfo();
        setupListeners();
    }

    private void initViews() {
        tvWelcomeUser    = findViewById(R.id.tvWelcomeUser);
        btnDaftarSeminar = findViewById(R.id.btnDaftarSeminar);
        btnLogout        = findViewById(R.id.btnLogout);
    }

    private void loadUserInfo() {
        String username = getIntent().getStringExtra("USERNAME");
        if (username != null && !username.isEmpty()) {
            // Capitalize first letter
            String displayName = username.substring(0, 1).toUpperCase() + username.substring(1);
            tvWelcomeUser.setText(displayName + " 🎓");
        }
    }

    private void setupListeners() {
        btnDaftarSeminar.setOnClickListener(v -> {
            Intent intent = new Intent(this, FormPendaftaranActivity.class);
            String username = getIntent().getStringExtra("USERNAME");
            intent.putExtra("USERNAME", username);
            startActivity(intent);
        });

        btnLogout.setOnClickListener(v -> {
            new AlertDialog.Builder(this)
                    .setTitle("Konfirmasi Logout")
                    .setMessage("Apakah Anda yakin ingin keluar dari aplikasi?")
                    .setPositiveButton("Ya, Logout", (dialog, which) -> {
                        Intent intent = new Intent(this, LoginActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish();
                    })
                    .setNegativeButton("Batal", null)
                    .show();
        });
    }
}