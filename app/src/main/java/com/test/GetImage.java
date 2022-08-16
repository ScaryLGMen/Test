package com.test;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;


public class GetImage extends AsyncTask<String, Void, Bitmap> {

    private ImageView imageView;
    private String link;

    public GetImage(ImageView imageView, String link) {
        this.imageView = imageView;
        this.link = link;

    }

    @Override
    protected Bitmap doInBackground(String... params) {
        try {
            URL url = null;
            url = new URL(link);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap bitmap = BitmapFactory.decodeStream(input);

            return bitmap;
        }catch (Exception e){
            Log.e("GetImage",e.toString());
        }
        return null;
    }

    @Override
    protected void onPostExecute(Bitmap result) {
        imageView.setImageBitmap(result);
    }

}