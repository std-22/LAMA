package io.github.studio22.lama;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.DecimalFormat;

public class MatrixResult extends AppCompatActivity {
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

        if (state) {
            setTheme(R.style.DarkAppTheme);
        } else {
            setTheme(R.style.AppTheme);
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.matrix_result);

        TextView functionName = findViewById(R.id.function_name);

        //передача названия функции с предыдущего экрана
        if (getIntent().hasExtra("selected")) {
            operation = getIntent().getParcelableExtra("selected");
            functionName.setText(operation.getName());
        }

        // Матрица
        if (getIntent().hasExtra("matrix_a") &
                !getIntent().hasExtra("matrix_b") &
                !getIntent().hasExtra("lambda")) {
            double[][] matrixA = (double[][]) getIntent().getExtras().get("matrix_a");
            TextView textView;
            switch (operation.getName()) {
                case "DET |A|":
                    double result = Result.getResult(operation.getName(), matrixA)[0][0];
                    textView = findViewById(resultTextViewID[0][0]);
                    textView.setVisibility(View.VISIBLE);
                    textView.setText(String.valueOf(result));
                    break;
                case "Транспонирование":
                    double[][] resultMatrix = Result.getResult(operation.getName(), matrixA);
                    for (int i = 0; i < resultMatrix.length; i++) {
                        for (int j = 0; j < resultMatrix[0].length; j++) {
                            textView = findViewById(resultTextViewID[i][j]);
                            textView.setVisibility(View.VISIBLE);
                            DecimalFormat df = new DecimalFormat("#.###");
                            textView.setText(df.format(resultMatrix[i][j]));
                        }
                    }
                    break;
                case "A\u1428\u00B9":
                    double[][] resultMatrixInv = Result.getResult(operation.getName(), matrixA);
                    if (MatrixCalculation.determinantCalc(resultMatrixInv) != 0
                            & !Double.isNaN(MatrixCalculation.determinantCalc(resultMatrixInv))) {
                        for (int i = 0; i < resultMatrixInv.length; i++) {
                            for (int j = 0; j < resultMatrixInv[0].length; j++) {
                                textView = findViewById(resultTextViewID[i][j]);
                                textView.setVisibility(View.VISIBLE);
                                DecimalFormat df = new DecimalFormat("#.###");
                                textView.setText(df.format(resultMatrixInv[i][j]));
                            }
                        }
                    } else {
                        textView = findViewById(resultTextViewID[0][0]);
                        textView.setVisibility(View.VISIBLE);
                        textView.setText("Матрица вырожденная");
                    }
                    break;
                case "Критерий Сильвестра":
                    int resultSilvester = (int) Result.getResult(operation.getName(), matrixA)[0][0];
                    textView = findViewById(resultTextViewID[0][0]);
                    textView.setVisibility(View.VISIBLE);
                    if (resultSilvester == 1) {
                        textView.setText("Положительная определенность");
                    } else if (resultSilvester == 0) {
                        textView.setText("Знакопеременная определенность");
                    } else {
                        textView.setText("Отрицательная определенность");
                    }
                    break;
                case "Поиск собственных значений":
                    double[][] resultEigen = Result.getResult(operation.getName(), matrixA);
                    if (matrixA.length == 2 & matrixA[0].length == 2) {
                        TextView lambda1TextView = findViewById(resultTextViewID[0][0]);
                        TextView lambda2TextView = findViewById(resultTextViewID[0][1]);
                        lambda1TextView.setVisibility(View.VISIBLE);
                        lambda2TextView.setVisibility(View.VISIBLE);
                        DecimalFormat df = new DecimalFormat("#.###");
                        lambda1TextView.setText(String.format("λ₁ = %s", df.format(resultEigen[0][0])));
                        lambda2TextView.setText(String.format("λ₂ = %s", df.format(resultEigen[1][0])));
                    } else {
                        textView = findViewById(resultTextViewID[0][0]);
                        textView.setText("В процессе разработки");
                    }
                    break;
                case "Поиск собственных векторов":
                    double[][] resultEigenVector = Result.getResult(operation.getName(), matrixA);
                    if (matrixA.length == 2 & matrixA[0].length == 2) {
                        TextView vector1TextView = findViewById(resultTextViewID[0][0]);
                        TextView vector2TextView = findViewById(resultTextViewID[0][1]);
                        vector1TextView.setVisibility(View.VISIBLE);
                        vector2TextView.setVisibility(View.VISIBLE);
                        DecimalFormat df = new DecimalFormat("#.###");
                        vector1TextView.setText(String.format("x₁ = %sy",
                                df.format(resultEigenVector[0][0])));
                        vector2TextView.setText(String.format("x₂ = %sy",
                                df.format(resultEigenVector[1][0])));
                    } else {
                        textView = findViewById(resultTextViewID[0][0]);
                        textView.setVisibility(View.VISIBLE);
                        textView.setText("В процессе разработки");
                    }
                    break;
                default:
                    TextView defaultTextView = findViewById(resultTextViewID[0][0]);
                    defaultTextView.setVisibility(View.VISIBLE);
                    defaultTextView.setText("В процессе разработки");
            }
        }
        // Матрица-Матрица
        else if (getIntent().hasExtra("matrix_a") & getIntent().hasExtra("matrix_b")) {
            double[][] matrixA = (double[][]) getIntent().getExtras().get("matrix_a");
            double[][] matrixB = (double[][]) getIntent().getExtras().get("matrix_b");
            double[][] result = Result.getResult(operation.getName(), matrixA, matrixB);
            for (int i = 0; i < result.length; i++) {
                for (int j = 0; j < result[0].length; j++) {
                    TextView textView = findViewById(resultTextViewID[i][j]);
                    textView.setVisibility(View.VISIBLE);
                    DecimalFormat df = new DecimalFormat("#.###");
                    textView.setText(df.format(result[i][j]));
                }
            }
        }
        // Матрица-Число
        else {
            double[][] matrixA = (double[][]) getIntent().getExtras().get("matrix_a");
            double lambda = (double) getIntent().getExtras().get("lambda");
            double[][] result = Result.getResult(operation.getName(), matrixA, lambda);
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