package com.example.runnerz;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.expression.spel.ast.TypeReference;
import org.springframework.stereotype.Component;

@Component
public class RunJsonDataLoader implements CommandLineRunner {

  private static final Logger log = LoggerFactory.getLogger(RunJsonDataLoader.class);
  private final RunRepository runRepository;
  private final ObjectMapper objectMapper;

  public RunJsonDataLoader(RunRepository runRepository, ObjectMapper objectMapper) {
    this.runRepository = runRepository;
    this.objectMapper = objectMapper;
  }

  @Override
  public void run(String... args) throws Exception {
    if (runRepository.count() == 0) {
      try (InputStream inputStream = TypeReference.class.getResourceAsStream("/data/runs.json")) {
        Runs allruns = objectMapper.readValue(inputStream, Runs.class);
        log.info("Loading data from runs.json and savinf to a database", allruns.runs().size());
        runRepository.saveAll(allruns.runs());
      } catch (IOException e) {
        throw new RuntimeException("Failed to load data from runs.json", e);
      }

    } else {
      log.info("Data already loaded");
    }
  }
}
