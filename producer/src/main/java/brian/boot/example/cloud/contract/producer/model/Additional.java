package brian.boot.example.cloud.contract.producer.model;

public class Additional {

	private String interest;
	private boolean drink;
	private boolean smoke;

	private Additional(){}
	
	public Additional(String interest, boolean drink, boolean smoke) {
		super();
		this.interest = interest;
		this.drink = drink;
		this.smoke = smoke;
	}

	public String getInterest() {
		return interest;
	}
	public void setInterest(String interest) {
		this.interest = interest;
	}
	public boolean isDrink() {
		return drink;
	}
	public void setDrink(boolean drink) {
		this.drink = drink;
	}
	public boolean isSmoke() {
		return smoke;
	}
	public void setSmoke(boolean smoke) {
		this.smoke = smoke;
	}
}
