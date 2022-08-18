package com.applicaiton.my_auth.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.applicaiton.my_auth.Model.HeaderModel;
import com.applicaiton.my_auth.R;

import java.util.List;

public class HeaderAdapter extends RecyclerView.Adapter<HeaderAdapter.ViewHolder> {

    private Context context;
    private List<HeaderModel> list;

    public HeaderAdapter (Context context, List<HeaderModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.single_headers_list,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        HeaderModel model = list.get(position);
        try {

            holder.messageId.setText(String.valueOf("Message ID: "+model.getIntHeaderID()));
            holder.message.setText(String.valueOf(model.getStrMessage()));
            holder.userName.setText(String.valueOf("From: "+model.getStrUserName()));
            holder.date.setText(String.valueOf("Date sent: "+model.getDate()));

        }catch (Exception e) {
            Log.d("HEADER_ADAPTER", "onBindViewHolder: "+e.getMessage());
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView messageId,message,userName,date;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            messageId = itemView.findViewById(R.id.messageId);
            message = itemView.findViewById(R.id.message);
            userName = itemView.findViewById(R.id.userName);
            date = itemView.findViewById(R.id.date);
        }
    }
}
