package com.offpad.demoprototype;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.math.BigInteger;
import java.security.GeneralSecurityException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPublicKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.codec.binary.Base64;

import sun.misc.BASE64Encoder;
/**
 * Servlet implementation class clientBrowser
 */
@WebServlet("/ClientServlet")
public class ClientServlet extends HttpServlet {
	// This would be filled from the another class which would be running in the
	// background....

	private static final long serialVersionUID = 1L;
	public static String hashReceived = "nothing", rDest = "Not_Received_Yet", rAmt = "000000";

	/**
	 * @throws NoSuchAlgorithmException 
	 * @throws InvalidKeySpecException 
	 * @see HttpServlet#HttpServlet()
	 */
	public ClientServlet() throws NoSuchAlgorithmException, InvalidKeySpecException {
		super();
		
		// TODO Auto-generated constructor stub
		String desktop = System.getProperty("user.home") + "/Desktop/";
		try {
			
			Reader file= null;
			file = new FileReader(desktop + "offpad.txt");
			System.out.println("File reading successful!");
			BufferedReader buff = new BufferedReader(file);
			hashReceived = buff.readLine();
			buff.close();
			
		} catch (IOException e) {
			//Create the file
			desktop = desktop+"offpad.txt";
			File f = new File(desktop);
			//e.printStackTrace();
		}

		System.out.println("total Received:"+hashReceived);

//		String decodedString = this.decrypt(decodedMessage,publicKey);
		// now decrypt it to fill in the forms auto...
		// Here is the decryption part..Sijan Gurung
		int p = 0, lo = 0;
		for (int i = 0; i < hashReceived.length(); i++) {
			if (hashReceived.charAt(i) == '+') {
				p++;
				if(p == 1){
					lo = i;
				rDest = hashReceived.substring(0, i);
				}else if (p == 2) {
					rAmt = hashReceived.substring(lo + 1, i);
					break;
				}
			}
		}
	
		
	
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		/*
		 * // we do not set content type, headers, cookies etc. //
		 * resp.setContentType("text/html"); // while redirecting as // it would
		 * most likely result in an IllegalStateException
		 * 
		 * // "/" is relative to the context root (your web-app name)
		 * RequestDispatcher view =
		 * request.getRequestDispatcher("/WEB-INF/html/index.html"); // don't
		 * add your web-app name to the path
		 * 
		 * view.forward(request, response);
		 */
		// Set response content type
		try {
			performTask(request, response);
		} catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // This method reloads this page once in
										// 15 seconds....
		response.setContentType("text/html");

		request.setAttribute("rDest", rDest);
		request.setAttribute("rAmt", rAmt);
		request.setAttribute("hashReceived", hashReceived);

		RequestDispatcher view = request.getRequestDispatcher("clientPage.jsp");
		view.forward(request, response);

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		// After this page is done... remove the values
		// This is for writing dummy values...after wards...
		String desktop = System.getProperty("user.home") + "/Desktop/";
		BufferedWriter writer = null;
		Writer write_file = new FileWriter(desktop + "offpad.txt");
		writer = new BufferedWriter(write_file);
		writer.write("Not_Received_Yet+000000+testing...");
		writer.close();
	}

	private void performTask(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, NoSuchAlgorithmException, InvalidKeySpecException {
		response.setContentType("text/html");
		response.addHeader("Refresh", "15");
		new ClientServlet();
	}

}
