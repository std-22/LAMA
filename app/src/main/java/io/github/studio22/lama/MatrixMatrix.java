package io.github.studio22.lama;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class MatrixMatrix extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.matrix_matrix);
    }

    public void OnClickBackMatrixMatrix(View view) {
        Intent intent = new Intent(MatrixMatrix.this, MenuNavActivity.class);
        startActivity(intent);
    }
}
