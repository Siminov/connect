package siminov.connect.utils;

import java.util.Map;

import siminov.connect.Constants;
import siminov.orm.exception.SiminovException;
import siminov.orm.utils.ClassUtils;

public class ServiceResourceUtils {

	public static String resolve(String query, Map<String, String> serviceResources) throws SiminovException {

		if(query == null) {
			return null;
		}
		
		
		if(query.contains(Constants.SERVICE_RESOURCE_HASH + Constants.SERVICE_RESOURCE_OPEN_CURLY_BRACKET)) {

			String serviceResourceKey = query.substring(query.indexOf(Constants.SERVICE_RESOURCE_HASH + Constants.SERVICE_RESOURCE_OPEN_CURLY_BRACKET) + 2, query.indexOf(Constants.SERVICE_RESOURCE_CLOSE_CURLY_BRACKET));
			
			String serviceResourceClass = serviceResourceKey.substring(0, serviceResourceKey.lastIndexOf(Constants.SERVICE_RESOURCE_DOT));
			String serviceResourceAPI = serviceResourceKey.substring(serviceResourceKey.lastIndexOf(Constants.SERVICE_RESOURCE_DOT) + 1, serviceResourceKey.length());
			
			Object classObject = ClassUtils.createClassInstance(serviceResourceClass);
			String serviceResourceValue = (String) ClassUtils.getValue(classObject, serviceResourceAPI);
		
			query = query.replace(Constants.SERVICE_RESOURCE_HASH + Constants.SERVICE_RESOURCE_OPEN_CURLY_BRACKET + serviceResourceKey + Constants.SERVICE_RESOURCE_CLOSE_CURLY_BRACKET, serviceResourceValue);
			
			resolve(query, serviceResources);
		} else if(query.contains(Constants.SERVICE_RESOURCE_OPEN_CURLY_BRACKET)) {
			
			String serviceResourceKey = query.substring(query.indexOf(Constants.SERVICE_RESOURCE_OPEN_CURLY_BRACKET) + 1, query.indexOf(Constants.SERVICE_RESOURCE_CLOSE_CURLY_BRACKET));
			String serviceResourceValue = serviceResources.get(serviceResourceKey);
			
			query = query.replace(Constants.SERVICE_RESOURCE_OPEN_CURLY_BRACKET + serviceResourceKey + Constants.SERVICE_RESOURCE_CLOSE_CURLY_BRACKET, serviceResourceValue);
			
			resolve(query, serviceResources);
		}
		
		return query;
	}
	
}
