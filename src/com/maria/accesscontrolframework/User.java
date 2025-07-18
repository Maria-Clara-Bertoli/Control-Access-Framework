package com.maria.accesscontrolframework;

import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class User {
	
	private int id;
	private String name;
	private String password;
	private static User user;
	public static ConnectionExecution connectionExecution;
	
	public User id(int id) {
		this.id = id;
		return this;
	}
	
	public User name(String name) {
		this.name = name;
		return this;
	}
	
	public User password(String password) {
		this.password = password;
		return this;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean insert() {

	    String checkNameSql = "SELECT 1 FROM user WHERE name = ?";
	    String checkPasswordSql = "SELECT 1 FROM user WHERE password = ?";
	    String insertSql = "INSERT INTO user (name, password) VALUES (?, ?)";

	    try (
	        Connection connection = connectionExecution.getConnection();
	        Statement statement = connection.createStatement();
	    		
	        PreparedStatement checkNameStatement = connection.prepareStatement(checkNameSql);
	        PreparedStatement checkPasswordStatement = connection.prepareStatement(checkPasswordSql);
	        PreparedStatement insertStatement = connection.prepareStatement(insertSql)
	    ) {
	        statement.execute(String.format("USE %s;", connectionExecution.getDatabaseName()));

	        checkNameStatement.setString(1, this.name);
	        ResultSet nameResult = checkNameStatement.executeQuery();

	        if (nameResult.next()) {
	            System.out.println("Este nome de usuário já está em uso. Por favor, escolha outro.");
	            return false;
	        }

	        checkPasswordStatement.setString(1, this.password);
	        ResultSet passwordResult = checkPasswordStatement.executeQuery();

	        if (passwordResult.next()) {
	            System.out.println("Esta senha já está em uso. Por favor, escolha outra.");
	            return false;
	        }

	        insertStatement.setString(1, this.name);
	        insertStatement.setString(2, this.password);
	        insertStatement.executeUpdate();

	        return true;
	    } 
	    catch (Exception error) {
	        error.printStackTrace();
	    }
	    return false;
	}
	
	public User search(String userName) {

	    String sql = "SELECT id, name, password FROM user WHERE name = ?";

	    try (
	        Connection connection = connectionExecution.getConnection();
	    		
	        Statement statement = connection.createStatement();
	        PreparedStatement preparedStatement = connection.prepareStatement(sql)
	    ) {
	        statement.execute(String.format("USE %s;", connectionExecution.getDatabaseName()));

	        preparedStatement.setString(1, userName);

	        ResultSet resultSet = preparedStatement.executeQuery();

	        if (resultSet.next()) {
	        	
	            User user = new User();
	            user.id = resultSet.getInt("id");
	            user.name = resultSet.getString("name");
	            user.password = resultSet.getString("password");
	            
	            return user;
	        } 
	        else {
	            return null;
	        }
	    } 
	    catch (Exception error) {
	        error.printStackTrace();
	        return null;
	    }
	}
	
	public static User getUser() {
		
		if(user == null) {
			user = new User();
		}
		return user;
	}
}
