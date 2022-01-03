package DataService;

import java.util.Calendar;
import java.util.Vector;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public interface CurrencyService {
	public abstract JSONArray getAllCurrencies();
	public abstract JSONObject getCurrencyStats(String currency);
	public abstract JSONArray getCurrencyValues(String currency);
	public abstract JSONObject getDailyCurrencies(Calendar date);
	public abstract Vector<Double> getCurrencyQuotes(String name,int days);
	
}
