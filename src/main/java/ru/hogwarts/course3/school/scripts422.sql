CREATE TABLE cars
(
    Id    SERIAL,
    Brand VARCHAR,
    MODEL VARCHAR,
    Price MONEY,
    CONSTRAINT car_id_pk PRIMARY KEY (Id)
);

CREATE TABLE persons
(
    Id          REAL,
    Name        TEXT PRIMARY KEY,
    Age         INTEGER,
    Has_license BOOLEAN,
    Car_id      INT REFERENCES cars (id)
);

SELECT persons.name, persons.age, persons.Has_license, cars.brand, cars.model
FROM persons
         INNER JOIN cars ON persons.Car_id = cars.id;