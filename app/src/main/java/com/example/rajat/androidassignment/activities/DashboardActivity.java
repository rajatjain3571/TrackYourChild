package com.example.rajat.androidassignment.activities;


import android.Manifest;

import android.content.Intent;
import android.content.pm.PackageManager;

import android.os.AsyncTask;

import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;

import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.rajat.androidassignment.R;
import com.example.rajat.androidassignment.helper.SendEmail;
import com.example.rajat.androidassignment.helper.UserSession;

public class DashboardActivity extends AppCompatActivity{

    private static DashboardActivity activity;
    UserSession userSession;
    final  int   MY_PERMISSIONS_REQUEST_READ_SMS = 5;

    public static DashboardActivity instance() {
        return activity;
    }
    Button reset;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        userSession= new UserSession(this);
        reset= (Button)findViewById(R.id.reset);
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userSession.clearData();// if user want to reset setup than click it will clear all shared pref and user can reset again
                Intent intent = new Intent(DashboardActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        //Taking permission for reading sms
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_SMS)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.READ_SMS)) {

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_SMS},
                        MY_PERMISSIONS_REQUEST_READ_SMS);


            } else {

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_SMS},
                        MY_PERMISSIONS_REQUEST_READ_SMS);

            }
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_SMS: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //readSMS();
                }else{
                    Toast.makeText(DashboardActivity.this,"Please give permission to continue",Toast.LENGTH_SHORT).show();
                }
                return;
            }
        }
    }



    @Override
    protected void onStart() {
        super.onStart();
        activity = this;
    }

// we will get call back of sms broadcast here ...later for sending email i am doing it by async task so it will run in background
    public void sendSms(final String newSms ,final String senderNumber) {
        new SendEmailAsync(Html.fromHtml(newSms).toString(),senderNumber).execute();
    }


    private class SendEmailAsync extends AsyncTask<Void, Void, Void>
    {

        String message,number;
        Exception exception = null;
        SendEmailAsync(String message,String number)
        {
            this.message=message;

            this.number=number;
        }

        @Override
        protected Void doInBackground(Void... params)
        {

            try
            {
                final String username=userSession.getFromEmailId();
                final String password = userSession.getFromEmailIdPwd();
                sendEmail(username,password,userSession.getToEmailId(),message,number);


            }
            catch(Exception exception)
            {
                this.exception = exception;
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

        }
    }

    public void sendEmail(final String username, final String password, String tosend, String messageBody, String fromNumber)
    {
        SendEmail sendEmail = new SendEmail(username,password);
        try {
            sendEmail.sendMail("PamSpam", "Message received from"+fromNumber+" is "+messageBody, username, tosend);
        }catch (Exception e)
        {

        }

    }

}