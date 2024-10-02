CREATE TABLE IF NOT EXISTS RunsTable (
    id INT NOT NULL,
    title varchar(250) NOT NULL,
    started_on timestamp NOT NULL,
    completed_on timestamp NOT NULL,
    distance INT NOT NULL,
    location varchar(10) NOT NULL,
    PRIMARY KEY (id)
);
