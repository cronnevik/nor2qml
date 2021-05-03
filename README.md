# Nordic to QuakeML converter tool package

## How to install
To download, build and run the various modules (see list below) within this package, you first need to have installed Git on you computer. See the official documentation for Git on: https://git-scm.com/

The nor2qml package is build upon Java (Spring boot) with Apache Maven as the build tool. In order to build the individual modules, both java and maven (https://maven.apache.org/) needs to be installed.

To simplify the build of the applications and the modules they depend on, maven profiles have been applied. See the list of applications and modules. 

To build an application, enter the command:\
``` mvn clean install -P  <maven profile name>```

The maven build tool then produce a jar or a war file which is found in the *target* folder within the component/application folder.\

The jar files is executable by:\
``` java -jar <name of file.jar> ```

The war files produces are applications ment for servers. For deployment these could be uploaded and made runnable by a Tomcat server.\
If you would like to run the server applications locally, an embedded tomcat server is applied and can be initiated by navigating into the application folder
and execute the command: 

``` mvn spring-boot:run ```

Please be aware that the *quakeml-web-service* and *quakeml-web-service-ingestor* applications require a MySQL database running.\
Configurations for the MySQL connection can be altered by changing the values in the *database.yml* file located within the application folder
following the path: *src/main/resources*

A docker configuration has also been implemented to bypass the requirement of installing MySQL and a possible tomcat server for the web-service.\ 
By running the docker-compose file a virtual container spins up to initiate the database and host the web-service application. 
The database setup like database name, user, password can be changed within the *docker-compose.yml* file directly, but please change the credentials
in the *database.yml* file for the applications as well as described in the previous paragraph. 

Run the docker by first installing Docker on your computer and then in the main folder, enter:\
``` docker-compose up```

To shutdown the docker instances, enter:\
``` docker-compose down ```

## Applications
 * Standalone converter (executable jar file for conversion)
 * Web converter (web application for conversion in the browser - server and client)
 * Web-service for data exchange (QuakeML, Nordic and text format)
 * Ingestor (executable jar for providing data to the web-service)

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
