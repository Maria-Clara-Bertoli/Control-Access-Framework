package com.maria.accesscontrolframework;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class Permission {
	
	private int id;
	private String permissionType;
	private User user = User.getUser();
	public static ConnectionExecution connectionExecution;

	public Permission id(int id) {
		this.id = id;
		return this;
	}
	
	public Permission permissionType(String permissionType) {
		this.permissionType = permissionType;
		return this;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPermissionType() {
		return permissionType;
	}

	public void setPermissionType(String permissionType) {
		this.permissionType = permissionType;
	}

	public boolean insert() {

	    String checkSql = "SELECT 1 FROM permission WHERE permission_type = ?";
	    String insertSql = "INSERT INTO permission (permission_type) VALUES (?)";

	    try (
	        Connection connection = connectionExecution.getConnection();
	        Statement statement = connection.createStatement();
	    		
	        PreparedStatement checkStatement = connection.prepareStatement(checkSql);
	        PreparedStatement insertStatement = connection.prepareStatement(insertSql)
	    ) {
	        statement.execute(String.format("USE %s;", connectionExecution.getDatabaseName()));

	        checkStatement.setString(1, this.permissionType);
	        ResultSet resultSet = checkStatement.executeQuery();

	        if (resultSet.next()) {
	            System.out.println("Esta permissão já existe no banco de dados.");
	            return false;
	        }
	        
	        insertStatement.setString(1, this.permissionType);
	        
	        insertStatement.executeUpdate();
	        
	        return true;
	    } 
	    catch (Exception error) {
	        error.printStackTrace();
	    }
	    return false;
	}
	
	public Permission search(String permissionType) {

	    String sql = "SELECT id, permission_type FROM permission WHERE permission_type = ?";

	    try (
	        Connection connection = connectionExecution.getConnection();
	    		
	        Statement statement = connection.createStatement();
	        PreparedStatement preparedStatement = connection.prepareStatement(sql)
	    ) {
	        statement.execute(String.format("USE %s;", connectionExecution.getDatabaseName()));

	        preparedStatement.setString(1, permissionType);

	        ResultSet resultSet = preparedStatement.executeQuery();

	        if (resultSet.next()) {
	            Permission permission = new Permission();
	            permission.id = resultSet.getInt("id");
	            permission.permissionType = resultSet.getString("permission_type");

	            return permission;
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
	
	public ArrayList<Permission> searchList() {
		
	    ArrayList<Permission> permissionList = new ArrayList<Permission>();

	    String sql = "SELECT DISTINCT permission.id, permission.permission_type " +
	    	    	 "FROM user " +
	    	    	 "JOIN user_role ON user.id = user_role.user_id " +
	    	    	 "JOIN role ON user_role.role_id = role.id " +
	    	    	 "JOIN role_permission ON role.id = role_permission.role_id " +
	    	    	 "JOIN permission ON role_permission.permission_id = permission.id " +
	    	    	 "WHERE user.name = ?";

	    try (
	        Connection connection = connectionExecution.getConnection();
	    		
	        Statement statement = connection.createStatement();
	        PreparedStatement preparedStatement = connection.prepareStatement(sql)
	    ) {
	        statement.execute(String.format("USE %s;", connectionExecution.getDatabaseName()));

	        preparedStatement.setString(1, this.user.getName());

	        ResultSet resultSet = preparedStatement.executeQuery();

	        while (resultSet.next()) {
	            Permission permission = new Permission();
	            permission.id = resultSet.getInt("id");
	            permission.permissionType = resultSet.getString("permission_type");

	            permissionList.add(permission);
	        }
	    } 
	    catch (Exception error) {
	        error.printStackTrace();
	    }

	    return permissionList;
	}
}
