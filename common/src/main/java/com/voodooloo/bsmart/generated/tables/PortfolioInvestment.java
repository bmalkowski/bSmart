/**
 * This class is generated by jOOQ
 */
package com.voodooloo.bsmart.generated.tables;

/**
 * This class is generated by jOOQ.
 */
@javax.annotation.Generated(
	value = {
		"http://www.jooq.org",
		"jOOQ version:3.5.1"
	},
	comments = "This class is generated by jOOQ"
)
@java.lang.SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class PortfolioInvestment extends org.jooq.impl.TableImpl<com.voodooloo.bsmart.generated.tables.records.PortfolioInvestmentRecord> {

	private static final long serialVersionUID = 65980274;

	/**
	 * The reference instance of <code>PUBLIC.PORTFOLIO_INVESTMENT</code>
	 */
	public static final com.voodooloo.bsmart.generated.tables.PortfolioInvestment PORTFOLIO_INVESTMENT = new com.voodooloo.bsmart.generated.tables.PortfolioInvestment();

	/**
	 * The class holding records for this type
	 */
	@Override
	public java.lang.Class<com.voodooloo.bsmart.generated.tables.records.PortfolioInvestmentRecord> getRecordType() {
		return com.voodooloo.bsmart.generated.tables.records.PortfolioInvestmentRecord.class;
	}

	/**
	 * The column <code>PUBLIC.PORTFOLIO_INVESTMENT.PORTFOLIO_ID</code>.
	 */
	public final org.jooq.TableField<com.voodooloo.bsmart.generated.tables.records.PortfolioInvestmentRecord, java.lang.Integer> PORTFOLIO_ID = createField("PORTFOLIO_ID", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

	/**
	 * The column <code>PUBLIC.PORTFOLIO_INVESTMENT.INVESTMENT_ID</code>.
	 */
	public final org.jooq.TableField<com.voodooloo.bsmart.generated.tables.records.PortfolioInvestmentRecord, java.lang.Integer> INVESTMENT_ID = createField("INVESTMENT_ID", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

	/**
	 * The column <code>PUBLIC.PORTFOLIO_INVESTMENT.PERCENTAGE</code>.
	 */
	public final org.jooq.TableField<com.voodooloo.bsmart.generated.tables.records.PortfolioInvestmentRecord, java.math.BigDecimal> PERCENTAGE = createField("PERCENTAGE", org.jooq.impl.SQLDataType.DECIMAL.precision(3, 2).nullable(false), this, "");

	/**
	 * Create a <code>PUBLIC.PORTFOLIO_INVESTMENT</code> table reference
	 */
	public PortfolioInvestment() {
		this("PORTFOLIO_INVESTMENT", null);
	}

	/**
	 * Create an aliased <code>PUBLIC.PORTFOLIO_INVESTMENT</code> table reference
	 */
	public PortfolioInvestment(java.lang.String alias) {
		this(alias, com.voodooloo.bsmart.generated.tables.PortfolioInvestment.PORTFOLIO_INVESTMENT);
	}

	private PortfolioInvestment(java.lang.String alias, org.jooq.Table<com.voodooloo.bsmart.generated.tables.records.PortfolioInvestmentRecord> aliased) {
		this(alias, aliased, null);
	}

	private PortfolioInvestment(java.lang.String alias, org.jooq.Table<com.voodooloo.bsmart.generated.tables.records.PortfolioInvestmentRecord> aliased, org.jooq.Field<?>[] parameters) {
		super(alias, com.voodooloo.bsmart.generated.Public.PUBLIC, aliased, parameters, "");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.UniqueKey<com.voodooloo.bsmart.generated.tables.records.PortfolioInvestmentRecord> getPrimaryKey() {
		return com.voodooloo.bsmart.generated.Keys.CONSTRAINT_F;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.util.List<org.jooq.UniqueKey<com.voodooloo.bsmart.generated.tables.records.PortfolioInvestmentRecord>> getKeys() {
		return java.util.Arrays.<org.jooq.UniqueKey<com.voodooloo.bsmart.generated.tables.records.PortfolioInvestmentRecord>>asList(com.voodooloo.bsmart.generated.Keys.CONSTRAINT_F);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.util.List<org.jooq.ForeignKey<com.voodooloo.bsmart.generated.tables.records.PortfolioInvestmentRecord, ?>> getReferences() {
		return java.util.Arrays.<org.jooq.ForeignKey<com.voodooloo.bsmart.generated.tables.records.PortfolioInvestmentRecord, ?>>asList(com.voodooloo.bsmart.generated.Keys.CONSTRAINT_F3, com.voodooloo.bsmart.generated.Keys.CONSTRAINT_F30);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public com.voodooloo.bsmart.generated.tables.PortfolioInvestment as(java.lang.String alias) {
		return new com.voodooloo.bsmart.generated.tables.PortfolioInvestment(alias, this);
	}

	/**
	 * Rename this table
	 */
	public com.voodooloo.bsmart.generated.tables.PortfolioInvestment rename(java.lang.String name) {
		return new com.voodooloo.bsmart.generated.tables.PortfolioInvestment(name, null);
	}
}