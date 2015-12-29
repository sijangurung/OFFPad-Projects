package com.offpad.bluetoothClient;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.bluetooth.UUID;
import javax.microedition.io.Connector;
import javax.microedition.io.StreamConnection;
import javax.microedition.io.StreamConnectionNotifier;

public class RFCOMMServer {


	private static final String TAG = null;

	public  RFCOMMServer(){
		try {

	   StreamConnectionNotifier service = 
                    (StreamConnectionNotifier) Connector.open("btspp://localhost:" 
                            + new UUID("8ce255c0200a11e0ac640800200c9a66",
			false).toString() + ";name=Sample SPP Server");

	    StreamConnection conn = 		     // block for connect
                     (StreamConnection) service.acceptAndOpen();

	    System.out.println("Connected");


        //read string from spp client
        InputStream inStream=conn.openInputStream();
        BufferedReader bReader=new BufferedReader(new InputStreamReader(inStream));
        String lineRead="";
        String txtMessage="";
        //Reading the whole line coming...
        while( (lineRead = bReader.readLine() ) != null )
        {  
        		txtMessage+= lineRead;
        		//lineRead = bReader.readLine();
        }
        if(txtMessage.length() < 1){
        	System.out.println("no MSG received!");
        }
        else
        {
        	
        	System.out.println(txtMessage);
                //Now here comes the sending it to server part..
      
        		String desktop = System.getProperty ("user.home") + "/Desktop/";
        		File myFile = new File (desktop + "offpad.txt");
        		BufferedWriter offpad_txt = new BufferedWriter(new FileWriter(myFile)); 
        		offpad_txt.write(txtMessage);
        		offpad_txt.close();
        
        }

 // send response to spp client
//        OutputStream outStream=conn.openOutputStream();
//        PrintWriter pWriter=new PrintWriter(new OutputStreamWriter(outStream));
//        pWriter.write("Response String from SPP Server\r\n");
//        pWriter.flush();
//        pWriter.close();

        
	    conn.close();
	    service.close();

	} catch (IOException e) {	System.err.print(e.toString());   }
//	return messages;
     }//end of 

	public static void main(String args[]) {	
		 new RFCOMMServer();
	}
/*	
	private String decodeMessageWithKey(String messageString){
		String decodedMessage="";
		// Get public key from string for decryption
        Key publicKey = null;

        // Decode the encoded data with RSA public key
        byte[] decodedBytes = null;
        try {
            Cipher c = Cipher.getInstance("RSA");
            c.init(Cipher.DECRYPT_MODE, publicKey);
            decodedBytes = c.doFinal(messageString);
        } catch (Exception e) {
            Log.e(TAG, "RSA decryption error");
        }
		
		decodedMessage =new String(decodedBytes);

		
		return decodedMessage;
		
	} */

}