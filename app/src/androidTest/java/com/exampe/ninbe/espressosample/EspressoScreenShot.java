package com.exampe.ninbe.espressosample;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Environment;
import android.util.DisplayMetrics;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class EspressoScreenShot {
    private String basePath;

    public EspressoScreenShot(Activity activity){
        basePath = Environment.getExternalStorageDirectory() + "/Pictures";

        File file = new File(basePath);
        if(!file.exists()) {
            file.mkdir();
        }
    }

    public File save(Activity activity, String fileName){
        return takeScreenShot(activity, basePath + "/" + fileName + ".png");
    }

    private File takeScreenShot(final Activity activity, String fileName){
        DisplayMetrics dm = activity.getResources().getDisplayMetrics();
        final Bitmap bitmap = Bitmap.createBitmap(
                dm.widthPixels, dm.heightPixels, Bitmap.Config.ARGB_8888);
        final File file = new File(fileName);

        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                OutputStream fos = null;
                try {
                    Canvas canvas = new Canvas(bitmap);
                    activity.getWindow().getDecorView().draw(canvas);

                    fos = new BufferedOutputStream(
                            new FileOutputStream(file));
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);

                    Runtime.getRuntime().exec(
                            new String[] {"chmod", "777", file.getAbsolutePath()});

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        fos.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        return file;
    }
}