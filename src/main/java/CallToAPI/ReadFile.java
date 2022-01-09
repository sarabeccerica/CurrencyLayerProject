package CallToAPI;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import BaseClasses.DailyData;
import BaseClasses.Currency;

/**
 * Questa classe gestisce la lettura da file delle informazioni utilizzando JSONArray
 * Come struttura dati di ritorno
 * @author Beccerica Sara
 */

public class ReadFile {
	/**
	 * @param CURRENCIES e' una stringa che contiene la lista delle valute di interesse
	 * per il programma 
	 */
	private static final String CURRENCIES = "EUR,JPY,GBP,CHF,AUD";
	/**
	 * @param fileName contiene il percorso del file contente i dati sulle valute
	 */
	private final String fileName = "src/main/java/DataService/data.json";
	/**
	 * @param currencies e' un vettore di stringhe ottenuto eseguendo la funzione
	 * split sulla stringa delle valute
	 */
	private String[] currencies=CURRENCIES.split(",");
	/**
	 * Questa funzione effettua la lettura del file in formato json convertendolo
	 * in una struttura ArrayList contenente tutti i dati sulle valute
	 * @return restituisce un ArrayList che contiene tutti i dati di interesse
	 */
	public ArrayList<DailyData> readFile() {
		JSONParser jParser = new JSONParser();
		JSONArray list = null;
		try(FileReader reader = new FileReader(fileName)) {
			Object obj = jParser.parse(reader);
			list = (JSONArray) obj;	
		}catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return convertJSONArray(list);
	}
	 /**
	  * Questa funzione converte una struttura dati JSONArray contenente i dati
	  * in un ArrayList
	  * @param jsonArray e' il parametro che contiene i dati in formato json
	  * @return restituisce un ArrayList con i dati di interesse
	  */
	public ArrayList<DailyData> convertJSONArray(JSONArray jsonArray) {
		ArrayList<DailyData> allData = new ArrayList<>();
		for (int i=0;i<jsonArray.size();i++) {
			ArrayList<Currency> dailyData = new ArrayList<>();
			 for(int j=0;j<5;j++) {
			 	JSONObject quotes= new JSONObject();
			 	quotes=(JSONObject)((JSONObject) jsonArray.get(i)).get("quotes");
			 	Currency e = new Currency("USD"+currencies[j],(double)quotes.get("USD"+currencies[j]));
			 	dailyData.add(e);
			 }
			 DailyData dataCurrencies = new DailyData();
			 String stringDate = (String)((JSONObject) jsonArray.get(i)).get("date");
			 String[] splitDate = stringDate.split("-");
			 Calendar date = Calendar.getInstance();
			 date.clear();
			 date.set(Integer.parseInt(splitDate[0]),Integer.parseInt(splitDate[1])-1,Integer.parseInt(splitDate[2]));
			 dataCurrencies.setDate(date);
			 dataCurrencies.setCurrencies(dailyData);
			 allData.add(dataCurrencies);
		}
		return allData;
	}
}
