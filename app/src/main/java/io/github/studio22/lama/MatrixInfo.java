package io.github.studio22.lama;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MatrixInfo extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    Boolean state;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        sharedPreferences = new SharedPreferences(this);
        state = sharedPreferences.loadNightModeState();

        if (state){
            setTheme(R.style.DarkAppTheme);
        } else {
            setTheme(R.style.AppTheme);
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.info);

        ImageView image = findViewById(R.id.scalar);
        if(state){
            image.setImageResource(R.drawable.scalar_m_dark);
        } else {
            image.setImageResource(R.drawable.scalar_m);
        }

        TextView functionName = findViewById(R.id.function_name);

        //передача с предыдущего экрана название функции
        if(getIntent().hasExtra("selected")){
            Operation operation = getIntent().getParcelableExtra("selected");
            functionName.setText(operation.getName());
        }
    }

    //возврат настроен только на matrix, а нужно на место вызова
    public void OnClickBackMatrix(View view) {
        Intent intent = new Intent(MatrixInfo.this, Matrix.class);
        startActivity(intent);
    }
}

