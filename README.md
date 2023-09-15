## Weather Report Metrics Project

The Weather Report Metrics Project is a Spring Boot-based RESTful API service that allows you to manage and query weather data. It provides endpoints for creating weather data records, querying data based on various parameters, and to calculate statistics(min,max,average and sum).

## Features

- Create weather data records with sensorNumber, metricName, Date, and value.
- Query sensor data based on sensorNumber, metricName, date range, and statistics (min, max, sum, average).
- Retrieve statistics for specific metrics within a specified date range.

## Getting Started

Follow these instructions to set up and run the Weather data Report Metrics Project on your local machine.

## Prerequisites

- Java Development Kit (JDK) 8 or later
- Apache Maven (for building and managing dependencies)
- Spring Boot

## Installation

1. Clone this repository to your local machine:

   ```shell
   https://github.com/honeybhagya/weather-data-report-api.git
Navigate to the project directory:
  - cd weather-report-metrics
  Build the project using Maven:
  - mvn clean install
  Run the application:
  - java -jar target/weather-report-metrics-1.0.0.jar

The application should now be running on http://localhost:8080.

##Usage

###Add weather data record

To create a new weather data record, we have to make an HTTP POST request to the /api/sensors/add endpoint with a JSON payload containing the sensor data details. 

http://localhost:8080/api/sensors/add

Here's a sample JSON:

-Positive Scenario:

{
  "sensorNumber": 1,
  "metricName": "temperature",
  "date": "2023-09-13",
  "value": 25.5
}

output: "Weather data created successfully"

 -Negative scenario:
{
  "sensorNumber": 1,
  "date": "2023-09-13",
  "value": 25.5
}

output:"Missing required fields in weather data"

###Query weather data

To query weather data, make an HTTP GET request to the /api/sensors/query endpoint with appropriate query parameters. You can filter data by sensor Number, metric Name, date range, and statistics.

Below is the sample query

 -Positive scenario: 
 Query average temperature for sensor 1 between 2023-09-01 and 2023-09-30

"http://localhost:8080/api/sensors/query?sensorNumber=1&metricName=temperature&statistic=average&startDate=2023-09-01&endDate=2023-09-30"

output : 25

 -Negative scenario:
Query with missing parameters 

"http://localhost:8080/api/sensors/query?sensorNumber=1&metricName=test&statistic=average&startDate=2023-09-01&endDate=2023-09-30"

output: 404 Not Found

##Features Missing

Updating Weather Data (PUT Request):

Create a method to handle PUT requests for updating weather data. This method should take the unique identifier of the data to be updated (id) as a path variable and the updated data in the request body.
Validate the input data to ensure it is complete and valid.
Use the id to look up the existing weather data in the database.
If the data with the specified id exists, update it with the new data. If it doesn't exist, return a 404 Not Found response.
Return a success message and an appropriate HTTP status code (e.g., 200 OK) if the update is successful.

Deleting Weather Data (DELETE Request):

Create a method to handle DELETE requests for deleting weather data. This method should take the unique identifier of the data to be deleted (id) as a path variable.
Use the id to check the existing weather data in your database.

If the data with the specified id exists, delete it. If it doesn't exist, return a 404 Not Found response.
Return a success message and an appropriate HTTP status code (e.g., 204 No Content) if the deletion is successful.

Pagination:

The controller currently lacks pagination. As the volume of data grows, pagination becomes crucial for efficiently retrieving data in manageable chunks. 
