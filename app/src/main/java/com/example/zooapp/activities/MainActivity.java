package com.example.zooapp.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.ActionBar;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.zooapp.R;
import com.example.zooapp.fragments.AnimaisFragment;
import com.example.zooapp.fragments.PrincipalFragment;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubePlayer;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpCookie;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import static android.content.Intent.ACTION_CALL;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;


public class MainActivity extends YouTubeBaseActivity  {


    private Button btnPrincipal, btnAnimal,btnLigar,btnLocaliza;
    private PrincipalFragment principalFragment;
    private AnimaisFragment animaisFragment;

    private static final String OPEN_WEATHER_MAP_URL="http://api.openweathermap.org/data/2.5/weather?lat=&s&lon=%s&units=metrics";
    private static final String OPEN_WEATHER_MAP_API="f322adcc1f864302fca972c745d74aa7";
    static String latitude;
    static String longitude;
    TextView cityField, currentTemperatureField, humidityField;
    Typeface weatherFont;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnAnimal=findViewById(R.id.btnAnimais);
        btnPrincipal=findViewById(R.id.btnPrincipal);
        principalFragment = new PrincipalFragment();
        animaisFragment = new AnimaisFragment();
        FloatingActionButton fab = findViewById(R.id.fab);
        btnLigar = findViewById(R.id.btnLigar);
        btnLocaliza = findViewById(R.id.btnLocaliza);

        btnLocaliza.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ZooLocaliza.class);
                startActivity(intent);
            }
        });

        btnLigar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String numero = "+551150730811";
                Uri uri = Uri.parse("tel: "+numero);
                Intent intent = new Intent(ACTION_CALL,
                        uri);
                if(ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CALL_PHONE)!= PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(MainActivity.this,new String[]{(Manifest.permission.CALL_PHONE)},1);
                }else{
                    startActivity(intent);
                }
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShareZoo();
            }
        });

        //Configurar objeto para o Fragmento

        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.frameLayout,principalFragment);
        transaction.commit();

        btnAnimal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                android.app.FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.frameLayout,animaisFragment);
                transaction.commit();
            }
        });

        btnPrincipal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                android.app.FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.frameLayout,principalFragment);
                transaction.commit();
            }
        });

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        requestPermission();

        FusedLocationProviderClient nFusedLocationProviderClient;
        nFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        if(ActivityCompat.checkSelfPermission(MainActivity.this, ACCESS_FINE_LOCATION)!=PackageManager.PERMISSION_GRANTED){
            return;
        }
        nFusedLocationProviderClient.getLastLocation().addOnSuccessListener(MainActivity.this, new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if(location!=null){
                    latitude=String.valueOf(location.getLatitude());
                    longitude=String.valueOf(location.getLongitude());

                    cityField = findViewById(R.id.txtCityField);
                    currentTemperatureField = findViewById(R.id.txtTemperatura);

                    String[] jsonData = getJSONResponse();

                    cityField.setText(jsonData[0]);
                    currentTemperatureField.setText(jsonData[2]);



                }
            }
        });

    }

    public String[] getJSONResponse(){
        String[] jsonData = new String[4];
        JSONObject jsonWeather = null;
        try{
            jsonWeather = getWeatherJSON(latitude,longitude);
        }catch(Exception e){
            Log.d("Error","Cannot process JSON results",e);
        }
        try{
            if(jsonWeather != null){
                JSONObject details = jsonWeather.getJSONArray("weather").getJSONObject(0);
                JSONObject main = jsonWeather.getJSONObject("main");
                DateFormat df = DateFormat.getDateInstance();

                String city = jsonWeather.getString("name")+","+jsonWeather.getJSONObject("sys").getString("country");
                String description = details.getString("description").toLowerCase(Locale.US);
                //Log.d("asdasd",jsonWeather.toString());
                String temperature = String.format("%.0f",main.getDouble("temp"))+"Graus";
                String humidity = main.getString("humidade: ")+"%";
                String updatedOn = df.format(new Date(jsonWeather.getLong("dt")*1000));

                jsonData[0] = city;
                jsonData[1] = description;
                jsonData[2] = temperature;
                jsonData[3] = humidity;
                jsonData[4] = updatedOn;
            }
        }catch (Exception e){

        }
        return jsonData;
    }





    public static JSONObject getWeatherJSON(String lat, String lon){

        try{
            URL url = new URL(String.format(OPEN_WEATHER_MAP_URL,lat,lon));
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.addRequestProperty("x-api-key",OPEN_WEATHER_MAP_API);
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuffer json = new StringBuffer(1024);
            String tmp = "";
            Log.d("asdasd",url.toString());

            while ((tmp = reader.readLine())!=null){
                json.append(tmp).append("\n");
            }
            reader.close();
            JSONObject data = new JSONObject(json.toString());
            if(data.getInt("cod")!=200){
                return null;
            }
            return data;
        }catch (Exception e){
            return null;
        }
    }

    public void requestPermission(){
        ActivityCompat.requestPermissions(this,new String[]{ACCESS_FINE_LOCATION},1);

    }

    public void ShareZoo(){

        //String zoomaps = "https://www.google.com/maps/place/Parque+Zool%C3%B3gico+de+S%C3%A3o+Paulo/@-23.6508522,-46.622613,17z/data=!3m1!4b1!4m5!3m4!1s0x94ce5b3001f4883d:0xb7f1ba1b449ce36a!8m2!3d-23.6508522!4d-46.6204243";
        //Intent intent = new Intent(Intent.ACTION_VIEW,Uri.parse(zoomaps));
        String zoonumber = "";
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_EMAIL,new String[]{"presidenciazoo@zoologico.sp.gov.br"});
        intent.putExtra(Intent.EXTRA_SUBJECT,"Título de sua mensagem!");
        intent.putExtra(Intent.EXTRA_TEXT,"Olha oque eu descobri sobre o zoológico! \n https://www.youtube.com/watch?v=odD9q6SScZ0");
        //intent.setType("message/rfc822");
        intent.setType("text/plain");
        startActivity(Intent.createChooser(intent,"Compartilhar"));
    }

}