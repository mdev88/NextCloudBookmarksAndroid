package com.tttdevs.stncbookmarks;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    String URL = "https://tttdevs.com";
    String title = "";

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();
        String action = intent.getAction();
        String type = intent.getType();

        WebView webView = (WebView) findViewById(R.id.webview);
        webView.setWebViewClient(new webViewClient());
        webView.setWebChromeClient(new WebChromeClient() {
            public void onCloseWindow(WebView wv){
                super.onCloseWindow(wv);
                finish();
            }
        });

        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true); // Enable JavaScript

        SharedPreferences sharedPref = this.getSharedPreferences("com.tttdevs.ncbookmarks.config", Context.MODE_PRIVATE);
        String serverURL = sharedPref.getString("server_url", "");

        if (Intent.ACTION_SEND.equals(action) && type != null) {  // Opened via share menu
            if ("text/plain".equals(type)) {
                if (serverURL.equals("")) {
                    Toast.makeText(this, "Server URL not set", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(MainActivity.this, ConfigActivity.class));
                }

                String rcvTitle = intent.getStringExtra(Intent.EXTRA_TITLE);
                String rcvURL = intent.getStringExtra(Intent.EXTRA_TEXT);
                if (rcvURL != null) {
                    URL = rcvURL ;
                }
                if (rcvTitle != null) {
                    title = rcvTitle ;
                }
                webView.loadUrl("javascript:(function(){var%20a=window,b=document,c=encodeURIComponent,e=c('"+ title +"'),d=a.open('"+ serverURL +"/index.php/apps/bookmarks/bookmarklet?output=popup&url='+c('"+URL+"')+'&title='+e,'_self','left='+((a.screenX||a.screenLeft)+10)+',top='+((a.screenY||a.screenTop)+10)+',height=500px,width=650px,resizable=1,alwaysRaised=1');a.setTimeout(function(){d.focus()},300);})();");
            }
        }
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }

    private class webViewClient extends WebViewClient {
        @Override
        public void onPageFinished(WebView view, String url) {
            view.loadUrl("javascript: ( function() {" +
                    "    if(document.getElementsByClassName('submit')) {" +
                    "        document.getElementsByClassName('submit')[0].classList.remove('submit');" +
                    "    }"+
                    "})();");

            findViewById(R.id.imgNextCloud).setVisibility(View.GONE);
            findViewById(R.id.txtLoading).setVisibility(View.GONE);
            findViewById(R.id.txtTTT).setVisibility(View.GONE);

            findViewById(R.id.webview).setVisibility(View.VISIBLE);
        }
    }


//    public void evaluateJavascript(final String script) {
//        webView.post(new Runnable() {
//            @Override
//            public void run() {
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//                    webView.evaluateJavascript(script, null);
//                } else {
//                    webView.loadUrl("javascript:" + script);
//                }
//            }
//        });
//    }

}
