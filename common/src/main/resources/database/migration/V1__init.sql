CREATE TABLE firm (
    id INTEGER not null auto_increment,
    name VARCHAR(200) not null,
    PRIMARY KEY (id)
);

CREATE TABLE fund (
    id INTEGER not null auto_increment,
    symbol VARCHAR(200) not null,
    name VARCHAR(200) not null,
    price DECIMAL(9, 4) not null,
    PRIMARY KEY (id)
);

CREATE TABLE portfolio (
    id INTEGER not null auto_increment,
    name VARCHAR(200) not null,
    PRIMARY KEY (id)
);

CREATE TABLE account (
    id INTEGER not null auto_increment,
    firm_id INTEGER not null,
    name VARCHAR(200) not null,
    PRIMARY KEY (id),
    FOREIGN KEY (firm_id) REFERENCES firm(id)
);

CREATE TABLE investment (
    id INTEGER not null auto_increment,
    account_id INTEGER not null,
    fund_id INTEGER not null,
    quantity DECIMAL(13, 4) not null,
    PRIMARY KEY (id),
    FOREIGN KEY (account_id) REFERENCES account(id),
    FOREIGN KEY (fund_id) REFERENCES fund(id)
);

CREATE TABLE portfolio_investment (
    portfolio_id INTEGER not null,
    investment_id INTEGER not null,
    percentage DECIMAL(3, 2) not null,
    PRIMARY KEY (portfolio_id, investment_id),
    FOREIGN KEY (portfolio_id) REFERENCES portfolio(id),
    FOREIGN KEY (investment_id) REFERENCES investment(id),
);
