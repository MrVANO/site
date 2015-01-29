package DAO;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import entities.Plan;

public class DatabaseHelper {
	Connection connection;
	InputStream in;
	
	public List<Plan> getPlans() throws ClassNotFoundException, SQLException, IOException{
		List<Plan> plansList = new ArrayList<Plan>();
		openConnection();
		Statement st = connection.createStatement();
		ResultSet rs = st.executeQuery("select * from plans");
		while(rs.next()){
			in = new ByteArrayInputStream(rs.getBytes(3));
			plansList.add(new Plan(rs.getString(2)));
		}
		closeConnection();
		return plansList;
	}
	
	private void openConnection() throws ClassNotFoundException, SQLException{
		DriverManager.registerDriver(new com.mysql.jdbc.Driver());
		connection = DriverManager.getConnection
				("jdbc:mysql://127.7.14.2:3306/wifinavigatorapp", "adminUJBQERY", "TcJcMQEcXnk6"); 
				//("jdbc:mysql://localhost/navigatorbase", "root", "root"); 
	}
	
	private void closeConnection() throws SQLException{
		connection.close();
	}
}
