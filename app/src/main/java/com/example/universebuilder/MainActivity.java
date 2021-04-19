package com.example.universebuilder;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import api.ApiInterface;
import api.ServiceGenerator;
import model.PaqueteUsuario;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences prefs = getSharedPreferences("sesion", MODE_PRIVATE);
        String correo = prefs.getString("email","");
        String password = prefs.getString("pass","");
        if (!correo.equals("")){
            entrarDirectamente(correo,password);
        }
        setContentView(R.layout.activity_main);
        Button boton_registrar = (Button) findViewById(R.id.boton_explorar);
        boton_registrar.setOnClickListener(v -> abrirRegistrar());
        Button boton_entrar = (Button) findViewById(R.id.boton_entrar);
        boton_entrar.setOnClickListener(v -> abrirEntrar());
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setMessage("¿Seguro que quieres salir?")
                .setCancelable(false)
                .setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        finish();
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }

    public void entrarDirectamente(String emailStr,String pswStr){
        ApiInterface apiInterface = ServiceGenerator.createService(ApiInterface.class);
        Call<PaqueteUsuario> call = apiInterface.getUsuario(emailStr, pswStr);
        call.enqueue(new Callback<PaqueteUsuario>() {
            @Override
            public void onResponse(Call<PaqueteUsuario> call, Response<PaqueteUsuario> response) {
                System.out.println("Success");
                String status = response.body().getStatus();
                if (status.equals("OK")) {
                    Intent intent = new Intent(MainActivity.this, MenuPrincipal.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast toast = Toast.makeText(MainActivity.this, "No existe este usuario", Toast.LENGTH_LONG);
                    toast.show();
                    System.out.println("USUARIO INEXISTENTE");
                }
            }

            @Override
            public void onFailure(Call<PaqueteUsuario> call, Throwable t) {
                Log.e("tag", t.getMessage());
            }
        });
    }

    //Abre la actividad para entrar
    public void abrirEntrar(){
        Intent intent = new Intent(this, Entrar.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_down,R.anim.slide_out_up);
        finish();
    }

    //Abre la actividad para registrarse
    public void abrirRegistrar(){
        Intent intent = new Intent(this, Registrar.class);
        startActivity(intent);
        finish();
    }


}