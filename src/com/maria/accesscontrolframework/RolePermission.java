package com.maria.accesscontrolframework;

import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class RolePermission {
	
	private int id;
	public static ConnectionExecution connectionExecution;
	
	public RolePermission id(int id) {
		this.id = id;
		return this;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public boolean insert(String roleType, String permissionType) {

	    Role role = new Role()
	        .search(roleType);

	    Permission permission = new Permission()
	        .search(permissionType);

	    if (role == null) {
	        System.out.println("Este papel não pode ser encontrado no banco de dados.");
	        return false;
	    }

	    if (permission == null) {
	        System.out.println("Esta permissão não pode ser encontrada no banco de dados.");
	        return false;
	    }

	    String checkSql = "SELECT 1 FROM role_permission WHERE role_id = ? AND permission_id = ?";
	    String insertSql = "INSERT INTO role_permission (role_id, permission_id) VALUES (?, ?)";

	    try (
	        Connection connection = connectionExecution.getConnection();
	        Statement statement = connection.createStatement();
	    		
	        PreparedStatement checkStatement = connection.prepareStatement(checkSql);
	        PreparedStatement insertStatement = connection.prepareStatement(insertSql)
	    ) {
	        statement.execute(String.format("USE %s;", connectionExecution.getDatabaseName()));

	        checkStatement.setInt(1, role.getId());
	        checkStatement.setInt(2, permission.getId());
	        ResultSet result = checkStatement.executeQuery();

	        if (result.next()) {
	            System.out.println("Esta associação já existe no banco de dados.");
	            return false;
	        }
	        
	        insertStatement.setInt(1, role.getId());
	        insertStatement.setInt(2, permission.getId());
	        insertStatement.executeUpdate();
	        
	        return true;
	    } 
	    catch (Exception error) {
	        error.printStackTrace();
	    }
	    return false;
	}
}
