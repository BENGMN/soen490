CREATE TABLE ServerConfiguration
(
variableName varchar(255),
value double NOT NULL, 
PRIMARY KEY (variableName)
);

INSERT INTO ServerConfiguration
VALUES ('minMessageSizeBytes', 10000.0);

INSERT INTO ServerConfiguration
VALUES ('maxMessageSizeBytes', 50000.0);

INSERT INTO ServerConfiguration
VALUES ('messageLifeDays', 7);

INSERT INTO ServerConfiguration
VALUES ('advertiserMessageLifeDays', 30);

INSERT INTO ServerConfiguration
VALUES ('minEmailLength', 15);

INSERT INTO ServerConfiguration
VALUES ('maxEmailLength', 50);

INSERT INTO ServerConfiguration
VALUES ('minPasswordLength', 6);

INSERT INTO ServerConfiguration
VALUES ('maxPasswordLength', 20);

INSERT INTO ServerConfiguration
VALUES ('speedThreshold', 15);

INSERT INTO ServerConfiguration
VALUES ('defaultMessageRadiusMeters', 100);
<<<<<<< HEAD

=======
>>>>>>> e91d034f340dbc7ef1c505a490bc3f78ca9bdac7
