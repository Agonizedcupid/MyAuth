package com.applicaiton.my_auth.Authentication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.applicaiton.my_auth.Interface.LogInInterface;
import com.applicaiton.my_auth.MainActivity;
import com.applicaiton.my_auth.Model.LogInModel;
import com.applicaiton.my_auth.Networking.Networking;
import com.applicaiton.my_auth.R;
import com.applicaiton.my_auth.Service.PostService;
import com.google.android.material.button.MaterialButton;

public class LogInActivity extends AppCompatActivity {

    private EditText userName, pinCode;
    private MaterialButton logInBtn;

    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        initUI();
    }

    @Override
    protected void onResume() {
        if (!PostService.isServiceRunning) {
            ContextCompat.startForegroundService(this, new Intent(this, PostService.class));
        }

        super.onResume();
    }

    private void initUI() {

        progressBar = findViewById(R.id.progressbar);
        userName = findViewById(R.id.userName);
        pinCode = findViewById(R.id.pinCode);
        logInBtn = findViewById(R.id.logIn);

        logInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);

                if (TextUtils.isEmpty(pinCode.getText().toString().trim())) {
                    pinCode.setError("Enter Pin!");
                    pinCode.requestFocus();
                    progressBar.setVisibility(View.GONE);
                    return;
                }
                new Networking().checkLoggedIn(userName.getText().toString(), Integer.parseInt(pinCode.getText().toString()), new LogInInterface() {
                    @Override
                    public void loggedIn(String successMessage, LogInModel model) {

                        if (successMessage.equals("success")) {
                            progressBar.setVisibility(View.GONE);
                            startActivity(new Intent(LogInActivity.this, MainActivity.class));
                            finish();
                        } else {
                            progressBar.setVisibility(View.GONE);
                        }

                    }

                    @Override
                    public void onError(String errorMessage) {
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(LogInActivity.this, "" + errorMessage, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}