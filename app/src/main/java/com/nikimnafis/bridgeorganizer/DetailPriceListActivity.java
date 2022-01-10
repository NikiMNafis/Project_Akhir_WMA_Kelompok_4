package com.nikimnafis.bridgeorganizer;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.imageview.ShapeableImageView;

public class DetailPriceListActivity extends AppCompatActivity {

    ShapeableImageView imageDetailPaket;

//    ImageView imageDetailPaket;
    TextView txtNamaPaket, txtDetailPaket;

    ImageButton btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_price_list);

        imageDetailPaket = findViewById(R.id.img_detail_paket);
        txtNamaPaket = findViewById(R.id.txt_nama_paket);
        txtDetailPaket = findViewById(R.id.txt_detail_paket);

        btnBack = findViewById(R.id.btn_back);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DetailPriceListActivity.this, MainActivity.class));
            }
        });

        Intent intent = getIntent();

        if (intent.getExtras() != null) {
            String selectedName = intent.getStringExtra("nama");
            int selectedImage = intent.getIntExtra("image", 0);
            int selectedDetail = intent.getIntExtra("detail", 0);

            txtNamaPaket.setText(selectedName);
            imageDetailPaket.setImageResource(selectedImage);
            txtDetailPaket.setText(selectedDetail);
        }
    }

    public void onClick(View view) {
    }
}