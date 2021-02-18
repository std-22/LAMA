package io.github.studio22.lama;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MatrixResult extends AppCompatActivity {
    private static String nameOfFunction;
    private static String result;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.matrix_result);

        TextView functionName = findViewById(R.id.function_name);

        if (getIntent().hasExtra("selected")) {
            nameOfFunction = getIntent().getExtras().get("selected").toString();
            functionName.setText(nameOfFunction);
        }

        TextView resultText = findViewById(R.id.result);

        if (getIntent().hasExtra("result")) {
            result = getIntent().getExtras().get("result").toString();
            resultText.setText(result);
        }
    }

    public void OnClickBackMatrix(View view) {
        Intent intent = new Intent(MatrixResult.this, MatrixInput.class);
        intent.putExtra("selected_next", nameOfFunction);
        startActivity(intent);
    }

    public void onClickToMenu(View view) {
        Intent intent = new Intent(MatrixResult.this, MenuNavActivity.class);
        startActivity(intent);
    }
}