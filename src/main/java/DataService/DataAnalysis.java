package DataService;

import java.util.ArrayList;
import java.util.Calendar;
import BaseClasses.Currency;
import BaseClasses.DailyData;
import Exceptions.CurrencyNotFoundException;
import Exceptions.DateNotFoundException;

/**
 * Questa interfaccia descrive come una classe che la implementa
 * deve gestire i dati e le operazioni sulle valute
 */

public interface DataAnalysis {
	/**
	 * Metodo per la conversione dei valori delle monete, l'API Currencylayer permette
	 * di ottenere i tassi di cambio in base al dollaro, con questo metodo vengono convertiti
	 * sulla base dell'euro
	 */
	public abstract void ConvertData(ArrayList<DailyData> allData);
	/**
	 * Metodo per il calcolo del valore medio di una moneta
	 * @param currency è il nome della moneta di cui si richiede il calcolo
	 * della media
	 * @return restituisce il valore della media
	 */
	public abstract double CurrencyAverage(String currency) throws CurrencyNotFoundException;
	/**
	 * Metodo per il calcolo della varianza di una moneta
	 * @param currency è il nome della moneta di cui si richiede il calcolo
	 * della varianza
	 * @return restituisce il valore della varianza
	 */
	public abstract double CurrencyVariance(String currency) throws CurrencyNotFoundException;
	/**
	 * Metodo per il calcolo della moneta con il valore più basso in un dato giorno
	 * @param date è la data di cui si vuole trovare la moneta con valore più basso
	 * @return restituisce un oggetto di classe Currency che nel giorno richiesto ha
	 * registrato il valore più basso
	 */
	public abstract Currency DailyLower(Calendar date) throws DateNotFoundException;
}

