package brian.boot.example.cloud.contract.producer;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import brian.boot.example.cloud.contract.producer.controller.CustomerController;
import brian.boot.example.cloud.contract.producer.service.CustomerService;
import io.restassured.module.mockmvc.RestAssuredMockMvc;

@RunWith(SpringRunner.class)
@SpringBootTest(classes= {BootExampleCloudContractProducerApplication.class})
public abstract class ContractTest {
	
	@Autowired
	CustomerService customerService;

	@Before
	public void setup() {
		RestAssuredMockMvc.standaloneSetup(new CustomerController(customerService));
	}
}
