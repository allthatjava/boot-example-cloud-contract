package brian.boot.example.cloud.contract.producer.model;

public class Customer {
	private String custId;
	private String firstName;
	private String lastName;
	private int age;
	private String phoneNo;
	private Additional additional;
	
	private Customer() {}
	
	public Customer(String custId, String firstName, String lastName, int age, String phoneNumber, Additional additional) {
		super();
		this.custId = custId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.age = age;
		this.phoneNo = phoneNumber;
		this.additional = additional;
	}
	
	public Additional getAdditional() {
		return additional;
	}

	public void setAdditional(Additional additional) {
		this.additional = additional;
	}

	public String getCustId() {
		return custId;
	}
	public void setCustId(String custId) {
		this.custId = custId;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	
	
}
