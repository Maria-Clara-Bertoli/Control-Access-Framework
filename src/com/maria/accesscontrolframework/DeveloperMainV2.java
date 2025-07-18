package com.maria.accesscontrolframework;

import java.util.Map;
import java.util.HashMap;

public class DeveloperMainV2 {

	public static void main(String[] args) {
		
		// Inicialização das classes Facade e ConnectionExecution e outras variáveis
		
		Facade facade = new Facade();
		
		Map<String, String> users = new HashMap<String, String> ();
		
		ConnectionExecution connectionExecution = new MySQLConnectionExecution()
				.databaseName("frameworkdatabase")
				.host("localhost")
				.userName("root")
				.password("")
				.port(3306);
		
		String FILE_PATH = "C:/Users/maria/eclipse-workspace/AccessControlFrameworkV1/src/com/maria/accesscontrolframework/tables_script.txt";
		
		// Chamada do método que define o tipo de execução da conexão com o banco de dados
		
		facade.setConnectionExecution(connectionExecution);
		
		// Inclusão das tabelas
		
		facade.insertTablesIntoDatabase(FILE_PATH);
		
		// Inclusão das permissões do sistema
		
		facade.includePermissions("include user", "include report", "update report", "delete report", "visualize report");
		
		// Inlcusão dos papéis de usuários
		
		facade.includeRoles("admin", "employee", "trainee");
		
		// Inclusão dos usuários padrão do sistema
		
		users.put("Ana", "@Ana123");
		users.put("Tiago", "@Tiago123");
		users.put("Pattern User", "@Pattern123");
		
		facade.includeUsers(users);
		
		// Inclusão dos relacionamentos entre papéis e permisssões
		
		facade.relateRolePermissions("trainee", "visualize report");
		facade.relateRolePermissions("employee", "include report", "update report", "delete report", "visualize report");
		facade.relateRolePermissions("admin", "include user", "include report", "update report", "delete report", "visualize report");
		
		// Inclusão dos relacionamentos entre usuários e papéis
		
		facade.relateUserRoles("Ana", "employee");
		facade.relateUserRoles("Tiago", "trainee");
		facade.relateUserRoles("Pattern User", "admin", "trainee");
	}
}
