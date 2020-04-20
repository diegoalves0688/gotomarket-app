package com.example.GoToMarket;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity {

    EditText userNameEditText;

    EditText userPasswordEditText;

    Button loginButton;

    Button goToNewUserButton;

    private DatabaseManager dbmanager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        dbmanager = new DatabaseManager(this);

        this.userNameEditText = findViewById(R.id.login_userName_editText);
        this.userPasswordEditText = findViewById(R.id.login_password_editText2);
        this.loginButton = findViewById(R.id.login_button);
        this.goToNewUserButton = findViewById(R.id.login_go_to_newuser_button);

        loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                String userName = userNameEditText.getText().toString();
                String userPassword = userPasswordEditText.getText().toString();

                try {
                    HttpsTrustManager.allowAllSSL();
                    WebClient client = new WebClient();
                    client.GetUser(userName);

                    while(client.IsReady() == false){
                        if(client.interator >= client.max_interator_retries)
                            throw new Exception("Max wait has reached.");
                        Thread.sleep(1000);
                        client.interator++;
                    }

                    User user = client.GetCurrentUser();

                    if(user != null && user.getPassword().equals(MD5_Hash(userPassword))){

                        dbmanager.clearData();
                        dbmanager.insertUser(user);

                        User loggedUser = dbmanager.loadUser();
                        ShowMessage("Login com sucesso: " + loggedUser.getEmail());

                        Intent intent = new Intent(getBaseContext(), MainActivity.class);
                        startActivity(intent);
                    }
                }
                catch (Exception ex){
                    System.out.println(ex.getMessage());
                    ShowMessage("Can't login. Please review your credentials.");
                }

            }
        });

        goToNewUserButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getBaseContext(), NewUserActivity.class);
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

    public static String MD5_Hash(String s) {
        MessageDigest m = null;

        try {
            m = MessageDigest.getInstance("MD5");
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

        m.update(s.getBytes(),0,s.length());
        String hash = new BigInteger(1, m.digest()).toString(16);
        return hash;
    }

}
