package com.utb.seminarapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;

public class HasilActivity extends AppCompatActivity {

    private TextView tvHasilNama, tvHasilEmail, tvHasilNoHp, tvHasilJK, tvHasilSeminar;
    private MaterialButton btnKembaliUtama;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hasil);

        initViews();
        loadData();
        setupListeners();
    }

    private void initViews() {
        tvHasilNama    = findViewById(R.id.tvHasilNama);
        tvHasilEmail   = findViewById(R.id.tvHasilEmail);
        tvHasilNoHp    = findViewById(R.id.tvHasilNoHp);
        tvHasilJK      = findViewById(R.id.tvHasilJK);
        tvHasilSeminar = findViewById(R.id.tvHasilSeminar);
        btnKembaliUtama = findViewById(R.id.btnKembaliUtama);
    }

    private void loadData() {
        Intent intent = getIntent();
        tvHasilNama.setText(intent.getStringExtra("NAMA"));
        tvHasilEmail.setText(intent.getStringExtra("EMAIL"));
        tvHasilNoHp.setText(intent.getStringExtra("NO_HP"));
        tvHasilJK.setText(intent.getStringExtra("JK"));
        tvHasilSeminar.setText(intent.getStringExtra("SEMINAR"));
    }

    private void setupListeners() {
        btnKembaliUtama.setOnClickListener(v -> {
            // Kembali ke halaman utama, clear semua activity di atas
            Intent intent = new Intent(this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
            finish();
        });
    }

    @Override
    public void onBackPressed() {
        // Saat back ditekan, kembali ke main
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
        finish();
    }
}