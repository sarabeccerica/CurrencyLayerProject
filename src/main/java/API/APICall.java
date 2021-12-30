package API;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.ParseException;

public class APICall {
	private static final String ACCESS_KEY = "69c05a59ff13694ce64b20f9186bfdc8" ;
	private static final String BASE_URL = "http://api.currencylayer.com/";
	private static final String ENDPOINT = "historical";
	private static final String CURRENCIES = "EUR,JPY,GBP,CHF,AUD";
	public JSONObject sendLiveRequest(String date) {
		String str = "";
		JSONObject jObject = null;
		try {
			URL url = new URL(BASE_URL + ENDPOINT + "?access_key=" + ACCESS_KEY+ "&currencies=" + CURRENCIES + "&date="+date);
			HttpURLConnection conn = (HttpURLConnection)url.openConnection();
			conn.setRequestMethod("GET");
			conn.connect();
			BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
			String inputLine;
			while((inputLine=in.readLine())!=null)
				str+=inputLine;
			jObject=(JSONObject)JSONValue.parseWithException(str);
			in.close();
				
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return jObject;
	}

}
