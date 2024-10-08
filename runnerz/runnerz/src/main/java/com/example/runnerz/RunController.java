package com.example.runnerz;

import com.example.run.RunNotFoundException;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController // This annotation is used to create RESTful web services using Spring MVC
@RequestMapping(
    "/api/runs") // RequestMapping at the contoroller level, every method with a mapping falls under
// this path
public class RunController {

  private final RunRepository runRepository;

  public RunController(RunRepository runRepository) {
    this.runRepository = runRepository;
  }

  @GetMapping(
      "") // This annotation maps HTTP requests to handler methods of MVC and REST controllers
  List<Run> findAll() {
    return runRepository.findAll();
  }

  @GetMapping("/1") // Wrong way to do this, hard coded value of one, want to use dynamic
  // variables
  Run findbyId() {
    return runRepository.findAll().get(0);
  }

  // // Read (Get)

  @GetMapping("/{id}") // This is a dynamic variable, the value of id will be passed in the URL
  Run findById(@PathVariable Integer id) {
    Optional<Run> run = runRepository.findById(id);
    if (run.isEmpty()) {
      throw new RunNotFoundException(); // If the run is not found, throw a 404 error
    }
    return run
        .get(); // The get() method of the Optional Class returns the value if present, otherwise
    // will throw a NoSuchElementException
  }

  // // Create (Post)
  @ResponseStatus(
      HttpStatus.CREATED) // This annotation is used to specify the HTTP response status code in the
  // controller
  // 201 instead of 200, notifies sender something is created
  @PostMapping("")
  void create(@Valid @RequestBody Run run) { // Somehow needs to pass in a run, N
    runRepository.create(run);
  }

  // // Update (Put)
  @ResponseStatus(HttpStatus.NO_CONTENT)
  @PutMapping("/{id}")
  void update(@Valid @RequestBody Run run, @PathVariable Integer id) {
    runRepository.update(run, id);
  }

  // // Delete (Delete)
  @ResponseStatus(HttpStatus.NO_CONTENT)
  @DeleteMapping("/{id}")
  void delete(@PathVariable Integer id) {
    runRepository.delete(id);
  }
}
