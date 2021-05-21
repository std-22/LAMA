package io.github.studio22.lama;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import static io.github.studio22.lama.R.drawable.list_item_view;
import static io.github.studio22.lama.R.drawable.list_item_view_down;
import static io.github.studio22.lama.R.drawable.list_item_view_up;

/**
 * Адаптер списка операций для RecyclerView
 */
public class OperationAdapter extends RecyclerView.Adapter<OperationAdapter.ViewHolder>{

    interface ClickListener{
        void onPositionClick(int position);
    }

    private final LayoutInflater inflater;
    private final List<Operation> operations;
    private final ClickListener clickListener;

    OperationAdapter(Context context, List<Operation> operations, ClickListener clickListener) {
        this.operations = operations;
        this.inflater = LayoutInflater.from(context);
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public OperationAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.list_item, parent, false);
        return new ViewHolder(view, clickListener);
    }

    @Override
    public void onBindViewHolder(OperationAdapter.ViewHolder holder, int position) {
        Operation operation = operations.get(position);
        holder.title.setText(operation.getName());
        if (position == 0){
            holder.field.setBackgroundResource(list_item_view_up);
        } else if (position == operations.size()-1){
            holder.field.setBackgroundResource(list_item_view_down);
            holder.line.setVisibility(View.GONE);
            holder.bottom.setVisibility(View.VISIBLE);
        } else {
            holder.field.setBackgroundResource(list_item_view);
        }
    }

    @Override
    public int getItemCount() {
        return operations.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        final TextView title;
        final View field;
        final View line;
        final View bottom;
        ClickListener clickListener;

        ViewHolder(View view, ClickListener clickListener){
            super(view);

            title = view.findViewById(R.id.operation_name);
            field = view.findViewById(R.id.operation_view);
            line  = view.findViewById(R.id.line);
            bottom = view.findViewById(R.id.bottom_space);
            this.clickListener = clickListener;
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            clickListener.onPositionClick(getBindingAdapterPosition());
        }
    }
}