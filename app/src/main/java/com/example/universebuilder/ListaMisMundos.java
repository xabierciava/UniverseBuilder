package com.example.universebuilder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;

public class ListaMisMundos extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {


    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    FragmentTransaction transaction;
    Fragment fragmentLista,fragmentEditar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_mis_mundos);

        drawerLayout = findViewById(R.id.drawer_layout_mis_mundos);
        navigationView = findViewById(R.id.nav_view_mis_mundos);
        toolbar = findViewById(R.id.toolbar_mis_mundos);

        setSupportActionBar(toolbar);

        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        navigationView.setCheckedItem(R.id.nav_lista);

        fragmentLista= new ListaFragment();
        fragmentEditar = new EditarFragment();

        getSupportFragmentManager().beginTransaction().add(R.id.contenedorFragments,fragmentLista).commit();
    }


    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        transaction=getSupportFragmentManager().beginTransaction();
        switch (menuItem.getItemId()) {
            case R.id.nav_lista:
                transaction.replace(R.id.contenedorFragments,fragmentLista);
                transaction.addToBackStack(null);
                break;
            case R.id.nav_edit_perfil:
                transaction.replace(R.id.contenedorFragments,fragmentEditar);
                transaction.addToBackStack(null);
                break;
        }
        transaction.commit();

        drawerLayout.closeDrawer(GravityCompat.START);

        return true;
    }
}