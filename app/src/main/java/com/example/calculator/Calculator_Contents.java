package com.example.calculator;

import android.os.Bundle;
import android.view.MenuItem;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import com.google.android.material.navigation.NavigationView;

public class Calculator_Contents extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout layout;
    NavigationView navigationView;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator_contents);

        layout = findViewById(R.id.drawerlyaut);
        navigationView = findViewById(R.id.navigation_view);
        toolbar = findViewById(R.id.ToolbarMenu);

        // Définir la Toolbar comme barre d'action
        setSupportActionBar(toolbar);

        // Configurer le ActionBarDrawerToggle
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this,
                layout,
                toolbar,
                R.string.OpenNav,
                R.string.CloseNav
        );

        layout.addDrawerListener(toggle);
        toggle.syncState();

        // Lier l'écouteur de sélection d'élément de navigation à l'activité
        navigationView.setNavigationItemSelectedListener(this);
    }



    // Implémentation de la méthode onNavigationItemSelected
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {


        }
        layout.closeDrawer(GravityCompat.START);
        return true;
    }
}
