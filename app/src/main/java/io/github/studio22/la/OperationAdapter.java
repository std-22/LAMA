package io.github.studio22.la;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

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

    @Override
    public OperationAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.list_item, parent, false);
        return new ViewHolder(view, clickListener);
    }

    @Override
    public void onBindViewHolder(OperationAdapter.ViewHolder holder, int position) {
        Operation operation = operations.get(position);
        holder.title.setText(operation.getName());
    }

    @Override
    public int getItemCount() {
        return operations.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        final TextView title;
        final View field;
        ClickListener clickListener;

        ViewHolder(View view, ClickListener clickListener){
            super(view);

            title = view.findViewById(R.id.operation_name);
            field = view.findViewById(R.id.operation_view);
            this.clickListener = clickListener;
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            clickListener.onPositionClick(getAdapterPosition());
        }
    }
}