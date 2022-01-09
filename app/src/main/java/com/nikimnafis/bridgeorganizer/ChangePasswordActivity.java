package com.nikimnafis.bridgeorganizer;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ChangePasswordActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageButton btnBack;
    private AppCompatButton btnChangePass;
    private TextView btnForgotPass;

    private EditText txtNewPass, txtRNewPass;

    private boolean passwordVisible;

    FirebaseAuth auth;
    FirebaseUser user;
    private String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        btnBack = findViewById(R.id.btn_back);
        btnChangePass = findViewById(R.id.btn_change_pass);
        btnForgotPass = findViewById(R.id.btn_forgot_pass);

        txtNewPass = findViewById(R.id.inp_new_pass);
        txtRNewPass = findViewById(R.id.inp_new_pass2);


        btnBack.setOnClickListener(this);
        btnChangePass.setOnClickListener(this);
        btnForgotPass.setOnClickListener(this);

        txtNewPass.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                final int Right = 2;
                if (event.getAction()==MotionEvent.ACTION_UP) {
                    if (event.getRawX()>=txtNewPass.getRight()-txtNewPass.getCompoundDrawables()[Right].getBounds().width()) {
                        int selection = txtNewPass.getSelectionEnd();
                        if (passwordVisible) {
                            // set gambar
                            txtNewPass.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,R.drawable.ic_baseline_visibility_24, 0);
                            // menyembunyikan password
                            txtNewPass.setTransformationMethod(PasswordTransformationMethod.getInstance());
                            passwordVisible=false;
                        } else {
                            // set gambar
                            txtNewPass.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,R.drawable.ic_baseline_visibility_off_24, 0);
                            // menyembunyikan password
                            txtNewPass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                            passwordVisible=true;
                        }
                        txtNewPass.setSelection(selection);
                        return true;
                    }
                }
                return false;
            }
        });

        txtRNewPass.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                final int Right = 2;
                if (event.getAction()==MotionEvent.ACTION_UP) {
                    if (event.getRawX()>=txtRNewPass.getRight()-txtRNewPass.getCompoundDrawables()[Right].getBounds().width()) {
                        int selection = txtRNewPass.getSelectionEnd();
                        if (passwordVisible) {
                            // set gambar
                            txtRNewPass.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,R.drawable.ic_baseline_visibility_24, 0);
                            // menyembunyikan password
                            txtRNewPass.setTransformationMethod(PasswordTransformationMethod.getInstance());
                            passwordVisible=false;
                        } else {
                            // set gambar
                            txtRNewPass.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,R.drawable.ic_baseline_visibility_off_24, 0);
                            // menyembunyikan password
                            txtRNewPass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                            passwordVisible=true;
                        }
                        txtRNewPass.setSelection(selection);
                        return true;
                    }
                }
                return false;
            }
        });
    }

    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.btn_back:
                startActivity(new Intent(this, MainActivity.class));
                break;
            case R.id.btn_change_pass:
                changePassword();
                break;
        }
    }

    private void changePassword() {
        if (!validasiPassword() | !validasiUlangPassword()) {
            return;
        }

        auth = FirebaseAuth.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();

        String newPass = txtRNewPass.getText().toString();

        user.updatePassword(newPass).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(ChangePasswordActivity.this, "Berhasil mengubah kata sandi", Toast.LENGTH_SHORT);
                } else {
                    Toast.makeText(ChangePasswordActivity.this, "Gagal mengubah kata sandi", Toast.LENGTH_SHORT);
                }
            }
        });
    }

    public Boolean validasiPassword() {
        String val = txtNewPass.getEditableText().toString();
        String passVal = "^" +
//                "(?=.*[0-9])" + //setidaknya 1 digit angka
//                "(?=.*[a-z])" + //setidaknya 1 huruf kecil
//                "(?=.*[A-Z])" + //setidaknya 1 huruf besar
                "(?=.*[a-zA-Z])" + //semua kerakter
                "(?=.*[@#$%^&+=])" + //setidaknya 1 spesial karakter
                "(?=\\s+$)" + //no white space
                ".{4,}" + //setidaknya 4 karakter
                "$";

        if (val.isEmpty()) {
            txtNewPass.setError("Form harus diisi");
            return false;
        } else if (val.length()<=8) {
            txtNewPass.setError("Password terlalu lemah");
            return false;
        } else {
            txtNewPass.setError(null);
            return true;
        }
    }
    public Boolean validasiUlangPassword() {
        String val = txtRNewPass.getEditableText().toString();
        String val2 = txtNewPass.getEditableText().toString();

        if (val.isEmpty()) {
            txtRNewPass.setError("Form harus diisi");
            return false;
        } else if (!val.equals(val2)) {
            txtRNewPass.setError("Kata sandi tidak sama");
            return false;
        } else {
            txtRNewPass.setError(null);
            return true;
        }
    }

}