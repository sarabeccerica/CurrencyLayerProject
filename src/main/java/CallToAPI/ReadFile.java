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

public class ReadFile {
	private static final String CURRENCIES = "EUR,JPY,GBP,CHF,AUD";
	private String fileName = "C:\\Users\\39389\\eclipse-workspace\\ProgettoOOP\\src\\main\\java\\ProgettoOOP\\API\\data.json";
	private String[] currencies=CURRENCIES.split(",");
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
