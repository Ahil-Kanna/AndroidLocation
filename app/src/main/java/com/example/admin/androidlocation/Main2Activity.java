package com.example.admin.androidlocation;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Main2Activity extends AppCompatActivity {
Button button,button1;
    boolean doubleBackToExitPressedOnce = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        button =(Button) findViewById(R.id.button2);
        button1 =(Button) findViewById(R.id.button3);

        TextView myTextView = (TextView) findViewById(R.id.textView3);
        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/CaviarDreams.ttf");
        myTextView.setTypeface(typeface);
        button.setTypeface(typeface);

        button1.setTypeface(typeface);
    }
    public void were(View view)
    {
        Intent i=new Intent(this,Main6Activity.class);
        startActivity(i);
    }
    public void were1(View view)
    {
        Intent intent=new Intent(this,LoginActivity.class);
        intent.putExtra("value",button.getText().toString());
        startActivity(intent);
    }
    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            Intent intent = new Intent(getApplicationContext(), haha.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtra("EXIT", true);
            startActivity(intent);
            return;

        }
        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Press BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }

    public void credits(View v){
        Intent i=new Intent(this,Main5Activity.class);
        startActivity(i);
    }


}
