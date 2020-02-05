# Test Automation Framework
This Test Automation Framework implements Page Object Model design pattern for structuring locators and (inter)actions together in Java classes by page/component, is used to run UI automation tests for "autobilis.lt PASKOLA" (powered by [Mokilizingas](https://www.mokilizingas.lt/paskola/paskola-automobiliui "Paskola auto(mo)biliui")) against Selenium/W3C WebDriver, has a parallelized cloud execution ability in place, and could be potentially integrated into Jenkins CI pipeline.

## 1. Tech Stack
1. Oracle JDK 1.8 (8u231)
2. IntelliJ IDEA Ultimate 2019.3.2 (you may also import this repo into any Java-supported IDE as a Maven project)
3. JUnit 4.13
4. Selenium 4.0.0-alpha-4 bindings for Java
5. ChromeDriver 79.0.3945.36 (the WebDriver used for this project is built for macOS, however it may be adapted to your platform of choice by downloading a desired WebDriver, then placing it within the vendors folder and finally making changes to the 34th row of src/test/java/tests/BaseTest.java)
6. geckodriver v0.26.0 (the same strategy is applicable for this WebDriver for Firefox as well, only this time it is the 38th row which has to be revised)
5. Saucerest 1.0.43 (you still need to use your own SAUCE_USERNAME and SAUCE_ACCESS_KEY defined as system environment variables)
6. Maven Surefire Plugin 3.0.0-M4 (for parallelized cloud execution)

## 2. Usage

### 2.1. IDE
Run tests (from src/test/java/tests/TestApplyForLoan.java) one-by-one or altogether, just as you normally would.

### 2.2. Terminal
Make sure you land on a working directory having pom.xml (`pwd`, `cd`, _xor_ `ls` may help for Unixers), and from there run `mvn clean test` (it is required to have [Apache Maven](https://maven.apache.org/download.cgi "Download Apache Maven") on your system upfront; it is [deadly simple](https://github.com/rajivkanaujia/alphaworks/wiki/Installing-Maven "Install Maven via Homebrew") for macOS users).

#### 2.2.1 Other Usage Options
* Tests scripts are tagged as "shallow" (critical tests for quick feedback) and "deep" (less critical ones and/or these which potentially could take longer to run). You may choose what you want to run (e.g., `mvn clean test -Dgroups=test.groups.Deep`).
* Besides running tests against different browsers locally (only Chrome and Firefox supported at this moment), you may also execute them in the cloud using Selenium Grid provided by Sauce Labs (e.g., `mvn clean test -Dhost=saucelabs -DplatformName='Windows 10' -DbrowserVersion=11.0`).

## 3. TODO/Nice to Have
1. Finish setting parallelized cloud execution up.
2. Set up a CI pipeline in Jenkins.
