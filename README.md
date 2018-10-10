-------------------------
Angular2 Spring Boot App
-------------------------

  This project is built as the skeleton app for all projects inside AZ wanting to use Angular2/Typescript for the UI with Spring Boot as the backend. The tooling/technologies used
  in this app are up-to-date and include the following:

	Angular2
	Typescript
	Webpack
	Karma
	Jasmine
	TSLint/Codelyzer
	Istanbul
	Maven
	Spring Boot
	Mockito
	JMeter tests/analysis

	
  Usage	
  -----
  
	1. Clone the project to your required folder.
	
	3. Import the project into STS and run 'mvn clean install' from the root. 
	   This will run the Maven build for all three layers and will download a local copy of 
	   Node and npm from AZ Artifactory to the ui folder. This will also install all the required npm packages.
	
	4. Inside STS, right click on 'srs-services' project and 'Run as Spring Boot App'
	
	5. For production: 
		You can directly browse to http://localhost:8080 and the app will be served from that link
	   
	   For development: 
		Open command terminal, goto srs-ui folder and run 'npm start'. This will bring up a Webpack dashboard in the console
		Open Chrome, browse to 'http://localhost:3333'	
	
		You can optionally disable production packaging with a command line parameter: 
			mvn clean install -DskipProductionPackaging=true
		This will not copy any static resources to Spring boot.
	
	7. Clicking on 'Service Call' will talk to the backend (if running) to display messages
	
	
  Running tests
  -------------
  
    1. Running Unit Tests for UI layer

		Single run: npm test
		This will also generate a code coverage report in coverage/ folder. JSON/HTML reports are generated.
		
		Live mode: npm run test-watch
	
	2. Running End-to-End Tests for UI layer

		npm start
		In another console: npm run e2e

	3. Performance testing in model layer

		This will be done by default as part of the Maven build process. JMX load test files are available
		in src/test/jmeter folder.
		
		Once the load tests are run, the JMeter Analysis plugin is invoked which creates reports in target/reports.
	
	
  Swagger	
  -------
	Once the Spring Boot app is running, all API documentation used in this project can be viewed from: http://localhost:8080/swagger-ui.html 

	
  Docker
  ------
	To build the docker image go to srs-services, and run 'docker build -t angular2-springboot-poc .'

	After that you can run your container with 'docker run -ti -p 8080:8080 angular2-springboot-poc'	
	
	
