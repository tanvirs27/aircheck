package com.newbies.aircheck;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.Scanner;

public class Pollution extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_pollution);
        final TextView polView = (TextView) findViewById(R.id.polView);
        polView.setMovementMethod(new ScrollingMovementMethod());

        Runnable runnable = new Runnable() {
            String pol=
                    "Concentration of Sulphur Di Oxide in Dhaka has been recorded at 365 microgram per cubic meter of air.23% people in Dhaka suffer from mild Nasal Obstruction.\n" +
                    "\n\nConcentration of Ozone in Dhaka has been recorded at 235 microgram per cubic meter of air.37% people in Dhaka reported mild to moderate eye irritation.\n" +
                    "\n\nConcentration of Nitrogen Di Oxide in Dhaka has been recorded at 100 microgram per cubic meter of air.11% people in Dhaka suffer from severe Nasal Obstruction .\n" ;
            @Override
            public void run() {

                String printer="";
                Scanner sc = new Scanner(pol);
                while(sc.hasNext()){
                    try {
                        Thread.sleep(800);
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

}
