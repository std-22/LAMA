package io.github.studio22.la;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class CategoryOperation extends AppCompatActivity {
    private static String selectedStringSize;
    private static String selectedColumnSize;
    private static String nameOfFunction;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.matrix_size);

        TextView functionName = findViewById(R.id.function_name);

        if(getIntent().hasExtra("selected")){
            Operation operation = getIntent().getParcelableExtra("selected");
            nameOfFunction = operation.getName();
            functionName.setText(operation.getName());
        }

        Spinner stringSpinner = findViewById(R.id.string_size);
        stringSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                selectedStringSize = parentView.getSelectedItem().toString();
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

    public void onClickToInputMatrix(View view) {
        Intent intent = new Intent(CategoryOperation.this, MatrixInput.class);
        intent.putExtra("selected_string_size", selectedStringSize);
        intent.putExtra("selected_column_size", selectedColumnSize);
        intent.putExtra("name", nameOfFunction);
        startActivity(intent);
    }
}
