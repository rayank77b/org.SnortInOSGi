README

This is org.SnortInOSGi project. This project build a monitoring solution for Snort.
This project is GPL

This project was part of my master thesis on Brunel University of London.
At moment it is in alpha stadium.

TODO:
- a lot of to do (english learn too ;)
- create github
- create trac
- add copyright GPL
- write a better README
- ....



this package has the needed bundles (osgi, equinox and r-osgi) and a description how to build.

Important properties

org.SnortInOSGi.backend.remotePort     remote port of backend
org.SnortInOSGi.backend.remoteIP       remote IP of backend

org.SnortInOSGi.dbanalyzer.remotePort  remote port of DBAnalyzer
org.SnortInOSGi.dbanalyzer.remoteIP    remote IP of DBAnalyzer

org.SnortInOSGi.bundle.dbconnector.configFile The paht of XML-config file



build Backend:
1) go to Manifest file: -> Exporting -> Export Wizard
  select:
  org.SnortInOSGi.bundle.DBconnector
  org.SnortInOSGi.bundle.MySQLConnector
  org.SnortInOSGi.bundle.WebStatus
  org.SnortInOSGi.backend.Cache
  org.SnortInOSGi.backend.SMDBreader
  org.SnortInOSGi.backend.SMDBwriter
  org.SnortInOSGi.bundle.Config
  org.SnortInOSGi.openAPI.snortschema
  org.SnortInOSGi.openAPI.interfaces
  
  select a Directory (example /tmp/snortinosgi/backend) -> Finish

2) go to path of workspace/buildPackages
   start:  /bin/sh createStartFileBackend.sh /tmp/snortinosgi/backend
 
3) go to /tmp/snortinosgi/backend
   start java -jar ....  (see last output of createStartFileBackend.sh)
   
   maybe you should set the right properties in config/config.ini (port, ip, path to xmlfile)



build DBAnalyzer:
1) go to Manifest file: -> Exporting -> Export Wizard
  select:
  org.SnortInOSGi.backend.Cache
  org.SnortInOSGi.bundle.Config
  org.SnortInOSGi.bundle.DBconnector
  org.SnortInOSGi.bundle.MySQLConnector
  org.SnortInOSGi.bundle.WebStatus
  org.SnortInOSGi.dbanalyzer.DBAreader
  org.SnortInOSGi.dbanalyzer.RebuildDB
  org.SnortInOSGi.openAPI.interfaces
  org.SnortInOSGi.openAPI.snortschema
  
  select a Directory (example /tmp/snortinosgi/dbanalyzer) -> Finish

2) go to path of workspace/buildPackages
   start:  /bin/sh createStartFileDBAnalyzer.sh /tmp/snortinosgi/dbanalyzer
 
3) go to /tmp/snortinosgi/dbanalyzer
   start java -jar ....  (see last output of createStartFileDBAnalyzer.sh)
   
   maybe you should set the right properties in config/config.ini (port, ip, path to xmlfile)
   
   
build Testing osgi-runtime for backend and dbanalyzer:
1) go to Manifest file: -> Exporting -> Export Wizard
  select:
  org.SnortInOSGi.openAPI.interfaces
  org.SnortInOSGi.openAPI.snortschema
  org.SnortInOSGi.zzzRemoteTests
  select a Directory (example /tmp/snortinosgi/tests) -> Finish

2) go to path of workspace/buildPackages
   start:  /bin/sh createStartFileTest.sh /tmp/snortinosgi/tests
 
3) go to /tmp/snortinosgi/tests
   start java -jar ....  (see last output of createStartFileTest.sh)
   
   maybe you should set the right properties in config/config.ini (port, ip, path to xmlfile)


