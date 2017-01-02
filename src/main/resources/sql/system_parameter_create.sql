CREATE TABLE system_parameter (
    id SERIAL PRIMARY KEY,
    key VARCHAR(50) NOT NULL UNIQUE,
    value VARCHAR(500) NOT NULL
    );