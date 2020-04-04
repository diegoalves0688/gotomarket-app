package com.example.myapplication;

import android.os.AsyncTask;
import android.util.JsonReader;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;

public class WebClient {

    private String postalCode;

    private String street;

    private String complement;

    private String city;

    private String state;

    private String ready = "";

    private Boolean success;

    public WebClient(String postalCode){

        this.postalCode = postalCode;

        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL("https://gotomarket.hopto.org/api/users/");

                    HttpsURLConnection connection = (HttpsURLConnection)url.openConnection();

                    connection.connect();

                    int responseCode = connection.getResponseCode();

                    if(responseCode == 200){
                        success = true;
                    }

                    InputStream inputStream = url.openStream();

                    Reader streamReader = new InputStreamReader(inputStream);

                    Gson gson = new Gson();
                    ArrayList<String> retorno = gson.fromJson(streamReader, ArrayList.class);

                    SetStreet(retorno.toString());

                    /*jsonReader.beginObject();

                    if(jsonReader.hasNext()){


                        jsonReader.nextName();
                        jsonReader.skipValue();

                        jsonReader.nextName();
                        SetStreet(jsonReader.nextString());

                    }*/

                    ready = "yes";
                }
                catch (Exception ex){

                    ready = "fail";

                }
            }
        });

    }

    public String IsReady(){
        return ready;
    }

    public void SetStreet(String street){
        this.street = street;
    }

    public String GetAddress(){
        return "Street: " + street;
    }

    public String GetPostalCode(){
        return postalCode;
    }

    public void SetPostalCode(String postalCode){
        this.postalCode = postalCode;
    }

}
