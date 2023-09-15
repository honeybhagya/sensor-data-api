package com.example.myassessment.entity;

import java.time.LocalDate;
import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class WeatherData {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private Long sensorNumber;
	private String metricName;
	private double value;
	private LocalDate date; 

	public Long getSensorNumber() {
		return sensorNumber;
	}

	public void setSensorNumber(Long sensorNumber) {
		this.sensorNumber = sensorNumber;
	}

	public String getMetricName() {
		return metricName;
	}

	public void setMetricName(String metricName) {
		this.metricName = metricName;
	}

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDatetime(LocalDate date) {
		this.date = date;
	}

	public WeatherData(Long sensorNumber, String metricName, double value, LocalDate date) {
		super();
		this.sensorNumber = sensorNumber;
		this.metricName = metricName;
		this.value = value;
		this.date = date;
	}

	public WeatherData() {
		super();
	}

}
