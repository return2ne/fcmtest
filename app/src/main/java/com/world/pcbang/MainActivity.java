package com.world.pcbang;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.android.gms.ads.identifier.AdvertisingIdClient;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Thread workerThread = new Thread(new Runnable() {

            @Override
            public void run() {
                AdvertisingIdClient.Info adInfo = null;
                try {
                    adInfo = AdvertisingIdClient.getAdvertisingIdInfo(MainActivity.this);
                } catch (GooglePlayServicesNotAvailableException e) {
                    // indicating that Google Play is not installed on this device.
                    e.printStackTrace();
                    Log.e(TAG, "ADID GooglePlayServicesNotAvailableException error = " + e.errorCode);
                } catch (GooglePlayServicesRepairableException e) {
                    // indicating that there was a recoverable error connecting to Google Play Services.
                    e.printStackTrace();
                    Log.e(TAG, "ADID GooglePlayServicesRepairableException error = " + e.getConnectionStatusCode());
                } catch (IOException e) {
                    // signaling connection to Google Play Services failed.
                    e.printStackTrace();
                    Log.e(TAG, "ADID getAdvertisingIdInfo IOException error 3");
                } catch (IllegalStateException e) {
                    //indicating this method was called on the main thread.
                    e.printStackTrace();
                    Log.e(TAG, "ADID getAdvertisingIdInfo IllegalStateException error 4");
                }

                //int ret = GooglePlayServicesUtil.isGooglePlayServicesAvailable(context);

                if (adInfo != null) {
                    Log.v(TAG, "ADID AdvertisingIDLoader mAdID = " + adInfo.getId());
                    Log.v(TAG, "ADID AdvertisingIDLoader mIsLAT = " + adInfo.isLimitAdTrackingEnabled());
                }
            }
        });
        workerThread.start();
    }
}
