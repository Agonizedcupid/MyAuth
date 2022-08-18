package com.applicaiton.my_auth.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.applicaiton.my_auth.Interface.ClickOperationInterface;
import com.applicaiton.my_auth.Interface.LineInterface;
import com.applicaiton.my_auth.LinesActivity;
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


                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("You are about to reject the request")
                        .setMessage("Are you sure")
                        .setCancelable(false)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                clickOperationInterface.onClick(0, model);
                                dialog.dismiss();
                            }
                        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int i) {
                                dialog.dismiss();
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();


            }
        });
        holder.authorizeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Are you sure you want to Authorize")
                        .setMessage("")
                        .setCancelable(false)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                clickOperationInterface.onClick(1, model);
                                dialog.dismiss();
                            }
                        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int i) {
                                dialog.dismiss();
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();

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
