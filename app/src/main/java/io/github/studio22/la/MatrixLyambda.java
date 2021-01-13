package io.github.studio22.la;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class MatrixLyambda extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.matrix_lyambda);
    }

    public void OnClickBackMatrixLyambda(View view) {
        Intent intent = new Intent(MatrixLyambda.this, MenuNavActivity.class);
        startActivity(intent);
    }
}
