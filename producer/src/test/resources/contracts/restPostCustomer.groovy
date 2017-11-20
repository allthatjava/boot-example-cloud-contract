package contracts

import org.springframework.cloud.contract.spec.Contract

Contract.make{
	
	request{
		method 'POST'
		url '/customer'
		body("""
		{
			"custId": "12345",
			"firstName": "John",
			"lastName": "Smith",
			"age": 20
		}
		""")
		headers {
			header( 'Content-Type': 'application/json;charset=UTF-8' )
		}
	}
	
	response{
		status 200
		body("""
		{
			"status": "OK",
			"message": "Customer created"
		}
		""")
		headers {
			header( 'Content-Type': 'application/json;charset=UTF-8' )
		}
	}
	
}