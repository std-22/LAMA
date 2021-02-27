package io.github.studio22.lama;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MatrixInfo extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.info);

        TextView functionName = findViewById(R.id.function_name);

        //передача с предыдущего экрана название функции
        if(getIntent().hasExtra("selected")){
            Operation operation = getIntent().getParcelableExtra("selected");
            functionName.setText(operation.getName());
        }
    }

    public void OnClickBackMatrix(View view) {
        Intent intent = new Intent(MatrixInfo.this, Matrix.class);
        startActivity(intent);
    }
}

