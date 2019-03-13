package com.mcds5510;

import java.sql.Connection;

import com.mcds5510.connect.ConnectionFactory;
import com.mcds5510.connect.MySQLJDBCConnection;
import com.mcds5510.logging.SimpleLogging;
import com.mcds5510.view.View;

public class Assignment2 extends SimpleLogging {

	public static Connection single_instance;
	public final String outputFile = "C:\\Users\\kshpa\\Documents\\MCDA5510_Assignments\\Assignment2\\Output\\Output.txt";

	public static Connection getInstance() {
		if (single_instance == null) {
			MySQLJDBCConnection dbConnection = new MySQLJDBCConnection();
			single_instance = dbConnection.setupConnection();
		}

		return single_instance;
	}

	public static void main(String[] args) {
		try {
			//Connection connection = getInstance();
			SimpleLogging logIT = new SimpleLogging();
	    	logIT.logFortheProj(); 
			ConnectionFactory factory = new ConnectionFactory();
			Connection connection = factory.getConnection("mySQLJDBC");

            View newView = new View();
            newView.showView(connection);

			if (connection != null) {
				connection.close();
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
