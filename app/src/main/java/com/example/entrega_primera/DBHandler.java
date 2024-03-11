package com.example.entrega_primera;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHandler extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "items";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_ITEMS = "items";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_BRAND = "brand";
    public static final String COLUMN_MODEL = "model";
    public static final String COLUMN_PRICE = "price";

    private static final String DATABASE_CREATE = "create table "
            + TABLE_ITEMS + "(" +
            COLUMN_ID + " integer primary key autoincrement, " +
            COLUMN_BRAND + " text not null, " +
            COLUMN_MODEL + " text not null, " +
            COLUMN_PRICE + " float not null);";

    public DBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void dropDB() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_ITEMS, null, null);
        db.close();
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ITEMS);
        onCreate(db);
    }
    public void editItem(Item item) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("UPDATE " + TABLE_ITEMS + " SET " +
                COLUMN_BRAND + " = '" + item.getBrand() + "', " +
                COLUMN_MODEL + " = '" + item.getModel() + "', " +
                COLUMN_PRICE + " = " + item.getPrice() +
                " WHERE " + COLUMN_ID + " = " + item.getId());
        db.close();
    }
    public void deleteItem(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_ITEMS, COLUMN_ID + " = ?",
                new String[]{String.valueOf(id)});
        db.close();
    }
    public Item getItemFromId(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Item item = null;
        Cursor cursor = db.query(TABLE_ITEMS,
                new String[]{COLUMN_ID, COLUMN_BRAND, COLUMN_MODEL, COLUMN_PRICE},
                COLUMN_ID + " = ?",
                new String[]{String.valueOf(id)},
                null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            String marca = cursor.getString(1);
            String modelo = cursor.getString(2);
            Float precio = cursor.getFloat(3);
            item = new Item(id, marca, modelo, precio);
            cursor.close();
        }
        db.close();
        return item;
    }
}
