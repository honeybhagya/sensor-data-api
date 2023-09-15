package com.example.myassessment.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.myassessment.entity.WeatherData;
import com.example.myassessment.repository.WeatherDataRepository;

@RestController
@RequestMapping("/api/sensors")
public class WeatherMetricsController {

	@Autowired
	private WeatherDataRepository weatherDataRepository;  

	/**
	 * Add weather data to the database.
	 * 
	 * @param weatherData The weather data to be added, provided in the request body.
	 * @return ResponseEntity with a status code and a message indicating the result of the operation.
	 */

	@PostMapping("/add")
	public ResponseEntity<String> addWeatherData(@RequestBody WeatherData weatherData) {
		// Save the incoming sensor data to the database
		if (weatherData.getSensorNumber() == null || weatherData.getMetricName() == null) {
			return ResponseEntity.badRequest().body("Missing required fields in weather data");
		}

		weatherDataRepository.save(weatherData);
		return ResponseEntity.status(201).body("Weather data created successfully");
	}


	/**
	 * Query weather data based on specified parameters.
	 *
	 * @param sensorNumber for filtering the weather data.
	 * @param metricName   for filtering the weather data.
	 * @param statistic    To calculate (min, max, sum, average) of weather data.
	 * @param startDate    To get the data in the specified date range filter (optional).
	 * @param endDate      To get the date in the specified date range filter (optional).
	 * @return ResponseEntity containing the calculated statistic or an error message.
	 */

	@GetMapping("/query")
	public ResponseEntity<?> queryWeatherData(
			@RequestParam(name = "sensorNumber") Long sensorNumber,
			@RequestParam(name = "metricName") String metricName,
			@RequestParam(name = "statistic") String statistic,
			@RequestParam(name = "startDate", required = false)
			@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
			@RequestParam(name = "endDate", required = false)
			@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {

		// Calculate the default start date (one month ago)
		if (startDate == null) {
			startDate = LocalDate.now().minusMonths(1);
		}

		// Calculate the default end date (today)
		if (endDate == null) {
			endDate = LocalDate.now();
		}


		// query weather data based on the given parameters
		List<WeatherData> weatherDataList = weatherDataRepository.findBySensorNumberAndMetricNameAndDateBetween(sensorNumber, metricName, startDate, endDate);

		// Based on statistic calculated min, max, sum, or average.
		double report = 0.0;
		
		if (weatherDataList.isEmpty()) {
			return ResponseEntity.notFound().build(); // Return 404 when no data is found
		}

		if (!weatherDataList.isEmpty()) {
			if ("min".equalsIgnoreCase(statistic)) {
				report = Double.MAX_VALUE;
				for (WeatherData data : weatherDataList) {
					if (data.getValue() < report) {
						report = data.getValue();
					}
				}
			} else if ("max".equalsIgnoreCase(statistic)) {
				report = Double.MIN_VALUE;
				for (WeatherData data : weatherDataList) {
					if (data.getValue() > report) {
						report = data.getValue();
					}
				}
			} else if ("sum".equalsIgnoreCase(statistic)) {
				for (WeatherData data : weatherDataList) {
					report += data.getValue();
				}
			} else if ("average".equalsIgnoreCase(statistic)) {
				double sum = 0.0;
				for (WeatherData data : weatherDataList) {
					sum += data.getValue();
				}
				report = sum / weatherDataList.size();
			} else {
				return ResponseEntity.badRequest().body("Invalid statistic: " + statistic);
			}
		}


		// report is Returned as JSON response
		return ResponseEntity.ok(report);
	}


}
