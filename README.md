# Nordic to QuakeML converter tool package

## How to install
To download, build and run the various modules (see list below) within this package, you first need to have installed Git on you computer. See the official documentation for Git on: https://git-scm.com/

Clone the project from this GitHub repository into your desired folder by entering the command:\
``` git clone https://github.com/cronnevik/nor2qml.git ```

The nor2qml package is build upon Java (Spring boot) with Apache Maven as the build tool. In order to build the individual modules, both java and maven (https://maven.apache.org/) needs to be installed.

To simplify the build of the applications and the modules they depend on, maven profiles have been applied. See the list of applications and modules. 

To build an application, enter the command:\
``` mvn clean install -P  <maven profile name>```

The maven build tool then produce a jar or a war file which is found in the *target* folder within the component/application folder.

## Applications
* Standalone converter (*executable jar*) - file conversion locally on the computer
* Ingestor (*executable jar*) - providing data to the web-service
* Web-service (*war file*) for data exchange (QuakeML, Nordic and text format)
* Web converter (*war file*) - web application for conversion in the browser - server and client

## How to run the applications
### Executable jars
The jar files is executable by:\
``` java -jar <name of file.jar> ```

Please be aware that the executable *quakeml-web-service-ingestor* application require a MySQL database running.
Configurations for the MySQL connection can be altered by changing the values in the *database.yml* file located at
the path: */quakeml-web-service-ingestor-executable/src/main/resources*

### War files
The war files produces are applications ment for servers. For deployment these could be uploaded and made runnable by a Tomcat server.\
If you would like to run the server applications locally, an embedded tomcat server is applied and can be initiated by navigating into the application folder
and execute the command: 

``` mvn spring-boot:run ```

Please be aware that the *quakeml-web-service*mapplication require a MySQL database running.
Configurations for the MySQL connection can be altered by changing the values in the *application.yml* file located within the application folder
following the path: */quakeml-web-service/src/main/resources*. Change the name (database name), username and password 
under the profile section for *prod*.

### Docker
A docker configuration has also been implemented to bypass the requirement of installing 
MySQL (ingestor, web-service) and a tomcat server for the web-service. Within docker, a virtual container spins up 
to initiate the database and host the web-service application.The docker option is only for running the
web-service application, but creates a MySQL instance that can be populated with data by executing the ingestor application
separately as an executable jar.

#### Configure Database and Tomcat server
The database setup for the MySQL instance (like database name, user, password) have some default test values. 
These values can be altered directly within the*docker-compose.yml* file, 
but a change in credentials is also needed for the application itself. Please see the description
in the last paragraph under the *War files* section. In addition, the tomcat server have their own credentials for 
installing and hosting the web-service application. These values are defined in the *tomcat-users.xml* found 
at *./quakeml-web-service/src/main/resources/*. The same credentials 

To summarise, the following files is where configurations can be applied:
 - *docker-compose.yml* in main folder
 - *tomcat-users.xml* under *./devops/tomcat/*
 - *application.yml* under *./quakeml-web-service/src/main/resources/*

#### Running docker
Make sure that you have docker installed on your computer and that it is running.
Open a terminal, navigate to the main folder and enter:\
``` docker-compose up```

To shutdown the docker instances, enter:\
``` docker-compose down ```

If you already did a run and want to change the database credentials,  add the flag '-v' like so:\
``` docker-compose down -v ```

Please be aware that the '-v' flag will permanently delete the database volume, and you will lose all the data stored
within the database created within the MySQL instance.

## Modules
Components | Maven profiles | Produces | Description
---------- |----------- |----------- | -----------
converter-core | - | jar |Reading and mapping formats 
converter-standalone | standalone | jar | Executable jar file for conversion
converter-web-client | web | - |Web interface (front end) for the converter
converter-web-server | web | war |Web server (back end) for the converter
quakeml-web-service | ws-prod, ws-container | war | Service for extracting data from a QuakeML based database. Both QuakeML and Nordic format is supported as output
quakeml-web-service-ingestor | ingestor | jar |Executable jar file for ingesting the Seisan catalog into database
seisan-quakeml-commons-file | - | jar | Shared tools for file handling
seisan-quakeml-commons-jpa | - | jar | The Nordic format and QuakeML represented as java objects
seisan-quakeml-commins-web | - | jar | Shared tools for interactions with web

## Mapping
Mapping between QuakeML entities and Nordic lines. Check the excel file under documentation folder for more detailed information.

- Amplitude (From Nordic line type 1 and 4)
- Arrival (From Nordic line type 1 and 4)
- Event (From Nordic line type 1, 3, 6, I and S)
- FocalMechanism (From Nordic line type F)
- Magnitude (From Nordic line type 1)
- MomentTensor (From Nordic line type M)
- Origin (From Nordic line type 1 and E)
- OriginUncertainty (Currently no mapping)
- Pick (From Nordic line type 1 and 4)
- StationMagnitude (Currently no mapping)
- StationMagnitudeContribution (Currently no mapping)
