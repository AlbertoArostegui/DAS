package com.example.entrega_primera;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;

import java.util.List;

public class UserProfileActivity extends AppCompatActivity {

    private ImageView userImageView;
    private TextView userNameTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        userImageView = findViewById(R.id.user_image);
        userNameTextView = findViewById(R.id.user_name);

        String username = getSharedPreferences("MySharedPref", MODE_PRIVATE).getString("username", "");

        WorkManager.getInstance(UserProfileActivity.this).getWorkInfosByTagLiveData("REMOTE_DB_WORK")
                .observe(UserProfileActivity.this, new Observer<List<WorkInfo>>() {
                    @Override
                    public void onChanged(List<WorkInfo> workInfos) {
                        for (WorkInfo workInfo : workInfos) {
                            if (workInfo.getState() == WorkInfo.State.SUCCEEDED) {
                                Data outputData = workInfo.getOutputData();
                                String nombre = outputData.getString("nombre");
                                String userimage = outputData.getString("encodedImage");
                                if (userimage != null) {
                                    byte[] decodedString = Base64.decode(userimage, Base64.DEFAULT);
                                    Bitmap userimage_bitmap = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                                    userImageView.setImageBitmap(userimage_bitmap);
                                    userNameTextView.setText(nombre);
                                } else {
                                    userImageView.setImageResource(R.drawable.default_profile_image);
                                    if (nombre != null) {
                                        userNameTextView.setText(nombre);
                                    } else {
                                        userNameTextView.setText("alberto");
                                    }
                                }
                            }
                        }
                    }
                });

        OneTimeWorkRequest pushImageRequest = new OneTimeWorkRequest.Builder(RemoteDBHandler.class)
                .addTag("REMOTE_DB_WORK")
                .setInputData(new Data.Builder()
                        .putString("tag", "getnameandphoto")
                        .putString("username", username)
                        .build())
                .build();
        WorkManager.getInstance(UserProfileActivity.this).enqueue(pushImageRequest);

        Button btnOpenCamera = findViewById(R.id.btn_open_camera);
        btnOpenCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserProfileActivity.this, CameraActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}