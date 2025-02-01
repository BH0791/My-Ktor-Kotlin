-- V1.0.0_init_data_model.sql
CREATE TABLE teams (
    id SERIAL PRIMARY KEY,
    name VARCHAR(50) NOT NULL
);

CREATE TABLE players (
    id SERIAL PRIMARY KEY,
    first_name VARCHAR(128) NOT NULL,
    team_id INT REFERENCES teams(id) ON DELETE CASCADE
);
