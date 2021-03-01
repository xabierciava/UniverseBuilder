package com.example.universebuilder;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;

import api.ApiInterface;
import api.ServiceGenerator;
import model.usuario;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Entrar extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entrar);
        TextView volver2 = (TextView)findViewById(R.id.volver_atras2);
        EditText nombre = (EditText)findViewById(R.id.editTextNombreEntrar);
        EditText psw = (EditText)findViewById(R.id.editTextPasswordEntrar);
        Button enviar = (Button)findViewById(R.id.botonInternoEntrar);
        volver2.setOnClickListener(v -> {
            finish();
            overridePendingTransition(R.anim.slide_in_up,R.anim.slide_out_down);
        });

        enviar.setOnClickListener(v -> enviarDatos(nombre.getText().toString(),psw.getText().toString()));
    }

    private void enviarDatos(String nombre,String psw){
        /*ApiInterface apiInterface = ServiceGenerator.createService(ApiInterface.class);
        Call<List<usuario>>call=apiInterface.getUsuario("00111111");
        call.enqueue(new Callback<List<usuario>>() {
            @Override
            public void onResponse(Call<List<usuario>> call, Response<List<usuario>> response) {
                if(response.isSuccessful()){
                    for (usuario usu : response.body()){
                         System.out.println("usuario: "+usu.getNombre());
                    }
                }
            }

            @Override
            public void onFailure(Call<List<usuario>> call, Throwable t) {
                 //ha fallado mostrar aviso de que es posible de que no tenga intertet
                 Log.e("tag", t.getMessage());
            }
        });*/
    }

}