package com.vano.clientserver;

import java.io.*;
import java.util.Hashtable;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@SuppressWarnings("serial")
public class DataSenderStarter extends HttpServlet{
	 @Override 
	 protected void doGet(HttpServletRequest req, HttpServletResponse res)
			    throws ServletException, IOException
			{


			  ServletOutputStream sos = res.getOutputStream();

			  res.setContentType("text/plain");
			  sos.write("This is the ping servlet".getBytes());

			  sos.flush();
			  sos.close();
			  System.out.println("Get response send");
			}

	@Override 
	protected void doPost(HttpServletRequest request, HttpServletResponse res)
			    throws ServletException, IOException
			{

			  ByteBuffer inputBB = new ByteBuffer(request.getInputStream());
			  ByteBuffer outputBB = null;

			  try {

			    // extract the hashmap
			    System.out.println("get object from request");
			    System.out.println("on IP "+request.getRemoteAddr());
			    ObjectInputStream ois = new ObjectInputStream(inputBB.getInputStream());
			    Object inputObject = ois.readObject();

			    ByteArrayOutputStream baos = new ByteArrayOutputStream();
			    ObjectOutputStream oos = new ObjectOutputStream(baos);
			    if(inputObject instanceof String){
			    	oos.writeObject(Main.getNavigationData((String)inputObject));
			    	outputBB = new ByteBuffer(baos.toByteArray());
				    System.out.println("sent response back...");
				    ServletOutputStream sos = res.getOutputStream();
					  if (outputBB != null) {
					    res.setContentType("application/octet-stream");
					    res.setContentLength(outputBB.getSize());
					    sos.write(outputBB.getBytes());
					  }
					  else {
					    res.setContentType("application/octet-stream");
					    res.setContentLength(inputBB.getSize());
					    sos.write(inputBB.getBytes());
					  }

					  sos.flush();
					  sos.close();				    
				} else
					if (inputObject instanceof NavigationData) {
						Main.saveData((NavigationData)inputObject);
						
						System.out.println("Data saved!");
					} else{
						System.out.println("Unknown type!");
					}
			  }
			  catch (Exception e) {
			    System.out.println(e);
			    e.printStackTrace();
			  }

		}
	 
}
