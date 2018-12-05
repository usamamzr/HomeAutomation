package com.example.usama.homeautomation.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WebView myWebView = new WebView(getApplicationContext());
        setContentView(myWebView);

        MyWebChromeClient myWebChromeClient = new MyWebChromeClient();
        myWebView.setWebChromeClient(myWebChromeClient);//to not open browser

//        WebSettings webSettings = myWebView.getSettings();
//        webSettings.setJavaScriptEnabled(true);
        myWebView.getSettings().setPluginState(WebSettings.PluginState.ON);
        myWebView.getSettings().setJavaScriptEnabled(true);
        myWebView.getSettings().setDomStorageEnabled(true);
        myWebView.getSettings().setAllowFileAccess(true);
        myWebView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        myWebView.getSettings().setLoadsImagesAutomatically(true);
        myWebView.clearCache(true);


        myWebView.loadUrl("http://192.168.0.183/video.cgi");

        Intent intent = new Intent(this, FloorsActivity.class);
        startActivity(intent);
    }
    public class MyWebChromeClient extends WebChromeClient {
        @Override
        public boolean onJsAlert(WebView view, String url, String message, JsResult jsResult) {
            // you can create your own dialog here or just return true for no pop up.
            return super.onJsAlert(view, url, message, jsResult);
//            AlertDialog dialog = new AlertDialog.Builder(view.getContext()).
//                    setTitle("YourAlertTitle").
//                    setMessage(message).
//                    setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            //do nothing
//                        }
//                    }).create();
//            dialog.show();
//            jsResult.confirm();
//            return true;
        }
    }
}
