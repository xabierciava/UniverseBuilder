package com.example.universebuilder;

import android.Manifest;
import android.app.Activity;
import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.universebuilder.markeditor.EditorControlBar;
import com.example.universebuilder.markeditor.MarkDEditor;
import com.example.universebuilder.markeditor.datatype.DraftDataItemModel;
import com.example.universebuilder.markeditor.models.DraftModel;
import com.example.universebuilder.markeditor.utilities.FilePathUtils;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import api.ApiInterface;
import api.ServiceGenerator;
import model.FichaUniverso;
import model.Pagina;
import model.Universo;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NuevaPaginaFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NuevaPaginaFragment extends Fragment implements EditorControlBar.EditorControlListener {

    public static final int NORMAL = 0;
    private final int REQUEST_IMAGE_SELECTOR = 110;
    private final int REQUEST_LINK= 5;

    EditorControlBar editorControlBar;
    MarkDEditor markDEditor;
    Button publicar;
    TextInputLayout textFieldLabels;
    ChipGroup chipGroup;
    EditText editTextLabels, editTextTitulo;
    String idUniverso;
    Pagina pagina;
    List<String> listaEtiquetas;
    InputMethodManager im;
    FrameLayout mainLayout;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public NuevaPaginaFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NuevaPaginaFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NuevaPaginaFragment newInstance(String param1, String param2) {
        NuevaPaginaFragment fragment = new NuevaPaginaFragment();
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
        View view = inflater.inflate(R.layout.fragment_nueva_pagina, container, false);
        init(view);
        return view;
    }

    public void init(View view) {
        markDEditor = view.findViewById(R.id.mdEditor);
        editorControlBar = view.findViewById(R.id.controlBar);
        editorControlBar.setEditorControlListener(NuevaPaginaFragment.this);
        markDEditor.configureEditor(
                "https://universebuilder.live/images/",//server url for image upload
                "",              //serverToken
                true,           // isDraft: set true when you are loading draft
                "Escribe aquí...", //default hint of input box
                NORMAL
        );
        markDEditor.loadDraft(getDraftContent());
        editorControlBar.setEditor(markDEditor);
        publicar = view.findViewById(R.id.boton_publicar);
        publicar.setOnClickListener(v -> publicarPagina());
        chipGroup = view.findViewById(R.id.chipGroupNueva);
        textFieldLabels = view.findViewById(R.id.textFieldCategoriasNueva);
        textFieldLabels.setEndIconOnClickListener(v -> addChip());
        editTextLabels = view.findViewById(R.id.editTextCategoríasNueva);
        idUniverso = getActivity().getIntent().getStringExtra("idUniverso");
        editTextTitulo = view.findViewById(R.id.editTextTituloNueva);
        mainLayout = view.findViewById(R.id.frameNuevaPagina);
        im = (InputMethodManager) requireActivity().getSystemService(Service.INPUT_METHOD_SERVICE);
    }

    private DraftModel getDraftContent() {
        ArrayList<DraftDataItemModel> contentTypes = new ArrayList<>();
        return new DraftModel(contentTypes);
    }

    @Override
    public void onInsertImageClicked() {
        openGallery();
    }

    @Override
    public void onInserLinkClicked() {
        Intent intent = new Intent(getActivity(), ElegirPagina.class);
        intent.putExtra("idUniverso",idUniverso);
        startActivityForResult(intent,REQUEST_LINK);
    }

    @Override
    public void onDeleteDraftClicked() {
        new AlertDialog.Builder(requireContext())
                .setMessage("¿Seguro que quieres borrar todo?")
                .setCancelable(false)
                .setPositiveButton("Sí", (dialog, id) -> {
                    EditarUniverso madre = (EditarUniverso) requireActivity();
                    Fragment nueva = new NuevaPaginaFragment();
                    madre.setNueva(nueva);
                    madre.getSupportFragmentManager().beginTransaction().add(R.id.contenedor_fragments_editor_universo,nueva).commit();
                })
                .setNegativeButton("No", null)
                .show();
    }

    private void openGallery() {
        try {
            if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(requireActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_IMAGE_SELECTOR);
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
                String filePath = FilePathUtils.getPath(requireContext(), uri);
                addImage(filePath);
            }
        }else if(requestCode == REQUEST_LINK){
            if (resultCode == Activity.RESULT_OK && data != null) {
                String id = data.getStringExtra("idPagina");
                markDEditor.addLink("Escribe lo que quieras", id);
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
                    Toast.makeText(requireContext(), "No has dado permisos para acceder al almacenamiento", Toast.LENGTH_LONG).show();
                }
                break;
        }
    }

    public void publicarPagina() {
        String titulo = editTextTitulo.getText().toString();
        if (!titulo.equals("") && chipGroup.getChildCount()>0) {
            DraftModel dm = markDEditor.getDraft();
            Gson gson = new GsonBuilder().create();
            String json = gson.toJson(dm);
            listaEtiquetas = new ArrayList<>();
            for (int i = 0; i < chipGroup.getChildCount(); i++) {
                Chip chip = (Chip) chipGroup.getChildAt(i);
                listaEtiquetas.add(chip.getText().toString());
            }

            pagina = new Pagina("placeholder", titulo, idUniverso, json, listaEtiquetas);
            ApiInterface apiInterface = ServiceGenerator.createService(ApiInterface.class);
            Call<String> call = apiInterface.insertaPagina(pagina);
            call.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    if (response.isSuccessful()) {
                        if(!response.body().equals("-1")){
                            Toast toast1 =
                                    Toast.makeText(requireContext(),
                                            "Página subida exitosamente", Toast.LENGTH_LONG);
                            toast1.show();
                        }else{
                            Toast toast1 =
                                    Toast.makeText(requireContext(),
                                            "Ya existe otra página con el mismo título", Toast.LENGTH_LONG);
                            toast1.show();
                        }

                    }
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    Log.e("tag", t.getMessage());
                }
            });
        }else{
            if (chipGroup.getChildCount()==0){
                Toast.makeText(requireContext(), "La página debe tener al menos una categoría", Toast.LENGTH_LONG).show();
            }else{
                Toast.makeText(requireContext(), "Ponle título a la página", Toast.LENGTH_LONG).show();
            }

        }
    }

    public void addImage(String filePath) {
        markDEditor.insertImage(filePath);
    }


    public void addChip() {
        if (!editTextLabels.getText().toString().equals("")) {
            Chip chip = new Chip(requireContext());
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
}