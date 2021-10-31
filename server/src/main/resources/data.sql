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

--run after /seed in API request
INSERT INTO period_budgets (amount, accounting_period_id, user_id) VALUES (4300.00, 1, 1);
INSERT INTO period_budgets (amount, accounting_period_id, user_id) VALUES (4300.00, 2, 1);
INSERT INTO period_budgets (amount, accounting_period_id, user_id) VALUES (4300.00, 3, 1);
INSERT INTO period_budgets (amount, accounting_period_id, user_id) VALUES (4300.00, 4, 1);
INSERT INTO period_budgets (amount, accounting_period_id, user_id) VALUES (4300.00, 5, 1);
INSERT INTO period_budgets (amount, accounting_period_id, user_id) VALUES (4300.00, 6, 1);
INSERT INTO period_budgets (amount, accounting_period_id, user_id) VALUES (4300.00, 7, 1);
INSERT INTO period_budgets (amount, accounting_period_id, user_id) VALUES (4300.00, 8, 1);
INSERT INTO period_budgets (amount, accounting_period_id, user_id) VALUES (4300.00, 9, 1);
INSERT INTO period_budgets (amount, accounting_period_id, user_id) VALUES (4300.00, 10, 1);
INSERT INTO period_budgets (amount, accounting_period_id, user_id) VALUES (4300.00, 11, 1);
INSERT INTO period_budgets (amount, accounting_period_id, user_id) VALUES (4300.00, 12, 1);
INSERT INTO period_budgets (amount, accounting_period_id, user_id) VALUES (4300.00, 13, 1);
INSERT INTO period_budgets (amount, accounting_period_id, user_id) VALUES (4300.00, 14, 1);
INSERT INTO period_budgets (amount, accounting_period_id, user_id) VALUES (4300.00, 15, 1);
INSERT INTO period_budgets (amount, accounting_period_id, user_id) VALUES (4300.00, 16, 1);
INSERT INTO period_budgets (amount, accounting_period_id, user_id) VALUES (4300.00, 17, 1);
INSERT INTO period_budgets (amount, accounting_period_id, user_id) VALUES (4300.00, 18, 1);
INSERT INTO period_budgets (amount, accounting_period_id, user_id) VALUES (4300.00, 19, 1);
INSERT INTO period_budgets (amount, accounting_period_id, user_id) VALUES (4300.00, 20, 1);
INSERT INTO period_budgets (amount, accounting_period_id, user_id) VALUES (4300.00, 21, 1);
INSERT INTO period_budgets (amount, accounting_period_id, user_id) VALUES (4300.00, 22, 1);
INSERT INTO period_budgets (amount, accounting_period_id, user_id) VALUES (4300.00, 23, 1);
INSERT INTO period_budgets (amount, accounting_period_id, user_id) VALUES (4300.00, 24, 1);


--not needed ------------------------------------------------------------------------
--INSERT INTO budget_types (type) VALUES ('Car Payment'); --1


-- insert into account_types (account_type) VALUES ('Checking'); --id: 1
-- insert into account_types (account_type) VALUES ('Savings'); --id: 2

-- INSERT INTO budget_types (type) VALUES ('Car Payment');
-- INSERT INTO budget_types (type) VALUES ('Savings Contribution');

-- INSERT INTO accounts (balance, date_opened, interest_rate, name, account_type_id) VALUES  (3000, NOW(), 0, 'Primary Checking', 1);
-- INSERT INTO accounts (balance, date_opened, interest_rate, name, account_type_id) VALUES  (7000, NOW(), 0.05, 'Primary Savings', 2);

-- INSERT INTO accounts_linked_users (account_account_id, linked_users_user_id) VALUES (1,1);
-- INSERT INTO accounts_linked_users (account_account_id, linked_users_user_id) VALUES (1,2);
-- INSERT INTO accounts_linked_users (account_account_id, linked_users_user_id) VALUES (2,1);
-- INSERT INTO accounts_linked_users (account_account_id, linked_users_user_id) VALUES (2,2);