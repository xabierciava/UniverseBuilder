package com.example.universebuilder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
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

    final int DRAGON=1;
    final int TERROR=2;
    final int NAVECITA=3;
    final int SUPERMAN=4;
    final int MUNDODISCO=5;
    final int EIFFEL=6;


    TextInputLayout textFieldLabels;
    EditText editTextLabels;
    ChipGroup chipGroup;
    TextView textViewAviso;
    Button botonCrearUniverso;
    EditText editTextNombre, editTextDescripcion;
    SwitchMaterial switchMaterial;
    List<String> listaEtiquetas;
    ImageView iconoDragon, iconoTerror, iconoMundodisco, iconoSuperman, iconoEiffel, iconoNavecita;
    FrameLayout frameIconoDragon, frameIconoTerror, frameIconoMundodisco, frameIconoSuperman, frameIconoEiffel, frameIconoNavecita;
    int icono;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo_universo);

        textFieldLabels = findViewById(R.id.outlinedTextFieldEtiquetasEditar);
        editTextLabels = findViewById(R.id.textInputEditTextEtiquetasEditar);
        chipGroup = findViewById(R.id.chipGroupEditar);
        textViewAviso = findViewById(R.id.texto_aviso_editar);
        botonCrearUniverso = findViewById(R.id.botonEditarUniverso);
        editTextNombre = findViewById(R.id.textInputEditTextNombreUniversoEditar);
        editTextDescripcion = findViewById(R.id.textInputEditTextDescripcionUniversoEditar);
        switchMaterial = findViewById(R.id.switchMaterialEditar);
        iconoDragon = findViewById(R.id.iconoDragon);
        iconoTerror = findViewById(R.id.iconoCthulhu);
        iconoEiffel = findViewById(R.id.iconoEiffel);
        iconoMundodisco = findViewById(R.id.iconoMundodisco);
        iconoSuperman = findViewById(R.id.iconoSuperman);
        iconoNavecita = findViewById(R.id.iconoNavecita);
        frameIconoDragon = findViewById(R.id.frameIconoDragon);
        frameIconoTerror = findViewById(R.id.frameIconoCthulhu);
        frameIconoEiffel = findViewById(R.id.frameIconoEiffel);
        frameIconoMundodisco = findViewById(R.id.frameIconoMundodisco);
        frameIconoSuperman = findViewById(R.id.frameIconoSuperman);
        frameIconoNavecita = findViewById(R.id.frameIconoNavecita);
        listaEtiquetas = new ArrayList<>();

        textViewAviso.setVisibility(View.INVISIBLE);

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

        icono = 0;

        iconoDragon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                icono = DRAGON;
                frameIconoDragon.setBackgroundResource(R.color.naranja_claro);
                frameIconoTerror.setBackgroundResource(R.color.gris_oscuro);
                frameIconoEiffel.setBackgroundResource(R.color.gris_oscuro);
                frameIconoMundodisco.setBackgroundResource(R.color.gris_oscuro);
                frameIconoNavecita.setBackgroundResource(R.color.gris_oscuro);
                frameIconoSuperman.setBackgroundResource(R.color.gris_oscuro);
            }
        });

        iconoTerror.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                icono = TERROR;
                frameIconoTerror.setBackgroundResource(R.color.naranja_claro);
                frameIconoDragon.setBackgroundResource(R.color.gris_oscuro);
                frameIconoEiffel.setBackgroundResource(R.color.gris_oscuro);
                frameIconoMundodisco.setBackgroundResource(R.color.gris_oscuro);
                frameIconoNavecita.setBackgroundResource(R.color.gris_oscuro);
                frameIconoSuperman.setBackgroundResource(R.color.gris_oscuro);
            }
        });

        iconoNavecita.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                icono = NAVECITA;
                frameIconoTerror.setBackgroundResource(R.color.gris_oscuro);
                frameIconoDragon.setBackgroundResource(R.color.gris_oscuro);
                frameIconoEiffel.setBackgroundResource(R.color.gris_oscuro);
                frameIconoMundodisco.setBackgroundResource(R.color.gris_oscuro);
                frameIconoNavecita.setBackgroundResource(R.color.naranja_claro);
                frameIconoSuperman.setBackgroundResource(R.color.gris_oscuro);
            }
        });

        iconoSuperman.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                icono = SUPERMAN;
                frameIconoTerror.setBackgroundResource(R.color.gris_oscuro);
                frameIconoDragon.setBackgroundResource(R.color.gris_oscuro);
                frameIconoEiffel.setBackgroundResource(R.color.gris_oscuro);
                frameIconoMundodisco.setBackgroundResource(R.color.gris_oscuro);
                frameIconoNavecita.setBackgroundResource(R.color.gris_oscuro);
                frameIconoSuperman.setBackgroundResource(R.color.naranja_claro);
            }
        });

        iconoMundodisco.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                icono = MUNDODISCO;
                frameIconoTerror.setBackgroundResource(R.color.gris_oscuro);
                frameIconoDragon.setBackgroundResource(R.color.gris_oscuro);
                frameIconoEiffel.setBackgroundResource(R.color.gris_oscuro);
                frameIconoMundodisco.setBackgroundResource(R.color.naranja_claro);
                frameIconoNavecita.setBackgroundResource(R.color.gris_oscuro);
                frameIconoSuperman.setBackgroundResource(R.color.gris_oscuro);
            }
        });

        iconoEiffel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                icono = EIFFEL;
                frameIconoTerror.setBackgroundResource(R.color.gris_oscuro);
                frameIconoDragon.setBackgroundResource(R.color.gris_oscuro);
                frameIconoEiffel.setBackgroundResource(R.color.naranja_claro);
                frameIconoMundodisco.setBackgroundResource(R.color.gris_oscuro);
                frameIconoNavecita.setBackgroundResource(R.color.gris_oscuro);
                frameIconoSuperman.setBackgroundResource(R.color.gris_oscuro);
            }
        });
    }

    public void addChip() {
        int maxChips = 6;
        int maxChars = 20;
        if (!editTextLabels.getText().toString().equals("")) {
            if (editTextLabels.getText().toString().length() <= maxChars) {
                if (chipGroup.getChildCount() < maxChips) {
                    textViewAviso.setVisibility(View.INVISIBLE);
                    Chip chip = new Chip(this);
                    chip.setText(editTextLabels.getText().toString());
                    chip.setCloseIconVisible(true);
                    chip.setCloseIconTintResource(R.color.fondos);
                    chip.setChipBackgroundColorResource(R.color.naranja_claro);
                    int fondos = getResources().getColor(R.color.fondos);
                    chip.setTextColor(fondos);
                    chip.setOnCloseIconClickListener(v1 -> {
                        chipGroup.removeView(chip);
                        if (chipGroup.getChildCount() < maxChips) {
                            textViewAviso.setVisibility(View.INVISIBLE);
                        }
                    });
                    chipGroup.addView(chip);
                    editTextLabels.setText("");
                } else {
                    textViewAviso.setText(R.string.avisoEtiquetas);
                    textViewAviso.setVisibility(View.VISIBLE);
                }
            } else {
                textViewAviso.setText(R.string.avisoEtiquetasLongitud);
                textViewAviso.setVisibility(View.VISIBLE);
            }
        }
    }

    public void crearUniverso() {
        String nombre = editTextNombre.getText().toString();
        String descripcion = editTextDescripcion.getText().toString();
        String visibilidad = switchMaterial.isChecked() ? "publico" : "privado";
        int maxCharsDesc = 150;
        int maxCharsNom = 30;
        if (icono != 0) {
            if (textViewAviso.getText().toString().equals(R.string.avisoIcono)) {
                textViewAviso.setText("");
                textViewAviso.setVisibility(View.INVISIBLE);
            }
            if (!nombre.equals("")) {
                if (textViewAviso.getText().toString().equals(R.string.avisoNombre)) {
                    textViewAviso.setText("");
                    textViewAviso.setVisibility(View.INVISIBLE);
                }
                if (descripcion.length() <= maxCharsDesc && nombre.length() <= maxCharsNom) {
                    for (int i = 0; i < chipGroup.getChildCount(); i++) {
                        Chip chip = (Chip) chipGroup.getChildAt(i);
                        listaEtiquetas.add(chip.getText().toString());
                    }
                    SharedPreferences prefs = getSharedPreferences("sesion", MODE_PRIVATE);
                    String creador = prefs.getString("id", "");
                    Universo universo = new Universo("-1", nombre, descripcion, creador, visibilidad, icono, listaEtiquetas);
                    ApiInterface apiInterface = ServiceGenerator.createService(ApiInterface.class);
                    Call<String> call = apiInterface.insertaUniverso(universo);
                    call.enqueue(new Callback<String>() {
                        @Override
                        public void onResponse(Call<String> call, Response<String> response) {
                            if (response.isSuccessful()) {
                                System.out.println("Success");
                                Intent intent = new Intent(NuevoUniverso.this, EditarUniverso.class);
                                intent.putExtra("idUniverso", response.body());
                                startActivity(intent);
                                Intent devolver = new Intent();
                                devolver.putExtra("universoNuevo", universo);
                                setResult(2, devolver);
                                finish();
                            }
                        }

                        @Override
                        public void onFailure(Call<String> call, Throwable t) {
                            Log.e("tag", t.getMessage());
                        }
                    });
                }
            } else {
                textViewAviso.setText(R.string.avisoNombre);
                textViewAviso.setVisibility(View.VISIBLE);
            }
        }else{
            textViewAviso.setText(R.string.avisoIcono);
            textViewAviso.setVisibility(View.VISIBLE);
        }
    }

}