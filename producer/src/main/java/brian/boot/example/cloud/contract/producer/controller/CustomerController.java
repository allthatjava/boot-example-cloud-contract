package brian.boot.example.cloud.contract.producer.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import brian.boot.example.cloud.contract.producer.model.Customer;
import brian.boot.example.cloud.contract.producer.model.CustomerResponse;

@RestController
public class CustomerController {

	@RequestMapping( value="/customer/{custId}", method = RequestMethod.GET )
	public Customer getCustomer(@PathVariable("custId") String custId){
		
		return new Customer("12345", "John", "Smith", 20);
	}
	
	@RequestMapping( value="/customer", method = RequestMethod.POST)
	public CustomerResponse createCustomer(@RequestBody Customer customer) {
		
		return new CustomerResponse( "OK", "Customer created");
	}
	
	@RequestMapping( value="/multiple-contract-customer/{custId}", method = RequestMethod.GET)
	public Customer getMultipleContractCustomer(@PathVariable("custId") String custId){
		
		int age = -1;
		if( "123".equals(custId))
			age = 20;
		else if ( "456".equals(custId))
			age = 30;
		
		return new Customer( custId, "John", "Smith", age);
	}
}
