CREATE TABLE testing_info (
    id SERIAL PRIMARY KEY NOT NULL,
    number_of_test INT NOT NULL,
    error REAL NOT NULL,
    generation VARCHAR(50) NOT NULL,
    network_id INT REFERENCES network_info(id)
    );