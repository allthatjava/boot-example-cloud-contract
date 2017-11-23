package brian.boot.example.cloud.contract.producer;

import org.junit.Before;

import brian.boot.example.cloud.contract.producer.controller.CustomerController;
import io.restassured.module.mockmvc.RestAssuredMockMvc;

public abstract class ContractTest {

	@Before
	public void setup() {
		RestAssuredMockMvc.standaloneSetup(new CustomerController());
	}
}
