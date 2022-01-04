package BaseClasses;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;
import java.util.Vector;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Investment extends Currency{
	private Calendar investmentDate;
	private String investorName;
	private double amount;
	private Vector<Double> historicalEarnings= new Vector<Double>();
	private final File file= new File(" src/main/java/DataService/Investment.json");
	
	public Investment(String name, double value, String investorName, double amount,Calendar date) {
		super(name, value);
		this.investorName = investorName;
		this.amount = amount;
		this.investmentDate=date;
	}//inserisci altri costruttori
	public String getInvestorName() {
		return investorName;
	}
	public void setInvestorName(String investorName) {
		this.investorName = investorName;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public Calendar getInvestmentDate() {
		return investmentDate;
	}
	public void setInvestmentDate(Calendar investmentDate) {
		this.investmentDate = investmentDate;
	}
	public Vector<Double> getHistoricalEarnings() {
		return historicalEarnings;
	}
	public void setHistoricalEarnings(Vector<Double> historicalEarnings) {
		this.historicalEarnings = historicalEarnings;
	}
	
	
	public void ReadInvestment(String name) {
		JSONParser jParser = new JSONParser();
		Calendar date = Calendar.getInstance();
		date.clear();
		String stringDate ="";
		 JSONArray list = null;
		 try(FileReader reader = new FileReader(file))
		 {
			 Object obj = jParser.parse(reader);
			 list = (JSONArray) obj;
			 for(int i=0;i<list.size();i++) {
				 JSONObject jObject= new JSONObject();
				 jObject=(JSONObject)list.get(i);
				 if(jObject.get("name").equals(name)) {
					 	stringDate = (String)((JSONObject) list.get(i)).get("date");
					 	String[] splitDate = stringDate.split("-");
					 date.set(Integer.parseInt(splitDate[0]),Integer.parseInt(splitDate[1]),Integer.parseInt(splitDate[2]));
					 this.amount=(double)jObject.get("amount");
					 this.investmentDate=date;
					 super.setName((String)jObject.get("currency"));
				 }
			 }
		 }catch (FileNotFoundException e) {
			 e.printStackTrace();
		 } catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	
	public Vector<Double> historicalEarnings(Vector<Double> historicalQuotes) {
		Vector<Double> earnings = new Vector<Double>();
		for(Double quotes : historicalQuotes) {
		earnings.add(quotes*this.amount);
		}
		return earnings;
		}

}
