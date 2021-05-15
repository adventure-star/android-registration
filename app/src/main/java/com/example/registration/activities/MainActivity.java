package com.example.registration.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.registration.Constants;
import com.example.registration.R;

public class MainActivity extends AppCompatActivity {

    EditText ip;
    Button button;

    Boolean connected = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = (Button)findViewById(R.id.receivebutton);

        ip = (EditText)findViewById(R.id.ipinput);

        ip.setText(getDefaultURL(MainActivity.this, Constants.DEFAULT_URL));

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                connected = checkInternetConenction();

                if(connected) {
                    Intent intent = new Intent(MainActivity.this, ListActivity.class);
                    intent.putExtra("ip", ip.getText().toString());
                    startActivity(intent);
                    overridePendingTransition(R.anim.left_to_right_enter, R.anim.left_to_right_enter);
                    finish();
                } else {
                    AlertDialog dialog = new AlertDialog.Builder(MainActivity.this).setTitle("Registration")
                            .setMessage("Please check network state!")
                            .setNegativeButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            })
                            .setPositiveButton("Open Settings", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    startActivityForResult(new Intent(android.provider.Settings.ACTION_SETTINGS), 0);
                                }
                            }).create();
                    dialog.show();
                    dialog.setCanceledOnTouchOutside(true);
                }

            }
        });

        connected = checkInternetConenction();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.settings:
                startActivity(new Intent(MainActivity.this, SettingsActivity.class));
                break;

            case R.id.help:
                startActivity(new Intent(MainActivity.this, HelpActivity.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private boolean checkInternetConenction() {
        // get Connectivity Manager object to check connection
        ConnectivityManager connec
                =(ConnectivityManager)getSystemService(getBaseContext().CONNECTIVITY_SERVICE);

        // Check for network connections
        if ( connec.getNetworkInfo(0).getState() ==
                android.net.NetworkInfo.State.CONNECTED ||
                connec.getNetworkInfo(0).getState() ==
                        android.net.NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo(1).getState() ==
                        android.net.NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTED ) {
            Toast.makeText(this, "Network Connected", Toast.LENGTH_SHORT).show();
            return true;
        }else if (
                connec.getNetworkInfo(0).getState() ==
                        android.net.NetworkInfo.State.DISCONNECTED ||
                        connec.getNetworkInfo(1).getState() ==
                                android.net.NetworkInfo.State.DISCONNECTED  ) {
            Toast.makeText(this, "Network Not Connected ", Toast.LENGTH_LONG).show();
            return false;
        }
        return false;
    }

    private String getDefaultURL(Context context, String defaultValue) {
        try {
            SharedPreferences pref = context.getSharedPreferences(Constants.TAG, Context.MODE_PRIVATE);
            return pref.getString(Constants.PREFERENCE_DEFAULT_URL, defaultValue);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return defaultValue;
    }

}
