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

/**
 * Questa classe descrive e modella il comportamento di un investimento
 * ereditando dalla classe Currency la moneta su cui si è svolto l'investimento
 *
 */

public class Investment extends Currency{
	/**
	 * @param investmentDate contiene la data in cui è stato fatto l'investimento
	 */
	private Calendar investmentDate;
	/**
	 * @param investorName contiene il nome dell'investitore
	 */
	private String investorName;
	/**
	 * @param amount contiene il totale di denaro investito
	 */
	private double amount;
	/**
	 * @param historicalEarnings contiene un vettore di Double che registra
	 * i guadagni storici
	 */
	private Vector<Double> historicalEarnings= new Vector<Double>();
	/**
	 * @param file contiene il documento con i dati relativi all'investimento
	 */
	private final File file= new File("src/main/java/BaseClasses/Investments.json");
	/**
	 * Costruttore della classe Investment
	 * @param name name è il nome della valuta
	 * @param value value è il valore della valuta al momento dell'investimento
	 * @param investorName investorName è il nome dell'investitore
	 * @param amount amount è il totale di denaro investito
	 * @param date date è la data in cui è stato effettuato l'investimento
	 */
	public Investment(String name, double value, String investorName, double amount,Calendar date) {
		super(name, value);
		this.investorName = investorName;
		this.amount = amount;
		this.investmentDate=date;
	}
	/**
	 * Costruttore della classe Investment senza il valore della valuta
	 * @param name name è il nome della valuta
	 * @param investorName investorName è il nome dell'investitore
	 * @param amount amount è il totale di denaro investito
	 * @param date date è la data in cui è stato effettuato l'investimento
	 */
	public Investment(String name, String investorName, double amount,Calendar date) {
		super(name,0);
		this.investorName = investorName;
		this.amount = amount;
		this.investmentDate=date;
	}
	/**
	 * Costruttore della classe Investment in assenza di parametri in input
	 */
	public Investment() {
		super();
		this.amount=0;
		this.investmentDate=null;
		this.investorName=null;
	}
	/**
	 * Metodo get per l'utilizzo dell'inverstorName al di fuori della classe
	 * @return restituisce il nome dell'investitore all'interno di una stringa
	 */
	public String getInvestorName() {
		return investorName;
	}
	/**
	 * Metodo set per la modifica dell'attributo investorName all'esterno della classe
	 * @param investorName contiene il nome dell'investitore
	 */
	public void setInvestorName(String investorName) {
		this.investorName = investorName;
	}
	/**
	 * Metodo get per l'utilizzo dell'attributo amount al di fuori della classe
	 * @return restituisce il totale di denaro investito
	 */
	public double getAmount() {
		return amount;
	}
	/**
	 * Metodo set per la modifica dell'attributo amount all'esterno della classe
	 * @param amount contiene il totale di denaro investito
	 */
	public void setAmount(double amount) {
		this.amount = amount;
	}
	/**
	 * Metodo get per l'utilizzo di investmentDate all'esterno della classe
	 * @return restituisce la data in cui è stato effettuato l'investimento
	 */
	public Calendar getInvestmentDate() {
		return investmentDate;
	}
	/**
	 * Metodo set per la modifica di investmentDate all'esterno della classe
	 * @param investmentDate contiene la data in cui è stato effettuato l'investimento
	 */
	public void setInvestmentDate(Calendar investmentDate) {
		this.investmentDate = investmentDate;
	}
	/**
	 * Metodo get per l'utilizzo di historicalEarnings all'esterno della classe
	 * @return historicalEarnings contiene i valori dei guadagni storici
	 */
	public Vector<Double> getHistoricalEarnings() {
		return historicalEarnings;
	}
	/**
	 * Metodo set per la modifica del vettore dei guadagni storici
	 * @param historicalEarnings historicslEarnings contiene i valori dei guadagni storici
	 */
	public void setHistoricalEarnings(Vector<Double> historicalEarnings) {
		this.historicalEarnings = historicalEarnings;
	}
	/**
	 * metodo per il salvataggio dell'istanza di Investment su file
	 * @return restituisce true in caso il salvataggio sia andato a buon fine,
	 * false in caso contrario
	 */
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
			int month = investmentDate.get(Calendar.MONTH)+1;
			String dateString = "" + investmentDate.get(Calendar.YEAR)+"-" + month +"-"+ investmentDate.get(Calendar.DATE);
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
	/**
	 * Metodo per la lettura del file da cui ricavare gli attributi di un
	 * investitore il cui nome viene dato in input
	 * @param name contiene il nome dell'investitore
	 */
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
	/**
	 * Metodo per la modifica del vettore dei guadagni storici 
	 * @param historicalQuotes contiene le quote delle valute
	 */
	public void historicalEarnings(Vector<Double> historicalQuotes) {
		for(Double quotes : historicalQuotes)
		historicalEarnings.add(quotes*this.amount);
	}
	/**
	 * Metodo per il calcolo dei giorni passati dalla data dell'investimento
	 * @return restituisce un intero che indica il numero di giorni trascorsi
	 * dalla data dell'investimento
	 */
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
