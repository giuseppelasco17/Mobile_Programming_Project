package com.gelsolasco.runnerapplication;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

/**La seguente activity rappresenta il log in da parte dell'utente
 */
public class MainActivity extends AppCompatActivity {
    EditText username, password;
    Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        login = findViewById(R.id.log_in);
        login.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String usr = username.getText().toString();
                String pass = password.getText().toString();
                if(usr.length() >=4 && pass.length() >= 4) {
                    URL url = null;
                    try { //Preparo l'URL da cui scaricare l'oggetto JSON
                        url = new URL("https://my-json-server.typicode.com/Giu380/demo/athletes/?username=" + usr);
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    }
                    new jsonAsyncTask().execute(url);
                }else{
                    Toast.makeText(MainActivity.this,
                            "Username and password must contain at least four characters", Toast.LENGTH_LONG).show();
                    username.setText("");
                    password.setText("");
                }
            }

        });

    }
    /** AsyncTask to download json data */
    @SuppressLint("StaticFieldLeak")
    private class jsonAsyncTask extends AsyncTask <URL, Void, String> {
        String usr = username.getText().toString();
        String pass = password.getText().toString();
        String user, pas, name;
        private HttpURLConnection connection = null;
        private BufferedReader reader;

        @Override
        protected String doInBackground(URL... urls){
            /*Scarico con l'AsyncTask il JSON attraverso il quale controllo la correttezza di
             *username e password inseriti dall'utente
             */
            try {
                connection = (HttpURLConnection) urls[0].openConnection();
                connection.connect();
                InputStream stream = connection.getInputStream();
                reader = new BufferedReader(new InputStreamReader(stream));
                StringBuilder buffer = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null){
                    buffer.append(line);
                }
                String finalJson = buffer.toString();
                JSONArray athArray = new JSONArray(finalJson);
                JSONObject athlete = athArray.getJSONObject(0);
                user = athlete.getString("username");
                pas = athlete.getString("password");
                name = athlete.getString("Name");

            } catch (IOException e) {
                e.printStackTrace();
                return "CONN_FAIL";
            } catch (JSONException e) {
                e.printStackTrace();
                return "JSON_FAIL";
            }
            if(!usr.equals(user) || !pass.equals(pas)){
                return "LOGIN_FAIL";
            }else{
                return "LOGIN_SUCCESS";
            }
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        /** Invoked by the Android on "doInBackground" is executed */

        @Override
        protected void onPostExecute(String result) {
            //Gestisco le eventuali eccezioni lanciate nel doInBackground
            switch (result){
                case "CONN_FAIL":
                    Toast.makeText(MainActivity.this,
                            "Connection failure", Toast.LENGTH_LONG).show();
                    break;
                case "JSON_FAIL":
                    Toast.makeText(MainActivity.this,
                            "Json error", Toast.LENGTH_LONG).show();
                    username.setText("");
                    password.setText("");
                    break;
                case "LOGIN_FAIL":
                    Toast.makeText(MainActivity.this,
                            "Username or password invalid", Toast.LENGTH_LONG).show();
                    username.setText("");
                    password.setText("");
                    break;
                case "LOGIN_SUCCESS":
                    Toast.makeText(MainActivity.this,
                            "Login success", Toast.LENGTH_LONG).show();
                    Intent i=new Intent(MainActivity.this, MenuActivity.class);
                    i.putExtra("name", name);
                    startActivity(i); //In caso di successo passo alla MenuActivity
                    break;
            }
        }//onPosExecute
    }//AsyncTask
}
