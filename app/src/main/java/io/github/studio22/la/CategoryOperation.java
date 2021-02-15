package io.github.studio22.la;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class CategoryOperation extends AppCompatActivity {
    private static String selectedRowSize;
    private static String selectedColumnSize;
    private static String nameOfFunction;

    @Override
    public void onCreate(Bundle savedInstanceState) {
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

        Spinner stringSpinner = findViewById(R.id.string_size);
        stringSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                selectedRowSize = parentView.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                return;
            }
        });

        Spinner columnSpinner = findViewById(R.id.column_size);
        columnSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                selectedColumnSize = parentView.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                return;
            }
        });
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
