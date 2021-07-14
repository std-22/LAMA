package io.github.studio22.lama;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

/**
 * Ввод матрицы A
 */
public class MatrixInput extends AppCompatActivity {
    private int selectedRowSizeA = 1;
    private int selectedColumnSizeA = 1;
    private EditText editText_lambda = null;
    double[][] matrixA;
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

        //текст кнопки
        Button button = findViewById(R.id.to_input_matrix);
        if ("MatrixMatrix".equals(operation.getNameOfClass())) {
            button.setText(R.string.next);
        }

        //считывание матрицы A с последующего экрана
        if (getIntent().hasExtra("matrix_a") &&
                ("MatrixMatrix".equals(operation.getNameOfClass()) ||
                        "Matrix".equals(operation.getNameOfClass()))) {
            matrixA = (double[][]) getIntent().getExtras().get("matrix_a");
            for (int i = 0; i < matrixA.length; i++) {
                for (int j = 0; j < matrixA[0].length; j++) {
                    EditText editText = findViewById(editTextId[i][j]);
                    editText.setVisibility(View.VISIBLE);
                    int temp = (int) matrixA[i][j];
                    if ((double) temp == matrixA[i][j]) {
                        editText.setText(String.valueOf(temp));
                    } else {
                        editText.setText(String.valueOf(matrixA[i][j]));
                    }
                }
            }
        } else if (getIntent().hasExtra("matrix_a") &&
                "MatrixLambda".equals(operation.getNameOfClass())) {
            matrixA = (double[][]) getIntent().getExtras().get("matrix_a");
            for (int i = 0; i < matrixA.length; i++) {
                for (int j = 0; j < matrixA[0].length; j++) {
                    EditText editText = findViewById(editTextId[i][j]);
                    editText.setVisibility(View.VISIBLE);
                    int temp = (int) matrixA[i][j];
                    if ((double) temp == matrixA[i][j]) {
                        editText.setText(String.valueOf(temp));
                    } else {
                        editText.setText(String.valueOf(matrixA[i][j]));
                    }
                }
            }
            if (getIntent().hasExtra("lambda")) {
                double lambda = (double) getIntent().getExtras().get("lambda");
                findViewById(R.id.number_input).setVisibility(View.VISIBLE);
                editText_lambda = findViewById(R.id.editTextNumber);
                editText_lambda.setVisibility(View.VISIBLE);
                int temp = (int) lambda;
                if ((double) temp == lambda) {
                    Log.d("IF", "1");
                    editText_lambda.setText(String.valueOf(temp));
                } else {
                    Log.d("ELSE", "2");
                    editText_lambda.setText(String.valueOf(lambda));
                }
            } else {
                findViewById(R.id.number_input).setVisibility(View.VISIBLE);
                editText_lambda = findViewById(R.id.editTextNumber);
                editText_lambda.setVisibility(View.VISIBLE);
            }
        } else {
            EditText editText = null;
            for (int i = 0; i < selectedRowSizeA; i++) {
                for (int j = 0; j < selectedColumnSizeA; j++) {
                    editText = findViewById(editTextId[i][j]);
                    editText.setVisibility(View.VISIBLE);
                }
            }
            assert editText != null;
            editText.setImeOptions(EditorInfo.IME_NULL);

            //Отображение полей для ввода числа в классе матрица-число
            if ("MatrixLambda".equals(operation.getNameOfClass())) {
                findViewById(R.id.number_input).setVisibility(View.VISIBLE);
                editText_lambda = findViewById(R.id.editTextNumber);
                editText_lambda.setVisibility(View.VISIBLE);
                if (functionName.getText().equals("A\u207F")) {
                    editText_lambda.setInputType(InputType.TYPE_CLASS_NUMBER);
                }
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
                if (x1 < x2 && Math.toDegrees(Math.atan((x2 - x1) / Math.abs(y2 - y1))) > 30.0) {
                    onSwipeBack();
                }
                break;
        }
        return false;
    }

    public void onSwipeBack() {
        Intent intent = new Intent(MatrixInput.this, CategoryOperationMatrixA.class);
        intent.putExtra("selected_next", operation);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    public void onClickToResult(View view) {
        Intent intent;

        if (getIntent().hasExtra("matrix_a")) {
            selectedRowSizeA = matrixA.length;
            selectedColumnSizeA = matrixA[0].length;
        }

        if ("MatrixMatrix".equals(operation.getNameOfClass())) {
            matrixA = new double[selectedRowSizeA][selectedColumnSizeA];

            try {
                for (int i = 0; i < selectedRowSizeA; i++) {
                    for (int j = 0; j < selectedColumnSizeA; j++) {
                        EditText editText = findViewById(editTextId[i][j]);
                        matrixA[i][j] = Double.parseDouble(editText.getText().toString());
                    }
                }
                intent = new Intent(MatrixInput.this, CategoryOperationMatrixB.class);
                intent.putExtra("matrix_a", matrixA);
                intent.putExtra("selected", operation);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            } catch (Exception ignored) {
                Toast toast = Toast.makeText(getApplicationContext(),
                        "Пропущены значения",
                        Toast.LENGTH_LONG);
                toast.show();
            }
        } else {
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
                intent.putExtra("selected", operation);
                if (editText_lambda != null) {
                    intent.putExtra("lambda", Double.parseDouble(editText_lambda.getText().toString()));
                }
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            } catch (Exception ignored) {
                Toast toast = Toast.makeText(getApplicationContext(),
                        "Пропущены значения",
                        Toast.LENGTH_LONG);
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