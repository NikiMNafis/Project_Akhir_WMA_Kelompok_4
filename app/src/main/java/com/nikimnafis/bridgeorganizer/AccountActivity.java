package com.nikimnafis.bridgeorganizer;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AccountActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseUser user;
    private DatabaseReference reference;

    private String userID;

    private FirebaseAuth auth;

    ImageButton btnBack;
    Button btnUpdateProf, btnDeleteAccount, btnLogOut;
    EditText edtNama, edtNoTelp, edtEmail;

    private SharedPreferences sharedPreferences;

    private static final String USER_DATA = "userdata";
    private static final String NAMA_USER = "nama";
    private static final String NOTELP_USER = "notelp";
    private static final String EMAIL_USER = "email";

    AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        btnBack = findViewById(R.id.btn_back);
        btnLogOut = findViewById(R.id.btn_log_out);
        btnUpdateProf = findViewById(R.id.btn_update_profil);
        btnDeleteAccount = findViewById(R.id.btn_delete_account);

        edtNama = findViewById(R.id.txt_view_nama);
        edtNoTelp = findViewById(R.id.txt_view_telp);
        edtEmail = findViewById(R.id.txt_view_email);

        btnBack.setOnClickListener(this);
        btnLogOut.setOnClickListener(this);
        btnUpdateProf.setOnClickListener(this);
        btnDeleteAccount.setOnClickListener(this);

        // Set profile menggunakan share preference
        sharedPreferences = getSharedPreferences(USER_DATA, MODE_PRIVATE);
//
//        String namaUser = sharedPreferences.getString(NAMA_USER, null);
//        String notelpUser = sharedPreferences.getString(NOTELP_USER, null);
//        String emailUser = sharedPreferences.getString(EMAIL_USER, null);
//
//        if (namaUser != null || notelpUser != null || emailUser != null) {
//            edtNama.setText(namaUser);
//            edtNoTelp.setText(notelpUser);
//            edtEmail.setText(emailUser);
//        }

        // Set profile menggunakan firebase auth
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

                    edtNama.setText(namaUser);
                    edtNoTelp.setText(noTelpUser);
                    edtEmail.setText(emailUser);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(AccountActivity.this, "Database error", Toast.LENGTH_SHORT).show();
            }
        });

        builder = new AlertDialog.Builder(this);

        auth = FirebaseAuth.getInstance();
    }

    public void onClick(View view) {
        int id = view.getId();

        switch (id) {
            case R.id.btn_back:
                startActivity(new Intent(AccountActivity.this, MainActivity.class));
                break;
            case R.id.btn_log_out:
                startActivity(new Intent(AccountActivity.this, LoginActivity.class));
                FirebaseAuth.getInstance().signOut();
                preferences.clearData(AccountActivity.this);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear();
                editor.commit();
                finish();
                break;
            case R.id.btn_delete_account:
                deleteAccount();
                break;
            case R.id.btn_update_profil:
                updateProfil();
                break;
        }
    }

    public void updateProfil() {
        if (!validasiNama() | !validasiNoTelp() | !validasiEmail()) {
            return;
        }

        String nama = edtNama.getEditableText().toString().trim();
        String noTelp = edtNoTelp.getEditableText().toString().trim();
        String email = edtEmail.getEditableText().toString().trim();

        user = FirebaseAuth.getInstance().getCurrentUser();

        UserRegister writeUserProfile = new UserRegister(nama, noTelp, email);
        reference = FirebaseDatabase.getInstance().getReference("users");

        userID = user.getUid();

        reference.child(userID).setValue(writeUserProfile).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    UserProfileChangeRequest profileChangeRequest = new UserProfileChangeRequest.Builder().
                            setDisplayName(nama).build();
                    user.updateProfile(profileChangeRequest);
                    finish();
                } else {
                    try {
                        throw task.getException();
                    } catch (Exception e) {
                        Toast.makeText(AccountActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    public void deleteAccount() {
        builder.setTitle("Hapus akun")
                .setMessage("Hapus akun secara permanen?")
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        FirebaseDatabase.getInstance().getReference().child("users")
                                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                .removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()){

                                    FirebaseUser user = auth.getCurrentUser();
                                    user.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Intent intent = new Intent(AccountActivity.this, LoginActivity.class);
                                                startActivity(intent);

                                                Toast.makeText(AccountActivity.this, "Akun berhasil dihapus", Toast.LENGTH_SHORT).show();
                                                auth.signOut();
                                                preferences.clearData(AccountActivity.this);
                                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                                editor.clear();
                                                editor.commit();
                                                finish();
                                            } else {
                                                Toast.makeText(AccountActivity.this, "Akun gagal dihapus", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                                } else {
                                    Toast.makeText(AccountActivity.this, "Akun gagal dihapus", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

                    }
                }).setNegativeButton("Cancel", null)
                .create().show();
    }

    // Validasi Input Data
    public Boolean validasiNama() {
        String val = edtNama.getEditableText().toString();

        if (val.isEmpty()) {
            edtNama.setError("Form harus diisi");
            return false;
        } else {
            edtNama.setError(null);
            return true;
        }
    }
    public Boolean validasiNoTelp() {
        String val = edtNoTelp.getEditableText().toString();

        if (val.isEmpty()) {
            edtNoTelp.setError("Form harus diisi");
            return false;
        } else if (val.length()<=10){
            edtNoTelp.setError("Nomor telepon salah");
            return false;
        } else {
            edtNoTelp.setError(null);
            return true;
        }
    }
    public Boolean validasiEmail() {
        String val = edtEmail.getEditableText().toString();
//        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        if (val.isEmpty()) {
            edtEmail.setError("Form harus diisi");
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(val).matches()) {
            edtEmail.setError("Alamat email salah");
            return false;
        } else {
            edtEmail.setError(null);
            return true;
        }
    }

}