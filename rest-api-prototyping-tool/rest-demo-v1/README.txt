The application runs on a provided jetty server. You just need to run:
  java -cp jetty-runner.jar org.mortbay.jetty.runner.Runner --port 8180 --path rest-bank rest-bank.war --path rest-doc rest-doc.war

This will start a jetty server running on port 8180 serving two applications:
  - http://localhost:8180/rest-bank
  - http://localhost:8180/rest-doc


***** NOTE *******************************************************************************************
|	
|	Documentation not working 
|
| 	We have noticed that many people have had issues when trying to run the documentation. 
|	This seems to occur when the java call runs from the jre, rather than from inside the JDK  
|	We recommend that in order to stop this issue, You replace java with the full Java path  to the JDK as seen below.
|
|	For Example:
|
|	java -cp jetty-runner.jar org.mortbay.jetty.runner.Runner --port 8180 --path rest-bank rest-bank.war --path rest-doc rest-doc.war
|
| "C:\Program Files\Java\jdk1.7.0_25\bin\java.exe" -cp  jetty-runner.jar org.mortbay.jetty.runner.Runner --port 8180 --path rest-bank rest-bank.war --path rest-doc rest-doc.war
| 
|
*******************************************************************
	HOW TO ACCESS A DEMO OF SERVICES PROVIDED BY THE API
=================================
|	Download Jmeter from here: http://apache.mirrors.timporter.net//jmeter/binaries/apache-jmeter-2.9.zip
|	Unzip the folder
|	go to the path ../apache-jmeter-2.9/bin and run jmeter.bat
|	In the GUI window that opened go to file>open and browse to "location you extracted the zip"/jmeter/main/happyPath.jmx
|	At the same location TestControlPanel can be found. This is a more comprehensive demo showing all possibilities including errors.
|	The said control panel runs several different modules which can be run independently. These can be found in the jmModules folder.

*******************************************************************
	HOW TO LOG IN TO THE SERVICES PROVIDED BY THE API
===================================================================
|	You can use any of these customer Ids to log in and where password is required , use customer Id as password too.
|	
|	69000,69001,69002,69003,69003,69004,69005,69006,69007,69008,69009,69010,69011,69012,69013,69014,69015
|
