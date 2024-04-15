package com.example.entrega_primera;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;

import java.util.List;

public class RegisterActivity extends AppCompatActivity {

    private EditText nombreField, usernameField, passwordField, confirmPasswordField;
    private TextView loginLink;
    private Button registerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        nombreField = findViewById(R.id.nombre);
        usernameField = findViewById(R.id.username);
        passwordField = findViewById(R.id.password);
        confirmPasswordField = findViewById(R.id.confirm_password);
        loginLink = findViewById(R.id.login_link);
        registerButton = findViewById(R.id.register_button);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validatePassword(passwordField.getText().toString(), confirmPasswordField.getText().toString())) {
                    WorkManager.getInstance(RegisterActivity.this).getWorkInfosByTagLiveData("REMOTE_DB_WORK")
                            .observe(RegisterActivity.this, new Observer<List<WorkInfo>>() {
                                @Override
                                public void onChanged(List<WorkInfo> workInfos) {
                                    for (WorkInfo workInfo : workInfos) {
                                        if (workInfo.getState() == WorkInfo.State.SUCCEEDED) {
                                            SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE);
                                            SharedPreferences.Editor myEdit = sharedPreferences.edit();
                                            myEdit.putString("username", usernameField.getText().toString());
                                            myEdit.apply();

                                            Intent intent = new Intent(RegisterActivity.this, ItemListActivity.class);
                                            startActivity(intent);
                                            finish();
                                        }
                                    }
                                }
                            });

                    OneTimeWorkRequest registerRequest = new OneTimeWorkRequest.Builder(RemoteDBHandler.class)
                            .setInputData(new Data.Builder()
                                    .putString("tag", "register")
                                    .putString("username", usernameField.getText().toString())
                                    .putString("password", passwordField.getText().toString())
                                    .putString("nombre", nombreField.getText().toString())
                                    .build())
                            .addTag("REMOTE_DB_WORK")
                            .build();
                    WorkManager.getInstance(RegisterActivity.this).enqueue(registerRequest);

                }
            }
        });

        loginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private boolean validatePassword(String password, String confirmPassword) {
        if (password.length() < 8) {
            Toast.makeText(this, "Password must be at least 8 characters long", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (!password.equals(confirmPassword)) {
            Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }
}