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
	}
	public Investment(String name, String investorName, double amount,Calendar date) {
		super(name,0);
		this.investorName = investorName;
		this.amount = amount;
		this.investmentDate=date;
	}
	
	public Investment() {
		super();
		this.amount=0;
		this.investmentDate=null;
		this.investorName=null;
	}
	
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
	@SuppressWarnings("unchecked")
	public boolean SaveInvestment() {
		JSONParser jParser = new JSONParser();
		JSONArray list = new JSONArray();
		try(FileReader reader = new FileReader(file))
		{
			if(file.length()!=0) 
				list = (JSONArray) jParser.parse(reader);
			if(!file.exists()) {
				file.createNewFile();
			}
			FileWriter writer = new FileWriter(file);
			JSONObject jObject = new JSONObject();
			String dateString = "" + investmentDate.get(Calendar.YEAR)+"-" + investmentDate.get(Calendar.MONTH) +"-"+ investmentDate.get(Calendar.DATE);
			jObject.put("name", investorName);
			jObject.put("date", dateString);
			jObject.put("currency", super.getName());
			jObject.put("amount", amount);
			list.add(jObject);
			writer.write(list.toJSONString());
			writer.close();
			return true;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return false;
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
	
	public void historicalEarnings(Vector<Double> historicalQuotes) {
		for(Double quotes : historicalQuotes)
		historicalEarnings.add(quotes*this.amount);
		
	}
	public int DaysNumber() {
		Calendar today = Calendar.getInstance();
		Calendar difference = Calendar.getInstance();
		difference.clear();
		difference.setTimeInMillis(today.getTimeInMillis()-this.investmentDate.getTimeInMillis());
		if(difference.get(Calendar.DAY_OF_YEAR)>5)
			return 5;
		return difference.get(Calendar.DAY_OF_YEAR);
	}
}
