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
import brian.boot.example.cloud.contract.consumer.model.CustomerResponse;
import brian.boot.example.cloud.contract.consumer.model.CustomerResponse.Status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureStubRunner(ids = "brian.boot.example.cloud.contract:producer:+:stubs:8080", workOffline=true)
public class CustomerServiceTest {

	@Autowired
	private CustomerService service;
	
	private Customer testCustomer1;
	private Customer testCustomer2;
	
	@Before
	public void setup() {
		testCustomer1 = new Customer();
		testCustomer1.setCustId("12345");
		testCustomer1.setFirstName("John");
		testCustomer1.setLastName("Smith");
		testCustomer1.setAge(20);
		
		testCustomer2 = new Customer();
		testCustomer2.setCustId("12345");
		testCustomer2.setFirstName("John");
		testCustomer2.setLastName("Smith");
		testCustomer2.setAge(20);
	}
	
	@Test
	public void testGetCustomer_withValidCustId_shouldReturnCustomer() {
		
		// Given 
		String testCustId = "12345";
		
		// Test
		Customer customer = service.getCustomer(testCustId);

		// Assert
		assertNotNull(customer);
		assertEquals(testCustomer1.getFirstName(), customer.getFirstName());
		assertEquals(testCustomer1.getLastName(), customer.getLastName());
		assertEquals(testCustomer1.getAge(), customer.getAge());
	}
	
	@Test
	public void testPostCustomer_withValidName_shouldUpdateCustomerName() {
				
		// Test
		CustomerResponse response = service.createCustomerAge(testCustomer2);

		// Assert
		assertEquals( Status.OK, response.getStatus());
		assertEquals( "Customer created", response.getMessage());
	}
}
