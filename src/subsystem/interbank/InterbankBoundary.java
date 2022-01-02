package subsystem.interbank;

import java.io.Console;

import common.exception.UnrecognizedException;
import utils.API;

public class InterbankBoundary {

	String query(String url, String data) {
		String response = null;
		try {
			System.out.println("query");
			response = API.post(url, data, null);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw new UnrecognizedException();
		}
		return response;
	}

}
