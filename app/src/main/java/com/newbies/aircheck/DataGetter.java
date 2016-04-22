package com.newbies.aircheck;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;

public class DataGetter extends AppCompatActivity {

    String URL = "http://map2.vis.earthdata.nasa.gov/image-download?TIME=2016103&extent=167,42,168,43&epsg=4326&layers=AIRS_RelativeHumidity_400hPa_Day&opacities=1&worldfile=false&format=image/jpeg&width=48&height=64";
    ImageView image;
    Button button;
    ProgressDialog mProgressDialog;
    Bitmap picture;
    String location, name, curdate, curtime, country;
    int age;
    TextView textView;
    int humidity;
    int hum_r[]={0,1,2,4,6,8,10,12,13,15,17,19,21,24,25,26,28,30,32,35,36,37,39,41,43,46,47,48,50,52,54,57,59, 60, 61, 63, 65, 68, 70, 71,72,74,76,79,86,93,96,100,107,114,121,128,133,136,142,149,156,164,170,174,178,185,192,199,206,210,214,220,227,234,242,242,242,242,243,243,244,244,244,245,245,246,246,247,247,247,248,248,248,249,249,249,250,250,251,251,252,252,252,253};
    int hum_g[]={17,20,24,30,36,43,50,54,58,63,69,76,83,89,92,96,102,109,116,122,127,130,136,142,149,155,161,165,169,175,182,188,195,199,203,208,215,221,228,233,237,241,248,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,246,241,236,227,218,209,200,194,189,182,173,163,154,147,142,136,127,118,109,100,95,91,81,72,63,54,47,43,36};
    int hum_b[]={255,254,254,254,254,254,254,254,254,254,254,254,254,254,254,253,253,253,253,253,253,253,253,253,253,253,253,253,253,253,252,252,252,252,252,252,252,252,252,252,252,252,252,252,241,230,223,218,208,197,186,175,166,161,153,142,131,120,109,104,98,87,76,65,54,46,40,32,21,10,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Get the layout from image.xml
        setContentView(R.layout.activity_data_getter);

        picture= null;

        Bundle extras = getIntent().getExtras();
        name = extras.getString("name");
        age = extras.getInt("age");
        location = extras.getString("location");
        country = extras.getString("country");

        new DownloadImage().execute(URL);


    }

    // DownloadImage AsyncTask
    private class DownloadImage extends AsyncTask<String, Void, Bitmap> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Create a progressdialog
            Log.d("rifat","onPreExecute");
            mProgressDialog = new ProgressDialog(DataGetter.this);
            // Set progressdialog title
            mProgressDialog.setTitle("Downloading air data");
            // Set progressdialog message
            mProgressDialog.setMessage("Loading...");
            mProgressDialog.setIndeterminate(false);
            // Show progressdialog
            mProgressDialog.show();
        }

        @Override
        protected Bitmap doInBackground(String... URL) {
            Log.d("rifat", "doInBackground");
            String imageURL = URL[0];

            Bitmap bitmap = null;
            try {
                // Download Image from URL
                InputStream input = new java.net.URL(imageURL).openStream();
                // Decode Bitmap
                bitmap = BitmapFactory.decodeStream(input);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap result) {

            Log.d("rifat","onPostExecute");
            // Set the bitmap into ImageView
            //image.setImageBitmap(result);
            picture= result;

            setRGB();

            Log.d("rifat", "all done");


            // Close progressdialog
            mProgressDialog.dismiss();


            Intent intent= new Intent(DataGetter.this,UserSymptom.class);
            intent.putExtra("name",name);
            intent.putExtra("age",age);
            intent.putExtra("location", location);
            intent.putExtra("country", country);
            intent.putExtra("humidity", humidity);
            startActivity(intent);

        }

        void setRGB(){
            int colour = picture.getPixel(30, 38);

            int red = Color.red(colour);
            int blue = Color.blue(colour);
            int green = Color.green(colour);
            int alpha = Color.alpha(colour);

            //textView.setText(red+" "+green + " "+blue );

            checker(red,green,blue);
        }

        void checker(int x,int y,int z){
            int index=0;
            int sum= Math.abs(hum_r[0]-x)+Math.abs(hum_g[0]-y)+Math.abs(hum_b[0]-z);
            for(int i=1;i<100;i++){
                int sum1= Math.abs(hum_r[i]-x)+Math.abs(hum_g[i]-y)+Math.abs(hum_b[i]-z);
                if(sum1<sum)
                {
                    sum=sum1;
                    index=i;
                }

            }
            humidity=index;
            Log.d("rifat","humidity");
        }
    }
}
