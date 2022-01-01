package DataService;

import java.util.Calendar;

import org.json.simple.JSONObject;

import BaseClasses.Currency;

//aggiungi eccezioni
public interface DataAnalysis {
	public abstract double CurrencyAverage(String currency);
	public abstract double CurrencyVariance(String currency);
	public abstract Currency DailyLower(Calendar date);
	public abstract JSONObject getCurrencyStats(String currency);
}

