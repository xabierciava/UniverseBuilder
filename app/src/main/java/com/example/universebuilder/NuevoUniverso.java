package com.example.universebuilder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.switchmaterial.SwitchMaterial;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;

import api.ApiInterface;
import api.ServiceGenerator;
import model.Universo;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NuevoUniverso extends AppCompatActivity {

    TextInputLayout textFieldLabels;
    EditText editTextLabels;
    ChipGroup chipGroup;
    TextView textViewLabels;
    Button botonCrearUniverso;
    EditText editTextNombre,editTextDescripcion;
    SwitchMaterial switchMaterial;
    List<String> listaEtiquetas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo_universo);

        textFieldLabels = findViewById(R.id.outlinedTextField);
        editTextLabels = findViewById(R.id.textInputEditText);
        chipGroup = findViewById(R.id.chipGroup);
        textViewLabels = findViewById(R.id.avisoEtiquetas);
        botonCrearUniverso = findViewById(R.id.botonCrearUniverso);
        editTextNombre = findViewById(R.id.textInputEditTextNombreUniverso);
        editTextDescripcion= findViewById(R.id.textInputEditTextDescripcionUniverso);
        switchMaterial = findViewById(R.id.switchMaterial);
        listaEtiquetas= new ArrayList<>();

        textViewLabels.setVisibility(View.INVISIBLE);

        textFieldLabels.setEndIconOnClickListener(v -> addChip());
        editTextLabels.setOnKeyListener((v, keyCode, event) -> {
            if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                    (keyCode == KeyEvent.KEYCODE_ENTER)) {
                addChip();
                return true;
            }
            return false;
        });

        botonCrearUniverso.setOnClickListener(v -> crearUniverso());
    }

    public void addChip() {
        int maxChips = 4;
        int maxChars = 20;
        if (!editTextLabels.getText().toString().equals("")) {
            if (editTextLabels.getText().toString().length() <= maxChars) {
                if (chipGroup.getChildCount() < maxChips) {
                    textViewLabels.setVisibility(View.INVISIBLE);
                    Chip chip = new Chip(this);
                    chip.setText(editTextLabels.getText().toString());
                    chip.setCloseIconVisible(true);
                    chip.setOnCloseIconClickListener(v1 -> {
                        chipGroup.removeView(chip);
                        if (chipGroup.getChildCount() < maxChips) {
                            textViewLabels.setVisibility(View.INVISIBLE);
                        }
                    });
                    chipGroup.addView(chip);
                    editTextLabels.setText("");
                } else {
                    textViewLabels.setText(R.string.avisoEtiquetas);
                    textViewLabels.setVisibility(View.VISIBLE);
                }
            }else{
                textViewLabels.setText(R.string.avisoEtiquetasLongitud);
                textViewLabels.setVisibility(View.VISIBLE);
            }
        }
    }

    public void crearUniverso(){
        String nombre = editTextNombre.getText().toString();
        String descripcion = editTextDescripcion.getText().toString();
        String visibilidad = switchMaterial.isChecked()?"publico":"privado";
        int maxCharsDesc=150;
        int maxCharsNom=30;
        if(descripcion.length()<= maxCharsDesc && nombre.length()<=maxCharsNom) {
            for (int i = 0; i < chipGroup.getChildCount(); i++) {
                Chip chip = (Chip) chipGroup.getChildAt(i);
                listaEtiquetas.add(chip.getText().toString());
            }
            SharedPreferences prefs = getSharedPreferences("sesion", MODE_PRIVATE);
            String creador = prefs.getString("id", "");
            Universo universo = new Universo("-1", nombre, descripcion, creador, visibilidad, listaEtiquetas);
            ApiInterface apiInterface = ServiceGenerator.createService(ApiInterface.class);
            Call<String> call = apiInterface.insertaUniverso(universo);
            call.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    if (response.isSuccessful()) {
                        System.out.println("Success");
                        System.out.println(response.body());
                        Intent intent = new Intent(NuevoUniverso.this, EditarUniverso.class);
                        startActivity(intent);
                        finish();
                    }
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    Log.e("tag", t.getMessage());
                }
            });
        }
    }

}