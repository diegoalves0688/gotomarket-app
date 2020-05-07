package com.example.GoToMarket;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class OrdersAdapter extends ArrayAdapter<Order> {
    public OrdersAdapter(Context context, ArrayList<Order> orders) {
        super(context, 0, orders);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Order order = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_order, parent, false);
        }

        TextView textViewName = (TextView) convertView.findViewById(R.id.item_order_firstLine);
        TextView textViewPrice = (TextView) convertView.findViewById(R.id.item_order_secondLine);

        textViewName.setText(order.getProductName());
        textViewPrice.setText("R$ " + order.getValue().toString());

        return convertView;
    }
}