package com.example.universebuilder;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

import api.ApiInterface;
import api.ServiceGenerator;
import model.FichaPagina;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VerUniverso extends AppCompatActivity implements ListaPaginasEditarAdapter.OnPaginaListener{

    String idUniverso;
    List<FichaPagina> elements;
    RecyclerView recyclerView;
    ListaPaginasEditarAdapter listAdapterPaginas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_universo);
        init();
    }

    public void init(){
        idUniverso = getIntent().getStringExtra("idUniverso");
        elements = new ArrayList<>();
        recyclerView = findViewById(R.id.recyclerEditarPaginas);
        ApiInterface apiInterface = ServiceGenerator.createService(ApiInterface.class);
        Call<List<FichaPagina>> call = apiInterface.getPaginaUniverso(idUniverso);
        call.enqueue(new Callback<List<FichaPagina>>() {
            @Override
            public void onResponse(Call<List<FichaPagina>> call, Response<List<FichaPagina>> response) {
                if (response.isSuccessful()){
                    elements=response.body();
                    listAdapterPaginas = new ListaPaginasEditarAdapter(elements, getApplicationContext(),VerUniverso.this);
                    recyclerView.setHasFixedSize(true);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                    recyclerView.setAdapter(listAdapterPaginas);
                }
            }

            @Override
            public void onFailure(Call<List<FichaPagina>> call, Throwable t) {
                Log.e("tag", t.getMessage());
            }
        });
        EditText buscarEditText = findViewById(R.id.editTextBuscar);
        buscarEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                listAdapterPaginas.getFilter().filter(s.toString());
            }
        });
    }

    @Override
    public void onPaginaClick(int position) {
        FichaPagina pagina = elements.get(position);
        Intent intent = new Intent(this, VerPagina.class);
        intent.putExtra("idPagina",pagina.getId());
        startActivity(intent);
    }
}