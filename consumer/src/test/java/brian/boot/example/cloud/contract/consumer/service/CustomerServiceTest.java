package brian.boot.example.cloud.contract.consumer.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.cloud.contract.stubrunner.spring.AutoConfigureStubRunner;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import brian.boot.example.cloud.contract.consumer.model.Customer;
import brian.boot.example.cloud.contract.consumer.model.CustomerResponse;
import brian.boot.example.cloud.contract.consumer.model.CustomerResponse.Status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.MOCK)
@AutoConfigureStubRunner(ids = "brian.boot.example.cloud.contract:producer:+:stubs:9999", workOffline=true)
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
	
	/**
	 * For the contract - restGetCustomer.groovy
	 */
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
		assertTrue(customer.getAge()==20 || customer.getAge()==30 );	// Contract says this value can be 20 or 30
	}
	
	/**
	 * For the contract - restPostCustomer.groovy
	 */
	@Test
	public void testPostCustomer_withValidName_shouldUpdateCustomerName() {
				
		// Test
		CustomerResponse response = service.createCustomerAge(testCustomer2);

		// Assert
		assertEquals( Status.OK, response.getStatus());
		assertEquals( "Customer created", response.getMessage());
	}
}
