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


