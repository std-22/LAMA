package io.github.studio22.la;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

public class CategoryOperation extends AppCompatActivity {
    public static final String EXTRA_OPERATION = "operation_id";
    private static final String TAG = "CategoryOperation";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.matrix_size);

        if(getIntent().hasExtra("selected")){
            Operation operation = getIntent().getParcelableExtra("selected");
            Log.d(TAG, "onCreate: " + operation.toString());
        }
    }
}
