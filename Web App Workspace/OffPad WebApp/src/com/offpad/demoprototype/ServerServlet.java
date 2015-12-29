package com.offpad.demoprototype;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Writer;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ServerServlet
 */
@WebServlet("/ServerServlet")
public class ServerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ServerServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

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

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		// Set response content type


		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		String title = "Server Side Data Handling .....";
		String destination=request.getParameter("destination").toString();
		String amount=request.getParameter("amount").toString();
		String hash=request.getParameter("hash");
		String received_hash="something";

		//Here is the decryption part..Sijan Gurung
		int p=0,lo=0;
		for(int i=0;i< hash.length() ;i++){
			if(hash.charAt(i)=='+'){
				p++;
				lo=i;
			}
			if(p == 2){
				received_hash= hash.substring(lo+1,hash.length());
				System.out.println("Received Hash = " + received_hash);
				break;
			}
		}
		String generatedHash = md5(destination+amount);
		Boolean isAuthentic = false;
		System.out.println("Generated Hash from client Value = "+ generatedHash);

		if(received_hash.equals( generatedHash )){
			isAuthentic = true;
		}
		else{
			isAuthentic = false;
		}
		
		/*Calling the request*/
		/*MAKING A CALL TO JSP*/
		request.setAttribute("destination", destination);
		request.setAttribute("amount", amount);
		
		request.setAttribute("isAuthentic", isAuthentic);
		request.setAttribute("generatedHash", generatedHash);
		request.setAttribute("received_hash", received_hash);

		RequestDispatcher view = request.getRequestDispatcher("serverPage.jsp");
		view.forward(request, response);

	}
	//THIS FUNCTION IS FOR SHOWING IMAGES.....
	public void show_image(String path_to_Show,HttpServletResponse resp){
		ServletContext cntx= getServletContext();
		// Get the absolute path of the image
		String filename = cntx.getRealPath(path_to_Show);
		// retrieve mimeType dynamically
		String mime = cntx.getMimeType(filename);
		if (mime == null) {
			resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			return;
		}

		resp.setContentType(mime);
		File file = new File(filename);
		resp.setContentLength((int)file.length());

		FileInputStream in = null;
		OutputStream out = null;
		try {
			in = new FileInputStream(file);
			out = resp.getOutputStream();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Copy the contents of the file to the output stream
		byte[] buf = new byte[1024];
		int count = 0;
		try {
			while ((count = in.read(buf)) >= 0) {
				out.write(buf, 0, count);
				out.close();
				in.close();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


	}//end of the function.....
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//After this page is done... remove the values
		//This is for writing dummy values...after wards...
		String desktop = System.getProperty("user.home") + "/Desktop/";
		BufferedWriter writer = null;
		Writer write_file = new FileWriter(desktop+"offpad.txt");
        writer = new BufferedWriter(write_file);
        writer.write("Not_Received_Yet+000000+testing...");
        writer.close();
	}

}