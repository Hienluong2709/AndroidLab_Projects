package com.example.lab10_intent_broadcastreceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.widget.Toast;

public class MySmsReceive extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        processReceive(context, intent);
    }

    public void processReceive(Context context, Intent intent) {
        Bundle extras = intent.getExtras();
        String message="";
        String body="";
        String address="";

        if (extras != null) {
            Object[] smsExtra = (Object[]) extras.get("pdus");
            for (int i = 0; i < smsExtra.length; i++) {
                SmsMessage sms = SmsMessage.createFromPdu((byte[]) smsExtra[i]);
                body = sms.getMessageBody(); // Nội dung tin nhắn
                address = sms.getOriginatingAddress(); // Số điện thoại gửi
                message += "Có 1 tin nhắn từ " + address + "\n" + body + " vừa gửi đến.\n";
            }

            // Hiển thị thông báo
            Toast.makeText(context, message, Toast.LENGTH_LONG).show();
        }
    }
}
