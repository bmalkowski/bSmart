CREATE TABLE investment_type (
    id INTEGER not null auto_increment,
    name VARCHAR(200) not null,
    PRIMARY KEY (id)
);

INSERT INTO investment_type (name) VALUES ('Fund');

CREATE TABLE investment (
    id INTEGER not null auto_increment,
    investment_type_id INTEGER not null,
    symbol VARCHAR(200) not null,
    name VARCHAR(200) not null,
    price DECIMAL(9, 4) not null,
    PRIMARY KEY (id),
    FOREIGN KEY (investment_type_id) REFERENCES investment_type(id)
);

CREATE TABLE fund (
    id INTEGER not null,
    expense_ratio DECIMAL(3, 2) not null,
    PRIMARY KEY (id),
    FOREIGN KEY (id) REFERENCES investment(id)
);
