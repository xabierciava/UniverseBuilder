package com.example.universebuilder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.WindowManager;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import api.ApiInterface;
import api.ServiceGenerator;
import model.Universo;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditarUniverso extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    BottomNavigationView navigation;
    Universo universo;
    FragmentTransaction transaction;
    Fragment ajustes,nueva,editar;

    public Fragment getNueva() {
        return nueva;
    }

    public void setNueva(Fragment nueva) {
        this.nueva = nueva;
    }



    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setMessage("¿Quieres abandonar el universo?")
                .setCancelable(false)
                .setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        finish();
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_universo);


        ajustes= new UniversoAjustesFragment();
        nueva = new NuevaPaginaFragment();
        editar = new EditarPaginasFragment();

        navigation = findViewById(R.id.bottom_navigation_editor);
        navigation.setOnNavigationItemSelectedListener(this);

        getSupportFragmentManager().beginTransaction().add(R.id.contenedor_fragments_editor_universo,editar).commit();
        navigation.setSelectedItemId(R.id.page_editar);

    }

    public Universo getUniverso(){
        return universo;
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        transaction=getSupportFragmentManager().beginTransaction();
        switch (item.getItemId()) {
            case R.id.page_ajustes:
                transaction.replace(R.id.contenedor_fragments_editor_universo,ajustes);
                break;
            case R.id.page_nueva:
                transaction.replace(R.id.contenedor_fragments_editor_universo,nueva);
                break;
            case R.id.page_editar:
                transaction.replace(R.id.contenedor_fragments_editor_universo,editar);
                break;
        }
        transaction.commit();
        return true;
    }
}