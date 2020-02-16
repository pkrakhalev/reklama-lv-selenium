Selenium tests for reklama.bb.lv/ru site
==========================================================

Automation tests are provided for "favorite" feature basic verification.

### Features
- Tests are running in parrallel. Settings can be modifed using **junit-platform.properties** file;
- WebDriver can be easily replaced with RemoteWebDriver to run tests in the Selenium hub or in the Docker container;
- Main properties can be modified using **application.properties** file;
- Page object model has been used.

### Execution
To run tests use "test" task or `gradlew test` command or run a test manually from the IDE.
