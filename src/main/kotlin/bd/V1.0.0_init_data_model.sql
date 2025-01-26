-- V1.0.0_init_data_model.sql
CREATE TABLE teams (
    id SERIAL PRIMARY KEY,
    name VARCHAR(128),
    CONSTRAINT name_length CHECK (LENGTH(name) >= 3 OR name IS NULL)
);

CREATE INDEX name_and_id_index ON teams (id, name);
