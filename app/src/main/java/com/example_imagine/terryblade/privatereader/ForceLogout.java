package com.example_imagine.terryblade.privatereader;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.WindowManager;

/**
 * Created by Terryblade on 2017/4/9.
 */
public class ForceLogout extends BroadcastReceiver {
    public void onReceive(final Context context,Intent intent){
        AlertDialog.Builder builder=new AlertDialog.Builder(context);
        builder.setTitle("Warnning");
        builder.setMessage("Please try to log in again");
        builder.setCancelable(false);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                ActivityCollector.FinishAll();
                Intent intent1=new Intent(context,Login.class);
                intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent1);
            }
        });
        AlertDialog alertDialog=builder.create();
        alertDialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
        alertDialog.show();
    }
}
