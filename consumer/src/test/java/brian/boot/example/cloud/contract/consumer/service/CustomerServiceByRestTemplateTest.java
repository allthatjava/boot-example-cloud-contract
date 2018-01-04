package brian.boot.example.cloud.contract.consumer.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.cloud.contract.stubrunner.StubFinder;
import org.springframework.cloud.contract.stubrunner.spring.AutoConfigureStubRunner;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import brian.boot.example.cloud.contract.consumer.model.Additional;
import brian.boot.example.cloud.contract.consumer.model.Customer;
import brian.boot.example.cloud.contract.consumer.model.CustomerResponse;
import brian.boot.example.cloud.contract.consumer.model.CustomerResponse.Status;

/**
 * This test uses random port for StubRunner. Also, it uses TestRestTemplate to directly test against StubRunner
 * 
 * @author hyen.heo
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureStubRunner(ids = "brian.boot.example.cloud.contract:producer:+:stubs", workOffline=true)
public class CustomerServiceByRestTemplateTest {

	@Autowired 
	StubFinder stubFinder;
	private String url;

	private static final String TEST_CUST_ID = "54321";
	private static final Customer testCustomer1 = new Customer(TEST_CUST_ID, "John", "Smith", 20);
	
	@Before
	public void setup(){
		// To pick up the random port StubRunner uses
		int port = stubFinder.findStubUrl("brian.boot.example.cloud.contract", "producer").getPort();
		url = "http://localhost:"+port;
	}

	@Test
	public void testGetCustomer_withValidCustId_shouldReturnCustomer() {
		
		TestRestTemplate restTemplate = new TestRestTemplate();
		HttpHeaders headers = new HttpHeaders();
		
		ResponseEntity<Customer> response = restTemplate.exchange(
				url+"/customer/"+TEST_CUST_ID, HttpMethod.GET,
				new HttpEntity<>(headers), Customer.class);

		Customer customer = response.getBody();
		
		// Assert
		assertNotNull(customer);
		assertEquals(testCustomer1.getFirstName(), customer.getFirstName());
		assertEquals(testCustomer1.getLastName(), customer.getLastName());
		assertEquals(testCustomer1.getAge(), customer.getAge());
		
		assertNotNull(customer.getAdditional());
		Additional additional = customer.getAdditional();
		assertEquals("Golf", additional.getInterest());
		assertEquals(true, additional.isDrink());
		assertEquals(false, additional.isSmoke());
	}

	@Test
	public void testPostCustomer_withValidName_shouldUpdateCustomerName() {

		TestRestTemplate restTemplate = new TestRestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
		
		ResponseEntity<CustomerResponse> response = restTemplate.exchange(
				url+"/customer", HttpMethod.POST,
				new HttpEntity<>(testCustomer1, headers), CustomerResponse.class);

		assertEquals( HttpStatus.OK, response.getStatusCode());

		CustomerResponse custResponse = response.getBody();
		assertEquals( Status.OK, custResponse.getStatus());
		assertEquals( "Customer created", custResponse.getMessage());
	}
	
	
}
