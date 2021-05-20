/*DROP TABLE Fruit IF EXISTS;*/
CREATE TABLE fruit IF NOT EXISTS
(
    id BIGINT(20), 
    name VARCHAR (255) NOT NULL, 
    description VARCHAR(255), 
    PRIMARY KEY (id)
);

