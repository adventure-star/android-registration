package com.example.registration.activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.registration.R;
import com.example.registration.Constants;

public class SettingsActivity extends AppCompatActivity {

    EditText settingsinput;
    Button button;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        TextView toptext = (TextView) findViewById(R.id.toptext);
        toptext.setText(getResources().getString(R.string.settings));

        settingsinput = (EditText) findViewById(R.id.settinginput);
        button = (Button) findViewById(R.id.setting_button);

        settingsinput.setText(getDefaultURL(this, Constants.DEFAULT_URL));

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String newurl = settingsinput.getText().toString();

                if(newurl != "") {
                    setDefaultURL(SettingsActivity.this, newurl);
                    Toast.makeText(SettingsActivity.this, "Default URL Saved", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(SettingsActivity.this, "Please enter URL", Toast.LENGTH_LONG).show();
                }

            }
        });
    }

    private void setDefaultURL(Context context, String value) {
        try {
            SharedPreferences pref = context.getSharedPreferences(Constants.TAG, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = pref.edit();
            editor.putString(Constants.PREFERENCE_DEFAULT_URL, value);
            editor.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
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
