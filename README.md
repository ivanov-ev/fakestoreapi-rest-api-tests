# Sample Test Automation Project in Java for the [FAKESTOREAPI.COM](https://fakestoreapi.com/) Website (REST API)

---
Fakestore.com is a website that provides a sample REST API intended for learning purposes.

<a href="https://fakestoreapi.com/"><img src="./images/logos/fakestore.com.logo.png"/></a>

> [!NOTE]
> This is my final project for the 'Java Test Automation' course at <a href="https://qa.guru">QA.GURU</a>. 
> The project is provided as a demonstration of my skills in REST API tests. 
> Keeping the project up to date is not guaranteed.
> 
> Access to Jira, Jenkins, and AllureTestOps is managed by the <a href="https://qa.guru">QA.GURU</a> administration.

---

## Contents:

- <a href="#tools">Tools and Technologies</a>
- <a href="#scenarios">Test Scenarios</a>
- <a href="#jenkins">CI/CD with Jenkins</a>
- <a href="#cli">Launch from the CLI</a>
- <a href="#allure">Allure Reports</a>
- <a href="#allure-testops">Integration with Allure TestOps</a>
- <a href="#jira">Integration with JIRA</a>
- <a href="#telegram">Telegram Bot Notifications</a>

---

<a id="tools"></a>
## Tools and Technologies:

| Java                                                                                                        | IntelliJ  <br>  Idea                                                                                                  | GitHub                                                                                                        | JUnit 5                                                                                                              | Gradle                                                                                                        | Allure <br> Report                                                                                                                    | Jenkins                                                                                                             | Jira                                                                                                                              | Telegram                                                                                                            | Allure <br> TestOps                                                                                                     |
|-------------------------------------------------------------------------------------------------------------|-----------------------------------------------------------------------------------------------------------------------|---------------------------------------------------------------------------------------------------------------|----------------------------------------------------------------------------------------------------------------------|---------------------------------------------------------------------------------------------------------------|---------------------------------------------------------------------------------------------------------------------------------------|---------------------------------------------------------------------------------------------------------------------|-----------------------------------------------------------------------------------------------------------------------------------|---------------------------------------------------------------------------------------------------------------------|-------------------------------------------------------------------------------------------------------------------------|
| <a href="https://www.java.com/"><img src="./images/logos/Java.svg" width="50" height="50"  alt="Java"/></a> | <a href="https://www.jetbrains.com/idea/"><img src="./images/logos/Idea.svg" width="50" height="50"  alt="IDEA"/></a> | <a href="https://github.com/"><img src="./images/logos/GitHub.svg" width="50" height="50"  alt="Github"/></a> | <a href="https://junit.org/junit5/"><img src="./images/logos/Junit5.svg" width="50" height="50"  alt="JUnit 5"/></a> | <a href="https://gradle.org/"><img src="./images/logos/Gradle.svg" width="50" height="50"  alt="Gradle"/></a> | <a href="https://github.com/allure-framework/allure2"><img src="./images/logos/Allure.svg" width="50" height="50"  alt="Allure"/></a> | <a href="https://www.jenkins.io/"><img src="./images/logos/Jenkins.svg" width="50" height="50"  alt="Jenkins"/></a> | <a href="https://www.atlassian.com/ru/software/jira/"><img src="./images/logos/Jira.svg" width="50" height="50"  alt="Jira"/></a> | <a href="https://telegram.org/"><img src="./images/logos/Telegram.svg" width="50" height="50"  alt="Telegram"/></a> | <a href="https://qameta.io/"><img src="./images/logos/Allure_TO.svg" width="50" height="50"  alt="Allure TestOps"/></a> |

---

<a id="scenarios"></a>
## Test Scenarios

REST API tests for the /users controller:
* [x] POST: Add a user (Automated)
* [x] PUT: Update a user (Automated)
* [x] DELETE: Delete a user (Automated)
* [x] GET: Get a single user (Automated)
* [x] GET: Get all users (Automated)
* [x] GET: Get multiple users limited by the 'limit' query string parameter (Automated, Parameterized)
* [x] GET: Get all users sorted in ascending and descending orders

---

<a id="jenkins"></a>
## <img alt="Jenkins" height="25" src="./images/logos/Jenkins.svg" width="25"/></a><a name="CI/CD with Jenkins"></a>CI/CD with [Jenkins](https://jenkins.autotests.cloud/job/fakestoreapi-com-ivanov-ev/)</a>

<img alt="Jenkins" src="./images/screenshots/Jenkins.png">

---

<a id="cli"></a>
## Launch from the CLI

REST API tests can be executed as follows:
```bash  
gradle clean rest_api_tests
```

---

<a id="allure"></a>
## <img alt="AllureReports" height="25" src="./images/logos/Allure.svg" width="25"/></a> <a name="Allure"></a>[Allure Reports](https://jenkins.autotests.cloud/job/fakestoreapi-com-ivanov-ev/allure/)</a>

`Allure report` contains test steps, which include REST API requests and responses stylized using `tpl` templates.

<img alt="Allure" src="./images/screenshots/AllureReports.png"> 

<img alt="Allure2" src="./images/screenshots/AllureReports2.png">

---



<a id="allure-testops"></a>
## <img alt="Allure" height="25" src="./images/logos/Allure_TO.svg" width="25"/></a> Integration with <a target="_blank" href="https://allure.autotests.cloud/project/4319/dashboards">Allure TestOps</a>

Test cases and test execution history are available in `Allure TestOps`.

The dashboard displays test run statistics:

<img alt="Dashboard" src="./images/screenshots/Dashboard.png">

Automated test cases:

<img alt="Cases" src="./images/screenshots/AutomatedTestCases.png">

Launches:

<img alt="Launch" src="./images/screenshots/Launches.png">

---

<a id="jira"></a>
## <img alt="Jira" height="25" src="./images/logos/Jira.svg" width="25"/></a> Integration with <a target="_blank" href="https://jira.autotests.cloud/browse/HOMEWORK-1292">Jira</a>

There is a task in `Jira` that contains references to Allure test cases and Allure launches:

<img alt="JIRA" src="./images/screenshots/JiraTask.png">

---

<a id="telegram"></a>
## <img alt="Telegram" height="25" src="./images/logos/Telegram.svg" width="25"/></a> Telegram Bot Notifications
After every launch in Jenkins, the Telegram bot sends a notification with an `Allure report`:

<img alt="Bot" src="./images/screenshots/TelegramBot.png"> 

