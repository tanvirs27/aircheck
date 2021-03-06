package com.newbies.aircheck;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class UserInput extends AppCompatActivity implements
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener,com.google.android.gms.location.LocationListener{

    private static final String TAG = "LocationActivity";
    private static final long INTERVAL = 1000 * 10;
    private static final long FASTEST_INTERVAL = 1000 * 5;

    GoogleApiClient mGoogleApiClient;
    Location mLastLocation;
    LocationRequest mLocationRequest;
    LocationManager locationManager;
    TextView tvLatlong,tvCity,tvCountry;
    double longitude=0,latitude=0;

    String location,name,country;
    int age;
    EditText nameText,ageText,locationText,countryText;
    Button idSubmitButton;
    ImageButton locationButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled( true );
        setTitle(R.string.userinput);
       // getActionBar().setIcon(R.drawable.ic_action_user_input);
        setContentView(R.layout.activity_user_input);
        nameText=(EditText)findViewById(R.id.locxText);
        ageText=(EditText)findViewById(R.id.ageText);
        locationText=(EditText)findViewById(R.id.locationText);
        countryText=(EditText)findViewById(R.id.countryText);
        idSubmitButton=(Button)findViewById(R.id.idSubmitButton);
        locationButton=(ImageButton)findViewById(R.id.locationButton);

        createLocationRequest();
        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }
    }

    public void nameClicker(View V)
    {
        int check=4;
        if(nameText.getText().length()==0 )
        {
               name="Anonymous";
        }
        else {
            check--;
            name = nameText.getText().toString();
        }
        if(locationText.getText().length()==0)
        {
            Toast.makeText(UserInput.this, "Please fill up all the Mandatory fields",Toast.LENGTH_SHORT).show();
            return;
        }
        else {
            check--;
            location=locationText.getText().toString();
        }
        if(countryText.getText().length()==0)
        {
            Toast.makeText(UserInput.this, "Please fill up all the Mandatory fields",Toast.LENGTH_SHORT).show();
            return;
        }
        else {
            check--;
            country=countryText.getText().toString();
        }
        if(ageText.getText().length()==0)
        {
            Toast.makeText(UserInput.this, "Please fill up the Mandatory fields",Toast.LENGTH_SHORT).show();
            return;
        }
        else {
            check--;
            age = Integer.parseInt(ageText.getText().toString());
        }

        if(check==1) {
            exitPrompt();
            return;
        }

        if(longitude==0.0 && latitude==0.0){
            getlong();
        }

        Intent intent=new Intent(UserInput.this,DataGetter.class);
        intent.putExtra("name", name);
        intent.putExtra("age",age);
        intent.putExtra("location",location);
        intent.putExtra("country",country);
        intent.putExtra("long",longitude);
        intent.putExtra("lat",latitude);
        intent.putExtra("choice",1);

        startActivity(intent);
        finish();
    }

    public void exitPrompt()
    {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        builder1.setMessage("Did you forget to write your name?");
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        builder1.setNegativeButton(
                "No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        Intent intent = new Intent(UserInput.this, UserSymptom.class);
                        intent.putExtra("name", name);
                        intent.putExtra("age", age);
                        intent.putExtra("location", location);
                        intent.putExtra("country", country);
                        startActivity(intent);
                        finish();
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();

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

    public void onClickGps(View view){
        InputMethodManager inputManager = (InputMethodManager)
                getSystemService(Context.INPUT_METHOD_SERVICE);

        inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS);

       // Toast.makeText(UserInput.this, "onClickGps",Toast.LENGTH_SHORT).show();

        if (mLastLocation != null) {
            latitude= mLastLocation.getLatitude();
            longitude= mLastLocation.getLongitude();

           // Toast.makeText(UserInput.this, "inside if",Toast.LENGTH_SHORT).show();

            // tvLatlong.setText("Latitude: "+ String.valueOf(latitude)+" Longitude: "+
            //         String.valueOf(longitude));

            getAddress();
        }
        else if(mLastLocation== null){
            //Toast.makeText(UserInput.this, "null",Toast.LENGTH_SHORT).show();
        }
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

    @Override
    public void onConnected(Bundle connectionHint) {



        if ( ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED ) {

            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION},
                    11);
        }

        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                mGoogleApiClient);

        startLocationUpdates();


    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult arg0) {
        Toast.makeText(this, "Failed to connect...", Toast.LENGTH_SHORT).show();

    }

    void getAddress(){

        Log.d("rifat", "inside getAddress");
        Geocoder geocoder = new Geocoder(this, Locale.ENGLISH);
        try {
            Log.d("rifat", "inside try");
            Log.d("rifat", latitude+ " "+ longitude);
            double a= latitude,b=longitude;
            List<Address> addresses = geocoder.getFromLocation(a, b, 1);

            Log.d("rifat", "address got");

            if (addresses.size() > 0) {
              //  System.out.println(addresses.get(0).getLocality());
              //  System.out.println(addresses.get(0).getCountryName());

                //   tvCity.setText(addresses.get(0).getLocality());
             //   tvCountry.setText(addresses.get(0).getCountryName());
                Log.d("rifat", "size greater 0");

                locationText.setText(addresses.get(0).getLocality());
                location=addresses.get(0).getLocality();
                countryText.setText(addresses.get(0).getCountryName());
                country=addresses.get(0).getCountryName();

                Log.d("rifat", location + " "+ country);
            //    Toast.makeText(UserInput.this, location + " "+ country,Toast.LENGTH_SHORT).show();
            }
        } catch (IOException e) {
            Log.d("rifat", e.toString());
            e.printStackTrace();
        }
    }

    protected void createLocationRequest() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(INTERVAL);
        mLocationRequest.setFastestInterval(FASTEST_INTERVAL);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "onStart fired ..............");

        if(!isGpsOn()){
            gpsDialog();
        }
        mGoogleApiClient.connect();
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "onStop fired ..............");


        try {
            if (mGoogleApiClient.isConnected()) {
                mGoogleApiClient.disconnect();
            }
        }
        catch(java.lang.IllegalStateException e)
        {

        };

    }

    protected void startLocationUpdates() {

        if ( ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ) {

            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    11);
        }

        PendingResult<Status> pendingResult = LocationServices.FusedLocationApi.requestLocationUpdates(
                mGoogleApiClient, mLocationRequest, this);
        Log.d(TAG, "Location update started ..............: ");
    }

    @Override
    protected void onPause() {
        super.onPause();
        try {
            stopLocationUpdates();
        }
        catch(java.lang.IllegalStateException e)
        {
            Log.i("Fahim","NO API");
        };
    }

    protected void stopLocationUpdates() {
        try {
            LocationServices.FusedLocationApi.removeLocationUpdates(
                    mGoogleApiClient, this);
            Log.d(TAG, "Location update stopped .......................");
        }
        catch(java.lang.IllegalStateException e)
        {
            Log.i("Fahim","NO API");
        };
    }

    @Override
    public void onResume() {
        super.onResume();
        try {
            if (mGoogleApiClient.isConnected()) {
                startLocationUpdates();
                Log.d(TAG, "Location update resumed .....................");
            }
        }
        catch(java.lang.IllegalStateException e)
        {
            Log.i("Fahim","NO API");
        };
    }

    @Override
    public void onLocationChanged(Location location) {
        Log.d(TAG, "Firing onLocationChanged..............................................");
        mLastLocation = location;
    }

    public boolean isGpsOn() {

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            return true;
        }
        return false;
    }

    public void gpsDialog()
    {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        builder1.setMessage("Do you want to turn GPS on?");
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent callGPSSettingIntent = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivity(callGPSSettingIntent);
                    }
                });

        builder1.setNegativeButton(
                "No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();

    }

}
