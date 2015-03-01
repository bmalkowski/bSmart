insert into firm (name) values ('Vanguard');
insert into firm (name) values ('Fidelity');

insert into fund (symbol, name, price) values ('vbtlx', 'Vanguard Total Bond Market Index Fund Admiral Shares', 10.89);
insert into fund (symbol, name, price) values ('vtsmx', 'Vanguard Total Stock Market Index Fund Investor Shares', 53.17);

insert into portfolio (name) values ('Retirement');

insert into account (firm_id, name) values (1, 'An IRA');
insert into account (firm_id, name) values (1, 'Another IRA');

insert into investment (account_id, fund_id, quantity) values (1, 1, 100);
insert into investment (account_id, fund_id, quantity) values (1, 2, 50);
insert into investment (account_id, fund_id, quantity) values (2, 1, 200);

insert into portfolio_investment (portfolio_id, investment_id, percentage) values (1, 1, 0.5);

insert into journal (account_id, fund_id, delta) values (2, 2, 75);
insert into journal (account_id, fund_id, delta) values (2, 2, 50);
