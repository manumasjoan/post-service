package com.example.jibberjabber;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.metrics.export.datadog.EnableDatadogMetrics;

//@EnableDatadogMetrics
@SpringBootApplication
public class JibberJabberApplication {

	public static void main(String[] args) {
		SpringApplication.run(JibberJabberApplication.class, args);
	}

}
