package com.newbies.aircheck;

import android.content.DialogInterface;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class UserSymptom extends AppCompatActivity {
    final int symptomNumber = 6;
    final int symptomGrade = 4;
    double[][] stat;
    final int eye = 0;
    final int cough = 1;
    final int sneeze = 2;
    final int nasal = 3;
    final int asthma = 4;
    final int chest_pain = 5;

    String Symptom[] = {"None", "Mild", "Moderate", "Severe"};
    static RadioGroup[] symGroup;
    RadioButton[][] symBtn;
    RadioButton[] valBtn;
    Random r;
    String location, name, curdate, curtime, country;
    int age;
    int val[];
    int selectedId[];
    Button symSubmitButton;
    database db;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        r = new Random();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_symptom);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        setTitle(R.string.usersymptom);
        //getActionBar().setIcon(R.drawable.ic_action_user_input);
        db = new database(this);
        stat= new double[symptomNumber][symptomGrade];
        db.getWritableDatabase();
        Bundle extras = getIntent().getExtras();
        name = extras.getString("name");
        age = extras.getInt("age");
        location = extras.getString("location");
        country = extras.getString("country");
        val = new int[symptomNumber];
        selectedId = new int[symptomNumber];
        valBtn = new RadioButton[symptomNumber];
        symGroup = new RadioGroup[symptomNumber + 2];
        symBtn = new RadioButton[symptomNumber + 2][symptomGrade + 2];
        radioInit();
        symSubmitButton = (Button) findViewById(R.id.symSubmitButton);
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    public void symClicker(View v) {
        for (int i = 0; i < symptomNumber; i++) {
            if (symGroup[i].getCheckedRadioButtonId() == -1) {
                Toast.makeText(UserSymptom.this, "Please fill up all the fields", Toast.LENGTH_SHORT).show();
                return;
            }
            selectedId[i] = symGroup[i].getCheckedRadioButtonId();
            Log.i("Fahim", symBtn[i][0] + " " + selectedId[i]);
            valBtn[i] = (RadioButton) findViewById(selectedId[i]);
            for (int j = 0; j < symptomGrade; j++) {
                if (Symptom[j].equals((valBtn[i].getText())) == true)
                    val[i] = j;
            }
        }
        createOnClick();
        // finish();
        String out = "Name=" + name + "\nAge =" + age + "\nLocation =" + location + "\nCountry" + country + "\nSymptom" ;
        for(int i=0;i<symptomNumber;i++)
            out+=" "+val[i];
        Toast.makeText(UserSymptom.this, out,Toast.LENGTH_SHORT).show();
        onclickshow();
    }

    protected void radioInit() {
        symGroup[0] = (RadioGroup) findViewById(R.id.eyeGroup);
        symGroup[1] = (RadioGroup) findViewById(R.id.coughGroup);
        symGroup[2] = (RadioGroup) findViewById(R.id.sneezeGroup);
        symGroup[3] = (RadioGroup) findViewById(R.id.nasalGroup);
        symGroup[4] = (RadioGroup) findViewById(R.id.AsthemaGroup);
        symGroup[5] = (RadioGroup) findViewById(R.id.chestGroup);
      /*
       symBtn[0][0]=(RadioButton)findViewById(R.id.itchy_none);
        symBtn[0][1]=(RadioButton)findViewById(R.id.itchy_mild);
        symBtn[0][2]=(RadioButton)findViewById(R.id.itchy_moderate);
        symBtn[0][3]=(RadioButton)findViewById(R.id.itchy_severe);
        symBtn[1][0]=(RadioButton)findViewById(R.id.cough_none);
        symBtn[1][1]=(RadioButton)findViewById(R.id.cough_mild);
        symBtn[1][2]=(RadioButton)findViewById(R.id.cough_moderate);
        symBtn[1][3]=(RadioButton)findViewById(R.id.cough_severe);
        symBtn[2][0]=(RadioButton)findViewById(R.id.sneeze_none);
        symBtn[2][1]=(RadioButton)findViewById(R.id.sneeze_mild);
        symBtn[2][2]=(RadioButton)findViewById(R.id.sneeze_moderate);
        symBtn[2][3]=(RadioButton)findViewById(R.id.sneeze_severe);
        symBtn[3][0]=(RadioButton)findViewById(R.id.nasal_none);
        symBtn[3][1]=(RadioButton)findViewById(R.id.nasal_mild);
        symBtn[3][2]=(RadioButton)findViewById(R.id.nasal_moderate);
        symBtn[3][3]=(RadioButton)findViewById(R.id.nasal_severe);
        return; */
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(menuItem);
        }
    }

    public void createOnClick() {

        SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat time = new SimpleDateFormat("HH:MM:SS aa");
        curdate = date.format(new Date());
        curtime = time.format(new Date());

        boolean res = db.insertData(curdate, curtime,
                age, location, country, val[0], val[1], val[2], val[3],val[4],val[5]);
        if (res == true)
            Toast.makeText(UserSymptom.this, "Data Inserted", Toast.LENGTH_LONG).show();
        else
            Toast.makeText(UserSymptom.this, "Data Insertion Failed", Toast.LENGTH_LONG).show();


    }

    public void onclickshow() {
        Cursor cursor = db.getData(location,val);
        if (cursor.getCount() == 0) {
            showMessage("Error", "Data not found");
            return;
        }

        StringBuffer stringBuffer = new StringBuffer();
        double total_count = 0;
        for(int i=0; i<symptomNumber; i++)
            for(int j=0; j<symptomGrade; j++)
                stat[i][j] = 0;
        while (cursor.moveToNext()) {
            /*stringBuffer.append("TIME: " + cursor.getString(1) + "\n" + "DATE: " + cursor.getString(2) + "\n\n");
            stringBuffer.append("Age :" + cursor.getString(3) + "\n");
            stringBuffer.append("Location :" + cursor.getString(4) + "\n");
            stringBuffer.append("Country :" + cursor.getString(5) + "\n");
            stringBuffer.append("Itchy eye :" + cursor.getString(6) + "\n");
            stringBuffer.append("Cough :" + cursor.getString(7) + "\n");
            stringBuffer.append("Sneeze :" + cursor.getString(8) + "\n");
            stringBuffer.append("Nasal obstruction :" + cursor.getString(9) + "\n");
            stringBuffer.append("Asthema :" + cursor.getString(10) + "\n");
            stringBuffer.append("Chest Pain :" + cursor.getString(11) + "\n");
            stringBuffer.append("Air quality :" + cursor.getString(12) + "\n");
            stringBuffer.append("Ash plumes  :" + cursor.getString(13) + "\n");
            stringBuffer.append("Smoke plumes :" + cursor.getString(14) + "\n");
            stringBuffer.append("Relative humidity :" + cursor.getString(15) + "\n");
            stringBuffer.append("\n");*/
            stringBuffer.append(cursor.getString(0)+" \n");
        }
        //showMessage("Your Personal Records :", stringBuffer.toString());
        cursor.close();
        db.close();
        String sendData="";
        int length=location.length();
        if(location.charAt(length-1)==' ')
            location=location.substring(0,length-1);
        //sendData=name.toLowerCase();
        String servLoc=location.toLowerCase();
        servLoc=servLoc.replace(' ','_');
        length=country.length();
        if(country.charAt(length-1)==' ')
            country=country.substring(0,length-1);
        String servCountry=country.toLowerCase();
        servCountry=servCountry.replace(' ','_');
        sendData+=age+" "+servLoc+" "+servCountry;
        for(int i=0;i<symptomNumber;i++)
            sendData+=" "+val[i];
        HttpSend httas = new HttpSend();
        httas.execute(sendData);
    }

    public void showMessage(String title, String message) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setCancelable(true);
        alertDialog.setTitle(title);
        alertDialog.setMessage(message);
        alertDialog.setPositiveButton(
                "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        dialog.dismiss();
                        finish();
                    }
                });
        AlertDialog alert1 = alertDialog.create();
        alert1.show();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "UserSymptom Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.newbies.aircheck/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "UserSymptom Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.newbies.aircheck/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }

    private class HttpSend extends AsyncTask<String, Void, String>

    {
        @Override
        protected String doInBackground(String... str) {
            try {

                //String get_url = "http://113.11.61.167/appserver.php/receive?q=" + str[0].replace(" ", "%20");
                //String get_url = "http://113.11.61.167/";
                String get_url = "https://nasa-maliha-93.c9users.io/appserver.php/receive?q=" + str[0].replace(" ", "%20");
                HttpClient Client = new DefaultHttpClient();
                HttpGet httpget;
                ResponseHandler<String> responseHandler = new BasicResponseHandler();
                httpget = new HttpGet(get_url);
                String content = Client.execute(httpget, responseHandler);

                return content;


            } catch (Exception e) {
                System.out.println(e);
            }
            return "Cannot Connect";
        }

        protected void onPostExecute(String result) {
            showMessage("server recieved:",result);
        }

    }

}
