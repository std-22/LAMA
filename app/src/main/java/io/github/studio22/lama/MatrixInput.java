package io.github.studio22.lama;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MatrixInput extends AppCompatActivity {
    private static int selectedRowSizeA = 1;
    private static int selectedColumnSizeA = 1;
    private static int selectedRowSizeMatrixB = 1;
    private static int selectedColumnSizeMatrixB = 1;
    private static final int[][] editTextId = {
            {R.id.editTextNumberA1, R.id.editTextNumberA2, R.id.editTextNumberA3, R.id.editTextNumberA4, R.id.editTextNumberA5, R.id.editTextNumberA6},
            {R.id.editTextNumberB1, R.id.editTextNumberB2, R.id.editTextNumberB3, R.id.editTextNumberB4, R.id.editTextNumberB5, R.id.editTextNumberB6},
            {R.id.editTextNumberC1, R.id.editTextNumberC2, R.id.editTextNumberC3, R.id.editTextNumberC4, R.id.editTextNumberC5, R.id.editTextNumberC6},
            {R.id.editTextNumberD1, R.id.editTextNumberD2, R.id.editTextNumberD3, R.id.editTextNumberD4, R.id.editTextNumberD5, R.id.editTextNumberD6},
            {R.id.editTextNumberE1, R.id.editTextNumberE2, R.id.editTextNumberE3, R.id.editTextNumberE4, R.id.editTextNumberE5, R.id.editTextNumberE6},
            {R.id.editTextNumberF1, R.id.editTextNumberF2, R.id.editTextNumberF3, R.id.editTextNumberF4, R.id.editTextNumberF5, R.id.editTextNumberF6}
    };

    SharedPreferences sharedPreferences;
    Boolean state;
    Operation operation;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        sharedPreferences = new SharedPreferences(this);
        state = sharedPreferences.loadNightModeState();

        if (state){
            setTheme(R.style.DarkAppTheme);
        } else {
            setTheme(R.style.AppTheme);
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.matrix_input);

        TextView functionName = findViewById(R.id.function_name);

        //передача названия функции с предыдущего экрана
        if(getIntent().hasExtra("selected")){
            operation = getIntent().getParcelableExtra("selected");
            functionName.setText(operation.getName());
        }

        //передача с последующего экрана название функции
        if (getIntent().hasExtra("selected_next")) {
            operation = getIntent().getParcelableExtra("selected_next");
            functionName.setText(operation.getName());
        }

        //считывание размеров матрицы A
        if (getIntent().hasExtra("selected_row_size")) {
            String temp = getIntent().getExtras().get("selected_row_size").toString();
            selectedRowSizeA = Integer.parseInt(temp);
        }

        if (getIntent().hasExtra("selected_column_size")) {
            String temp = getIntent().getExtras().get("selected_column_size").toString();
            selectedColumnSizeA = Integer.parseInt(temp);
        }

        for(int i = 0; i < selectedRowSizeA; i++){
            for(int j = 0; j < selectedColumnSizeA; j++){
                EditText editText = findViewById(editTextId[i][j]);
                editText.setVisibility(View.VISIBLE);
            }
        }

        //текст кнопки
        Button button = findViewById(R.id.to_input_matrix);
        if (getIntent().hasExtra("selected_row_size_matrix_B")) {
            button.setText(R.string.next);
        }
    }

    public void OnClickBackMatrix(View view) {
        Intent intent;
        if (getIntent().hasExtra("selected_row_size_matrix_B")) {
            intent = new Intent(MatrixInput.this, CategoryOperationMatrixB.class);
            intent.putExtra("selected_row_size", selectedRowSizeA);
            intent.putExtra("selected_column_size", selectedColumnSizeA);
        } else {
            intent = new Intent(MatrixInput.this, CategoryOperationMatrixA.class);
        }
        intent.putExtra("selected_next", operation);
        startActivity(intent);
    }

    public void onClickToResult(View view) {
        Intent intent;
        double[][] result;
        double[][] matrixA;
        //считывание размеров матрицы B
        if (getIntent().hasExtra("selected_row_size_matrix_B")) {
            //нет сохранения матрицы А
            String temp = getIntent().getExtras().get("selected_row_size_matrix_B").toString();
            selectedRowSizeMatrixB = Integer.parseInt(temp);

            if (getIntent().hasExtra("selected_column_size_matrix_B")) {
                temp = getIntent().getExtras().get("selected_column_size_matrix_B").toString();
                selectedColumnSizeMatrixB = Integer.parseInt(temp);
            }

            matrixA = new double[selectedRowSizeA][selectedColumnSizeA];
            for(int i = 0; i < selectedRowSizeA; i++){
                for(int j = 0; j < selectedColumnSizeA; j++){
                    EditText editText = findViewById(editTextId[i][j]);
                    matrixA[i][j] = Double.parseDouble(editText.getText().toString());
                }
            }

            intent = new Intent(MatrixInput.this, MatrixInputB.class);
            intent.putExtra("selected_row_size_matrix_B", selectedRowSizeMatrixB);
            intent.putExtra("selected_column_size_matrix_B", selectedColumnSizeMatrixB);
        } else {
            //в этот момент можно считать
            //нужно вызвать метод getResult() в блоке try
            matrixA = new double[selectedRowSizeA][selectedColumnSizeA];
            for(int i = 0; i < selectedRowSizeA; i++){
                for(int j = 0; j < selectedColumnSizeA; j++){
                    EditText editText = findViewById(editTextId[i][j]);
                    matrixA[i][j] = Double.parseDouble(editText.getText().toString());
                }
            }

            result = Result.getResult(operation.getName(), matrixA);
            intent = new Intent(MatrixInput.this, MatrixResult.class);
            intent.putExtra("matrix_a", matrixA);
        }
        intent.putExtra("matrix_a", matrixA);
        intent.putExtra("selected_row_size", selectedRowSizeA);
        intent.putExtra("selected_column_size", selectedColumnSizeA);
        intent.putExtra("selected", operation);

        startActivity(intent);
    }
}

