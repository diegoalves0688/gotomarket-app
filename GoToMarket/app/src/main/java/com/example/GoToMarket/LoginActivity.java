package com.example.GoToMarket;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    EditText userNameEditText;

    EditText userPasswordEditText;

    Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        this.userNameEditText = findViewById(R.id.login_userName_editText);
        this.userPasswordEditText = findViewById(R.id.login_password_editText2);
        this.loginButton = findViewById(R.id.login_button);

        loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                String userName = userNameEditText.getText().toString();
                String userPassword = userPasswordEditText.getText().toString();

                ShowMessage("Login com sucesso: " + userName);

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
