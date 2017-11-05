package com.example.bcarlson.spptest;

import android.bluetooth.BluetoothSocket;
import android.icu.util.Output;
import android.os.Looper;
import android.util.Log;

import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by bcarlson on 10/12/17.
 */

public class ScoutWriter extends Thread {

    public static final String TAG = "ScoutWriter";

    OutputStream mOutputStream;

    public ScoutWriter(BluetoothSocket socket)
    {
        try
        {
            mOutputStream = socket.getOutputStream();
        } catch (Exception e) {
            Log.v(TAG, e.getMessage());
        }

    }

    public void run() {

        byte[] testArray = "HelloThereHereAreSomeBytes".getBytes();

        while(true) {

            try {
                mOutputStream.write(testArray);
                mOutputStream.flush();

                sleep(1000);
            } catch (Exception e) {
                Log.v(TAG, e.getMessage());
            }

        }

    }

}
