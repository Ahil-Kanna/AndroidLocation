package com.example.admin.androidlocation;
import android.Manifest;
import android.app.Activity;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.util.Log;
import android.view.ContextMenu;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Main4Activity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,LocationListener {
    ListView listItemView;
    int position;
    String w;
    boolean doubleBackToExitPressedOnce = false;
    offerdata offerdata;
    offerdata db = new offerdata(this);
    loginadapter loginadapter;
    String YourEditTextValue;
    String s, k,h;
    String a[] = new String[1000];
    ArrayAdapter<String> m_adapter;
    DatabaseHandler db3=new DatabaseHandler(this);
    ArrayList<String> m_listItems = new ArrayList<String>();
    Context m = this;
    TextView textView,textView1;
    private String  latitudePosition;
    String editText;
    String t1,t2,t3;
    private String longitudePosition;
    private String currentCity;
    private LocationManager locationManager;
    private Location location;
    private final int REQUEST_LOCATION = 200;
    private static final String TAG = "MainActivity";
    PendingIntent pendingIntent;
    DatabaseHandler db1 = new DatabaseHandler(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);
        textView = (TextView) findViewById(R.id.name);
        loginadapter = new loginadapter(getApplicationContext());
        offerdata = new offerdata(getApplicationContext());
        offerdata = offerdata.open();
        h = getIntent().getStringExtra("shop");
        k = getIntent().getStringExtra("value");
        final AlertDialog.Builder alert = new AlertDialog.Builder(m);
        TextView t = (TextView) findViewById(R.id.textView);
        listItemView = (ListView) findViewById(R.id.listview1);
        registerForContextMenu(listItemView);
        m_listItems = db.openAndQueryDatabase(k);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        m_adapter = new ArrayAdapter<String>(this, R.layout.listview, R.id.textView, m_listItems);
        listItemView.setAdapter(m_adapter);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                final EditText edittext = new EditText(getApplicationContext());
                edittext.setTextColor(Color.BLUE);
                alert.setMessage("\tEnter Your Offer  : ");
                alert.setView(edittext);
                alert.setPositiveButton("Yes Option", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int whichButton) {
                        Editable YouEditTextValue = edittext.getText();
                                    if(edittext.getText().toString().isEmpty()) {
                                        Toast.makeText(getApplicationContext(),"Offer cannot be Empty",Toast.LENGTH_SHORT).show();
                                    }
                                    else
                                    {
                                        YourEditTextValue = YouEditTextValue.toString();
                                        offerdata.insertData(YourEditTextValue, h, k);
                                        m_adapter.add( YourEditTextValue);
                                        Snackbar.make(view, "Offer Added", Snackbar.LENGTH_LONG)
                                                .setAction("Action", null).show();
                                    }
                        registerForContextMenu(listItemView);
                    }
                });

                alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {

                        dialog.cancel();
                    }
                });
                AlertDialog a=alert.create();
                //TextView tv = (TextView) a.findViewById(android.R.id.message);
                //Toast.makeText(Main4Activity.this, tv.getText().toString(), Toast.LENGTH_SHORT).show();
                //Typeface typeface1 = Typeface.createFromAsset(getAssets(), "fonts/quicksand/Quicksand-Regular.otf");
                //tv.setTypeface(typeface1);
                a.show();
            }
        });
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View header=navigationView.getHeaderView(0);
/*View view=navigationView.inflateHeaderView(R.layout.nav_header_main);*/
        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/CaviarDreams.ttf");
         textView = (TextView)header.findViewById(R.id.name);
        textView1=(TextView)header.findViewById(R.id.shopname);
                textView.setText("USERNAME : "+k);
        textView1.setText("SHOPNAME : "+h);
        textView.setTypeface(typeface);
        textView1.setTypeface(typeface);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        locationManager = (LocationManager) getSystemService(Service.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(Main4Activity.this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
        } else {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 100, 2, this);
            if (locationManager != null) {
                location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            }
        }
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            if (location != null) {
              latitudePosition=(String.valueOf(location.getLatitude()));
                longitudePosition=(String.valueOf(location.getLongitude()));

                getAddressFromLocation(location, getApplicationContext(), new Main4Activity.GeoCoderHandler());
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
        /*latitudePosition.setText(String.valueOf(location.getLatitude()));
        longitudePosition.setText(String.valueOf(location.getLongitude()));
        */
        getAddressFromLocation(location, getApplicationContext(), new Main4Activity.GeoCoderHandler());
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
            currentCity=result;
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if (doubleBackToExitPressedOnce) {
                Intent intent = new Intent(getApplicationContext(), Main2Activity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("EXIT", true);
                finishAffinity();
                startActivity(intent);
                return;
            }

            this.doubleBackToExitPressedOnce = true;
            Toast.makeText(this, "Press BACK again to exit", Toast.LENGTH_SHORT).show();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    doubleBackToExitPressedOnce = false;
                }
            }, 2000);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main4, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if(id==R.id.add)
        {
            Toast.makeText(this,"Wait Till Your Data is inserted",Toast.LENGTH_SHORT).show();
            //Toast.makeText(this,h,Toast.LENGTH_SHORT).show();
                       boolean isInserted = db3.insertData(latitudePosition,
                                longitudePosition,currentCity,h);
                        if(isInserted == true)
                            Toast.makeText(Main4Activity.this,"Data Inserted",Toast.LENGTH_SHORT).show();
                        else
                            Toast.makeText(Main4Activity.this,"Data not Inserted",Toast.LENGTH_LONG).show();

        }
        if (id == R.id.action_settings) {
            Intent intent = new Intent(getApplicationContext(), Main2Activity.class);
            finishAffinity();
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.nav_share) {
            Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
            sharingIntent.setType("text/plain");
            sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "OFFERTAS");
            sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, "Now click here to download apk https://offertas.com/ ");
            startActivity(Intent.createChooser(sharingIntent, "Share via"));

        } else if (id == R.id.nav_send) {

            Toast.makeText(m, "ADDRESS: "+currentCity, Toast.LENGTH_SHORT).show();


        }
        else if(id==R.id.log){
            Intent i=new Intent(getApplicationContext(),Main2Activity.class);
            finishAffinity();
            startActivity(i);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        int position = listItemView.getPositionForView(v);
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.setHeaderTitle("Select Action");
        menu.add(0, v.getId(), 0, "Delete");
        menu.add(0, v.getId(), 0, "Update");
    }

    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        if (item.getTitle() == "Delete") {
            String a=m_adapter.getItem(info.position);
            Snackbar.make(getCurrentFocus(), "Offer "+a+" Deleted", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();

            //Toast.makeText(m,"Offer " +a+"DELETED", Toast.LENGTH_SHORT).show();
            m_adapter.remove(a);
            offerdata.deleteData(a.trim());
            m_adapter.notifyDataSetChanged();
        }
        else if(item.getTitle() == "Update"){
            final String a=m_adapter.getItem(info.position);
            AlertDialog.Builder alert1=new AlertDialog.Builder(this);
            final EditText et = new EditText(getApplicationContext());
            et.setTextColor(Color.BLUE);

            alert1.setMessage("\tEnter new Offer  : ");
            alert1.setView(et);
            alert1.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    Editable YouEditTextValue = et.getText();
                    if(et.getText().toString().isEmpty()) {
                        Toast.makeText(getApplicationContext(),"Offer cannot be Empty",Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        YourEditTextValue = YouEditTextValue.toString();
                        offerdata.updateData("",a,YouEditTextValue.toString());
                        m_adapter.remove(a);
                        m_adapter.add( YourEditTextValue);
                    }
                    registerForContextMenu(listItemView);
                }
            });
            alert1.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {

                    dialog.cancel();
                }
            });
            AlertDialog alert2 = alert1.create();
            alert2.show();
            //Typeface typeface1 = Typeface.createFromAsset(getAssets(), "fonts/quicksand/Quicksand-Regular.otf");


        }
        return true;
    }
    public static void shareAPK(Activity activity) {
        if (ContextCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            try {
                // First we should copy apk file from source dir to ur external dir
                ApplicationInfo app = activity.getPackageManager().getApplicationInfo(activity.getPackageName(), 0);

                File apkFile = new File(app.sourceDir);
                File backupFile = new File(Environment.getExternalStorageDirectory(), ".apk");
                copy(apkFile, backupFile);
                Intent shareIntent = getShareIntent(backupFile);
                activity.startActivity(Intent.createChooser(shareIntent, "Send [AppName] application APK using"));
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static Intent getShareIntent(File file) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file));
        intent.setType("application/vnd.android.package-archive");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        return intent;
    }
    public static void copy(File src, File dst) throws IOException {
        FileInputStream inStream = new FileInputStream(src);
        FileOutputStream outStream = new FileOutputStream(dst);
        FileChannel inChannel = inStream.getChannel();
        FileChannel outChannel = outStream.getChannel();
        inChannel.transferTo(0, inChannel.size(), outChannel);
        inStream.close();
        outStream.close();
    }
}

