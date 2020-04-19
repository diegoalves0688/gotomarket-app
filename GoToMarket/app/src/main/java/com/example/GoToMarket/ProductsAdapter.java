package com.example.GoToMarket;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ProductsAdapter extends ArrayAdapter<Product> {
    public ProductsAdapter(Context context, ArrayList<Product> products) {
        super(context, 0, products);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Product product = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_product, parent, false);
        }

        ImageView iconImageView = (ImageView) convertView.findViewById(R.id.icon);
        TextView textViewName = (TextView) convertView.findViewById(R.id.firstLine);
        TextView textViewPrice = (TextView) convertView.findViewById(R.id.secondLine);

        Picasso.get().load(product.getImageUrl()).into(iconImageView);

        textViewName.setText(product.getName());
        textViewPrice.setText(product.getPrice().toString());

        return convertView;
    }
}