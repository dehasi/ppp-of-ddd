
CREATE TABLE users (
    id INT NOT NULL PRIMARY KEY
);

CREATE TABLE orders (
    id INT NOT NULL PRIMARY KEY,
    `date` DATE NOT NULL,
    user_id INT NOT NULL,
    net_profit DECIMAL NOT NULL,

    FOREIGN KEY (user_id) REFERENCES users(Id)
);


CREATE TABLE loyalty_settings (
    id INT NOT NULL PRIMARY KEY,
    `month` DATE NOT NULL,
    points_per_dollar INT NOT NULL
);

CREATE TABLE loyalty_accounts (
    id INT NOT NULL PRIMARY KEY,
    user_id INT NOT NULL,
    is_active BIT NOT NULL,
    created_at DATE NOT NULL,

    FOREIGN KEY (user_id) REFERENCES users(Id)
);

CREATE TABLE dealerships (
    id INT NOT NULL PRIMARY KEY,
    name VARCHAR(32) NOT NULL
);


CREATE TABLE  denormalized_loyalty_report_view_cache (
    `month` DATE NOT NULL,
    points_per_dollar INT NOT NULL,
    net_profit DECIMAL NOT NULL,
    signups INT NOT NULL,
    purchases  INT NOT NULL
);
