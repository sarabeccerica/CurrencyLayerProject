package CallToAPI;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.ParseException;

import BaseClasses.Currency;
import BaseClasses.DailyData;

/**
 * Questa classe gestisce la chiamata all'API Currencylayer utilizzando JSONArray
 * Come struttura dati di ritorno per i dati sulle monete
 */

public class APICall {
	/**
	 * @param DAYSNUMBER  contiene il numero di giorni di cui si vogliono 
	 * ottenere e analizzare i dati
	 */
	private static final int DAYSNUMBER = 5;
	/**
	 * @param BASE_URL contiene l'URL necessario per la chiamata all'API
	 */
	private static final String BASE_URL = "http://api.currencylayer.com/";
	/**
	 * @param ENDPOINT contiene la parola chiave che Currencylayer identifica
	 * univocamente per accedere ai dati storici
	 */
	private static final String ENDPOINT = "historical";
	/**
	 * @param CURRENCIES è una stringa che contiene la lista delle valute di interesse
	 * per il programma 
	 */
	private static final String CURRENCIES = "EUR,CHF,GBP,AUD,KYD,JPY,CNY";
	/**
	 * @param currencies è un vettore di stringhe ottenuto eseguendo la funzione
	 * split sulla stringa delle valute
	 */
	private String[] currencies=CURRENCIES.split(",");
	/**
	 * Funzione per la lettura della key dell'API da file
	 * @return restituisce una stringa con il valore della chiave
	 */
	public String readFile() {
		String key="";
		try {
			@SuppressWarnings("resource")
			BufferedReader reader = new BufferedReader(new FileReader("src/main/java/CallToAPI/ACCESS_KEY.txt"));
			key=reader.readLine();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return key;
	}
	/**
	  * Questa funzione effettua la chiamata all'API relativa al parametro in input
	  * @param date che è una stringa contenente la data in formato yyyy-mm-dd
	  * @return restituisce un JSONObject che contiene le valute di quel giorno
	  */
	public JSONObject sendLiveRequest(String date) {
		String str = "";
		JSONObject jObject = null;
		try {
			URL url = new URL(BASE_URL + ENDPOINT + "?access_key=" + readFile()+ "&currencies=" + CURRENCIES + "&date="+date);
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
	/**
	 * Questa funzione raccoglie i dati restituiti dalla funzione sendLiveRequest 
	 * relativi ai giorni di interesse
	 * @return restituisce un ArrayList contenete tutti i dati richiesti all'API
	 */
	public ArrayList<DailyData> sendRequest() {
		ArrayList<DailyData> allData = new ArrayList<>();
		try {
			for(int i=0;i<DAYSNUMBER;i++) {
				Calendar date = Calendar.getInstance();
				date.add(Calendar.DAY_OF_MONTH,-i);
			 	String date1 = "" + date.get(Calendar.YEAR)+"-" + date.get(Calendar.MONTH) +"-"+ date.get(Calendar.DATE);
			 	JSONObject obj = new JSONObject();
				obj = sendLiveRequest(date1);
			 	obj.remove("terms");
			 	obj.remove("success");
			 	obj.remove("privacy");
			 	obj.remove("historical");
				obj.remove("timestamp");
				obj.remove("source");
				ArrayList<Currency> dailyData = new ArrayList<>();
				for(int j=0;j<5;j++) {
					JSONObject quotes= new JSONObject();
				 	quotes=(JSONObject)obj.get("quotes");
				 	Currency e = new Currency("USD"+currencies[j],(double)quotes.get("USD"+currencies[j]));
				 	dailyData.add(e);
				 }
				DailyData dataCurrencies = new DailyData();
				dataCurrencies.setDate(date);
				dataCurrencies.setCurrencies(dailyData);
				allData.add(dataCurrencies);
				Thread.sleep(2000);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return allData;
	}

}
