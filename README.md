# zup

Tech used:
- Java
- Selenium
- Junit5
- ExtentReport
- Maven

How to use:
- Clone this repo.
- Change the directory to the one you downloaded.
- Run "mvn clean install".
- Change the directory to the /target
- Run "java -jar zup-1.0-SNAPSHOT.jar"

Results:
- You should see a folder created into your disk with name /report.
- This folder should contain the results for the run.

Assumptions:
- You have maven installed in your machine.
- You have java installed in your machine.

Notes:
- The inner classes are running in parallel.
- I'm using extent reports to generate the simple report.
- The tests are added to the same test class using @Nested.
