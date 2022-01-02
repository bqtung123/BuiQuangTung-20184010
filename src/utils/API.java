package utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.Console;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.logging.Logger;

import org.junit.jupiter.api.condition.DisabledIfSystemProperties;

import entity.payment.CreditCard;
import entity.payment.PaymentTransaction;

/**
 * Class cung cap cac phuong thuc gui request len server va nhan du lieu tra ve
 * Date: 11/12/2021
 * @author BQT
 *@version 1.0
 */
public class API {

	/** 
	 * Thuoc tinh giup format ngay thang theo dinh dang
	 */
	public static DateFormat DATE_FORMATER = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	/**
	 * Thuoc tinh giup log thong tin ra console
	 */
	private static Logger LOGGER = Utils.getLogger(Utils.class.getName());

	/**
	 * Phuong thuc giup goi cac api dang get
	 *
	 * @param url : duong dan toi server can request
	 * @param token: doan ma bam can cung cap de xac thuc nguoi dung
	 * @return response: phan hoi tu server (dang string)
	 * @throws Exception the exception
	 */
	public static String get(String url, String token) throws Exception {
		
		
		//Phan 1: setup
		HttpURLConnection conn = setupConnection(url,"GET", token);
		
		
		//Phan 2: Doc du lieu tra ve tu server
		String response = readResponse(conn);
		
		return response;
	}

	private static HttpURLConnection setupConnection(String url,String method, String token)
			throws MalformedURLException, IOException, ProtocolException {
		System.out.println(url);
		LOGGER.info("Request URL: " + url + "\n");
		URL line_api_url = new URL(url);
		HttpURLConnection conn = (HttpURLConnection) line_api_url.openConnection();
		System.out.println(conn);
		conn.setDoInput(true);
		conn.setDoOutput(true);
		System.out.println("set do input out put");
		conn.setRequestMethod(method);
		System.out.println("set request method");
		conn.setRequestProperty("Content-Type", "application/json");
		System.out.println("set content type");
		conn.setRequestProperty("Authorization", "Bearer " + token);
		System.out.println("set authorization");
		return conn;
	}

	int var;

	/**
	 * Phuong thuc giup goi cac api dang post
	 *
	 * @param url: Duong dan toi url can request
	 * @param data: du lieu dua len server de xu ly (dang JSON)
	 * @return response: phan hoi tu server dang string
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static String post(String url, String data
			, String token
	) throws IOException {
		System.out.println(data);
		allowMethods("PATCH");
		
		// Phan 1: Setup
		HttpURLConnection conn = setupConnection(url,"PATCH", token);
	
		
		// Phan 2: Gui du lieu 
		Writer writer = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
		writer.write(data);
		writer.close();
	
		
		//Phan 3: Doc du lieu gui ve tu server
		String response = readResponse(conn);
		
		return response;
	}

	private static String readResponse(HttpURLConnection conn) throws IOException {
		BufferedReader in;
		String inputLine;
		if (conn.getResponseCode() / 100 == 2) {
			in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		} else {
			in = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
		}
		StringBuilder response = new StringBuilder();// using StringBuilder for the sake of memory and performance
		while ((inputLine = in.readLine()) != null)
			response.append(inputLine);
		in.close();
		LOGGER.info("Respone Info: " + response.toString());
		return response.toString();
	}

	
	/**
	 * Phuong thuc cho phep cac loai giao thuc api khac nhau nhu PATCH, PUT,... (chi hoat dong voi Java <=11)
	 * @deprecated chi hoat dong voi Java <=11
	 * @param methods the methods
	 */
	private static void allowMethods(String... methods) {
		System.out.println("allows method");
		try {
			Field methodsField = HttpURLConnection.class.getDeclaredField("methods");
			methodsField.setAccessible(true);

			Field modifiersField = Field.class.getDeclaredField("modifiers");
			modifiersField.setAccessible(true);
			modifiersField.setInt(methodsField, methodsField.getModifiers() & ~Modifier.FINAL);

			String[] oldMethods = (String[]) methodsField.get(null);
			Set<String> methodsSet = new LinkedHashSet<>(Arrays.asList(oldMethods));
			methodsSet.addAll(Arrays.asList(methods));
			String[] newMethods = methodsSet.toArray(new String[0]);

			methodsField.set(null/* static field */, newMethods);
		} catch (NoSuchFieldException | IllegalAccessException e) {
			throw new IllegalStateException(e);
		}
	}

}
