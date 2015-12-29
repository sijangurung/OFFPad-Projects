package com.offpad.testoffpad;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

/**
 * Created by djlophu on 13/09/15.
 */
public class Options extends Activity {
    private BluetoothAdapter BA;
     Button btnTransfer, btnBlueToothToogle, btnAbout, btnHistory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        //others methods....
        BA = BluetoothAdapter.getDefaultAdapter();
        btnTransfer = (Button) findViewById (R.id.btnTransfer);
        btnBlueToothToogle = (Button) findViewById (R.id.btnBluetoothToogle);
        btnAbout = (Button) findViewById (R.id.btnAbout);
        btnHistory = (Button) findViewById (R.id.btnHistory);

       btnTransfer.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent newIntent = new Intent(Options.this,MainActivity.class);
               startActivity(newIntent);
           }
       });
        //About page...
        btnAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Intent ourIntent = new Intent(Options.this,AsymmetricAlgorithmRSA.class);
                    startActivity(ourIntent);
            }
        });

        //for Bluetooth toogle..
        btnBlueToothToogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!BA.isEnabled()) {
                    Intent turnOn = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                    startActivityForResult(turnOn, 0);
                    Toast.makeText(getApplicationContext(), "Turned on", Toast.LENGTH_LONG).show();
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"Already on", Toast.LENGTH_LONG).show();
                }
            }
        });
        //For History
        btnHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ourIntent = new Intent(Options.this,History.class);
                startActivity(ourIntent);
            }
        });

    }
    public void Quit(View v){
        super.finish();
    }
}