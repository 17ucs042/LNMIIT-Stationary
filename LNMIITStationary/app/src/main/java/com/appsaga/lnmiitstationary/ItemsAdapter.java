package com.appsaga.lnmiitstationary;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.ArrayList;

public class ItemsAdapter extends ArrayAdapter<Items> {

    ArrayList<Items> itemsList;
    Context context;

    ItemsAdapter(Context context,ArrayList<Items> itemList){

        super(context,0,itemList);
        itemsList=itemList;
        this.context=context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if an existing view is being reused, otherwise inflate the view
        View listItemView = convertView;

        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.item_view, parent, false);

            //itemsHolder.selected.setOnCheckedChangeListener((ItemsList) context);
        }

        Items CurrentItem = getItem(position);

        TextView value0 = listItemView.findViewById(R.id.value0);
        value0.setText(CurrentItem.getID());

        TextView value1 = listItemView.findViewById(R.id.value1);
        value1.setText(CurrentItem.getName());

        //TextView value2 = listItemView.findViewById(R.id.value2);
        //value2.setText(CurrentItem.isPerishable());

        TextView value3 = listItemView.findViewById(R.id.value3);
        value3.setText(CurrentItem.getQuantity());

        TextView value4 = listItemView.findViewById(R.id.value4);
        value4.setText(CurrentItem.getOnHold());

       TextView value5 = listItemView.findViewById(R.id.value5);
        value5.setText(CurrentItem.getMinQty());

        CheckBox checkBox = listItemView.findViewById(R.id.check);
        checkBox.setSelected(CurrentItem.isSelected());

        checkBox.setOnCheckedChangeListener((ItemsList) context);
        return listItemView;
    }
}
