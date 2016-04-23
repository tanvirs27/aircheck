package com.newbies.aircheck;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

import java.util.Scanner;

public class Predictor extends AppCompatActivity {
    static int symptomNumber=6;
    static int symptomGrade=4;
    static TextView hlthPredView,humidityView;
    int eye,cough,sneeze,nasal,asthma,chest,maxVal;
    int humidity;
    String[] val,results;
    String location;
    String sendData;
    int[] nope;
    ProgressDialog pDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_predictor);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled( true );
        setTitle("Health Predictor");
        hlthPredView= (TextView) findViewById(R.id.hlthPredView);
        hlthPredView.setMovementMethod(new ScrollingMovementMethod());
        humidityView=(TextView)findViewById(R.id.humidityView);
        Bundle extras = getIntent().getExtras();
        humidity=extras.getInt("humidity");
        location=extras.getString("location");
        sendData=Integer.toString(humidity);
        val=new String[symptomNumber];
        nope=new int[symptomNumber];
        results=new String[symptomNumber];
        HttpSend httas = new HttpSend();
        httas.execute(sendData);

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

    private class HttpSend extends AsyncTask<String, Void, String>

    {
        @Override
        protected String doInBackground(String... str) {
            try {

                //String get_url = "http://113.11.61.167/appserver.php/receive?q=" + str[0].replace(" ", "%20");
                //String get_url = "http://113.11.61.167/";
                String get_url = "https://nasa-maliha-93.c9users.io/relative.php/receive?q=" + str[0].replace(" ", "%20");
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


        protected void onPostExecute(String result)
        {
            humidityView.setText("Relative Humidity for "+location+" "+Integer.toString(humidity));
            pDialog=new ProgressDialog(Predictor.this);
            pDialog.setTitle("Retrieving data from server");
            pDialog.setMessage("Loading");
            pDialog.setIndeterminate(false);
            pDialog.show();
            parser(result);
        }

        void parser(String main)
        {
            if(main.equals("Cannot Connect")==true)
            {
                pDialog.dismiss();
                Toast.makeText(Predictor.this, "Sorry,Could not Connect with the server", Toast.LENGTH_SHORT).show();
                return;

            }
            pDialog.dismiss();
            Scanner S=new Scanner(main);
            for(int i=0;i<symptomNumber;i++)
                val[i]=S.next();
            for(int i=0;i<symptomNumber;i++)
                results[i]="";
            for(int i=0;i<symptomNumber;i++)
                nope[0]=0;
            if(val[0].equals("none")==false)
            {
                nope[0]=1;
                results[0] = "Itchy Eye : " + val[0];
            }
            if(val[1].equals("none")==false)
            {
                nope[1]=1;
                results[1]="Cough : "+ val[1];
            }
            if(val[2].equals("none")==false)
            {
                nope[2]=1;
                results[2]="Sneezing : "+ val[2];
            }
            if(val[3].equals("none")==false)
            {
                nope[3]=1;
                results[3]="Nasal Obstruction : "+ val[3];
            }
            if(val[4].equals("none")==false)
            {
                nope[4]=1;
                results[4]="Asthema : "+val[4];
            }
            if(val[5].equals("none")==false)
            {
                nope[5]=1;
                results[5]="Chest Pain : "+val[5];
            }
            printer();
        }

        void printer()
        {
            int none=0;
            for(int i=0;i<symptomNumber;i++)
            {
                none+=nope[i];
            }
            if(none==0)
            {
                hlthPredView.setText("HURRAH! You are free of any sort of health risk");
                hlthPredView.setTextSize(15);
                return;
            }
            Runnable runnable = new Runnable() {
                String stat="According to Previous user data ,you are in the health risk of being affected by the following symptoms \n"+" \n"+results[0]+"\n"+results[1]+"\n"+results[2]+"\n"+results[3]+"\n"+results[4]+"\n"+results[5]+"\n";

                @Override
                public void run() {

                    int k=0;
                    String printer="";
                    Scanner sc = new Scanner(stat);
                    while(sc.hasNext()){
                        try {
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        printer+=sc.nextLine();
                        if(nope[k]==1)
                            printer+="\n";
                        final String temp = printer;
                        hlthPredView.post(new Runnable() {
                            @Override
                            public void run() {
                                hlthPredView.setText(temp);
                            }
                        });
                    }
                }
            };

            new Thread(runnable).start();

        }

    }

}
