package com.applicaiton.my_auth.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.applicaiton.my_auth.Model.QueueModel;
import com.applicaiton.my_auth.QueueActivity;
import com.applicaiton.my_auth.R;

import java.util.List;

public class QueueAdapter extends RecyclerView.Adapter<QueueAdapter.ViewHolder> {

    private Context context;
    private List<QueueModel> list;

    public QueueAdapter(Context context, List<QueueModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.single_queue_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        QueueModel model = list.get(position);
        try {
            holder.authLineId.setText(String.valueOf(model.getIntAuthLineId()));
            holder.isAuthorized.setText(String.valueOf(model.getIsAuthorized()));
            holder.instructions.setText(String.valueOf(model.getInstructions()));
        }catch (Exception e) {

        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView authLineId,instructions,isAuthorized;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            authLineId = itemView.findViewById(R.id.primaryKey);
            instructions = itemView.findViewById(R.id.item);
            isAuthorized = itemView.findViewById(R.id.isAuthorized);
        }
    }
}
