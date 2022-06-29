package io.github.studio22.lama;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;

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

        if (state) {
            setTheme(R.style.DarkAppTheme);
        } else {
            setTheme(R.style.AppTheme);
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_nav);

        ImageView lama = findViewById(R.id.menu_lama_image);

        if (state) {
            lama.setImageResource(R.drawable.logo_new_dark);
        } else {
            lama.setImageResource(R.drawable.logo_new);
        }

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);

        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.matrix_fragment, R.id.app_fragment, R.id.function_research_fragment)
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
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    public void onClickMatrixLambda(View view) {
        Intent intent = new Intent(MenuNavActivity.this, MatrixLambda.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    public void onClickMatrix(View view) {
        Intent intent = new Intent(MenuNavActivity.this, Matrix.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    /* changing theme */

    public void onClickToDark(View view) {
        sharedPreferences.setNightModeState(!state);
        Intent intent = new Intent(getApplicationContext(), MenuNavActivity.class);
        startActivity(intent);
        finish();
    }

    /* about program button */

    public void onClickSendEmail(View view) {
        Intent emailIntent = new Intent(Intent.ACTION_VIEW,
                Uri.parse("mailto:" + "melikbekyan.ashot@yandex.ru"));
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Lama");
        EditText messageET = findViewById(R.id.for_message);
        emailIntent.putExtra(Intent.EXTRA_TEXT, messageET.getText().toString());
        try {
            startActivity(Intent.createChooser(emailIntent, "Отправить письмо через"));
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(getApplicationContext(),
                    "Приложение почты не обранужено",
                    Toast.LENGTH_SHORT).show();
        }
    }
}