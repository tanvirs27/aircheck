package com.newbies.aircheck;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class mapper extends AppCompatActivity {

    WebView myWebView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapper);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled( true );
        setTitle(R.string.nearme);
        //getActionBar().setIcon(R.drawable.home);
        myWebView = (WebView) findViewById(R.id.webView);
        WebSettings webSettings = myWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        myWebView.setWebViewClient(new WebViewClient());

    //    myWebView.loadUrl("https://www.google.com.bd/maps/@23.7288312,90.3988565,20z?hl=bn");
        myWebView.loadUrl("http://aqicn.org/city/bangladesh/dhaka/us-consulate/");
        //myWebView.loadUrl("https://worldview.earthdata.nasa.gov/?p=geographic&l=VIIRS_SNPP_CorrectedReflectance_TrueColor(hidden),MODIS_Aqua_CorrectedReflectance_TrueColor(hidden),MODIS_Terra_CorrectedReflectance_TrueColor,AIRS_Prata_SO2_Index_Night,AIRS_Prata_SO2_Index_Day,AIRS_CO_Total_Column_Night,AIRS_CO_Total_Column_Day,MODIS_Terra_Aerosol,Reference_Labels(hidden),Reference_Features(hidden),Coastlines&t=2016-03-27&v=-432.9905721991639,-177.9888915296982,335.3844278008361,197.1986084703018");
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // Check if the key event was the Back button and if there's history
        if ((keyCode == KeyEvent.KEYCODE_BACK) && myWebView.canGoBack()) {
            myWebView.goBack();
            return true;
        }
        // If it wasn't the Back key or there's no web page history, bubble up to the default
        // system behavior (probably exit the activity)
        return super.onKeyDown(keyCode, event);
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
