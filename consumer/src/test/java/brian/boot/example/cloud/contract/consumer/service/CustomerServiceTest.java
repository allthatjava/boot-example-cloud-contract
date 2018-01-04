package brian.boot.example.cloud.contract.consumer.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.cloud.contract.stubrunner.spring.AutoConfigureStubRunner;
import org.springframework.test.context.junit4.SpringRunner;

import brian.boot.example.cloud.contract.consumer.model.Additional;
import brian.boot.example.cloud.contract.consumer.model.Customer;
import brian.boot.example.cloud.contract.consumer.model.CustomerResponse;
import brian.boot.example.cloud.contract.consumer.model.CustomerResponse.Status;

/**
 * This test uses fixed port number 9999 for StubRunner. The port and url are picked up from application.yml and they are used by CustomerService. 
 * 
 * @author hyen.heo
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.MOCK)
@AutoConfigureStubRunner(ids = "brian.boot.example.cloud.contract:producer:+:stubs:9999", workOffline=true)
public class CustomerServiceTest {

	@Autowired
	private CustomerService service;
	
	private static final String TEST_CUST_ID = "12345";
	
	/**
	 * For the contract - restGetCustomer.groovy
	 */
	@Test
	public void testGetCustomer_withValidCustId_shouldReturnCustomer() {
		
		// Given 
		Customer testCustomer1 = new Customer(TEST_CUST_ID, "John", "Smith", 20);
		
		// Test
		Customer customer = service.getCustomer(TEST_CUST_ID);		// Service is provided from Producer

		// Assert
		assertNotNull(customer);
		assertEquals(testCustomer1.getFirstName(), customer.getFirstName());
		assertEquals(testCustomer1.getLastName(), customer.getLastName());
		assertTrue(customer.getAge()==20 || customer.getAge()==30 );	// Contract says this value can be 20 or 30
		
		assertNotNull(customer.getAdditional());
		Additional additional = customer.getAdditional();
		assertEquals("Golf", additional.getInterest());
		assertEquals(true, additional.isDrink());
		assertEquals(false, additional.isSmoke());
	}
	
	/**
	 * For the contract - restPostCustomer.groovy
	 */
	@Test
	public void testPostCustomer_withValidName_shouldUpdateCustomerName() {
				
		// Given
		Customer testCustomer2 = new Customer("54321", "John", "Smith", 20);
		
		// Test
		CustomerResponse response = service.createCustomerAge(testCustomer2);

		// Assert
		assertEquals( Status.OK, response.getStatus());
		assertEquals( "Customer created", response.getMessage());
	}
}
