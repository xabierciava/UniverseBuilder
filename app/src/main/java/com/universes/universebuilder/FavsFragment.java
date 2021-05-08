package com.universes.universebuilder;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import api.ApiInterface;
import api.ServiceGenerator;
import model.FichaUniversoExplorar;
import model.Universo;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FavsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FavsFragment extends Fragment implements ListAdapterExplorarUniversos.OnUniverseListener{

    List<FichaUniversoExplorar> elements;
    RecyclerView recyclerView;
    List<Universo> listaUniversos;
    ListAdapterExplorarUniversos listAdapterExplorarUniversos;



    public FavsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * @return A new instance of fragment FavsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FavsFragment newInstance(String param1, String param2) {
        FavsFragment fragment = new FavsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_favs, container, false);
        init(view);
        return view;
    }

    private void init(View view){
        recyclerView = view.findViewById(R.id.recyclerFavoritos);
        listaUniversos = new ArrayList<>();
        elements = new ArrayList<>();
        String idUsuario;
        SharedPreferences prefs = Objects.requireNonNull(getActivity()).getSharedPreferences("sesion", Context.MODE_PRIVATE);
        idUsuario = prefs.getString("id","");
        ApiInterface apiInterface = ServiceGenerator.createService(ApiInterface.class);
        Call<List<Universo>> call = apiInterface.getUniversosFavoritos(idUsuario);
        call.enqueue(new Callback<List<Universo>>() {
            @Override
            public void onResponse(Call<List<Universo>> call, Response<List<Universo>> response) {
                assert response.body() != null;
                listaUniversos.addAll(response.body());
                for (Universo universo: listaUniversos){
                    elements.add(new FichaUniversoExplorar(universo.getIcono(),universo.getNombre(),universo.getDescripcion(),universo.getId(),true));
                }
                listAdapterExplorarUniversos = new ListAdapterExplorarUniversos(elements, requireContext(),FavsFragment.this);
                recyclerView.setHasFixedSize(true);
                recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
                recyclerView.setAdapter(listAdapterExplorarUniversos);
            }

            @Override
            public void onFailure(Call<List<Universo>> call, Throwable t) {

            }
        });
    }

    @Override
    public void onUniverseClick(int position) {
        FichaUniversoExplorar universo = elements.get(position);
        Intent intent = new Intent(getActivity(),VerUniverso.class);
        intent.putExtra("idUniverso",universo.getId());
        startActivity(intent);
    }
}