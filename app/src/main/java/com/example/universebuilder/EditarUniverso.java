package com.example.universebuilder;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class EditarUniverso extends AppCompatActivity {

    String idUniverso;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_universo);
        idUniverso = getIntent().getStringExtra("idUniverso");
        System.out.println(idUniverso);
    }
}