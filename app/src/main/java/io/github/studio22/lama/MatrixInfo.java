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

        ImageView image = findViewById(R.id.scalar);
        if(state){
            image.setImageResource(R.drawable.scalar_m_dark);
        } else {
            image.setImageResource(R.drawable.scalar_m);
        }

        TextView functionName = findViewById(R.id.function_name);

        //передача с предыдущего экрана название функции
        if(getIntent().hasExtra("selected")){
            operation = getIntent().getParcelableExtra("selected");
            functionName.setText(operation.getName());
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

