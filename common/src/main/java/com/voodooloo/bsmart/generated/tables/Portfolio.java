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
public class Portfolio extends org.jooq.impl.TableImpl<com.voodooloo.bsmart.generated.tables.records.PortfolioRecord> {

	private static final long serialVersionUID = -1107675734;

	/**
	 * The reference instance of <code>PUBLIC.PORTFOLIO</code>
	 */
	public static final com.voodooloo.bsmart.generated.tables.Portfolio PORTFOLIO = new com.voodooloo.bsmart.generated.tables.Portfolio();

	/**
	 * The class holding records for this type
	 */
	@Override
	public java.lang.Class<com.voodooloo.bsmart.generated.tables.records.PortfolioRecord> getRecordType() {
		return com.voodooloo.bsmart.generated.tables.records.PortfolioRecord.class;
	}

	/**
	 * The column <code>PUBLIC.PORTFOLIO.ID</code>.
	 */
	public final org.jooq.TableField<com.voodooloo.bsmart.generated.tables.records.PortfolioRecord, java.lang.Integer> ID = createField("ID", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaulted(true), this, "");

	/**
	 * The column <code>PUBLIC.PORTFOLIO.NAME</code>.
	 */
	public final org.jooq.TableField<com.voodooloo.bsmart.generated.tables.records.PortfolioRecord, java.lang.String> NAME = createField("NAME", org.jooq.impl.SQLDataType.VARCHAR.length(200).nullable(false), this, "");

	/**
	 * Create a <code>PUBLIC.PORTFOLIO</code> table reference
	 */
	public Portfolio() {
		this("PORTFOLIO", null);
	}

	/**
	 * Create an aliased <code>PUBLIC.PORTFOLIO</code> table reference
	 */
	public Portfolio(java.lang.String alias) {
		this(alias, com.voodooloo.bsmart.generated.tables.Portfolio.PORTFOLIO);
	}

	private Portfolio(java.lang.String alias, org.jooq.Table<com.voodooloo.bsmart.generated.tables.records.PortfolioRecord> aliased) {
		this(alias, aliased, null);
	}

	private Portfolio(java.lang.String alias, org.jooq.Table<com.voodooloo.bsmart.generated.tables.records.PortfolioRecord> aliased, org.jooq.Field<?>[] parameters) {
		super(alias, com.voodooloo.bsmart.generated.Public.PUBLIC, aliased, parameters, "");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Identity<com.voodooloo.bsmart.generated.tables.records.PortfolioRecord, java.lang.Integer> getIdentity() {
		return com.voodooloo.bsmart.generated.Keys.IDENTITY_PORTFOLIO;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.UniqueKey<com.voodooloo.bsmart.generated.tables.records.PortfolioRecord> getPrimaryKey() {
		return com.voodooloo.bsmart.generated.Keys.CONSTRAINT_E8;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.util.List<org.jooq.UniqueKey<com.voodooloo.bsmart.generated.tables.records.PortfolioRecord>> getKeys() {
		return java.util.Arrays.<org.jooq.UniqueKey<com.voodooloo.bsmart.generated.tables.records.PortfolioRecord>>asList(com.voodooloo.bsmart.generated.Keys.CONSTRAINT_E8);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public com.voodooloo.bsmart.generated.tables.Portfolio as(java.lang.String alias) {
		return new com.voodooloo.bsmart.generated.tables.Portfolio(alias, this);
	}

	/**
	 * Rename this table
	 */
	public com.voodooloo.bsmart.generated.tables.Portfolio rename(java.lang.String name) {
		return new com.voodooloo.bsmart.generated.tables.Portfolio(name, null);
	}
}
