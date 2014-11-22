package com.vano.clientserver;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;


@SuppressWarnings("serial")
public class DataSenderStarter extends HttpServlet{
	 public void init() throws ServletException
	    {
		 	Main main = new Main();
		 	Thread thread = new Thread(main);  
	        thread.start();
	    }
}
