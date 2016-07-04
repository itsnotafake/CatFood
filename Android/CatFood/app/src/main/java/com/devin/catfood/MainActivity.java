package com.devin.catfood;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final CheckBox checkBox = (CheckBox) findViewById(R.id.checkBox);
        checkBox.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                checkBox.setEnabled(false);
                Log.w("dg", "checkBox pressed");
            }
        });

        final CheckBox checkBox2 = (CheckBox) findViewById(R.id.checkBox2);
        checkBox2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                checkBox2.setEnabled(false);
                Log.w("dg", "checkBox2 pressed");
            }
        });
    }
}
