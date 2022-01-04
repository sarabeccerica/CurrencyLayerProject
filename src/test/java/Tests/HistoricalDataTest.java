package Tests;

import java.util.ArrayList;
import java.util.Calendar;

import org.junit.jupiter.api.Test;

import BaseClasses.Currency;
import BaseClasses.DailyData;
import DataService.HistoricalData;
// da implementare import Exceptions.DateNotFound;
import junit.framework.TestCase;

public class HistoricalDataTest extends TestCase{
	private HistoricalData allData;
	Calendar date1= Calendar.getInstance();
	Calendar date2= Calendar.getInstance();
	public HistoricalDataTest(String name){ super(name); }
	public void setUp(){
		Currency eur= new Currency("eur",1);
		Currency usd= new Currency("usd",1.4);
		Currency jpy= new Currency("jpy",1.2);
		Currency aud= new Currency("aud",1.3);
		ArrayList<Currency> curr= new ArrayList<Currency>();
		curr.add(eur);
		curr.add(aud);
		ArrayList<Currency> curr2= new ArrayList<Currency>();
		curr2.add(usd);
		curr2.add(jpy);

		date2.add(Calendar.DAY_OF_MONTH,-1);
		DailyData day1= new DailyData(date1,curr);
		DailyData day2= new DailyData(date2,curr2);
		ArrayList<DailyData> data= new ArrayList<DailyData>();
		data.add(day1);
		data.add(day2);
		allData= new HistoricalData(data);
	}
	@Test
	public void test(){// throws DateNotFound{
		Currency right1= new Currency("eur",1);
		Currency right2= new Currency("jpy",1.2);
		assertEquals(allData.DailyLower(date1),right1);
		assertEquals(allData.DailyLower(date2),right2);
	}
}

