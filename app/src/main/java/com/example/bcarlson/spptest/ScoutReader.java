package com.example.bcarlson.spptest;

import android.app.Activity;
import android.bluetooth.BluetoothSocket;
import android.util.Log;

import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by bcarlson on 10/13/17.
 */

public class ScoutReader extends Thread {

    public static final String TAG = "ScoutReader";

    InputStream mInputStream;
    MainActivity mMainActivity;

    public ScoutReader(BluetoothSocket socket, MainActivity mainActivity)
    {
        try
        {
            mInputStream = socket.getInputStream();
            mMainActivity = mainActivity;
        } catch (Exception e) {
            Log.v(TAG, e.getMessage());
        }

    }

    public void run() {

        byte[] testArray = new byte[255];


        while(true) {

            try {
                final int bytesRead = mInputStream.read(testArray);

                mMainActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mMainActivity.updateBytesRead(bytesRead);
                    }
                });

                sleep(100);
            } catch (Exception e) {
                Log.v(TAG, e.getMessage());
            }

        }

    }

}
