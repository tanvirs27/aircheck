package com.newbies.aircheck;

import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;

public class mapper extends AppCompatActivity {
    String location,userLocation,servReq;
    EditText hlthReqText;
    double longitude,latitude;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapper);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled( true );
        setTitle("Health Predictor");

        hlthReqText=(EditText)findViewById(R.id.hlthReqText);
    }

    public void getlong(){
        Geocoder coder = new Geocoder(this);

        Log.i("rifat","in getlong");
        try {
            ArrayList<Address> adresses = (ArrayList<Address>) coder.getFromLocationName(location, 50);
            for(Address add : adresses){

                longitude = add.getLongitude();
                latitude = add.getLatitude();

                Log.i("rifat",longitude+" "+latitude);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void hlthReq(View v)
    {
        InputMethodManager inputManager = (InputMethodManager)
                getSystemService(Context.INPUT_METHOD_SERVICE);

        inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS);
        location= hlthReqText.getText().toString();
        userLocation=location;
        getlong();
        Intent intent=new Intent(mapper.this,DataGetter.class);
        intent.putExtra("name", "Fahim");
        intent.putExtra("age","20");
        intent.putExtra("location",location);
        intent.putExtra("country","bangladesh");
        intent.putExtra("long",longitude);
        intent.putExtra("lat",latitude);
        intent.putExtra("choice",2);
        startActivity(intent);
        finish();
        //servReq(location);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem)
    {
        switch (menuItem.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(menuItem);
        }
    }
}
