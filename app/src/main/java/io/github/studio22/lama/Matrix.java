package io.github.studio22.lama;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class Matrix extends AppCompatActivity {
    ArrayList<Operation> operations = new ArrayList<>();
    SharedPreferences sharedPreferences;
    Boolean state;
    String color;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        sharedPreferences = new SharedPreferences(this);
        state = sharedPreferences.loadNightModeState();

        if (state){
            setTheme(R.style.DarkAppTheme);
        } else {
            setTheme(R.style.AppTheme);
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.matrix);

        if (state){
            color = "#253040";
        } else {
            color = "#F9D19A";
        }

        setInitialData();
        final RecyclerView recyclerView = findViewById(R.id.matrix_operations);

        final OperationAdapter adapter = new OperationAdapter(this, operations, new OperationAdapter.ClickListener() {
            @Override
            public void onPositionClick(int position) {
                Intent intent = new Intent(Matrix.this, CategoryOperation.class);
                intent.putExtra("selected", operations.get(position));
                startActivity(intent);
            }
        });
        recyclerView.setAdapter(adapter);

        SwipeHelper swipeHelper = new SwipeHelper(this){

            @Override
            public void instantiateUnderlayButton(RecyclerView.ViewHolder viewHolder, List<UnderlayButton> underlayButtons) {
                underlayButtons.add(new SwipeHelper.UnderlayButton(
                        Matrix.this,
                        Color.parseColor(color),
                        new SwipeHelper.UnderlayButtonClickListener() {
                            @Override
                            public void onClick(final int pos) {
                                Intent intent = new Intent(Matrix.this, MatrixInfo.class);
                                intent.putExtra("selected", operations.get(pos));
                                startActivity(intent);
                            }
                        }
                ));
            }
        };
        swipeHelper.attachToRecyclerView(recyclerView);
    }

    private void setInitialData(){
        operations.add(new Operation ("DET |A|"));
        operations.add(new Operation ("A¹"));
        operations.add(new Operation ("Транспонирование"));
        operations.add(new Operation ("Ранг матрицы"));
        operations.add(new Operation ("Решение системы уравнений"));
        operations.add(new Operation ("Критерий Сильвестра"));
        operations.add(new Operation ("Поиск собственных значений"));
        operations.add(new Operation ("Поиск собственных векторов"));
        operations.add(new Operation ("Приведение к треугольному виду"));
        operations.add(new Operation ("Приведение к диагональному виду"));
    }

    public void OnClickBackMatrix(View view) {
        Intent intent = new Intent(Matrix.this, MenuNavActivity.class);
        startActivity(intent);
    }
}
