package com.nikimnafis.bridgeorganizer;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Patterns;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText inputNama, inputNoTelp, inputEmail, inputPassword, inputUlangPassword;
    boolean passwordVisible;
    private ImageButton buttonBack, buttonDaftar;

    private FirebaseDatabase rootNode;
    private DatabaseReference reference;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        buttonBack = findViewById(R.id.btn_back);
        buttonDaftar = findViewById(R.id.btn_daftar);

        inputNama = findViewById(R.id.inp_nama);
        inputNoTelp = findViewById(R.id.inp_no_telp);
        inputEmail = findViewById(R.id.inp_email);
        inputPassword = findViewById(R.id.inp_pass);
        inputUlangPassword = findViewById(R.id.inp_ulang_pass);

        buttonBack.setOnClickListener(this);
        buttonDaftar.setOnClickListener(this);

        inputPassword.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                final int Right = 2;
                if (event.getAction()==MotionEvent.ACTION_UP) {
                    if (event.getRawX()>=inputPassword.getRight()-inputPassword.getCompoundDrawables()[Right].getBounds().width()) {
                        int selection = inputPassword.getSelectionEnd();
                        if (passwordVisible) {
                            // set gambar
                            inputPassword.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,R.drawable.ic_baseline_visibility_24, 0);
                            // menyembunyikan password
                            inputPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                            passwordVisible=false;
                        } else {
                            // set gambar
                            inputPassword.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,R.drawable.ic_baseline_visibility_off_24, 0);
                            // menyembunyikan password
                            inputPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                            passwordVisible=true;
                        }
                        inputPassword.setSelection(selection);
                        return true;
                    }
                }
                return false;
            }
        });

        inputUlangPassword.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                final int Right = 2;
                if (event.getAction()==MotionEvent.ACTION_UP) {
                    if (event.getRawX()>=inputUlangPassword.getRight()-inputUlangPassword.getCompoundDrawables()[Right].getBounds().width()) {
                        int selection = inputUlangPassword.getSelectionEnd();
                        if (passwordVisible) {
                            // set gambar
                            inputUlangPassword.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,R.drawable.ic_baseline_visibility_24, 0);
                            // menyembunyikan password
                            inputUlangPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                            passwordVisible=false;
                        } else {
                            // set gambar
                            inputUlangPassword.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,R.drawable.ic_baseline_visibility_off_24, 0);
                            // menyembunyikan password
                            inputUlangPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                            passwordVisible=true;
                        }
                        inputUlangPassword.setSelection(selection);
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
                startActivity(new Intent(this, LoginActivity.class));
                break;
            case R.id.btn_daftar:
                userRegister();
//                UserData();

                break;
        }
    }

    private void userRegister() {
        if (!validasiNama() | !validasiNoTelp() | !validasiEmail() | !validasiPassword() |
                !validasiUlangPassword()) {
            return;
        }

        String nama = inputNama.getEditableText().toString().trim();
        String noTelp = inputNoTelp.getEditableText().toString().trim();
        String email = inputEmail.getEditableText().toString().trim();
        String password = inputPassword.getEditableText().toString().trim();

        mAuth = FirebaseAuth.getInstance();

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                            UserRegister user = new UserRegister(nama, noTelp, email);

                            FirebaseDatabase.getInstance().getReference("users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                    if (task.isSuccessful()) {
                                        Toast.makeText(RegisterActivity.this, "Registrasi berhasil", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(RegisterActivity.this, "Registrasi gagal, coba ulang kembali", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        } else {
                            Toast.makeText(RegisterActivity.this, "Registrasi gagal, coba ulang kembali", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

//    private void UserData() {
//        String nama = inputNama.getEditableText().toString().trim();
//        String noTelp = inputNoTelp.getEditableText().toString().trim();
//        String email = inputEmail.getEditableText().toString().trim();
//        String password = inputPassword.getEditableText().toString().trim();
//
//        rootNode = FirebaseDatabase.getInstance();
//        reference = rootNode.getReference("user");
//
//        UserHelper helperClass = new UserHelper(nama, noTelp, email, password);
//        reference.child(password).setValue(helperClass);
//    }


    // Validasi Input Data
    public Boolean validasiNama() {
        String val = inputNama.getEditableText().toString();

        if (val.isEmpty()) {
            inputNama.setError("Form harus diisi");
            return false;
        } else {
            inputNama.setError(null);
            return true;
        }
    }
    public Boolean validasiNoTelp() {
        String val = inputNoTelp.getEditableText().toString();

        if (val.isEmpty()) {
            inputNoTelp.setError("Form harus diisi");
            return false;
        } else if (val.length()<=10){
            inputNoTelp.setError("Nomor telepon salah");
            return false;
        } else {
            inputNoTelp.setError(null);
            return true;
        }
    }
    public Boolean validasiEmail() {
        String val = inputEmail.getEditableText().toString();
//        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        if (val.isEmpty()) {
            inputEmail.setError("Form harus diisi");
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(val).matches()) {
            inputEmail.setError("Alamat email salah");
            return false;
        } else {
            inputEmail.setError(null);
            return true;
        }
    }
    public Boolean validasiPassword() {
        String val = inputPassword.getEditableText().toString();
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
            inputPassword.setError("Form harus diisi");
            return false;
        } else if (val.length()<=8) {
            inputPassword.setError("Password terlalu lemah");
            return false;
        } else {
            inputPassword.setError(null);
            return true;
        }
    }
    public Boolean validasiUlangPassword() {
        String val = inputUlangPassword.getEditableText().toString();
        String val2 = inputPassword.getEditableText().toString();

        if (val.isEmpty()) {
            inputUlangPassword.setError("Form harus diisi");
            return false;
        } else if (!val.equals(val2)) {
            inputUlangPassword.setError("Kata sandi tidak sama");
            return false;
        } else {
            inputUlangPassword.setError(null);
            return true;
        }
    }

}