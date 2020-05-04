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

import java.io.Console;
import java.io.InputStream;
import java.net.URL;
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

        String imageUrl = product.getImageUrl();

        Picasso.get().load(imageUrl).into(iconImageView);

        textViewName.setText(product.getName());
        textViewPrice.setText(product.getPrice().toString());

        if(imageUrl.equals("21efda5f-0db7-4ecb-a421-52a89287db10")){
            try {
                HttpsTrustManager.allowAllSSL();
                WebClient client = new WebClient();
                client.GetImageById(imageUrl);

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
                    iconImageView.setImageBitmap(decodedByte);
                }


            }
            catch (Exception ex){
                String excep = ex.getMessage();
            }
        }



        return convertView;
    }

}