package com.example.financial_manager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class ViewItemActivity extends AppCompatActivity {

    private int currentId;

    private EditText nameEdtiText;

    private EditText categoryEdtiText;

    private EditText valueEdtiText;

    private EditText startDateEdtiText;

    private EditText installmentsEdtiText;

    private EditText installmentEdtiText;

    private EditText monthEdtiText;

    private EditText yearEdtiText;

    private DatabaseManager dbmanager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_item);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initializeComponents();
    }

    public void initializeComponents(){

        dbmanager = new DatabaseManager(this);

        this.nameEdtiText = findViewById(R.id.ID2_nameeditText);

        this.categoryEdtiText = findViewById(R.id.ID2_categoryeditText2);
        this.valueEdtiText = findViewById(R.id.ID2_valueeditText3);
        this.startDateEdtiText = findViewById(R.id.ID2_startDateeditText4);
        this.installmentsEdtiText = findViewById(R.id.ID2_installmentseditText5);
        this.installmentEdtiText = findViewById(R.id.ID2_installmenteditText);
        this.monthEdtiText = findViewById(R.id.ID2_montheditText2);
        this.yearEdtiText = findViewById(R.id.ID2_yeareditText3);

        FloatingActionButton saveButton = findViewById(R.id.ID2_savefloatingActionButton);

        Intent intent = getIntent();
        String itemId = intent.getStringExtra("itemId");
        this.currentId = Integer.parseInt(itemId);

        checkUpdate(dbmanager, itemId);

        saveButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                String name = nameEdtiText.getText().toString();
                String category = categoryEdtiText.getText().toString();
                String value = valueEdtiText.getText().toString();
                String startDate = startDateEdtiText.getText().toString();
                String installments = installmentsEdtiText.getText().toString();
                String installment = installmentEdtiText.getText().toString();
                String month = monthEdtiText.getText().toString();
                String year = yearEdtiText.getText().toString();

                Expense expense = new Expense(currentId, name,
                        category, Long.valueOf(value),
                        startDate, Long.valueOf(installments),
                        Long.valueOf(installment),
                        Long.valueOf(month),
                        Long.valueOf(year));

                dbmanager.updateItem(expense);

                ShowMessage("Expense updated: " + name);

                Intent intent = new Intent(getBaseContext(), SearchActivity.class);
                startActivity(intent);

            }
        });

    }

    public void checkUpdate(DatabaseManager dbmanager, String itemId){

        if(itemId!= null && !itemId.equals("")){

            ArrayList<Expense> resultList = dbmanager.search("ID", itemId);

            for (Expense element: resultList) {
                this.nameEdtiText.setText(element.getName());
                this.categoryEdtiText.setText(element.getCategory());
                this.valueEdtiText.setText(String.valueOf(element.getValue()));
                this.startDateEdtiText.setText(element.getStartDate());
                this.installmentsEdtiText.setText(String.valueOf(element.getInstallments()));
                this.installmentEdtiText.setText(String.valueOf(element.getInstallment()));
                this.monthEdtiText.setText(String.valueOf(element.getMonth()));
                this.yearEdtiText.setText(String.valueOf(element.getYear()));
            }

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
