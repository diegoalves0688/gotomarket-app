package com.example.GoToMarket;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

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

        textViewName.setText(product.getName());
        textViewPrice.setText(product.getPrice().toString());

        //Picasso.get().load(product.getImageId()).into(iconImageView);

        Picasso.get().load(product.getImageId()).into(iconImageView);

        //SetImage(iconImageView, product.getImageId());

        return convertView;
    }

    public void SetImage(ImageView imageView, String imageId){
        try {

            HttpsTrustManager.allowAllSSL();

            if(client.imageInstanceCache != null && client.imageInstanceCache.containsKey(imageId)){
                ImageContent imageContent = client.imageInstanceCache.get(imageId);
                if(imageContent.name != null){
                    byte[] decodedString = Base64.decode(imageContent.image, Base64.DEFAULT);
                    Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                    imageView.setImageBitmap(decodedByte);
                    return;
                }
            }

            client.GetImageById(imageId);

            while(client.IsReady() == false){
                if(client.interator >= client.max_interator_retries)
                    throw new Exception("Max wait has reached.");
                Thread.sleep(1000);
                client.interator++;
            }

            ImageContent imageContent = client.GetImageContent();

            if(imageContent.name != null){
                byte[] decodedString = Base64.decode(imageContent.image, Base64.DEFAULT);
                Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                imageView.setImageBitmap(decodedByte);
            }
        }
        catch (Exception ex){
            String excep = ex.getMessage();
        }
    }

}