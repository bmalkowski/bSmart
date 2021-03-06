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
public class InvestmentType extends org.jooq.impl.TableImpl<com.voodooloo.bsmart.generated.tables.records.InvestmentTypeRecord> {

	private static final long serialVersionUID = 1348166547;

	/**
	 * The reference instance of <code>PUBLIC.INVESTMENT_TYPE</code>
	 */
	public static final com.voodooloo.bsmart.generated.tables.InvestmentType INVESTMENT_TYPE = new com.voodooloo.bsmart.generated.tables.InvestmentType();

	/**
	 * The class holding records for this type
	 */
	@Override
	public java.lang.Class<com.voodooloo.bsmart.generated.tables.records.InvestmentTypeRecord> getRecordType() {
		return com.voodooloo.bsmart.generated.tables.records.InvestmentTypeRecord.class;
	}

	/**
	 * The column <code>PUBLIC.INVESTMENT_TYPE.ID</code>.
	 */
	public final org.jooq.TableField<com.voodooloo.bsmart.generated.tables.records.InvestmentTypeRecord, java.lang.Integer> ID = createField("ID", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaulted(true), this, "");

	/**
	 * The column <code>PUBLIC.INVESTMENT_TYPE.NAME</code>.
	 */
	public final org.jooq.TableField<com.voodooloo.bsmart.generated.tables.records.InvestmentTypeRecord, java.lang.String> NAME = createField("NAME", org.jooq.impl.SQLDataType.VARCHAR.length(200).nullable(false), this, "");

	/**
	 * Create a <code>PUBLIC.INVESTMENT_TYPE</code> table reference
	 */
	public InvestmentType() {
		this("INVESTMENT_TYPE", null);
	}

	/**
	 * Create an aliased <code>PUBLIC.INVESTMENT_TYPE</code> table reference
	 */
	public InvestmentType(java.lang.String alias) {
		this(alias, com.voodooloo.bsmart.generated.tables.InvestmentType.INVESTMENT_TYPE);
	}

	private InvestmentType(java.lang.String alias, org.jooq.Table<com.voodooloo.bsmart.generated.tables.records.InvestmentTypeRecord> aliased) {
		this(alias, aliased, null);
	}

	private InvestmentType(java.lang.String alias, org.jooq.Table<com.voodooloo.bsmart.generated.tables.records.InvestmentTypeRecord> aliased, org.jooq.Field<?>[] parameters) {
		super(alias, com.voodooloo.bsmart.generated.Public.PUBLIC, aliased, parameters, "");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Identity<com.voodooloo.bsmart.generated.tables.records.InvestmentTypeRecord, java.lang.Integer> getIdentity() {
		return com.voodooloo.bsmart.generated.Keys.IDENTITY_INVESTMENT_TYPE;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.UniqueKey<com.voodooloo.bsmart.generated.tables.records.InvestmentTypeRecord> getPrimaryKey() {
		return com.voodooloo.bsmart.generated.Keys.CONSTRAINT_5;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.util.List<org.jooq.UniqueKey<com.voodooloo.bsmart.generated.tables.records.InvestmentTypeRecord>> getKeys() {
		return java.util.Arrays.<org.jooq.UniqueKey<com.voodooloo.bsmart.generated.tables.records.InvestmentTypeRecord>>asList(com.voodooloo.bsmart.generated.Keys.CONSTRAINT_5);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public com.voodooloo.bsmart.generated.tables.InvestmentType as(java.lang.String alias) {
		return new com.voodooloo.bsmart.generated.tables.InvestmentType(alias, this);
	}

	/**
	 * Rename this table
	 */
	public com.voodooloo.bsmart.generated.tables.InvestmentType rename(java.lang.String name) {
		return new com.voodooloo.bsmart.generated.tables.InvestmentType(name, null);
	}
}
