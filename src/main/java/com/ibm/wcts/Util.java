package com.ibm.wcts;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;



public class Util {
	
	public static void main(String[] args){
		System.out.println(System.getProperty("file.encoding"));
		
		String text = "https://message-hub-jenkins.swg-devops.com/job/ops/job/ops-worker-reload-prod/";
		System.out.println(transformURLIntoLinks(text));
	}

	public static String toHex(String arg) {
		    
	    return String.format("%040x", new BigInteger(1, arg.getBytes(/*YOUR_CHARSET?*/)));
	}
	
	public static String transformURLIntoLinks(String text){
		String urlValidationRegex = "(https?|ftp)://(www\\d?|[a-zA-Z0-9]+)?.[a-zA-Z0-9-]+(\\:|.)([a-zA-Z0-9-.]+|(\\d+)?)([/?:].*)?";
		Pattern p = Pattern.compile(urlValidationRegex);
		Matcher m = p.matcher(text);
		StringBuffer sb = new StringBuffer();
		while(m.find()){
		    String found =m.group(0); 
		    m.appendReplacement(sb, "<a href='"+found+"' target='_blank'>"+found+"</a>"); 
		}
		m.appendTail(sb);
		return sb.toString();
		}

	
	public static boolean IsMatch(String s, String pattern) {
        try {
            Pattern patt = Pattern.compile(pattern);
            Matcher matcher = patt.matcher(s);
            
            return matcher.matches();
        } catch (RuntimeException e) {
        return false;
        }       
	}
        
	public static String cleanTextContent(String text) 
	{
	    // strips off all non-ASCII characters
	    text = text.replaceAll("[^\\x00-\\x7F]", "");
	 
	    // erases all the ASCII control characters
	    text = text.replaceAll("[\\p{Cntrl}&&[^\r\n\t]]", "");
	     
	    // removes non-printable characters from Unicode
	    text = text.replaceAll("\\p{C}", "");
	 
	    return text.trim();
	}
	
	public String getPropValues() throws IOException {

		InputStream inputStream = null;

		String port = "80";

		try {
			Properties prop = new Properties();
			String propFileName = "config.properties";
			
			

			inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);

			if (inputStream != null) {
				prop.load(inputStream);
			} else {
				throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
			}

			// get the property value and print it out
			port = prop.getProperty("port");

		} catch (Exception e) {
			System.out.println("Exception: " + e);
		} finally {
			inputStream.close();
		}
		return port;
	}

	public  String getPropValues(String name) throws IOException {

		InputStream inputStream = null;

		String port = "80";

		try {
			Properties prop = new Properties();
			String propFileName = "config.properties";
			
			

			inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);

			if (inputStream != null) {
				prop.load(inputStream);
			} else {
				throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
			}

			// get the property value and print it out
			port = prop.getProperty(name);

		} catch (Exception e) {
			System.out.println("Exception: " + e);
		} finally {
			inputStream.close();
		}
		return port;
	}

	
}
