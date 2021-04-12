package com.example.universebuilder;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import api.ApiInterface;
import api.ServiceGenerator;
import model.Universo;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ListaFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ListaFragment extends Fragment implements ListAdapter.OnUniverseListener {

    List<FichaUniverso> elements;
    RecyclerView recyclerView;
    ImageView imagenPlaneta;
    List<Universo> listaUniversos;
    ListAdapter listAdapter;
    View view;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ListaFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ListaFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ListaFragment newInstance(String param1, String param2) {
        ListaFragment fragment = new ListaFragment();
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        view = inflater.inflate(R.layout.fragment_lista, container, false);
        recyclerView = view.findViewById(R.id.listRecyclerView);
        imagenPlaneta = view.findViewById(R.id.imagen_planeta_fab);
        imagenPlaneta.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), NuevoUniverso.class);
            startActivity(intent);
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        refrescarLista();
    }

    public void refrescarLista(){
        elements=new ArrayList<>();
        SharedPreferences prefs = getActivity().getSharedPreferences("sesion", MODE_PRIVATE);
        String id = prefs.getString("id","");
        listaUniversos = new ArrayList<>();
        ApiInterface apiInterface = ServiceGenerator.createService(ApiInterface.class);
        Call<List<Universo>> call = apiInterface.getUniversoUsuario(id);
        call.enqueue(new Callback<List<Universo>>() {
            @Override
            public void onResponse(Call<List<Universo>> call, Response<List<Universo>> response) {
                if(response.isSuccessful()){
                    listaUniversos.addAll(response.body());
                    elements = new ArrayList<>();
                    for (Universo universo: listaUniversos){
                        elements.add(new FichaUniverso("#6F3D99",universo.getNombre(),universo.getDescripcion(),universo.getId()));
                        listAdapter = new ListAdapter(elements, view.getContext(),ListaFragment.this);
                        recyclerView.setHasFixedSize(true);
                        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
                        recyclerView.setAdapter(listAdapter);
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Universo>> call, Throwable t) {
                Log.e("tag", t.getMessage());
            }
        });
    }

    @Override
    public void onUniverseClick(int position) {
        FichaUniverso universo = elements.get(position);
        Intent intent = new Intent(getActivity(),EditarUniverso.class);
        intent.putExtra("idUniverso",universo.getId());
        startActivity(intent);
    }
}