package io.github.studio22.lama;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MatrixMatrix extends AppCompatActivity {
    ArrayList<Operation> operations = new ArrayList<>();
    SharedPreferences sharedPreferences;
    Boolean state;
    String color;
    String nameOfClass = this.getClass().getSimpleName();

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
        setContentView(R.layout.matrix_matrix);

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
                    Intent intent = new Intent(MatrixMatrix.this, CategoryOperationMatrixA.class);
                    intent.putExtra("selected", operations.get(position));
                    startActivity(intent);
                }
            });
        recyclerView.setAdapter(adapter);

        SwipeHelper swipeHelper = new SwipeHelper(this){

            @Override
            public void instantiateUnderlayButton(RecyclerView.ViewHolder viewHolder, List<UnderlayButton> underlayButtons) {
                underlayButtons.add(new SwipeHelper.UnderlayButton(
                        MatrixMatrix.this,
                            Color.parseColor(color),
                            new SwipeHelper.UnderlayButtonClickListener() {
                                @Override
                                public void onClick(final int pos) {
                                    Intent intent = new Intent(MatrixMatrix.this, MatrixInfo.class);
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
            operations.add(new Operation ("A \u00D7 B", nameOfClass));
            operations.add(new Operation ("A \u00D7 B\u207B\u00B9", nameOfClass));
            operations.add(new Operation ("Поэлементное A + B", nameOfClass));
            operations.add(new Operation ("Поэлементное A - B", nameOfClass));
            operations.add(new Operation ("Поэлементное A \u00D7 B", nameOfClass));
            operations.add(new Operation ("Поэлементное A / B", nameOfClass));
    }

    public void OnClickBackMatrixMatrix(View view) {
        Intent intent = new Intent(MatrixMatrix.this, MenuNavActivity.class);
        startActivity(intent);
    }
}


