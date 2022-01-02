package DataService;

import java.util.Calendar;
import BaseClasses.Currency;

//aggiungi eccezioni
public interface DataAnalysis {
	public abstract void ConvertData();
	public abstract double CurrencyAverage(String currency);
	public abstract double CurrencyVariance(String currency);
	public abstract Currency DailyLower(Calendar date);
}

