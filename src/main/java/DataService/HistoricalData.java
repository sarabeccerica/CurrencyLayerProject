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
	public double CurrencyAverage(String currency) {
		double average=0;
		for(DailyData daily: historicalData) {
			for(int i=0;i<daily.getCurrencies().size();i++) {
				if(daily.getCurrencies().get(i).getName().equals(currency))
					average+=daily.getCurrencies().get(i).getValue();
			}
		}
		return average/historicalData.size();
	}
	
	public double CurrencyVariance(String currency) {
		double variance=0;
		for(DailyData daily: historicalData) {
			for(int i=0;i<daily.getCurrencies().size();i++) {
				if(daily.getCurrencies().get(i).getName().equals(currency))
					variance+=Math.pow(daily.getCurrencies().get(i).getValue()-CurrencyAverage(currency),2);
			}
		}
		return variance/(historicalData.size()-1);
	}
}
