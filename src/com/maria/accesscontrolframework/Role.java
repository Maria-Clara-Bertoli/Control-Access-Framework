package com.maria.accesscontrolframework;

import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class Role {
    
	private int id;
    private String roleType;
    public static ConnectionExecution connectionExecution;
    
    public Role id(int id) {
		this.id = id;
		return this;
	}

    public Role roleType(String roleType) {
        this.roleType = roleType;
        return this;
    }
    
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getRoleType() {
		return roleType;
	}

	public void setRoleType(String roleType) {
		this.roleType = roleType;
	}
	
	public boolean insert() {

	    String checkSql = "SELECT 1 FROM role WHERE role_type = ?";
	    String insertSql = "INSERT INTO role (role_type) VALUES (?)";

	    try (
	        Connection connection = connectionExecution.getConnection();
	        Statement statement = connection.createStatement();
	    		
	        PreparedStatement checkStatement = connection.prepareStatement(checkSql);
	        PreparedStatement insertStatement = connection.prepareStatement(insertSql)
	    ) {
	        statement.execute(String.format("USE %s;", connectionExecution.getDatabaseName()));

	        checkStatement.setString(1, this.roleType);
	        ResultSet resultSet = checkStatement.executeQuery();

	        if (resultSet.next()) {
	            System.out.println("Este papel já existe no banco de dados.");
	            return false;
	        }

	        insertStatement.setString(1, this.roleType);
	        
	        insertStatement.executeUpdate();
	        
	        return true;
	    } 
	    catch (Exception error) {
	        error.printStackTrace();
	    }
	    return false;
	}
	
	public Role search(String roleType) {

	    String sql = "SELECT id, role_type FROM role WHERE role_type = ?";

	    try (
	        Connection connection = connectionExecution.getConnection();
	        Statement statement = connection.createStatement();
	    		
	        PreparedStatement preparedStatement = connection.prepareStatement(sql)
	    ) {
	        statement.execute(String.format("USE %s;", connectionExecution.getDatabaseName()));

	        preparedStatement.setString(1, roleType);

	        ResultSet resultSet = preparedStatement.executeQuery();

	        if (resultSet.next()) {
	        	
	            Role role = new Role();
	            role.id = resultSet.getInt("id");
	            role.roleType = resultSet.getString("role_type");

	            return role;
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
}
