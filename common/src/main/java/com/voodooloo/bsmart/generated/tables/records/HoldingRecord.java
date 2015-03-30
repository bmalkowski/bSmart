/**
 * This class is generated by jOOQ
 */
package com.voodooloo.bsmart.generated.tables.records;

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
public class HoldingRecord extends org.jooq.impl.UpdatableRecordImpl<com.voodooloo.bsmart.generated.tables.records.HoldingRecord> implements org.jooq.Record4<java.lang.Integer, java.lang.Integer, java.lang.Integer, java.math.BigDecimal> {

	private static final long serialVersionUID = -100335405;

	/**
	 * Setter for <code>PUBLIC.HOLDING.ID</code>.
	 */
	public void setId(java.lang.Integer value) {
		setValue(0, value);
	}

	/**
	 * Getter for <code>PUBLIC.HOLDING.ID</code>.
	 */
	public java.lang.Integer getId() {
		return (java.lang.Integer) getValue(0);
	}

	/**
	 * Setter for <code>PUBLIC.HOLDING.ACCOUNT_ID</code>.
	 */
	public void setAccountId(java.lang.Integer value) {
		setValue(1, value);
	}

	/**
	 * Getter for <code>PUBLIC.HOLDING.ACCOUNT_ID</code>.
	 */
	public java.lang.Integer getAccountId() {
		return (java.lang.Integer) getValue(1);
	}

	/**
	 * Setter for <code>PUBLIC.HOLDING.INVESTMENT_ID</code>.
	 */
	public void setInvestmentId(java.lang.Integer value) {
		setValue(2, value);
	}

	/**
	 * Getter for <code>PUBLIC.HOLDING.INVESTMENT_ID</code>.
	 */
	public java.lang.Integer getInvestmentId() {
		return (java.lang.Integer) getValue(2);
	}

	/**
	 * Setter for <code>PUBLIC.HOLDING.QUANTITY</code>.
	 */
	public void setQuantity(java.math.BigDecimal value) {
		setValue(3, value);
	}

	/**
	 * Getter for <code>PUBLIC.HOLDING.QUANTITY</code>.
	 */
	public java.math.BigDecimal getQuantity() {
		return (java.math.BigDecimal) getValue(3);
	}

	// -------------------------------------------------------------------------
	// Primary key information
	// -------------------------------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Record1<java.lang.Integer> key() {
		return (org.jooq.Record1) super.key();
	}

	// -------------------------------------------------------------------------
	// Record4 type implementation
	// -------------------------------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Row4<java.lang.Integer, java.lang.Integer, java.lang.Integer, java.math.BigDecimal> fieldsRow() {
		return (org.jooq.Row4) super.fieldsRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Row4<java.lang.Integer, java.lang.Integer, java.lang.Integer, java.math.BigDecimal> valuesRow() {
		return (org.jooq.Row4) super.valuesRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.Integer> field1() {
		return com.voodooloo.bsmart.generated.tables.Holding.HOLDING.ID;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.Integer> field2() {
		return com.voodooloo.bsmart.generated.tables.Holding.HOLDING.ACCOUNT_ID;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.Integer> field3() {
		return com.voodooloo.bsmart.generated.tables.Holding.HOLDING.INVESTMENT_ID;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.math.BigDecimal> field4() {
		return com.voodooloo.bsmart.generated.tables.Holding.HOLDING.QUANTITY;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.lang.Integer value1() {
		return getId();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.lang.Integer value2() {
		return getAccountId();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.lang.Integer value3() {
		return getInvestmentId();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.math.BigDecimal value4() {
		return getQuantity();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public HoldingRecord value1(java.lang.Integer value) {
		setId(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public HoldingRecord value2(java.lang.Integer value) {
		setAccountId(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public HoldingRecord value3(java.lang.Integer value) {
		setInvestmentId(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public HoldingRecord value4(java.math.BigDecimal value) {
		setQuantity(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public HoldingRecord values(java.lang.Integer value1, java.lang.Integer value2, java.lang.Integer value3, java.math.BigDecimal value4) {
		return this;
	}

	// -------------------------------------------------------------------------
	// Constructors
	// -------------------------------------------------------------------------

	/**
	 * Create a detached HoldingRecord
	 */
	public HoldingRecord() {
		super(com.voodooloo.bsmart.generated.tables.Holding.HOLDING);
	}

	/**
	 * Create a detached, initialised HoldingRecord
	 */
	public HoldingRecord(java.lang.Integer id, java.lang.Integer accountId, java.lang.Integer investmentId, java.math.BigDecimal quantity) {
		super(com.voodooloo.bsmart.generated.tables.Holding.HOLDING);

		setValue(0, id);
		setValue(1, accountId);
		setValue(2, investmentId);
		setValue(3, quantity);
	}
}