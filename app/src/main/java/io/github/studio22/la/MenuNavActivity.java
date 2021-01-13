package io.github.studio22.la;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.navigation.NavigationView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class MenuNavActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_nav);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);

        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.matrix_fragment, R.id.app_fragment, R.id.achievements_fragment)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    public void onClickMatrixMatrix(View view) {
        Intent intent = new Intent(MenuNavActivity.this, MatrixMatrix.class);
        startActivity(intent);
    }

    public void onClickMatrixLyambda(View view) {
        Intent intent = new Intent(MenuNavActivity.this, MatrixLyambda.class);
        startActivity(intent);
    }

    public void onClickMatrix(View view) {
        Intent intent = new Intent(MenuNavActivity.this, Matrix.class);
        startActivity(intent);
    }

    public void onClickToDark(View view) {
        Intent intent = new Intent(MenuNavActivity.this, MenuNavDarkActivity.class);
        startActivity(intent);
    }
}