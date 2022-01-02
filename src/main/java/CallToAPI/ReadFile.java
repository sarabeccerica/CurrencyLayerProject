package CallToAPI;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import BaseClasses.DailyData;

public class ReadFile {
	private static final String CURRENCIES = "EUR,JPY,GBP,CHF,AUD";
	private String fileName = "C:\\Users\\39389\\eclipse-workspace\\ProgettoOOP\\src\\main\\java\\ProgettoOOP\\API\\data.json";
	private String[] currencies=CURRENCIES.split(",");
	public JSONArray readFile() {
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
		return list;
	}
}
