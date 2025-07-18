package com.maria.accesscontrolframework;

import java.util.ArrayList;
import java.lang.reflect.Method;
import com.maria.accesscontrolframework.SpecialAnnotation.CheckPermission;

/**
 * Classe responsável por verificar as permissões de um usuário e invocar um determinado método solicitado por ele somente se este usuário possuir privilégios 
 * suficientes para executá-lo
 * 
 * @author mariaclarabertoli41@gmail.com
 */

public class Middleware {
	
	public static ArrayList<Permission> permissionList;
	public static ConnectionExecution connectionExecution;
	
	/**
	 * Método responsável por realizar a busca de todas as permissões de um usuário no banco de dados e invocar um determinado método cujo o nome é passado como 
	 * parâmetro, somente se este usuário possuir privilégios suficientes para executá-lo
	 * 
	 * @param object Classe que contém os métodos que devem ser executados por um usuário
	 * @param methodName Nome do método que o usuário deseja executar
	 * @param methodArguments Argumentos do método que o usuário deseja executar
	 * @return O retorno do método chamado pelo usuário, caso ele possua permissões suficientes para executá-lo. Caso não às possua, o retorno é null
	 */
	public static Object executeMethod(Object object, String methodName, Object... methodArguments) {
        try {
        	if (permissionList == null) {
    			
    			permissionList = new Permission()
    					.searchList();
    		}
        	
        	Class<?>[] parametersType = new Class[methodArguments.length];
        	
        	for (int index = 0; index <methodArguments.length; index++) {
            	parametersType[index] = methodArguments[index].getClass();
            }
        	
        	Method method = object.getClass().getMethod(methodName, parametersType);
            CheckPermission checkPermission = method.getAnnotation(CheckPermission.class);
        	
        	if (permissionList != null && checkPermission != null) {
	            
        		for (Permission permission : permissionList) {
	
		            if (permission.getPermissionType().equals(checkPermission.permissionType())) {
		                Object methodReturn = method.invoke(object, methodArguments);
		                
		                return methodReturn;
		            } 
        		}
	        } 
        }
        catch (Exception e) {
            e.printStackTrace();
        }
		return null;
    }
}
