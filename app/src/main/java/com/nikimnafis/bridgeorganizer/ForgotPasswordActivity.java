package com.nikimnafis.bridgeorganizer;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPasswordActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageButton buttonBack, buttonNext;
    private EditText inpEmail;

    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        buttonBack = findViewById(R.id.btn_back);
        buttonNext = findViewById(R.id.btn_next);
        inpEmail = findViewById(R.id.inp_FPass);

        buttonBack.setOnClickListener(this);
        buttonNext.setOnClickListener(this);

        auth = FirebaseAuth.getInstance();
    }

    public void onClick(View view) {
        int id = view.getId();

        switch (id) {
            case R.id.btn_back:
                startActivity(new Intent(this, LoginActivity.class));
                break;
            case R.id.btn_next:
                forgotPassword();
        }
    }

    private void forgotPassword() {
        String email = inpEmail.getEditableText().toString().trim();

        if (email.isEmpty()) {
            inpEmail.setError("Form harus diisi");
            inpEmail.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            inpEmail.setError("Alamat email salah");
            inpEmail.requestFocus();
            return;
        }

        auth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(ForgotPasswordActivity.this, "Cek email untuk mengubah password", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ForgotPasswordActivity.this, "Reset password gagal, coba ulang kembali", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}