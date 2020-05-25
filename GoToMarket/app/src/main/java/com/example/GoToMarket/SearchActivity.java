package com.example.GoToMarket;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        Intent intent = getIntent();
        String searchParam = intent.getStringExtra("searchParam");
        String searchType = intent.getStringExtra("searchType");

        ArrayList<Product> arrayOfProducts = new ArrayList<Product>();
        ProductsAdapter adapter = new ProductsAdapter(this, arrayOfProducts);
        ListView listView = (ListView) findViewById(R.id.searchListView);
        listView.setAdapter(adapter);

        try {
            WebClient client = new WebClient();
            client.SearchProducts(searchParam, searchType);

            while(client.IsReady() == false){
                if(client.interator >= client.max_interator_retries)
                    throw new Exception("Max wait has reached.");
                Thread.sleep(1000);
                client.interator++;
            }

            ArrayList<Product> productList = client.ProductList();

            for (int i = 0; i < productList.size(); i++) {
                adapter.add(productList.get(i));
            }

        }
        catch (Exception ex){
            ShowMessage(ex.getMessage());
        }

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                Product product = (Product)parent.getAdapter().getItem(position);
                Gson gson = new Gson();
                String productSerialized = gson.toJson(product);

                Intent intent = new Intent(getBaseContext(), ProductActivity.class);
                intent.putExtra("productSerialized", productSerialized);
                startActivity(intent);
            }
        });
    }

    public void ShowMessage(String message){

        Context context = getApplicationContext();
        CharSequence text = message;
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();

    }
}
