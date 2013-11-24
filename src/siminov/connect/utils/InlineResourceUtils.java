package siminov.connect.utils;

import java.util.Map;

import siminov.connect.Constants;
import siminov.orm.exception.SiminovException;
import siminov.orm.utils.ClassUtils;

public class InlineResourceUtils {

	public static Object resolve(final String query, Map<String, String> inlineResources) throws SiminovException {
		
		if(query.contains(Constants.INLINE_RESOURCE_HASH + Constants.INLINE_RESOURCE_OPEN_CURLY_BRACKET)) {

			String inlineResourceKey = query.substring(query.indexOf(Constants.INLINE_RESOURCE_HASH + Constants.INLINE_RESOURCE_OPEN_CURLY_BRACKET) + 1, query.indexOf(Constants.INLINE_RESOURCE_CLOSE_CURLY_BRACKET) - 1);
			
			String inlineResourceClass = inlineResourceKey.substring(0, query.lastIndexOf(Constants.INLINE_RESOURCE_DOT));
			String inlineResourceAPI = inlineResourceKey.substring(query.lastIndexOf(Constants.INLINE_RESOURCE_DOT), query.length());
			
			Class<?> classObject = ClassUtils.createClass(inlineResourceClass);
			return ClassUtils.getValue(classObject, inlineResourceAPI);
		} else if(query.contains(Constants.INLINE_RESOURCE_OPEN_CURLY_BRACKET)) {
			
			String inlineResourceKey = query.substring(query.indexOf(Constants.INLINE_RESOURCE_OPEN_CURLY_BRACKET) + 1, query.indexOf(Constants.INLINE_RESOURCE_CLOSE_CURLY_BRACKET) - 1);
			return inlineResources.get(inlineResourceKey);
		}
		
		return null;
	}
	
}
