CREATE TABLE ServerParameters 
(
paramName varchar(64), 
description varchar(256), 
value varchar(32) NOT NULL, 
PRIMARY KEY (paramName)
);


INSERT INTO ServerParameters 
VALUES ('minMessageSizeBytes', 'The minimum size of uploaded audio files that should be accepted, in bytes.', 2000);

INSERT INTO ServerParameters 
VALUES ('maxMessageSizeBytes', 'The maximum size of uploaded audio files that should be accepted, in bytes.', 50000);

INSERT INTO ServerParameters 
VALUES ('messageLifeDays', 'The time to live of regular messages. If a message is older than this amount, in days, it should be deleted.', 7);

INSERT INTO ServerParameters 
VALUES ('advertiserMessageLifeDays', 'The time to live of advertiser messages.', 30);

INSERT INTO ServerParameters 
VALUES ('minEmailLength', 'The minimum character length of email addresses when creating user accounts.', 15);

INSERT INTO ServerParameters 
VALUES ('maxEmailLength', 'The maximum character length of email addresses when creating user accounts.', 50);

INSERT INTO ServerParameters 
VALUES ('minPasswordLength', 'The minimum character length of password when creating user accounts.', 6);

INSERT INTO ServerParameters 
VALUES ('maxPasswordLength', 'The maximum character length of password when creating user accounts.', 20);

INSERT INTO ServerParameters 
VALUES ('speedThreshold', 'The speed threshold to compare against a user requesting messages.', 15);

INSERT INTO ServerParameters 
VALUES ('defaultMessageRadiusMeters', 'The default radius, in meters, in which to check if there are any messages.', 83);

INSERT INTO ServerParameters 
VALUES ('minMessages', 'The minimum amount of messages fetched to the user.', 10);

INSERT INTO ServerParameters 
VALUES ('maxMessages', 'The maximum amount of messages fetched to the user.', 50);


