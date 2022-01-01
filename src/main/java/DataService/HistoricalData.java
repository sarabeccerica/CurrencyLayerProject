package DataService;

import java.util.ArrayList;
import java.util.Calendar;

import org.json.simple.JSONObject;

import BaseClasses.Currency;
import BaseClasses.DailyData;

public class HistoricalData{
	private ArrayList<DailyData> historicalData;

	public ArrayList<DailyData> getHistoricalData() {
		return historicalData;
	}

	public void setHistoricalData(ArrayList<DailyData> historicalData) {
		this.historicalData = historicalData;
	}

	public HistoricalData(ArrayList<DailyData> historicalData) {
		this.historicalData = historicalData;
	}
	
}
