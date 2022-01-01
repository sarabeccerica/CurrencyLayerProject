package BaseClasses;

public class Currency {
	private String name;
	private double value;
	
	public Currency(String name, double value) {
		this.name = name;
		this.value = value;
	}
	public Currency() {
		this.name="";
		this.value=0;
	}

	public String toString() {
		return "Currency [name=" + name + ", value=" + value + "]";
	}

	
	public String getName() {
		System.out.println("ciao");
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public double getValue() {
		return value;
	}
	public void setValue(double value) {
		this.value = value;
	}
	
	
}
