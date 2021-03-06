# Spring Cloud Contract example

### Sample project
* __Producer__ : Service provider. Takes a contract by Pull Request from Consumer and implement the feature requested.

* __Consumer__ : Service consumer. Sends a contract to Producer and test its code with Producer's stub.

### Run the example
* Run the following command in __Producer__ directory
```
./gradlew clean build install
```
* Run the following command in __Consumer__ directory
```
./gradlew clean build
```

### Project implementation flow
1. Consumer - creates a Test case. (under src/test/java ) 
```gradle
buildscript{
	ext{ springCloudContractVersion = '1.2.0.RELEASE' }
	dependencies{
		classpath "org.springframework.cloud:spring-cloud-contract-gradle-plugin:${springCloudContractVersion}"
	}
}
dependencies {
	testCompile('org.springframework.cloud:spring-cloud-starter-contract-stub-runner')
}
dependenciesManagement {
	imports {
		mavenBom "org.springframework.cloud:spring-cloud-contract-dependencies:${springCloudContractVersion}"
	}
}
```
2. Consumer - creates a contract(*.groovy) and send a Pull Request to the service provider's repository. Contracts must be dropped under the src/test/resources/contracts in producer's project. That is the default directory where spring-cloud-contract picks up contracts. If you want to change, you can change in Gradle script. (src/test/resources/contracts is the default base directories. You can separate contracts by consumers such as src/test/resources/contracts/com/google/consumer1 )
```groovy
import org.springframework.cloud.contract.spec.Contract

Contract.make{
//	ignore() 		// If this contract need to be ignored, ignore() can be used
	description('''
		This 'description' is optional. Often recommended to have the test case explained with 'given...when...then...' format.
''')
	request{
		method 'GET'
		urlPath $( consumer(regex('/customer/[0-9]{5}')) ) 
//		urlPath $( '/abc/' ) {			// urlPath can have queryParameters to pass the parameters
//			queryParameters {
//				parameter 'limit': 100
//				parameter 'gender': value(consumer(containing("[mf]")), producer('mf'))
//			}
//		}
		body(			// Bodycan be included in the request.
		)
		multipart(		// Multipart can be included in the request. See the Spring Cloud Contract document for detail
		)
	}
	response{
		status 200						// Status is 'must' attribute in the response
//		body(file("response.json"))		// If JSON file can be used for contract. In this case, file must be in the same directory.
		body(
			custId: '12345',
			firstName: 'John',
			lastName: 'Smith',
			age: $(producer(regex('(20|30)')))
		)
		headers {
			header(
				'Content-Type': $( producer( regex('application/json.*')), consumer('application/json') )	
			)
		}
	}
	
}
```
3. Producer - approves the pull request
4. Producer - runs the following command to generate Automated Unit Test for the Contract. This will generate Unit tests under /build/generated-test-sources/contracts (generated unit test directory can be changed in Gradle script)
```
./gradlew generateContractTests
```
5. Producer - Creates a base class for Test and add the path in build.gradle file. After the following process, run the above(#4) command again. This time generated test will be extended from the base test class.
- Create a base class for test case
```java
package brian.boot.example.cloud.contract.producer;

@RunWith(SpringRunner.class)
@SpringBootTest(classes={Application.class})
public abstract class ContractTest {

	// Anything that need for the test can be here
	@Autowired CustomerService service; 

	@Before
	public void setup() {
		// CustomerController has end points to test
		RestAssuredMockMvc.standaloneSetup(new CustomerController(service));		
	}
}
```
- Add the following line in the build.gradle
```gradle
contracts{
  baseClassForTests = 'brian.boot.example.cloud.contract.producer.controller.ContractTest'
}
// or to have separate base test class for contracts
contracts{
  baseClassMappings {
	  baseClassMapping('.*producer.*', 'brian.boot.exmaple.cloud.producer.ProducerBase')
  }
}
```
- Run the following command to generate test cases with the above base test class
```
./gradlew clean generateContractTests
```
6. Producer - Implements code to satisfy the contract (To pass the generated-test cases)
7. Producer - run the following command to upload generated '~stub.jar' to local repository
```
./gradlew clean build install
```
	- '~stub.jar' has a WireMock mapping
	- under build/stubs/.../mappings directory, you can check how WireMock mapping file has been generated
```json
{
  "id" : "a3146df0-9fc0-48bb-84cb-65ccd971a940",
  "request" : {
    "urlPattern" : "/customer/[0-9]{5}",
    "method" : "GET"
  },
  "response" : {
    "status" : 200,
    "body" : "{\"custId\":\"12345\",\"firstName\":\"John\",\"lastName\":\"Smith\",\"age\":\"30\"}",
    "headers" : {
      "Content-Type" : "application/json"
    },
    "transformers" : [ "response-template" ]
  },
  "uuid" : "a3146df0-9fc0-48bb-84cb-65ccd971a940"
}
```
8. Consumer - add a annotation on the test case ( + means using the latest version of
	- @AutoConfigureStubRunner(ids = "{producer application groupid}:{producer artifact id}:{version}:stubs:{test port}", workOffline=true)
	- '+' means the latest version
	- Don't put the port if you want to test in random port
	- change the workOffline option to false if stub should be pulled from external repository 
```java
@AutoConfigureStubRunner(ids = "brian.boot.example.cloud.contract:producer:+:stubs:9999", workOffline=true)
```

9.Publishing to Artifactory (Optional)
	- To include ~stubs.jar to be published to Artifactory, following additional script is needed in build.gradle
```
publishing {
    publications {
        mavenJava(MavenPublication) {
            artifact verifierStubsJar {
                classifier 'stubs'
            }
        }
    }
}
```

### Additional Note
* optional() can be use for the optional fields
	* NOTE: Consumer(Stub) side can have optional() only in __request__ and Producer(Test) side can have optional() only in __response__.

### References
Spring Cloud Contract - [https://cloud.spring.io/spring-cloud-contract/](https://cloud.spring.io/spring-cloud-contract/)

* Documents
	- Spring Cloud Contract
		* [Spring Cloud Contract 1.1.4](http://cloud.spring.io/spring-cloud-static/spring-cloud-contract/1.1.4.RELEASE/single/spring-cloud-contract.html)
		* [Github](https://github.com/spring-cloud/spring-cloud-contract)
	- Martin Fowler: [https://martinfowler.com/articles/consumerDrivenContracts.html](https://martinfowler.com/articles/consumerDrivenContracts.html)
	- How to write a Contract : [spring_cloud_contract_advanced](https://github.com/spring-cloud-samples/spring-cloud-contract-samples/blob/master/docs/tutorials/spring_cloud_contract_advanced.adoc)
	- Contract DSL - [Contrac DSL Link](http://cloud.spring.io/spring-cloud-static/spring-cloud-contract/1.1.4.RELEASE/multi/multi__contract_dsl.html]

* Samples
	- Spring IO Samples : [Here](http://cloud-samples.spring.io/spring-cloud-contract-samples/workshops.html#contract-workshop-introduction-video)
	- Step by Step workshop : [Here](https://specto.io/blog/2016/11/16/spring-cloud-contract/)
		-> Code : [Code Sample](https://github.com/SpectoLabs/spring-cloud-contract-blog)

* Video
	- [https://youtu.be/iyNzYOcuU4I](https://youtu.be/iyNzYOcuU4I)   <-- part of the Spring IO Samples document
	- [https://youtu.be/sAAklvxmPmk](https://youtu.be/sAAklvxmPmk)    <--- recommended from above video
