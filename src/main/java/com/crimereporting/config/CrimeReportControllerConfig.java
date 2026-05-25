package com.crimereporting.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;

@Configuration
@Profile("local")
@PropertySource("classpath:application-gcp.properties")
public class CrimeReportControllerConfig {
}
