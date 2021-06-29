package io.github.studio22.lama;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import github.hellocsl.cursorwheel.CursorWheelLayout;

public class CategoryOperationMatrixA extends AppCompatActivity implements CursorWheelLayout.OnMenuSelectedListener {
    private String selectedRowSize;
    private String selectedColumnSize;
    private final String[] numbers = {"1", "2", "3", "4", "5", "6",
            "1", "2", "3", "4", "5", "6"};

    CursorWheelLayout wheel_text_left, wheel_text_right;
    List<MenuItemData> textList_left;
    List<MenuItemData> textList_right;
    Operation operation;

    SharedPreferences sharedPreferences;
    Boolean state;
    Boolean buttonState = false;
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
        setContentView(R.layout.matrix_size);

        TextView functionName = findViewById(R.id.function_name);

        //передача с предыдущего экрана название функции
        if (getIntent().hasExtra("selected")) {
            operation = getIntent().getParcelableExtra("selected");
            functionName.setText(operation.getName());
        }

        //передача с последующего экрана название функции
        if (getIntent().hasExtra("selected_next")) {
            operation = getIntent().getParcelableExtra("selected_next");
            functionName.setText(operation.getName());
        }

        wheel_text_left = findViewById(R.id.wheel_text_left);
        wheel_text_right = findViewById(R.id.wheel_text_right);
        loadData();

        wheel_text_left.setOnMenuSelectedListener(this);
        wheel_text_right.setOnMenuSelectedListener(this);
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

    private void loadData() {
        textList_left = new ArrayList<>();
        textList_right = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            textList_left.add(new MenuItemData(numbers[i]));
            textList_right.add(new MenuItemData((numbers[numbers.length - i - 1])));
        }

        WheelTextAdapter adapter_left = new WheelTextAdapter(getBaseContext(), textList_left);
        WheelTextAdapter adapter_right = new WheelTextAdapter(getBaseContext(), textList_right);
        wheel_text_left.setAdapter(adapter_left);
        wheel_text_right.setAdapter(adapter_right);
    }

    @Override
    public void onItemSelected(CursorWheelLayout parent, View view, int pos) {
        if (parent.getId() == R.id.wheel_text_left) {
            selectedRowSize = textList_left.get(pos).number;
        }
        if (parent.getId() == R.id.wheel_text_right) {
            selectedColumnSize = textList_right.get(pos).number;
        }
    }

    public void onSwipeBack() {
        Intent intent;
        switch (operation.getNameOfClass()) {
            case "Matrix":
                intent = new Intent(CategoryOperationMatrixA.this, Matrix.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                break;
            case "MatrixMatrix":
                intent = new Intent(CategoryOperationMatrixA.this, MatrixMatrix.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                break;
            case "MatrixLambda":
                intent = new Intent(CategoryOperationMatrixA.this, MatrixLambda.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                break;
        }
    }

    public void onClickToInputMatrix(View view) {
        if (!CheckSize.checkSize(operation.getName(), selectedRowSize, selectedColumnSize)) {
            Toast toast = Toast.makeText(this,
                    operation.getName() + " применяется только к квадратным матрицам",
                    Toast.LENGTH_LONG);
            toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL,
                    0, 0);
            toast.show();
            return;
        }

        Intent intent = new Intent(CategoryOperationMatrixA.this, MatrixInput.class);

        intent.putExtra("selected_row_size", selectedRowSize);
        intent.putExtra("selected_column_size", selectedColumnSize);
        intent.putExtra("selected", operation);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    public void onClickExtraMenu(View view) {
        ImageButton vision = findViewById(R.id.vision);
        ImageButton clock = findViewById(R.id.clock);
        //ImageButton photo = findViewById(R.id.photo);

        if (buttonState) {
            buttonState = !buttonState;
            vision.setImageResource(R.drawable.ic_visibility);
            Animation anim = AnimationUtils.loadAnimation(this, R.anim.disappearance);
            clock.startAnimation(anim);
            clock.setVisibility(View.GONE);
            //photo.setVisibility(View.GONE);
        } else {
            buttonState = !buttonState;
            vision.setImageResource(R.drawable.ic_visibility_off);
            clock.setVisibility(View.VISIBLE);
            Animation anim = AnimationUtils.loadAnimation(this, R.anim.appearance);
            clock.startAnimation(anim);
            clock.setOnClickListener(view1 -> {
                Intent intent = new Intent(CategoryOperationMatrixA.this, History.class);
                intent.putExtra("selected", operation);
                startActivity(intent);
            });
            //photo.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}

class MenuItemData {
    public String number;

    public MenuItemData(String number) {
        this.number = number;
    }
}