package com.example.rajat.androidassignment.helper;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by rajat on 12/16/17.
 */

public class UserSession {
    SharedPreferences pref;

    // Editor reference for Shared preferences
    SharedPreferences.Editor editor;

    // Context
    Context _context;
    public static final String KEY_From_Email="fromemail";
    public static final String KEY_To_EMAIL="toemail";
    public static final String KEY_From_Pwd="pwd";
    public static final String KEY_Login="login";

    public UserSession(Context context) {
        this._context = context;
        pref = PreferenceManager.getDefaultSharedPreferences(context);
        editor = pref.edit();   //gets  a SharedPreferences instance that points to the default file that is
        // used by the preference framework in the given context.
    }
    public boolean isLoggedIn(){
        return pref.getBoolean(KEY_Login,false);
    }
    public void markLogin(boolean login)
    {
        editor.putBoolean(KEY_Login, login);
        editor.commit();
    }
    public void clearData(){
        setFromEmailPwd("");
        setToEmailId("");
        setFromEmailId("");
        markLogin(false);
    }
    public void setFromEmailId(String email) {
        editor.putString(KEY_From_Email, email);
        editor.commit();
    }

    public String getFromEmailId(){
        return pref.getString(KEY_From_Email,"");
    }

    public void setToEmailId(String email) {
        editor.putString(KEY_To_EMAIL, email);
        editor.commit();
    }

    public String getToEmailId(){
        return pref.getString(KEY_To_EMAIL,"");
    }

    public void setFromEmailPwd(String password) {
        editor.putString(KEY_From_Pwd, password);
        editor.commit();
    }

    public String getFromEmailIdPwd(){
        return pref.getString(KEY_From_Pwd,"");
    }
    public static void dismissDialog(Dialog dialog)
    {
        if(dialog != null && dialog.isShowing())
        {
            dialog.dismiss();
        }
    }

    public static Dialog showDialog(Context context)
    {
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Please wait...");
        progressDialog.show();
        // progressDialog.setCancelable(false);
        return progressDialog;
    }

}