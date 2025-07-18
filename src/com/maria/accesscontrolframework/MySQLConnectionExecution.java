package com.maria.accesscontrolframework;

import java.sql.Connection;
import java.sql.DriverManager;

public class MySQLConnectionExecution extends ConnectionExecution{
	
	public MySQLConnectionExecution port(int port) {
		super.port = port;
		return this;
    }
	
	public MySQLConnectionExecution host(String host) {
		super.host = host;
		return this;
    }
	
	public MySQLConnectionExecution userName(String userName) {
		super.userName = userName;
		return this;
    }
	
	public MySQLConnectionExecution password(String password) {
		super.password = password;
		return this;
    }
	
	public MySQLConnectionExecution databaseName(String databaseName) {
		super.databaseName = databaseName;
		return this;
    }
	
	@Override
	public Connection getConnection() {
		try {
			String driver = "com.mysql.jdbc.Driver";
			
			String url = String.format("jdbc:mysql://%s/", this.host);
					
    		Class.forName(driver);
    		
            return DriverManager.getConnection(url, this.userName, this.password);
        } 
		catch (Exception error) {
        	error.printStackTrace();
            return null;
        }
    }
}
