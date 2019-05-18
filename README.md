# CarRenting
 
> Car Renting is a tool desingned to rent and manage the cars and rents as easily as possible. Users can rent cars by imputing the garages of origin and destination and the starting and ending dates of the rent. Also, administrator members can edit all the information regarding garages and cars and see statistics of the renting services. Finally, employees can see all the rents. 
 


## Usage


### Instalation


**Previous steps:**

* Stablish de conection to the database named carrenting in localhost

* Username and password of the Database are "root"

Use this command in the root of the project:

```
mvn compile
```


Then this one to create the database schema:

```
mvn datanucleus:schema-create
```

### Execution

First we hace to execute the Server, for that we use the next command:

```
mvn exec:java -P server
```

Finally, to execute the client we need to use this command:

```
mvn exec:java -P client```
