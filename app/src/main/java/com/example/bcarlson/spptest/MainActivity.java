package com.example.bcarlson.spptest;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.OutputStream;
import java.util.UUID;

import java.lang.reflect.Method;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "BTTEST";

    private BluetoothAdapter mBluetoothAdapter;
    private BluetoothDevice mBluetoothDevice;
    private BluetoothSocket mBluetoothSocket;
    private OutputStream mBluetoothOutStream;

    private ScoutWriter mScoutWriter;
    private ScoutReader mScoutReader;
    private TextView tvBytes;

    Integer mTotalBytes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btConnect = (Button)findViewById(R.id.btConnect);
        Button btStream = (Button)findViewById(R.id.btStream);
        final TextView etAddress = (TextView)findViewById(R.id.etAddress);
        tvBytes = (TextView)findViewById(R.id.tvBytesRead);

        mTotalBytes = 0;

        btConnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String connectAddress = etAddress.getText().toString();
                Log.v("CONNECT", "Attempting Connect - " + connectAddress);
                connectBluetooth(connectAddress);
            }
        });

        btStream.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.v("STREAM", "Clicked Stream");
                mScoutWriter = new ScoutWriter(mBluetoothSocket);
                mScoutWriter.start();

                mScoutReader = new ScoutReader(mBluetoothSocket, MainActivity.this);
                mScoutReader.start();
            }
        });
    }


    public void connectBluetooth(String mac) {
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        try {
            mBluetoothDevice = mBluetoothAdapter.getRemoteDevice(mac);
        } catch (Exception e) {
            Log.v(TAG, e.getMessage());
        }

        UUID SERIAL_UUID = UUID.fromString("94f39d29-7d6d-437d-973b-fba39e49d4ee");

        try {
            mBluetoothSocket = mBluetoothDevice.createRfcommSocketToServiceRecord(SERIAL_UUID);
        } catch (Exception e) {
            Log.v("CONNECT","Error creating socket");
        }

        try {
            mBluetoothSocket.connect();
            Log.v("CONNECT","Connected");
        } catch (Exception e) {
            Log.v("CONNECT", e.getMessage());
        }
    }

    public void updateBytesRead(Integer bytes) {
        mTotalBytes += bytes;
        tvBytes.setText(mTotalBytes.toString());
    }
}
