package io.github.studio22.lama;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MatrixInputB extends AppCompatActivity {
    private int selectedRowSizeMatrixB = 1;
    private int selectedColumnSizeMatrixB = 1;
    double[][] matrixA;
    double[][] matrixB;
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
    float x1, y1, x2, y2;

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

        //извлечение матрицы А
        if (getIntent().hasExtra("matrix_a")) {
            matrixA = (double[][]) getIntent().getExtras().get("matrix_a");
        }

        //считывание размеров матрицы B
        if (getIntent().hasExtra("selected_row_size_matrix_B")) {
            String temp = getIntent().getExtras().get("selected_row_size_matrix_B").toString();
            selectedRowSizeMatrixB = Integer.parseInt(temp);
        }

        if (getIntent().hasExtra("selected_column_size_matrix_B")) {
            String temp = getIntent().getExtras().get("selected_column_size_matrix_B").toString();
            selectedColumnSizeMatrixB = Integer.parseInt(temp);
        }

        //считывание матрицы B с последующего экрана
        if (getIntent().hasExtra("matrix_b")) {
            matrixB = (double[][]) getIntent().getExtras().get("matrix_b");
            for (int i = 0; i < matrixB.length; i++) {
                for (int j = 0; j < matrixB[0].length; j++) {
                    EditText editText = findViewById(editTextId[i][j]);
                    editText.setVisibility(View.VISIBLE);
                    editText.setText(String.valueOf(matrixB[i][j]));
                }
            }
        } else {
            try {
                for (int i = 0; i < selectedRowSizeMatrixB; i++) {
                    for (int j = 0; j < selectedColumnSizeMatrixB; j++) {
                        EditText editText = findViewById(editTextId[i][j]);
                        editText.setVisibility(View.VISIBLE);
                    }
                }
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
        Intent intent = new Intent(MatrixInputB.this, CategoryOperationMatrixB.class);
        intent.putExtra("matrix_a", matrixA);
        intent.putExtra("selected_next", operation);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    public void onClickToResult(View view) {
        try {
            double[][] matrixB = new double[selectedRowSizeMatrixB][selectedColumnSizeMatrixB];
            for (int i = 0; i < selectedRowSizeMatrixB; i++) {
                for (int j = 0; j < selectedColumnSizeMatrixB; j++) {
                    EditText editText = findViewById(editTextId[i][j]);
                    matrixB[i][j] = Double.parseDouble(editText.getText().toString());
                }
            }
            Intent intent = new Intent(MatrixInputB.this, MatrixResult.class);
            intent.putExtra("selected", operation);
            intent.putExtra("matrix_a", matrixA);
            intent.putExtra("matrix_b", matrixB);
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

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}