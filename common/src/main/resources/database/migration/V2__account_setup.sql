CREATE TABLE firm (
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

CREATE TABLE holding (
    id INTEGER not null auto_increment,
    account_id INTEGER not null,
    investment_id INTEGER not null,
    quantity DECIMAL(13, 4) not null,
    PRIMARY KEY (id),
    FOREIGN KEY (account_id) REFERENCES account(id),
    FOREIGN KEY (investment_id) REFERENCES investment(id),
    UNIQUE (account_id, investment_id)
);

CREATE TABLE account_journal (
    id INTEGER not null auto_increment,
    account_id INTEGER not null,
    investment_id INTEGER not null,
    trade_date DATETIME not null,
    reason VARCHAR(200) not null,
    quantity DECIMAL(13, 4) not null,
    price DECIMAL(9, 4) not null,
    PRIMARY KEY (id),
    FOREIGN KEY (account_id) REFERENCES account(id),
    FOREIGN KEY (investment_id) REFERENCES investment(id)
);

CREATE TRIGGER trigger_account_journal_update
    AFTER INSERT, DELETE, UPDATE
    ON account_journal
    FOR EACH ROW
    CALL "com.voodooloo.bsmart.triggers.AccountJournalUpdate";
