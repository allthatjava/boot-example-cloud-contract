package brian.boot.example.cloud.contract.consumer.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.stubrunner.spring.AutoConfigureStubRunner;
import org.springframework.test.context.junit4.SpringRunner;

import brian.boot.example.cloud.contract.consumer.model.Customer;
import brian.boot.example.cloud.contract.consumer.model.CustomerResponse.Status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureStubRunner(ids = "brian.boot.example.cloud.contract:producer:+:stubs:8080", workOffline=true)
public class CustomerServiceTest {

	@Autowired
	private CustomerService service;
	
	private Customer testCustomer;
	
	@Before
	public void setup() {
		testCustomer = new Customer();
		testCustomer.setCustId("12345");
		testCustomer.setCustId("John");
		testCustomer.setCustId("Smith");
		testCustomer.setAge(20);
	}
	
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
	
	public void testPostCustomer_withValidName_shouldUpdateCustomerName() {
				
		// Test
		Status status = service.createCustomerAge(testCustomer);

		// Assert
		assertEquals( Status.OK, status);
	}
}
