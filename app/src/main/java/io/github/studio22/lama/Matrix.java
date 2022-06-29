package io.github.studio22.lama;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * Класс, отвечающий за работу раздела Матрица
 */
public class Matrix extends AppCompatActivity {
    ArrayList<Operation> operations = new ArrayList<>();
    SharedPreferences sharedPreferences;
    Boolean state;
    Boolean tip = false;
    String color;
    String nameOfClass = this.getClass().getSimpleName();
    Dialog dialog;
    float x1, y1, x2, y2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        android.content.SharedPreferences mSettings = getSharedPreferences("lama_settings", Context.MODE_PRIVATE);
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

        // Всплывающее окно с подсказкой
        tip = mSettings.getBoolean("tip", false);

        if (!tip) {
            dialog = new Dialog(this);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); //удаление заголовка по умолчанию
            dialog.setContentView(R.layout.dialog_window_of_choice);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT)); //установка фона
            dialog.setCancelable(false); //не даем закрыть окно кнопками навигации
            CheckBox tipCheckBox = dialog.findViewById(R.id.checkBox);
            tipCheckBox.setOnCheckedChangeListener((compoundButton, isChecked) -> {
                android.content.SharedPreferences.Editor editor = mSettings.edit();
                editor.putBoolean("tip", isChecked);
                editor.apply();
            });
            Button tipButton = dialog.findViewById(R.id.tip_button);
            tipButton.setOnClickListener(view -> dialog.dismiss());
            dialog.show();
        }

        setInitialData();

        // Навигация свайпом
        final RecyclerView recyclerView = findViewById(R.id.matrix_operations);
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
                            Intent intent = new Intent(Matrix.this, MenuNavActivity.class);
                            startActivity(intent);
                            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                        }
                        break;
                }
                return false;
            }

            @Override
            public void onTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {
            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }
        });

        // Переход на окно выбора размера матрицы
        final OperationAdapter adapter = new OperationAdapter(this, operations, position -> {
            if (operations.get(position).getName().equals("Приведение к диагональному виду") ||
                    operations.get(position).getName().equals("Поиск собственных значений") ||
                    operations.get(position).getName().equals("Поиск собственных векторов")) {
                @SuppressLint("ShowToast") Toast toast = Toast.makeText(this, "В разработке", Toast.LENGTH_SHORT);
                toast.show();
            } else {
                Intent intent = new Intent(Matrix.this, CategoryOperationMatrixA.class);
                intent.putExtra("selected", operations.get(position));
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });
        recyclerView.setAdapter(adapter);

        // Реализация свайпа объектов списка
        SwipeHelper swipeHelper = new SwipeHelper(this){
            @Override
            public void instantiateUnderlayButton(RecyclerView.ViewHolder viewHolder, List<UnderlayButton> underlayButtons) {
                underlayButtons.add(new SwipeHelper.UnderlayButton(
                        Matrix.this,
                        Color.parseColor(color),
                        pos -> {
                            Intent intent = new Intent(Matrix.this, MatrixInfo.class);
                            intent.putExtra("selected", operations.get(pos));
                            startActivity(intent);
                            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                        }
                ));
            }
        };
        swipeHelper.attachToRecyclerView(recyclerView);
    }

    /**
     * Добавление операция над матрицей
     */
    private void setInitialData(){
        operations.add(new Operation("DET |A|", nameOfClass));
        operations.add(new Operation("A\u1428\u00B9", nameOfClass));
        operations.add(new Operation("Транспонирование", nameOfClass));
        operations.add(new Operation ("Ранг матрицы", nameOfClass));
        operations.add(new Operation ("Решение системы уравнений", nameOfClass));
        operations.add(new Operation ("Критерий Сильвестра", nameOfClass));
        operations.add(new Operation ("Поиск собственных значений", nameOfClass));
        operations.add(new Operation ("Поиск собственных векторов", nameOfClass));
        operations.add(new Operation ("Приведение к треугольному виду", nameOfClass));
        operations.add(new Operation ("Приведение к диагональному виду", nameOfClass));
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}
