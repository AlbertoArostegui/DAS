package com.example.entrega_primera;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.app.AlertDialog;
import android.content.DialogInterface;

public class ItemDetailActivity extends AppCompatActivity {

    private TextView textViewBrand;
    private TextView textViewModel;
    private TextView textViewPrice;
    private Button buttonEdit;
    private DBHandler dbHandler;
    private int itemId;
    private Button buttonDelete;

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

        buttonDelete = findViewById(R.id.buttonDelete);
        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ItemDetailActivity.this);
                builder.setTitle("Confirmar eliminación");
                builder.setMessage("¿Estás seguro de que quieres eliminar este elemento?");

                builder.setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // User clicked Yes, delete the item
                        db.deleteItem(itemId);
                        finish();
                    }
                });

                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        buttonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Abrir la actividad de edición con el ID del elemento
                Intent intent = new Intent(ItemDetailActivity.this, EditItemActivity.class);
                intent.putExtra("item_id", itemId);
                startActivity(intent);
            }
        });
    }
}
