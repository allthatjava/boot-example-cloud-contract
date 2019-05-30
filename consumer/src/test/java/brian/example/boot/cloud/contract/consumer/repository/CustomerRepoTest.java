package brian.example.boot.cloud.contract.consumer.repository;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.cloud.contract.stubrunner.spring.AutoConfigureStubRunner;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.HttpClientErrorException;

import brian.example.boot.cloud.contract.consumer.repository.CustomerRepo;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.MOCK)
@AutoConfigureStubRunner(ids = "brian.boot.example.cloud.contract:producer:+:stubs:9999", workOffline=true)
// Used specific version 0.0.1-SNAPSHOT - if test must be done against latest, put + symbol instead of the version
public class CustomerRepoTest {

	@Autowired
	CustomerRepo repo;
	
	/**
	 * For the contract restGetMultipleCustomerContract.groovy
	 * 
	 * only one test case out of 3 test cases is implemented for the failing case (Because I am lazy :'( )
	 */
	@Test
	public void testCreateCustomer_withInvalidCustId_shouldReturnBAD_REQUEST_400() {
		
		try {
			repo.getCustomerInfoFromMultipleContract("abc");
		} catch (HttpClientErrorException e) {
			assertEquals(HttpStatus.BAD_REQUEST, e.getStatusCode());
			assertEquals("Customer ID is not numeric type", e.getResponseHeaders().getFirst("X-HTTP-Error-Description"));
		}
	}
}
