package com.example.entrega_primera;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;

public class EditItemActivity extends AppCompatActivity {

    private EditText editTextBrand;
    private EditText editTextModel;
    private EditText editTextPrice;
    private Button buttonSave;
    private DBHandler dbHandler;
    private int itemId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);

        editTextBrand = findViewById(R.id.editTextBrand);
        editTextModel = findViewById(R.id.editTextModel);
        editTextPrice = findViewById(R.id.editTextPrice);
        buttonSave = findViewById(R.id.buttonSave);

        dbHandler = new DBHandler(this);

        // Obtener el ID del elemento de la intención
        itemId = getIntent().getIntExtra("item_id", -1);

        if (itemId != -1) {
            // Cargar los detalles del elemento desde la base de datos
            Item item = dbHandler.getItemFromId(itemId);
            if (item != null) {
                editTextBrand.setText(item.getBrand());
                editTextModel.setText(item.getModel());
                editTextPrice.setText(Float.toString(item.getPrice()));
            }
        }

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Obtener los valores de los campos de texto
                String brand = editTextBrand.getText().toString();
                String model = editTextModel.getText().toString();
                float price = Float.parseFloat(editTextPrice.getText().toString());

                // Crear un nuevo objeto Item con los valores editados
                Item editedItem = new Item(itemId, brand, model, price);

                dbHandler.editItem(editedItem);

                finish();
            }
        });
    }

}