package com.example.registration.activities;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.registration.models.Insurer;
import com.example.registration.models.Item;
import com.example.registration.utils.ListAdapter;
import com.example.registration.R;
import com.example.registration.models.Registration;
import com.example.registration.models.Vehicle;

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
import java.util.ArrayList;

public class ListActivity extends AppCompatActivity {

    public int DATA_RECEIVED = 200;

    ProgressDialog pd;
    ListView listview;

    public void onBackPressed() {
        AlertDialog dialog = new AlertDialog.Builder(this).setTitle("Registration")
                .setMessage("Do you want to really exit?")
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                }).create();
        dialog.show();
        dialog.setCanceledOnTouchOutside(true);

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        Intent intent = getIntent();

        new JsonTask().execute(intent.getStringExtra("ip"));

        listview = (ListView) findViewById(R.id.listview);

    }

    protected Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if(msg.what == DATA_RECEIVED) {

                try {
                    JSONObject serverResp = new JSONObject(msg.obj.toString());

                    JSONArray value = serverResp.getJSONArray("registrations");

                    ArrayList<Item> list = new ArrayList<>();

                    int index;

                    for (index = 0 ; index < value.length() ; index ++) {

                        JSONObject mixed = (JSONObject) value.get(index);

                        String plate_number = (String) mixed.get("plate_number");

                        JSONObject registration = (JSONObject) mixed.get("registration");
                        JSONObject vehicle = (JSONObject) mixed.get("vehicle");
                        JSONObject insurer = (JSONObject) mixed.get("insurer");

                        Boolean expired = (Boolean) registration.get("expired");
                        String expiry_date = (String) registration.get("expiry_date");

                        Registration newregistration = new Registration(expired, expiry_date);

                        String type = (String) vehicle.get("type");
                        String make = (String) vehicle.get("make");
                        String model = (String) vehicle.get("model");
                        String colour = (String) vehicle.get("colour");
                        String vin = (String) vehicle.get("vin");
                        Integer tare_weight = (Integer) vehicle.get("tare_weight");
                        Integer gross_mass = vehicle.get("gross_mass") instanceof Integer ? (Integer)vehicle.get("gross_mass") : 0;

                        Vehicle newvehicle = new Vehicle(type, make, model, colour, vin, tare_weight, gross_mass);

                        Integer code = (Integer) insurer.get("code");
                        String name = (String) insurer.get("name");

                        Insurer newinsurer = new Insurer(code, name);

                        Item item = new Item(plate_number, newregistration, newvehicle, newinsurer);

                        list.add(item);
                    }

                    ListAdapter customAdapter = new ListAdapter(ListActivity.this, R.layout.list_item, list);
                    listview.setAdapter(customAdapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }
    };

    private class JsonTask extends AsyncTask<String, String, String> {

        protected void onPreExecute() {
            super.onPreExecute();

            pd = new ProgressDialog(ListActivity.this);
            pd.setMessage("Please wait");
            pd.setCancelable(false);
            pd.show();
        }

        protected String doInBackground(String... params) {

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

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if (pd.isShowing()){
                pd.dismiss();
            }
            if(result == null){
                Toast.makeText(ListActivity.this, "API failed", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(ListActivity.this, "Data Recevied from API", Toast.LENGTH_LONG).show();

                Message msg = new Message();
                msg.what=DATA_RECEIVED;
                msg.obj = result;

                handler.sendMessage(msg);
            }

        }
    }
}
