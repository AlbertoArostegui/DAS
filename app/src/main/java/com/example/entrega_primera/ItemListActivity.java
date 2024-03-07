package com.example.entrega_primera;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class ItemListActivity extends AppCompatActivity {

    private ListView listView;
    private ArrayList<Item> listaItem;
    private ArrayAdapter<Item> adapter;
    private DBHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_list);
        insertarDatos();

        listView = findViewById(R.id.listView);
        listaItem = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listaItem);
        listView.setAdapter(adapter);

        dbHandler = new DBHandler(this);

        cargarItemsDeBaseDeDatos();

        Button addButton = findViewById(R.id.addButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Abrir la actividad para añadir un nuevo elemento
                Intent intent = new Intent(ItemListActivity.this, AddItemActivity.class);
                startActivity(intent);
            }
        });

        // Configurar el ListView para abrir la actividad de detalle cuando se hace clic en un elemento
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Item item = listaItem.get(position);
                // Abrir la actividad de detalle con los datos del elemento seleccionado
                Intent intent = new Intent(ItemListActivity.this, ItemDetailActivity.class);
                intent.putExtra("item_id", "23");
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        cargarItemsDeBaseDeDatos();
    }
    private void cargarItemsDeBaseDeDatos() {
        DBHandler handler = new DBHandler(this);
        SQLiteDatabase db = handler.getReadableDatabase();
        Cursor cu = db.query(DBHandler.TABLE_ITEMS,
                new String[]{DBHandler.COLUMN_ID, DBHandler.COLUMN_BRAND, DBHandler.COLUMN_MODEL, DBHandler.COLUMN_PRICE},
                null, null, null, null, null);

        listaItem.clear();
        if (cu != null && cu.moveToFirst()) {
            do {
                String brand = cu.getString(0);
                String model = cu.getString(1);
                float price = cu.getFloat(2);
                listaItem.add(new Item(brand, model, price));
            } while (cu.moveToNext());
            cu.close();
        }
        db.close();
        adapter.notifyDataSetChanged();
    }

    private void insertarDatos() {
        ContentValues values = new ContentValues();
        values.put(DBHandler.COLUMN_BRAND, "Ford");
        values.put(DBHandler.COLUMN_MODEL, "Raptor");
        values.put(DBHandler.COLUMN_PRICE, "69999.0");

        SQLiteDatabase db = dbHandler.getWritableDatabase();

        long newRow = db.insert(DBHandler.TABLE_ITEMS, null, values);
        db.close();

        if (newRow != -2) {
            System.out.println("Se inserto correctamente");
        }
    }
}
