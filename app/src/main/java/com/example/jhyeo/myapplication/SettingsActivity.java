package com.example.jhyeo.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class SettingsActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Button button1 = (Button) findViewById(R.id.button);
        final EditText input1 = (EditText) findViewById(R.id.editText);

        button1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String addr = getString(R.string.api_url);
                Log.d("MYMSG",addr);
                ConnectThread thread = new ConnectThread("http://m.naver.com");
                thread.execute();
            }
        });
    }
}
