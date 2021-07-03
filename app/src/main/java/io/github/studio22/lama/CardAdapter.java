package io.github.studio22.lama;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.io.FileOutputStream;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Scanner;

/**
 * Адаптер матриц в истории для RecyclerView
 */

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.ViewHolder>{
    private final LayoutInflater inflater;
    private final List<Matrices> matrices;
    //private String selectedRowSize;
    //private String selectedColumnSize;
    Operation operation;
    Context context;
    double[][] matrixA;
    double[][] matrix;
    private static final int[][] resultTextViewID = {
            {R.id.resultA1, R.id.resultA2, R.id.resultA3, R.id.resultA4, R.id.resultA5, R.id.resultA6},
            {R.id.resultB1, R.id.resultB2, R.id.resultB3, R.id.resultB4, R.id.resultB5, R.id.resultB6},
            {R.id.resultC1, R.id.resultC2, R.id.resultC3, R.id.resultC4, R.id.resultC5, R.id.resultC6},
            {R.id.resultD1, R.id.resultD2, R.id.resultD3, R.id.resultD4, R.id.resultD5, R.id.resultD6},
            {R.id.resultE1, R.id.resultE2, R.id.resultE3, R.id.resultE4, R.id.resultE5, R.id.resultE6},
            {R.id.resultF1, R.id.resultF2, R.id.resultF3, R.id.resultF4, R.id.resultF5, R.id.resultF6},
    };

    CardAdapter(Context context, List<Matrices> matrices, Operation operation) {
        this.context = context;
        this.matrices = matrices;
        this.inflater = LayoutInflater.from(context);
        this.operation = operation;
    }

    CardAdapter(Context context, List<Matrices> matrices, Operation operation, double[][] matrixA) {
        this.context = context;
        this.matrices = matrices;
        this.inflater = LayoutInflater.from(context);
        this.matrixA = matrixA;
        //this.selectedRowSize = String.valueOf(matrixA.length - 1);
        //this.selectedColumnSize = String.valueOf(matrixA[0].length - 1);
        this.operation = operation;
    }


    @NonNull
    @Override
    public CardAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.card_item, parent, false);
        return new CardAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CardAdapter.ViewHolder holder, int position) {
        matrix = matrices.get(position).getMatrix();
        int fontSize;
        if (matrix.length >= 4 || matrix[0].length >= 4){
            fontSize = 22;
        } else {
            fontSize = 28;
        }
        holder.subtitle.setText("Date");
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                holder.textViewArray[i][j].setVisibility(View.VISIBLE);
                DecimalFormat df = new DecimalFormat("#.###");
                holder.textViewArray[i][j].setText(df.format(matrix[i][j]));
                holder.textViewArray[i][j].setTextSize(fontSize);
            }
        }

        //обработка кнопки удаления
        holder.ib_delete.setOnClickListener(view -> {
            matrices.remove(position);
            removeRecord(position+1);
            notifyDataSetChanged();
        });

        //обработка кнопки далее
        holder.ib_next.setOnClickListener(view -> {
            matrix = matrices.get(position).getMatrix();
            Intent intent;
            Activity activity = (Activity) context;
            if (matrixA != null) {
                //нужна проверка на размерность матриц для вычисления
                //String selectedRowSizeMatrixB = String.valueOf(matrix.length - 1);
                //String selectedColumnSizeMatrixB = String.valueOf(matrix[0].length - 1);

                intent = new Intent(context, MatrixResult.class);
                intent.putExtra("matrix_a", matrixA);
                intent.putExtra("selected", operation);
                intent.putExtra("matrix_b", matrix);

                context.startActivity(intent);
                activity.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            } else {
                switch (operation.getNameOfClass()) {
                    case "Matrix":
                        intent = new Intent(context, MatrixResult.class);
                        intent.putExtra("matrix_a", matrix);
                        intent.putExtra("selected", operation);

                        context.startActivity(intent);
                        activity.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                        break;
                    case "MatrixMatrix":
                        intent = new Intent(context, CategoryOperationMatrixB.class);
                        intent.putExtra("matrix_a", matrix);
                        intent.putExtra("selected", operation);

                        context.startActivity(intent);
                        activity.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                        break;
                    case "MatrixLambda":
                        intent = new Intent(context, MatrixInput.class);
                        intent.putExtra("matrix_a", matrix);
                        intent.putExtra("selected", operation);

                        context.startActivity(intent);
                        activity.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                        break;
                }
            }
        });
    }

    private void removeRecord(int position) {
        File source = new File(Matrices.internalStorageDir, "history.txt");
        File newFile = new File(Matrices.internalStorageDir,  "history2.txt");
        String currentLine;
        try {
            Scanner scanner = new Scanner(source);
            FileOutputStream fos = new FileOutputStream(newFile, true);
            int i = 1;

            while (scanner.hasNext()) {
                currentLine = scanner.nextLine();

                if (i != position){
                    currentLine += "\n";
                    fos.write(currentLine.getBytes());
                }
                i++;
            }
            fos.close();

            fos = new FileOutputStream(source, false);
            fos.write("".getBytes());
            fos.close();

            fos = new FileOutputStream(source, true);
            scanner = new Scanner(newFile);
            while (scanner.hasNext()) {
                currentLine = scanner.nextLine();
                currentLine += "\n";
                fos.write(currentLine.getBytes());
            }
            fos.close();

            fos = new FileOutputStream(newFile, false);
            fos.write("".getBytes());
            fos.close();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return matrices.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        final TextView subtitle;
        final TextView[][] textViewArray = new TextView[6][6];
        final ImageButton ib_next;
        final ImageButton ib_delete;
        final View field;

        ViewHolder(View view){
            super(view);
            subtitle = view.findViewById(R.id.view_info);
            field = view.findViewById(R.id.field);
            ib_next = view.findViewById(R.id.ib_next);
            ib_delete = view.findViewById(R.id.ib_delete);
            for (int i = 0; i < 6; i++) {
                for (int j = 0; j < 6; j++) {
                    textViewArray[i][j] = view.findViewById(resultTextViewID[i][j]);
                }
            }
        }
    }
}