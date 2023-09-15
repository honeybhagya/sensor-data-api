package com.example.myassessment.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.example.myassessment.entity.WeatherData;
import com.example.myassessment.repository.WeatherDataRepository;

@RunWith(MockitoJUnitRunner.class)
public class WeatherMetricsControllerTest {



	@Mock
	private WeatherData weatherData;
	@Mock
	private WeatherDataRepository weatherDataRepository;
	@InjectMocks
	private WeatherMetricsController weatherMetricsController;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	public void testAddWeatherData_ValidInput() {
		// Created a sample WeatherData with valid data
		Long sensorNumber = 1L; 
		String metricName = "temperature";  
		LocalDate endDate = LocalDate.now(); double
		reportValue =15; 

		WeatherData weatherData = new WeatherData(sensorNumber, metricName,reportValue, endDate );

		// save method of WeatherDataRepository
		when(weatherDataRepository.save(weatherData)).thenReturn(weatherData);

		// Call the addWeatherData method
		ResponseEntity<String> response = weatherMetricsController.addWeatherData(weatherData);

		// Verifying that the save method was called
		verify(weatherDataRepository, times(1)).save(weatherData);

		// Success message, response code and body
		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		assertEquals("Weather data created successfully", response.getBody());
	}

	@Test
	public void testAddWeatherData_MissingData() {
		//sample WeatherData object with required data missing
		WeatherData weatherData = new WeatherData();

		// Call the addWeatherData method
		ResponseEntity<String> response = weatherMetricsController.addWeatherData(weatherData);

		// Bad request status code and error message
		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
		assertTrue(response.getBody().contains("Missing required fields"));
	}

	@Test
	public void testQueryWeatherData_InvalidParameters() {
		// QueryWeatherData method is called with missing parameters
		ResponseEntity<?> response = weatherMetricsController.queryWeatherData(null, "temperature", "average", LocalDate.now().minusMonths(1), LocalDate.now());

		// Bad Request status code.
		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

	}

	@Test
	public void testQueryWeatherData_NoDataFound() {
		// Passed the empty value
		when(weatherDataRepository.findBySensorNumberAndMetricNameAndDateBetween(anyLong(), anyString(), any(), any()))
		.thenReturn(Collections.emptyList());

		// Call the queryWeatherData method
		ResponseEntity<?> response = weatherMetricsController.queryWeatherData(1L, "temperature", "average", LocalDate.now().minusMonths(1), LocalDate.now());

		// Not found status code
		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
	}


	@Test
	public void testQueryWeatherData_Positive() {
		// Pass the valid weather Data
		WeatherData weatherData = new WeatherData();
		weatherData.setMetricName("Temperature");
		weatherData.setSensorNumber(1L);
		weatherData.setValue(15L);
		List<WeatherData> mockWeatherDataList = new ArrayList<>();
		mockWeatherDataList.add(weatherData);

		// Return the sample WeatherData list
		when(weatherDataRepository.findBySensorNumberAndMetricNameAndDateBetween(anyLong(), anyString(), any(), any()))
		.thenReturn(mockWeatherDataList);

		//queryWeatherData method is called with valid parameters
		ResponseEntity<?> response = weatherMetricsController.queryWeatherData(1L, "temperature", "average", LocalDate.now().minusMonths(1), LocalDate.now());

		// 200 response status code
		assertEquals(HttpStatus.OK, response.getStatusCode());

	}
}
	



