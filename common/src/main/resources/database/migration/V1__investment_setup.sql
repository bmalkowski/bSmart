CREATE TABLE investment_type (
    id INTEGER not null auto_increment,
    name VARCHAR(200) not null,
    PRIMARY KEY (id)
);

INSERT INTO investment_type (name) VALUES ('Mutual Fund');

CREATE TABLE investment (
    id INTEGER not null auto_increment,
    investment_type_id INTEGER not null,
    symbol VARCHAR(200) not null,
    name VARCHAR(200) not null,
    price DECIMAL(9, 4) not null,
    PRIMARY KEY (id),
    FOREIGN KEY (investment_type_id) REFERENCES investment_type(id)
);

CREATE TABLE mutual_fund (
    id INTEGER not null,
    expense_ratio DECIMAL(3, 2) not null,
    PRIMARY KEY (id),
    FOREIGN KEY (id) REFERENCES investment(id)
);

CREATE TABLE category (
    id INTEGER not null auto_increment,
    name VARCHAR(200) not null,
    PRIMARY KEY (id)
);

INSERT INTO category (name) VALUES ('Unknown');
INSERT INTO category (name) VALUES ('US Stocks');
INSERT INTO category (name) VALUES ('US Bonds');

CREATE TABLE investment_category (
    investment_id INTEGER not null,
    category_id INTEGER not null,
    percentage DECIMAL(3, 2) not null,
    PRIMARY KEY (investment_id, category_id),
    FOREIGN KEY (investment_id) REFERENCES investment(id),
    FOREIGN KEY (category_id) REFERENCES category(id)
);