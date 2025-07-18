package com.maria.accesscontrolframework;

import java.util.Map;
import java.util.HashMap;

import com.maria.accesscontrolframework.SpecialAnnotation.CheckPermission;

public class TestMethods {
	
	private Facade facade = new Facade();
	
	private ConnectionExecution connectionExecution = new MySQLConnectionExecution()
			.databaseName("frameworkdatabase")
			.host("localhost")
			.userName("root")
			.password("")
			.port(3306);
	
	@CheckPermission(permissionType = "include user")
	public boolean includeUser(String name, String password) {
		
		boolean response = false;
		Map<String, String> users = new HashMap<String, String> ();
		
		facade.setConnectionExecution(connectionExecution);
		
		users.put(name, password);
		response = facade.includeUsers(users);
		
		return response;
	}
	
	@CheckPermission(permissionType = "include report")
	public void includeReport(String name) {
		System.out.println("O relatório " + name + " foi inserido com sucesso.");
	}
	
	@CheckPermission(permissionType = "visualize report")
	public void visualizeReport() {
		System.out.println("O relatório está sendo visualizado neste momento.");
	}
}
