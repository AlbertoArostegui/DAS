package com.example.entrega_primera;
import android.content.ContentValues;
import android.content.Intent;
import android.content.res.Configuration;
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
    private ItemAdapter adapter;
    private DBHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_list);

        dbHandler = new DBHandler(this);
        listaItem = new ArrayList<Item>();

        listView = findViewById(R.id.listView);
        
        adapter = new ItemAdapter(this, R.layout.item_card, listaItem);
        listView.setAdapter(adapter);

        Button addButton = findViewById(R.id.addButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ItemListActivity.this, AddItemActivity.class);
                startActivity(intent);
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Item item = listaItem.get(position);
                Intent intent = new Intent(ItemListActivity.this, ItemDetailActivity.class);
                intent.putExtra("item_id", item.getId());
                startActivity(intent);
            }
        });

        Button btnUserProfile = findViewById(R.id.btn_user_profile);
        btnUserProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ItemListActivity.this, UserProfileActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        cargarItemsDeBaseDeDatos();
    }
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
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
                int id = cu.getInt(0);
                String brand = cu.getString(1);
                String model = cu.getString(2);
                float price = cu.getFloat(3);
                listaItem.add(new Item(id, brand, model, price));
            } while (cu.moveToNext());
            cu.close();
        }
        db.close();
        adapter.notifyDataSetChanged();
    }


}
