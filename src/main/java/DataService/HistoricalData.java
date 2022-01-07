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

public class HistoricalData implements CurrencyService,DataAnalysis{
	private ArrayList<DailyData> historicalData;
	private static final String EURCURRENCIES = "EURUSD,EURCHF,EURGBP,EURAUD,EURKYD,EURJPY,EURCNY";
	private String[] eurCurrencies=EURCURRENCIES.split(",");

	public ArrayList<DailyData> getHistoricalData() {
		return historicalData;
	}

	public void setHistoricalData(ArrayList<DailyData> historicalData) {
		this.historicalData = historicalData;
	}

	public HistoricalData(ArrayList<DailyData> historicalData) {
		this.historicalData = historicalData;
	}

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
	

	@SuppressWarnings("unchecked")
	public JSONObject getCurrencyStats(String currency) throws CurrencyNotFoundException {
		JSONObject stats = new JSONObject();
		stats.put("Variance",CurrencyVariance(currency));
		stats.put("Average",CurrencyAverage(currency));
		return stats;
	}

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
					dailyCurrency.put('"'+eurCurrencies[j]+'"',daily.getCurrencies().get(j).getValue());
			}
		dailyCurrency.put('"'+"lower: "+DailyLower(date).getName()+'"',DailyLower(date).getValue());
		if(flag)
			throw new DateNotFoundException("date not found");
		return dailyCurrency;
	}
	
	@SuppressWarnings("unchecked")
	public JSONArray getCurrencyValues(String currency) throws CurrencyNotFoundException{
		boolean flag = true;
		JSONArray allCurrencies = new JSONArray();
		JSONObject dailyCurrency = new JSONObject();
		for(DailyData daily: historicalData) {
			dailyCurrency.put("date", daily.toStringDate());
			for(int j=0;j<daily.getCurrencies().size();j++)
				if(daily.getCurrencies().get(j).getName().equals(currency)) {
					dailyCurrency.put('"'+"EUR"+currency+'"',daily.getCurrencies().get(j).getValue());
					flag = false;
				}
			allCurrencies.add(dailyCurrency);
		}
		if(flag)
			throw new CurrencyNotFoundException("currency not found");
		return allCurrencies;
	}

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
