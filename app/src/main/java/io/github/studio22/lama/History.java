package io.github.studio22.lama;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class History extends AppCompatActivity {
    ArrayList<Matrices> matrices = new ArrayList<>();
    SharedPreferences sharedPreferences;
    Boolean state;
    Operation operation;
    double[][] matrixA;
    float x1, y1, x2, y2;

    public void onCreate(Bundle savedInstanceState) {
        sharedPreferences = new SharedPreferences(this);
        state = sharedPreferences.loadNightModeState();

        if (state) {
            setTheme(R.style.DarkAppTheme);
        } else {
            setTheme(R.style.AppTheme);
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.history);

        //передача с предыдущего экрана название функции
        if (getIntent().hasExtra("selected")) {
            operation = getIntent().getParcelableExtra("selected");
        }

        //передача с последующего экрана название функции
        if (getIntent().hasExtra("selected_next")) {
            operation = getIntent().getParcelableExtra("selected_next");
        }

        //извлечение матрицы А
        if (getIntent().hasExtra("matrix_a")) {
            matrixA = (double[][]) getIntent().getExtras().get("matrix_a");
        }

        setInitialData();
        final RecyclerView recyclerView = findViewById(R.id.matrices);
        final CardAdapter adapter;
        if (matrixA != null){
            adapter = new CardAdapter(this, matrices, operation, matrixA);
        } else {
            adapter = new CardAdapter(this, matrices, operation);
        }
        recyclerView.setAdapter(adapter);
        recyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {
                switch (e.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        x1 = e.getX();
                        y1 = e.getY();
                        break;
                    case MotionEvent.ACTION_UP:
                        x2 = e.getX();
                        y2 = e.getY();
                        if (x1<x2 && Math.toDegrees(Math.atan((x2-x1)/Math.abs(y2-y1))) > 30.0){
                            Intent intent;
                            if (matrixA == null){
                                intent = new Intent(History.this, CategoryOperationMatrixA.class);
                            } else {
                                intent = new Intent(History.this, CategoryOperationMatrixB.class);
                            }
                            intent.putExtra("selected_next", operation);
                            startActivity(intent);
                            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                        }
                        break;
                }
                return false;
            }

            @Override
            public void onTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {}

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {}
        });
    }

    private void setInitialData() {
        File history = new File(Matrices.internalStorageDir, "history.txt");

        try{
            Scanner scanner = new Scanner(history);
            while(scanner.hasNext()){
                String[] elements = scanner.nextLine().split(" ");
                int row = Integer.parseInt(elements[0]);
                int col = Integer.parseInt(elements[1]);
                int k = 2;
                double[][] resultMatrix = new double[row][col];
                for(int i = 0; i < row; i++){
                    for(int j = 0; j < col; j++){
                        elements[k] = elements[k].replace(",", ".");
                        resultMatrix[i][j] = Double.parseDouble(elements[k]);
                        k++;
                    }
                }
                matrices.add(new Matrices(resultMatrix));
            }
        }catch(FileNotFoundException e){
            System.out.println(e.getClass());
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
                    Intent intent;
                    if (matrixA == null) {
                        intent = new Intent(History.this, CategoryOperationMatrixA.class);
                    } else {
                        intent = new Intent(History.this, CategoryOperationMatrixB.class);
                    }
                    intent.putExtra("selected_next", operation);
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                }
                break;
        }
        return false;
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}
