package com.example.universebuilder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
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

    String regex = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";

    public final Pattern EMAIL_ADDRESS_PATTERN = Pattern.compile(regex);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entrar);
        TextView volver2 = (TextView)findViewById(R.id.volver_atras2);
        email = (EditText)findViewById(R.id.textInputEditTextEmailEntrar);
        psw = (EditText)findViewById(R.id.textInputEditTextPassEntrar);
        Button enviar = (Button)findViewById(R.id.botonInternoEntrar);
        volver2.setOnClickListener(v -> {
            Intent intent = new Intent(Entrar.this, MainActivity.class);
            startActivity(intent);
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
                        user.setPass(psw);
                        SharedPreferences prefs = getSharedPreferences("sesion", MODE_PRIVATE);
                        SharedPreferences.Editor editor = prefs.edit();
                        editor.putString("nombre",user.getNombre());
                        editor.putString("email",user.getEmail());
                        editor.putString("pass",psw);
                        editor.putString("id",user.getId());
                        editor.apply();
                        Intent intent = new Intent(Entrar.this, MenuPrincipal.class);
                        startActivity(intent);
                        finish();
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