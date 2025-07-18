package com.maria.accesscontrolframework;

import java.util.Map;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.MethodOrderer;
import static org.junit.Assert.assertEquals;
import org.junit.jupiter.api.TestMethodOrder;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)

public class JUnitTest {
	
	private Facade facade = new Facade();
	
	Map<String, String> users = Map.of(
			"Ana", "@Ana123",
			"Tiago", "@Tiago123",
			"Pattern User", "@Pattern123"
			);
	
	private TestMethods testMethods = new TestMethods();
	
	private ConnectionExecution connectionExecution = new MySQLConnectionExecution()
			.databaseName("frameworkdatabase")
			.host("localhost")
			.userName("root")
			.password("")
			.port(3306);
	
	private String FILE_PATH = "C:/Users/maria/eclipse-workspace/AccessControlFrameworkV1/src/com/maria/accesscontrolframework/tables_script.txt";
	
	@Test 
	@Order(1)
	public void testInsertTablesIntoDatabase() {
		facade.setConnectionExecution(connectionExecution);
		boolean response = facade.insertTablesIntoDatabase(FILE_PATH);
		assertEquals(true, response);
	}
	
	@Test 
	@Order(2)
	public void testIncludeDifferentPermissions() {
		facade.setConnectionExecution(connectionExecution);
		boolean response = facade.includePermissions("include user", "include report", "update report", "delete report", "visualize report");
		assertEquals(true, response);
	}
	
	@Test 
	@Order(3)
	public void testIncludeEqualPermissions() {
		facade.setConnectionExecution(connectionExecution);
		boolean response = facade.includePermissions("include user", "include report", "update report", "include product", "update product");
		assertEquals(false, response);
	}
	
	@Test 
	@Order(4)
	public void testIncludeDifferentRoles() {
		facade.setConnectionExecution(connectionExecution);
		boolean response = facade.includeRoles("admin", "employee", "trainee");
		assertEquals(true, response);
	}
	
	@Test 
	@Order(5)
	public void testIncludeEqualRoles() {
		facade.setConnectionExecution(connectionExecution);
		boolean response = facade.includeRoles("admin", "employee", "trainee");
		assertEquals(false, response);
	}
	
	@Test 
	@Order(6)
	public void testIncludeDifferentUsers() {
		facade.setConnectionExecution(connectionExecution);
		boolean response = facade.includeUsers(users);
		assertEquals(true, response);
	}
	
	@Test 
	@Order(7)
	public void testRelateTraineeRoleWithPermissions() {
		facade.setConnectionExecution(connectionExecution);
		boolean response = facade.relateRolePermissions("trainee", "visualize report");
		assertEquals(true, response);
	}
	
	@Test 
	@Order(8)
	public void testRelatePreExistingRoleWithPermissions() {
		facade.setConnectionExecution(connectionExecution);
		boolean response = facade.relateRolePermissions("trainee", "visualize report");
		assertEquals(false, response);
	}
	
	@Test 
	@Order(9)
	public void testRelateEmployeeRoleWithPermissions() {
		facade.setConnectionExecution(connectionExecution);
		boolean response = facade.relateRolePermissions("employee", "include report", "update report", "delete report", "visualize report");
		assertEquals(true, response);
	}
	
	@Test 
	@Order(10)
	public void testRelateAdminRoleWithPermissions() {
		facade.setConnectionExecution(connectionExecution);
		boolean response = facade.relateRolePermissions("admin", "include user", "include report", "update report", "delete report", "visualize report");
		assertEquals(true, response);
	}
	
	@Test 
	@Order(11)
	public void testRelateAnaUserWithRoles() {
		facade.setConnectionExecution(connectionExecution);
		boolean response = facade.relateUserRoles("Ana", "employee");
		assertEquals(true, response);
	}
	
	@Test 
	@Order(12)
	public void testRelatePreExistingUserWithPermissions() {
		facade.setConnectionExecution(connectionExecution);
		boolean response = facade.relateUserRoles("Ana", "employee");
		assertEquals(false, response);
	}
	
	@Test 
	@Order(13)
	public void testRelateTiagoUserWithRoles() {
		facade.setConnectionExecution(connectionExecution);
		boolean response = facade.relateUserRoles("Tiago", "trainee");
		assertEquals(true, response);
	}
	
	@Test 
	@Order(14)
	public void testRelatePatternUserWithRoles() {
		facade.setConnectionExecution(connectionExecution);
		boolean response = facade.relateUserRoles("Pattern User", "admin", "trainee");
		assertEquals(true, response);
	}
	
	@Test
	@Order(15)
	public void callMethodIncludeReportByPatternUser() {
		facade.setConnectionExecution(connectionExecution);
		
		User.getUser()
		.name("Pattern User")
		.password("@Pattern123");
		
		Object response = Middleware.executeMethod(testMethods, "includeUser", "Rita", "@Rita123");
		
		Middleware.permissionList = null;
		
		assertEquals(true, response);
	}
	
	@Test
	@Order(16)
	public void callMethodIncludeReportByAnaUser() {
		facade.setConnectionExecution(connectionExecution);
		
		User.getUser()
		.name("Ana")
		.password("@Ana123");
		
		Object response = Middleware.executeMethod(testMethods, "includeUser", "Carlos", "@Carlos123");
		
		Middleware.permissionList = null;
		
		assertEquals(null, response);
	}
	
	@Test
	@Order(17)
	public void callMethodIncludeReportByTiagoUser() {
		facade.setConnectionExecution(connectionExecution);
		
		User.getUser()
		.name("Tiago")
		.password("@Tiago123");
		
		Object response = Middleware.executeMethod(testMethods, "visualizeReport");
		
		assertEquals(null, response);
	}
}
