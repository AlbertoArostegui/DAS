package com.example.entrega_primera;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;


public class ItemDetailActivity extends AppCompatActivity {

    private TextView textViewBrand;
    private TextView textViewModel;
    private TextView textViewPrice;
    private Button buttonEdit;
    private DBHandler dbHandler;
    private int itemId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_detail);

        textViewBrand = findViewById(R.id.textViewBrand);
        textViewModel = findViewById(R.id.textViewModel);
        textViewPrice = findViewById(R.id.textViewPrice);
        buttonEdit = findViewById(R.id.buttonEdit);

        DBHandler db = new DBHandler(this);

        // Obtener el ID del elemento de la intención
        itemId = getIntent().getIntExtra("item_id", -1);

        if (itemId != -1) {
            // Cargar los detalles del elemento desde la base de datos
            Item item = db.getItemFromId(itemId);
            if (item != null) {
                System.out.println(item.getBrand());
                textViewBrand.setText("Marca: " + item.getBrand());
                textViewModel.setText("Modelo: " + item.getModel());
                textViewPrice.setText("Precio: " + Float.toString(item.getPrice()) + "€");
            }
        }

        // Configurar el botón para editar el elemento
        /*
        buttonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Abrir la actividad de edición con el ID del elemento
                Intent intent = new Intent(ItemDetailActivity.this, EditItemActivity.class);
                intent.putExtra("item_id", itemId);
                startActivity(intent);
            }
        });
        */
    }
}
