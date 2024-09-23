package com.example.runnerz;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Repository;

import jakarta.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;


@Repository // This annotation is used to indicate that the class provides the mechanism for storage, retrieval, search, update and delete operation on objects
public class RunRepository {

    
    private List <Run> runs = new ArrayList<>();

    List<Run> findAll() {
        return runs;
    }

    Optional<Run> findById(Integer id){
        return runs.stream().filter(run -> run.id().equals(id)).findFirst();

        // Stream() converts the collection (runs) into a stream, a stream is a sequence of elements from the collection that allows you to perform operations on its elements
        // Used because it allows us to perform operations like filtering, mapping and reducting on the collection in a function way, without explicitly writing loops
        // fildter method will filer the stream created, based on a condition, in this case that condition is to keep only the ones that match the id parameter passed in, the arrow function is a lambda expression, it means for each run in the stream, check if run.id() is equal to id
        // Find first() returns the first element in the stream that matches the condition set by filter, find first will only return if the first match exists, because the stream has been filetered returns an optional, which is a container object that may or may not contain a non-null value
        // orElse() returns the value if present, otherwise returns the default value passed in, in this case null
    }

    void create (Run run) {
        runs.add(run);
    }

    @PostConstruct // Used to perform operations after dependency injection is done to perform any initialization
    private void init() {
        runs.add(new Run(
            1, "First Run", LocalDateTime.now(), LocalDateTime.now().plus(1, ChronoUnit.HOURS), 5, Location.OUTDOOR
        ));
        runs.add(new Run(
            2, "Second Run", LocalDateTime.now(), LocalDateTime.now().plus(2, ChronoUnit.HOURS), 5, Location.INDOOR
        ));

    }



}
