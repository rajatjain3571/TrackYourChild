package com.example.rajat.androidassignment.activities;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.rajat.androidassignment.R;
import com.example.rajat.androidassignment.helper.UserSession;


public class MainActivity extends AppCompatActivity {
    EditText fromemail,password,toemail;
    Button ctn;
    private Dialog dialog = null;

    UserSession userSession;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userSession=new UserSession(this);
        setContentView(R.layout.activity_main);

        // Checking whether user have already done setup or not. if yes than landing to direct dashboard page
        if (userSession.isLoggedIn())
        {
            Intent intent = new Intent(getApplicationContext(), DashboardActivity.class);
            startActivity(intent);
        }
        fromemail = (EditText)findViewById(R.id.from_email_input);  // Enter gmail id from where you will receive emails
        password = (EditText) findViewById(R.id.from_password_input);//Enter gmail password
        toemail = (EditText) findViewById(R.id.to_email_input);// Enter to which gmail id you want to receive email
        ctn=(Button)findViewById(R.id.ctn_btn);

        ctn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog = userSession.showDialog(MainActivity.this);
                String fromEmail=fromemail.getText().toString();
                String pwd=password.getText().toString();
                String toEmail=toemail.getText().toString();
                if(fromEmail.equals("")||toEmail.equals("")||pwd.equals(""))
                {
                    userSession.dismissDialog(dialog);// Just checking whether details are not empty// we can add other validations also like this
                    Snackbar snackbar = Snackbar
                            .make(findViewById(android.R.id.content),"Please Enter Details", Snackbar.LENGTH_LONG);

                    snackbar.show();
                }
                else
                {// creating setup for email

                    userSession.setFromEmailId(fromEmail);
                    userSession.setToEmailId(toEmail);
                    userSession.setFromEmailPwd(pwd);
                    userSession.markLogin(true);
                    userSession.dismissDialog(dialog);
                    Intent intent = new Intent(MainActivity.this,DashboardActivity.class);
                    startActivity(intent);
                    finish();

                }


            }
        });


    }

}
