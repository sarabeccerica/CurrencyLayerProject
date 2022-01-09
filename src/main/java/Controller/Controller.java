package Controller;

import java.util.Calendar;
import java.util.Vector;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import CallToAPI.ReadFile;
import DataService.HistoricalData;
import Exceptions.CurrencyNotFoundException;
import Exceptions.DateNotFoundException;
import BaseClasses.Investment;

/**
 *  Questa classe rappresenta un Controllerper gestire le chiamate HTTP inoltrate 
 *  dall'utente su una specifica rotta. Invia un oggetto ResponseEntity con i dati
 *  richiesti in formatoJson e il codice di stato HTTP
 *  @author Beccerica Sara
 */

@RestController
public class Controller {
	/**
	 * @param call contiene le informazioni ottenute dall'API Currencylayer
	 */
	//APICall call = new APICall();
	/**
	 * @param data contiene le informazioni sulle valute rielaborate 
	 */
	//HistoricalData data = new HistoricalData(obj.sendRequest());
	/**
	 * @param call contiene le infromazioni ottenute dalla lettura del file locale
	 */
	ReadFile call = new ReadFile();
	/**
	 * @param data contiene le informazioni sulle valute rielaborate 
	 */
	HistoricalData data = new HistoricalData(call.readFile());
	/**
	 * @param isConverted e' un flag necessario per sapere se la conversione delle quote
	 * e' stata eseguita
	 */
	boolean isConverted=false;
	/**
	 * Gestisce le chiamate inoltrate sulla rotta /convert
	 * @return restituisce una stringa che comunica che la conversione e' andata a buon fine
	 * oppure se era già stata eseguita
	 */
	@RequestMapping(value = "/convert",method = RequestMethod.GET)
	public String convertData(){
		if(!isConverted) {
			data.ConvertData();
			isConverted=true;
			return "Valute convertite da cambio USD  a cambio EUR";
		}
		return "Valute già convertite";
	}
	
	/**
	 * Gestisce le chiamate inoltrate sulla rotta /quotes
	 * @return restituisce tutti i dati sulle valute nel periodo degli ultimi 5 giorni.
	 */
	@RequestMapping(value ="/quotes",method = RequestMethod.GET)
	public ResponseEntity<Object> requestAllCurrencies() {
		return new ResponseEntity<Object>(data.getAllCurrencies(),HttpStatus.OK);
	}
	/**
	 * Gestisce le chiamate inoltrate sulla rotta /quotes/daily
	 * @param year e' un intero che indica l'anno della data in input
	 * @param month e' un intero che indica il mese della data in input
	 * @param day e' un intero che indica il giornp del mese della data in input
	 * @return restituisce i dati sulle valute relativi ad uno specifico giorno
	 * @throws DateNotFoundException se non e' possibile trovare la data richiesta
	 */
	@RequestMapping(value ="/quotes/daily",method = RequestMethod.GET)
	public ResponseEntity<Object> requestDailyCurrencies(@RequestParam("year") int year,
														 @RequestParam("month") int month,
														 @RequestParam("day") int day) throws DateNotFoundException{
		Calendar date = Calendar.getInstance();
		date.set(year, month-1, day);
		return new ResponseEntity<Object>(data.getDailyCurrencies(date),HttpStatus.OK);
	}
	/**
	 * Gestisce le chiamate inoltrate sulla rotta /quotes/analysis
	 * @param currency e' il nome della valuta di cui si richiedono le statistiche
	 * @return restituisce la media e la varianza di una valuta
	 * @throws CurrencyNotFoundException se non e' possibile trovare la valuta richiesta
	 */
	@RequestMapping(value ="/quotes/analysis",method = RequestMethod.GET)
	public ResponseEntity<Object> requestCurrencyStats (@RequestParam("name") String currency) throws CurrencyNotFoundException{
		return new ResponseEntity<Object>(data.getCurrencyStats(currency),HttpStatus.OK);
	}
	/**
	 * Gestisce le chiamate inoltrate sulla rotta /quotes/analysis
	 * @param currency e' il nome della valuta di cui si richiedono le quote
	 * @return restituisce un vettore di double contenente le quote della valuta
	 * @throws CurrencyNotFoundException se non e' possibile trovare la valuta richiesta
	 */
	@RequestMapping(value = "/quotes/currency",method = RequestMethod.GET)
	public ResponseEntity<Object> requestCurrencyQuotes(@RequestParam("name") String currency) throws CurrencyNotFoundException{
		return new ResponseEntity<Object>(data.getCurrencyValues(currency),HttpStatus.OK);
	}
	/**
	 * Gestisce le chiamate inoltrate sulla rotta /investment
	 * Permette di creare un nuovo investimento
	 * @param name e' il nome dell'investitore
	 * @param currency e' la valuta su cui si vuole investire
	 * @param amount e' la somma di denaro che si vuole investire
	 * @return restituisce una stringa che riferisce se l'investimento e'
	 * stato eseguito o meno
	 */
	@RequestMapping(value ="/investment",method = RequestMethod.GET) 
	public String newInvestment(@RequestParam("name") String name,
								@RequestParam("currency") String currency,
								@RequestParam("amount") double amount) {
		Calendar date = Calendar.getInstance();
		Investment inv = new Investment(currency, name, amount,date);
		if(inv.SaveInvestment())
			return "investimento fatto";
		return "non fatto";
	}
	/**
	 * Gestisce le chiamate inoltrate sulla rotta /investment/earning
	 * comunica i guadagno storici relativi ad un investimento
	 * @param name e' il nome dell'investitore di cui si richiedono i dati
	 * @return restituisce un vettore con i guadagni di ogni giornata
	 * @throws CurrencyNotFoundException se non e' possibile trovare la valuta richiesta
	 */
	@RequestMapping(value ="/investment/earning",method = RequestMethod.GET) 
	public Vector<Double> historicalEarning(@RequestParam("investor") String name) throws CurrencyNotFoundException{
		Investment investment = new Investment();
		investment.ReadInvestment(name);
		investment.historicalEarnings(data.getCurrencyQuotes(investment.getName(),investment.DaysNumber()));
		return investment.getHistoricalEarnings();
	}
	
}
