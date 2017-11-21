package contracts

import org.springframework.cloud.contract.spec.Contract

[
	Contract.make{
		description('''
		This is multiple Contract within a file example. This way, one contract can cover multiple test scenario for one service.

		- Implemented code is in CustomerController.java
''')
		request{
			method 'GET'
			url $(c('/multiple-contract-customer/123'))
		}
		response{
			status 200
			body(
				custId: '123',
				firstName: 'John',
				lastName: 'Smith',
				age: 20						// For custId 123, response must have age 20.
			)
			headers {
				header(
					'Content-Type': value( producer( regex('application/json.*')), consumer('application/json') )	
				)
			}
		}
	},
	Contract.make{
		request{
			method 'GET'
			url $(c('/multiple-contract-customer/456'))
		}
		response{
			status 200
			body(
				custId: '456',
				firstName: 'John',
				lastName: 'Smith',
				age: 30						// For custId 456, response must be have age 30.
			)
			headers {
				header(
					'Content-Type': value( producer( regex('application/json.*')), consumer('application/json') )
				)
			}
		}
	},
	Contract.make{
		description('''
			Failure case for GET /multiple-contract-customer/abc

			- If custId validation fails, response will have status 400 and additional message in the header
		''')
		request{
			method 'GET'
			url $(c('/multiple-contract-customer/abc'))
		}
		response{
			status 400
			headers {
				header(
					"X-HTTP-Error-Description": "Customer ID is not numeric type"
				)
			}
		}
	}
]