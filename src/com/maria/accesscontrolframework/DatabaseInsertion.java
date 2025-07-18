package com.maria.accesscontrolframework;

import java.io.File;
import java.io.FileReader;
import java.sql.Statement;
import java.sql.Connection;
import java.io.BufferedReader;

public class DatabaseInsertion {
	
	private String filePath;
	public static ConnectionExecution connectionExecution;
	
	public DatabaseInsertion filePath(String filePath) {
		this.filePath = filePath;
		return this;
	}
	
	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public boolean executeScript() {
		
		String line;
		
	    try (
	        Connection connection = connectionExecution.getConnection();
	        Statement statement = connection.createStatement()
	    ) {
	    	File file = new File(this.filePath);

	        statement.execute(String.format("USE %s;", connectionExecution.getDatabaseName()));

	        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));

	        while ((line = bufferedReader.readLine()) != null) {
	        	
	            line = line.trim();
	            statement.execute(line);
	        }
	        bufferedReader.close();
	        connection.close();
	        
	        return true;
	    } 
	    catch (Exception error) {
	        error.printStackTrace();
	    }
	    return false;
	}
}
