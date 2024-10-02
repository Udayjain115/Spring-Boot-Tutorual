package com.example.runnerz;

import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

@Repository // This annotation is used to indicate that the class provides the mechanism for
// storage, retrieval, search, update and delete operation on objects
public class RunRepository {

  private static final Logger log = LoggerFactory.getLogger(RunRepository.class);
  private final JdbcClient jdbcClient;

  public RunRepository(JdbcClient jdbcClient) {
    this.jdbcClient = jdbcClient;
  }

  public List<Run> findAll() {
    return jdbcClient.sql("select * from RunsTable").query(Run.class).list();
  }

  public Optional<Run> findById(Integer id) {
    return jdbcClient
        .sql("select * from RunsTable where id = :id")
        .param("id", id)
        .query(Run.class)
        .optional();
  }

  public void create(Run run) {
    var updated =
        jdbcClient
            .sql(
                "INSERT INTO RunsTable(id, title, started_on, completed_on, distance, location)"
                    + " values(?, ?, ?, ?, ?, ?)")
            .params(
                List.of(
                    run.id(),
                    run.title(),
                    run.startedOn(),
                    run.completedOn(),
                    run.distance(),
                    run.location().toString()))
            .update();

    Assert.state(updated == 1, "Run not created" + run.title());
  }

  public void update(Run run, Integer id) {
    var updated =
        jdbcClient
            .sql(
                "UPDATE RunsTable SET title = ?, started_on = ?, completed_on = ?, distance = ?,"
                    + " location = ? WHERE id = ?")
            .param(
                List.of(
                    run.title(),
                    run.startedOn(),
                    run.completedOn(),
                    run.distance(),
                    run.location(),
                    id))
            .update();

    Assert.state(updated == 1, "Run not updated" + run.title());
  }

  public void delete(Integer id) {
    var updated = jdbcClient.sql("DELETE FROM RunsTable WHERE id = :id").param("id", id).update();

    Assert.state(updated == 1, "Run not deleted" + id);
  }

  public int count() {
    return jdbcClient.sql("select * from RunsTable").query().listOfRows().size();
  }

  public void saveAll(List<Run> runs) {
    runs.stream().forEach(this::create);
  }

  public List<Run> findByLocation(String location) {
    return jdbcClient
        .sql("select * from RunsTable where location = :location")
        .param("location", location)
        .query(Run.class)
        .list();
  }
}
