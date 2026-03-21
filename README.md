# WIT Student HUB

## Get MBTA API Key
1. go to https://api-v3.mbta.com/register and create a account
2. once you have created an account go to https://api-v3.mbta.com/portal and click on request new key
3. save the key somewhere safe 

## Build
1. clone GitHub repo
2. Get a MBTA API KEY
3. once you have the mbta api key open the Main.java file and replace the API_KEY Variable value to the api key
2. open a terminal window and cd into project root directory (WIT-Student-Info) and run the command `mvm clean package` this will clear out the build dir and then compile the project and its dependencies.
3. once it says `BUILD SUCCESS` run the command java -jar target/student-info-1.0-jar.jar

## Usage
The WIT Student hub brings campus information about the libary, fitness centers, and the dining hall all in one place as well as a MBTA Tracker tool that lets you see when the next train is arriving on amy stops on the Green Line

## Possible Features that may be added in the future
1. being able to select from the Blue, Orange, and Red lines
2. booking library study rooms from within the app
3. map of where the current train are located

# Backend Information

### Create MBTA Object
`MBTA mbta = new MBTA();`

### Set API Key
`final String API_KEY = "PUT MBTA API KEY HERE"`

`mbta.setApiKey(apiKey);`

### MBTA Class Methods
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



NOTES:  
- when you  call the `getTrainArrivals()` method it will clear the items in the ArrayList before returning the new list this is due to limitations in how the MBTA API works
- The TrainArrival Object has a attribute called direction which is a string that has 2 possible values
  
  - 0 (train heading outbound)
  - 1 (train heading inbound) 
