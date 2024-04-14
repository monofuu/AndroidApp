package com.example.clicker;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Button;
import android.content.Intent;
import android.webkit.WebView;


public class MainActivity extends AppCompatActivity {
    private TextView textView;
    private WebView webView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        webView = (WebView) findViewById(R.id.webView);
        textView = (TextView) findViewById(R.id.textView);
        textView.setText("Question is in progress...");

        public void btnHandler(View view) {
            int id = view.getId();
            if (id == R.id.buttonA) {
                webView.loadUrl(http://ip_addr:port/clicker/select?choice=a);
            }
            else if (id == R.id.buttonB) {
                webView.loadUrl(http://ip_addr:port/clicker/select?choice=b);
            }
            else if (id == R.id.buttonC) {
                webView.loadUrl(http://ip_addr:port/clicker/select?choice=c);
            }
            else if (id == R.id.buttonD) {
                webView.loadUrl(http://ip_addr:port/clicker/select?choice=d);
            }
        }
    }
}