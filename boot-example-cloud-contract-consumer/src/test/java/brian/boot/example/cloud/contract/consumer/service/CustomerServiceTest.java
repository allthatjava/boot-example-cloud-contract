package brian.boot.example.cloud.contract.consumer.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.stubrunner.spring.AutoConfigureStubRunner;
import org.springframework.test.context.junit4.SpringRunner;

import brian.boot.example.cloud.contract.consumer.model.Customer;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureStubRunner(ids = "brian.boot.example.cloud.contract:boot-example-cloud-contract-producer:+:stubs:8080", workOffline=true)
public class CustomerServiceTest {

	@Autowired
	private CustomerService service;
	
	@Test
	public void testGetCustomer_withValidCustId_shouldReturnCustomer() {
		
		// Given 
		String testCustId = "12345";
		
		// Test
		Customer customer = service.getCustomer(testCustId);

		// Assert
		assertNotNull(customer);
		assertEquals("John", customer.getFirstName());
		assertEquals("Smith", customer.getLastName());
		assertEquals(20, customer.getAge());
	}
}
