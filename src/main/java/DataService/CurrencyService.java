package DataService;

import java.util.Calendar;
import java.util.Vector;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import Exceptions.CurrencyNotFoundException;
import Exceptions.DateNotFoundException;

public interface CurrencyService {
	public abstract JSONArray getAllCurrencies();
	public abstract JSONObject getCurrencyStats(String currency) throws CurrencyNotFoundException;
	public abstract JSONArray getCurrencyValues(String currency) throws CurrencyNotFoundException;
	public abstract JSONObject getDailyCurrencies(Calendar date) throws DateNotFoundException;
	public abstract Vector<Double> getCurrencyQuotes(String name,int days) throws CurrencyNotFoundException;
}
