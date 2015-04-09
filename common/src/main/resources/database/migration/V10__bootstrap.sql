insert into investment (investment_type_id, symbol, name, price) values (1, 'vbtlx', 'Vanguard Total Bond Market Index Fund Admiral Shares', 10.89);
insert into investment (investment_type_id, symbol, name, price) values (1, 'vtsmx', 'Vanguard Total Stock Market Index Fund Investor Shares', 53.17);

insert into investment_category (investment_id, category_id, percentage) values (1, 2, 1);
insert into investment_category (investment_id, category_id, percentage) values (2, 1, 1);

insert into firm (name) values ('Vanguard');
insert into firm (name) values ('Fidelity');

insert into account (firm_id, name) values (1, 'An IRA');
insert into account (firm_id, name) values (1, 'Another IRA');

insert into account_journal (account_id, investment_id, trade_date, reason, quantity, price) values (1, 1, CURRENT_DATE(), 'buy', 100, 10.15);
insert into account_journal (account_id, investment_id, trade_date, reason, quantity, price) values (1, 2, CURRENT_DATE(), 'buy', 50, 15.12);
insert into account_journal (account_id, investment_id, trade_date, reason, quantity, price) values (2, 2, CURRENT_DATE(), 'buy', 75, 3.34);
insert into account_journal (account_id, investment_id, trade_date, reason, quantity, price) values (2, 2, CURRENT_DATE(), 'buy', 50, 20.15);

insert into portfolio (name) values ('Retirement');
insert into portfolio (name) values ('Emergency');

insert into portfolio_holding (portfolio_id, holding_id, percentage) values (1, 1, 0.5);
insert into portfolio_holding (portfolio_id, holding_id, percentage) values (1, 3, 1);
insert into portfolio_holding (portfolio_id, holding_id, percentage) values (2, 1, 0.5);

