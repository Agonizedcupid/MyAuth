package com.applicaiton.my_auth;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.applicaiton.my_auth.Adapter.QueueAdapter;
import com.applicaiton.my_auth.Database.DatabaseAdapter;
import com.applicaiton.my_auth.Model.QueueModel;

import java.util.List;

public class QueueActivity extends AppCompatActivity {
    
    private RecyclerView recyclerView;
    private DatabaseAdapter databaseAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_queue);

        databaseAdapter = new DatabaseAdapter(this);

        initUI();

        findViewById(R.id.backBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    private void initUI() {
        recyclerView = findViewById(R.id.queueListView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void onResume() {
        loadLocalData();
        super.onResume();
    }

    private void loadLocalData() {
        List<QueueModel> list = databaseAdapter.getQueue();

        if (list.size() <=0) {
            Toast.makeText(this, "No Items in the Queue!", Toast.LENGTH_SHORT).show();
        } else {
            QueueAdapter adapter = new QueueAdapter(this, list);
            recyclerView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }

    }
}