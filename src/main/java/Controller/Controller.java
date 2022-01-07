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

@RestController
public class Controller {

	ReadFile call = new ReadFile();
	HistoricalData data = new HistoricalData(call.readFile());

	@RequestMapping(value ="/quotes",method = RequestMethod.GET)
	public ResponseEntity<Object> requestAllCurrencies() {
		data.ConvertData();
		return new ResponseEntity<Object>(data.getAllCurrencies(),HttpStatus.OK);
	}

	@RequestMapping(value ="/quotes/daily",method = RequestMethod.GET)
	public ResponseEntity<Object> requestDailyCurrencies(@RequestParam("year") int year,
														 @RequestParam("month") int month,
														 @RequestParam("day") int day) throws DateNotFoundException{
		data.ConvertData();
		Calendar date = Calendar.getInstance();
		date.set(year, month, day);
		return new ResponseEntity<Object>(data.getDailyCurrencies(date),HttpStatus.OK);
	}
	
	@RequestMapping(value ="/quotes/analysis",method = RequestMethod.GET)
	public ResponseEntity<Object> requestCurrencyStats (@RequestParam("name") String currency) throws CurrencyNotFoundException{
		data.ConvertData();
		return new ResponseEntity<Object>(data.getCurrencyStats(currency),HttpStatus.OK);
	}
	
	@RequestMapping(value = "/quotes/currency",method = RequestMethod.GET)
	public ResponseEntity<Object> requestCurrencyQuotes(@RequestParam("name") String currency) throws CurrencyNotFoundException{
		data.ConvertData();
		return new ResponseEntity<Object>(data.getCurrencyValues(currency),HttpStatus.OK);
	}
	
	@RequestMapping(value ="/investment",method = RequestMethod.GET) 
	public String newInvestment(@RequestParam("name") String name,
								@RequestParam("currency") String currency,
								@RequestParam("amount") double amount) {
		data.ConvertData();
		Calendar date = Calendar.getInstance();
		Investment inv = new Investment(currency, name, amount,date);
		if(inv.SaveInvestment())
			return "investimento fatto";
		return "non fatto";
	}
	
	@RequestMapping(value ="/investment/earning",method = RequestMethod.GET) 
	public Vector<Double> historicalEarning(@RequestParam("investor") String name) throws CurrencyNotFoundException{
		data.ConvertData();
		Investment investment = new Investment();
		investment.ReadInvestment(name);
		investment.historicalEarnings(data.getCurrencyQuotes(investment.getName(),investment.DaysNumber()));
		return investment.getHistoricalEarnings();
	}
	
}
