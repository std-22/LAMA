package io.github.studio22.lama;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MatrixResult extends AppCompatActivity {
    private static String result;

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
        setContentView(R.layout.matrix_result);

        TextView functionName = findViewById(R.id.function_name);

        //передача названия функции с предыдущего экрана
        if(getIntent().hasExtra("selected")){
            operation = getIntent().getParcelableExtra("selected");
            functionName.setText(operation.getName());
        }

        TextView resultText = findViewById(R.id.result);

        if (getIntent().hasExtra("result")) {
            result = getIntent().getExtras().get("result").toString();
            resultText.setText(result);
        }
    }

    public void OnClickBackMatrix(View view) {
        Intent intent;
        if (getIntent().hasExtra("selected_row_size_matrix_B")) {
            intent = new Intent(MatrixResult.this, MatrixInputB.class);
            String temp = getIntent().getExtras().get("selected_row_size_matrix_B").toString();
            int selectedRowSizeMatrixB = Integer.parseInt(temp);
            intent.putExtra("selected_row_size", selectedRowSizeMatrixB);

            if (getIntent().hasExtra("selected_column_size_matrix_B")) {
                temp = getIntent().getExtras().get("selected_column_size_matrix_B").toString();
                int selectedColumnSizeMatrixB = Integer.parseInt(temp);
                intent.putExtra("selected_column_size", selectedColumnSizeMatrixB);
            }
        } else {
            intent = new Intent(MatrixResult.this, MatrixInput.class);
            //считывание размеров матрицы A
            if (getIntent().hasExtra("selected_row_size")) {
                String temp = getIntent().getExtras().get("selected_row_size").toString();
                int selectedRowSize = Integer.parseInt(temp);
                intent.putExtra("selected_row_size", selectedRowSize);
            }

            if (getIntent().hasExtra("selected_column_size")) {
                String temp = getIntent().getExtras().get("selected_column_size").toString();
                int selectedColumnSize = Integer.parseInt(temp);
                intent.putExtra("selected_column_size", selectedColumnSize);
            }
        }
        intent.putExtra("selected_next", operation);
        startActivity(intent);
    }

    public void onClickToMenu(View view) {
        Intent intent = new Intent(MatrixResult.this, MenuNavActivity.class);
        startActivity(intent);
    }
}