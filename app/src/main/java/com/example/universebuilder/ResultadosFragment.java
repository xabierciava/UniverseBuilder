package com.example.universebuilder;

import android.content.Context;
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
 * Use the {@link ResultadosFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ResultadosFragment extends Fragment implements ListAdapterExplorarUniversos.OnUniverseListener{

    List<FichaUniversoExplorar> elements;
    RecyclerView recyclerView;
    List<Universo> listaUniversos;
    ListAdapterExplorarUniversos listAdapterExplorarUniversos;


    private static final String ARG_PARAM1 = "query";

    private String query;

    public ResultadosFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @return A new instance of fragment ResultadosFragment.
     */
    public static ResultadosFragment newInstance(String param1) {
        ResultadosFragment fragment = new ResultadosFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            query = getArguments().getString(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_resultados, container, false);
        init(view);
        return view;
    }

    private void init(View view){
        recyclerView = view.findViewById(R.id.recyclerResultadosBusqueda);
        listaUniversos = new ArrayList<>();
        elements = new ArrayList<>();
        ApiInterface apiInterface = ServiceGenerator.createService(ApiInterface.class);
        Call<List<Universo>> call = apiInterface.getUniversosQuery(query);
        call.enqueue(new Callback<List<Universo>>() {
            @Override
            public void onResponse(Call<List<Universo>> call, Response<List<Universo>> response) {
                if(response.isSuccessful()){
                    String idUsuario;
                    assert response.body() != null;
                    listaUniversos.addAll(response.body());
                    SharedPreferences prefs = requireActivity().getSharedPreferences("sesion", Context.MODE_PRIVATE);
                    idUsuario = prefs.getString("id","");
                    ApiInterface apiInterface = ServiceGenerator.createService(ApiInterface.class);
                    Call<List<String>> callFavs = apiInterface.getFavoritos(idUsuario);
                    callFavs.enqueue(new Callback<List<String>>() {
                        @Override
                        public void onResponse(Call<List<String>> call, Response<List<String>> response) {
                            assert response.body() != null;
                            List<String> favoritos = new ArrayList<>(response.body());
                            for (Universo universo: listaUniversos){
                                elements.add(new FichaUniversoExplorar(universo.getIcono(),universo.getNombre(),universo.getDescripcion(),universo.getId(),favoritos.contains(universo.getId())));
                            }
                            listAdapterExplorarUniversos = new ListAdapterExplorarUniversos(elements, requireContext(),ResultadosFragment.this);
                            recyclerView.setHasFixedSize(true);
                            recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
                            recyclerView.setAdapter(listAdapterExplorarUniversos);
                        }

                        @Override
                        public void onFailure(Call<List<String>> call, Throwable t) {

                        }
                    });


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
        FichaUniversoExplorar universo = elements.get(position);
        Intent intent = new Intent(getActivity(),VerUniverso.class);
        intent.putExtra("idUniverso",universo.getId());
        startActivity(intent);
    }
}