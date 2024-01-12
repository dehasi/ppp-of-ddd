INSERT INTO users VALUES (1), (2);

INSERT INTO loyalty_accounts
(id, user_id, is_active,   created_at) VALUES
(1,        1,         1, '2023-02-05'),
(2,        2,         0, '2023-02-05');

INSERT INTO loyalty_settings
(id,     `month`, points_per_dollar)  VALUES
(1, '2023-02-01',                 2);

INSERT INTO orders
(id, user_id,       `date`, net_profit)  VALUES
(1,        1, '2023-02-05',      25.25),
(2,        2, '2023-02-10',      44.55);

INSERT INTO dealerships
(id,         name) VALUES
(1, 'dealership1'),
(2, 'dealership2'),
(3, 'dealership3');


INSERT INTO denormalized_loyalty_report_view_cache
(`month`,     points_per_dollar, net_profit, signups, purchases) VALUES
('2023-02-01',                2,       4.55,       2,         4),
('2023-03-01',                4,       42.5,       7,         5),
('2023-04-01',                3,       14.8,       5,         8),
('2023-05-01',                1,       45.0,       3,         3);
