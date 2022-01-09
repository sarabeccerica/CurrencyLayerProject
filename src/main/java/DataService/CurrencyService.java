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
 * @author Attili Loris
 */

public interface CurrencyService {
	/**
	 * Metodo get per l'utilizzo della struttura dati historicalData al di fuori della classe
	 * @return restituisce i dati storici in formato JSONArray
	 */
	public abstract JSONArray getAllCurrencies();
	/**
	 * Metodo get per avere un resoconto sulle statistiche di una valuta
	 * @param currency e' una stringa contenente il nome della valuta di cui si
	 * richiedono le statistiche
	 * @return restituisce le informazioni della valuta all' interno di un JSONObject
	 * @throws CurrencyNotFoundException se non e' possibile trovare la valuta richiesta
	 */
	public abstract JSONObject getCurrencyStats(String currency) throws CurrencyNotFoundException;
	/**
	 * Metodo get che permette di ricevere le quote di una valuta nell'arco dei 5 giorni
	 * @param currency  e' una stringa contenente il nome della valuta di cui si
	 * richiedono le statistiche
	 * @return restituisce le informazioni della valuta all' interno di un JSONObject	
	 * @throws CurrencyNotFoundException se non e' possibile trovare la valuta richiesta
	 */
	public abstract JSONArray getCurrencyValues(String currency) throws CurrencyNotFoundException;
	/**
	 * Metodo get per avere i dati delle valute relativi ad un giorno specifico
	 * @param date e' la data di cui si richiedono i dati
	 * @return restituisce un JSONObject contenente i dati sulle valute relativi al giorno indicato
	 * @throws DateNotFoundException se non e' possibile trovare la data richiesta
	 */
	public abstract JSONObject getDailyCurrencies(Calendar date) throws DateNotFoundException;
	/**
	 * Metodo get che permette di ricevere i valori di una valuta in un lasso di tempo specificato
	 * @param name e' il nome della valuta di cui si richiedono i valori
	 * @param days e' il numero di giorni di cui si richiedono i dati, partendo dal giorno corrente
	 * @return restituisce il vettore contenente i valori della valuta richiesti
	 * @throws CurrencyNotFoundException se non e' possibile trovare la valuta richiesta
	 */
	public abstract Vector<Double> getCurrencyQuotes(String name,int days) throws CurrencyNotFoundException;
}
