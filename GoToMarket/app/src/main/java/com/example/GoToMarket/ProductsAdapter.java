package com.example.GoToMarket;

import android.content.Context;
import android.graphics.BitmapShader;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.util.ArrayList;

public class ProductsAdapter extends ArrayAdapter<Product> {

    WebClient client;

    public ProductsAdapter(Context context, ArrayList<Product> products) {
        super(context, 0, products);

        client = new WebClient();
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
        TextView textViewInstallments = (TextView) convertView.findViewById(R.id.thirdLine);

        textViewName.setText(product.getName());
        textViewPrice.setText("R$ " + product.getPrice().toString());


        textViewInstallments.setText("À vista");

        Picasso.get().load(product.getImageUrl()).resize(140, 140).centerCrop().into(iconImageView);

        return convertView;
    }
}