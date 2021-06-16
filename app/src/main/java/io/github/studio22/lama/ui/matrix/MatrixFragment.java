package io.github.studio22.lama.ui.matrix;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import io.github.studio22.lama.MenuNavActivity;
import io.github.studio22.lama.R;

public class MatrixFragment extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_matrix, container, false);

        ImageView two_matrix = root.findViewById(R.id.two_matrix_image);
        ImageView lambda_matrix = root.findViewById(R.id.lambda_matrix_image);
        ImageView matrix = root.findViewById(R.id.matrix_image);

        if (MenuNavActivity.theme){
            two_matrix.setImageResource(R.drawable.two_matrix_dark);
            lambda_matrix.setImageResource(R.drawable.lambda_matrix_dark);
            matrix.setImageResource(R.drawable.one_matrix_dark);
        } else {
            two_matrix.setImageResource(R.drawable.two_matrix);
            lambda_matrix.setImageResource(R.drawable.lambda_matrix);
            matrix.setImageResource(R.drawable.one_matrix);
        }

        return root;
    }
}