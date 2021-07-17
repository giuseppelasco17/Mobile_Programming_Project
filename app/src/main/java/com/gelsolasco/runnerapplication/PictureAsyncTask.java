package com.gelsolasco.runnerapplication;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**Questa classe estende AsyncTask la quale scarica le immagini e le trasforma in Bitmap
 */


@SuppressLint("StaticFieldLeak")
public class PictureAsyncTask extends AsyncTask<URL, Void, Bitmap> {

    private ImageView imageView;
    InputStream in;

    PictureAsyncTask(ImageView imageView) {
        this.imageView = imageView;
    }

    @Override
    protected Bitmap doInBackground(URL... urls){
        Bitmap bitmap = null;
        try {
            // 1. Recupero e definisco l'url da richiamare
            HttpURLConnection conn = (HttpURLConnection) urls[0].openConnection();
            // 2. Apro la connessione
            conn.connect();
            in = conn.getInputStream();
            // 3. Download e decodifico l'immagine bitmap
            bitmap = BitmapFactory.decodeStream(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    /** Invoked by the Android on "doInBackground" is executed */

    @Override
    protected void onPostExecute(Bitmap result) {
        imageView.setImageBitmap(result);

    }  //onPosExecute
}//AsyncTask
