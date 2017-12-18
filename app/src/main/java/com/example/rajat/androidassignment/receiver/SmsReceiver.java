package com.example.rajat.androidassignment.receiver;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.widget.Toast;

import com.example.rajat.androidassignment.R;
import com.example.rajat.androidassignment.activities.DashboardActivity;

public class SmsReceiver extends BroadcastReceiver {

    // SmsManager class is responsible for all SMS related actions
    private final SmsManager sms = SmsManager.getDefault();
    // UserSession userSession = new UserSession(SmsAppliaction.SmsApplication);
    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        Bundle bundle = intent.getExtras();

        try{
            if(bundle != null){
                // A PDU is a "protocol data unit". This is the industrial standard for SMS message
                final Object[] pdusObj = (Object[]) bundle.get("pdus");
                for(int i = 0;i < pdusObj.length;i++){
                    // This will create an SmsMessage object from the received pdu
                    SmsMessage smsMessage = SmsMessage.createFromPdu((byte[])pdusObj[i]);
                    //getSender PhoneNumber
                    String senderNumber = smsMessage.getDisplayOriginatingAddress();
                    String message = smsMessage.getMessageBody();

                    String formattedText = String.format(context.getResources().getString(R.string.sms_message),senderNumber,message);
                    Toast.makeText(context, formattedText, Toast.LENGTH_LONG).show();
                    DashboardActivity inst = DashboardActivity.instance();
                    inst.sendSms(formattedText,senderNumber);
                    //    userSession.setMessageReceived(formattedText);
                    // Intent intent1 = new Intent(,DashboardActivity.class);
                }
            }
        }catch (Exception exception){
            exception.printStackTrace();
        }
        // throw new UnsupportedOperationException("Not yet implemented");
    }
}