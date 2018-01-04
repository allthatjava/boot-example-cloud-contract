package contracts.consumer

import org.springframework.cloud.contract.spec.Contract

Contract.make{
	description('''
		POST method test. Anything within triple quote(") will be considered as literal.

		- Implemented code is in CustomerController.java
''')
	
	request{
		method 'POST'
		url '/customer'
		headers {
			header( 'Content-Type': 'application/json;charset=UTF-8' )
		}
		body("""
		{
			"custId": "54321",
			"firstName": "John",
			"lastName": "Smith",
			"age": 20
		}
		""")
	}
	response{
		status 200
		headers {
			header( 'Content-Type': 'application/json;charset=UTF-8' )
		}
		body("""
		{
			"status": "OK",
			"message": "Customer created"
		}
		""")
	}
}