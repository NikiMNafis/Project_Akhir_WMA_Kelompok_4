package com.nikimnafis.bridgeorganizer;

import android.content.Intent;
import android.content.SharedPreferences;
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

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText inputEmail, inputPassword;
    boolean passwordVisible;
    private TextView buttonDaftar, buttonLupaPassword;
    private ImageButton buttonMasuk;

    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private DatabaseReference reference;

    private String userID;

    private SharedPreferences sharedPreferences;

    private static final String USER_DATA = "userdata";
    private static final String NAMA_USER = "nama";
    private static final String NOTELP_USER = "notelp";
    private static final String EMAIL_USER = "email";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        inputEmail = findViewById(R.id.inp_email);
        inputPassword = findViewById(R.id.inp_pass);

        mAuth = FirebaseAuth.getInstance();

        buttonDaftar = findViewById(R.id.btn_daftar);
        buttonMasuk = findViewById(R.id.btn_masuk);
        buttonLupaPassword = findViewById(R.id.btn_lupa_pass);

        buttonDaftar.setOnClickListener(this);
        buttonMasuk.setOnClickListener(this);
        buttonLupaPassword.setOnClickListener(this);

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

//        createRequest();
    }

    public void onClick(View view) {
        int id = view.getId();

        switch (id) {
            case R.id.btn_daftar:
                startActivity(new Intent(this, RegisterActivity.class));
                break;
            case R.id.btn_masuk:
                userLogin();
//                userData();

//                String email = inputEmail.getEditableText().toString().trim();
//                String password = inputPassword.getEditableText().toString().trim();

//                Query cekUser = FirebaseDatabase.getInstance().getReference("user").orderByChild("email").equalTo(email);
//
//                cekUser.addListenerForSingleValueEvent(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot snapshot) {
//                        if (snapshot.exists()) {
//                            inputEmail.setError(null);
//                            String systemPassword = snapshot.child("").child("password").getValue(String.class);
//                            if (systemPassword.equals(inputPassword)) {
//                                inputPassword.setError(null);
//                                String nama = snapshot.child("").child("nama").getValue(String.class);
//                                Toast.makeText(LoginActivity.this, "Selamat Datang" + nama, Toast.LENGTH_SHORT).show();
//                            } else {
//                                Toast.makeText(LoginActivity.this, "Password Salah", Toast.LENGTH_SHORT).show();
//                            }
//                        } else {
//                            Toast.makeText(LoginActivity.this, "Akun tidak ada", Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError error) {
//                        Toast.makeText(LoginActivity.this, "Database error", Toast.LENGTH_SHORT).show();
//                    }
//                });
                break;
            case R.id.btn_lupa_pass:
                startActivity(new Intent(this, ForgotPasswordActivity.class));
                break;
        }
    }

    private void userLogin() {
        if (!validasiEmail() | !validasiPassword()) {
            return;
        }

        String email = inputEmail.getEditableText().toString().trim();
        String password = inputPassword.getEditableText().toString().trim();

        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    preferences.setDataLogin(LoginActivity.this, true);
                    userData();
                    Toast.makeText(LoginActivity.this, "Login berhasil", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(LoginActivity.this, "Gagal login, email atau password salah", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void userData() {
        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("users");
        userID = user.getUid();

        reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                UserRegister userProfile = snapshot.getValue(UserRegister.class);

                if (userProfile != null) {
                    String namaUser = userProfile.nama;
                    String noTelpUser = userProfile.noTelp;
                    String emailUser = userProfile.email;

                    sharedPreferences = getSharedPreferences(USER_DATA, MODE_PRIVATE);

                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString(NAMA_USER, namaUser);
                    editor.putString(NOTELP_USER, noTelpUser);
                    editor.putString(EMAIL_USER, emailUser);
                    editor.apply();
                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {
                Toast.makeText(LoginActivity.this, "Database error", Toast.LENGTH_SHORT).show();
            }
        });

    }

    //    Validasi input data
    public Boolean validasiEmail() {
        String val = inputEmail.getEditableText().toString();

        if (val.isEmpty()) {
            inputEmail.setError("Form harus diisi");
            return false;
        } else {
            inputEmail.setError(null);
            return true;
        }
    }
    public Boolean validasiPassword() {
        String val = inputPassword.getEditableText().toString();

        if (val.isEmpty()) {
            inputPassword.setError("Form harus diisi");
            return false;
        } else {
            inputPassword.setError(null);
            return true;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (preferences.getDataLogin(this)) {
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish();
        }
    }
}