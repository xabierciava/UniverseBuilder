package com.example.universebuilder;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import api.ApiInterface;
import api.ServiceGenerator;

public class Registrar extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar);
        TextView volver = (TextView)findViewById(R.id.volver_atras);
        EditText nombre = (EditText)findViewById(R.id.editTextNombreRegistrar);
        EditText psw1 = (EditText)findViewById(R.id.editTextPasswordRegistrar);
        EditText psw2 = (EditText)findViewById(R.id.editTextPassword2Registrar);
        Button enviar = (Button)findViewById(R.id.botonInternoRegistrar);

        volver.setOnClickListener(v -> {
            finish();
        });
        enviar.setOnClickListener(v -> {
            enviarDatos(nombre.getText().toString(),psw1.getText().toString(),psw2.getText().toString());
        });
    }

    private void enviarDatos(String nombre,String psw,String psw2){
        if (checkPsw(psw,psw2)){
            //enviar datos a la base de datos
            ApiInterface apiInterface = ServiceGenerator.createService(ApiInterface.class);

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