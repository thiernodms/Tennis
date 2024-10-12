package com.thierno.tennis.service;

import com.thierno.tennis.ApplicationStatus;
import com.thierno.tennis.HealthCheck;
import com.thierno.tennis.data.HealthCheckRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HealthCheckService {
    @Autowired
    private HealthCheckRepository healthCheckRepository;

    public HealthCheck healthCheck(){
        Long activeSession = healthCheckRepository.countApplicationConnections();
        if (activeSession > 0){
            return new HealthCheck(ApplicationStatus.OK, "Welcome to Thierno tennis app");
        }else {
            return new HealthCheck(ApplicationStatus.KO, "Thierno tennis isn't fully functionnal, please check your configuration");
        }
    }
}
