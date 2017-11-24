package contracts

import org.springframework.cloud.contract.spec.Contract

Contract.make{
	description('''
		This 'description' is optional. Often recommended to have the test case explained with 'given...when...then...' format.

		- Implemented code is in CustomerController.java
''')
	request{
		method 'GET'
		url value( consumer(regex('/customer/[0-9]{5}')) )	// Regex pattern will generate random data for test case every time.
	}
	
	response{
		status 200
		body(
			custId: "${fromRequest().path(1)}",
			firstName: 'John',
			lastName: 'Smith',
			age: $(producer(regex('(20|30)')))	// Same as above, either number will be chosen randomly for response data for test case.
		)
		headers {
			header(
				'Content-Type': value( producer( regex('application/json.*')), consumer('application/json') )	
			)
		}
	}
}