package siminov.connect.utils;

import java.util.Map;

import siminov.connect.Constants;
import siminov.orm.exception.SiminovException;
import siminov.orm.utils.ClassUtils;

public class InlineResourceUtils {

	public static String resolve(String query, Map<String, String> inlineResources) throws SiminovException {

		if(query == null) {
			return null;
		}
		
		
		if(query.contains(Constants.INLINE_RESOURCE_HASH + Constants.INLINE_RESOURCE_OPEN_CURLY_BRACKET)) {

			String inlineResourceKey = query.substring(query.indexOf(Constants.INLINE_RESOURCE_HASH + Constants.INLINE_RESOURCE_OPEN_CURLY_BRACKET) + 2, query.indexOf(Constants.INLINE_RESOURCE_CLOSE_CURLY_BRACKET));
			
			String inlineResourceClass = inlineResourceKey.substring(0, inlineResourceKey.lastIndexOf(Constants.INLINE_RESOURCE_DOT));
			String inlineResourceAPI = inlineResourceKey.substring(inlineResourceKey.lastIndexOf(Constants.INLINE_RESOURCE_DOT) + 1, inlineResourceKey.length());
			
			Object classObject = ClassUtils.createClassInstance(inlineResourceClass);
			String inlineValue = (String) ClassUtils.getValue(classObject, inlineResourceAPI);
		
			query = query.replace(Constants.INLINE_RESOURCE_HASH + Constants.INLINE_RESOURCE_OPEN_CURLY_BRACKET + inlineResourceKey + Constants.INLINE_RESOURCE_CLOSE_CURLY_BRACKET, inlineValue);
			
			resolve(query, inlineResources);
		} else if(query.contains(Constants.INLINE_RESOURCE_OPEN_CURLY_BRACKET)) {
			
			String inlineResourceKey = query.substring(query.indexOf(Constants.INLINE_RESOURCE_OPEN_CURLY_BRACKET) + 1, query.indexOf(Constants.INLINE_RESOURCE_CLOSE_CURLY_BRACKET) - 1);
			String inlineValue = inlineResources.get(inlineResourceKey);
			
			query = query.replace(Constants.INLINE_RESOURCE_OPEN_CURLY_BRACKET + inlineResourceKey + Constants.INLINE_RESOURCE_CLOSE_CURLY_BRACKET, inlineValue);
			
			resolve(query, inlineResources);
		}
		
		return query;
	}
	
}
