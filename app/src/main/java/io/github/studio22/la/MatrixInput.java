package io.github.studio22.la;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MatrixInput extends AppCompatActivity {
    private static int selectedStringSize = 1;
    private static int selectedColumnSize = 1;
    private static String nameOfFunction;
    private static int[][] editTextId = {
            {R.id.editTextNumberA1, R.id.editTextNumberA2, R.id.editTextNumberA3, R.id.editTextNumberA4, R.id.editTextNumberA5},
            {R.id.editTextNumberB1, R.id.editTextNumberB2, R.id.editTextNumberB3, R.id.editTextNumberB4, R.id.editTextNumberB5},
            {R.id.editTextNumberC1, R.id.editTextNumberC2, R.id.editTextNumberC3, R.id.editTextNumberC4, R.id.editTextNumberC5},
            {R.id.editTextNumberD1, R.id.editTextNumberD2, R.id.editTextNumberD3, R.id.editTextNumberD4, R.id.editTextNumberD5},
            {R.id.editTextNumberE1, R.id.editTextNumberE2, R.id.editTextNumberE3, R.id.editTextNumberE4, R.id.editTextNumberE5}
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.matrix_input);

        TextView functionName = findViewById(R.id.function_name);

        if (getIntent().hasExtra("name")) {
            nameOfFunction = getIntent().getExtras().get("name").toString();
            functionName.setText(nameOfFunction);
        }

        if (getIntent().hasExtra("selected_string_size")) {
            String temp = getIntent().getExtras().get("selected_string_size").toString();
            selectedStringSize = Integer.parseInt(temp);
        }

        if (getIntent().hasExtra("selected_column_size")) {
            String temp = getIntent().getExtras().get("selected_column_size").toString();
            selectedColumnSize = Integer.parseInt(temp);
        }

        for(int i = 0; i < selectedStringSize; i++){
            for(int j = 0; j < selectedColumnSize; j++){
                EditText editText = findViewById(editTextId[i][j]);
                editText.setVisibility(View.VISIBLE);
            }
        }
    }
/*
    public void onClickToInputMatrix(View view) {
        Intent intent = new Intent(CategoryOperation.this, MenuNavDarkActivity.class);
        intent.putExtra("selected_string_size", selectedStringSize);
        intent.putExtra("selected_column_size", selectedColumnSize);
        intent.putExtra("selected", NameOfFunction);
        startActivity(intent);
    }*/
    public void OnClickBackMatrix(View view) {
        Intent intent = new Intent(MatrixInput.this, CategoryOperation.class);
        startActivity(intent);
    }
}

