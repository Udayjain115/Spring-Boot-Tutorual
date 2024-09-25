package com.example.runnerz;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;



public record Run(
    Integer id,
    @NotEmpty
    String title,
    LocalDateTime startedOn, 
    LocalDateTime completedOn,
    @Positive
    Integer distance,
    Location location


    ){

        public Run {
            if(startedOn.isAfter(completedOn)){
                throw new IllegalArgumentException("Start date must be before end date");
            }
        }
    }
