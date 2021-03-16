package com.example.universebuilder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import api.ApiInterface;
import api.ServiceGenerator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Registrar extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar);
        TextView volver = (TextView)findViewById(R.id.volver_atras);
        EditText nombre = (EditText)findViewById(R.id.editTextNombreRegistrar);
        EditText psw1 = (EditText)findViewById(R.id.editTextPasswordRegistrar);
        EditText psw2 = (EditText)findViewById(R.id.editTextPassword2Registrar);
        EditText email = (EditText)findViewById(R.id.editTextEmailRegistrar);
        Button enviar = (Button)findViewById(R.id.botonInternoRegistrar);

        volver.setOnClickListener(v -> {
            finish();
        });
        enviar.setOnClickListener(v -> {
            enviarDatos(nombre.getText().toString(),psw1.getText().toString(),psw2.getText().toString(),email.getText().toString());
        });
    }

    private void enviarDatos(String nombre,String psw,String psw2,String email){
        if (checkPsw(psw,psw2)){
            //enviar datos a la base de datos
            ApiInterface apiInterface = ServiceGenerator.createService(ApiInterface.class);
            Call<String> call=apiInterface.insertaUsuario(nombre,psw,email);
            call.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    if(response.isSuccessful()){
                        System.out.println("Success");
                        if(response.body().equals("-1")){
                            Toast toast = Toast.makeText(getApplicationContext(), "Ya existe una cuenta con este correo", Toast.LENGTH_LONG);
                            toast.show();
                            System.out.println("ya existe este correo");
                        }else{
                            Intent intent = new Intent(Registrar.this, menuPrincipal.class);
                            startActivity(intent);
                        }

                    }
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    Log.e("tag", t.getMessage());
                }
            });

        }else{
            Toast toast = Toast.makeText(getApplicationContext(), "Las contraseñas no coinciden", Toast.LENGTH_LONG);
            toast.show();
        }
    }

    public Boolean checkPsw(String psw1,String psw2){
        if(psw1.equals(psw2)){
            return true;
        }else{
            return false;
        }
    }

}