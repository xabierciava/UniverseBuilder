package com.example.universebuilder;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import api.ApiInterface;
import api.ServiceGenerator;
import model.FichaPagina;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EditarPaginasFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EditarPaginasFragment extends Fragment implements ListaPaginasEditarAdapter.OnPaginaListener {

    String idUniverso;
    List<FichaPagina> elements;
    RecyclerView recyclerView;
    ListaPaginasEditarAdapter listAdapterPaginas;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public EditarPaginasFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EditarPaginasFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static EditarPaginasFragment newInstance(String param1, String param2) {
        EditarPaginasFragment fragment = new EditarPaginasFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_editar_paginas, container, false);
        init(view);
        setHasOptionsMenu(true);
        return view;
    }

    public void init(View view){
        idUniverso = getActivity().getIntent().getStringExtra("idUniverso");
        elements = new ArrayList<>();
        recyclerView = view.findViewById(R.id.recyclerEditarPaginas);
        ApiInterface apiInterface = ServiceGenerator.createService(ApiInterface.class);
        Call<List<FichaPagina>> call = apiInterface.getPaginaUniverso(idUniverso);
        call.enqueue(new Callback<List<FichaPagina>>() {
            @Override
            public void onResponse(Call<List<FichaPagina>> call, Response<List<FichaPagina>> response) {
                if (response.isSuccessful()){
                    elements=response.body();
                    listAdapterPaginas = new ListaPaginasEditarAdapter(elements, requireContext(),EditarPaginasFragment.this);
                    recyclerView.setHasFixedSize(true);
                    recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
                    recyclerView.setAdapter(listAdapterPaginas);
                }
            }

            @Override
            public void onFailure(Call<List<FichaPagina>> call, Throwable t) {
                Log.e("tag", t.getMessage());
            }
        });
        EditText buscarEditText = view.findViewById(R.id.editTextBuscar);
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
    public void onPaginaClick(int position){
        FichaPagina pagina = elements.get(position);
        Intent intent = new Intent(requireActivity(), EditarPagina.class);
        intent.putExtra("idPagina",pagina.getId());
        intent.putExtra("idUniverso",idUniverso);
        startActivity(intent);
    }



}