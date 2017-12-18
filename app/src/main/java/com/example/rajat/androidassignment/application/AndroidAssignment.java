package com.example.rajat.androidassignment.application;

import android.app.Application;
import android.content.ContentResolver;
import android.content.Intent;

import com.example.rajat.androidassignment.activities.DashboardActivity;
import com.example.rajat.androidassignment.helper.UserSession;

/**
 * Created by rajat on 12/18/17.
 */

public class AndroidAssignment extends Application {

    public static AndroidAssignment  assignmentApplication = null  ;

    @Override
    public void onCreate() {
        super.onCreate();
        assignmentApplication = this;
        ContentResolver contentResolver = assignmentApplication.getContentResolver();
        //contentResolver.delete(DbConstants.URI_DASHBOARD, null, null);
        UserSession session = new UserSession(getApplicationContext());

    }

}

