**Log Extractor**

This repository creates a jar file which extracts data from a log file in the provided time range.

**Build**

From the root directory of the repo, run a gradle task
`./gradlew fatJar`
This task creates a jar file "LogExtractor-1.0-SNAPSHOT.jar" in the build/libs directory.

**Run Jar**

From build/libs directory, run the jar by following command
`java -jar -Di=fileLocation -Df=1990-03-31T20:12:38.1234Z -Dt=2020-05-31T20:12:38.1234Z LogExtractor-1.0-SNAPSHOT.jar 
`
The `Di` argument is for file location.
The `Df` argument is for from time.
The `Dt` argument is for to time.

**Conclusion** 

The JAR will print all the entries in the given range to console/command line
with the specified time of logging or any errors that might have occurred while running the JAR.
Please find the test log file in the resources folder.
