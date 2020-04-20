package com.example.GoToMarket;

import android.os.AsyncTask;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;

public class WebClient {

    private String BASE_URL = "https://gotomarket.hopto.org/";

    private String PRODUCTLIST_URL = BASE_URL + "api/products/";
    private ArrayList<Product> productList;

    private String USERS_URL = BASE_URL + "api/users/";

    private String ORDERS_URL = BASE_URL + "api/orders/";

    public Product currentProduct;

    public User currentUser;

    public Order currentOrder;

    public String userEmail;

    public String response;

    private Boolean done = false;

    public long interator = 1;
    public long max_interator_retries = 10;

    public ArrayList<Product> GetProducts() throws InterruptedException {

        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    HttpsTrustManager.allowAllSSL();

                    URL url = new URL(PRODUCTLIST_URL);

                    HttpsURLConnection connection = (HttpsURLConnection)url.openConnection();

                    connection.connect();

                    int responseCode = connection.getResponseCode();

                    InputStream inputStream = url.openStream();

                    Reader streamReader = new InputStreamReader(inputStream);

                    Type typeMyType = new TypeToken<ArrayList<Product>>(){}.getType();
                    Gson gson = new Gson();
                    ArrayList<Product> list = gson.fromJson(streamReader, typeMyType);

                    productList = list;
                    done = true;
                }
                catch (Exception ex){
                    System.out.println(ex.getMessage());
                    done = true;
                }
            }
        });

        return productList;
    }

    public String PostNewProduct(Product product) throws Exception{

        this.currentProduct = product;

        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                try {

                    URL url = new URL(PRODUCTLIST_URL);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setReadTimeout(20000);
                    conn.setConnectTimeout(20000);
                    conn.setRequestMethod("POST");
                    conn.setDoInput(true);
                    conn.setDoOutput(true);
                    conn.setRequestProperty("Content-Type", "application/json");

                    OutputStream os = conn.getOutputStream();
                    BufferedWriter writer = new BufferedWriter( new OutputStreamWriter(os, "UTF-8"));

                    Gson gson = new Gson();
                    String userSerialized = gson.toJson(currentProduct);

                    writer.write(userSerialized);
                    writer.flush();
                    writer.close();
                    os.close();

                    int responseCode=conn.getResponseCode();
                    if (responseCode == HttpsURLConnection.HTTP_OK) {

                        BufferedReader in=new BufferedReader( new InputStreamReader(conn.getInputStream()));
                        StringBuffer sb = new StringBuffer("");
                        String line="";
                        while((line = in.readLine()) != null) {
                            sb.append(line);
                            break;
                        }
                        in.close();
                        response = sb.toString();
                    }
                    response = null;
                    done = true;
                }
                catch (Exception ex){
                    System.out.println(ex.getMessage());
                    done = true;
                }
            }
        });

        return response;
    }

    public User GetUser(String email) throws InterruptedException {

        this.userEmail = email;

        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    HttpsTrustManager.allowAllSSL();

                    URL url = new URL(USERS_URL + userEmail);

                    HttpsURLConnection connection = (HttpsURLConnection)url.openConnection();

                    connection.connect();

                    int responseCode = connection.getResponseCode();

                    InputStream inputStream = url.openStream();

                    Reader streamReader = new InputStreamReader(inputStream);

                    Type typeMyType = new TypeToken<User>(){}.getType();
                    Gson gson = new Gson();
                    User user = gson.fromJson(streamReader, typeMyType);

                    currentUser = user;
                    done = true;
                }
                catch (Exception ex){
                    System.out.println(ex.getMessage());
                    done = true;
                }
            }
        });

        return currentUser;
    }

    public String PostNewUser(User user) throws Exception{

        this.currentUser = user;

        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                try {

                    URL url = new URL(USERS_URL);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setReadTimeout(20000);
                    conn.setConnectTimeout(20000);
                    conn.setRequestMethod("POST");
                    conn.setDoInput(true);
                    conn.setDoOutput(true);
                    conn.setRequestProperty("Content-Type", "application/json");

                    OutputStream os = conn.getOutputStream();
                    BufferedWriter writer = new BufferedWriter( new OutputStreamWriter(os, "UTF-8"));

                    Gson gson = new Gson();
                    String userSerialized = gson.toJson(currentUser);

                    writer.write(userSerialized);
                    writer.flush();
                    writer.close();
                    os.close();

                    int responseCode=conn.getResponseCode();
                    if (responseCode == HttpsURLConnection.HTTP_OK) {

                        BufferedReader in=new BufferedReader( new InputStreamReader(conn.getInputStream()));
                        StringBuffer sb = new StringBuffer("");
                        String line="";
                        while((line = in.readLine()) != null) {
                            sb.append(line);
                            break;
                        }
                        in.close();
                        response = sb.toString();
                    }
                    response = null;
                    done = true;
                }
                catch (Exception ex){
                    System.out.println(ex.getMessage());
                    done = true;
                }
            }
        });

        return response;
    }

    public String PostNewOrder(Order order) throws Exception{

        this.currentOrder = order;

        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                try {

                    URL url = new URL(ORDERS_URL);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setReadTimeout(20000);
                    conn.setConnectTimeout(20000);
                    conn.setRequestMethod("POST");
                    conn.setDoInput(true);
                    conn.setDoOutput(true);
                    conn.setRequestProperty("Content-Type", "application/json");

                    OutputStream os = conn.getOutputStream();
                    BufferedWriter writer = new BufferedWriter( new OutputStreamWriter(os, "UTF-8"));

                    Gson gson = new Gson();
                    String orderSerialized = gson.toJson(currentOrder);

                    writer.write(orderSerialized);
                    writer.flush();
                    writer.close();
                    os.close();

                    int responseCode=conn.getResponseCode();
                    if (responseCode == HttpsURLConnection.HTTP_OK) {

                        BufferedReader in=new BufferedReader( new InputStreamReader(conn.getInputStream()));
                        StringBuffer sb = new StringBuffer("");
                        String line="";
                        while((line = in.readLine()) != null) {
                            sb.append(line);
                            break;
                        }
                        in.close();
                        response = sb.toString();
                    }
                    response = null;
                    done = true;
                }
                catch (Exception ex){
                    System.out.println(ex.getMessage());
                    done = true;
                }
            }
        });

        return response;
    }

    public Boolean IsReady(){
        return done;
    }

    public User GetCurrentUser(){
        return currentUser;
    }

    public ArrayList<Product> ProductList(){
        return productList;
    }
}