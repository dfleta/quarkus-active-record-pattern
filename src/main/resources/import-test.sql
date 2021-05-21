DROP TABLE IF EXISTS Fruit;
CREATE TABLE Fruit
(
    id BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT UNIQUE,
    name VARCHAR (255) NOT NULL, 
    description VARCHAR(255), 
    PRIMARY KEY (id)
);
INSERT INTO Fruit (id, name, description) VALUES (1000, 'Apple', 'Winter fruit');
INSERT INTO Fruit (id, name, description) VALUES (2000, 'Pineapple', 'Tropical fruit');
