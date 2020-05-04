package com.example.GoToMarket;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;

public class NewProductActivity extends AppCompatActivity {

    EditText productNameEditText;

    EditText imageUrlEditText;

    EditText productValueEditText;

    EditText productQuantityEditText;

    EditText productDecriptionEditText;

    TextView loggedUserIdTextView;

    Button photoButton;

    ImageView image;

    String imageUniqueId;

    Button saveProductButton;

    String ownerId;

    static final int REQUEST_IMAGE_CAPTURE = 1;

    private DatabaseManager dbmanager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_product);

        verifyLogin();

        this.productNameEditText = findViewById(R.id.new_product_name_editText2);
        this.imageUrlEditText = findViewById(R.id.new_product_imageurl_editText4);
        this.productValueEditText = findViewById(R.id.new_product_value_editText3);
        this.productQuantityEditText = findViewById(R.id.new_product_quantity_editText6);
        this.productDecriptionEditText = findViewById(R.id.new_product_description_editText7);
        this.image = findViewById(R.id.new_product_imageView);
        this.photoButton = findViewById(R.id.new_product_photo_button);
        this.saveProductButton = findViewById(R.id.new_product_button);

        photoButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                try{
                    dispatchTakePictureIntent();
                }catch (Exception ex){
                    ShowMessage(ex.getMessage());
                }
            }
        });

        saveProductButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                verifyLogin();
                String productName;
                String productDecription;
                String imageUrl;
                Long productValue;
                Long productQuantity;

                try{

                    productName = productNameEditText.getText().toString();
                    productDecription = productDecriptionEditText.getText().toString();
                    imageUrl = imageUrlEditText.getText().toString();
                    productValue = Long.parseLong(productValueEditText.getText().toString());
                    productQuantity = Long.parseLong(productQuantityEditText.getText().toString());

                    Product product = new Product(productName, productDecription, productValue, productQuantity, imageUrl, ownerId);

                    HttpsTrustManager.allowAllSSL();
                    WebClient client = new WebClient();
                    client.PostNewProduct(product);

                    while(client.IsReady() == false){
                        if(client.interator >= client.max_interator_retries)
                            throw new Exception("Max wait has reached.");
                        Thread.sleep(1000);
                        client.interator++;
                    }

                    ShowMessage("Produto criado com sucesso.");
                    Intent intent = new Intent(getBaseContext(), MainActivity.class);
                    startActivity(intent);

                }catch (Exception ex){
                    ShowMessage(ex.getMessage());
                }
            }
        });
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            image.setImageBitmap(imageBitmap);

            String imageString = BitMapToString(imageBitmap);

            try{

                HttpsTrustManager.allowAllSSL();
                WebClient client = new WebClient();
                client.PostImage(imageString);

                while(client.IsReady() == false){
                    if(client.interator >= client.max_interator_retries)
                        throw new Exception("Max wait has reached.");
                    Thread.sleep(1000);
                    client.interator++;
                }

                ShowMessage("Imagem criada com sucesso.");

            }catch (Exception ex){
                ShowMessage(ex.getMessage());
            }
        }
    }

    public String BitMapToString(Bitmap userImage1) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        userImage1.compress(Bitmap.CompressFormat.PNG, 60, baos);
        byte[] b = baos.toByteArray();
        String Document_img1 = Base64.encodeToString(b, Base64.DEFAULT);
        return Document_img1;
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
            ownerId = loggedUser.getId();
        }
    }
}
