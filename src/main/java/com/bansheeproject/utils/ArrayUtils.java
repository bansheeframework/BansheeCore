package com.bansheeproject.utils;

/**
 * Contains helper methods for arrays.
 * @author Alexandre Saudate
 * @since 1.0
 */
public class ArrayUtils {

	public static <T> String parseArrayToString (T[] array) {
		
		if (array == null)
			return "[null]";
		StringBuilder string = new StringBuilder();
		for (int i = 0; i < array.length; i++) {
			string.append(array[i].toString()).append(",");
		}
		if (string.length() > 0)
			string.deleteCharAt(string.length() - 1);
		else {
			string.append("[empty]");
		}
		return string.toString();
	}
	
	
}
