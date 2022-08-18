package com.applicaiton.my_auth.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.applicaiton.my_auth.Interface.ClickOperationInterface;
import com.applicaiton.my_auth.Interface.LineInterface;
import com.applicaiton.my_auth.Model.LineModel;
import com.applicaiton.my_auth.R;
import com.google.android.material.button.MaterialButton;

import java.util.List;

public class LinesAdapter extends RecyclerView.Adapter<LinesAdapter.ViewHolder> {

    private Context context;
    private List<LineModel> list;
    ClickOperationInterface clickOperationInterface;
    int type = -1;
    String instructions = "";
    public LinesAdapter(Context context, List<LineModel> list, ClickOperationInterface clickOperationInterface) {
        this.context = context;
        this.list = list;
        this.clickOperationInterface = clickOperationInterface;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.single_lines_list,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        LineModel model = list.get(position);
        try {
            holder.messageOne.setText(model.getStrLineMessage1());
            holder.messageTwo.setText(model.getStrLineMessage2());
            holder.messageThree.setText(model.getStrLineMessage3());
            holder.lineId.setText(String.valueOf("Line Id: "+model.getIntAuthLineId()));
        }catch (Exception e) {

        }


        holder.rejectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickOperationInterface.onClick(0, model);
            }
        });
        holder.authorizeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickOperationInterface.onClick(1, model);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView messageOne, messageTwo,messageThree;
        private TextView lineId;
        private MaterialButton rejectBtn, authorizeBtn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            messageOne = itemView.findViewById(R.id.messageOne);
            messageTwo = itemView.findViewById(R.id.messageTwo);
            messageThree = itemView.findViewById(R.id.messageThree);
            lineId = itemView.findViewById(R.id.lineId);
            rejectBtn = itemView.findViewById(R.id.rejectBtn);
            authorizeBtn = itemView.findViewById(R.id.authorizeBtn);
        }
    }
}
