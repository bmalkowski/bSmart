CREATE TABLE portfolio (
    id INTEGER not null auto_increment,
    name VARCHAR(200) not null,
    PRIMARY KEY (id)
);

CREATE TABLE portfolio_holding (
    portfolio_id INTEGER not null,
    holding_id INTEGER not null,
    percentage DECIMAL(3, 2) not null,
    PRIMARY KEY (portfolio_id, holding_id),
    FOREIGN KEY (portfolio_id) REFERENCES portfolio(id),
    FOREIGN KEY (holding_id) REFERENCES holding(id)
);
