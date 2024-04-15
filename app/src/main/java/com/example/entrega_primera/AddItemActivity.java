package com.example.entrega_primera;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;

import java.util.List;

public class AddItemActivity extends AppCompatActivity {

    private DBHandler dbHandler;
    private EditText editTextMarca;
    private EditText editTextModel;
    private EditText editTextPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        dbHandler = new DBHandler(this);

        editTextMarca = findViewById(R.id.editTextMarca);
        editTextModel = findViewById(R.id.editTextModel);
        editTextPrice = findViewById(R.id.editTextPrice);

        System.out.println("onCreate");

        WorkManager.getInstance(this).getWorkInfosByTagLiveData("REMOTE_DB_WORK")
                .observe(this, new Observer<List<WorkInfo>>() {
                    @Override
                    public void onChanged(List<WorkInfo> workInfos) {
                        for (WorkInfo workInfo : workInfos) {
                            if (workInfo.getState() == WorkInfo.State.SUCCEEDED) {
                                if (workInfo.getTags().contains("REMOTE_DB_WORK")) {
                                    Data datos = workInfo.getOutputData();
                                    String username = datos.getString("nombre");
                                    System.out.println("AddItemActivity: " + username);
                                    TextView tvRes = findViewById(R.id.tv_result2);
                                    tvRes.setText(username);
                                } else {
                                    System.out.println("AddItemActivity: RemoteDBHandler failed");
                                }
                            }
                        }
                    }
                });

        OneTimeWorkRequest getNameRequest = new OneTimeWorkRequest.Builder(RemoteDBHandler.class)
                .setInputData(new Data.Builder().putString("tag", "getname").putString("username", "usuPrueba").build())
                .addTag("REMOTE_DB_WORK")
                .build();
        WorkManager.getInstance(this).enqueue(getNameRequest);

        Button buttonAdd = findViewById(R.id.buttonAdd);
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String brand = editTextMarca.getText().toString();
                String model = editTextModel.getText().toString();
                float price = Float.parseFloat(editTextPrice.getText().toString());

                Item newItem = new Item(0, brand, model, price);

                insertarItemEnBaseDeDatos(newItem);

                finish();
            }
        });
    }

    private void insertarItemEnBaseDeDatos(Item item) {
        ContentValues values = new ContentValues();
        values.put(DBHandler.COLUMN_BRAND, item.getBrand());
        values.put(DBHandler.COLUMN_MODEL, item.getModel());
        values.put(DBHandler.COLUMN_PRICE, item.getPrice());

        SQLiteDatabase db = dbHandler.getWritableDatabase();
        long newRow = db.insert(DBHandler.TABLE_ITEMS, null, values);
        db.close();

        if (newRow != -1) {
            System.out.println("Se inserto correctamente");
            MainActivity.notifyNewItem(this);
        }
    }
}