package edu.rosehulman.defaritl.weatherpics;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;

/**
 * Created by defaritl on 1/24/2016.
 */
public class BitmapWorkerTask extends AsyncTask<String, Void, Bitmap> {
    private final WeakReference<ImageView> imageViewReference;
    private String dataUrl;

    public BitmapWorkerTask(ImageView imageView){
        imageViewReference = new WeakReference<ImageView>(imageView);
    }

    @Override
    protected Bitmap doInBackground(String... params) {
        dataUrl = params[0];
        InputStream in = null;
        try {
            in = new java.net.URL(dataUrl).openStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Bitmap bitmap = BitmapFactory.decodeStream(in);
        return bitmap;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        super.onPostExecute(bitmap);
        if(imageViewReference != null && bitmap != null){
            final ImageView imageView = imageViewReference.get();
            if(imageView != null){
                imageView.setImageBitmap(bitmap);
            }
        }

    }
}
