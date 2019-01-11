package com.appsaga.lnmiitstationary;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import static android.support.v7.widget.AppCompatDrawableManager.get;

public class OrderSummary extends AppCompatActivity {

    ArrayList<Quantity> quantity;
    ListView list;
    QuantityAdapter quantityAdapter;
    HashSet<String> orders;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_summary);

        Intent intent = getIntent();
        ArrayList<String> Items = intent.getStringArrayListExtra("items");

        quantity = new ArrayList<>();
        /*String name = intent.getStringExtra("items");

        TextView text = findViewById(R.id.name);
        text.setText(name);*/

        for(int i=0;i<Items.size();i++){

            quantity.add(new Quantity(Items.get(i),false,"0",false));
        }

        quantityAdapter = new QuantityAdapter(this,quantity);
        list = findViewById(R.id.item_list);

        list.setAdapter(quantityAdapter);

        FloatingActionButton goTo = findViewById(R.id.goTo);

        goTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(OrderSummary.this,com.appsaga.lnmiitstationary.ConfirmOrder.class);
                startActivity(intent);
            }
        });
    }

    public void increment(View view){

        int position = (Integer) view.getTag();
        //Toast.makeText(OrderSummary.this,quantity.get(position).getQuantity(),Toast.LENGTH_SHORT).show();

        int quant = Integer.parseInt(quantity.get(position).getQuantity());
        quant++;

       // Toast.makeText(OrderSummary.this,""+quant,Toast.LENGTH_SHORT).show();
        quantity.get(position).setQuantity(""+quant);

        list.setAdapter(quantityAdapter);

        if(quant>0){
            orders.add(quantity.get(position).getItemName());
        }
    }

    public void decrement(View view){

        int position = (Integer) view.getTag();
       // Toast.makeText(OrderSummary.this,quantity.get(position).getQuantity(),Toast.LENGTH_SHORT).show();

        int quant = Integer.parseInt(quantity.get(position).getQuantity());

        if(quant>0)
            quant--;

        //Toast.makeText(OrderSummary.this,""+quant,Toast.LENGTH_SHORT).show();
        quantity.get(position).setQuantity(""+quant);

        list.setAdapter(quantityAdapter);

        if(quant==0){
            orders.remove(quantity.get(position).getItemName());
        }
    }
}