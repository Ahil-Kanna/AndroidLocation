package com.example.admin.androidlocation;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.List;

/**
 * Created by Admin on 09/11/2017.
 */

public class signup extends Activity {
   TextInputEditText shop11;
    String s1;
    String s;
    loginadapter loginDataBaseAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);
        Button b=(Button) findViewById(R.id.button6);

        s=getIntent().getStringExtra("value");
        //Toast.makeText(this, getIntent().getStringExtra("user"), Toast.LENGTH_SHORT).show();
        loginDataBaseAdapter = new loginadapter(getApplicationContext());
        b.setOnClickListener(new View.OnClickListener (){

            public void onClick(View v) {

                loginDataBaseAdapter = loginDataBaseAdapter.open();
                int j=0;
                TextInputEditText user = (TextInputEditText) findViewById(R.id.username1);
                TextInputLayout userl1 = (TextInputLayout) findViewById(R.id.textInputLayout1);
                TextInputEditText pass = (TextInputEditText) findViewById(R.id.password1);
                TextInputLayout passl1 = (TextInputLayout) findViewById(R.id.passlay1);
                TextInputEditText passr = (TextInputEditText) findViewById(R.id.password2);
                TextInputLayout passl2 = (TextInputLayout) findViewById(R.id.passlay2);
                TextInputLayout shop =(TextInputLayout) findViewById(R.id.shopnamelay);
                shop11 =(TextInputEditText) findViewById(R.id.shopname);
                 s1=shop11.getText().toString();
                if (TextUtils.isEmpty(user.getText().toString())) {
                    userl1.setError("Field can't be empty");
                } else
                    userl1.setError(null);
                if (TextUtils.isEmpty(pass.getText().toString())||TextUtils.isEmpty(passr.getText().toString())) {
                    passl1.setError("Field can't be empty");
                }
                else
                    shop.setError(null);
                if (TextUtils.isEmpty(shop11.getText().toString())) {
                    shop.setError("Field can't be empty");
                }
                else
                    passl2.setError(null);
                if(!user.getText().toString().matches("[a-zA-z0-9._-]+@[a-z]+.[a-z]+")){


                    userl1.setError("INVALID EMAIL USERNAME");
                }
                else{
                if (!pass.getText().toString().equals(passr.getText().toString())) {
                    Toast.makeText(getApplicationContext(), "Password does not match", Toast.LENGTH_LONG).show();
                    passl2.setError("Password doesn't match!!!");

                } else if (!TextUtils.isEmpty(user.getText().toString()) && !TextUtils.isEmpty(pass.getText().toString())) {
                    if(!loginDataBaseAdapter.usercheck(user.getText().toString()))
                    {
                        userl1.setError("Username already exist");
                    }
                    else {
                        loginDataBaseAdapter.insertEntry(user.getText().toString(), pass.getText().toString(), user.getText().toString(), shop11.getText().toString());
                        Toast.makeText(getApplicationContext(), "\""+shop11.getText().toString()+ "\" Account Successfully Created ", Toast.LENGTH_LONG).show();
                        Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                         i.putExtra("shop", user.getText().toString());
                        startActivity(i);
                        finish();
                    }
                }

            }}
        });


    }
    public void onBackPressed()
    {
        Intent i=new Intent(getApplicationContext(),Main2Activity.class);
        startActivity(i);
    }

    public void login(View v){
        Intent i=new Intent(this,LoginActivity.class);
        i.putExtra("shop",s1);
        startActivity(i);
    }

    public void onDestroy(){
        super.onDestroy();
        //loginDataBaseAdapter.close();
    }

}
