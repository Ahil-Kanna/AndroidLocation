package com.example.admin.androidlocation;

import android.Manifest;
import android.app.ProgressDialog;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
public class MainActivity extends AppCompatActivity implements LocationListener{
    private TextView latitudePosition;
    TextView t1,t2,t3;
    int distance12;
    private TextView longitudePosition;
    private TextView currentCity;
    private LocationManager locationManager;
    public static Location location;
    private final int REQUEST_LOCATION = 200;
    private static final String TAG = "MainActivity";
    DatabaseHandler db = new DatabaseHandler(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SeekBar simpleSeekBar = (SeekBar) findViewById(R.id.seekBar); // initiate the Seek bar
        simpleSeekBar.setMax(3);
        simpleSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progressChangedValue = 0;

            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progressChangedValue = progress;
                distance12=progress;
            }

            public void onStartTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            public void onStopTrackingTouch(SeekBar seekBar) {
                //Toast.makeText(MainActivity.this, "Seek bar progress is :" + progressChangedValue,
                      //  Toast.LENGTH_SHORT).show();

            }
        });
        SwipeRefreshLayout ms=(SwipeRefreshLayout) findViewById(R.id.swipeToRefresh);
        ms.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            public void onRefresh() {
                Intent intent = getIntent();
                finish();
                overridePendingTransition( 0, 0);
                startActivity(intent);
                overridePendingTransition( 0, 0);

            }
        });
        latitudePosition =(TextView) findViewById(R.id.latitude);
        longitudePosition=(TextView) findViewById(R.id.longitude);
        currentCity=(TextView) findViewById(R.id.city);
        locationManager = (LocationManager) getSystemService(Service.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
        } else {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 100, 2, this);
            if (locationManager != null) {
                location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            }
        }
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            if (location != null) {
                latitudePosition.setText(String.valueOf(location.getLatitude()));
                longitudePosition.setText(String.valueOf(location.getLongitude()));
                getAddressFromLocation(location, getApplicationContext(), new GeoCoderHandler());
            }
        } else {
            showGPSDisabledAlertToUser();
        }
    }
    ProgressDialog progressDoalog;

/*
    public void DeleteData() {
        btnDelete.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Integer deletedRows = db.deleteData(editTextId.getText().toString());
                        if(deletedRows > 0)
                            Toast.makeText(MainActivity.this,"Data Deleted",Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(MainActivity.this,"Data not Deleted",Toast.LENGTH_LONG).show();
                    }
                }
        );
    }
    public void UpdateData() {
        btnviewUpdate.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean isUpdate = myDb.updateData(editTextId.getText().toString(),
                                editName.getText().toString(),
                                editSurname.getText().toString(),editMarks.getText().toString());
                        if(isUpdate == true)
                            Toast.makeText(MainActivity.this,"Data Update",Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(MainActivity.this,"Data not Updated",Toast.LENGTH_LONG).show();
                    }
                }
        );
    }
    */

    @Override
    public void onLocationChanged(Location location) {
        latitudePosition.setText(String.valueOf(location.getLatitude()));
        longitudePosition.setText(String.valueOf(location.getLongitude()));
        getAddressFromLocation(location, getApplicationContext(), new GeoCoderHandler());
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
            showGPSDisabledAlertToUser();
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == REQUEST_LOCATION) {
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            }
        }
    }
    private void showGPSDisabledAlertToUser() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("GPS is disabled in your device. Would you like to enable it?")
                .setCancelable(false)
                .setPositiveButton("Goto Settings Page To Enable GPS", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent callGPSSettingIntent = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivity(callGPSSettingIntent);
                    }
                });
        alertDialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
        //Toast.makeText(this, "GPS OFF", Toast.LENGTH_SHORT).show();
    }

    public static void getAddressFromLocation(final Location location, final Context context, final Handler handler) {
        Thread thread = new Thread() {
            @Override public void run() {
                Geocoder geocoder = new Geocoder(context, Locale.getDefault());
                String result = null;
                try {
                    List<Address> list = geocoder.getFromLocation(
                            location.getLatitude(), location.getLongitude(), 1);
                    if (list != null && list.size() > 0) {
                        Address address = list.get(0);
                        result = address.getAddressLine(0) + ", " + address.getLocality() + ", " +  address.getCountryName() ;
                    }
                } catch (IOException e) {
                    Log.e(TAG, "Impossible to connect to Geocoder", e);
                } finally {
                    Message msg = Message.obtain();
                    msg.setTarget(handler);
                    if (result != null) {
                        msg.what = 1;
                        Bundle bundle = new Bundle();
                        bundle.putString("address", result);
                        msg.setData(bundle);
                    } else
                        msg.what = 0;
                    msg.sendToTarget();
                }
            }
        };
        thread.start();
    }
    private class GeoCoderHandler extends Handler {
        @Override
        public void handleMessage(Message message) {
            String result;
            switch (message.what) {
                case 1:
                    Bundle bundle = message.getData();
                    result = bundle.getString("address");
                    break;
                default:
                    result = null;
            }
            currentCity.setText(result);
        }
    }
    public void click2(View view)
    {
        Intent intent = getIntent();
        finish();
        overridePendingTransition( 0, 0);
        startActivity(intent);
        overridePendingTransition( 0, 0);
    }
    public  void click(View view) {
        SharedPreferences bb = getSharedPreferences("my_prefs", 0);

        String m = bb.getString("NUM", "");
        Toast.makeText(this,m,Toast.LENGTH_SHORT).show();
        Toast.makeText(this, m, Toast.LENGTH_SHORT).show();
                       /* boolean isInserted = db.insertData(t1.getText().toString(),
                                t2.getText().toString(),
                                t3.getText().toString(),editText.getText().toString(),m);
                        if(isInserted == true)
                            Toast.makeText(MainActivity.this,"Data Inserted",Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(MainActivity.this,"Data not Inserted",Toast.LENGTH_LONG).show();*/
    }
    public void click1(View view1) {
    Intent intent=new Intent (getApplicationContext(),Main2Activity.class);
        startActivity(intent);
    }
    public void showMessage(String title,String Message){
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }


    public void onBackPressed(){
        Intent i=new Intent(getApplicationContext(),Main6Activity.class);
        i.putExtra("dist",distance12);
        //Toast.makeText(this, ""+distance12, Toast.LENGTH_SHORT).show();
        finish();
        startActivity(i);
    }
}