package com.utb.seminarapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.checkbox.MaterialCheckBox;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class FormPendaftaranActivity extends AppCompatActivity {

    private TextInputLayout tilNama, tilEmail, tilNoHp;
    private TextInputEditText etNama, etEmail, etNoHp;
    private RadioGroup rgJenisKelamin;
    private Spinner spinnerSeminar;
    private MaterialCheckBox cbPersetujuan;
    private MaterialButton btnSubmit, btnKembali;
    private TextView tvErrorJK, tvErrorSeminar, tvErrorCheckbox;

    private boolean seminarDipilih = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_pendaftaran);

        initViews();
        setupRealTimeValidation();
        setupListeners();
    }

    private void initViews() {
        tilNama    = findViewById(R.id.tilNama);
        tilEmail   = findViewById(R.id.tilEmail);
        tilNoHp    = findViewById(R.id.tilNoHp);

        etNama  = findViewById(R.id.etNama);
        etEmail = findViewById(R.id.etEmail);
        etNoHp  = findViewById(R.id.etNoHp);

        rgJenisKelamin  = findViewById(R.id.rgJenisKelamin);
        spinnerSeminar  = findViewById(R.id.spinnerSeminar);
        cbPersetujuan   = findViewById(R.id.cbPersetujuan);
        btnSubmit       = findViewById(R.id.btnSubmit);
        btnKembali      = findViewById(R.id.btnKembali);

        tvErrorJK       = findViewById(R.id.tvErrorJK);
        tvErrorSeminar  = findViewById(R.id.tvErrorSeminar);
        tvErrorCheckbox = findViewById(R.id.tvErrorCheckbox);
    }

    private void setupRealTimeValidation() {
        // Real-time: Nama
        etNama.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) tilNama.setError(null);
                else tilNama.setError("Nama tidak boleh kosong");
            }
            @Override public void afterTextChanged(Editable s) {}
        });

        // Real-time: Email - validasi @ real-time
        etEmail.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
                String email = s.toString().trim();
                if (email.isEmpty()) {
                    tilEmail.setError("Email tidak boleh kosong");
                } else if (!email.contains("@")) {
                    tilEmail.setError("Email harus mengandung '@'");
                } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    tilEmail.setError("Format email tidak valid");
                } else {
                    tilEmail.setError(null);
                }
            }
            @Override public void afterTextChanged(Editable s) {}
        });

        // Real-time: Nomor HP
        etNoHp.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
                validateNoHpRealtime(s.toString());
            }
            @Override public void afterTextChanged(Editable s) {}
        });

        // Spinner listener
        spinnerSeminar.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0) {
                    seminarDipilih = true;
                    tvErrorSeminar.setVisibility(View.GONE);
                } else {
                    seminarDipilih = false;
                }
            }
            @Override public void onNothingSelected(AdapterView<?> parent) {}
        });

        // Checkbox listener
        cbPersetujuan.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) tvErrorCheckbox.setVisibility(View.GONE);
        });
    }

    private void validateNoHpRealtime(String noHp) {
        if (noHp.isEmpty()) {
            tilNoHp.setError("Nomor HP tidak boleh kosong");
        } else if (!noHp.matches("[0-9]+")) {
            tilNoHp.setError("Nomor HP hanya boleh berisi angka");
        } else if (!noHp.startsWith("08")) {
            tilNoHp.setError("Nomor HP harus diawali dengan 08");
        } else if (noHp.length() < 10) {
            tilNoHp.setError("Nomor HP minimal 10 digit");
        } else if (noHp.length() > 13) {
            tilNoHp.setError("Nomor HP maksimal 13 digit");
        } else {
            tilNoHp.setError(null);
        }
    }

    private void setupListeners() {
        btnKembali.setOnClickListener(v -> onBackPressed());
        btnSubmit.setOnClickListener(v -> {
            if (validateAll()) {
                showConfirmationDialog();
            }
        });
    }

    private boolean validateAll() {
        boolean valid = true;

        // Nama
        String nama = etNama.getText() != null ? etNama.getText().toString().trim() : "";
        if (nama.isEmpty()) {
            tilNama.setError("Nama tidak boleh kosong");
            valid = false;
        }

        // Email
        String email = etEmail.getText() != null ? etEmail.getText().toString().trim() : "";
        if (email.isEmpty()) {
            tilEmail.setError("Email tidak boleh kosong");
            valid = false;
        } else if (!email.contains("@")) {
            tilEmail.setError("Email harus mengandung '@'");
            valid = false;
        }

        // No HP
        String noHp = etNoHp.getText() != null ? etNoHp.getText().toString().trim() : "";
        if (noHp.isEmpty()) {
            tilNoHp.setError("Nomor HP tidak boleh kosong");
            valid = false;
        } else if (!noHp.matches("[0-9]+")) {
            tilNoHp.setError("Nomor HP hanya boleh berisi angka");
            valid = false;
        } else if (!noHp.startsWith("08")) {
            tilNoHp.setError("Nomor HP harus diawali dengan 08");
            valid = false;
        } else if (noHp.length() < 10 || noHp.length() > 13) {
            tilNoHp.setError("Nomor HP harus 10-13 digit");
            valid = false;
        }

        // Jenis Kelamin
        if (rgJenisKelamin.getCheckedRadioButtonId() == -1) {
            tvErrorJK.setVisibility(View.VISIBLE);
            valid = false;
        } else {
            tvErrorJK.setVisibility(View.GONE);
        }

        // Seminar
        if (!seminarDipilih || spinnerSeminar.getSelectedItemPosition() == 0) {
            tvErrorSeminar.setVisibility(View.VISIBLE);
            valid = false;
        } else {
            tvErrorSeminar.setVisibility(View.GONE);
        }

        // Checkbox
        if (!cbPersetujuan.isChecked()) {
            tvErrorCheckbox.setVisibility(View.VISIBLE);
            valid = false;
        } else {
            tvErrorCheckbox.setVisibility(View.GONE);
        }

        return valid;
    }

    private void showConfirmationDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Konfirmasi Data")
                .setMessage("Apakah data yang Anda isi sudah benar?\n\nPastikan semua informasi yang Anda masukkan akurat sebelum melanjutkan.")
                .setPositiveButton("Ya, Lanjutkan ✅", (dialog, which) -> {
                    // Lanjut ke Halaman Hasil
                    goToHasil();
                })
                .setNegativeButton("Tidak, Periksa Lagi ✏️", (dialog, which) -> {
                    dialog.dismiss(); // Tetap di halaman form
                })
                .setCancelable(false)
                .show();
    }

    private void goToHasil() {
        String nama    = etNama.getText() != null ? etNama.getText().toString().trim() : "";
        String email   = etEmail.getText() != null ? etEmail.getText().toString().trim() : "";
        String noHp    = etNoHp.getText() != null ? etNoHp.getText().toString().trim() : "";
        String jk      = rgJenisKelamin.getCheckedRadioButtonId() == R.id.rbLakiLaki
                ? "👨 Laki-laki" : "👩 Perempuan";
        String seminar = spinnerSeminar.getSelectedItem().toString();

        Intent intent = new Intent(this, HasilActivity.class);
        intent.putExtra("NAMA", nama);
        intent.putExtra("EMAIL", email);
        intent.putExtra("NO_HP", noHp);
        intent.putExtra("JK", jk);
        intent.putExtra("SEMINAR", seminar);
        startActivity(intent);
    }
}