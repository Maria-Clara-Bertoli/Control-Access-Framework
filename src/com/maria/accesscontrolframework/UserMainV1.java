package com.maria.accesscontrolframework;

import java.util.ArrayList;

public class UserMainV1 {

	public static void main(String[] args) {
		
		// Definição do login do usuário que acabou de se logar no sistema
		
		User.getUser()
		.name("Pattern User")
		.password("@Pattern123");
		
		// Inicialização da classe ConnectionExecution
		
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
		
		// Istância de uma classe qualquer do sistema
		
		TestMethods testMethods = new TestMethods();
		
		// Chamada de um método com parâmetros e sem retorno da classe instanciada
		
		Object includeReportResponse = Middleware.executeMethod(testMethods, "includeReport", "Relatório de Funcionários");
		
		System.out.println("\n" + includeReportResponse + "\n");
		
		// Chamada de um método com parâmetros e com retorno da classe instanciada
		
		Object includeUserResponse = Middleware.executeMethod(testMethods, "includeUser", "Pedro", "@Pedro123");
		
		System.out.println("\n" + includeUserResponse + "\n");
		
		// Chamada de um método sem parâmetros e sem retorno da classe instanciada
		
		Object visualizeReportResponse = Middleware.executeMethod(testMethods, "visualizeReport");
		
		System.out.println("\n" + visualizeReportResponse + "\n");
		
		// Listagem das permissões do usuário logado no sistema
		
		System.out.println("Listagem das Permissões do Usuário Logado no Sistema\n");
		
		ArrayList<Permission> permissionList = new Permission()
				.searchList();
		
		for(Permission permission : permissionList) {
			System.out.println(permission.getPermissionType());
		}
	}
}
