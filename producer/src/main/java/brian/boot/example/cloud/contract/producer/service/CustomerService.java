package brian.boot.example.cloud.contract.producer.service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Service;

import brian.boot.example.cloud.contract.producer.model.Additional;
import brian.boot.example.cloud.contract.producer.model.Customer;
import brian.boot.example.cloud.contract.producer.model.CustomerResponse;

@Service
public class CustomerService {
	
	public Customer getCustomer(String custId) {
		
		return new Customer( custId, "John", "Smith", 20, new Additional("Golf", true, false));
	}

	public CustomerResponse createCustomer(Customer customer) {
		
		return new CustomerResponse( "OK", "Customer created");
	}
	
	public Customer getMultipleContractCustomer(String custId){
		
		Pattern pattern = Pattern.compile("[0-9]{3}");
        Matcher matcher = pattern.matcher(custId);
        
        if( !matcher.matches() )
        	throw new IllegalArgumentException("Customer ID is not numeric type");
		
		
		int age = -1;
		if( "123".equals(custId))
			age = 20;
		else if ( "456".equals(custId))
			age = 30;
		
		return new Customer( custId, "John", "Smith", age, new Additional("Golf", true, false));
	}
}
