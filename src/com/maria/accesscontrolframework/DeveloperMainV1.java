package com.maria.accesscontrolframework;

public class DeveloperMainV1 {

	public static void main(String[] args) {
		
		// Inicialização da classe ConnectionExecution e outras variáveis
		
		boolean INSERT_TABLE = true;
		
		ConnectionExecution connectionExecution = new MySQLConnectionExecution()
				.databaseName("frameworkdatabase")
				.host("localhost")
				.userName("root")
				.password("")
				.port(3306);
		
		// Utilização da classe ConnectionExecution
		
		User.connectionExecution = connectionExecution;
		Role.connectionExecution = connectionExecution;
		UserRole.connectionExecution = connectionExecution;
		Permission.connectionExecution = connectionExecution;
		Middleware.connectionExecution = connectionExecution;
		RolePermission.connectionExecution = connectionExecution;
		DatabaseInsertion.connectionExecution = connectionExecution;
		
		// Inclusão das tabelas
		
		if (INSERT_TABLE) {
			
			String FILE_PATH = "C:/Users/maria/eclipse-workspace/AccessControlFrameworkV1/src/com/maria/accesscontrolframework/tables_script.txt";
			
			new DatabaseInsertion()
			.filePath(FILE_PATH)
			.executeScript();
		}
		
		// Inclusão das permissões do sistema
		
		new Permission()
		.permissionType("include user")
		.insert();
		
		new Permission()
		.permissionType("include report")
		.insert();
		
		new Permission()
		.permissionType("update report")
		.insert();
		
		new Permission()
		.permissionType("delete report")
		.insert();
		
		new Permission()
		.permissionType("visualize report")
		.insert();
		
		// Inlcusão dos papéis de usuários
		
		new Role()
		.roleType("admin")
		.insert();
		
		new Role()
		.roleType("employee")
		.insert();
		
		new Role()
		.roleType("trainee")
		.insert();
		
		// Inclusão dos usuários padrão do sistema
		
		new User()
		.name("Pattern User")
		.password("@Pattern123")
		.insert();
		
		new User()
		.name("Tiago")
		.password("@Tiago123")
		.insert();
		
		new User()
		.name("Ana")
		.password("@Ana123")
		.insert();
		
		// Inclusão dos relacionamentos entre papéis e permisssões
		
		new RolePermission() 
		.insert("admin", "include user");
		
		new RolePermission() 
		.insert("admin", "include report");
		
		new RolePermission() 
		.insert("admin", "update report");
		
		new RolePermission() 
		.insert("admin", "delete report");
		
		new RolePermission() 
		.insert("admin", "visualize report");
		
		new RolePermission() 
		.insert("employee", "include report");
		
		new RolePermission() 
		.insert("employee", "update report");
		
		new RolePermission() 
		.insert("employee", "delete report");
		
		new RolePermission() 
		.insert("employee", "visualize report");
		
		new RolePermission() 
		.insert("trainee", "visualize report");
		
		// Inclusão dos relacionamentos entre usuários e papéis
		
		new UserRole()
		.insert("Pattern User", "admin");
		
		new UserRole()
		.insert("Tiago", "trainee");
		
		new UserRole()
		.insert("Pattern User", "trainee");
		
		new UserRole()
		.insert("Ana", "employee");
	}
}
