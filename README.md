# Test Automation Framework
UI automation tests for "autobilis.lt PASKOLA" powered by Mokilizingas

## Tech Stack
1. Java SDK 1.8 (8u231)
2. IntelliJ IDEA ULTIMATE 2019.3.2 (you may import this repo into any Java-supported IDE as a Maven project)
3. JUnit 4.13
4. Selenium 4.0.0-alpha-4
5. ChromeDriver 79.0.3945.36
6. geckodriver v0.26.0
5. Saucerest 1.0.43 (you may use your own SAUCE_USERNAME and SAUCE_ACCESS_KEY)
6. Maven Surefire Plugin 3.0.0-M4 (for parallelized cloud execution)

## Usage

### IDE
Run tests (src/test/java/tests/TestApplyForLoan.java) one by one or altogether as usually.

### Terminal
Make sure you land on a working directory having pom.xml ("pwd", "ls"), and from there run "mvn clean test".

#### Other Usage Options
* Tests scripts are tagged as "shallow" (critical tests for quick feedback) and "deep" (less critical ones and/or potentialy could take longer to run). You may choose what you want to run (e.g., "mvn clean test -Dgroups=test.groups.Deep").
* Besides running tests against different browsers (Chrome and Firefox supported currently) locally, you may also execute them in cloud using Selenium Grid provided by Sauce Labs (e.g., "mvn clean test -Dhost=saucelabs -DplatformName='Windows 10' -DbrowserVersion=11.0").

## TODO
1. Finish setuping parallelized cloud execution.
2. Set up a CI pipeline in Jenkins.
