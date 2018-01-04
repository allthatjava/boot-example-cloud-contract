# As a Producer

This document will guide you to create a Producer side workflow of CDC(Consumer Driven Contract). A part of the Spring Framework, Spring Cloud Contract will be used to implement this cases.

NOTE:
* Please read Contract Code Convention before start writing a Contract
* Some libraries version can be different depends on the target environment

## Review and confirm the Contract
By the convention, consumer will send a Pull Request for their Contract. It should be placed under the /src/test/resources/contracts and possibly sub directory for each client.

* Review the contract on Pull Request. You can see the sample of Contract at the As a Consumer page.
* Adjust the contract if necessary
* Approve and merge the contract


## Update build.gradle
### Dependencies
To use Spring Cloud plugins, add following dependencies in buildscript{}. 
```groovy
buildscript {
	ext {
		springBootVersion = '1.5.8.RELEASE'
		springCloudContractVersion = '1.2.0.RELEASE'		// Added for Spring Cloud Contract
	}
	dependencies {
		classpath "org.springframework.boot:spring-boot-gradle-plugin:${springBootVersion}"
		classpath "io.spring.gradle:dependency-management-plugin:1.0.2.RELEASE"
		classpath "org.springframework.cloud:spring-cloud-contract-gradle-plugin:${springCloudContractVersion}"
	}
}
```

### DependencyManagement
Using the dependencyManager is recommended since some dependent libraries may have version issues.
```groovy
dependencyManagement {
	imports {
        mavenBom "org.springframework.cloud:spring-cloud-dependencies:${springCloudVersion}"
        mavenBom "org.springframework.cloud:spring-cloud-contract-dependencies:${springCloudContractVersion}"
	}
}
```

### Contract Verifier
Contract Verifier is only dependency you need to implement a producer application with Spring Cloud Contract.
```groovy
dependencies {
	testCompile('org.springframework.cloud:spring-cloud-starter-contract-verifier')
}
```

### Contract and Base Test Class
Add baseClass. It can be multiple for each consumer/contract.
```groovy
contracts{
	baseClassForTests = 'brian.boot.example.cloud.contract.producer.ContractTest'
}
// Or manually map the contract and base class 
//contracts{
//	baseClassMappings {
//		baseClassMapping('.*consumer.*', 'brian.boot.example.cloud.contract.producer.ContractTest')
//	}
//}
```

### Set up for Publishing
Also add `publishing{}` to publish ~stubs.jar to repository/artifactory
```groovy
// To publish stubs.jar along the application jar
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

## Generate Test Code
Run following command to generate test case from contracts
Verify the test cases are generated as you expected
```sh
./gradlew generateContractTests
```

## Run the test
At the first time, it will fail. (Since you haven't implemented any code yet)
```sh
./gradlew test
```

## Implement Code
To remove any failures from the automatically generated test class, now you should work on implementation.

* To work in pararell with consumer, it would be a good idea to make a skeleton of the code that returns what contracts expect.
* Above approaches gives you an ability to generates stubs.jar and publish stubs.jar to repository. So, consumer application developers can work with verified stubs.jar by provider.
* stubs.jar only includes contracts and Wiremock scripts, so it won't be changed even if you change your code. (Unless you change the contracts)

## Publish the Stubs.jar
To shared the generated stubs.jar with consumer, the best way is through repository/artifactory. Gradle task 'publishToMavenLocal' will do the job for you. 

* You may need 'maven-publish' Gradle plugin to use the following command unless the build script already has it
```sh
./gradlew clean build publishToMavenLocal
```

## Inform consumer and work on the implementation
* Now that you have publish the stubs.jar, inform consumer. So, they can work with stubs.jar that has been verified by producer.
* Implement the skeleton code to fullfil the requirement
* When you run the build script, automatically generated test code will be continously running and raise a flag if anything breaks the contract