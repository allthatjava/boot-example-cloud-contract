package brian.boot.example.cloud.contract.producer.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import brian.boot.example.cloud.contract.producer.model.Customer;
import brian.boot.example.cloud.contract.producer.model.CustomerResponse;
import brian.boot.example.cloud.contract.producer.service.CustomerService;

@RestController
public class CustomerController {
	
	CustomerService customerService;
	
	@Autowired
	public CustomerController(CustomerService customerService)
	{
		this.customerService = customerService;
	}

	@RequestMapping( value="/customer/{custId}", method = RequestMethod.GET )
	public Customer getCustomer(@PathVariable("custId") String custId){
		
		return customerService.getCustomer(custId);
	}
	
	@RequestMapping( value="/customer", method = RequestMethod.POST)
	public CustomerResponse createCustomer(@RequestBody Customer customer) {
		
		return customerService.createCustomer(customer);
	}
	
	@RequestMapping( value="/multiple-contract-customer/{custId}", method = RequestMethod.GET)
	public Customer getMultipleContractCustomer(@PathVariable("custId") String custId){
		
		return customerService.getMultipleContractCustomer(custId);
	}
	
    @ExceptionHandler
    public void handleIllegalArgumentException(IllegalArgumentException e, HttpServletResponse response)
            throws IOException {
    	response.setStatus(HttpStatus.SC_BAD_REQUEST);
        response.addHeader("X-HTTP-Error-Description", e.getMessage());
    }
}
