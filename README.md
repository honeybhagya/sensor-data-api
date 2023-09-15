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
   git clone https://github.com/your-/weather-report-metrics.git

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

Here's an sample JSON:

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
  "metricName": "",
  "date": "2023-09-13",
  "value": 25.5
}

output:"Missing required fields in weather data"

###Query weather data

To query weather data, make an HTTP GET request to the /api/sensors/query endpoint with appropriate query parameters. You can filter data by sensor Number, metric Name, date range, and statistics.

Below is the sample query

 -Positive scenario 
 Query average temperature for sensor 1 between 2023-09-01 and 2023-09-30

"http://localhost:8080/api/sensors/query?sensorNumber=1&metricName=temperature&statistic=average&startDate=2023-09-01&endDate=2023-09-30"

output : 25

 -Negative scenario
Query with missing parameters 

"http://localhost:8080/api/sensors/query?sensorNumber=&metricName=temperature&statistic=average&startDate=2023-09-01&endDate=2023-09-30"

output: "Invalid parameters" 


