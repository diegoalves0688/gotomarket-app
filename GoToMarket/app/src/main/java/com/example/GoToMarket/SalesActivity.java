package com.example.GoToMarket;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class SalesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sales);

        ArrayList<Order> arrayOfOrders = new ArrayList<Order>();
        SalesAdapter adapter = new SalesAdapter(this, arrayOfOrders);
        ListView listView = (ListView) findViewById(R.id.sales_ListView);
        listView.setAdapter(adapter);

        try {
            HttpsTrustManager.allowAllSSL();
            WebClient client = new WebClient();
            client.GetSales();

            while(client.IsReady() == false){
                if(client.interator >= client.max_interator_retries)
                    throw new Exception("Max wait has reached.");
                Thread.sleep(1000);
                client.interator++;
            }

            ArrayList<Order> orderList = client.OrderList();

            for (int i = 0; i < orderList.size(); i++) {
                adapter.add(orderList.get(i));
            }

        }
        catch (Exception ex){
            ShowMessage(ex.getMessage());
        }
    }

    public void ShowMessage(String message){

        Context context = getApplicationContext();
        CharSequence text = message;
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();

    }
}
