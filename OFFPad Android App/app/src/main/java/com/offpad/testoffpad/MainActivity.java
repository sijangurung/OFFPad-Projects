package com.offpad.testoffpad;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.offpad.testoffpad.Models.Transactions;

import java.io.IOException;
import java.io.OutputStream;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.util.Date;
import java.util.TimeZone;
import java.util.UUID;

public class MainActivity extends Activity {
    TextView out,txtMessage;
    EditText txtDestNo,txtAmount;
    Button btnSend;

    private static final int REQUEST_ENABLE_BT = 1;
    private BluetoothAdapter btAdapter = null;
    private BluetoothSocket btSocket = null;
    private OutputStream outStream = null;

    //for saving for History...
    Transactions newTransaction = new Transactions();

    // Well known SPP UUID
    private static final UUID MY_UUID =
            UUID.fromString("8ce255c0-200a-11e0-ac64-0800200c9a66");

    // Insert your server's MAC address
    private static String address = "28:CF:DA:D5:00:C5";

    //This is for generation of the hash......
    public static String md5(String input) {
        String md5 = null;
        if(null == input) return null;
        try {
            //Create MessageDigest object for MD5
            MessageDigest digest = MessageDigest.getInstance("MD5");
            //Update input string in message digest
            digest.update(input.getBytes(), 0, input.length());
            //Converts message digest value in base 16 (hex)
            md5 = new BigInteger(1, digest.digest()).toString(16);
        } catch (NoSuchAlgorithmException e) {

            e.printStackTrace();
        }
        return md5;
    }//end of the function.....


    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.transfer);
        out = (TextView) findViewById(R.id.out);
        txtDestNo = (EditText) findViewById(R.id.txtDestNo);
        txtAmount = (EditText) findViewById(R.id.txtAmount);
        //error message field..
        txtMessage = (TextView) findViewById(R.id.txtMessage);
        txtMessage.setVisibility(View.GONE);


        btnSend=(Button) findViewById(R.id.btnSend);
        out.append("\n...Logs Message are here !...");
        out.append("\n...In onCreate()...");

        btAdapter = BluetoothAdapter.getDefaultAdapter();
        CheckBTState();
        // TODO Auto-generated method stub
        out.append("\n...onCreate()...\n...Attempting client connect...");

        // Set up a pointer to the remote node using it's address.
        BluetoothDevice device = btAdapter.getRemoteDevice(address);

        // Two things are needed to make a connection:
        //   A MAC address, which we got above.
        //   A Service ID or UUID.  In this case we are using the
        //     UUID for SPP.
        try {
            btSocket = device.createRfcommSocketToServiceRecord(MY_UUID);
        } catch (IOException e) {
            AlertBox("Fatal Error", "In onResume() and socket create failed: " + e.getMessage() + ".");
        }


    }
    public boolean checkField(TextView tv){
        out.append("\n...Checking validation...");
        if( (tv.getText().toString().length() < 1) || (tv.getText().toString() == null) )
        {
            txtMessage.setVisibility(View.VISIBLE);
            return false;
        }
        return true;
    }

    @Override
    public void onStart() {
        super.onStart();
        out.append("\n...In onStart()...");

    }

    @SuppressLint("NewApi") @Override
    public void onResume() {
        super.onResume();
        //Here shoudl be the original code...
        btnSend.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                //Check the fields validation first...
                if(!checkField(txtDestNo)) {
                    txtMessage.setText("* Destination Account field is empty!");
                    out.append("\n...Destination Field empty!...");
                    return;
                }
                if(!checkField(txtAmount))
                {
                    txtMessage.setText("* Amount field is empty!");
                    out.append("\n...Amount Field is empty!...");
                    return;
                }
                out.append("\n...Validation passed !");


                // Discovery is resource intensive.  Make sure it isn't going on
                // when you attempt to connect and pass your message.
                btAdapter.cancelDiscovery();

                // Establish the connection.  This will block until it connects.
                try {
                    btSocket.connect();
                    out.append("\n...Connection established and data link opened...");
                } catch (IOException e) {
                    try {
                        btSocket.close();
                    } catch (IOException e2) {
                        AlertBox("Fatal Error", "In onResume() and unable to close socket during connection failure" + e2.getMessage() + ".");
                    }
                }

                // Create a data stream so we can talk to server.
                out.append("\n...Sending message to server...");

                try {
                    outStream = btSocket.getOutputStream();
                } catch (IOException e) {
                    AlertBox("Fatal Error", "In onResume() and output stream creation failed:" + e.getMessage() + ".");
                }

                //Here do the encoding of the messages to send....
		    /*
		     FORMAT ..
		     message =  destination number+amount+hashmessage;
		     */
                String destination=null,amount=null;
                destination=txtDestNo.getText().toString();
                amount=txtAmount.getText().toString();
                //adding to the message string for sending....
                String message = destination;
                message+="+" +amount ;
                message+="+"+md5(destination+amount);

                /* HERE WE DO THE ENCODING OF THE WHOLE STRING ...*/
                String encodedMessage  = AsymmetricAlgorithmRSA.encodeMessageWithKey(message);

                //Now write to the database for history purpose...
                //Getting DATE and TIME ....
                DateFormat df = DateFormat.getDateTimeInstance();
                df.setTimeZone(TimeZone.getTimeZone("UTC"));
                String gmtTime = df.format(new Date());

                newTransaction.Date = gmtTime;
                newTransaction.Destination_Number = destination;
                newTransaction.amount = amount;
                try {
                    System.out.println("md5(dest+amt):"+md5(destination + amount));
                    outStream.write(message.getBytes());
                    System.out.println("Encoded Message"+encodedMessage);
                    newTransaction.status = "success";
                    out.append("\n...Message Sent!!!");
                    //close the socket.....
                    outStream.flush();
                    outStream.close();
                    btSocket.close();
                    //Call oncreate here..
                    onCreate(new Bundle());

                } catch (IOException e) {
                    String msg = "In onResume() and an exception occurred during write: " + e.getMessage();
                    //setting status to fail..
                    newTransaction.status = "failed";
                    out.append("\n...Message Sending failed!");
                    //try the fallback
                    try {

                    } catch (Exception e1) {
                        msg="BT:Could not initialize FallbackBluetoothSocket classes."+ e1;
                    }

                    if (address.equals("28:CF:DA:D5:00:C5"))
                        msg = msg + ".\n\nUpdate your server address from 28:CF:DA:D5:00:C5 to the correct address on line 37 in the java code";
                    msg = msg +  ".\n\nCheck that the SPP UUID: " + MY_UUID.toString() + " exists on server.\n\n";

                    AlertBox("Fatal Error", msg);
                }finally{
                    //saving the transactions....
                    newTransaction.save();
                    out.append("\n...Message Saved !!!");

                }

            }

        });
        this.closeContextMenu();
    }

    @Override
    public void onPause() {
        super.onPause();

        out.append("\n...In onPause()...");

        if (outStream != null) {
            try {
                outStream.flush();
            } catch (IOException e) {
                AlertBox("Fatal Error", "In onPause() and failed to flush output stream: " + e.getMessage() + ".");
            }
        }

    }

    @Override
    public void onStop() {
        super.onStop();
        out.append("\n...In onStop()...");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        out.append("\n...In onDestroy()...");
    }

    private void CheckBTState() {
        // Check for Bluetooth support and then check to make sure it is turned on

        // Emulator doesn't support Bluetooth and will return null
        if(btAdapter==null) {
            AlertBox("Fatal Error", "Bluetooth Not supported. Aborting.");
        } else {
            if (btAdapter.isEnabled()) {
                out.append("\n...Bluetooth is enabled...");
            } else {
                //Prompt user to turn on Bluetooth
                Intent enableBtIntent = new Intent(btAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
            }
        }
    }

    public void AlertBox( String title, String message ){
        new AlertDialog.Builder(this)
                .setTitle( title )
                .setMessage( message + " Press OK to exit." )
                .setPositiveButton("OK", new OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                        finish();
                    }
                }).show();
    }
    public void Back(View v){
        this.finish();
    }
}