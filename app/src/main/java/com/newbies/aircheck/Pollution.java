package com.newbies.aircheck;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

import java.util.Scanner;

public class Pollution extends AppCompatActivity {
    static int symptomNumber=6;
    static int symptomGrade=4;
    static TextView polView;
    ProgressDialog pDialog;
    String servLoc;
    String location,userLocation;
    String[] sympVal={"no ","mild","moderate","severe"};
    //EditText locText;
    String[] results;
    int[] maxind;
    double[] eye,cough,sneeze,nasal,asthma,chest,maxVal;
    EditText locReqText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled( true );
        setTitle("Symptom Stats");
        setContentView(R.layout.activity_pollution);
        locReqText=(EditText)findViewById(R.id.locReqText);
        polView= (TextView) findViewById(R.id.polView);
        polView.setMovementMethod(new ScrollingMovementMethod());
        eye=new double[symptomGrade];
        cough=new double[symptomGrade];
        sneeze=new double[symptomGrade];
        nasal=new double[symptomGrade];
        asthma=new double[symptomGrade];
        chest=new double[symptomGrade];
        maxVal=new double[symptomNumber];
        maxind=new int[symptomNumber];
        results=new String[symptomNumber];
        Button locSubmitButton=(Button)findViewById(R.id.locSubmitButton);

        polView.setMovementMethod(new ScrollingMovementMethod());
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
    public void onBackPressed() {
        super.finish();
    }

    public void showMessage(String message) {
        //polView.setText(message);
    }

    public void locReq(View v)
    {
        InputMethodManager inputManager = (InputMethodManager)
                getSystemService(Context.INPUT_METHOD_SERVICE);

        inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS);
        location= locReqText.getText().toString();
        userLocation=location;
        servReq(location);
    }


    public void servReq(String location)
    {

        int length=location.length();
        if(location.charAt(length-1)==' ')
            location=location.substring(0,length-1);
        servLoc=location.toLowerCase();
        servLoc=servLoc.replace(' ','_');
        HttpSend httas = new HttpSend();
        httas.execute(servLoc);

    }

    private class HttpSend extends AsyncTask<String, Void, String>

    {
        @Override
        protected String doInBackground(String... str) {
            try {

                //String get_url = "http://113.11.61.167/appserver.php/receive?q=" + str[0].replace(" ", "%20");
                //String get_url = "http://113.11.61.167/";
                String get_url = "https://nasa-maliha-93.c9users.io/appquery.php/receive?q=" + str[0].replace(" ", "%20");
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
            pDialog=new ProgressDialog(Pollution.this);
            pDialog.setTitle("Retrieving data from server");
            pDialog.setMessage("Loading");
            pDialog.setIndeterminate(false);
            pDialog.show();
            parser(result);
        }

        void parser(String main)
        {
            if(main.equals("Cannot Connect")==true||main.equals("nodata")==true)
            {
                pDialog.dismiss();
                Toast.makeText(Pollution.this, "Sorry,Insufficient data to analyze", Toast.LENGTH_SHORT).show();
                return;

            }

            pDialog.dismiss();
            Scanner S=new Scanner(main);
            String city=S.next();
            double total=Integer.parseInt(S.next())*1.0;
            for(int i=0;i<symptomNumber;i++)
                maxVal[i]=0;
            for(int i=0;i<symptomGrade;i++)
            {
                eye[i] = (Integer.parseInt(S.next()) * 100.0) / total;
                if(eye[i]>=maxVal[0]) {
                    maxVal[0] = eye[i];
                    maxind[0]=i;
                }

            }
            for(int i=0;i<symptomGrade;i++)
            {
                cough[i] = (Integer.parseInt(S.next()) * 100.0) / total;
                if(cough[i]>=maxVal[1]) {
                    maxVal[1] = cough[i];
                    maxind[1]=i;
                }
            }
            for(int i=0;i<symptomGrade;i++)
            {
                sneeze[i] = (Integer.parseInt(S.next()) * 100.0) / total;
                if(sneeze[i]>=maxVal[2]) {
                    maxVal[2] = sneeze[i];
                    maxind[2]=i;
                }
            }
            for(int i=0;i<symptomGrade;i++)
            {
                nasal[i] = (Integer.parseInt(S.next()) * 100.0) / total;
                if(nasal[i]>=maxVal[3]) {
                    maxVal[3] = nasal[i];
                    maxind[3]=i;
                }
            }
            for(int i=0;i<symptomGrade;i++)
            {
                asthma[i]=(Integer.parseInt(S.next())*100.0)/total;
                if(asthma[i]>=maxVal[4]) {
                    maxVal[4] = asthma[i];
                    maxind[4]=i;
                }
            }
            for(int i=0;i<symptomGrade;i++)
            {
                chest[i]=(Integer.parseInt(S.next())*100.0)/total;
                if(chest[i]>=maxVal[5])
                {
                    maxVal[5] = chest[i];
                    maxind[5]=i;
                }
            }
            for(int i=0;i<symptomNumber;i++)
                results[i]="";
            results[0]+=checker(("About "+String.format( "%.2f", (eye[1]+eye[2]))+" % people experienced mild to moderate eye itchiness."),(eye[1]+eye[2]))+checker(("Only "+String.format( "%.2f", eye[3])+" % people reported severe Itchiness in the eye.\n"),eye[3]);
            results[1]+=checker(("Coughing is pre-dominantly "+sympVal[maxind[1]]+" here.About "+String.format( "%.2f", maxVal[1])+"% people suffered from "+ sympVal[maxind[1]]+" Coughing problem.\n"),maxind[1]);
            //sneeze
            if(sneeze[1]!=0.0 && sneeze[2]!=0.0)
                results[2]+="Sneezing ranged from "+String.format( "%.2f", sneeze[1])+"% to "+String.format( "%.2f", sneeze[2])+"% for mild to moderate symptoms, "+checker((String.format( "%.2f", sneeze[3])+" % reported severe sneezing.\n"),sneeze[3]);
            else
                results[2]+=checker(("Sneezing is pre-dominantly " + sympVal[maxind[2]]+" here."),maxind[2])+checker(("About "+String.format( "%.2f", maxVal[2])+"% people suffered from "+ sympVal[maxind[2]]+" Sneezing.\n"),maxind[2]);
            //nasal
            results[3]+=String.format( "%.2f", maxVal[3])+"% people suffered from "+sympVal[maxind[3]]+" Nasal Obstruction Problem.\n";
             //asthma
            results[4]=checker(("Asthema is pre-dominantly " + sympVal[maxind[4]]+" here."),maxind[4])+checker(("About "+String.format( "%.2f", maxVal[4])+"% people suffered from "+ sympVal[maxind[4]]+" Asthma.\n"),maxind[4]);
             //chest
            results[5]+=checker(("About "+String.format( "%.2f", (chest[1]+chest[2]))+" % people experienced mild to moderate Chest Pain."),(chest[1]+chest[2]));
            if(chest[3]==0.0)
                results[5]+="\n";
            else
                results[5]+=checker(("Only "+String.format( "%.2f", chest[3])+" % people reported severe pain in the chest.\n"),chest[3]);
            printer();
        }

        String checker(String printer,double x)
        {
            if(x==0)
                return "";
            else
                return printer;


        }

        void printer()
        {
            Runnable runnable = new Runnable() {
                String stat=results[0]+"\n"+results[1]+"\n"+results[2]+"\n"+results[3]+"\n"+results[4]+"\n"+results[5]+"\n";

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
                        polView.post(new Runnable() {
                            @Override
                            public void run() {
                                polView.setText(temp);
                            }
                        });
                    }
                }
            };

            new Thread(runnable).start();

        }

    }

}