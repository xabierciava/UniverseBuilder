package com.universes.universebuilder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

public class ListaExplorar extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, MaterialSearchView.OnQueryTextListener {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    FragmentTransaction transaction;
    Fragment fragmentHome,fragmentFavs,fragmentResultados;
    MaterialSearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_explorar);

        drawerLayout = findViewById(R.id.drawer_layout_explorar);
        navigationView = findViewById(R.id.nav_view_explorar);
        toolbar = findViewById(R.id.toolbar_explorar);

        setSupportActionBar(toolbar);

        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        navigationView.setCheckedItem(R.id.nav_home);

        fragmentFavs = new FavsFragment();
        fragmentHome = new HomeFragment();


        getSupportFragmentManager().beginTransaction().add(R.id.contenedorFragments,fragmentHome).commit();

        getSupportActionBar().setTitle("Inicio");

        searchView = findViewById(R.id.search_view);
        searchView.setOnQueryTextListener(this);

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
            case R.id.nav_home:
                transaction.replace(R.id.contenedorFragments,fragmentHome);
                getSupportActionBar().setTitle("Inicio");
                break;
            case R.id.nav_favs:
                transaction.replace(R.id.contenedorFragments,fragmentFavs);
                getSupportActionBar().setTitle("Favoritos");
                break;

        }
        transaction.commit();

        drawerLayout.closeDrawer(GravityCompat.START);

        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search,menu);
        MenuItem item = menu.findItem(R.id.action_search);
        searchView.setMenuItem(item);
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        fragmentResultados = ResultadosFragment.newInstance(query);
        transaction=getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.contenedorFragments,fragmentResultados);
        transaction.addToBackStack(null);
        getSupportActionBar().setTitle("resultados");
        transaction.commit();
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }
}