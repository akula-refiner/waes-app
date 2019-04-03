# Assignment Scalable Web

Provide 2 http endpoints that accepts JSON base64 encoded binary data on both endpoints 
	```<host>/v1/diff/<ID>/left
	```<host>/v1/diff/<ID>/right
The provided data needs to be diff-ed and the results shall be available on a third end point <host>/v1/diff/<ID>
The results shall provide the following info in JSON format
- If equal return that
- If not of equal size just return that
- If of same size provide insight in where the diffs are, actual diffs are not needed.
	So mainly offsets + length in the data
Make assumptions in the implementation explicit, choices are good but need to be communicated


### Prerequisites

## Running the tests

mvn clean test

## Deployment

mvn clean spring-boot:run or 
mvn clean package and then java -jar ./target/waes-app-0.1.jar &

## Built With

* [Spring Boot](https://spring.io/docs) - The framework used
* [Maven](https://maven.apache.org/) - Dependency Management

## Author

* **Alibek Kulzhabayev** - (https://github.com/akula-refiner)

## Suggestions for improvement

* Configure some kind of cache on the api to get the results, since same IDs can be invoked several times.
* Could be load balanced by using by nginx or other proxy server
 and by running the same app on different port and/or different machine.
 * More tests should be added. 
