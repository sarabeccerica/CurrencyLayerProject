package Controller;

import java.util.Calendar;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import CallToAPI.ReadFile;
import DataService.HistoricalData;

public class Controller {

	ReadFile call = new ReadFile();
	HistoricalData data = new HistoricalData(call.readFile());

	@RequestMapping(value ="/quotes",method = RequestMethod.GET)
	public ResponseEntity<Object> requestAllCurrencies() {
		data.ConvertData();
		return new ResponseEntity<Object>(data.getAllCurrencies(),HttpStatus.OK);
	}

	@RequestMapping(value ="/dailyQuotes",method = RequestMethod.GET)
	public ResponseEntity<Object> requestDailyCurrencies(@RequestParam("year") int year,
														 @RequestParam("month") int month,
														 @RequestParam("day") int day){
		data.ConvertData();
		Calendar date = Calendar.getInstance();
		date.set(year, month, day);
		return new ResponseEntity<Object>(data.getDailyCurrencies(date),HttpStatus.OK);
	}
	
}
