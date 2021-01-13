package io.github.studio22.la;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Matrix extends AppCompatActivity {
    ArrayList<Operation> operations = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.matrix);

        setInitialData();
        RecyclerView recyclerView = findViewById(R.id.matrix_operations);

        OperationAdapter adapter = new OperationAdapter(this, operations, new OperationAdapter.ClickListener() {
            @Override
            public void onPositionClick(int position) {
                if (position == 0){
                    Intent intent = new Intent(Matrix.this, CategoryOperation.class);
                    //intent.putExtra(CategoryOperation.EXTRA_OPERATION, position);
                    intent.putExtra("selected", operations.get(position));
                    startActivity(intent);
                }
            }
        });
        recyclerView.setAdapter(adapter);
    }

    private void setInitialData(){
        operations.add(new Operation ("DET |A|"));
        operations.add(new Operation ("A ^ (-1)"));
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
