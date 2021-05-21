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
    Operation operation;

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
            operation = getIntent().getParcelableExtra("selected");
            functionName.setText(operation.getName());
        }
    }

    public void OnClickBackMatrix(View view) {
        Intent intent;
        switch(operation.getNameOfClass()){
            case "Matrix":
                intent = new Intent(MatrixInfo.this, Matrix.class);
                startActivity(intent);
                break;
            case "MatrixMatrix":
                intent = new Intent(MatrixInfo.this, MatrixMatrix.class);
                startActivity(intent);
                break;
            case "MatrixLambda":
                intent = new Intent(MatrixInfo.this, MatrixLambda.class);
                startActivity(intent);
                break;
        }
    }
}

