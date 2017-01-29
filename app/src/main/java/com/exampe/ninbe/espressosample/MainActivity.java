package com.exampe.ninbe.espressosample;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        WebView webView = (WebView)findViewById(R.id.webview1);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl("http://www.google.co.jp");
        webView.getSettings().setJavaScriptEnabled(true);
        // google search box id lst-ib
        // input name btnK
    }

    public File save(String basePath, String fileName){
        return takeScreenShot(this, basePath + "/" + fileName + ".png");
    }

    private File takeScreenShot(final Activity activity, String fileName) {
        DisplayMetrics dm = activity.getResources().getDisplayMetrics();
        final Bitmap bitmap = Bitmap.createBitmap(
                dm.widthPixels, dm.heightPixels, Bitmap.Config.ARGB_8888);

        final File file = new File(fileName);

        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    Canvas canvas = new Canvas(bitmap);
                    activity.getWindow().getDecorView().draw(canvas);
                    OutputStream fos = new BufferedOutputStream(
                            new FileOutputStream(file));
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);

                    Runtime.getRuntime().exec(
                            new String[]{"chmod", "777", file.getAbsolutePath()});

                    fos.close();
                } catch (FileNotFoundException e) {
                    System.out.println("ZZZZ:not found " + e.toString());
                    e.printStackTrace();
                } catch (IOException e) {
                    System.out.println("ZZZZ:io " + e.toString());
                    e.printStackTrace();
                }
            }
        });
        return file;
    }
}
