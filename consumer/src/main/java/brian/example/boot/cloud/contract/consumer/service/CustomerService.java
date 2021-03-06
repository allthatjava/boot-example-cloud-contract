package brian.example.boot.cloud.contract.consumer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import brian.example.boot.cloud.contract.consumer.model.Customer;
import brian.example.boot.cloud.contract.consumer.model.CustomerResponse;
import brian.example.boot.cloud.contract.consumer.model.CustomerResponse.Status;
import brian.example.boot.cloud.contract.consumer.repository.CustomerRepo;

@Service
public class CustomerService {

	private final CustomerRepo customerRepo;

	@Autowired
	public CustomerService(CustomerRepo customerRepo) {
		this.customerRepo = customerRepo;
	}
	
	public Customer getCustomer(String custId)
	{
		return customerRepo.getCustomerInfo(custId).getBody();
	}
	
	public CustomerResponse createCustomerAge(Customer customer) {
		return customerRepo.createCustomer(customer).getBody();
	}
}
