package com.bansheeproject.utils;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.Map.Entry;

/**
 * Contains helper methods for handling collections.
 * 
 * @author Alexandre Saudate
 * @since 1.0
 */
public class CollectionsUtils {
	
	
	public static Map<String, String> propertiesAsMap(Properties properties) {
		
		Set<Entry<Object, Object>> entries = properties.entrySet();
		
		Map<String, String> responseMap = new HashMap<String, String>();
		
		for (Entry<Object, Object> entry : entries) {
			responseMap.put((String)entry.getKey(), (String)entry.getValue());
		}
		
		return responseMap;
		
	}

	public static String collectionAsString (Collection<?> collection) {
		if (collection == null)
			return "[null]";
		if (collection.size() == 0) 
			return "[empty]";
		StringBuilder builder = new StringBuilder();
		for (Object obj : collection) {
			builder.append(obj).append(", ");
		}
		builder.deleteCharAt(builder.length() - 1);
		return builder.toString();
	}
	
}
