package com.newbies.aircheck;

import android.content.DialogInterface;
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

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class UserSymptom extends AppCompatActivity {

    final int symptomNumber=4;
    final int symptomGrade=4;
    final int eye=0;
    final int cough=1;
    final int sneeze=2;
    final int nasal=3;
    String Symptom[]={"None","Mild","Moderate","Severe"};
    RadioGroup[] symGroup;
    RadioButton[][] symBtn;
    RadioButton[] valBtn;
    Random r;
    String location,name,curdate,curtime,country;
    int age;
    int val[];
    int selectedId[];
    Button symSubmitButton;
    database db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        r=new Random();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_symptom);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled( true );
        setTitle(R.string.usersymptom);
        //getActionBar().setIcon(R.drawable.ic_action_user_input);
        db = new database(this);
        db.getWritableDatabase();
        Bundle extras = getIntent().getExtras();
        name = extras.getString("name");
        age =extras.getInt("age");
        location=extras.getString("location");
        country=extras.getString("country");
        val=new int[symptomNumber];
        selectedId=new int[symptomNumber];
        valBtn=new RadioButton[symptomNumber];
        symGroup=new RadioGroup[symptomNumber+2];
        symBtn=new RadioButton[symptomNumber+2][symptomGrade+2];
        radioInit();
        symSubmitButton=(Button)findViewById(R.id.symSubmitButton);
    }

    public void symClicker(View v) {
        for(int i=0;i<symptomNumber;i++) {
            if (symGroup[i].getCheckedRadioButtonId() == -1)
            {
                Toast.makeText(UserSymptom.this, "Please fill up all the fields",Toast.LENGTH_SHORT).show();
                return;
            }
            selectedId[i] = symGroup[i].getCheckedRadioButtonId();
            Log.i("Fahim",symBtn[i][0]+" "+selectedId[i]);
            valBtn[i] = (RadioButton) findViewById(selectedId[i]);
            for(int j=0;j<symptomGrade;j++)
            {
                if(Symptom[j].equals((valBtn[i].getText()))==true)
                    val[i]=j;
            }
        }
        createOnClick();
       // finish();
        String out="Name="+name+"\nAge ="+age+"\nLocation ="+location+"\nCountry"+country+"\nSymptom"+val[0]+" "+val[1]+" "+val[2]+" "+val[3];
        //Toast.makeText(UserSymptom.this, out,Toast.LENGTH_SHORT).show();
        onclickshow();
    }

    protected void radioInit()
    {
        symGroup[0]=(RadioGroup)findViewById(R.id.eyeGroup);
        symGroup[1]=(RadioGroup)findViewById(R.id.coughGroup);
        symGroup[2]=(RadioGroup)findViewById(R.id.sneezeGroup);
        symGroup[3]=(RadioGroup)findViewById(R.id.nasalGroup);
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

    public void createOnClick() {

        SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat time = new SimpleDateFormat("HH:MM:SS aa");
        curdate = date.format(new Date());
        curtime = time.format(new Date());
        double air_quality=r.nextDouble()*100;
        double ash_plumes=r.nextDouble()*70;
        double smoke_plumes=r.nextDouble()*20;
        double relative_humidity=r.nextDouble()*400;

        boolean res = db.insertData(curdate, curtime,
                age,location,country,val[0],val[1],val[2],val[3],10,20,30,40);
        if (res == true)
            Toast.makeText(UserSymptom.this, "Data Inserted", Toast.LENGTH_LONG).show();
        else
            Toast.makeText(UserSymptom.this, "Data Insertion Failed", Toast.LENGTH_LONG).show();


    }

    public void onclickshow() {
        Cursor cursor = db.getData();
        if (cursor.getCount() == 0) {
            showMessage("Error", "Data not found");
            return;
        }

        StringBuffer stringBuffer = new StringBuffer();
        while (cursor.moveToNext()) {
            stringBuffer.append("TIME: "+ cursor.getString(1)+"\n"+"DATE: "+cursor.getString(2) + "\n\n");
            stringBuffer.append("Age :" + cursor.getString(3) + "\n");
            stringBuffer.append("Location :" + cursor.getString(4) + "\n");
            stringBuffer.append("Country :" + cursor.getString(5) +"\n");
            stringBuffer.append("Itchy eye :" + cursor.getString(6) + "\n");
            stringBuffer.append("Cough :" + cursor.getString(7) + "\n");
            stringBuffer.append("Sneeze :" + cursor.getString(8) + "\n");
            stringBuffer.append("Nasal obstruction :" + cursor.getString(9) + "\n");
            stringBuffer.append("Air quality :" + cursor.getString(10) + "\n");
            stringBuffer.append("Ash plumes  :" + cursor.getString(11) + "\n");
            stringBuffer.append("Smoke plumes :" + cursor.getString(12) + "\n");
            stringBuffer.append("Relative humidity :" + cursor.getString(13) + "\n");
            stringBuffer.append("\n");
        }
        showMessage("Your Personal Records :", stringBuffer.toString());
        cursor.close();
        db.close();
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


}
