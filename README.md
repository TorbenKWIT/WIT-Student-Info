
# UI (Tyler)

# Web Scraping Backend(Danny)

# MBTA Backend Information(Torben)

This is the information on the Transit Backend 
## Get MBTA API Key
1. go to https://api-v3.mbta.com/register and create a account
2. once you have created an account go to https://api-v3.mbta.com/portal and click on request new key
3. save the key somewhere safe 

## Build

1. clone GitHub repo
2. inside the directory the repo is in run the command
	1. `mvn package` (this will build the jar file with the necessary dependency)
3. move into the target directory
4. run `java -jar transit-1.0-SNAPSHOT.jar`

