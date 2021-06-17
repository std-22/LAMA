package io.github.studio22.lama;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MatrixInput extends AppCompatActivity {
    private static int selectedRowSizeA = 1;
    private static int selectedColumnSizeA = 1;
    private static int selectedRowSizeMatrixB = 1;
    private static int selectedColumnSizeMatrixB = 1;
    private EditText editText_lambda = null;
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
    int temp_i;
    float x1, y1, x2, y2;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        sharedPreferences = new SharedPreferences(this);
        state = sharedPreferences.loadNightModeState();

        if (state) {
            setTheme(R.style.DarkAppTheme);
        } else {
            setTheme(R.style.AppTheme);
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.matrix_input);

        TextView functionName = findViewById(R.id.function_name);

        //передача названия функции с предыдущего экрана
        if (getIntent().hasExtra("selected")) {
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

        for (int i = 0; i < selectedRowSizeA; i++) {
            temp_i = i;
            for (int j = 0; j < selectedColumnSizeA; j++) {
                EditText editText = findViewById(editTextId[i][j]);
                editText.setVisibility(View.VISIBLE);
            }
        }

        //текст кнопки
        Button button = findViewById(R.id.to_input_matrix);
        if (getIntent().hasExtra("selected_row_size_matrix_B")) {
            button.setText(R.string.next);
        }

        //Отображение полей для ввода числа в классе матрица-число
        if (   operation.getName().equals("Поэлементное A + n")  || operation.getName().equals("Поэлементное A - n")
            || operation.getName().equals("Поэлементное A \u00D7 n")  || operation.getName().equals("Поэлементное A / n")
            || operation.getName().equals("Поэлементное A\u207F")  || operation.getName().equals("A\u207F")){
            findViewById(R.id.number_input).setVisibility(View.VISIBLE);
            editText_lambda = findViewById(R.id.editTextNumber);
            editText_lambda.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                x1 = event.getX();
                y1 = event.getY();
                break;
            case MotionEvent.ACTION_UP:
                x2 = event.getX();
                y2 = event.getY();
                if (x1<x2 && Math.toDegrees(Math.atan((x2-x1)/Math.abs(y2-y1))) > 30.0){
                    onSwipeBack();
                }
                break;
        }
        return false;
    }

    public void onSwipeBack() {
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
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    public void onClickToResult(View view) {
        Intent intent;
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

            try {
                for (int i = 0; i < selectedRowSizeA; i++) {
                    for (int j = 0; j < selectedColumnSizeA; j++) {
                        EditText editText = findViewById(editTextId[i][j]);
                        matrixA[i][j] = Double.parseDouble(editText.getText().toString());
                    }
                }
                intent = new Intent(MatrixInput.this, MatrixInputB.class);
                intent.putExtra("selected_row_size_matrix_B", selectedRowSizeMatrixB);
                intent.putExtra("selected_column_size_matrix_B", selectedColumnSizeMatrixB);
                intent.putExtra("matrix_a", matrixA);
                intent.putExtra("selected_row_size", selectedRowSizeA);
                intent.putExtra("selected_column_size", selectedColumnSizeA);
                intent.putExtra("selected", operation);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            } catch (Exception ignored) {
                Toast toast = Toast.makeText(getApplicationContext(),
                        "Пропущены значения",
                        Toast.LENGTH_LONG);
                toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL,
                        0, 0);
                toast.show();
            }
        } else {
            //в этот момент можно считать
            matrixA = new double[selectedRowSizeA][selectedColumnSizeA];
            try {
                for (int i = 0; i < selectedRowSizeA; i++) {
                    for (int j = 0; j < selectedColumnSizeA; j++) {
                        EditText editText = findViewById(editTextId[i][j]);
                        matrixA[i][j] = Double.parseDouble(editText.getText().toString());
                    }
                }
                intent = new Intent(MatrixInput.this, MatrixResult.class);
                intent.putExtra("matrix_a", matrixA);
                intent.putExtra("selected_row_size", selectedRowSizeA);
                intent.putExtra("selected_column_size", selectedColumnSizeA);
                intent.putExtra("selected", operation);
                if (editText_lambda != null){
                    intent.putExtra("lambda", Double.parseDouble(editText_lambda.getText().toString()));
                }
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            } catch (Exception ignored) {
                Toast toast = Toast.makeText(getApplicationContext(),
                        "Пропущены значения",
                        Toast.LENGTH_LONG);
                toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL,
                        0, 0);
                toast.show();
            }
        }
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}