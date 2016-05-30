INSERT INTO CLIENT (LOGIN, PASSWORD, GENDER)
VALUES ('login', 'password', 'M'),
  ('client', 'qwerty', 'F');

INSERT INTO CATEGORY (NAME)
VALUES ('category0'),
  ('category1');

INSERT INTO RECORD (CLIENT_ID, CATEGORY_ID, TYPE, AMOUNT, DESCRIPTION, CREATION_DATE)
VALUES (0, 0, 'INCOME', 100, 'description0', '2016-01-01'),
  (0, 1, 'INCOME', 200, 'description1', '2016-01-02'),
  (1, 1, 'EXPENDITURE', 100, 'description2', '2016-01-01'),
  (1, 0, 'EXPENDITURE', 200, 'description3', '2016-01-02');

