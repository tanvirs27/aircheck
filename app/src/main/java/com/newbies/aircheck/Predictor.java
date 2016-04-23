package com.newbies.aircheck;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
    ProgressDialog pDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_predictor);
        hlthPredView= (TextView) findViewById(R.id.hlthPredView);
        humidityView=(TextView)findViewById(R.id.humidityView);
        Bundle extras = getIntent().getExtras();
        humidity=extras.getInt("humidity");
        location=extras.getString("location");
        sendData=Integer.toString(humidity);
        val=new String[symptomNumber];
        results=new String[symptomNumber];
        HttpSend httas = new HttpSend();
        httas.execute(sendData);

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
            results[0]="Itchy Eye : "+ val[0];
            results[1]="Cough : "+ val[1];
            results[2]="Sneezing : "+ val[2];
            results[3]="Nasal Obstruction : "+ val[3];
            results[4]="Asthema : "+val[4];
            results[5]="Chest Pain : "+val[5];
            printer();
        }

        void printer()
        {
            Runnable runnable = new Runnable() {
                String stat="According to Previous user data ,you are in the health risk of the being affected by the following symptoms "+" "+results[0]+"\n"+results[1]+"\n"+results[2]+"\n"+results[3]+"\n"+results[4]+"\n"+results[5]+"\n";

                @Override
                public void run() {

                    String printer="";
                    Scanner sc = new Scanner(stat);
                    while(sc.hasNext()){
                        try {
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        printer+=sc.nextLine()+"\n";
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
