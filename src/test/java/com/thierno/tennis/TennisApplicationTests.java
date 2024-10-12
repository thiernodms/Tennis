package com.thierno.tennis;

import com.thierno.tennis.data.HealthCheckRepository;
import com.thierno.tennis.service.HealthCheckService;
import com.thierno.tennis.web.HealthCheckController;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class TennisApplicationTests {

	@Autowired
	private HealthCheckController healthCheckController;
	@Autowired
	private HealthCheckService healthCheckService;
	@Autowired
	HealthCheckRepository healthCheckRepository;
	@Test
	void contextLoads() {
		Assertions.assertThat(healthCheckController).isNotNull();
		Assertions.assertThat(healthCheckService).isNotNull();
		Assertions.assertThat(healthCheckRepository).isNotNull();


	}

}
