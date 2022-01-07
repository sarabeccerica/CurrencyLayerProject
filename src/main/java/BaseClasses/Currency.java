package BaseClasses;

/**
 * Questa classe descrive una valuta, e gestendone il nome e il valore 
 */

public class Currency {
	/**
	 * @param name contiene il nome della moneta 
	 */
	private String name;
	/**
	 * @param value contiene il valore della moneta
	 */
	private double value;
	/**
	 * Costruttore per la classe Currency 
	 * @param name nome della valuta
	 * @param value valore della valuta
	 */
	public Currency(String name, double value) {
		this.name = name;
		this.value = value;
	}
	/**
	 * Costruttore della classe Currency senza inizializzazione dei
	 * parametri
	 */
	public Currency() {
		this.name="";
		this.value=0;
	}
	/**
	 * Questo metodo metodo get è necessario per l'utilizzo del nome della 
	 * valuta al di fuori della classe
	 * @return restituisce il nome della valuta
	 */
	public String getName() {
		return name;
	}
	/**
	 * Questo metodo set è necessario per modificare il nome di un'istanza
	 * della classe valuta
	 * @param name è il nuovo nome da assegnare
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * Questo metodo metodo get è necessario per l'utilizzo del valore della 
	 * valuta al di fuori della classe
	 * @return restituisce il valore della valuta
	 */
	public double getValue() {
		return value;
	}
	/**
	 * Questo metodo set è necessario per modificare il valore di un'istanza
	 * della classe valuta
	 * @param value è il nuovo valore da assegnare
	 */
	public void setValue(double value) {
		this.value = value;
	}
	
	
}
