package com.universes.universebuilder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.app.Service;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import com.universes.universebuilder.markeditor.EditorControlBar;
import com.universes.universebuilder.markeditor.MarkDEditor;
import com.universes.universebuilder.markeditor.datatype.DraftDataItemModel;
import com.universes.universebuilder.markeditor.models.DraftModel;
import com.universes.universebuilder.markeditor.utilities.FilePathUtils;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import api.ApiInterface;
import api.ServiceGenerator;
import model.Pagina;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditarPagina extends AppCompatActivity implements EditorControlBar.EditorControlListener {

    public static final int NORMAL = 0;
    private final int REQUEST_IMAGE_SELECTOR = 110;
    private final int REQUEST_LINK= 5;

    EditorControlBar editorControlBar;
    MarkDEditor markDEditor;
    Button confirmar, cancelar;
    TextInputLayout textFieldLabels;
    ChipGroup chipGroup;
    EditText editTextLabels;
    String idUniverso, idPagina;
    Pagina pagina;
    List<String> listaEtiquetas;
    InputMethodManager im;
    RelativeLayout mainLayout;
    ScrollView scrollEditor;
    private String m_Text = "";
    AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_pagina);
        init();
    }

    public void init() {
        markDEditor = findViewById(R.id.mdEditorEditar);
        editorControlBar = findViewById(R.id.controlBarEditar);
        editorControlBar.setEditorControlListener(this);
        markDEditor.configureEditor(
                "https://universebuilder.live/images/",//server url for image upload
                "",              //serverToken
                true,           // isDraft: set true when you are loading draft
                "Escribe aquí...", //default hint of input box
                NORMAL
        );
        editorControlBar.setEditor(markDEditor);
        confirmar = findViewById(R.id.boton_confirmar);
        cancelar = findViewById(R.id.boton_cancelar);
        confirmar.setOnClickListener(v -> confirmarEdicion());
        cancelar.setOnClickListener(v -> cancelar());
        chipGroup = findViewById(R.id.chipGroupEditar);
        textFieldLabels = findViewById(R.id.textFieldCategoriasEditar);
        textFieldLabels.setEndIconOnClickListener(v -> addChip());
        editTextLabels = findViewById(R.id.editTextCategoríasEditar);
        idPagina = getIntent().getStringExtra("idPagina");
        idUniverso = getIntent().getStringExtra("idUniverso");
        mainLayout = findViewById(R.id.RelativeEditarPagina);
        scrollEditor = findViewById(R.id.scrollEditorEditar);
        im = (InputMethodManager) getSystemService(Service.INPUT_METHOD_SERVICE);

        cargarDatos();
    }

    public void cargarDatos() {
        ApiInterface apiInterface = ServiceGenerator.createService(ApiInterface.class);
        Call<Pagina> call = apiInterface.getPaginaId(idPagina);
        call.enqueue(new Callback<Pagina>() {
            @Override
            public void onResponse(Call<Pagina> call, Response<Pagina> response) {
                if (response.isSuccessful()) {
                    pagina = response.body();
                    try {
                        rellenarDraft();
                        markDEditor.clearFocus();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<Pagina> call, Throwable t) {
                Log.e("tag", t.getMessage());
            }
        });
    }

    private void rellenarDraft() throws JSONException {
        String contenido = pagina.getContenido();
        for (String etiqueta : pagina.getCategoria()) {
            Chip chip = new Chip(this);
            chip.setText(etiqueta);
            chip.setCloseIconVisible(true);
            chip.setChipBackgroundColorResource(R.color.naranja_claro);
            chip.setCloseIconTintResource(R.color.fondos);
            int fondos = getResources().getColor(R.color.fondos);
            chip.setTextColor(fondos);
            chip.setOnCloseIconClickListener(cerrarChip);
            chipGroup.addView(chip);
        }
        markDEditor.loadDraft(getDraftContent(contenido));
    }

    View.OnClickListener cerrarChip = new View.OnClickListener(){
        public void onClick(View v){
            chipGroup.removeView(v);
        }
    };

    private DraftModel getDraftContent(String contenido) throws JSONException {
        JSONObject json = new JSONObject();
        try {
            json = new JSONObject(contenido);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JSONArray array = (JSONArray) json.get("items");
        ArrayList<DraftDataItemModel> contentTypes = new ArrayList<>();

        for(int i = 0; i < array.length(); i++)
        {
            JSONObject object = array.getJSONObject(i);
            DraftDataItemModel item = new DraftDataItemModel();


            if(object.has("itemType")){
                item.setItemType((Integer) object.get("itemType"));
            }
            if(object.has("mode")){
                item.setMode((Integer) object.get("mode"));
            }
            if(object.has("downloadUrl")){
                item.setDownloadUrl((String) object.get("downloadUrl"));
            }
            if(object.has("caption")){
                item.setCaption((String) object.get("caption"));
            }
            if(object.has("style")){
                item.setStyle((Integer) object.get("style"));
            }
            if(object.has("content")){
                String content = (String) object.get("content");
                content = content.replaceAll("<u0001>","\"");
                content = content.replaceAll("<u0002>","\'");
                item.setContent(content);
            }
            contentTypes.add(item);
        }

        if(contentTypes.isEmpty()){
            return  new DraftModel();
        }else {
            return new DraftModel(contentTypes);
        }
    }

    @Override
    public void onInsertImageClicked() {
        openGallery();
    }

    @Override
    public void onInserLinkClicked() {
        Intent intent = new Intent(this, ElegirPagina.class);
        intent.putExtra("idUniverso",idUniverso);
        startActivityForResult(intent,REQUEST_LINK);
    }

    @Override
    public void onDeleteDraftClicked() {
        new AlertDialog.Builder(this)
                .setMessage("¿Seguro que quieres borrar todo?")
                .setCancelable(false)
                .setPositiveButton("Sí", (dialog, id) -> {
                    //Gestionar
                })
                .setNegativeButton("No", null)
                .show();
    }

    private void openGallery() {
        try {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_IMAGE_SELECTOR);
            } else {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, REQUEST_IMAGE_SELECTOR);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_SELECTOR) {
            if (resultCode == Activity.RESULT_OK && data != null && data.getData() != null) {
                Uri uri = data.getData();
                String filePath = FilePathUtils.getPath(this, uri);
                addImage(filePath);
            }
        }else if(requestCode == REQUEST_LINK){
            if (resultCode == Activity.RESULT_OK && data != null) {
                String id = data.getStringExtra("idPagina");
                builder = new AlertDialog.Builder(this,R.style.AppCompatAlertDialogStyle);
                builder.setTitle("Texto visible del link");

                // Set up the input
                final EditText input = new EditText(this);
                input.setBackgroundResource(R.color.fondos);
                input.requestFocus();
                input.setTextColor(getResources().getColor(R.color.naranja_claro));
                builder.setView(input);
                // Set up the buttons
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        m_Text = input.getText().toString();
                        markDEditor.addLink(m_Text, id);
                    }
                });
                builder.show();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_IMAGE_SELECTOR:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    openGallery();
                } else {
                    Toast.makeText(this, "No has dado permisos para acceder al almacenamiento", Toast.LENGTH_LONG).show();
                }
                break;
        }
    }

    public void confirmarEdicion() {
        String titulo = "";
        if (chipGroup.getChildCount() > 0) {
            DraftModel dm = markDEditor.getDraft();
            Gson gson = new GsonBuilder().disableHtmlEscaping().create();
            String json = gson.toJson(dm);
            System.out.println(json);
            listaEtiquetas = new ArrayList<>();
            for (int i = 0; i < chipGroup.getChildCount(); i++) {
                Chip chip = (Chip) chipGroup.getChildAt(i);
                listaEtiquetas.add(chip.getText().toString());
            }

            pagina = new Pagina(idPagina, titulo, idUniverso, json, listaEtiquetas);
            ApiInterface apiInterface = ServiceGenerator.createService(ApiInterface.class);
            Call<String> call = apiInterface.editarPagina(pagina);
            call.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    if(response.isSuccessful()){
                        Toast.makeText(getApplicationContext(), "Pagina editada con éxito", Toast.LENGTH_LONG).show();
                        finish();
                    }
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {

                }
            });
        } else {
            Toast.makeText(this, "La página debe tener al menos una categoría", Toast.LENGTH_LONG).show();

        }
    }

    public void addImage(String filePath) {
        markDEditor.insertImage(filePath);
    }


    public void addChip() {
        if (!editTextLabels.getText().toString().equals("")) {
            Chip chip = new Chip(this);
            chip.setText(editTextLabels.getText().toString());
            chip.setCloseIconVisible(true);
            chip.setCloseIconTintResource(R.color.fondos);
            chip.setChipBackgroundColorResource(R.color.naranja_claro);
            int fondos = getResources().getColor(R.color.fondos);
            chip.setTextColor(fondos);
            chip.setOnCloseIconClickListener(v1 -> {
                chipGroup.removeView(chip);
            });
            chipGroup.addView(chip);
            editTextLabels.setText("");
        }
    }

    private void cancelar() {
        finish();
    }


}
