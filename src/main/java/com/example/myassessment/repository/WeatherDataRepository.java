package com.example.myassessment.repository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.myassessment.entity.WeatherData;

@Repository
public interface WeatherDataRepository extends JpaRepository <WeatherData, Long> {
	    List<WeatherData> findBySensorNumberAndMetricNameAndDateBetween(
	    		Long sensorNumber, String metricName, LocalDate startDate, LocalDate endDate);
}
