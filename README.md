A Simple Login/Register Web Application
========================================
This is a simple web application using Spring MVC, Hibernate, Shiro Security, Bootstrap and JQuery-validation.

Overview of the project
-----------------------
### Backend Server Overview
Backend server is implemented like below:
* Foundation: Java, Spring and Spring MVC framework;
  The backend server is written in java, so the popular spring and spring mvc for web application frameworks
  are choosen to be the base framework which provides **dependency injection**, **model-view-controller processing
  pattern**, and **framework integration** abilities.
* Persistence: MySQL RDBMS, Hibernate, Hibernate-validator and Spring Data JPA framework
  Database server is the popular MySQL RDBMS, Hibernate is to provide **ORM functionality**, Hibernate-validator is
  used to provide **parameter validation**, and Spring Data JPA is used to simplify the writing of the **DAO service**.
* Logging: slf4j and logback.

### Frontend Overview
* CSS styles: bootstrap css is applied
* Form validation: JQuery-validation is used to perform **basic validations** and some **ajax actions**
* Others: Sitemesh is used to generate consistent site headers and footers

Setup the project
-----------------
### clone the project

    git clone https://github.com/YigWoo/user-manage.git user-manage

### Set up the database
The database used here is mysql, download it from [mysql](http://www.mysql.com/downloads/).
In order to access the database using java, you need Connector J(mysql-connector-java-x.x.x-bin.jar file),
 you can find it in your mysql installation directory(if you're using windows), the jar file should both
 be contained in your project's classpath and WebContent/WEB-INF/lib, since the first one is used when your
 .java file is compiled into .class file, the second one is used when you actually running your application.

**Warning: if you're going to execute resources/mysql_shema.sql, the sql is going to drop table mydb/users_table
if it exists!**

### Set up the Tomcat
To run the web application, you'll need to setup [Tomcat](http://tomcat.apache.org/index.html), **DON'T** use
 Tomcat8, since the current edition of eclipse doesn't support Tomcat8 yet.

### Import the project
To setup the project, you'll need [eclipse](http://www.eclipse.org/downloads/). Java EE edition is recommended.

Simply import the project into the eclipse workspace will do.
