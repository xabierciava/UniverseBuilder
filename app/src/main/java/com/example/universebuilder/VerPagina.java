package com.example.universebuilder;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.example.universebuilder.markeditor.MarkDEditor;
import com.example.universebuilder.markeditor.MarkDVisor;
import com.example.universebuilder.markeditor.datatype.DraftDataItemModel;
import com.example.universebuilder.markeditor.models.DraftModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import api.ApiInterface;
import api.ServiceGenerator;
import model.Pagina;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VerPagina extends AppCompatActivity {

    public static final int NORMAL = 0;

    MarkDVisor markDVisor;
    String idPagina;
    Pagina pagina;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_pagina);
        init();
    }

    public void init() {
        markDVisor = findViewById(R.id.visualizador);
        idPagina = getIntent().getStringExtra("idPagina");
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
        markDVisor.loadDraft(getDraftContent(contenido));
    }

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

}