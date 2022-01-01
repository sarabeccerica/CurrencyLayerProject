package BaseClasses;

import java.util.ArrayList;
import java.util.Calendar;

public class DailyData {
	private Calendar date;
	private ArrayList<Currency> currencies;
	
	public DailyData(Calendar date, ArrayList<Currency> currencies) {
		this.date = date;
		this.currencies = currencies;
	}
	
	public DailyData() {
		this.currencies = null;
		this.date = null;
	}

	public Calendar getDate() {
		return date;
	}

	public void setDate(Calendar date) {
		this.date = date;
	}

	public ArrayList<Currency> getCurrencies() {
		return currencies;
	}

	public void setCurrencies(ArrayList<Currency> currencies) {
		this.currencies = currencies;
	}

	public String toStringDate() {
		return ""+date.get(Calendar.YEAR)+"-" + date.get(Calendar.MONTH) +"-"+ date.get(Calendar.DATE);
	}
}
