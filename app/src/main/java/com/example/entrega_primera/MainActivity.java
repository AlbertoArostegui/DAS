package com.example.entrega_primera;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ListView listView;
    private ArrayList<Item> listaItem;
    private ItemAdapter adapter;
    private DBHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.custom_list_view);

        listaItem = new ArrayList<Item>();

        adapter = new ItemAdapter(this, R.layout.card_view, listaItem);
        listView.setAdapter(adapter);

        dbHandler = new DBHandler(this);
        cargarItemsDeBaseDeDatos();
    }

    private void cargarItemsDeBaseDeDatos() {
        DBHandler handler = new DBHandler(this);
        SQLiteDatabase db = handler.getReadableDatabase();
        Cursor cu = db.query(DBHandler.TABLE_ITEMS,
                new String[]{DBHandler.COLUMN_ID, DBHandler.COLUMN_TITLE, DBHandler.COLUMN_DESCRIPTION},
                null, null, null, null, null);

        listaItem.clear();
        if (cu != null && cu.moveToFirst()) {
            do {
                int id = cu.getInt(0);
                String titulo = cu.getString(1);
                String desc = cu.getString(2);
                listaItem.add(new Item(titulo, desc));
            } while (cu.moveToNext());
            cu.close();
        }
        db.close();
        adapter.notifyDataSetChanged();
    }
}