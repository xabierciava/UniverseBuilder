package com.example.universebuilder;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

public class MenuPrincipal extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_principal);
        Button boton_explorar = findViewById(R.id.boton_explorar);
        Button boton_mis_mundos = findViewById(R.id.boton_mis_mundos);
        ImageView imagen_explorar = findViewById(R.id.imagen_explorar);
        ImageView imagen_mis_mundos = findViewById(R.id.imagen_mis_mundos);
        imagen_explorar.setOnClickListener(v -> explorar());
        boton_explorar.setOnClickListener(v -> explorar());
        imagen_mis_mundos.setOnClickListener(v -> mis_mundos());
        boton_mis_mundos.setOnClickListener(v -> mis_mundos());
    }


    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setMessage("¿Seguro que quieres salir?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        finish();
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }

    public void explorar(){
        Intent intent = new Intent(MenuPrincipal.this, ListaExplorar.class);
        startActivity(intent);
    }

    public void mis_mundos(){
        Intent intent = new Intent(MenuPrincipal.this, ListaMisMundos.class);
        startActivity(intent);
    }

}