package com.example.entrega_primera;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

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