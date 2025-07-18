package com.maria.accesscontrolframework;

import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class UserRole {
	
	private int id;
    public static ConnectionExecution connectionExecution;
    
    public UserRole id(int id) {
		this.id = id;
		return this;
	}
    
    public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public boolean insert(String userName, String roleType) {

        User user = new User()
            .search(userName);

        Role role = new Role()
            .search(roleType);

        if (user == null) {
            System.out.println("Este usuário não pode ser encontrado no banco de dados.");
            return false;
        }

        if (role == null) {
            System.out.println("Este papel não pode ser encontrado no banco de dados.");
            return false;
        }

        String checkSql = "SELECT 1 FROM user_role WHERE user_id = ? AND role_id = ?";
        String insertSql = "INSERT INTO user_role (user_id, role_id) VALUES (?, ?)";

        try (
            Connection connection = connectionExecution.getConnection();
            Statement statement = connection.createStatement();
        		
            PreparedStatement checkStatement = connection.prepareStatement(checkSql);
            PreparedStatement insertStatement = connection.prepareStatement(insertSql)
        ) {
            statement.execute(String.format("USE %s;", connectionExecution.getDatabaseName()));

            checkStatement.setInt(1, user.getId());
            checkStatement.setInt(2, role.getId());
            ResultSet result = checkStatement.executeQuery();

            if (result.next()) {
                System.out.println("Esta associação já existe no banco de dados.");
                return false;
            }

            insertStatement.setInt(1, user.getId());
            insertStatement.setInt(2, role.getId());
            insertStatement.executeUpdate();
            
            return true;
        } 
        catch (Exception error) {
            error.printStackTrace();
        }
        return false;
    }
}
