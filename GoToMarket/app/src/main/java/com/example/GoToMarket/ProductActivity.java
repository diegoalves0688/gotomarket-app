package com.example.GoToMarket;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Type;

public class ProductActivity extends AppCompatActivity {

    ImageView productImageView;

    TextView productNameTextView;

    TextView productValueTextView;

    TextView productDescriptionTextView;

    EditText productCardNumberEditText;

    EditText productCardExpDateEditText;

    EditText productCardSecurityCodeEditText;

    Button buyButton;

    String loggedUserId;

    Product currentProduct;

    private DatabaseManager dbmanager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        dbmanager = new DatabaseManager(this);

        this.productImageView = findViewById(R.id.product_imageView6);
        this.productNameTextView = findViewById(R.id.product_name_textView3);
        this.productValueTextView = findViewById(R.id.product_value_textView3);
        this.productDescriptionTextView = findViewById(R.id.product_description_textView5);
        this.productCardNumberEditText = findViewById(R.id.product_card_number_editText);
        this.productCardExpDateEditText = findViewById(R.id.product_card_exp_editText2);
        this.productCardSecurityCodeEditText = findViewById(R.id.product_card_security_editText3);
        this.buyButton = findViewById(R.id.buy_button);

        Intent intent = getIntent();
        String productSerialized = intent.getStringExtra("productSerialized");

        Type typeMyType = new TypeToken<Product>(){}.getType();
        Gson gson = new Gson();
        Product product = gson.fromJson(productSerialized, typeMyType);

        currentProduct = product;

        Picasso.get().load(product.getImageUrl()).resize(240, 240).centerCrop().into(productImageView);

        productNameTextView.setText(product.getName());
        productValueTextView.setText("R$ " + String.valueOf(product.getPrice()));
        productDescriptionTextView.setText(product.getDescription());

        buyButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                verifyLogin();

                try{

                    String cardNumber = productCardNumberEditText.getText().toString();
                    String cardExpDate = productCardExpDateEditText.getText().toString();
                    String cardSecurityCode = productCardSecurityCodeEditText.getText().toString();

                    Order order = new Order(Long.valueOf(currentProduct.getPrice()),
                            currentProduct.getName(),
                            Long.valueOf(currentProduct.getOwnerId()),
                            Long.valueOf(loggedUserId),
                            cardNumber, cardExpDate, cardSecurityCode);

                    HttpsTrustManager.allowAllSSL();
                    WebClient client = new WebClient();
                    client.PostNewOrder(order);

                    while(client.IsReady() == false){
                        if(client.interator >= client.max_interator_retries)
                            throw new Exception("Max wait has reached.");
                        Thread.sleep(1000);
                        client.interator++;
                    }

                    ShowMessage("Pedido criado com sucesso.");
                    Intent intent = new Intent(getBaseContext(), MainActivity.class);
                    startActivity(intent);

                }catch (Exception ex){
                    ShowMessage(ex.getMessage());
                }
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

    public void verifyLogin(){
        dbmanager = new DatabaseManager(this);
        User loggedUser = dbmanager.loadUser();
        if(loggedUser == null){
            ShowMessage("Você precisa estar logado para comprar ou criar um produto.");
            Intent intent = new Intent(getBaseContext(), LoginActivity.class);
            startActivity(intent);
        }else{
            loggedUserId = loggedUser.getId();
        }
    }
}
