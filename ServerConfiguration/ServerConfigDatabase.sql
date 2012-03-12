CREATE TABLE ServerConfiguration
(
variable varchar(255),
value double
);

INSERT INTO ServerConfiguration
VALUES ('minMessageSize', 20.0);

INSERT INTO ServerConfiguration
VALUES ('maxMessageSize', 1500.0);

INSERT INTO ServerConfiguration
VALUES ('messageLife', 7);

INSERT INTO ServerConfiguration
VALUES ('mdvMessageLife', 30);

INSERT INTO ServerConfiguration
VALUES ('minEmailLength', 3);

INSERT INTO ServerConfiguration
VALUES ('maxEmailLength', 50);

INSERT INTO ServerConfiguration
VALUES ('minPasswordLength', 6);

INSERT INTO ServerConfiguration
VALUES ('minPasswordLength', 20);

INSERT INTO ServerConfiguration
VALUES ('speedThreshhold', 15);

INSERT INTO ServerConfiguration
VALUES ('defaultMessageRadius', 100);

