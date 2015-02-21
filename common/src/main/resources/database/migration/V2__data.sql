insert into firm (name) values ('Vanguard');
insert into firm (name) values ('Fidelity');

insert into fund (symbol, name, price) values ('vbtlx', 'Vanguard Total Bond Market Index Fund Admiral Shares', 10.89);

insert into portfolio (name) values ('Retirement');

insert into account (firm_id, name) values (1, 'IRA');

insert into investment (account_id, fund_id, quantity) values (1, 1, 100);

insert into portfolio_investment (portfolio_id, investment_id, percentage) values (1, 1, 0.5);
