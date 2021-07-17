package com.gelsolasco.runnerapplication;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

/**La seguente activity permette la visualizzazione dellea lista di atleti
 */

public class ListActivity extends AppCompatActivity {

    private ListView ath_lv;
    private EditText editsearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        ath_lv = findViewById(R.id.ath_lv);
        editsearch = findViewById(R.id.e_search);
        URL url = null;
        //Preparazione URL
        try {
            url = new URL("https://my-json-server.typicode.com/Giu380/demo/athletes/");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        new jsonAsyncTask().execute(url);
        // Capture Text in EditText

    }

    /**
     * AsyncTask per il download dei dati json
     */
    @SuppressLint("StaticFieldLeak")
    private class jsonAsyncTask extends AsyncTask<URL, Void, List<Athlete>> {

        private HttpURLConnection connection = null;
        private BufferedReader reader;

        @Override
        protected List<Athlete> doInBackground(URL... urls) {
            List<Athlete> list = new LinkedList<>();
            //Connessione al sito e download dei dati
            try {
                connection = (HttpURLConnection) urls[0].openConnection();
                connection.connect();
                InputStream stream = connection.getInputStream();
                reader = new BufferedReader(new InputStreamReader(stream));
                StringBuilder buffer = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    buffer.append(line);
                }
                String finalJson = buffer.toString();
                //Recupero le informazioni dal JSON e le mappo in una lista di oggetti Athlete
                JSONArray athArray = new JSONArray(finalJson);
                for(int i = 0; i < athArray.length(); i++){
                    JSONObject jsonObject;
                    jsonObject = athArray.getJSONObject(i);
                    Athlete athlete = new Athlete(jsonObject.getInt("idathlete"),
                            jsonObject.getString("Name"), jsonObject.getString("Birth"),
                            jsonObject.getString("Sex"), jsonObject.getInt("CardNo"),
                            jsonObject.getString("note"),jsonObject.getString("nationality"),
                            jsonObject.getString("expcard"), jsonObject.getString("expcert"),
                            jsonObject.getString("username"), jsonObject.getString("password"),
                            jsonObject.getString("picture"),jsonObject.getString("email"));
                    list.add(athlete);
                }
                return list;
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(ListActivity.this,
                        "CONN_FAIL", Toast.LENGTH_LONG).show();
            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(ListActivity.this,
                        "JSON_FAIL", Toast.LENGTH_LONG).show();
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }


        @Override
        protected void onPostExecute(List result) {
            //Chiamo l'adapter per il riempimento della lista
            final CustomAdapter adapter = new CustomAdapter(ListActivity.this, result);
            ath_lv.setAdapter(adapter);
            editsearch.addTextChangedListener(new TextWatcher() {
                //Implemento gli handler per la ricerca sulla lista tramite il filtro dell'adapter
                @Override
                public void afterTextChanged(Editable arg0) {
                    String text = editsearch.getText().toString().toLowerCase(Locale.getDefault());
                    adapter.filter(text);
                }

                @Override
                public void beforeTextChanged(CharSequence arg0, int arg1,
                                              int arg2, int arg3) {
                }

                @Override
                public void onTextChanged(CharSequence arg0, int arg1, int arg2,
                                          int arg3) {
                }
            });
        }//onPosExecute
    }//AsyncTask
}

