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

        dbHandler = new DBHandler(this);

        // Obtener el ID del elemento de la intención
        itemId = getIntent().getIntExtra("item_id", -1);

        if (itemId != -1) {
            // Cargar los detalles del elemento desde la base de datos
            Item item = dbHandler.getItemFromId(itemId);
            if (item != null) {
                System.out.println(item.getBrand());
                textViewBrand.setText(getString(R.string.brand) + ": " + item.getBrand());
                textViewModel.setText(getString(R.string.model) + ": " + item.getModel());
                textViewPrice.setText(getString(R.string.price) + ": " + Float.toString(item.getPrice()) + "€");
            }
        }

        buttonDelete = findViewById(R.id.buttonDelete);
        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ItemDetailActivity.this);
                builder.setTitle(getString(R.string.confirm_delete));
                builder.setMessage(getString(R.string.are_you_sure));

                builder.setPositiveButton(getString(android.R.string.yes), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dbHandler.deleteItem(itemId);
                        finish();
                    }
                });

                builder.setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
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

    @Override
    protected void onResume() {
        super.onResume();

        // Obtener el ID del elemento de la intención
        int itemId = getIntent().getIntExtra("item_id", -1);

        if (itemId != -1) {
            // Cargar los detalles del elemento desde la base de datos
            Item item = dbHandler.getItemFromId(itemId);
            if (item != null) {
                // Actualizar las vistas con los detalles del elemento
                TextView textViewBrand = findViewById(R.id.textViewBrand);
                TextView textViewModel = findViewById(R.id.textViewModel);
                TextView textViewPrice = findViewById(R.id.textViewPrice);

                textViewBrand.setText(getString(R.string.brand) + ": " + item.getBrand());
                textViewModel.setText(getString(R.string.model) + ": " + item.getModel());
                textViewPrice.setText(getString(R.string.price) + ": " + Float.toString(item.getPrice()));
            }
        }
    }
}
