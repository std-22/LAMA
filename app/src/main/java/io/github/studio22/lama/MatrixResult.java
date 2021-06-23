package io.github.studio22.lama;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;

public class MatrixResult extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    Boolean state;
    Operation operation;
    float x1, y1, x2, y2;
    double[][] resultMatrix;
    FileOutputStream fos;
    enum Output {E, P5, P1}
    Output output = Output.E;

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
                    resultMatrix = new double[1][1];
                    resultMatrix[0][0] = Result.getResult(operation.getName(), matrixA)[0][0];
                    textView = findViewById(resultTextViewID[0][0]);
                    textView.setVisibility(View.VISIBLE);
                    textView.setText(String.valueOf(resultMatrix[0][0]));
                    break;
                case "Транспонирование":
                    resultMatrix = Result.getResult(operation.getName(), matrixA);
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
                    resultMatrix = Result.getResult(operation.getName(), matrixA);
                    if (MatrixCalculation.determinantCalc(matrixA) != 0) {
                        for (int i = 0; i < resultMatrix.length; i++) {
                            for (int j = 0; j < resultMatrix[0].length; j++) {
                                textView = findViewById(resultTextViewID[i][j]);
                                textView.setVisibility(View.VISIBLE);
                                DecimalFormat df = new DecimalFormat("#.###");
                                textView.setText(df.format(resultMatrix[i][j]));
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
            resultMatrix = Result.getResult(operation.getName(), matrixA, matrixB);
            for (int i = 0; i < resultMatrix.length; i++) {
                for (int j = 0; j < resultMatrix[0].length; j++) {
                    TextView textView = findViewById(resultTextViewID[i][j]);
                    textView.setVisibility(View.VISIBLE);
                    DecimalFormat df = new DecimalFormat("#.###");
                    textView.setText(df.format(resultMatrix[i][j]));
                }
            }
        }
        // Матрица-Число
        else {
            double[][] matrixA = (double[][]) getIntent().getExtras().get("matrix_a");
            double lambda = (double) getIntent().getExtras().get("lambda");
            resultMatrix = Result.getResult(operation.getName(), matrixA, lambda);
            for (int i = 0; i < resultMatrix.length; i++) {
                for (int j = 0; j < resultMatrix[0].length; j++) {
                    TextView textView = findViewById(resultTextViewID[i][j]);
                    textView.setVisibility(View.VISIBLE);
                    DecimalFormat df = new DecimalFormat("#.###");
                    textView.setText(df.format(resultMatrix[i][j]));
                }
            }
        }

        if (resultMatrix != null && (resultMatrix.length != 1 || resultMatrix[0].length!=1)){
            TextView rem_matrix = findViewById(R.id.rem_matrix);
            rem_matrix.setVisibility(View.VISIBLE);
            CheckBox checkBox = findViewById(R.id.checkBox);
            checkBox.setVisibility(View.VISIBLE);
            checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
                if(isChecked) {
                    checkBox.setVisibility(View.GONE);
                    Animation anim = AnimationUtils.loadAnimation(this, R.anim.disappearance);
                    rem_matrix.startAnimation(anim);
                    rem_matrix.setText("Скопировано");
                    anim = AnimationUtils.loadAnimation(this, R.anim.appearance);
                    rem_matrix.startAnimation(anim);
                    File history = new File(Matrices.internalStorageDir, "history.txt");

                    try {
                        fos = new FileOutputStream(history, true);
                        String matrix = resultMatrix.length + " " + resultMatrix[0].length + " ";
                        for (double[] doubles : resultMatrix) {
                            for (int j = 0; j < resultMatrix[0].length; j++) {
                                DecimalFormat df = new DecimalFormat("#.###");
                                matrix += df.format(doubles[j]);
                                matrix += " ";
                            }
                        }
                        matrix += "\n";
                        fos.write(matrix.getBytes());
                        fos.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
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
            intent = new Intent(MatrixResult.this, MatrixInputB.class);
            String temp = getIntent().getExtras().get("selected_row_size_matrix_B").toString();
            int selectedRowSizeMatrixB = Integer.parseInt(temp);
            intent.putExtra("selected_row_size", selectedRowSizeMatrixB);

            if (getIntent().hasExtra("selected_column_size_matrix_B")) {
                temp = getIntent().getExtras().get("selected_column_size_matrix_B").toString();
                int selectedColumnSizeMatrixB = Integer.parseInt(temp);
                intent.putExtra("selected_column_size", selectedColumnSizeMatrixB);
            }

            if (getIntent().hasExtra("selected_row_size")) {
                temp = getIntent().getExtras().get("selected_row_size").toString();
                int selectedRowSize = Integer.parseInt(temp);
                intent.putExtra("selected_row_size", selectedRowSize);
            }

            if (getIntent().hasExtra("selected_column_size")) {
                temp = getIntent().getExtras().get("selected_column_size").toString();
                int selectedColumnSize = Integer.parseInt(temp);
                intent.putExtra("selected_column_size", selectedColumnSize);
            }
            double[][] matrixA = (double[][]) getIntent().getExtras().get("matrix_a");
            intent.putExtra("matrix_a", matrixA);
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
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    public void onClickToMenu(View view) {
        Intent intent = new Intent(MatrixResult.this, MenuNavActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    public void onClickIB(View view) {
        ImageButton ib = findViewById(R.id.ib);
        switch (output){
            case E:
                output = Output.P5;
                ib.setImageResource(R.drawable.ic_p5);
                break;
            case P5:
                output = Output.P1;
                ib.setImageResource(R.drawable.ic_p1);
                break;
            case P1:
                output = Output.E;
                ib.setImageResource(R.drawable.ic_e);
                break;
        }
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}