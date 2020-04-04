package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

public class EditActivity extends AppCompatActivity {

    private int elementId;

    private TextView idTextView;

    private EditText nameEditText;

    private EditText dateEditText;

    private Button button;

    private Button deleteButton;

    private DatabaseManager dbmanager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        dbmanager = new DatabaseManager(this);

        Intent intent = getIntent();
        final String itemId = intent.getStringExtra("itemId");
        elementId = Integer.parseInt(itemId);

        this.idTextView = findViewById(R.id.ID2_IDTextView);
        this.nameEditText = findViewById(R.id.ID2_nameeditText);
        this.dateEditText = findViewById(R.id.ID2_dateeditText2);

        this.idTextView.setText(String.valueOf(elementId));

        ArrayList<Input> resultList = dbmanager.search(itemId);

        for (Input element: resultList) {
            this.nameEditText.setText(element.getName());
            this.dateEditText.setText(element.getDate());
        }

        button = findViewById(R.id.ID2_button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String name = nameEditText.getText().toString();
                String date = dateEditText.getText().toString();

                Input input = new Input(elementId, name, date);

                dbmanager.updateItem(input);

                Intent intent = new Intent(getBaseContext(), MainActivity.class);
                startActivity(intent);

            }
        });

        deleteButton = findViewById(R.id.ID2_deletebutton2);

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dbmanager.deleteItem(itemId);

                Intent intent = new Intent(getBaseContext(), MainActivity.class);
                startActivity(intent);

            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

}
