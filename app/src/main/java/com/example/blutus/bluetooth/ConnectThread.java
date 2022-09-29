package com.example.blutus.bluetooth;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.util.Log;

import androidx.core.app.ActivityCompat;

import java.io.IOException;
import java.util.UUID;

public class ConnectThread extends Thread {
    private BluetoothAdapter btAdapter;
    private BluetoothSocket mSocket;
    private ReceiveThread rThread;
    public static final String UUID = "00001101-0000-1000-8000-00805F9B34FB";

    public ConnectThread(BluetoothAdapter btAdapter, BluetoothDevice device){
        this.btAdapter = btAdapter;
        try{
            mSocket = device.createRfcommSocketToServiceRecord(java.util.UUID.fromString(UUID));
        } catch (IOException e){

        }
    }

    @Override
    public void run() {
        btAdapter.cancelDiscovery();
        try{
            mSocket.connect();
            rThread = new ReceiveThread(mSocket);
            rThread.start();
            Log.d("MyLog","Connected");
        } catch (IOException e){
            Log.d("MyLog","Not Connected");
            closeConnection();
        }
    }
    public void closeConnection(){
        try{
            mSocket.close();
        } catch (IOException y){

        }
    }

    public ReceiveThread getRThread() {
        return rThread;
    }
}