package com.example.universebuilder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

import java.util.regex.Pattern;

import api.ApiInterface;
import api.ServiceGenerator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Registrar extends AppCompatActivity {

    EditText nombre;
    EditText email;
    EditText psw1;
    EditText psw2;
    TextInputLayout layPsw;
    TextInputLayout layEmail;

    String regex = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";

    public final Pattern EMAIL_ADDRESS_PATTERN = Pattern.compile(regex);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar);
        TextView volver = (TextView)findViewById(R.id.volver_atras);
        nombre = (EditText)findViewById(R.id.textInputEditTextNombreRegistrar);
        psw1 = (EditText)findViewById(R.id.textInputEditTextPassRegistrar);
        psw2 = findViewById(R.id.textInputEditTextPass2Registrar);
        layPsw = findViewById(R.id.outlinedTextFieldPass2Registrar);
        email = (EditText)findViewById(R.id.textInputEditTextEmailRegistrar);
        layEmail = findViewById(R.id.outlinedTextFieldEmailRegistrar);
        Button enviar = (Button)findViewById(R.id.botonInternoRegistrar);

        volver.setOnClickListener(v -> {
            finish();
        });
        enviar.setOnClickListener(v -> {
            enviarDatos(nombre.getText().toString(),psw1.getText().toString(),psw2.getText().toString(),email.getText().toString());
        });
    }

    private void enviarDatos(String nombreStr,String pswStr,String psw2Str,String emailStr){
        if(EMAIL_ADDRESS_PATTERN.matcher(emailStr).matches()) {
            layEmail.setError(null);
            if (checkPsw(pswStr, psw2Str)) {
                layPsw.setError(null);
                //enviar datos a la base de datos
                ApiInterface apiInterface = ServiceGenerator.createService(ApiInterface.class);
                Call<String> call = apiInterface.insertaUsuario(nombreStr, pswStr, emailStr);
                call.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        if (response.isSuccessful()) {
                            System.out.println("Success");
                            if (response.body().equals("-1")) {
                                layEmail.setError("Ya existe una cuenta con este correo");
                                System.out.println("ya existe este correo");
                            } else {
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

            } else {
                layPsw.setError("Las contraseñas no coinciden");
                Toast toast = Toast.makeText(getApplicationContext(), "Las contraseñas no coinciden", Toast.LENGTH_LONG);
                toast.show();
            }
        }else{
            layEmail.setError("No es un correo electrónico");
        }
    }

    public Boolean checkPsw(String psw1Str,String psw2Str){
        if(psw1Str.equals(psw2Str)){
            return true;
        }else{
            return false;
        }
    }

}