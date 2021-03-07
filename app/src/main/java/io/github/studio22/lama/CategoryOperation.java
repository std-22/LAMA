package io.github.studio22.lama;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import github.hellocsl.cursorwheel.CursorWheelLayout;

public class CategoryOperation extends AppCompatActivity implements CursorWheelLayout.OnMenuSelectedListener{
    private static String selectedRowSize;
    private static String selectedColumnSize;
    private static String nameOfFunction;
    private final String[] numbers = {"1", "2", "3", "4", "5", "6",
                                "1", "2", "3", "4", "5", "6"};

    CursorWheelLayout wheel_text_left, wheel_text_right;
    List<MenuItemData> textList_left;
    List<MenuItemData> textList_right;

    SharedPreferences sharedPreferences;
    Boolean state;

    public void onCreate(Bundle savedInstanceState) {
        sharedPreferences = new SharedPreferences(this);
        state = sharedPreferences.loadNightModeState();

        if (state){
            setTheme(R.style.DarkAppTheme);
        } else {
            setTheme(R.style.AppTheme);
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.matrix_size);

        TextView functionName = findViewById(R.id.function_name);

        //передача с предыдущего экрана название функции
        if(getIntent().hasExtra("selected")){
            Operation operation = getIntent().getParcelableExtra("selected");
            nameOfFunction = operation.getName();
            functionName.setText(operation.getName());
        }

        //передача с последующего экрана название функции
        if (getIntent().hasExtra("selected_next")) {
            nameOfFunction = getIntent().getExtras().get("selected_next").toString();
            functionName.setText(nameOfFunction);
        }

        wheel_text_left = findViewById(R.id.wheel_text_left);
        wheel_text_right = findViewById(R.id.wheel_text_right);
        loadData();

        wheel_text_left.setOnMenuSelectedListener(this);
        wheel_text_right.setOnMenuSelectedListener(this);
    }

    private void loadData(){
        textList_left = new ArrayList<>();
        textList_right = new ArrayList<>();
        for (int i=0; i<12; i++){
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
        if (parent.getId() == R.id.wheel_text_left){
            selectedRowSize = textList_left.get(pos).number;
        }
        if (parent.getId() == R.id.wheel_text_right){
            selectedColumnSize = textList_right.get(pos).number;
        }
    }

    public void OnClickBackMatrix(View view) {
        Intent intent = new Intent(CategoryOperation.this, Matrix.class);
        startActivity(intent);
    }

    public void onClickToInputMatrix(View view) {
        if (!CheckSize.checkSize(nameOfFunction, selectedRowSize, selectedColumnSize)){
            Toast toast = Toast.makeText(this, "wrong size", Toast.LENGTH_LONG);
            toast.show();
            return;
        }

        Intent intent = new Intent(CategoryOperation.this, MatrixInput.class);
        intent.putExtra("selected_row_size", selectedRowSize);
        intent.putExtra("selected_column_size", selectedColumnSize);
        intent.putExtra("name", nameOfFunction);
        startActivity(intent);
    }
}

class MenuItemData {
    public String number;
    public MenuItemData(String number){
        this.number = number;
    }
}