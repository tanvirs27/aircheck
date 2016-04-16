package com.newbies.aircheck;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

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
    String location,name;
    int age;
    int val[];
    int selectedId[];
    Button symSubmitButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_symptom);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Bundle extras = getIntent().getExtras();
        name = extras.getString("name");
        age =extras.getInt("age");
        location=extras.getString("location");
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
        String out="Name="+name+"\nAge ="+age+"\nLocation ="+location+"\nSymptom"+val[0]+" "+val[1]+" "+val[2]+" "+val[3];
        Toast.makeText(UserSymptom.this, out,Toast.LENGTH_SHORT).show();
        finish();
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

}
