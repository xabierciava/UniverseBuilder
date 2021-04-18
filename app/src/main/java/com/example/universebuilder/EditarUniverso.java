package com.example.universebuilder;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.DialogInterface;
import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import api.ApiInterface;
import api.ServiceGenerator;
import model.Universo;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditarUniverso extends AppCompatActivity {

    String idUniverso;
    BottomNavigationView navigation;
    Universo universo;
    Boolean flagNueva=false;
    Fragment ajustes,nueva,editar;
    private enum NavigationFragment{
        Ajustes,
        Nueva,
        Editar
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = item -> {

                    switch (item.getItemId()) {
                        case R.id.page_ajustes:
                            ChangeFragment(NavigationFragment.Ajustes);
                            return true;
                        case R.id.page_nueva:
                            ChangeFragment(NavigationFragment.Nueva);
                            return true;
                        case R.id.page_editar:
                            ChangeFragment(NavigationFragment.Editar);
                            return true;
                    }
                return false;
            };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_universo);

        ajustes= new UniversoAjustesFragment();
        nueva = new NuevaPaginaFragment();
        editar = new EditarPaginasFragment();

        navigation = findViewById(R.id.bottom_navigation_editor);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        ChangeFragment(NavigationFragment.Editar);
        navigation.setSelectedItemId(R.id.page_editar);

    }

    public Universo getUniverso(){
        return universo;
    }


    private void ChangeFragment(NavigationFragment value){
        Fragment fragment = null;
        switch (value) {
            case Ajustes:
                fragment = ajustes;
                break;
            case Nueva:
                fragment = nueva;
                break;
            case Editar:
                fragment= editar;
                break;
        }
        if(fragment!=null)
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.contenedor_fragments_editor_universo, fragment)
                    .commit();

    }

}