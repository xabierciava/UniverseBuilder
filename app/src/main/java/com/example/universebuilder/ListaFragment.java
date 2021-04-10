package com.example.universebuilder;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ListaFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ListaFragment extends Fragment implements ListAdapter.OnUniverseListener {

    List<FichaUniverso> elements;
    RecyclerView recyclerView;
    ImageView imagenPlaneta;

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
        init();
        View view = inflater.inflate(R.layout.fragment_lista, container, false);
        recyclerView = view.findViewById(R.id.listRecyclerView);
        ListAdapter listAdapter = new ListAdapter(elements, view.getContext(),this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recyclerView.setAdapter(listAdapter);
        imagenPlaneta = view.findViewById(R.id.imagen_planeta_fab);
        imagenPlaneta.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), NuevoUniverso.class);
            startActivity(intent);
        });

        return view;
    }

    public void init(){
        elements = new ArrayList<>();
        elements.add(new FichaUniverso("#A8913E","Revo", "Idoia Cía"));
        elements.add(new FichaUniverso("#F5D973","Ventalia", "Xabier Cía"));
        elements.add(new FichaUniverso("#FFD12D","Juego de tronos", "George RR Martin"));
        elements.add(new FichaUniverso("#2D39A8","Star Trek", "Spock"));
        elements.add(new FichaUniverso("#737FF5","Star Wars", "George Lucas"));
        elements.add(new FichaUniverso("#737FF5","Star Wars", "George Lucas"));
        elements.add(new FichaUniverso("#737FF5","Star Wars", "George Lucas"));
        elements.add(new FichaUniverso("#737FF5","Star Wars", "George Lucas"));
    }

    @Override
    public void onUniverseClick(int position) {
        FichaUniverso universo = elements.get(position);
        System.out.println("||||||||||||||||||||"+universo.getUniverso()+"|||||||||||||||||||||");
    }
}