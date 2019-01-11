package com.appsaga.lnmiitstationary;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;

public class QuantityAdapter extends ArrayAdapter<Quantity> {

    QuantityAdapter(Context context, ArrayList<Quantity> quantity_list){

        super(context,0,quantity_list);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View listItemView = convertView;

        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.quantity_view, parent, false);

            //itemsHolder.selected.setOnCheckedChangeListener((ItemsList) context);
        }

        Quantity item = getItem(position);

        TextView itemName = listItemView.findViewById(R.id.item);
        itemName.setText(item.getItemName());

        TextView quantity = listItemView.findViewById(R.id.quantity);
        quantity.setText(item.getQuantity());

        ImageButton dec = listItemView.findViewById(R.id.dec);
        dec.setClickable(true);

        ImageButton inc = listItemView.findViewById(R.id.inc);
        inc.setClickable(true);

        dec.setTag(position);
        inc.setTag(position);

        return listItemView;
    }
}
