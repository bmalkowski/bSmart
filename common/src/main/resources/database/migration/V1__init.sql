CREATE TABLE portfolios (
    id INTEGER not null auto_increment,
    name VARCHAR(200) not null,
    PRIMARY KEY ( id )
);

CREATE TABLE investments (
    id INTEGER not null auto_increment,
    name VARCHAR(200) not null,
    PRIMARY KEY ( id )
);

CREATE TABLE accounts (
    id INTEGER not null auto_increment,
    name VARCHAR(200) not null,
    PRIMARY KEY ( id )
);

CREATE TABLE firms (
    id INTEGER not null auto_increment,
    name VARCHAR(200) not null,
    PRIMARY KEY ( id )
);
