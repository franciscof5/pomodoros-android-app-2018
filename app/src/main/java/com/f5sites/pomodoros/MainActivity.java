package com.f5sites.pomodoros;

import android.os.Build;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Message;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.webkit.WebChromeClient;
import android.os.Bundle;
import android.view.KeyEvent;
import android.webkit.ConsoleMessage;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.content.Context;
import android.widget.Toast;
import android.view.ContextThemeWrapper;
import android.app.AlertDialog;
import android.webkit.WebChromeClient;

public class MainActivity extends Activity {

    private WebView webView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        WebView webView = (WebView) findViewById(R.id.webview);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WebView.setWebContentsDebuggingEnabled(true);
        }

        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        webView.setWebChromeClient(new WebChromeClient());

        CustomWebViewClient c = new CustomWebViewClient();
        webView.setWebViewClient(c);
        //webView.setWebViewClient(new WebViewClient());


        //webView.getSettings().setPluginsEnabled(true);
        //ajax
        //webView.getSettings().setAllowUniversalAccessFromFileURLs(true);
        //WebViewClientImpl webViewClient = new WebViewClientImpl(this);

        //webView.setWebViewClient(webViewClient);

        /*webView.setWebChromeClient(new WebChromeClient() {
            public boolean onConsoleMessage(ConsoleMessage cm) {
                Log.d("MyApplication", cm.message() + " -- From line "
                        + cm.lineNumber() + " of "
                        + cm.sourceId() );
                return true;
            }
        });*/

        //webView.getSettings().setAppCacheEnabled(false);
        //webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        /*webView.getSettings().setAppCacheMaxSize( 5 * 1024 * 1024 ); // 5MB
        webView.getSettings().setAppCachePath( getApplicationContext().getCacheDir().getAbsolutePath() );
        webView.getSettings().setAllowFileAccess( true );
        webView.getSettings().setAppCacheEnabled( true );

        if ( !isNetworkAvailable() ) { // loading offline
            webView.getSettings().setCacheMode( WebSettings.LOAD_CACHE_ELSE_NETWORK );
        }*/

        //Foridentifie webview in PHP  (api level 9 )
        String agentModified = webView.getSettings().getUserAgentString().concat(" MobileApplication(com.f5sites.pomodoros)");
        webView.getSettings().setUserAgentString(agentModified);

        if(isNetworkStatusAvialable (getApplicationContext())) {
            Toast.makeText(getApplicationContext(), "internet avialable", Toast.LENGTH_SHORT).show();
            webView.loadUrl("https://www.pomodoros.com.br/app");
        } else {
            Toast.makeText(getApplicationContext(), "internet is not avialable", Toast.LENGTH_SHORT).show();


            webView.loadUrl("file:///android_asset/pomodoros_offline.html" );

        }

    }

    // Function to load all URLs in same webview
    private class CustomWebViewClient extends WebViewClient {
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if (!DetectConnection.checkInternetConnection(getApplicationContext())) {
                Toast.makeText(getApplicationContext(), "No Internet!", Toast.LENGTH_SHORT).show();
                view.loadUrl("file:///android_asset/pomodoros_offline.html" );
            } else {
                view.loadUrl(url);
            }
            return true;
        }
    }
   /* private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService( CONNECTIVITY_SERVICE );
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }*/
   public static boolean isNetworkStatusAvialable (Context context) {
       ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
       if (connectivityManager != null)
       {
           NetworkInfo netInfos = connectivityManager.getActiveNetworkInfo();
           if(netInfos != null)
               if(netInfos.isConnected())
                   return true;
       }
       return false;
   }
/*
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && this.webView.canGoBack()) {
            this.webView.goBack();
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

    public boolean isConnectingToInternet() {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        @SuppressWarnings("deprecation")
        NetworkInfo wifiNetwork = cm
                .getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (wifiNetwork != null && wifiNetwork.isConnected()) {
            return true;
        }

        @SuppressWarnings("deprecation")
        NetworkInfo mobileNetwork = cm
                .getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if (mobileNetwork != null && mobileNetwork.isConnected()) {
            return true;
        }

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (activeNetwork != null && activeNetwork.isConnected()) {
            return true;
        }

        return false;

    }

    public void showAlert(String msg) {

        ContextThemeWrapper themedContext;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            themedContext = new ContextThemeWrapper(context,android.R.style.Theme_Holo_Light_Dialog_NoActionBar);
        } else {
            themedContext = new ContextThemeWrapper(
                    context,
                    android.R.style.Theme_Light_NoTitleBar);
        }

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                themedContext);

        alertDialogBuilder.setTitle(context.getResources().getString(R.string.app_name));
        alertDialogBuilder.setCancelable(false);
        alertDialogBuilder.setMessage(msg).setPositiveButton("Reload",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //Call webview again.
                        dialog.cancel();

                    }
                });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();

    }*/
}
/*
private class MyWebViewClient extends WebViewClient {
    @Override
    public boolean shouldOverrideUrlLoading(WebView webView, String url) {
        if(url.indexOf("pomodoros.com.br") > -1 ) return false;
        return true;
        //return false;
    }
}*/

/*
import android.annotation.SuppressLint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;

public class MainActivity extends AppCompatActivity {

    //private WebView mWebView;
    //@SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //setContentView(R.layout.activity_main_webview);

        //WebView myWebView = (WebView) findViewById(R.id.activity_main_webview);

        //WebView myWebView = new WebView(activityContext);
        //setContentView(myWebView);

        ///myWebView.loadUrl("http://www.f5sites.com");
        //mWebView = (WebView) findViewById(R.id.activity_main_webview);
        //WebSettings webSettings = mWebView.getSettings();
        //webSettings.setJavaScriptEnabled(true);
        //mWebView.loadUrl("http://beta.html5test.com/");
        //mWebView.setWebViewClient(new WebViewClient());
    }
}
*/