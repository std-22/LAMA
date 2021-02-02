package io.github.studio22.la;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;

import java.util.List;

public class MenuNavDarkActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_nav_dark);
        Toolbar toolbar = findViewById(R.id.toolbar_dark);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout_dark);
        NavigationView navigationView = findViewById(R.id.nav_view_dark);

        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.matrix_fragment_dark, R.id.app_fragment_dark, R.id.achievements_fragment_dark)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_dark);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_dark);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    public void onClickMatrixMatrix(View view) {
        Intent intent = new Intent(MenuNavDarkActivity.this, MatrixMatrix.class);
        startActivity(intent);
    }

    public void onClickMatrixLyambda(View view) {
        Intent intent = new Intent(MenuNavDarkActivity.this, MatrixLyambda.class);
        startActivity(intent);
    }

    public void onClickMatrix(View view) {
        Intent intent = new Intent(MenuNavDarkActivity.this, Matrix.class);
        startActivity(intent);
    }

    public void onClickSendEmailDark(View view) {
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mail to", "studio-22-5600@pages.plusgoogle.com", null));
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Разработчикам");
                emailIntent.putExtra(Intent.EXTRA_TEXT, ((EditText) findViewById(R.id.for_message_dark)).getText().toString());

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
        List<ResolveInfo> activities = packageManager.queryIntentActivities(intent, 0);
        return activities.size() > 0;
    }
}
