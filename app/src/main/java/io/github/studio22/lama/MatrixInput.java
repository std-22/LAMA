package io.github.studio22.lama;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MatrixInput extends AppCompatActivity {
    private static int selectedRowSize = 1;
    private static int selectedColumnSize = 1;
    private static String nameOfFunction;
    private static final int[][] editTextId = {
            {R.id.editTextNumberA1, R.id.editTextNumberA2, R.id.editTextNumberA3, R.id.editTextNumberA4, R.id.editTextNumberA5, R.id.editTextNumberA6},
            {R.id.editTextNumberB1, R.id.editTextNumberB2, R.id.editTextNumberB3, R.id.editTextNumberB4, R.id.editTextNumberB5, R.id.editTextNumberB6},
            {R.id.editTextNumberC1, R.id.editTextNumberC2, R.id.editTextNumberC3, R.id.editTextNumberC4, R.id.editTextNumberC5, R.id.editTextNumberC6},
            {R.id.editTextNumberD1, R.id.editTextNumberD2, R.id.editTextNumberD3, R.id.editTextNumberD4, R.id.editTextNumberD5, R.id.editTextNumberD6},
            {R.id.editTextNumberE1, R.id.editTextNumberE2, R.id.editTextNumberE3, R.id.editTextNumberE4, R.id.editTextNumberE5, R.id.editTextNumberE6},
            {R.id.editTextNumberF1, R.id.editTextNumberF2, R.id.editTextNumberF3, R.id.editTextNumberF4, R.id.editTextNumberF5, R.id.editTextNumberF6}
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

        //передача с последующего экрана название функции
        if (getIntent().hasExtra("selected_next")) {
            nameOfFunction = getIntent().getExtras().get("selected_next").toString();
            functionName.setText(nameOfFunction);
        }

        if (getIntent().hasExtra("selected_row_size")) {
            String temp = getIntent().getExtras().get("selected_row_size").toString();
            selectedRowSize = Integer.parseInt(temp);
        }

        if (getIntent().hasExtra("selected_column_size")) {
            String temp = getIntent().getExtras().get("selected_column_size").toString();
            selectedColumnSize = Integer.parseInt(temp);
        }

        for(int i = 0; i < selectedRowSize; i++){
            for(int j = 0; j < selectedColumnSize; j++){
                EditText editText = findViewById(editTextId[i][j]);
                editText.setVisibility(View.VISIBLE);
            }
        }


    }

    public void OnClickBackMatrix(View view) {
        Intent intent = new Intent(MatrixInput.this, CategoryOperation.class);
        //intent.putExtra("selected_string_size", selectedRowSize);
        //intent.putExtra("selected_column_size", selectedColumnSize);
        intent.putExtra("selected_next", nameOfFunction);
        startActivity(intent);
    }

    public void onClickToResult(View view) {
        //в этот момент можно считать
        //нужно вызвать метод getResult() в блоке try
        int[][] field = new int[selectedRowSize][selectedColumnSize];
        for(int i = 0; i < selectedRowSize; i++){
            for(int j = 0; j < selectedColumnSize; j++){
                EditText editText = findViewById(editTextId[i][j]);
                field[i][j] = Integer.parseInt(editText.getText().toString());
            }
        }

        String result = Result.getResult(nameOfFunction, field);

        Intent intent = new Intent(MatrixInput.this, MatrixResult.class);
        intent.putExtra("selected_row_size", selectedRowSize);
        intent.putExtra("selected_column_size", selectedColumnSize);
        intent.putExtra("selected", nameOfFunction);
        intent.putExtra("result", result);

        startActivity(intent);
    }


}

