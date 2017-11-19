package brian.boot.example.cloud.contract.consumer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import brian.boot.example.cloud.contract.consumer.model.Customer;
import brian.boot.example.cloud.contract.consumer.repository.CustomerRepo;

@Service
public class CustomerService {

	private final CustomerRepo customerRepo;

	@Autowired
	public CustomerService(CustomerRepo customerRepo) {
		this.customerRepo = customerRepo;
	}
	
	public Customer getCustomer(String custId)
	{
		return customerRepo.getCustomerInfo(custId);
	}
}
