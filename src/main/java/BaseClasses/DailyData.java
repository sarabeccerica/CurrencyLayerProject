package BaseClasses;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Questa classe modella il comportamento di una struttura dati
 * contenente le valute e il loro valore in una specifica data
 * @author Attili Loris
 */

public class DailyData {
	/**
	 * @param date contiene la data a cui si riferiscono le valute
	 */
	private Calendar date;
	/**
	 * @param currencies e' un ArrayList con elementi della classe Currency
	 * che contengono i valori delle valute relativi all'attributo date 
	 */
	private ArrayList<Currency> currencies;
	/**
	 * Costruttore della classe 
	 * @param date e' la data a cui si riferiscono i valori
	 * @param currencies e' un ArrayList con le valute
	 */
	public DailyData(Calendar date, ArrayList<Currency> currencies) {
		this.date = date;
		this.currencies = currencies;
	}
	/**
	 * Costruttore della classe senza parametri in ingresso
	 */
	public DailyData() {
		this.currencies = null;
		this.date = null;
	}
	/**
	 * Metodo get per la data
	 * @return restituisce la data in formato Calendar 
	 */
	public Calendar getDate() {
		return date;
	}
	/**
	 * Metodo set necessario per modificare la data dall'esterno della classe
	 * @param date e' la nuova data da impostare
	 */
	public void setDate(Calendar date) {
		this.date = date;
	}
	/**
	 * Metodo get per utilizzare l'ArrayList al di fuori della classe
	 * @return restituisce l'intera ArrayList di valute
	 */
	public ArrayList<Currency> getCurrencies() {
		return currencies;
	}
	/**
	 * Metodo set necessario per modificare l'ArrayList dall'esterno della classe
	 * @param currencies e' la nuova lista da impostare
	 */
	public void setCurrencies(ArrayList<Currency> currencies) {
		this.currencies = currencies;
	}
	/**
	 * Questo metodo restituisce il valore della data all'interno di una stringa
	 * @return Restituisce il valore della data all'interno di una stringa nel formato
	 * yyyy-mm-dd
	 */
	public String toStringDate() {
		int month = date.get(Calendar.MONTH)+1;
		return ""+date.get(Calendar.YEAR)+"-" + month +"-"+ date.get(Calendar.DATE);
	}
}
