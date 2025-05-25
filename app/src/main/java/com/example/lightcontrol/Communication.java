package com.example.lightcontrol;

import static java.lang.Thread.sleep;

import android.content.Context;
import android.widget.Toast;

public class Communication {
    private BluetoothConnection bluetoothConnection;
    private Context context;

    Communication(Context ctx)
    {
        context=ctx;
        bluetoothConnection=new BluetoothConnection(context);
    }

    private void checkIfBluetoothOnAndIfNotAsked()
    {
        if(BluetoothConnection.isEnabled()==false) bluetoothConnection.enableBluetooth();
    }

    private int[] incomingData;
    private boolean ackCame;
    public int[] getIncomingData()
    {
        return incomingData;
    }

    private byte[] outgoingMessage;
    public void sendCommand(Mode mode,int redValue,int greenValue,int blueValue,int brightnessValue,int sensitivityValue)
    {
        Toast toast = Toast.makeText(context, "Sending...", Toast.LENGTH_LONG);
        toast.show();

        checkIfBluetoothOnAndIfNotAsked();
        if(mode==Mode.NORMAL)
        {
            outgoingMessage = new byte[5];
            outgoingMessage[0]=(byte)1;
            outgoingMessage[1]=(byte)redValue;
            outgoingMessage[2]=(byte)greenValue;
            outgoingMessage[3]=(byte)blueValue;
            outgoingMessage[4]=(byte)brightnessValue;
        }
        else if(mode==Mode.PHOTO)
        {
            outgoingMessage = new byte[6];
            outgoingMessage[0]=(byte)2;
            outgoingMessage[1]=(byte)redValue;
            outgoingMessage[2]=(byte)greenValue;
            outgoingMessage[3]=(byte)blueValue;
            outgoingMessage[4]=(byte)brightnessValue;
            outgoingMessage[5]=(byte)sensitivityValue;
        }
        else if(mode==Mode.MOTION)
        {
            outgoingMessage = new byte[5];
            outgoingMessage[0]=(byte)3;
            outgoingMessage[1]=(byte)redValue;
            outgoingMessage[2]=(byte)greenValue;
            outgoingMessage[3]=(byte)blueValue;
            outgoingMessage[4]=(byte)brightnessValue;
        }
        else if(mode==Mode.TV)
        {
            outgoingMessage = new byte[1];
            outgoingMessage[0]=(byte)4;
        }
        else return;
        send();

        String resultInfo;
        if(ackCame)
        {
            resultInfo="Command sent.";
        }
        else
        {
            resultInfo="Error of connection.";
        }
        Toast toast1 = Toast.makeText(context, resultInfo, Toast.LENGTH_LONG);
        toast1.show();
    }

    private void send()
    {
        boolean isConnected= bluetoothConnection.connect();
        if(isConnected)
        {
            sendMessage();
            bluetoothConnection.disconnect();
        }
    }

    private void sendMessage()
    {
        final int[][] data = {null};
        final int[] counter = {0};
        boolean finish=false;
        ackCame=false;
        while(finish==false)
        {
            data[0] = BluetoothConnection.readData();
            if (data[0] == null)
            {
                BluetoothConnection.sendData(outgoingMessage);
            }
            else
            {
                ackCame=true;
                incomingData=data[0];
                finish=true;
                return;
            }

            if(counter[0]==10)
            {
                ackCame=false;
                finish=true;
                return;
            }
            counter[0]++;

            try {
                sleep(500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
