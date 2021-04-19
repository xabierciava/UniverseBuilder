package com.example.universebuilder;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UniversoAjustesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UniversoAjustesFragment extends Fragment {

    TextInputLayout textFieldLabels;
    EditText editTextLabels;
    ChipGroup chipGroup;
    TextView textViewAviso;
    Button botonEditarUniverso;
    EditText editTextNombre,editTextDescripcion;
    SwitchMaterial switchMaterial;
    List<String> listaEtiquetas;
    String idUniverso;
    Universo universo;
    int maxChips;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public UniversoAjustesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment UniversoAjustesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static UniversoAjustesFragment newInstance(String param1, String param2) {
        UniversoAjustesFragment fragment = new UniversoAjustesFragment();
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
        View view = inflater.inflate(R.layout.fragment_universo_ajustes, container, false);
        init(view);
        textFieldLabels.setEndIconOnClickListener(v -> addChip());
        editTextLabels.setOnKeyListener((v, keyCode, event) -> {
            if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                    (keyCode == KeyEvent.KEYCODE_ENTER)) {
                addChip();
                return true;
            }
            return false;
        });
        botonEditarUniverso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cambiarDatos();
            }
        });

        return view;
    }

    public void init(View view){
        textFieldLabels = view.findViewById(R.id.outlinedTextFieldEtiquetasEditar);
        editTextLabels = view.findViewById(R.id.textInputEditTextEtiquetasEditar);
        chipGroup = view.findViewById(R.id.chipGroupEditar);
        textViewAviso = view.findViewById(R.id.texto_aviso_editar);
        botonEditarUniverso = view.findViewById(R.id.botonEditarUniverso);
        editTextNombre = view.findViewById(R.id.textInputEditTextNombreUniversoEditar);
        editTextDescripcion= view.findViewById(R.id.textInputEditTextDescripcionUniversoEditar);
        switchMaterial = view.findViewById(R.id.switchMaterialEditar);
        listaEtiquetas= new ArrayList<>();

        idUniverso = getActivity().getIntent().getStringExtra("idUniverso");
        ApiInterface apiInterface = ServiceGenerator.createService(ApiInterface.class);
        Call<Universo> call = apiInterface.getUniversoId(idUniverso);
        call.enqueue(new Callback<Universo>() {
            @Override
            public void onResponse(Call<Universo> call, Response<Universo> response) {
                if(response.isSuccessful()){
                    universo = response.body();
                    editTextNombre.setText(universo.getNombre());
                    editTextDescripcion.setText(universo.getDescripcion());
                    switchMaterial.setChecked(universo.getVisibilidad().equals("publico"));
                    ApiInterface apiInterfaceEtiquetas = ServiceGenerator.createService(ApiInterface.class);
                    Call<List<String>> callEtiquetas = apiInterfaceEtiquetas.getEtiquetasId(idUniverso);
                    callEtiquetas.enqueue(new Callback<List<String>>() {
                        @Override
                        public void onResponse(Call<List<String>> call, Response<List<String>> response) {
                            if(response.isSuccessful()){
                                System.out.println("Success");
                                universo.setListaEtiquetas(response.body());
                                for(String etiqueta: universo.getListaEtiquetas()){
                                    Context thisContext = getContext();
                                    if(thisContext!=null){
                                        Chip chip = new Chip(getContext());
                                        chip.setText(etiqueta);
                                        chip.setCloseIconVisible(true);
                                        chip.setChipBackgroundColorResource(R.color.naranja_claro);
                                        chip.setCloseIconTintResource(R.color.fondos);
                                        int fondos = getResources().getColor(R.color.fondos);
                                        chip.setTextColor(fondos);
                                        chip.setOnCloseIconClickListener(cerrarChip);
                                        chipGroup.addView(chip);
                                    }else{
                                        System.out.println("||||||||||||error contexto|||||||||||");
                                    }

                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<List<String>> call, Throwable t) {
                            Log.e("tag", t.getMessage());
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<Universo> call, Throwable t) {
                Log.e("tag", t.getMessage());
            }
        });
    }

    public void cambiarDatos(){
        universo.setDescripcion(editTextDescripcion.getText().toString());
        universo.setNombre(editTextNombre.getText().toString());
        List<String> listaEtiquetasEditadas = new ArrayList<>();
        for (int i = 0; i < chipGroup.getChildCount(); i++) {
            Chip chip = (Chip) chipGroup.getChildAt(i);
            listaEtiquetasEditadas.add(chip.getText().toString());
        }
        universo.setListaEtiquetas(listaEtiquetasEditadas);
        universo.setVisibilidad(switchMaterial.isChecked()?"publico":"privado");

        ApiInterface apiInterface = ServiceGenerator.createService(ApiInterface.class);
        Call<String> call = apiInterface.editarUniverso(universo);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()){
                    System.out.println("Success");
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.e("tag", t.getMessage());
            }
        });
    }

    public void addChip() {
        maxChips = 6;
        int maxChars = 20;
        if (!editTextLabels.getText().toString().equals("")) {
            if (editTextLabels.getText().toString().length() <= maxChars) {
                if (chipGroup.getChildCount() < maxChips) {
                    textViewAviso.setVisibility(View.INVISIBLE);
                    Chip chip = new Chip(requireContext());
                    chip.setText(editTextLabels.getText().toString());
                    chip.setCloseIconVisible(true);
                    chip.setCloseIconTintResource(R.color.fondos);
                    chip.setChipBackgroundColorResource(R.color.naranja_claro);
                    int fondos = getResources().getColor(R.color.fondos);
                    chip.setTextColor(fondos);
                    chip.setOnCloseIconClickListener(cerrarChip);
                    chipGroup.addView(chip);
                    editTextLabels.setText("");
                } else {
                    textViewAviso.setText(R.string.avisoEtiquetas);
                    textViewAviso.setVisibility(View.VISIBLE);
                }
            }else{
                textViewAviso.setText(R.string.avisoEtiquetasLongitud);
                textViewAviso.setVisibility(View.VISIBLE);
            }
        }
    }

    View.OnClickListener cerrarChip = new View.OnClickListener(){
        public void onClick(View v){
            chipGroup.removeView(v);
            if (chipGroup.getChildCount() < maxChips) {
                textViewAviso.setVisibility(View.INVISIBLE);
            }
        }
    };
}