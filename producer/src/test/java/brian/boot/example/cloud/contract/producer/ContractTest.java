package brian.boot.example.cloud.contract.producer;

import org.junit.Before;

import com.jayway.restassured.module.mockmvc.RestAssuredMockMvc;

import brian.boot.example.cloud.contract.producer.controller.CustomerController;

public class ContractTest {

	@Before
	public void setup() {
		RestAssuredMockMvc.standaloneSetup(new CustomerController());
	}
	
	public int getAge(String custId)
	{
		if("12345".equals(custId))
			return 20;
		else
			return 30;
	}
}
