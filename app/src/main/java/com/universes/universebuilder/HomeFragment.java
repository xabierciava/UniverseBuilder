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

import api.ApiInterface;
import api.ServiceGenerator;
import model.FichaUniversoExplorar;
import model.Universo;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    List<FichaUniversoExplorar> elementsPopulares,elementsExplorar;
    RecyclerView recyclerViewPopulares,recyclerViewExplorar;
    List<Universo> listaUniversosPopulares,listaUniversosExplorar;
    ListAdapterExplorarUniversos listAdapterUniversosPopulares,listAdapterUniversosExplorar;





    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        init(view);
        return view;
    }

    public void init(View view){
        recyclerViewPopulares = view.findViewById(R.id.listaPopulares);
        listaUniversosPopulares = new ArrayList<>();
        elementsPopulares = new ArrayList<>();
        recyclerViewExplorar = view.findViewById(R.id.listaExplorar);
        listaUniversosExplorar = new ArrayList<>();
        elementsExplorar = new ArrayList<>();
        ApiInterface apiInterface = ServiceGenerator.createService(ApiInterface.class);
        Call<List<Universo>> call = apiInterface.getUniversosPopulares();
        call.enqueue(new Callback<List<Universo>>() {
            @Override
            public void onResponse(Call<List<Universo>> call, Response<List<Universo>> response) {
                if(response.isSuccessful()){
                    String idUsuario;
                    listaUniversosPopulares.addAll(response.body());
                    SharedPreferences prefs = getActivity().getSharedPreferences("sesion", Context.MODE_PRIVATE);
                    idUsuario = prefs.getString("id","");
                    ApiInterface apiInterface = ServiceGenerator.createService(ApiInterface.class);
                    Call<List<String>> callFavs = apiInterface.getFavoritos(idUsuario);
                    callFavs.enqueue(new Callback<List<String>>() {
                        @Override
                        public void onResponse(Call<List<String>> call, Response<List<String>> response) {
                            assert response.body() != null;
                            List<String> favoritos = new ArrayList<>(response.body());
                            for (Universo universo: listaUniversosPopulares){
                                elementsPopulares.add(new FichaUniversoExplorar(universo.getIcono(),universo.getNombre(),universo.getDescripcion(),universo.getId(),favoritos.contains(universo.getId())));
                            }
                            listAdapterUniversosPopulares = new ListAdapterExplorarUniversos(elementsPopulares, requireContext(), new ListAdapterExplorarUniversos.OnUniverseListener() {
                                @Override
                                public void onUniverseClick(int position) {
                                    FichaUniversoExplorar universo = elementsPopulares.get(position);
                                    Intent intent = new Intent(getActivity(),VerUniverso.class);
                                    intent.putExtra("idUniverso",universo.getId());
                                    startActivity(intent);
                                }
                            });
                            recyclerViewPopulares.setHasFixedSize(true);
                            recyclerViewPopulares.setLayoutManager(new LinearLayoutManager(requireContext()));
                            recyclerViewPopulares.setAdapter(listAdapterUniversosPopulares);
                        }

                        @Override
                        public void onFailure(Call<List<String>> call, Throwable t) {

                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<List<Universo>> call, Throwable t) {

            }
        });


        Call<List<Universo>> callExplorar = apiInterface.getUniversosAleatorios();
        callExplorar.enqueue(new Callback<List<Universo>>() {
            @Override
            public void onResponse(Call<List<Universo>> call, Response<List<Universo>> response) {
                String idUsuario;
                listaUniversosExplorar.addAll(response.body());
                SharedPreferences prefs = getActivity().getSharedPreferences("sesion", Context.MODE_PRIVATE);
                idUsuario = prefs.getString("id","");
                ApiInterface apiInterface = ServiceGenerator.createService(ApiInterface.class);
                Call<List<String>> callExpl = apiInterface.getFavoritos(idUsuario);
                callExpl.enqueue(new Callback<List<String>>() {
                    @Override
                    public void onResponse(Call<List<String>> call, Response<List<String>> response) {
                        assert response.body() != null;
                        List<String> favoritos = new ArrayList<>(response.body());
                        for (Universo universo: listaUniversosExplorar){
                            elementsExplorar.add(new FichaUniversoExplorar(universo.getIcono(),universo.getNombre(),universo.getDescripcion(),universo.getId(),favoritos.contains(universo.getId())));
                        }
                        listAdapterUniversosExplorar = new ListAdapterExplorarUniversos(elementsExplorar, requireContext(), new ListAdapterExplorarUniversos.OnUniverseListener() {
                            @Override
                            public void onUniverseClick(int position) {
                                FichaUniversoExplorar universo = elementsExplorar.get(position);
                                Intent intent = new Intent(getActivity(),VerUniverso.class);
                                intent.putExtra("idUniverso",universo.getId());
                                startActivity(intent);
                            }
                        });
                        recyclerViewExplorar.setHasFixedSize(true);
                        recyclerViewExplorar.setLayoutManager(new LinearLayoutManager(requireContext()));
                        recyclerViewExplorar.setAdapter(listAdapterUniversosExplorar);
                    }

                    @Override
                    public void onFailure(Call<List<String>> call, Throwable t) {

                    }
                });
            }

            @Override
            public void onFailure(Call<List<Universo>> call, Throwable t) {

            }
        });
    }
}