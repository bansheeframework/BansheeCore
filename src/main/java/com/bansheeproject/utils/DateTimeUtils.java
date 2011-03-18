package com.bansheeproject.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;


/**
 * Contains helper methods for handling dates.
 * 
 * @author Alexandre Saudate
 * @since 1.0
 */
public class DateTimeUtils {

	
	
	public static String getXMLTimestamp (Date date) {
		
		int offset = TimeZone.getDefault().getOffset(System.currentTimeMillis());
		date = new Date(date.getTime() - offset);
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss.SSS'Z'");
		return dateFormat.format(date);
		
	}
	
	
	
	
	
	
}
