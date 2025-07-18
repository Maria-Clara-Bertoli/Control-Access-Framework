package com.maria.accesscontrolframework;

import java.util.Map;

/**
 * Classe responsável por encapsular a lógica das chamadas dos métodos da biblioteca, utilizados pelo desenvolvedor
 * 
 * @author mariaclarabertoli41@gmail.com
 */

public class Facade {
	
	/**
	 * Método responsável por definir o tipo de conexão com o banco de dados
	 * 
	 * @param connectionExecution Objeto que contém os métodos necessários para executar a conexão com o banco de dados
	 */
	public void setConnectionExecution(ConnectionExecution connectionExecution) {

		User.connectionExecution = connectionExecution;
		Role.connectionExecution = connectionExecution;
		UserRole.connectionExecution = connectionExecution;
		Permission.connectionExecution = connectionExecution;
		Middleware.connectionExecution = connectionExecution;
		RolePermission.connectionExecution = connectionExecution;
		DatabaseInsertion.connectionExecution = connectionExecution;
	}
	
	/**
	 * Método responsável por inserir as tabelas referentes às permissões, papéis e usuários no banco de dados
	 * 
	 * @param filePath Caminho do arquivo no qual está definido o script SQL de inserção de tabelas no banco de dados
	 * @return true caso todas as tabelas sejam inseridas com sucesso no banco de dados e false caso não sejam
	 */
	public boolean insertTablesIntoDatabase(String filePath) {
		boolean response = new DatabaseInsertion()
				.filePath(filePath)
				.executeScript();
		
		return response;
	}
	
	/**
	 * Método responsável por incluir permissões no banco de dados
	 * 
	 * @param permissionTypes Lista de textos que definem as permissões que devem ser inseridas no banco de dados
	 * @return true caso todas as permissões sejam inseridas com sucesso no banco de dados e false caso não sejam
	 */
	public boolean includePermissions(String... permissionTypes) {
		boolean analysisResponse = true;
		
		for(String permissionType : permissionTypes) {
			boolean response = new Permission()
					.permissionType(permissionType)
					.insert();
			
			if (response == false) {
				analysisResponse = false;
			}
		}
		return analysisResponse;
	}
	
	/**
	 * Método responsável por incluir papéis no banco de dados
	 * 
	 * @param roleTypes Lista de textos que definem os papéis que devem ser inseridos no banco de dados
	 * @return true caso todos os papéis sejam inseridos com sucesso no banco de dados e false caso não sejam
	 */
	public boolean includeRoles(String... roleTypes) {
		boolean analysisResponse = true;
		
			for(String roleType : roleTypes) {
				boolean response = new Role()
						.roleType(roleType)
						.insert();
				
				if (response == false) {
					analysisResponse = false;
				}
			}
			return analysisResponse;
		}
	
	/**
	 * Método responsável por incluir usuários no banco de dados
	 * 
	 * @param users Map que define o nome e a senha dos usuários que devem ser inseridos no banco de dados
	 * @return true caso todas as informações dos usuários sejam inseridas com sucesso no banco de dados e false caso não sejam
	 */
	public boolean includeUsers(Map<String, String> users) {
		boolean analysisResponse = true;
		
		for(Map.Entry<String, String> user : users.entrySet()) {
			boolean response = new User()
					.name(user.getKey())
					.password(user.getValue())
					.insert();
			
			if (response == false) {
				analysisResponse = false;
			}
		}
		return analysisResponse;
	}
	
	/**
	 * Método responsável por inserir relacionamentos entre um papel e várias permissões no banco de dados
	 * 
	 * @param roleType Texto que define o papel que deve ser relacionado à várias permissões no banco de dados
	 * @param permissionTypes Textos que definem as permissões que devem ser relacionadas à um único papel no banco de dados
	 * @return true caso todos os relacionamentos sejam inseridos com sucesso no banco de dados e false caso não sejam
	 */
	public boolean relateRolePermissions(String roleType, String... permissionTypes) {
		boolean analysisResponse = true;
		
		for(String permissionType : permissionTypes) {
			boolean response = new RolePermission() 
					.insert(roleType, permissionType);
			
			if (response == false) {
				analysisResponse = false;
			}
		}
		return analysisResponse;
	}
	
	/**
	 * Método responsável por inserir relacionamentos entre um usuário e vários papeis no banco de dados
	 * 
	 * @param userName Nome do usuário que deve ser relacionado à vários papéis no banco de dados
	 * @param roleTypes Textos que definem os papeis que devem ser relacionados à um único usuário no banco de dados
	 * @return true caso todos os relacionamentos sejam inseridos com sucesso no banco de dados e false caso não sejam
	 */
	public boolean relateUserRoles(String userName, String... roleTypes) {
		boolean analysisResponse = true;
		
		for(String roleType : roleTypes) {
			boolean response = new UserRole() 
			.insert(userName, roleType);
			
			if (response == false) {
				analysisResponse = false;
			}
		}
		return analysisResponse;
	}
}
