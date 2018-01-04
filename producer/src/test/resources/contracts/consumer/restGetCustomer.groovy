package contracts.consumer

import org.springframework.cloud.contract.spec.Contract

Contract.make{
	description('''
		This 'description' is optional. Often recommended to have the test case explained with 'given...when...then...' format.

		- Implemented code is in CustomerController.java
''')
	request{
		method 'GET'
		url value(regex('/customer/[0-9]{5}'))	// Regex pattern will generate random data for test case every time.
	}
	response{
		status 200
		headers {
			header( 'Content-Type': $(regex('application/json.*')) )
		}
		body(
			custId: "${fromRequest().path(1)}",
			firstName: 'John',
			lastName: 'Smith',
			age: 20,
			additional: [
				interest: 'Golf',
				drink: true,
				smoke: false
			]
		)
	}
}