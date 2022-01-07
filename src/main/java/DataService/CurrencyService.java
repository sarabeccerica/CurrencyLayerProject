package DataService;

import java.util.Calendar;
import java.util.Vector;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import Exceptions.CurrencyNotFoundException;
import Exceptions.DateNotFoundException;

/**
 * Questa interfaccia descrive come una funzione che la implementa
 * deve gestire i servizi relativi alle valute
 */

public interface CurrencyService {
	/**
	 * Metodo get per l'utilizzo della struttura dati historicalData al di fuori della classe
	 * @return restituisce i dati storici in formato JSONArray
	 */
	public abstract JSONArray getAllCurrencies();
	/**
	 * Metodo get per avere un resoconto sulle statistiche di una valuta
	 * @param currency è una stringa contenente il nome della valuta di cui si
	 * richiedono le statistiche
	 * @return restituisce le informazioni della valuta all' interno di un JSONObject
	 * @throws CurrencyNotFoundException
	 */
	public abstract JSONObject getCurrencyStats(String currency) throws CurrencyNotFoundException;
	/**
	 * Metodo get che permette di ricevere le quote di una valuta nell'arco dei 5 giorni
	 * @param currency  è una stringa contenente il nome della valuta di cui si
	 * richiedono le statistiche
	 * @return restituisce le informazioni della valuta all' interno di un JSONObject	
	 * @throws CurrencyNotFoundException
	 */
	public abstract JSONArray getCurrencyValues(String currency) throws CurrencyNotFoundException;
	/**
	 * Metodo get per avere i dati delle valute relativi ad un giorno specifico
	 * @param date è la data di cui si richiedono i dati
	 * @return restituisce un JSONObject contenente i dati sulle valute relativi al giorno indicato
	 * @throws DateNotFoundException
	 */
	public abstract JSONObject getDailyCurrencies(Calendar date) throws DateNotFoundException;
	/**
	 * Metodo get che permette di ricevere i valori di una valuta in un lasso di tempo specificato
	 * @param name è il nome della valuta di cui si richiedono i valori
	 * @param days è il numero di giorni di cui si richiedono i dati, partendo dal giorno corrente
	 * @return restituisce il vettore contenente i valori della valuta richiesti
	 * @throws CurrencyNotFoundException
	 */
	public abstract Vector<Double> getCurrencyQuotes(String name,int days) throws CurrencyNotFoundException;
}
