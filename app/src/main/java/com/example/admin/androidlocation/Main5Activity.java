package com.example.admin.androidlocation;

import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class Main5Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main5);
        Typeface typeface1 = Typeface.createFromAsset(getAssets(), "fonts/quicksand/Quicksand-Regular.otf");
        TextView tv=(TextView) findViewById(R.id.textView4);
        tv.setTypeface(typeface1);
    }
}
