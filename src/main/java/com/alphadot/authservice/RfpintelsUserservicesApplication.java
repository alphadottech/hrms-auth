package com.alphadot.authservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

@SpringBootApplication
@EntityScan(basePackageClasses = {
		RfpintelsUserservicesApplication.class,
        Jsr310JpaConverters.class
})
public class RfpintelsUserservicesApplication {

	public static void main(String[] args) {
		SpringApplication.run(RfpintelsUserservicesApplication.class, args);
	}

}
