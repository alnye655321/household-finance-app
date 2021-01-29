TRUNCATE TABLE accounts_linked_users, user_account, users_linked_accounts,  users RESTART IDENTITY;

TRUNCATE  TABLE accounting_periods RESTART IDENTITY;

DROP TABLE accounts_linked_users, user_account, users_linked_accounts,  users;

insert into users (name, hashed_password, email) values ('betty', 'pw', 'a@aol.com'); --1
insert into users (name, hashed_password, email) values ('sue', 'pw', 'b@aol.com'); --2


INSERT INTO accounting_periods (start_date, end_date) VALUES ('2021-01-01', '2021-01-15');
INSERT INTO accounting_periods (start_date, end_date) VALUES ('2021-01-16', '2021-01-31');

INSERT INTO accounting_periods (start_date, end_date) VALUES ('2021-02-01', '2021-02-15');
INSERT INTO accounting_periods (start_date, end_date) VALUES ('2021-02-16', '2021-02-28');

INSERT INTO accounting_periods (start_date, end_date) VALUES ('2021-03-01', '2021-03-15');
INSERT INTO accounting_periods (start_date, end_date) VALUES ('2021-03-16', '2021-03-31');

INSERT INTO accounting_periods (start_date, end_date) VALUES ('2021-04-01', '2021-04-15');
INSERT INTO accounting_periods (start_date, end_date) VALUES ('2021-04-16', '2021-04-30');

INSERT INTO accounting_periods (start_date, end_date) VALUES ('2021-05-01', '2021-05-15');
INSERT INTO accounting_periods (start_date, end_date) VALUES ('2021-05-16', '2021-05-31');

INSERT INTO accounting_periods (start_date, end_date) VALUES ('2021-06-01', '2021-06-15');
INSERT INTO accounting_periods (start_date, end_date) VALUES ('2021-06-16', '2021-06-30');

INSERT INTO accounting_periods (start_date, end_date) VALUES ('2021-07-01', '2021-07-15');
INSERT INTO accounting_periods (start_date, end_date) VALUES ('2021-07-16', '2021-07-31');

INSERT INTO accounting_periods (start_date, end_date) VALUES ('2021-08-01', '2021-08-15');
INSERT INTO accounting_periods (start_date, end_date) VALUES ('2021-08-16', '2021-08-31');

INSERT INTO accounting_periods (start_date, end_date) VALUES ('2021-09-01', '2021-09-15');
INSERT INTO accounting_periods (start_date, end_date) VALUES ('2021-09-16', '2021-09-30');

INSERT INTO accounting_periods (start_date, end_date) VALUES ('2021-10-01', '2021-10-15');
INSERT INTO accounting_periods (start_date, end_date) VALUES ('2021-10-16', '2021-10-31');

INSERT INTO accounting_periods (start_date, end_date) VALUES ('2021-11-01', '2021-11-15');
INSERT INTO accounting_periods (start_date, end_date) VALUES ('2021-11-16', '2021-11-30');

INSERT INTO accounting_periods (start_date, end_date) VALUES ('2021-12-01', '2021-12-15');
INSERT INTO accounting_periods (start_date, end_date) VALUES ('2021-12-16', '2021-12-31');


INSERT INTO budget_types (type) VALUES ('Car Payment'); --1