package com.voice.bridgejsjava;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class GameActivity extends AppCompatActivity implements AccelerometerSensor.AccelerometerSensorChangedListener {
    private WebView webView;
    public RelativeLayout rlGame;
    private AccelerometerSensor sensor;

    public float rotationOY;
    public float rotationOX;
    public float rotationOZ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        setContentView(R.layout.activity_game);

        rlGame = findViewById(R.id.rl_game);
        webView = findViewById(R.id.wv_game);
        webView.setWebChromeClient(new WebChromeClient() {});
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setDomStorageEnabled(true);
        webView.addJavascriptInterface(new WebViewJavascriptInterface(this), "JSHandler");
        webView.loadUrl("file:///android_asset/test.html");

        sensor = new AccelerometerSensor(this);
        sensor.setListener(this);

//        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN);
//        this.getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
//        getSupportActionBar().hide();
    }

    @Override
    public void onAccelerometerSensorChanged(float rotY, float rotX, float rotZ) {
        rotationOY = rotY;
        rotationOX = rotX;
        rotationOZ = rotZ;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        sensor.close();
        finish();
    }

    public class WebViewJavascriptInterface {

        private Context context;

        public WebViewJavascriptInterface(Context _context) {
            context = _context;
        }


        @JavascriptInterface
        public void receiveMessageFromJS(String message)
        {
            Log.e("JavascriptInterface", "receiving from html.." + message);
        }


        @JavascriptInterface
        public String getFromAndroid()
        {
            Log.e("JavascriptInterface", "getting from android..");
            return "from android";
        }

        @JavascriptInterface
        public String getScreenDimension()
        {
            return ((GameActivity)context).rlGame.getWidth() + "/" + ((GameActivity)context).rlGame.getHeight();
        }

        @JavascriptInterface
        public String getXYFromGyro()
        {
            return ((GameActivity) context).rotationOY + "/" + ((GameActivity)context).rotationOX + "/" + ((GameActivity)context).rotationOZ;
        }

    }
}



