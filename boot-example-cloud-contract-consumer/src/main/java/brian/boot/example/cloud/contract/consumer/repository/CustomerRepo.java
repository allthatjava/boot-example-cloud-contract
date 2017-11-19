package brian.boot.example.cloud.contract.consumer.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import brian.boot.example.cloud.contract.consumer.model.Customer;

@Repository
public class CustomerRepo {

	private final RestTemplate restTemplate;
	
	private final String url;
	private final int port;
	
	@Autowired
    public CustomerRepo(RestTemplate restTemplate, 
    						@Value("${producer.url}") String url, 
    						@Value("${producer.port}") int port) {
        this.restTemplate = restTemplate;
        this.url = url;
        this.port = port;
    }
	
	public Customer getCustomerInfo(String custId) {
		
		ResponseEntity<Customer> res = restTemplate.getForEntity(url+":"+port+"/customer/"+custId, Customer.class);
		
		return res.getBody();
	}
}
