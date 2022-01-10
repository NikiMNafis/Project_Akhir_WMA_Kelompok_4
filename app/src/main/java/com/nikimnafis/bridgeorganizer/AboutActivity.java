package com.nikimnafis.bridgeorganizer;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class AboutActivity extends AppCompatActivity implements View.OnClickListener {

    ImageButton btnBack;

    TextView devNafis, devAli, devKiki, devAsif, devReza;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        btnBack = findViewById(R.id.btn_back);

        devAli = findViewById(R.id.dev_ali);
        devNafis = findViewById(R.id.dev_nafis);
        devKiki = findViewById(R.id.dev_kiki);
        devAsif = findViewById(R.id.dev_asif);
        devReza = findViewById(R.id.dev_reza);

        btnBack.setOnClickListener(this);
        devAli.setOnClickListener(this);
        devNafis.setOnClickListener(this);
        devKiki.setOnClickListener(this);
        devAsif.setOnClickListener(this);
        devReza.setOnClickListener(this);
    }

    public void onClick(View view) {
        int id = view.getId();

        switch (id) {
            case R.id.btn_back:
                startActivity(new Intent(this,MainActivity.class));
                break;
            case R.id.dev_ali:
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.instagram.com/aliimrnsf/?utm_medium=copy_link"));
                startActivity(intent);
                break;
            case R.id.dev_nafis:
                Intent intent2 = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.instagram.com/niki_nafis23/?utm_medium=copy_link"));
                startActivity(intent2);
                break;
            case R.id.dev_kiki:
                Intent intent3 = new Intent(Intent.ACTION_VIEW, Uri.parse("https://instagram.com/haekiyo?utm_medium=copy_link"));
                startActivity(intent3);
                break;
            case R.id.dev_asif:
                Intent intent4 = new Intent(Intent.ACTION_VIEW, Uri.parse("https://instagram.com/sukit_24?utm_medium=copy_link"));
                startActivity(intent4);
                break;
            case R.id.dev_reza:
                Intent intent5 = new Intent(Intent.ACTION_VIEW, Uri.parse("https://instagram.com/amreza_alfa?utm_medium=copy_link"));
                startActivity(intent5);
                break;
        }
    }
}