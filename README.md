A Simple User Management Web Application
========================================
This is a simple web application using eclipse, tomcat, java ee and mysql.

Setup the project
-----------------
### clone the project

    git clone https://github.com/YigWoo/user-manage.git user-manage

### Set up the database
The database used here is mysql, download it from [mysql](http://www.mysql.com/downloads/).
 Install it on your system, then create an administrator. Then modify the database context
 parameter in the DD("web.xml"). Say if the username you use is "user" and password is "guesswhat?"
 Your DD should contains something looks like this:
 
    <context-param>
    <param-name>dbUser</param-name>
    <param-value>user</param-value>
    </context-param>  
    <context-param>
    <param-name>dbPassword</param-name>
    <param-value>guesswhat?</param-value>
    </context-param>
    
In order to access the database using java, you need Connector J(mysql-connector-java-x.x.x-bin.jar file),
 you can find it in your mysql installation directory(if you're using windows), the jar file should both
 be contained in your project's classpath and WebContent/WEB-INF/lib, since the first one is used when your
 .java file is compiled into .class file, the second one is used when you actually running your application.

### Set up the Tomcat
To run the web application, you'll need to setup [Tomcat](http://tomcat.apache.org/index.html), **DON'T** use
 Tomcat8, since the current edition of eclipse doesn't support Tomcat8 yet.
 
### Import the project
To setup the project, you'll need [eclipse](http://www.eclipse.org/downloads/). Java EE edition is recommended.
 
 Simply import the project into the eclipse workspace will do.