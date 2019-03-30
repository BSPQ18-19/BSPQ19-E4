EXECUTION OF RMIREGISTRY, SERVER AND CLIENT AS 3 PROCESSES
----------------------------------------------------------

First, compile the whole code:
1. mvn compile

Then, in three separate cmd windows, run:

1. rmiregistry -J-Djava.rmi.server.useCodebaseOnly=false
2. mvn exec:java -Pserver
3. mvn exec:java -Pclient


EXECUTION OF JUNIT TEST THAT LAUNCHES THE WHOLE THREE PROCESSES
---------------------------------------------------------------
In one single cmd window, run:
1. mvn test


DataNucleus Tutorial for JDO using Maven2
=========================================
This relies on the DataNucleus Maven2 plugin.
You can download this plugin from the DataNucleus downloads area.

1. Set the database configuration in the "datanucleus.properties" file.

2. Make sure you have the JDBC driver jar specified in the "pom.xml" file

3. Run the command: "mvn clean compile"
   This builds everything, and enhances the classes 

4. Run the command: "mvn datanucleus:schema-create"
   This creates the schema for this sample.

5. Run the command: "mvn exec:java"
   This runs the tutorial

6. Run the command: "mvn datanucleus:schema-delete"
   This deletes the schema for this sample. See note for 4 also.



(c) Copyright 2008-2011 DataNucleus
