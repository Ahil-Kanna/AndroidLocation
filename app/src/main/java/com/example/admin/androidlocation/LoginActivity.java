package com.example.admin.androidlocation;

import android.Manifest;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static android.Manifest.permission.READ_CONTACTS;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {
    /**
     * Id to identity READ_CONTACTS permission request.
     */
    private static final int REQUEST_READ_CONTACTS = 0;
    /**
     * A dummy authentication store containing known user names and passwords.
     * TODO: remove after connecting to a real authentication system.
     */

private EditText e,f;
    String s=new String();
    String k=new String();
    MainActivity d=new MainActivity();
    loginadapter loginadapter;
    offerdata offerdata;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent i = getIntent();
        k= i.getStringExtra("shop");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginadapter=new loginadapter(getApplicationContext());
        offerdata=new offerdata(getApplicationContext());
        //offerdata=offerdata.open();
        loginadapter=loginadapter.open();
    ;}


    public void signup(View v){
        Intent i=new Intent(this,signup.class);
        i.putExtra("value",s);
        startActivity(i);
        finishAffinity();
    }
    public void signin(View view)
    {

        logindata appdet1=new logindata(getApplicationContext());


        TextInputEditText user=(TextInputEditText) findViewById(R.id.usernamel);
        TextInputLayout userl=(TextInputLayout) findViewById(R.id.textInputLayout);
        TextInputEditText pass=(TextInputEditText) findViewById(R.id.passwordl);
        TextInputLayout passl=(TextInputLayout) findViewById(R.id.passlay);


        if(TextUtils.isEmpty(user.getText().toString())) {
            userl.setError("This field can't be empty");
            Toast.makeText(this, "Enter fields", Toast.LENGTH_SHORT).show();
        }
        else if(!TextUtils.isEmpty(user.getText().toString())) {
            userl.setError(null);
        }

        if(TextUtils.isEmpty(pass.getText().toString())){
            passl.setError("This field can't be empty");
            Toast.makeText(this, "Enter fields", Toast.LENGTH_SHORT).show();
        }

        else if(!TextUtils.isEmpty(pass.getText().toString())) {
            userl.setError(null);
        }
        if(!TextUtils.isEmpty(user.getText().toString())&& !TextUtils.isEmpty(pass.getText().toString())){

            String storedPassword= loginadapter.getpass(user.getText().toString());

            if(pass.getText().toString().equals(storedPassword)) {
                Toast.makeText(this,user.getText().toString()+" Successfully Logged in",Toast.LENGTH_LONG).show();
                k=loginadapter.shopname(user.getText().toString());
                Intent intent=new Intent(this,Main4Activity.class);
                intent.putExtra("value",user.getText().toString());
                intent.putExtra("shop",k);
                finishAffinity();
                startActivity(intent);
                finish();
            }
            else
            {
                Toast.makeText(this, "User Name or Password does not match", Toast.LENGTH_LONG).show();
            }
        }}
    public void register(View view)
    {
        Intent i=new Intent(this,signup.class);
        i.putExtra("value",s);
        startActivity(i);
        finishAffinity();
    }
}


