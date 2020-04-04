package com.example.myapplication;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.JsonReader;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity {

    private EditText nameEditText;

    private EditText dateEditText;

    private EditText postalCodeEditText;

    private EditText addressEditText;

    private ListView contentListView;

    private Button button;

    private Button clearButton;

    private Button runButton;

    private ArrayAdapter<String> adapter;

    private ArrayList<String> dataList;

    private DatabaseManager dbmanager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        dbmanager = new DatabaseManager(this);

        this.nameEditText = findViewById(R.id.ID1_nameEditText);

        this.addressEditText = findViewById(R.id.ID1_addresseditText3);

        this.dateEditText = findViewById(R.id.ID1_dateEditText8);

        this.postalCodeEditText = findViewById(R.id.ID1_postalCodeeditText);

        this.runButton = findViewById(R.id.ID1_runButton);

        runButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    String postalCode = postalCodeEditText.getText().toString();

                    HttpsTrustManager.allowAllSSL();

                    WebClient response = new WebClient(postalCode);

                    while(response.IsReady() == ""){
                        Thread.sleep(1000);
                    }

                    addressEditText.setText(response.GetAddress());
                }
                catch (Exception ex){
                    System.out.println(ex.getMessage());
                }

            }
        });

        this.contentListView = findViewById(R.id.ID1_contentListsView);
        this.dataList = new ArrayList<String>();

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1,dataList);

        contentListView.setAdapter(adapter);

        dbmanager.load(dataList);

        adapter.notifyDataSetChanged();

        button = findViewById(R.id.ID1_button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String name = nameEditText.getText().toString();
                String date = dateEditText.getText().toString();

                Input input = new Input(name, date);

                dbmanager.insert(input);

                adapter.notifyDataSetChanged();

                dbmanager.load(dataList);

            }
        });

        clearButton = findViewById(R.id.ID1_clearButton);

        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dbmanager.clearData();

                adapter.notifyDataSetChanged();

                dbmanager.load(dataList);

            }
        });

        contentListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String selectedItemContent = (String) contentListView.getItemAtPosition(position);
                String[] itemArr = selectedItemContent.split("-");
                String elementContent = itemArr[0];
                elementContent = elementContent.replace("Id:", "").replace(" ", "");

                Intent intent = new Intent(getBaseContext(), EditActivity.class);
                intent.putExtra("itemId", elementContent);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
