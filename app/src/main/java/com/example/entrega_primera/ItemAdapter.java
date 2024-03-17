package com.example.entrega_primera;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.ArrayList;

public class ItemAdapter extends ArrayAdapter<Item> {

    private ArrayList<Item> itemList;
    private Context context;

    public ItemAdapter(Context context, int resource, ArrayList<Item> itemList) {
        super(context, resource, itemList);
        this.context = context;
        this.itemList = itemList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_card, parent, false);
        }

        Item currentItem = itemList.get(position);

        TextView textBrand = convertView.findViewById(R.id.textBrand);
        TextView textModel = convertView.findViewById(R.id.textModel);

        textBrand.setText(currentItem.getBrand());
        textModel.setText(currentItem.getModel());

        return convertView;
    }
}