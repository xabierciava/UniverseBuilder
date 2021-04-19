package com.example.universebuilder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import api.ApiInterface;
import api.ServiceGenerator;
import model.PaqueteUsuario;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PantallaCarga extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_carga);

        SharedPreferences prefs = getSharedPreferences("sesion", MODE_PRIVATE);
        String correo = prefs.getString("email","");
        String password = prefs.getString("pass","");
        if (!correo.equals("")){
            entrarDirectamente(correo,password);
        }else{
            Intent intent = new Intent(PantallaCarga.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
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
                    Intent intent = new Intent(PantallaCarga.this, MenuPrincipal.class);
                    startActivity(intent);
                    finish();
                } else {
                    Intent intent = new Intent(PantallaCarga.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }

            @Override
            public void onFailure(Call<PaqueteUsuario> call, Throwable t) {
                Log.e("tag", t.getMessage());
            }
        });
    }
}