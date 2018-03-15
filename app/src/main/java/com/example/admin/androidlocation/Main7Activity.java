package com.example.admin.androidlocation;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Main7Activity extends AppCompatActivity implements recad.ItemClickListener{

    String x;
    ArrayList<String> a=new ArrayList<String>();
    DatabaseHandler db=new DatabaseHandler(this);
    offerdata db1=new offerdata(this);
    String lat ,lon;
    recad r;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main7);
        TextView tv=(TextView) findViewById(R.id.textView5);
        ListView lv=(ListView) findViewById(R.id.lv2);
        x=getIntent().getStringExtra("sp");
        //Toast.makeText(this,x+ "", Toast.LENGTH_SHORT).show();

        int j=0;
       //a.add(" LOCATION   :-) ");
        Cursor cursor=db.getAllData();
        String y,z;
        if(cursor!=null){
            if(cursor.moveToFirst())
            do{
                y=cursor.getString(cursor.getColumnIndex("SHOPNAME"));
                if(y!=null)
                if(y.equals(x)) {
                    //a.add(cursor.getString(cursor.getColumnIndex("LOCATION")));
                    tv.setText(cursor.getString(cursor.getColumnIndex("LOCATION")));
                    lat=cursor.getString(cursor.getColumnIndex("LATITUDE"));
                    lon=cursor.getString(cursor.getColumnIndex("LONGITUDE"));

                }


            }while(cursor.moveToNext());

        }
        cursor=db1.getAllData();
        if(cursor!=null){
            if(cursor.moveToFirst())
            do{
                z=cursor.getString(cursor.getColumnIndex("SHOPNAME"));

                //Toast.makeText(this, z+"", Toast.LENGTH_SHORT).show();
                if(z!=null)
                if(z.equals(x)) {
                    a.add(cursor.getString(cursor.getColumnIndex("OFFERS")));

                }

            }while(cursor.moveToNext());

        }
        //ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,a);
       // lv.setAdapter(adapter);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerviewn);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        r = new recad(this, a);
        r.setClickListener(this);
        recyclerView.setAdapter(r);
    }

    public void showmap(View v){
        Intent i = new
                Intent(android.content.Intent.ACTION_VIEW,
                Uri.parse("geo:" +lat+"," +lon));
        startActivity(i);
    }

    public void onItemClick(View view, int position) {
       }
}
