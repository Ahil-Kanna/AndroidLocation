package com.example.admin.androidlocation;

import android.Manifest;
import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Main6Activity extends AppCompatActivity implements LocationListener, recad.ItemClickListener{

    recad r;
    int d123=0;
    public double  d;
    ArrayList <String> a=new ArrayList<String>();
    DatabaseHandler db=new DatabaseHandler(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main6);
        d123=getIntent().getIntExtra("dist",1);

        //Toast.makeText(this, ""+d123, Toast.LENGTH_SHORT).show();
        if(d123==0){
            d=1000.0;
        }
        else if(d123==1){
            d=5000.0;
        }
        else if(d123==2)
        {
            d=10000.0;
        }
        else
        d=-1;


        SwipeRefreshLayout ms=(SwipeRefreshLayout) findViewById(R.id.swipeToRefresh1);
        ms.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            public void onRefresh() {
                Intent intent = getIntent();
                finish();
                overridePendingTransition( 0, 0);
                startActivity(intent);
                overridePendingTransition( 0, 0);

            }
        });
        Cursor cursor=db.getAllData();
        if(cursor!=null){
            if(cursor.moveToFirst())
            do{
                double lat1=cursor.getDouble(cursor.getColumnIndex("LATITUDE"));
                double lon1=cursor.getDouble(cursor.getColumnIndex("LONGITUDE"));
                LocationManager locationManager;
                Location location=null;
                double lat2=0,lon2=0;
                locationManager = (LocationManager) getSystemService(Service.LOCATION_SERVICE);
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(Main6Activity.this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}, 200);
                } else {
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 100, 2, Main6Activity.this);
                    if (locationManager != null) {
                        location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                    }
                }
                if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                    if (location != null) {
                        lat2=location.getLatitude();
                        lon2=location.getLongitude();
                    }
                }
                final int R = 6371; // Radius of the earth

                double latDistance = Math.toRadians(lat2 - lat1);
                double lonDistance = Math.toRadians(lon2 - lon1);
                double a1 = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                        + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                        * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
                double c = 2 * Math.atan2(Math.sqrt(a1), Math.sqrt(1 - a1));
                double distance = R * c * 1000; // convert to meters

                distance = Math.pow(distance, 2);
                if(d>0){
                if(Math.sqrt(distance)<d)
                    a.add(cursor.getString(cursor.getColumnIndex("SHOPNAME")));}
                else
                    a.add(cursor.getString(cursor.getColumnIndex("SHOPNAME")));

            }while(cursor.moveToNext());

        }
        /*ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,a);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View view, int position, long arg) {
                Intent appInfo = new Intent(getApplicationContext(), Main7Activity.class);
                appInfo.putExtra("sp",a.get(position));
                //Toast.makeText(Main6Activity.this, a.get(position)+"", Toast.LENGTH_SHORT).show();

                if(position!=0)
                startActivity(appInfo);
            }
        });*/
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerview2);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        r = new recad(this, a);
        r.setClickListener(Main6Activity.this);
        recyclerView.setAdapter(r);
        }
        @Override
        public void onLocationChanged(Location location1) {

        }
        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
        }
        @Override
        public void onProviderEnabled(String provider) {
        }
        @Override
        public void onProviderDisabled(String provider) {
            if (provider.equals(LocationManager.GPS_PROVIDER)) {
                Toast.makeText(this, "GPS OFF!!!!", Toast.LENGTH_SHORT).show();
            }
        }
        @Override
        public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
            if (requestCode == 200) {
                if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                }
            }



        }
public void onItemClick(View view, int position) {
    Intent appInfo = new Intent(getApplicationContext(), Main7Activity.class);
    appInfo.putExtra("sp",a.get(position));
    //Toast.makeText(Main6Activity.this, a.get(position)+"", Toast.LENGTH_SHORT).show();
        startActivity(appInfo);
        }

    public void locat(View v){
        Intent i=new Intent(this,MainActivity.class);
        startActivity(i);
    }

    public void ref(View v){
        ArrayList <String> a=new ArrayList<String>();
        a.add("                        Shopname");
        Cursor cursor=db.getAllData();
        if(cursor.moveToFirst()){
            do{
                double lat1=cursor.getDouble(cursor.getColumnIndex("LATITUDE"));
                double lon1=cursor.getDouble(cursor.getColumnIndex("LONGITUDE"));
                 LocationManager locationManager;
                 Location location=null;
                double lat2=0,lon2=0;
                locationManager = (LocationManager) getSystemService(Service.LOCATION_SERVICE);
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(Main6Activity.this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}, 200);
                } else {
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 100, 2, Main6Activity.this);
                    if (locationManager != null) {
                        location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                    }
                }
                if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                    if (location != null) {
                        lat2=location.getLatitude();
                        lon2=location.getLongitude();
                    }
                }
                final int R = 6371; // Radius of the earth

                double latDistance = Math.toRadians(lat2 - lat1);
                double lonDistance = Math.toRadians(lon2 - lon1);
                double a1 = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                        + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                        * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
                double c = 2 * Math.atan2(Math.sqrt(a1), Math.sqrt(1 - a1));
                double distance = R * c * 1000; // convert to meters

                distance = Math.pow(distance, 2);
                if(Math.sqrt(distance)<d)
                a.add(cursor.getString(cursor.getColumnIndex("SHOPNAME")));
            }while(cursor.moveToNext());

        }
        //ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,a);
        //lv.setAdapter(adapter);



        }

    public void onBackPressed(){
        Intent i=new Intent(this,Main2Activity.class);
        finishAffinity();
        startActivity(i);
    }
}
