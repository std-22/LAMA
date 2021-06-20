package io.github.studio22.lama;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DecimalFormat;
import java.util.List;

/**
 * Адаптер матриц в истории для RecyclerView
 */

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.ViewHolder>{
    private final LayoutInflater inflater;
    private final List<Matrices> matrices;
    private static final int[][] resultTextViewID = {
            {R.id.resultA1, R.id.resultA2, R.id.resultA3, R.id.resultA4, R.id.resultA5, R.id.resultA6},
            {R.id.resultB1, R.id.resultB2, R.id.resultB3, R.id.resultB4, R.id.resultB5, R.id.resultB6},
            {R.id.resultC1, R.id.resultC2, R.id.resultC3, R.id.resultC4, R.id.resultC5, R.id.resultC6},
            {R.id.resultD1, R.id.resultD2, R.id.resultD3, R.id.resultD4, R.id.resultD5, R.id.resultD6},
            {R.id.resultE1, R.id.resultE2, R.id.resultE3, R.id.resultE4, R.id.resultE5, R.id.resultE6},
            {R.id.resultF1, R.id.resultF2, R.id.resultF3, R.id.resultF4, R.id.resultF5, R.id.resultF6},
    };

    CardAdapter(Context context, List<Matrices> matrices) {
        this.matrices = matrices;
        this.inflater = LayoutInflater.from(context);
    }


    @NonNull
    @Override
    public CardAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.card_item, parent, false);
        return new CardAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CardAdapter.ViewHolder holder, int position) {
        double[][] matrix = matrices.get(position).getMatrix();
        int fontSize = 0;
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

        //обработка кнопки удаления - не реализовано удаление и обновление экрана
        holder.ib_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.field.setVisibility(View.GONE);
                holder.ib_next.setVisibility(View.GONE);
                holder.subtitle.setVisibility(View.GONE);
                holder.ib_delete.setVisibility(View.GONE);
            }
        });
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