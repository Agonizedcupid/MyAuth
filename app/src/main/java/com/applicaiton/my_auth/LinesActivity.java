package com.applicaiton.my_auth;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.applicaiton.my_auth.Adapter.LinesAdapter;
import com.applicaiton.my_auth.Common.Constant;
import com.applicaiton.my_auth.Database.DatabaseAdapter;
import com.applicaiton.my_auth.Interface.ClickOperationInterface;
import com.applicaiton.my_auth.Interface.LineInterface;
import com.applicaiton.my_auth.Model.LineModel;
import com.applicaiton.my_auth.Model.QueueModel;
import com.applicaiton.my_auth.Networking.Networking;
import com.google.android.material.button.MaterialButton;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class LinesActivity extends AppCompatActivity implements ClickOperationInterface {

    private RecyclerView recyclerView;
    private MaterialButton authorizeBtn, rejectBtn;
    private TextView toolbarTitle, messageID;

    private DatabaseAdapter databaseAdapter;

    private String headerId = "";
    private String message = "";
    //private String messageId = "";
    private int groupId = -777;
    LinesAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lines);
        databaseAdapter = new DatabaseAdapter(this);

        initUI();
    }

    private void initUI() {

        toolbarTitle = findViewById(R.id.toolbarTitle);
        messageID = findViewById(R.id.messageId);

        rejectBtn = findViewById(R.id.rejectBtn);
        authorizeBtn = findViewById(R.id.authorizeBtn);
        recyclerView = findViewById(R.id.linesListView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        if (getIntent() != null) {
            headerId = getIntent().getStringExtra("headerId");
            message = getIntent().getStringExtra("msg");
            //messageId = getIntent().getStringExtra("msgId");
            groupId = getIntent().getIntExtra("groupId", -777);
            messageID.setText(String.valueOf("Message ID: " + headerId));
            toolbarTitle.setText(message);
        }

        findViewById(R.id.backBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    @Override
    protected void onResume() {
        loadDataFromLocal(headerId);
        super.onResume();
    }

    private void loadDataFromLocal(String headerId) {
        ClickOperationInterface clickOperationInterface = this;
        new Networking().getLines(databaseAdapter, new LineInterface() {
            @Override
            public void gotLines(List<LineModel> list) {
                adapter = new LinesAdapter(LinesActivity.this, list, clickOperationInterface);
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void error(String errorMessage) {
                Toast.makeText(LinesActivity.this, "" + errorMessage, Toast.LENGTH_SHORT).show();
            }
        }, headerId);
    }

    @Override
    public void onClick(int type, LineModel model) {
        StringBuilder builder = new StringBuilder();
        builder.append(model.getIntAuthLineId()).append("|").append(type).append("|").append(groupId)
                .append("|").append(currentDate()).append("|");


        QueueModel mod = new QueueModel(model.getIntAuthLineId(), type, builder.toString());
        if (Constant.isInternetConnected(this)) {
            new Networking().postDirectToServer(mod,0, databaseAdapter);
            List<LineModel> list = databaseAdapter.getLinesByHeaderId(headerId);
            adapter = new LinesAdapter(LinesActivity.this, list, this);
            recyclerView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        } else {
            String feedback = new Networking().insertQueue(databaseAdapter, mod);
            if (!feedback.equals("-777")) {
                long id = databaseAdapter.deleteLines(feedback);
                if (id > 0) {
                    Toast.makeText(this, "Added Queue!", Toast.LENGTH_SHORT).show();
                    List<LineModel> list = databaseAdapter.getLinesByHeaderId(headerId);
                    adapter = new LinesAdapter(LinesActivity.this, list, this);
                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(this, "Failed to add on Queue!", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "Unable to post", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private String currentDate() {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();
        return formatter.format(date);
    }
}