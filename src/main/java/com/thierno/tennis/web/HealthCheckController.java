package com.thierno.tennis.web;

import com.thierno.tennis.HealthCheck;
import com.thierno.tennis.service.HealthCheckService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "HealthCheck API") // regroupe toutes les methode du HealthCheckController sous ce nom
@RestController
public class HealthCheckController {

    @Autowired
    private HealthCheckService healthCheckService;

    //Documentation de chaque methode
    @Operation(summary = "Returns application status", description = "Returns the application status")
    @ApiResponses( value = {
            //Documentation des differentes reponses possible
            @ApiResponse(responseCode = "200", description = "Healthcheck status with some details",
                    content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = HealthCheck.class))})
    })
    @GetMapping("/healthcheck")
    public HealthCheck healthCheck(){
        return healthCheckService.healthCheck();
    }
}
