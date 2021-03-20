package com.example.universebuilder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.Pattern;

import api.ApiInterface;
import api.ServiceGenerator;
import model.PaqueteUsuario;
import model.Usuario;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Entrar extends AppCompatActivity {

    EditText email;
    EditText psw;

    public final Pattern EMAIL_ADDRESS_PATTERN = Pattern.compile(
            "[a-zA-Z0-9+._%-+]{1,256}" +
                    "@" +
                    "[a-zA-Z0-9][a-zA-Z0-9-]{0,64}" +
                    "(" +
                    "." +
                    "[a-zA-Z0-9][a-zA-Z0-9-]{0,25}" +
                    ")+"
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entrar);
        TextView volver2 = (TextView)findViewById(R.id.volver_atras2);
        email = (EditText)findViewById(R.id.textInputEditTextEmailEntrar);
        psw = (EditText)findViewById(R.id.textInputEditTextPassEntrar);
        Button enviar = (Button)findViewById(R.id.botonInternoEntrar);
        volver2.setOnClickListener(v -> {
            finish();
            overridePendingTransition(R.anim.slide_in_up,R.anim.slide_out_down);
        });

        enviar.setOnClickListener(v -> enviarDatos(email.getText().toString(),psw.getText().toString()));
    }

    private void enviarDatos(String emailStr,String psw){
        //Si el email no tiene formato email
        if(!EMAIL_ADDRESS_PATTERN.matcher(emailStr).matches()){
            email.setError("No es un correo electrónico");
        }else{
            email.setError(null);
            ApiInterface apiInterface = ServiceGenerator.createService(ApiInterface.class);
            Call<PaqueteUsuario> call = apiInterface.getUsuario(emailStr, psw);
            call.enqueue(new Callback<PaqueteUsuario>() {

                @Override
                public void onResponse(Call<PaqueteUsuario> call, Response<PaqueteUsuario> response) {
                    System.out.println("Success");
                    String status = response.body().getStatus();
                    if (status.equals("OK")) {
                        Usuario user = response.body().getData();
                        Intent intent = new Intent(Entrar.this, menuPrincipal.class);
                        startActivity(intent);
                    } else {
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

}