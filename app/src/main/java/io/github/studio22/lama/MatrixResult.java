package io.github.studio22.lama;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.DecimalFormat;

public class MatrixResult extends AppCompatActivity {
    private static String result;

    SharedPreferences sharedPreferences;
    Boolean state;
    Operation operation;

    private static final int[][] resultTextViewID = {
            {R.id.resultA1, R.id.resultA2, R.id.resultA3, R.id.resultA4, R.id.resultA5, R.id.resultA6},
            {R.id.resultB1, R.id.resultB2, R.id.resultB3, R.id.resultB4, R.id.resultB5, R.id.resultB6},
            {R.id.resultC1, R.id.resultC2, R.id.resultC3, R.id.resultC4, R.id.resultC5, R.id.resultC6},
            {R.id.resultD1, R.id.resultD2, R.id.resultD3, R.id.resultD4, R.id.resultD5, R.id.resultD6},
            {R.id.resultE1, R.id.resultE2, R.id.resultE3, R.id.resultE4, R.id.resultE5, R.id.resultE6},
            {R.id.resultF1, R.id.resultF2, R.id.resultF3, R.id.resultF4, R.id.resultF5, R.id.resultF6},
    };

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

        if (getIntent().hasExtra("result")) {
            double[][] result = (double[][]) getIntent().getExtras().get("result");
            for (int i = 0; i < result.length; i++) {
                for (int j = 0; j < result[0].length; j++) {
                    TextView textView = findViewById(resultTextViewID[i][j]);
                    textView.setVisibility(View.VISIBLE);
                    DecimalFormat df = new DecimalFormat("#.###");
                    textView.setText(df.format(result[i][j]));
                }
            }
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