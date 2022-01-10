package com.nikimnafis.bridgeorganizer;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ChatActivity extends AppCompatActivity implements View.OnClickListener {

    ImageButton btnBack, btnKirim;
    EditText inpMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        btnBack = findViewById(R.id.btn_back);
        btnKirim = findViewById(R.id.btn_kirim);
        inpMessage = findViewById(R.id.inp_message);

        btnBack.setOnClickListener(this);
        btnKirim.setOnClickListener(this);
    }

    public void onClick(View view) {
        int id = view.getId();

        switch (id) {
            case R.id.btn_back:
                startActivity(new Intent(this,MainActivity.class));
                break;
            case R.id.btn_kirim:
                String getMessage = inpMessage.getText().toString();

                if (!getMessage.isEmpty()) {
                    if (isWhatsAppInstalled()) {
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://api.whatsapp.com/send?phone=+6282264802206&text="+getMessage));
                        startActivity(intent);
                        inpMessage.setText("");
                    } else {
                        Toast.makeText(ChatActivity.this, "Aplikasi WhatsApp belum terinstall", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(ChatActivity.this, "Isi pesan tidak boleh kosong", Toast.LENGTH_SHORT).show();
                }

        }
    }

    private boolean isWhatsAppInstalled() {

        PackageManager packageManager = getPackageManager();
        boolean whatsappInstalled;

        try {
            packageManager.getPackageInfo("com.whatsapp", PackageManager.GET_ACTIVITIES);
            whatsappInstalled = true;

        } catch (PackageManager.NameNotFoundException e) {
            whatsappInstalled = false;
        }

        return whatsappInstalled;
    }
}