package com.example.apartment_finder2;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class NewSubmission extends AppCompatActivity {
    private WebView myWebView;
    ProgressDialog mDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_submission);
        mDialog = new ProgressDialog(this);
        myWebView = (WebView)findViewById(R.id.webView);
        WebSettings webSettings = myWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        final String EimageURL=getIntent().getStringExtra("DrawerType");
        String ss=EimageURL;
        /*mDialog.setMessage("Creating User please wait...");
        mDialog.setCanceledOnTouchOutside(false);
        mDialog.show();*/
        if(ss.equalsIgnoreCase("Blog"))
        {
            myWebView.loadUrl("https://fihaspoint.com/news/");
        }
        else if(ss.equalsIgnoreCase("faq"))
        {
            myWebView.loadUrl("https://fihaspoint.com/faqs/");
        }

        //mDialog.dismiss();
        myWebView.setWebViewClient(new WebViewClient());
    }
    @Override
    public void onBackPressed() {
        if(myWebView.canGoBack()) {
            myWebView.goBack();
        } else {
            super.onBackPressed();
        }
    }

}
