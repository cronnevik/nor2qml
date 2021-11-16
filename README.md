# Nordic to QuakeML converter tool package

![GitHub release (latest by date)](https://img.shields.io/github/v/release/cronnevik/nor2qml)
[![License](https://img.shields.io/badge/License-Apache_2.0-yellowgreen.svg)](https://opensource.org/licenses/Apache-2.0)

* [What is Nor2QML?](#what-is-nor2qml)
* [Applications within the package](#applications)
* [How to download and build the applications?](#how-to-download-and-build-the-applications)
* [How to run the applications?](#how-to-run-the-applications)
  * [Executable jars](#executable-jars)
  * [War files](#war-files)
  * [Docker](#docker)
    * [Configuration file](#configure-database-catalog-path-and-tomcat-server) 
    * [Edit HTML for web-service](#configure-html-document-for-the-web-service)
    * [Make custom profiles](#make-own-custom-profiles-for-html-documents)
    * [Running docker](#running-docker)
* [Modules](#modules)
* [Mapping](#mapping)

## What is Nor2QML?
Nor2QML is a conversion tool between the Nordic (used in SEISAN) and QuakeML (European Standard) formats for provision 
of earthquake parametric data. The SEISAN seismic analysis system contains a set of programs for analyzing earthquakes 
from analog and digital data ([See official SEISAN documentation](http://seis.geus.net/software/seisan/seisan.pdf)).
The data is organized in catalogue structure using the file system. The smallest basic unit is a file (the S-file) 
containing original phase readings (arrival times, amplitude, period, azimuth, and apparent velocity) for one event. 
These s-files are expressed through the Nordic format, which since 1985 has been the common exchange format in the 
Nordic countries. The standard format for provision of earthquake parametric data within various European 
infrastructures is QuakeML ([See official QuakeML documentation](https://quake.ethz.ch/quakeml/docs/latest?action=AttachFile&do=get&target=QuakeML-BED.pdf)).

In addition, the Nor2QML package include a set of tools for easier exchange of earthquake parametric data 
between applications. The interaction is organized through a web-service , where A client can send a request over 
the internet for data through a web-service and receive the data in multiple formats (Nordic, Nordic2, 
QuakeML, Text) of their choice. 

## Applications
* Standalone converter (*executable jar*) - file conversion locally on the computer
* Ingestor (*executable jar*) - providing data to the web-service
* Web-service (*war file*) for data exchange (QuakeML, Nordic and text format)
* Web converter (*war file*) - web application for conversion in the browser - server and client

## How to download and build the applications?
To download, build and run the various modules ([see list below](#modules)) within this package, you first need to have installed Git on you computer. See the official documentation for Git on: https://git-scm.com/

Clone the project from this GitHub repository into your desired folder by entering the command:\
``` git clone https://github.com/cronnevik/nor2qml.git ```

The nor2qml package is build upon Java (Spring boot) with Apache Maven as the build tool. In order to build the individual modules, both java and maven (https://maven.apache.org/) needs to be installed.

To simplify the build of the applications and the modules they depend on, maven profiles have been applied. See the list of applications and modules. 

To build an application, enter the command:\
``` mvn clean install -P  <maven profile name>```

The maven profile name is found in the [modules list below](#modules). The maven build tool then produce a jar or a war file which is found in the *target* folder within the component/application folder.

## How to run the applications?
For the executable jars and war files, please read first in the previous section on how to build the applications.

### Executable jars
The jar files (located in the target folder of the application after building the application) is executable by the command:\
``` java -jar <name of file.jar> ```

Please be aware that the executable *quakeml-web-service-ingestor* application require a MySQL database running.
Configurations for the MySQL connection can be altered by changing the values in the *.env* file located 
in the project root folder.

### War files
The war files produces are applications ment for servers. For deployment these could be uploaded and made runnable by a Tomcat server.\
If you would like to run the server applications locally, an embedded tomcat server is applied and can be initiated by navigating into the application folder
and execute the command: 

``` mvn spring-boot:run -Dspring.profiles.active=prod ```

Please be aware that the *quakeml-web-service*mapplication require a MySQL database running.
Configurations for the MySQL connection can be altered by changing the values in *.env* file located
in the project root folder.

### Docker
A docker configuration has also been implemented to bypass the requirement of installing 
MySQL (ingestor, web-service) and a tomcat server for the web-service. Within docker, a virtual containers spins up 
to initiate the database, host the web-service application and populates the database with data
from one or multiple catalog(s).

#### Configure Database, catalog path and Tomcat server
A configuration file (.env) exists in the main project folder for configuring credentials and ports for the MySQl database 
and tomcat server. The application name can also be altered, whose name will be the end path of your hosting domain.
In addition, a path to REA folder (or alternative folder where catalogs are located) needs to
be specified within the .env file. A catalogs options exists to select one or multiple catalogs
to be accessible through the web-service. It is important to keep the quotes and separate each 
catalog with a space.

``` 
DB_NAME=dbname
DB_USER=username
DB_PW=password
DB_ROOT_PW=password
DB_PORT=3306
TOMCAT_USER=username
TOMCAT_PASSWORD=password
TOMCAT_PORT=8090
WS_APP_NAME=eqcat
REA_FOLDER=C:/SEISAN/REA
CATALOGS="CATALOG1_ CATALOG2_"
PROFILE=default
```

#### Configure html document for the web-service
The query builder page can be customised to your needs. The application has build
in profiles for change of content in title, header and footer. To add your own
custom content for these sections, please edit the default section (html comment is
given where you can add your html code) within *title.html*, *header.html* and
*footer.html* in the following folder:

``` quakeml-web-service -> src -> main -> resources -> pages -> fragments ```

After changes are made, if you have already built (applying the run command) the docker images, please make sure to remove
the previous build images and rebuild (description given in section about running docker).

#### Make own custom profiles for html documents
If you want to make your own profile, or multiple profiles for various deployments,
you can do so by adding another fragment within the same files as mentioned in the
previous section. For example:

``` 
<header th:fragment="default-header"></header> 
```

Replace the *default-header* with you custom reference name. Then go into the *index.html file*
within the pages folder (one level above the fragments folder) and add another statement below the following block:

```
<div th:replace="${profile == 'default'} ? ~{fragments/header :: default-header} : ~{}" />
```

Change *default-header* with you custom reference name, and change the *default* name in 
*${profile == 'default'}* with a profile name of your choice. The last step is to apply this 
profile name within the *.env* file in the root folder of all the projects. Change the 
*default* in *PROFILE=default* with the name your selected for your new profile.

After changes are made, if you have already built (applying the run command)  the docker images, please make sure to remove
the previous build images and rebuild (description given in section about running docker). 

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

To remove the images created (useful for rebuild if changes are made to the html documents
or other files within the applications), use the following command:\
``` docker-compose down --rmi=all ```

To build the images again, enter the *docker-compose up* command again.

## Modules
Components | Maven profiles | Produces | Description
---------- |----------- |----------- | -----------
converter-core | - | jar |Reading and mapping formats 
converter-standalone | standalone | jar | Executable jar file for conversion
converter-web-client | web | - |Web interface (front end) for the converter
converter-web-server | web | war |Web server (back end) for the converter
quakeml-web-service | ws-prod, ws-container | war | Service for extracting data from a QuakeML based database. Both QuakeML and Nordic format is supported as output
quakeml-web-service-ingestor | ing-prod, ing-container | jar |Executable jar file for ingesting the Seisan catalog into database
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
