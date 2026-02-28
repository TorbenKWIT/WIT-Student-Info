
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

## Usage

### Create MBTA Object
`MBTA mbta = new MBTA();`

### Set API Key
`final String API_KEY = "PUT MBTA API KEY HERE"`

`mbta.setApiKey(apiKey);`

### MBTA Methods
### `pullData()`
Description: Pulls updated data from the MBTA REST API maps the data to HashMaps you should run this when you make the MBTA Object 


### `getAllStops()`
Description: returns a HashMap of all the Stop Objects
Example: 

### `getAllRoutes()`  
Description: returns a HashMap of all the Route Objects  
Example:



### `getStopsReachable()`  
Description: Takes in a Route Object and returns a ArrayList of all the Stops on a Route

Example:  
`ArrayList<Stop> stopsReachable = mbta.getStopsReachable(mbta.getAllRoutes().get("Green-D"));`
`System.out.println(stopsReachable.get(0).getName());`
`System.out.println(stopsReachable.get(0).getId()); `

`getTrainArrivals()` Takes in a Route Object and Stop Object and returns a ArrayList of the upcoming arrivals

Example:  
`System.out.println(mbta.getTrainArrivals(stops.get("place-tapst"), mbta.getAllRoutes().get("Green-C")));`

NOTE: when you  call the `getTrainArrivals()` method it will clear the items in the ArrayList before returning the new list this is due to limitations in how the MBTA API works

