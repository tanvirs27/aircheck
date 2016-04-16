package com.newbies.aircheck;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    public void nearMe(View v)
    {
        Intent intent=new Intent(MainActivity.this,mapper.class);
        startActivity(intent);
    }

    public void credits(View v)
    {
        Intent intent=new Intent(MainActivity.this,Credits.class);
        startActivity(intent);
    }


    public void pollutionStats(View v)
    {
        Intent intent=new Intent(MainActivity.this,Pollution.class);
        startActivity(intent);
    }

    public void symptoms(View v) {
        Intent intent=new Intent(MainActivity.this,UserInput.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);


        return true;
    }

    @Override
    public void onBackPressed() {

        //exitPrompt();
        finish();
    }

    public void exitPrompt()
    {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        builder1.setMessage("Do you really want to leave us ?");
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        finish();
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
