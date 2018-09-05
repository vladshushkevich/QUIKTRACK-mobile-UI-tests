[phantomjs]: http://phantomjs.org/download.html
[maven]: http://maven.apache.org/
[git]: http://git-scm.com/

## FinSys API tests

### Getting Started

To run tests you need to install [maven][maven], [git][git] and [phantomjs][phantomjs].

To generate Allure Report you should perform following steps:

```bash
$ git clone git@gitlab.com:flgalaxy-finsys/api-tests-finsys.git
$ mvn clean test
$ mvn site
```

To see a report, run `mvn jetty:run` and open `http://localhost:8080` in your browser

**NOTE: required maven version 3.1.1 or above**
