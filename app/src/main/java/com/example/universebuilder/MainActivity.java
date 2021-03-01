package com.example.universebuilder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button boton_registrar = (Button) findViewById(R.id.boton_registrar);
        boton_registrar.setOnClickListener(v -> abrirRegistrar());
        Button boton_entrar = (Button) findViewById(R.id.boton_entrar);
        boton_entrar.setOnClickListener(v -> abrirEntrar());
    }

    //Abre la actividad para entrar
    public void abrirEntrar(){
        Intent intent = new Intent(this, Entrar.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_down,R.anim.slide_out_up);
    }

    //Abre la actividad para registrarse
    public void abrirRegistrar(){
        Intent intent = new Intent(this, Registrar.class);
        startActivity(intent);
    }


}