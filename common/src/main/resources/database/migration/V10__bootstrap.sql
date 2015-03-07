insert into investment (investment_type_id, symbol, name, price) values (1, 'vbtlx', 'Vanguard Total Bond Market Index Fund Admiral Shares', 10.89);
insert into investment (investment_type_id, symbol, name, price) values (1, 'vtsmx', 'Vanguard Total Stock Market Index Fund Investor Shares', 53.17);

insert into firm (name) values ('Vanguard');
insert into firm (name) values ('Fidelity');

insert into account (firm_id, name) values (1, 'An IRA');
insert into account (firm_id, name) values (1, 'Another IRA');

insert into journal (account_id, investment_id, delta, trade_date) values (2, 2, 75, CURRENT_DATE());
insert into journal (account_id, investment_id, delta, trade_date) values (2, 2, 50, CURRENT_DATE());

insert into portfolio (name) values ('Retirement');

insert into portfolio_holding (portfolio_id, holding_id, percentage) values (1, 1, 0.5);

