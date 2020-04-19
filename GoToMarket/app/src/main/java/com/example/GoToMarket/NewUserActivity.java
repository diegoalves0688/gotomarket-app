package com.example.GoToMarket;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class NewUserActivity extends AppCompatActivity {

    EditText userNameEditText;

    EditText userPasswordEditText;

    EditText userEmailEditText;

    EditText userAddressEditText;

    EditText userPaymentKeyEditText;

    EditText userPaymentTokenEditText;

    Button saveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_user);

        this.userNameEditText = findViewById(R.id.new_user_name_editText);
        this.userPasswordEditText = findViewById(R.id.new_user_password_editText6);
        this.userEmailEditText = findViewById(R.id.new_user_email_editText2);
        this.userAddressEditText = findViewById(R.id.new_user_address_editText3);
        this.userPaymentKeyEditText = findViewById(R.id.new_user_payment_key_editText4);
        this.userPaymentTokenEditText = findViewById(R.id.new_user_payment_token_editText5);
        this.saveButton = findViewById(R.id.new_user_button);

        saveButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                String userName = userNameEditText.getText().toString();
                String userPassword = userPasswordEditText.getText().toString();

                ShowMessage("Usuario criado com sucesso: " + userName);

                Intent intent = new Intent(getBaseContext(), MainActivity.class);
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
