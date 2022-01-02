package Controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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
	
}
