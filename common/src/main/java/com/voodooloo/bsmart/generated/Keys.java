/**
 * This class is generated by jOOQ
 */
package com.voodooloo.bsmart.generated;

/**
 * A class modelling foreign key relationships between tables of the <code>PUBLIC</code> 
 * schema
 */
@javax.annotation.Generated(
	value = {
		"http://www.jooq.org",
		"jOOQ version:3.5.1"
	},
	comments = "This class is generated by jOOQ"
)
@java.lang.SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Keys {

	// -------------------------------------------------------------------------
	// IDENTITY definitions
	// -------------------------------------------------------------------------

	public static final org.jooq.Identity<com.voodooloo.bsmart.generated.tables.records.InvestmentTypeRecord, java.lang.Integer> IDENTITY_INVESTMENT_TYPE = Identities0.IDENTITY_INVESTMENT_TYPE;
	public static final org.jooq.Identity<com.voodooloo.bsmart.generated.tables.records.InvestmentRecord, java.lang.Integer> IDENTITY_INVESTMENT = Identities0.IDENTITY_INVESTMENT;
	public static final org.jooq.Identity<com.voodooloo.bsmart.generated.tables.records.CategoryRecord, java.lang.Integer> IDENTITY_CATEGORY = Identities0.IDENTITY_CATEGORY;
	public static final org.jooq.Identity<com.voodooloo.bsmart.generated.tables.records.FirmRecord, java.lang.Integer> IDENTITY_FIRM = Identities0.IDENTITY_FIRM;
	public static final org.jooq.Identity<com.voodooloo.bsmart.generated.tables.records.AccountRecord, java.lang.Integer> IDENTITY_ACCOUNT = Identities0.IDENTITY_ACCOUNT;
	public static final org.jooq.Identity<com.voodooloo.bsmart.generated.tables.records.HoldingRecord, java.lang.Integer> IDENTITY_HOLDING = Identities0.IDENTITY_HOLDING;
	public static final org.jooq.Identity<com.voodooloo.bsmart.generated.tables.records.AccountJournalRecord, java.lang.Integer> IDENTITY_ACCOUNT_JOURNAL = Identities0.IDENTITY_ACCOUNT_JOURNAL;
	public static final org.jooq.Identity<com.voodooloo.bsmart.generated.tables.records.PortfolioRecord, java.lang.Integer> IDENTITY_PORTFOLIO = Identities0.IDENTITY_PORTFOLIO;

	// -------------------------------------------------------------------------
	// UNIQUE and PRIMARY KEY definitions
	// -------------------------------------------------------------------------

	public static final org.jooq.UniqueKey<com.voodooloo.bsmart.generated.tables.records.SchemaVersionRecord> SCHEMA_VERSION_PK = UniqueKeys0.SCHEMA_VERSION_PK;
	public static final org.jooq.UniqueKey<com.voodooloo.bsmart.generated.tables.records.InvestmentTypeRecord> CONSTRAINT_5 = UniqueKeys0.CONSTRAINT_5;
	public static final org.jooq.UniqueKey<com.voodooloo.bsmart.generated.tables.records.InvestmentRecord> CONSTRAINT_8 = UniqueKeys0.CONSTRAINT_8;
	public static final org.jooq.UniqueKey<com.voodooloo.bsmart.generated.tables.records.MutualFundRecord> CONSTRAINT_F = UniqueKeys0.CONSTRAINT_F;
	public static final org.jooq.UniqueKey<com.voodooloo.bsmart.generated.tables.records.CategoryRecord> CONSTRAINT_3 = UniqueKeys0.CONSTRAINT_3;
	public static final org.jooq.UniqueKey<com.voodooloo.bsmart.generated.tables.records.InvestmentCategoryRecord> CONSTRAINT_3B = UniqueKeys0.CONSTRAINT_3B;
	public static final org.jooq.UniqueKey<com.voodooloo.bsmart.generated.tables.records.FirmRecord> CONSTRAINT_2 = UniqueKeys0.CONSTRAINT_2;
	public static final org.jooq.UniqueKey<com.voodooloo.bsmart.generated.tables.records.AccountRecord> CONSTRAINT_E = UniqueKeys0.CONSTRAINT_E;
	public static final org.jooq.UniqueKey<com.voodooloo.bsmart.generated.tables.records.HoldingRecord> CONSTRAINT_6 = UniqueKeys0.CONSTRAINT_6;
	public static final org.jooq.UniqueKey<com.voodooloo.bsmart.generated.tables.records.HoldingRecord> CONSTRAINT_6BDE = UniqueKeys0.CONSTRAINT_6BDE;
	public static final org.jooq.UniqueKey<com.voodooloo.bsmart.generated.tables.records.AccountJournalRecord> CONSTRAINT_A = UniqueKeys0.CONSTRAINT_A;
	public static final org.jooq.UniqueKey<com.voodooloo.bsmart.generated.tables.records.PortfolioRecord> CONSTRAINT_E8 = UniqueKeys0.CONSTRAINT_E8;
	public static final org.jooq.UniqueKey<com.voodooloo.bsmart.generated.tables.records.PortfolioHoldingRecord> CONSTRAINT_E42 = UniqueKeys0.CONSTRAINT_E42;

	// -------------------------------------------------------------------------
	// FOREIGN KEY definitions
	// -------------------------------------------------------------------------

	public static final org.jooq.ForeignKey<com.voodooloo.bsmart.generated.tables.records.InvestmentRecord, com.voodooloo.bsmart.generated.tables.records.InvestmentTypeRecord> CONSTRAINT_8F = ForeignKeys0.CONSTRAINT_8F;
	public static final org.jooq.ForeignKey<com.voodooloo.bsmart.generated.tables.records.MutualFundRecord, com.voodooloo.bsmart.generated.tables.records.InvestmentRecord> CONSTRAINT_FE = ForeignKeys0.CONSTRAINT_FE;
	public static final org.jooq.ForeignKey<com.voodooloo.bsmart.generated.tables.records.InvestmentCategoryRecord, com.voodooloo.bsmart.generated.tables.records.InvestmentRecord> CONSTRAINT_3B7 = ForeignKeys0.CONSTRAINT_3B7;
	public static final org.jooq.ForeignKey<com.voodooloo.bsmart.generated.tables.records.InvestmentCategoryRecord, com.voodooloo.bsmart.generated.tables.records.CategoryRecord> CONSTRAINT_3B73 = ForeignKeys0.CONSTRAINT_3B73;
	public static final org.jooq.ForeignKey<com.voodooloo.bsmart.generated.tables.records.AccountRecord, com.voodooloo.bsmart.generated.tables.records.FirmRecord> CONSTRAINT_E4 = ForeignKeys0.CONSTRAINT_E4;
	public static final org.jooq.ForeignKey<com.voodooloo.bsmart.generated.tables.records.HoldingRecord, com.voodooloo.bsmart.generated.tables.records.AccountRecord> CONSTRAINT_6B = ForeignKeys0.CONSTRAINT_6B;
	public static final org.jooq.ForeignKey<com.voodooloo.bsmart.generated.tables.records.HoldingRecord, com.voodooloo.bsmart.generated.tables.records.InvestmentRecord> CONSTRAINT_6BD = ForeignKeys0.CONSTRAINT_6BD;
	public static final org.jooq.ForeignKey<com.voodooloo.bsmart.generated.tables.records.AccountJournalRecord, com.voodooloo.bsmart.generated.tables.records.AccountRecord> CONSTRAINT_A1 = ForeignKeys0.CONSTRAINT_A1;
	public static final org.jooq.ForeignKey<com.voodooloo.bsmart.generated.tables.records.AccountJournalRecord, com.voodooloo.bsmart.generated.tables.records.InvestmentRecord> CONSTRAINT_A16 = ForeignKeys0.CONSTRAINT_A16;
	public static final org.jooq.ForeignKey<com.voodooloo.bsmart.generated.tables.records.PortfolioHoldingRecord, com.voodooloo.bsmart.generated.tables.records.PortfolioRecord> CONSTRAINT_E42F = ForeignKeys0.CONSTRAINT_E42F;
	public static final org.jooq.ForeignKey<com.voodooloo.bsmart.generated.tables.records.PortfolioHoldingRecord, com.voodooloo.bsmart.generated.tables.records.HoldingRecord> CONSTRAINT_E42F9 = ForeignKeys0.CONSTRAINT_E42F9;

	// -------------------------------------------------------------------------
	// [#1459] distribute members to avoid static initialisers > 64kb
	// -------------------------------------------------------------------------

	private static class Identities0 extends org.jooq.impl.AbstractKeys {
		public static org.jooq.Identity<com.voodooloo.bsmart.generated.tables.records.InvestmentTypeRecord, java.lang.Integer> IDENTITY_INVESTMENT_TYPE = createIdentity(com.voodooloo.bsmart.generated.tables.InvestmentType.INVESTMENT_TYPE, com.voodooloo.bsmart.generated.tables.InvestmentType.INVESTMENT_TYPE.ID);
		public static org.jooq.Identity<com.voodooloo.bsmart.generated.tables.records.InvestmentRecord, java.lang.Integer> IDENTITY_INVESTMENT = createIdentity(com.voodooloo.bsmart.generated.tables.Investment.INVESTMENT, com.voodooloo.bsmart.generated.tables.Investment.INVESTMENT.ID);
		public static org.jooq.Identity<com.voodooloo.bsmart.generated.tables.records.CategoryRecord, java.lang.Integer> IDENTITY_CATEGORY = createIdentity(com.voodooloo.bsmart.generated.tables.Category.CATEGORY, com.voodooloo.bsmart.generated.tables.Category.CATEGORY.ID);
		public static org.jooq.Identity<com.voodooloo.bsmart.generated.tables.records.FirmRecord, java.lang.Integer> IDENTITY_FIRM = createIdentity(com.voodooloo.bsmart.generated.tables.Firm.FIRM, com.voodooloo.bsmart.generated.tables.Firm.FIRM.ID);
		public static org.jooq.Identity<com.voodooloo.bsmart.generated.tables.records.AccountRecord, java.lang.Integer> IDENTITY_ACCOUNT = createIdentity(com.voodooloo.bsmart.generated.tables.Account.ACCOUNT, com.voodooloo.bsmart.generated.tables.Account.ACCOUNT.ID);
		public static org.jooq.Identity<com.voodooloo.bsmart.generated.tables.records.HoldingRecord, java.lang.Integer> IDENTITY_HOLDING = createIdentity(com.voodooloo.bsmart.generated.tables.Holding.HOLDING, com.voodooloo.bsmart.generated.tables.Holding.HOLDING.ID);
		public static org.jooq.Identity<com.voodooloo.bsmart.generated.tables.records.AccountJournalRecord, java.lang.Integer> IDENTITY_ACCOUNT_JOURNAL = createIdentity(com.voodooloo.bsmart.generated.tables.AccountJournal.ACCOUNT_JOURNAL, com.voodooloo.bsmart.generated.tables.AccountJournal.ACCOUNT_JOURNAL.ID);
		public static org.jooq.Identity<com.voodooloo.bsmart.generated.tables.records.PortfolioRecord, java.lang.Integer> IDENTITY_PORTFOLIO = createIdentity(com.voodooloo.bsmart.generated.tables.Portfolio.PORTFOLIO, com.voodooloo.bsmart.generated.tables.Portfolio.PORTFOLIO.ID);
	}

	private static class UniqueKeys0 extends org.jooq.impl.AbstractKeys {
		public static final org.jooq.UniqueKey<com.voodooloo.bsmart.generated.tables.records.SchemaVersionRecord> SCHEMA_VERSION_PK = createUniqueKey(com.voodooloo.bsmart.generated.tables.SchemaVersion.SCHEMA_VERSION, com.voodooloo.bsmart.generated.tables.SchemaVersion.SCHEMA_VERSION.VERSION);
		public static final org.jooq.UniqueKey<com.voodooloo.bsmart.generated.tables.records.InvestmentTypeRecord> CONSTRAINT_5 = createUniqueKey(com.voodooloo.bsmart.generated.tables.InvestmentType.INVESTMENT_TYPE, com.voodooloo.bsmart.generated.tables.InvestmentType.INVESTMENT_TYPE.ID);
		public static final org.jooq.UniqueKey<com.voodooloo.bsmart.generated.tables.records.InvestmentRecord> CONSTRAINT_8 = createUniqueKey(com.voodooloo.bsmart.generated.tables.Investment.INVESTMENT, com.voodooloo.bsmart.generated.tables.Investment.INVESTMENT.ID);
		public static final org.jooq.UniqueKey<com.voodooloo.bsmart.generated.tables.records.MutualFundRecord> CONSTRAINT_F = createUniqueKey(com.voodooloo.bsmart.generated.tables.MutualFund.MUTUAL_FUND, com.voodooloo.bsmart.generated.tables.MutualFund.MUTUAL_FUND.ID);
		public static final org.jooq.UniqueKey<com.voodooloo.bsmart.generated.tables.records.CategoryRecord> CONSTRAINT_3 = createUniqueKey(com.voodooloo.bsmart.generated.tables.Category.CATEGORY, com.voodooloo.bsmart.generated.tables.Category.CATEGORY.ID);
		public static final org.jooq.UniqueKey<com.voodooloo.bsmart.generated.tables.records.InvestmentCategoryRecord> CONSTRAINT_3B = createUniqueKey(com.voodooloo.bsmart.generated.tables.InvestmentCategory.INVESTMENT_CATEGORY, com.voodooloo.bsmart.generated.tables.InvestmentCategory.INVESTMENT_CATEGORY.INVESTMENT_ID, com.voodooloo.bsmart.generated.tables.InvestmentCategory.INVESTMENT_CATEGORY.CATEGORY_ID);
		public static final org.jooq.UniqueKey<com.voodooloo.bsmart.generated.tables.records.FirmRecord> CONSTRAINT_2 = createUniqueKey(com.voodooloo.bsmart.generated.tables.Firm.FIRM, com.voodooloo.bsmart.generated.tables.Firm.FIRM.ID);
		public static final org.jooq.UniqueKey<com.voodooloo.bsmart.generated.tables.records.AccountRecord> CONSTRAINT_E = createUniqueKey(com.voodooloo.bsmart.generated.tables.Account.ACCOUNT, com.voodooloo.bsmart.generated.tables.Account.ACCOUNT.ID);
		public static final org.jooq.UniqueKey<com.voodooloo.bsmart.generated.tables.records.HoldingRecord> CONSTRAINT_6 = createUniqueKey(com.voodooloo.bsmart.generated.tables.Holding.HOLDING, com.voodooloo.bsmart.generated.tables.Holding.HOLDING.ID);
		public static final org.jooq.UniqueKey<com.voodooloo.bsmart.generated.tables.records.HoldingRecord> CONSTRAINT_6BDE = createUniqueKey(com.voodooloo.bsmart.generated.tables.Holding.HOLDING, com.voodooloo.bsmart.generated.tables.Holding.HOLDING.ACCOUNT_ID, com.voodooloo.bsmart.generated.tables.Holding.HOLDING.INVESTMENT_ID);
		public static final org.jooq.UniqueKey<com.voodooloo.bsmart.generated.tables.records.AccountJournalRecord> CONSTRAINT_A = createUniqueKey(com.voodooloo.bsmart.generated.tables.AccountJournal.ACCOUNT_JOURNAL, com.voodooloo.bsmart.generated.tables.AccountJournal.ACCOUNT_JOURNAL.ID);
		public static final org.jooq.UniqueKey<com.voodooloo.bsmart.generated.tables.records.PortfolioRecord> CONSTRAINT_E8 = createUniqueKey(com.voodooloo.bsmart.generated.tables.Portfolio.PORTFOLIO, com.voodooloo.bsmart.generated.tables.Portfolio.PORTFOLIO.ID);
		public static final org.jooq.UniqueKey<com.voodooloo.bsmart.generated.tables.records.PortfolioHoldingRecord> CONSTRAINT_E42 = createUniqueKey(com.voodooloo.bsmart.generated.tables.PortfolioHolding.PORTFOLIO_HOLDING, com.voodooloo.bsmart.generated.tables.PortfolioHolding.PORTFOLIO_HOLDING.PORTFOLIO_ID, com.voodooloo.bsmart.generated.tables.PortfolioHolding.PORTFOLIO_HOLDING.HOLDING_ID);
	}

	private static class ForeignKeys0 extends org.jooq.impl.AbstractKeys {
		public static final org.jooq.ForeignKey<com.voodooloo.bsmart.generated.tables.records.InvestmentRecord, com.voodooloo.bsmart.generated.tables.records.InvestmentTypeRecord> CONSTRAINT_8F = createForeignKey(com.voodooloo.bsmart.generated.Keys.CONSTRAINT_5, com.voodooloo.bsmart.generated.tables.Investment.INVESTMENT, com.voodooloo.bsmart.generated.tables.Investment.INVESTMENT.INVESTMENT_TYPE_ID);
		public static final org.jooq.ForeignKey<com.voodooloo.bsmart.generated.tables.records.MutualFundRecord, com.voodooloo.bsmart.generated.tables.records.InvestmentRecord> CONSTRAINT_FE = createForeignKey(com.voodooloo.bsmart.generated.Keys.CONSTRAINT_8, com.voodooloo.bsmart.generated.tables.MutualFund.MUTUAL_FUND, com.voodooloo.bsmart.generated.tables.MutualFund.MUTUAL_FUND.ID);
		public static final org.jooq.ForeignKey<com.voodooloo.bsmart.generated.tables.records.InvestmentCategoryRecord, com.voodooloo.bsmart.generated.tables.records.InvestmentRecord> CONSTRAINT_3B7 = createForeignKey(com.voodooloo.bsmart.generated.Keys.CONSTRAINT_8, com.voodooloo.bsmart.generated.tables.InvestmentCategory.INVESTMENT_CATEGORY, com.voodooloo.bsmart.generated.tables.InvestmentCategory.INVESTMENT_CATEGORY.INVESTMENT_ID);
		public static final org.jooq.ForeignKey<com.voodooloo.bsmart.generated.tables.records.InvestmentCategoryRecord, com.voodooloo.bsmart.generated.tables.records.CategoryRecord> CONSTRAINT_3B73 = createForeignKey(com.voodooloo.bsmart.generated.Keys.CONSTRAINT_3, com.voodooloo.bsmart.generated.tables.InvestmentCategory.INVESTMENT_CATEGORY, com.voodooloo.bsmart.generated.tables.InvestmentCategory.INVESTMENT_CATEGORY.CATEGORY_ID);
		public static final org.jooq.ForeignKey<com.voodooloo.bsmart.generated.tables.records.AccountRecord, com.voodooloo.bsmart.generated.tables.records.FirmRecord> CONSTRAINT_E4 = createForeignKey(com.voodooloo.bsmart.generated.Keys.CONSTRAINT_2, com.voodooloo.bsmart.generated.tables.Account.ACCOUNT, com.voodooloo.bsmart.generated.tables.Account.ACCOUNT.FIRM_ID);
		public static final org.jooq.ForeignKey<com.voodooloo.bsmart.generated.tables.records.HoldingRecord, com.voodooloo.bsmart.generated.tables.records.AccountRecord> CONSTRAINT_6B = createForeignKey(com.voodooloo.bsmart.generated.Keys.CONSTRAINT_E, com.voodooloo.bsmart.generated.tables.Holding.HOLDING, com.voodooloo.bsmart.generated.tables.Holding.HOLDING.ACCOUNT_ID);
		public static final org.jooq.ForeignKey<com.voodooloo.bsmart.generated.tables.records.HoldingRecord, com.voodooloo.bsmart.generated.tables.records.InvestmentRecord> CONSTRAINT_6BD = createForeignKey(com.voodooloo.bsmart.generated.Keys.CONSTRAINT_8, com.voodooloo.bsmart.generated.tables.Holding.HOLDING, com.voodooloo.bsmart.generated.tables.Holding.HOLDING.INVESTMENT_ID);
		public static final org.jooq.ForeignKey<com.voodooloo.bsmart.generated.tables.records.AccountJournalRecord, com.voodooloo.bsmart.generated.tables.records.AccountRecord> CONSTRAINT_A1 = createForeignKey(com.voodooloo.bsmart.generated.Keys.CONSTRAINT_E, com.voodooloo.bsmart.generated.tables.AccountJournal.ACCOUNT_JOURNAL, com.voodooloo.bsmart.generated.tables.AccountJournal.ACCOUNT_JOURNAL.ACCOUNT_ID);
		public static final org.jooq.ForeignKey<com.voodooloo.bsmart.generated.tables.records.AccountJournalRecord, com.voodooloo.bsmart.generated.tables.records.InvestmentRecord> CONSTRAINT_A16 = createForeignKey(com.voodooloo.bsmart.generated.Keys.CONSTRAINT_8, com.voodooloo.bsmart.generated.tables.AccountJournal.ACCOUNT_JOURNAL, com.voodooloo.bsmart.generated.tables.AccountJournal.ACCOUNT_JOURNAL.INVESTMENT_ID);
		public static final org.jooq.ForeignKey<com.voodooloo.bsmart.generated.tables.records.PortfolioHoldingRecord, com.voodooloo.bsmart.generated.tables.records.PortfolioRecord> CONSTRAINT_E42F = createForeignKey(com.voodooloo.bsmart.generated.Keys.CONSTRAINT_E8, com.voodooloo.bsmart.generated.tables.PortfolioHolding.PORTFOLIO_HOLDING, com.voodooloo.bsmart.generated.tables.PortfolioHolding.PORTFOLIO_HOLDING.PORTFOLIO_ID);
		public static final org.jooq.ForeignKey<com.voodooloo.bsmart.generated.tables.records.PortfolioHoldingRecord, com.voodooloo.bsmart.generated.tables.records.HoldingRecord> CONSTRAINT_E42F9 = createForeignKey(com.voodooloo.bsmart.generated.Keys.CONSTRAINT_6, com.voodooloo.bsmart.generated.tables.PortfolioHolding.PORTFOLIO_HOLDING, com.voodooloo.bsmart.generated.tables.PortfolioHolding.PORTFOLIO_HOLDING.HOLDING_ID);
	}
}
