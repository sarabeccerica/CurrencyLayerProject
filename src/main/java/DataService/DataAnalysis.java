package DataService;

import java.util.Calendar;
import BaseClasses.Currency;
import Exceptions.CurrencyNotFoundException;
import Exceptions.DateNotFoundException;

//aggiungi eccezioni
public interface DataAnalysis {
	public abstract void ConvertData();
	public abstract double CurrencyAverage(String currency) throws CurrencyNotFoundException;
	public abstract double CurrencyVariance(String currency) throws CurrencyNotFoundException;
	public abstract Currency DailyLower(Calendar date) throws DateNotFoundException;
}

