package com.example.entrega_primera;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;

import java.util.List;

public class LoginActivity extends AppCompatActivity {

    private EditText usernameField, passwordField;
    private Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        usernameField = findViewById(R.id.username);
        passwordField = findViewById(R.id.password);
        loginButton = findViewById(R.id.login_button);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameField.getText().toString();
                String password = passwordField.getText().toString();

                WorkManager.getInstance(LoginActivity.this).getWorkInfosByTagLiveData("REMOTE_DB_WORK")
                        .observe(LoginActivity.this, new Observer<List<WorkInfo>>() {
                            @Override
                            public void onChanged(List<WorkInfo> workInfos) {
                                for (WorkInfo workInfo : workInfos) {
                                    if (workInfo.getState() == WorkInfo.State.SUCCEEDED) {
                                        if (workInfo.getOutputData().getString("status") != null) {
                                            if (workInfo.getOutputData().getString("status").equals("ok")) {
                                                System.out.println("LoginActivity - status: " + workInfo.getOutputData().getString("status"));
                                                SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE);
                                                SharedPreferences.Editor myEdit = sharedPreferences.edit();
                                                myEdit.putString("username", username);
                                                myEdit.putString("nombre", workInfo.getOutputData().getString("nombre"));
                                                myEdit.apply();

                                                Intent intent = new Intent(LoginActivity.this, ItemListActivity.class);
                                                startActivity(intent);
                                                finish();
                                            } else {
                                                System.out.println("LoginActivity - status: " + workInfo.getOutputData().getString("status"));
                                                Toast.makeText(LoginActivity.this, "Invalid username or password", Toast.LENGTH_SHORT).show();
                                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                                startActivity(intent);
                                            }
                                        }
                                    }
                                }
                            }
                        });

                OneTimeWorkRequest registerRequest = new OneTimeWorkRequest.Builder(RemoteDBHandler.class)
                        .setInputData(new Data.Builder()
                                .putString("tag", "login")
                                .putString("username", username)
                                .putString("password", password)
                                .build())
                        .addTag("REMOTE_DB_WORK")
                        .build();
                WorkManager.getInstance(LoginActivity.this).enqueue(registerRequest);
            }
        });
    }
}