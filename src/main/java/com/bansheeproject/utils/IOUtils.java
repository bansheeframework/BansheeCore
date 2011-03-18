package com.bansheeproject.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


/**
 * 
 * Contains helper methods for handling I/O.
 * 
 * @author Alexandre Saudate
 * @since 1.0
 */
public class IOUtils {
	
	
	private static final int STREAM_BUFFER_SIZE = 2048;
	
	public static String getStringFromStream (InputStream stream) throws IOException {

		StringBuilder builder = new StringBuilder();
		
		BufferedReader br = null;
		
		try {
			br = new BufferedReader(new InputStreamReader(stream));
			String temp;
			while ((temp = br.readLine()) != null) {


				builder.append(temp);

			}
		}
		finally {
			if (br != null) 
				br.close();
		}
		return builder.toString();
		
	
		
		
	}

}
