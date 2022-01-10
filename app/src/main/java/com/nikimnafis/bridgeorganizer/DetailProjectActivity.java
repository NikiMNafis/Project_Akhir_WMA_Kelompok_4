package com.nikimnafis.bridgeorganizer;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.imageview.ShapeableImageView;

public class DetailProjectActivity extends AppCompatActivity {

    ShapeableImageView imageDetailProject;

//    ImageView imageDetailProject;
    TextView txtNamaProject, txtDetailProject;

    ImageButton btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_project);

        imageDetailProject = findViewById(R.id.img_detail_project);
        txtNamaProject = findViewById(R.id.txt_nama_project);
        txtDetailProject = findViewById(R.id.txt_detail_project);

        btnBack = findViewById(R.id.btn_back);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DetailProjectActivity.this, MainActivity.class));
            }
        });

        Intent intent = getIntent();

        if (intent.getExtras() != null) {
            String selectedName = intent.getStringExtra("nama");
            int selectedImage = intent.getIntExtra("image", 0);
            int selectedDetail = intent.getIntExtra("detail", 0);

            txtNamaProject.setText(selectedName);
            imageDetailProject.setImageResource(selectedImage);
            txtDetailProject.setText(selectedDetail);
        }
    }

}