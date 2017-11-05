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

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "BTTEST";

    private BluetoothAdapter mBluetoothAdapter;
    private BluetoothDevice mBluetoothDevice;
    private BluetoothSocket mBluetoothSocket;
    private OutputStream mBluetoothOutStream;

    private ScoutWriter mScoutWriter;
    private ScoutReader mScoutReader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btConnect = (Button)findViewById(R.id.btConnect);
        final TextView etAddress = (TextView)findViewById(R.id.etAddress);

        btConnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String connectAddress = etAddress.getText().toString();
                connectBluetooth(connectAddress);
            }
        });
    }

    public void connectBluetooth(String mac) {
        try {
            mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

            mBluetoothDevice = mBluetoothAdapter.getRemoteDevice(mac);

            UUID SERIAL_UUID = UUID.fromString("94f39d29-7d6d-437d-973b-fba39e49d4ee");

            mBluetoothSocket = mBluetoothDevice.createRfcommSocketToServiceRecord(SERIAL_UUID);

            mBluetoothSocket.connect();

            mScoutWriter = new ScoutWriter(mBluetoothSocket);
            mScoutWriter.start();

            mScoutReader = new ScoutReader(mBluetoothSocket, MainActivity.this);
            mScoutReader.start();
        } catch (Exception e) {
            Log.v(TAG, e.getMessage());
        }
    }
}
