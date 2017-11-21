package contracts

import org.springframework.cloud.contract.spec.Contract

Contract.make{
	
	request{
		method 'GET'
		url value( consumer(regex('/customer/[0-9]{5}')) )
	}
	
	response{
		status 200
		body(
			custId: '12345',
			firstName: 'John',
			lastName: 'Smith',
			age: $(producer(regex('(20|30)')))
		)
		headers {
			header(
				'Content-Type': value( producer( regex('application/json.*')), consumer('application/json') )	
			)
		}
	}
	
}