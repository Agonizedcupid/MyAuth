package com.applicaiton.my_auth;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.applicaiton.my_auth.Adapter.HeaderAdapter;
import com.applicaiton.my_auth.Database.DatabaseAdapter;
import com.applicaiton.my_auth.Model.HeaderModel;
import com.applicaiton.my_auth.Networking.ResponseHandler;
import com.applicaiton.my_auth.ViewModel.HeaderViewModel;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private HeaderViewModel headerViewModel;
    private DatabaseAdapter databaseAdapter;

    private RecyclerView recyclerView;

    public static int position = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        databaseAdapter = new DatabaseAdapter(this);

        initUI();

        findViewById(R.id.q).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, QueueActivity.class));
            }
        });
    }

    private void initUI() {
        recyclerView = findViewById(R.id.headerListView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

    @Override
    protected void onResume() {
        recyclerView.scrollToPosition(position);
        headerViewModel = new ViewModelProvider(this).get(HeaderViewModel.class);
        headerViewModel.init(databaseAdapter);
        headerViewModel.getHeaders().observe(this, new Observer<ResponseHandler<List<HeaderModel>>>() {
            @Override
            public void onChanged(ResponseHandler<List<HeaderModel>> listResponseHandler) {
                switch (listResponseHandler.status) {
                    case ERROR:
                        recyclerView.setVisibility(View.GONE);
                        Toast.makeText(MainActivity.this, "" + listResponseHandler.errorMessage, Toast.LENGTH_SHORT).show();
                        break;
                    case SUCCESS:
                        recyclerView.setVisibility(View.VISIBLE);
                        HeaderAdapter adapter = new HeaderAdapter(MainActivity.this, listResponseHandler.response);
                        recyclerView.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                        break;
                    case LOADING:
                        break;
                }

            }
        });
        super.onResume();
    }
}