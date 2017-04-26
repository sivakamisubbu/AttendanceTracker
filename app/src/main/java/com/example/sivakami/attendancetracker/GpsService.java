package com.example.sivakami.attendancetracker;

import android.Manifest;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.widget.Toast;

/**
 * Created by SIVAKAMI on 26/0/17.
 */

public class GpsService extends Service
{
    Double getlongitue;
    Double getlatitude;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, final int startId) {
        Toast.makeText(getApplicationContext(), "service has started", Toast.LENGTH_LONG).show();
        class mythread extends Thread {

            public void run() {

                LocationManager mloc = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

                LocationListener locationListener = new LocationListener() {
                    public void onLocationChanged(Location location) {
                        // Called when a new location is found by the network location provider.
                        getlongitue = location.getLongitude();
                        getlatitude = location.getLatitude();
                    }

                    public void onStatusChanged(String provider, int status, Bundle extras) {
                    }

                    public void onProviderEnabled(String provider) {

                    }

                    public void onProviderDisabled(String provider) {
                    }
                };



// Register the listener with the Location Manager to receive location updates

                if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                mloc.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener, Looper.getMainLooper());
            }


        }

        mythread mythreadobj=new mythread();
        mythreadobj.start();
        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        Toast.makeText(getApplicationContext(),"end: longitude: "+getlatitude+" latitude: "+getlongitue,Toast.LENGTH_LONG).show();
    }


}
