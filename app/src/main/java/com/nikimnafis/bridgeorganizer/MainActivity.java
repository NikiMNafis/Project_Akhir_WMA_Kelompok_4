package com.nikimnafis.bridgeorganizer;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    BottomNavigationView navigationView;
    ImageView btnChat;
    TextView txtNamaUser;

    private FirebaseUser user;
    private DatabaseReference reference;

    private String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnChat = findViewById(R.id.btn_chat);
        txtNamaUser = findViewById(R.id.txt_nama_user);

        btnChat.setOnClickListener(this);


        navigationView = findViewById(R.id.bottom_navigation);
        getSupportFragmentManager().beginTransaction().replace(R.id.body_container, new ProjectFragment()).commit();
        navigationView.setSelectedItemId(R.id.nav_project);

        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment = null;
                switch (item.getItemId()) {
                    case R.id.nav_project:
                        fragment = new ProjectFragment();
                        break;
                    case R.id.nav_price_list:
                        fragment = new PriceListFragment();
                        break;
                    case R.id.nav_anggota:
                        fragment = new CrewFragment();
                        break;
                    case R.id.nav_profile:
                        fragment = new ProfileFragment();
                        break;
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.body_container, fragment).commit();

                return true;
            }
        });

        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("users");
        userID = user.getUid();

        reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                UserRegister userProfile = snapshot.getValue(UserRegister.class);

                if (userProfile != null) {
                    String namaUser = userProfile.nama;
                    String noTelpUser = userProfile.noTelp;
                    String emailUser = userProfile.email;
                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {
                Toast.makeText(MainActivity.this, "Database Error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void onClick(View view) {
        int id = view.getId();

        switch (id) {
            case R.id.btn_chat:
                startActivity(new Intent(this, ChatActivity.class));
                break;
//            case R.id.btn_more_menu:
//                FirebaseAuth.getInstance().signOut();
//                startActivity(new Intent(this, LoginActivity.class));
//                break;
        }
    }

//    @Override
//    protected void onStart() {
//        super.onStart();
//        cekuserstatus();
//    }
//
//    private void cekuserstatus() {
//        SharedPreferences sharedPreferences = getSharedPreferences("logindata", MODE_PRIVATE);
//        Boolean status = sharedPreferences.getBoolean("loginstatus", Boolean.valueOf(String.valueOf(MODE_PRIVATE)));
//        String username = sharedPreferences.getString("namauser", String.valueOf(MODE_PRIVATE));
//        if (status) {
//            txtNamaUser.setText(username);
//        } else {
//            startActivity(new Intent(MainActivity.this,LoginActivity.class));
//            finish();
//        }
//    }
}