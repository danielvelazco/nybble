## Background  

Nybble code challenge    

There are two different type of testing:    
1.  e2e test: Stack Java/TestNG/Selenium is implemented to execute tests over Levi APP performing user actions through the browser.  

2.  api test: Stack Java/TestNG/Rest-Assured is implemented to execute test over reqres.in REST-API responses.  

## Prerequisites  
1.  Java 1.8 or higher version.  
2.  Maven 3.6 or higher version.  

### Run tests  
1.  Clone repo from github:     
    `$ git clone https://github.com/danielvelazco/nybble.git`  

2.  Enter into the repo cloned:  
    `$ cd nybble`  

3.  Execute the following command  
    `$ mvn clean package || mvn clean test`  
    
A few things:  
By default, selenium will launch a chrome instance to execute the tests.  
If you want to use firefox, go to testng.xlm file and change the value of the browser parameter like so:  
    `<parameter name="browser" value="firefox"/>`  

Note: If you get some error related to the browser version, please update the drivers into ./bin directory according to the browsers you have installed in your machine.  

### Reporting  
Go to the following path and open the index.html file to get a test summary in html version:  
    `target/surefire-reports/index.html`  