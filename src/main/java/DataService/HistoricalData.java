package DataService;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Vector;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import BaseClasses.Currency;
import BaseClasses.DailyData;
import Exceptions.CurrencyNotFoundException;
import Exceptions.DateNotFoundException;

/**
 * Questa classe descrive il comportamento di una struttura dati contenente 
 * tutte le informazioni relative alle valute in un lasso di tempo
 */

public class HistoricalData implements CurrencyService,DataAnalysis{
	/**
	 * @param historicalData è un ArrayList che contiene tutti i dati storici
	 * relativi alle valute
	 */
	private ArrayList<DailyData> historicalData;
	/**
	 * @param EURCURRENCIES è una stringa che contiene tutte le valute di interesse
	 */
	private static final String EURCURRENCIES = "EURUSD,EURCHF,EURGBP,EURAUD,EURKYD,EURJPY,EURCNY";
	/**
	 * @ param eurCurrencies è un vettore di stringhe, è la lista delle valute di interesse
	 */
	private String[] eurCurrencies=EURCURRENCIES.split(",");
	/**
	 * Metodo get per l'utilizzo dell'ArrayList historical data all'esterno della classe
	 * @return restituisce tutte le informazioni sulle valute in un ArrayList con elementi
	 * di classe DailyData
	 */
	public ArrayList<DailyData> getHistoricalData() {
		return historicalData;
	}
	/**
	 * Metodo set per la modifica dell'ArrayList historicalData 
	 * @param historicalData ArrayList con elementi di classe DailyData
	 *  con i nuovi dati sulle monete
	 */
	public void setHistoricalData(ArrayList<DailyData> historicalData) {
		this.historicalData = historicalData;
	}
	/**
	 * Costruttore della classe HistoricalData
	 * @param historicalData è la struttura dati contenente tutte le informazioni sulle valute
	 */
	public HistoricalData(ArrayList<DailyData> historicalData) {
		this.historicalData = historicalData;
	}
	/**
	 * Metodo per la conversione dei valori delle monete, l'API Currencylayer permette
	 * di ottenere i tassi di cambio in base al dollaro, con questo metodo vengono convertiti
	 * sulla base dell'euro
	 */
	public void ConvertData() {
		double eurUsd=1;
		for(int i=0;i<historicalData.size();i++) {
			DailyData daily= new DailyData();
			daily=historicalData.get(i);
			if(i==0)
				eurUsd=1/daily.getCurrencies().get(i).getValue();
			for(int j=0;j<daily.getCurrencies().size();j++) {
				double curUsd=1;
				if(j!=0)
					curUsd=daily.getCurrencies().get(j).getValue();
				daily.getCurrencies().get(j).setName(eurCurrencies[j]);
				daily.getCurrencies().get(j).setValue(eurUsd*curUsd);
			}
			this.historicalData.set(i, daily);
		}
	}
	/**
	 * Metodo per il calcolo del valore medio di una moneta
	 * @param currency è il nome della moneta di cui si richiede il calcolo
	 * della media
	 * @return restituisce il valore della media
	 */
	public double CurrencyAverage(String currency) throws CurrencyNotFoundException{
		boolean flag = true;
		double average=0;
		for(DailyData daily: historicalData) {
			for(int i=0;i<daily.getCurrencies().size();i++) {
				if(daily.getCurrencies().get(i).getName().equals(currency)) {
					average+=daily.getCurrencies().get(i).getValue();
					flag = false;
				}
			}
		}
		if(flag)
			throw new CurrencyNotFoundException("currency not found");
		return average/historicalData.size();
	}
	/**
	 * Metodo per il calcolo della varianza di una moneta
	 * @param currency è il nome della moneta di cui si richiede il calcolo
	 * della varianza
	 * @return restituisce il valore della varianza
	 */
	public double CurrencyVariance(String currency) throws CurrencyNotFoundException{
		boolean flag = true;
		double variance=0;
		for(DailyData daily: historicalData) {
			for(int i=0;i<daily.getCurrencies().size();i++) {
				if(daily.getCurrencies().get(i).getName().equals(currency)) {
					variance+=Math.pow(daily.getCurrencies().get(i).getValue()-CurrencyAverage(currency),2);
					flag = false;
				}
			}
		}
		if(flag)
			throw new CurrencyNotFoundException("currency not found");
		return variance/(historicalData.size()-1);
	}
	/**
	 * Metodo per il calcolo della moneta con il valore più basso in un dato giorno
	 * @param date è la data di cui si vuole trovare la moneta con valore più basso
	 * @return restituisce un oggetto di classe Currency che nel giorno richiesto ha
	 * registrato il valore più basso
	 */
	public Currency DailyLower(Calendar date) throws DateNotFoundException{
		boolean flag = true;
		Currency lower = new Currency();
		int month = date.get(Calendar.MONTH)+1;
		String dateString = "" + date.get(Calendar.YEAR)+"-" + month +"-"+ date.get(Calendar.DATE);
		for(DailyData daily: historicalData)
			if(daily.toStringDate().equals(dateString)) {
				flag = false;
				for(int i=0;i<daily.getCurrencies().size();i++) {
					if(i==0) {
						lower.setName(daily.getCurrencies().get(i).getName());
						lower.setValue(daily.getCurrencies().get(i).getValue());
					}
					else if(daily.getCurrencies().get(i).getValue()<lower.getValue()){
						lower.setName(daily.getCurrencies().get(i).getName());
						lower.setValue(daily.getCurrencies().get(i).getValue());
					}
				}
			}
		if(flag)
			throw new DateNotFoundException("date not found");
		return lower;
	}
	
	/**
	 * Metodo get per avere un resoconto sulle statistiche di una valuta
	 * @param currency è una stringa contenente il nome della valuta di cui si
	 * richiedono le statistiche
	 * @return restituisce le informazioni della valuta all' interno di un JSONObject
	 */
	@SuppressWarnings("unchecked")
	public JSONObject getCurrencyStats(String currency) throws CurrencyNotFoundException {
		JSONObject stats = new JSONObject();
		stats.put("Variance",CurrencyVariance(currency));
		stats.put("Average",CurrencyAverage(currency));
		return stats;
	}
	/**
	 * Metodo get per avere i dati delle valute relativi ad un giorno specifico
	 * @param date è la data di cui si richiedono i dati
	 * @return restituisce un JSONObject contenente i dati sulle valute relativi al giorno indicato
	 */
	@SuppressWarnings("unchecked")
	public JSONObject getDailyCurrencies(Calendar date) throws DateNotFoundException {
		boolean flag = true;
		JSONObject dailyCurrency = new JSONObject();
		int month = date.get(Calendar.MONTH)+1;
		String dateString = "" + date.get(Calendar.YEAR)+"-" + month +"-"+ date.get(Calendar.DATE);
		for(DailyData daily: historicalData)
			if(daily.toStringDate().equals(dateString)) {
				flag = false;
				dailyCurrency.put("date", dateString);
				for(int j=0;j<daily.getCurrencies().size();j++)
					dailyCurrency.put('"'+daily.getCurrencies().get(j).getName()+'"',daily.getCurrencies().get(j).getValue());
			}
		dailyCurrency.put('"'+"lower: "+DailyLower(date).getName()+'"',DailyLower(date).getValue());
		if(flag)
			throw new DateNotFoundException("date not found");
		return dailyCurrency;
	}
	/**
	 * Metodo get che permette di ricevere le quote di una valuta nell'arco dei 5 giorni
	 * @param currency  è una stringa contenente il nome della valuta di cui si
	 * richiedono le statistiche
	 * @return restituisce le informazioni della valuta all' interno di un JSONObject	
	 * @throws CurrencyNotFoundException
	 */
	@SuppressWarnings("unchecked")
	public JSONArray getCurrencyValues(String currency) throws CurrencyNotFoundException{
		boolean flag = true;
		JSONArray allCurrencies = new JSONArray();
		for(DailyData daily: historicalData) {
			JSONObject dailyCurrency = new JSONObject();
			dailyCurrency.put("date", daily.toStringDate());
			for(int j=0;j<daily.getCurrencies().size();j++)
				if(daily.getCurrencies().get(j).getName().equals(currency)) {
					dailyCurrency.put('"'+currency+'"',daily.getCurrencies().get(j).getValue());
					flag = false;
				}
			allCurrencies.add(dailyCurrency);
		}
		if(flag)
			throw new CurrencyNotFoundException("currency not found");
		return allCurrencies;
	}
	/**
	 * Metodo get per l'utilizzo dell'ArrayList historicalData al di fuori della classe
	 * @return restituisce i dati storici in formato JSONArray
	 */
	@SuppressWarnings("unchecked")
	public JSONArray getAllCurrencies() {
		JSONArray allCurrencies = new JSONArray();
		for(DailyData daily: historicalData) {
				try {
					allCurrencies.add(getDailyCurrencies(daily.getDate()));
				} catch (DateNotFoundException e) {
					e.printStackTrace();
				}
		}
		return allCurrencies;
	}
	/**
	 * Metodo get che permette di ricevere le quote di una valuta in un lasso di tempo specificato
	 * @param name è il nome della valuta di cui si richiedono i valori
	 * @param days è il numero di giorni di cui si richiedono i dati, partendo dal giorno corrente
	 * @return restituisce il vettore contenente i valori della valuta richiesti
	 */
	public Vector<Double> getCurrencyQuotes(String name,int days) throws CurrencyNotFoundException{
		boolean flag = true;
		Vector<Double> currency= new Vector<Double>();
		for(int i=0;i<days;i++)
			for(int j=0;j<historicalData.get(i).getCurrencies().size();j++)
				if(historicalData.get(i).getCurrencies().get(j).getName().equals(name)) {
					currency.add(historicalData.get(i).getCurrencies().get(j).getValue());
					flag = false;
				}
		if(flag)
			throw new CurrencyNotFoundException("currency not found");
		return currency;
	}
}
