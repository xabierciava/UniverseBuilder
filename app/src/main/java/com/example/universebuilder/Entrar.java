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
import model.PaqueteUsuario;
import model.Usuario;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Entrar extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entrar);
        TextView volver2 = (TextView)findViewById(R.id.volver_atras2);
        EditText email = (EditText)findViewById(R.id.editTextEmailEntrar);
        EditText psw = (EditText)findViewById(R.id.editTextPasswordEntrar);
        Button enviar = (Button)findViewById(R.id.botonInternoEntrar);
        volver2.setOnClickListener(v -> {
            finish();
            overridePendingTransition(R.anim.slide_in_up,R.anim.slide_out_down);
        });

        enviar.setOnClickListener(v -> enviarDatos(email.getText().toString(),psw.getText().toString()));
    }

    private void enviarDatos(String email,String psw){
        ApiInterface apiInterface = ServiceGenerator.createService(ApiInterface.class);
        Call<PaqueteUsuario> call=apiInterface.getUsuario(email,psw);
        call.enqueue(new Callback<PaqueteUsuario>() {

            @Override
            public void onResponse(Call<PaqueteUsuario> call, Response<PaqueteUsuario> response) {
                System.out.println("Success");
                String status = response.body().getStatus();
                if(status.equals("OK")){
                    Usuario user = response.body().getData();
                    Intent intent = new Intent(Entrar.this, menuPrincipal.class);
                    startActivity(intent);
                }else{
                    Toast toast = Toast.makeText(Entrar.this, "No existe este usuario", Toast.LENGTH_LONG);
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

}