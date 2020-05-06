package com.example.GoToMarket;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import android.view.View;

import androidx.core.view.GravityCompat;
import androidx.appcompat.app.ActionBarDrawerToggle;

import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private DatabaseManager dbmanager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        dbmanager = new DatabaseManager(this);
        User loggedUser = dbmanager.loadUser();
        if(loggedUser != null){
            TextView loggedUserEmailPrefix = findViewById(R.id.logged_user_email_prefix_textView);
            loggedUserEmailPrefix.setText("Logado como:");
            TextView loggedUserEmail = findViewById(R.id.logged_user_email_textView5);
            loggedUserEmail.setText(loggedUser.getEmail());
        }

        FloatingActionButton fab = findViewById(R.id.ID1_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), NewProductActivity.class);
                startActivity(intent);
            }
        });

        ArrayList<Product> arrayOfProducts = new ArrayList<Product>();
        ProductsAdapter adapter = new ProductsAdapter(this, arrayOfProducts);
        ListView listView = (ListView) findViewById(R.id.itemListView);
        listView.setAdapter(adapter);

        try {
            WebClient client = new WebClient();
            client.GetProducts();

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

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.ID1_about_action) {
            Intent intent = new Intent(getBaseContext(), AboutActivity.class);
            startActivity(intent);
            return true;
        }

        if (id == R.id.ID1_logout) {
            dbmanager.clearData();

            TextView loggedUserEmailPrefix = findViewById(R.id.logged_user_email_prefix_textView);
            loggedUserEmailPrefix.setText("");
            TextView loggedUserEmail = findViewById(R.id.logged_user_email_textView5);
            loggedUserEmail.setText("");

            ShowMessage("Logout efetuado com sucesso.");
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_new_product) {
            Intent intent = new Intent(getBaseContext(), NewProductActivity.class);
            startActivity(intent);
        }else if (id == R.id.nav_sales) {
            Intent intent = new Intent(getBaseContext(), SalesActivity.class);
            startActivity(intent);
        }else if (id == R.id.nav_orders) {
            Intent intent = new Intent(getBaseContext(), OrdersActivity.class);
            startActivity(intent);
        }else if (id == R.id.nav_login) {
            Intent intent = new Intent(getBaseContext(), LoginActivity.class);
            startActivity(intent);
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void ShowMessage(String message){

        Context context = getApplicationContext();
        CharSequence text = message;
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();

    }


}
