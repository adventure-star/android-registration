package com.example.registration.activities;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.registration.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class HelpActivity extends AppCompatActivity {

    ProgressDialog pd;

    TextView textview;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

        TextView toptext = (TextView) findViewById(R.id.toptext);
        toptext.setText(getResources().getString(R.string.help));

        textview = (TextView) findViewById(R.id.textview);

//        new JsonTask().execute("https://api.currencyquake.com/pairs/d1/");
//        new JsonTask().execute("http://10.97.5.35:3006/all");
        new JsonTask().execute("https://www.google.com");

    }

    private class JsonTask extends AsyncTask<String, String, String> {

        protected void onPreExecute() {
            super.onPreExecute();

            pd = new ProgressDialog(HelpActivity.this);
            pd.setMessage("Please wait");
            pd.setCancelable(false);
            pd.show();
        }

        protected String doInBackground(String... params) {

            if(params[0].split(":")[0] == "https") {
                HttpsURLConnection connection = null;
                BufferedReader reader = null;

                try {
                    URL url = new URL(params[0]);
                    connection = (HttpsURLConnection) url.openConnection();
                    connection.connect();

                    InputStream stream = connection.getInputStream();

                    reader = new BufferedReader(new InputStreamReader(stream));

                    StringBuffer buffer = new StringBuffer();
                    String line = "";

                    while ((line = reader.readLine()) != null) {
                        buffer.append(line+"\n");
                        Log.d("Response: ", "> " + line);   //here u ll get whole response...... :-)

                    }

                    return buffer.toString();

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (connection != null) {
                        connection.disconnect();
                    }
                    try {
                        if (reader != null) {
                            reader.close();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                return null;
            } else {

                HttpURLConnection connection = null;
                BufferedReader reader = null;

                try {
                    URL url = new URL(params[0]);
                    connection = (HttpURLConnection) url.openConnection();
                    connection.connect();

                    InputStream stream = connection.getInputStream();

                    reader = new BufferedReader(new InputStreamReader(stream));

                    StringBuffer buffer = new StringBuffer();
                    String line = "";

                    while ((line = reader.readLine()) != null) {
                        buffer.append(line+"\n");
                        Log.d("Response: ", "> " + line);   //here u ll get whole response...... :-)

                    }

                    return buffer.toString();

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (connection != null) {
                        connection.disconnect();
                    }
                    try {
                        if (reader != null) {
                            reader.close();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                return null;

            }

        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if (pd.isShowing()){
                pd.dismiss();
            }

            textview.setText(result);


        }
    }
}
