package com.vano.clientserver;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import java.sql.* ;

import com.mysql.jdbc.Driver ;

import java.io.*;


public class Main {
	private static ServerSocket serverSocket;
	private static Socket clientSocket;
	private static int columnCount=0;
	private static ObjectInputStream deserializer;
	private static ObjectOutputStream serializer;
	private static Connection connection;

	private static void waitToClient() throws ClassNotFoundException, SQLException{
		try {
			System.out.println("Waiting to client...");	
			clientSocket = serverSocket.accept();
			System.out.println("Client connected!");		
			sendTabletoClient();
			clientSocket.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	
	/*
	 * �������������� ����� ��� �������� �� ����
	 */
	
	public static NavigationData getNavigationData(String numberOfPlace) throws ClassNotFoundException, SQLException, IOException{
		openConnection();
		NavigationData nd = new NavigationData();
		Statement st = connection.createStatement();
		ResultSet rs = st.executeQuery("select * from "+numberOfPlace);
		
		ResultSetMetaData rsmd = rs.getMetaData();
		ArrayList<String> SSID = new ArrayList<String>();
		columnCount=rsmd.getColumnCount();
		for (int i=1; i<=columnCount; i++){		
			SSID.add(rsmd.getColumnName(i));
		}
		ArrayList<Integer[]> coordinats = new ArrayList<Integer[]>();
		ArrayList<ArrayList<Integer>> signals = new ArrayList<ArrayList<Integer>>();
		while (rs.next())
		{
		coordinats.add(new Integer[]{rs.getInt(2), rs.getInt(3)});
		ArrayList<Integer> rowSignals = new ArrayList<Integer>();
			for (int i=4; i<=columnCount; i++){
				rowSignals.add(rs.getInt(i));
			}
		signals.add(rowSignals);
		}
		nd.setCoordinats(coordinats);
		nd.setSignals(signals);
        nd.setSSID(SSID);
        nd.setTableName(numberOfPlace);
        ResultSet rs2 = st.executeQuery("select PlanImage from plans where tableName='"+numberOfPlace+"'");
        while (rs2.next())
		{
        	nd.setPlaceMap(rs2.getBytes(1));
		}
        closeConnection();
        nd.setPlaceMap(getImageInBytes(numberOfPlace));
		return nd;
	}
	
	/*
	 * �������� ��������������� ����� NavigationData �������
	 * ���
	 * ���������� � �� ��������� ������
	 */
	private static void sendTabletoClient() throws IOException, ClassNotFoundException, SQLException{
		deserializer = new ObjectInputStream(clientSocket.getInputStream());
		serializer = new ObjectOutputStream(clientSocket.getOutputStream());
		Object inputObject = deserializer.readObject();
		if(inputObject instanceof String){
			serializer.writeObject(getNavigationData((String)inputObject));
	        serializer.flush();	
	        System.out.println("Data sending!");
		} else
			if (inputObject instanceof NavigationData) {
				saveData((NavigationData)inputObject);
				
				System.out.println("Data saved!");
			} else{
				System.out.println("Unknown type!");
			}
		
		
	}
	
	/*
	 * ������� ��������������� ����������� �� �������
	 * � ��������� ��� � ������� ������
	 */
	private static byte[] getImageInBytes(String imageName) throws IOException, ClassNotFoundException, SQLException{
		/*
		 * �������� ����� ����� �� ����������� ����������� � �����, 
		 * � ����� ��� �� ��, ��� ��� ����� ��������� � ����
		 * ������� ������
		 */
		
		openConnection();
	 	Statement st = connection.createStatement();
	 	ResultSet image = st.executeQuery("SELECT PlanImage FROM plans WHERE TableName='"+imageName+"'");
	 	image.next();
	 	byte[] temp = image.getBytes(1);
	 	closeConnection();
	 	return temp;
	}
	
	
	/*
	 * ��������� ������ (������� ������������ ������� �������� ����������� 
	 * � ����������� ����� �����. 
	 */
	public static void saveData(NavigationData tableDB) throws IOException, SQLException, ClassNotFoundException{
	 	   openConnection();
	 	   String CreateTablesquery = "CREATE TABLE IF NOT EXISTS "+tableDB.getTableName() +" (id INTEGER PRIMARY KEY AUTO_INCREMENT, x NUMERIC, y NUMERIC";
	 	   for (int i=3; i<tableDB.getSSID().size(); i++)
	 		   CreateTablesquery+=", "+ tableDB.getSSID().get(i) + " NUMERIC";
	 	   CreateTablesquery+=");";
	 	   Statement st = connection.createStatement();
	 	   st.execute(CreateTablesquery);
	 	   String checkTableExist = "SELECT * FROM "+tableDB.getTableName()+";";
	 	   ResultSet rs = st.executeQuery(checkTableExist);
	 	   if(!rs.next()){
		 	   for(int i=0;i<tableDB.getCoordinats().size(); i++)
		 	   {
		 		   String insertQueryPoints="INSERT INTO "+tableDB.getTableName()+" VALUES ("+(i+1)+","+ tableDB.getCoordinats().get(i)[0] + "," + tableDB.getCoordinats().get(i)[1];
		 		   for (int j=0; j<tableDB.getSSID().size()-3; j++)
		 			   insertQueryPoints+=","+ tableDB.getSignals().get(i).get(j);
		 		   insertQueryPoints+=");";				  
		 		   st.execute(insertQueryPoints);
		 	   }
	 	   }
	 	 ResultSet countOfRows = st.executeQuery("SELECT COUNT(*) FROM plans");
	 	 countOfRows.next();
	 	 int count = countOfRows.getInt(1);
	 	 PreparedStatement stmt = connection.prepareStatement("INSERT INTO plans values ("+ ++count +", ?, ?)"); //�������� ��� ��������� �� ���������� ������
	 	 stmt.setString(1, tableDB.getTableName());
	 	 stmt.setBytes(2, tableDB.getPlaceMap());
	 	 stmt.execute();
	 	 closeConnection();
	    }
	
	
	private static void openConnection() throws ClassNotFoundException, SQLException{
		DriverManager.registerDriver(new com.mysql.jdbc.Driver());
		connection = DriverManager.getConnection
				("jdbc:mysql://127.7.14.2:3306/wifinavigatorapp", "adminUJBQERY", "TcJcMQEcXnk6"); 
	}
	
	private static void closeConnection() throws SQLException{
		connection.close();
	}
	
}

