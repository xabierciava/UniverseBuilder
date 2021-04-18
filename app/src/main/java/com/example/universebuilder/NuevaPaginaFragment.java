package com.example.universebuilder;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.universebuilder.markeditor.EditorControlBar;
import com.example.universebuilder.markeditor.MarkDEditor;
import com.example.universebuilder.markeditor.datatype.DraftDataItemModel;
import com.example.universebuilder.markeditor.models.DraftModel;
import com.example.universebuilder.markeditor.utilities.FilePathUtils;
import com.google.gson.Gson;

import java.util.ArrayList;



/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NuevaPaginaFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NuevaPaginaFragment extends Fragment implements EditorControlBar.EditorControlListener {

    public static final int NORMAL = 0;
    private final int REQUEST_IMAGE_SELECTOR = 110;

    EditorControlBar editorControlBar;
    MarkDEditor markDEditor;
    Button publicar;

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
        View view =inflater.inflate(R.layout.fragment_nueva_pagina, container, false);
        init(view);
        return view;
    }

    public void init(View view){
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
        }
        catch (Exception e) {
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
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_IMAGE_SELECTOR:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    openGallery();
                } else {
                    //do something like displaying a message that he didn`t allow the app to access gallery and you wont be able to let him select from gallery
                    Toast.makeText(requireContext(), "No has dado permisos para acceder al almacenamiento", Toast.LENGTH_LONG).show();
                }
                break;
        }
    }

    public void publicarPagina(){
        DraftModel dm = markDEditor.getDraft();
        String json = new Gson().toJson(dm);
        Log.d("MarkDEditor", json);
    }

    public void addImage(String filePath) {
        markDEditor.insertImage(filePath);
    }
}