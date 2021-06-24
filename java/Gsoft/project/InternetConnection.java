package Gsoft.project;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class InternetConnection extends AppCompatActivity {

    static boolean isConnected(Activity activity) {

        ConnectivityManager cm = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);

        /*NetworkInfo wifiCon = cm.getActiveNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo mobCon  = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        if ((wifiCon !=null && wifiCon.isConnected() || (mobCon !=null && mobCon.isConnected()) )){
            return true;
        } else {
            return false;
        }*/

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (activeNetwork !=null){
            return true;
        } else {
            return false;
        }
    }

    static void showCustomDailog(final Context context, final Activity activity) {

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("You are not connected to the internet")
                .setCancelable(false)
                .setPositiveButton("Connect", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        context.startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
                    }
                })
                .setNegativeButton("Exit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        activity.finish();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }


}
