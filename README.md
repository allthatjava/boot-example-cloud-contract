# Spring Cloud Contract example

### Sample project
* __Producer__ : Service provider. That will take a Pull Request with Contract from Consumer.

* __Consumer__ : Service consumer. The client application that will user the provider's service.

### Project implement flow
1. Consumer - App (boot-example-cloud-contract-consumer) create a Test case.
2. Consumer - App Developer create a contract and send it as __Pull Request__ to the service provider's repository.
3. Producer - approves the pull request
4. Producer - run the command 
```
./gradlew generateContractTests
```
5. Producer - Add base class for Test and add it in build.gradle file
```
contracts{
  baseClassForTests = 'brian.boot.example.cloud.contract.producer.controller.ContractTest'
}
```
6. Producer - Implements code to satisfy the contract
7. Producer - run the following command to upload '~stub.jar' to __Artifactory__
	- '~stub.jar' has a WireMock mapping
8. Consumer - add a annotation on the test case ( + means using the latest version of
	- @AutoConfigureStubRunner(ids = "{producer artifact groupid}:{producer project name}:{version}:stubs:{test port}", workOffline=true)  
```
// So, it will be like this
@AutoConfigureStubRunner(ids = "brian.boot.example.cloud.contract:producer:+:stubs:8080", workOffline=true)
```


### References
Spring Cloud Contract - [https://cloud.spring.io/spring-cloud-contract/](https://cloud.spring.io/spring-cloud-contract/)

* Documents
	- Spring Cloud Contract : [Spring Cloud Contract 1.1.4](http://cloud.spring.io/spring-cloud-static/spring-cloud-contract/1.1.4.RELEASE/single/spring-cloud-contract.html)
	- Martin Fowler: [https://martinfowler.com/articles/consumerDrivenContracts.html](https://martinfowler.com/articles/consumerDrivenContracts.html)
 
* Samples
	- Spring IO Samples : [Here](http://cloud-samples.spring.io/spring-cloud-contract-samples/workshops.html#contract-workshop-introduction-video)
	- Step by Step workshop : [Here](https://specto.io/blog/2016/11/16/spring-cloud-contract/)
		-> Code : [Code Sample](https://github.com/SpectoLabs/spring-cloud-contract-blog)
 
* Video
	- [https://youtu.be/iyNzYOcuU4I](https://youtu.be/iyNzYOcuU4I)   <-- part of the Spring IO Samples document
	- [https://youtu.be/sAAklvxmPmk](https://youtu.be/sAAklvxmPmk)    <--- recommended from above video
	
* Optional REST service examples
	- [RESTFul Service examples](http://www.springboottutorial.com/creating-rest-service-with-spring-boot)