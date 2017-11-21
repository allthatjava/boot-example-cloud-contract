package contracts

import org.springframework.cloud.contract.spec.Contract

[
	Contract.make{
		
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
				age: 20
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
				age: 30
			)
			headers {
				header(
					'Content-Type': value( producer( regex('application/json.*')), consumer('application/json') )
				)
			}
		}
		
	}
]