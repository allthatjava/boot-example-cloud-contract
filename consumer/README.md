# As a Consumer
This document will guide you to create a Consumer side Test case of CDC(Consumer Driven Contract). A part of the Spring Framework, Spring Cloud Contract will be used to implement this cases.

NOTE: Please read Contract Code Convention before start writing a Contract.

## Create a JUnit test
You can create a unit test with TestRestTemplate. The test should contain the process as if it is making an actual call to the Producer's service.

* Customer is a POJO - The example has customerId(String), firstName(String), lastName(String), age(int), and additional(Additional) as attributes
* Write a Unit test - The example's Unit test expects
	* Consumer application will send a HTTP request through GET method with RESTful URL and Header info
	* Response should include a body that can be mapped with the data model Customer. So, the body should like this below
	
```json
{ "customerId":"12345", "firstName":"John","lastName":"Smith","age":20 }
```

## Unit Test code
```java
@RunWith(SpringRunner.class)
@SpringBootTest
public class CustomerServiceIntegrationTest {
 
    private String url = "http://localhost:8080";
    private static final String TEST_CUST_ID = "12345";
    private static final Customer testCustomer1 = new Customer(TEST_CUST_ID, "John", "Smith", 20);
     
    @Test
    public void testGetCustomer_withValidCustId_shouldReturnCustomer() {
         
        TestRestTemplate restTemplate = new TestRestTemplate();
        HttpHeaders headers = new HttpHeaders();
         
        ResponseEntity<Customer> response = restTemplate.exchange(url+"/customer/"+TEST_CUST_ID, HttpMethod.GET, new HttpEntity<>(headers), Customer.class);
 
        Customer customer = response.getBody();
         
        // Assert
        assertNotNull(customer);
        assertEquals(testCustomer1.getFirstName(), customer.getFirstName());
        assertEquals(testCustomer1.getLastName(), customer.getLastName());
        assertEquals(testCustomer1.getAge(), customer.getAge());
         
        assertNotNull(customer.getAdditional());
        Additional additional = customer.getAdditional();
        assertEquals("Golf", additional.getInterest());
        assertEquals(true, additional.isDrink());
        assertEquals(false, additional.isSmoke());
    }
```

## Create a Contract
Based on your unit test code, now you should write a Contract in Groovy syntax. The Contract uses very small set of Groovy, so it is easy to write. Also, you can use regular expression and reference variables to give some flexibility in the contract. The Contract has two big section, request and response.

### Request
* Require fields are method and url
* The headers and body can be added as required
### Response
* Required field is status
* The headers and body can be added as required

```groovy
package contracts
import org.springframework.cloud.contract.spec.Contract
 
Contract.make{
    request{
        method 'GET'
        url $(regex('/customer/[0-9]{5}'))  // Regex pattern will generate random data for test case every time.
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
```

## Push the contract to Producer's repository
Assuming you already know the producer's code repository, you are going to checkout the producer's code from the repository and add a contract. Then send a Pull Request to producer's repository. From here, Consumer and Producer will start their conversation over the contract. The following is the typical flow how to drop the contract into Producer's repository.

* Checkout the producer's code from the repository
* Create a branch and add the Contract 
* Commit and Push
* Send a Pull Request

### Default drop zone
Unless base directory is specified, /src/test/resources/contracts will be the default directory where contract should be dropped. The producer may ask you to drop the contract in different directory since it is the producer will take multiple contract from different consumers. It is often good idea to have a separate directory under the /src/test/resources/contracts per consumers. e.g. /src/test/resources/contracts/consumerA, /src/test/resources/contracts/consumerB ...

### If Producer's repository is not accessible
It is recommanded to work in repository directly but, in the worst scenario, you can send the Contract to the producer by email or some other way. In the end, you can verify that producer used your contract or not in the Stubs.jar

## Add StubRunner on the JUnit Test
Once the producer is ready to provide Stubs.jar, you can either generate Stubs.jar by yourself from the producer's code or uses Stubs.jar from the Artifactory.

### Use Annotaion for StubRunner
You can add the following annotation on the Class level. If you have an option workOffline=true in the annotation, test will look for the Stubs.jar from the local repository. You can use this option when you already have downloaded the stubs.jar from the remote repository to your local or if you can generate stubs.jar from the producer's code that you have checked out earlier.

* The annotation can be used in the following manner on the Class level
@AutoConfigureStubRunner(ids = "{producer application groupid}:{producer artifact id}:{version}:stubs:{test port}", workOffline=true)
* The following options should be used to get the Stubs.jar from the remote repository (Optional)
	* repositoryRoot
	* username
	* password
	
```java
@AutoConfigureStubRunner(ids="brian.boot.example.cloud.contract:producer:+:stubs:8080", workOffline=true)
```

### Use Properties for StubRunner
@AutoConfigureStubRunner annotation is required to start the Mock Service but, all the options can be placed in application property file. You can use literals or system variables for the optional values.

```
stubrunner:
    work-offline: false
    ids: brian.boot.example.cloud.contract:producer:+:stubs:8080
    repositoryRoot: ${artifactory_contextUrl}/{artifactory_projectRepoKey}
    username: ${artifactory_user}
    password: ${artifactory_password}
```

## Optional
### Using Random Port
If you don't want to specify the port number for the StubRunner, you can use random port. The following example shows you that you can use StubFinder to pick zup the random port to use for the test case.

* Remove the port from the ids option
* Use StubFinder to pick up the random port

```java
@Autowired
StubFinder stubFinder;
private String url;
 
@Before
public void setup(){
    // To pick up the random port StubRunner uses
    int port = stubFinder.findStubUrl("brian.boot.example.cloud.contract", "producer").getPort();
    url = "http://localhost:"+port;
}
```
