package com.ibm.wcts;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Util {

	public static void main(String[] args) {
		System.out.println(System.getProperty("file.encoding"));

		String text = "https://message-hub-jenkins.swg-devops.com/job/ops/job/ops-worker-reload-prod/";
		System.out.println(transformURLIntoLinks(text));
	}

	public static String toHex(String arg) {

		return String.format("%040x", new BigInteger(1, arg.getBytes(/* YOUR_CHARSET? */)));
	}

	public static String transformURLIntoLinks(String text) {

		String tag = "href";

		String urlValidationRegex = "(https?|ftp)://(www\\d?|[a-zA-Z0-9]+)?.[a-zA-Z0-9-]+(\\:|.)([a-zA-Z0-9-_=&+.@]+|(\\d+)?)([/?:].*)?";

		String[] lines = text.split(System.getProperty("line.separator"));

		StringBuffer sb = new StringBuffer();

		for (int counter = 0; counter < lines.length; counter++) {
			String line = lines[counter];

			if (line.contains(tag)) {
				// already encoded

				sb.append(line);

			} else {
				Pattern p = Pattern.compile(urlValidationRegex);
				Matcher m = p.matcher(line);

				while (m.find()) {
					String found = m.group(0);
					m.appendReplacement(sb, "<a href='" + found + "' target='_blank'>" + found + "</a>");
				}
				m.appendTail(sb);

			}
		}

		return sb.toString();
	}

	public static boolean IsMatch(String s, String pattern) {
		try {
			Pattern patt = Pattern.compile(pattern);
			Matcher matcher = patt.matcher(s);

			return matcher.matches();
		} catch (RuntimeException e) {
			System.out.println("Doesn't match");
			e.printStackTrace();
			return false;
		}
	}

	public static String cleanTextContent(String text) {
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
			String propFileName = "config.properties"; // using src/main/resources/config.properties

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

	public String getPropValues(String name) throws IOException {

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
