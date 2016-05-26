INSERT INTO CLIENT (LOGIN, PASSWORD, GENDER)
VALUES ('login', 'password', 'M'),
  ('client', 'qwerty', 'F');

INSERT INTO CATEGORY (NAME)
VALUES ('category0'),
  ('category1');

INSERT INTO INCOME (CLIENT_ID, CATEGORY_ID, PRICE, DESCRIPTION, CREATION_DATE)
VALUES (0, 0, 100, 'description0', '1999-12-31'),
  (1, 1, 100, 'description1', '2000-01-01');
