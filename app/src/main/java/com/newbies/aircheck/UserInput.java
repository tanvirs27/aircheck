package com.newbies.aircheck;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class UserInput extends AppCompatActivity {
    String location,name;
    int age;
    EditText nameText,ageText,locationText;
    Button idSubmitButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_user_input);
        nameText=(EditText)findViewById(R.id.nameText);
        ageText=(EditText)findViewById(R.id.ageText);
        locationText=(EditText)findViewById(R.id.locationText);
        idSubmitButton=(Button)findViewById(R.id.idSubmitButton);
    }

    public void nameClicker(View V)
    {
        int check=3;
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
        Intent intent=new Intent(UserInput.this,UserSymptom.class);
        intent.putExtra("name",name);
        intent.putExtra("age",age);
        intent.putExtra("Location",location);
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
                        Intent intent=new Intent(UserInput.this,UserSymptom.class);
                        intent.putExtra("name",name);
                        intent.putExtra("age",age);
                        intent.putExtra("Location",location);
                        startActivity(intent);
                        finish();
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();

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
