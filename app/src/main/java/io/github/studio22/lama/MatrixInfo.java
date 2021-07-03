package io.github.studio22.lama;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MatrixInfo extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    Boolean state;
    Operation operation;
    float x1, y1, x2, y2;

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
        setContentView(R.layout.info);

        TextView functionName = findViewById(R.id.function_name);

        //передача с предыдущего экрана название функции
        if(getIntent().hasExtra("selected")){
            operation = getIntent().getParcelableExtra("selected");
            functionName.setText(operation.getName());
        }

        ImageView infoImage;
        TextView infoText;

        switch (operation.getName()){
            case "DET |A|":
                infoImage = findViewById(R.id.info_image);
                if(state){
                    infoImage.setVisibility(View.VISIBLE);
                    infoImage.setImageResource(R.drawable.diagw);
                } else {
                    infoImage.setVisibility(View.VISIBLE);
                    infoImage.setImageResource(R.drawable.diag);
                }
                break;
            case "Ранг матрицы":
                infoText = findViewById(R.id.info_text);
                infoText.setVisibility(View.VISIBLE);
                infoText.setText(R.string.rang);
                break;
            case "Приведение к треугольному виду":
                infoText = findViewById(R.id.info_text);
                infoText.setVisibility(View.VISIBLE);
                infoText.setText(R.string.triangle);
                infoImage = findViewById(R.id.info_image);
                if(state){
                    infoImage.setVisibility(View.VISIBLE);
                    infoImage.setImageResource(R.drawable.trianglew);
                } else {
                    infoImage.setVisibility(View.VISIBLE);
                    infoImage.setImageResource(R.drawable.triangle);
                }
                break;
            case "Транспонирование":
                infoImage = findViewById(R.id.info_image);
                if(state){
                    infoImage.setVisibility(View.VISIBLE);
                    infoImage.setImageResource(R.drawable.transw);
                } else {
                    infoImage.setVisibility(View.VISIBLE);
                    infoImage.setImageResource(R.drawable.trans);
                }
                break;
            case "Приведение к диагональному виду":
                infoImage = findViewById(R.id.info_image);
                if(state){
                    infoImage.setVisibility(View.VISIBLE);
                    infoImage.setImageResource(R.drawable.diagw);
                } else {
                    infoImage.setVisibility(View.VISIBLE);
                    infoImage.setImageResource(R.drawable.diag);
                }
                break;
            case "Решение системы уравнений":
                infoText = findViewById(R.id.info_text);
                infoText.setVisibility(View.VISIBLE);
                infoText.setText(R.string.Gause);
                break;
            case "Критерий Сильвестра":
                infoText = findViewById(R.id.info_text);
                infoText.setVisibility(View.VISIBLE);
                infoText.setText(R.string.Silv);
                break;
            case "Поиск собственных значений":
                infoText = findViewById(R.id.info_text);
                infoText.setVisibility(View.VISIBLE);
                infoText.setText(R.string.sobzn);
                break;
            case "Поиск собственных векторов":
                infoText = findViewById(R.id.info_text);
                infoText.setVisibility(View.VISIBLE);
                infoText.setText(R.string.sobvec);
                break;
            case "A\u207F":
                infoText = findViewById(R.id.info_text);
                infoText.setVisibility(View.VISIBLE);
                infoText.setText("N раз");
                break;
            case "Поэлементное A + n":
                infoImage = findViewById(R.id.info_image);
                if(state){
                    infoImage.setVisibility(View.VISIBLE);
                    infoImage.setImageResource(R.drawable.anw);
                } else {
                    infoImage.setVisibility(View.VISIBLE);
                    infoImage.setImageResource(R.drawable.an);
                }
                break;
            case "Поэлементное A - n":
                infoImage = findViewById(R.id.info_image);
                if(state){
                    infoImage.setVisibility(View.VISIBLE);
                    infoImage.setImageResource(R.drawable.anrw);
                } else {
                    infoImage.setVisibility(View.VISIBLE);
                    infoImage.setImageResource(R.drawable.anr);
                }
                break;
            case "Поэлементное A / n":
                infoImage = findViewById(R.id.info_image);
                if(state){
                    infoImage.setVisibility(View.VISIBLE);
                    infoImage.setImageResource(R.drawable.andw);
                } else {
                    infoImage.setVisibility(View.VISIBLE);
                    infoImage.setImageResource(R.drawable.and);
                }
                break;
            case "Поэлементное A * n":
                infoImage = findViewById(R.id.info_image);
                if(state){
                    infoImage.setVisibility(View.VISIBLE);
                    infoImage.setImageResource(R.drawable.anmw);
                } else {
                    infoImage.setVisibility(View.VISIBLE);
                    infoImage.setImageResource(R.drawable.anm);
                }
                break;
            case "Поэлементное A ^ n":
                infoImage = findViewById(R.id.info_image);
                if(state){
                    infoImage.setVisibility(View.VISIBLE);
                    infoImage.setImageResource(R.drawable.anpw);
                } else {
                    infoImage.setVisibility(View.VISIBLE);
                    infoImage.setImageResource(R.drawable.anp);
                }
                break;
            default:
                break;
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
        switch(operation.getNameOfClass()){
            case "Matrix":
                intent = new Intent(MatrixInfo.this, Matrix.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                break;
            case "MatrixMatrix":
                intent = new Intent(MatrixInfo.this, MatrixMatrix.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                break;
            case "MatrixLambda":
                intent = new Intent(MatrixInfo.this, MatrixLambda.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                break;
        }
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}

