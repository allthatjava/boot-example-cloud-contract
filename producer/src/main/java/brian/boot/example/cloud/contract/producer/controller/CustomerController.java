package brian.boot.example.cloud.contract.producer.controller;

import javax.websocket.server.PathParam;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import brian.boot.example.cloud.contract.producer.model.Customer;
import brian.boot.example.cloud.contract.producer.model.CustomerResponse;

@RestController
public class CustomerController {

	@RequestMapping( value="/customer/{custId}", method = RequestMethod.GET )
	public Customer getCustomer(@PathParam("custId") String custId){
		
		return new Customer("12345", "John", "Smith", 20);
	}
	
	@RequestMapping( value="/customer", method = RequestMethod.POST)
	public CustomerResponse createCustomer(@RequestBody Customer customer) {
		
		return new CustomerResponse( "OK", "Customer created");
	}
}
