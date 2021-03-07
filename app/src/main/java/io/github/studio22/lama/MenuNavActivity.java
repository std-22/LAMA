package io.github.studio22.lama;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.List;

/**
 * Класс реализовывает навигацию в приложении
 */

public class MenuNavActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    public static Boolean theme;
    SharedPreferences sharedPreferences;
    Boolean state;

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        sharedPreferences = new SharedPreferences(this);
        state = sharedPreferences.loadNightModeState();
        theme = state;

        if (state){
            setTheme(R.style.DarkAppTheme);
        } else {
            setTheme(R.style.AppTheme);
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_nav);

        ImageView lama = findViewById(R.id.menu_lama_image);
        //ImageButton night = findViewById(R.id.night);

        if(state){
            lama.setImageResource(R.drawable.logo_new_dark);
            //night.setImageResource(R.drawable.ic_sun);
        } else {
            lama.setImageResource(R.drawable.logo_new);
            //night.setImageResource(R.drawable.ic_moon);
        }

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);

        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.matrix_fragment, R.id.app_fragment, R.id.achievements_fragment)
                .setOpenableLayout(drawer)   //setDrawerLayout(drawer) deprecated
                .build();

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    /* navigation */

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    /* choose of category */

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

    /* changing theme */

    public void onClickToDark(View view) {
        sharedPreferences.setNightModeState(!state);

        Intent intent = new Intent(getApplicationContext(), MenuNavActivity.class);
        startActivity(intent);
        finish();
    }

    /*public void onClickToLight(View view) {
        sharedPreferences.setNightModeState(!state);

        Intent intent = new Intent(getApplicationContext(), MenuNavActivity.class);
        startActivity(intent);
        finish();
    }*/

    /* about program button */

    public void onClickSendEmail(View view) {
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mail to", "studio-22-5600@pages.plusgoogle.com", null));
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Разработчикам");
                emailIntent.putExtra(Intent.EXTRA_TEXT, ((EditText) findViewById(R.id.for_message)).getText().toString());

                if (isIntentSafe(emailIntent)){
                    startActivity(emailIntent);
                } else {
                    Toast.makeText(getApplicationContext(), "no app for dial on your device", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    /* check safety of opening app */

    private Boolean isIntentSafe(Intent intent){
        PackageManager packageManager = getPackageManager();
        @SuppressLint("QueryPermissionsNeeded") List<ResolveInfo> activities = packageManager.queryIntentActivities(intent, 0);
        return activities.size() > 0;
    }
}