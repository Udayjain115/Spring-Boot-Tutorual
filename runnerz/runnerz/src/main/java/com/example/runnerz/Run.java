package com.example.runnerz;

import java.time.LocalDateTime;


public record Run(
    Integer id,
    String title,
    LocalDateTime startedOn, 
    LocalDateTime CompletedOn,
    Integer distance,
    Location location

    ){}
