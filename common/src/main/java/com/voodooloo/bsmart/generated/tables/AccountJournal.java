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
public class AccountJournal extends org.jooq.impl.TableImpl<com.voodooloo.bsmart.generated.tables.records.AccountJournalRecord> {

	private static final long serialVersionUID = 1606970525;

	/**
	 * The reference instance of <code>PUBLIC.ACCOUNT_JOURNAL</code>
	 */
	public static final com.voodooloo.bsmart.generated.tables.AccountJournal ACCOUNT_JOURNAL = new com.voodooloo.bsmart.generated.tables.AccountJournal();

	/**
	 * The class holding records for this type
	 */
	@Override
	public java.lang.Class<com.voodooloo.bsmart.generated.tables.records.AccountJournalRecord> getRecordType() {
		return com.voodooloo.bsmart.generated.tables.records.AccountJournalRecord.class;
	}

	/**
	 * The column <code>PUBLIC.ACCOUNT_JOURNAL.ID</code>.
	 */
	public final org.jooq.TableField<com.voodooloo.bsmart.generated.tables.records.AccountJournalRecord, java.lang.Integer> ID = createField("ID", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaulted(true), this, "");

	/**
	 * The column <code>PUBLIC.ACCOUNT_JOURNAL.ACCOUNT_ID</code>.
	 */
	public final org.jooq.TableField<com.voodooloo.bsmart.generated.tables.records.AccountJournalRecord, java.lang.Integer> ACCOUNT_ID = createField("ACCOUNT_ID", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

	/**
	 * The column <code>PUBLIC.ACCOUNT_JOURNAL.INVESTMENT_ID</code>.
	 */
	public final org.jooq.TableField<com.voodooloo.bsmart.generated.tables.records.AccountJournalRecord, java.lang.Integer> INVESTMENT_ID = createField("INVESTMENT_ID", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

	/**
	 * The column <code>PUBLIC.ACCOUNT_JOURNAL.TRADE_DATE</code>.
	 */
	public final org.jooq.TableField<com.voodooloo.bsmart.generated.tables.records.AccountJournalRecord, java.sql.Timestamp> TRADE_DATE = createField("TRADE_DATE", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false), this, "");

	/**
	 * The column <code>PUBLIC.ACCOUNT_JOURNAL.REASON</code>.
	 */
	public final org.jooq.TableField<com.voodooloo.bsmart.generated.tables.records.AccountJournalRecord, java.lang.String> REASON = createField("REASON", org.jooq.impl.SQLDataType.VARCHAR.length(200).nullable(false), this, "");

	/**
	 * The column <code>PUBLIC.ACCOUNT_JOURNAL.QUANTITY</code>.
	 */
	public final org.jooq.TableField<com.voodooloo.bsmart.generated.tables.records.AccountJournalRecord, java.math.BigDecimal> QUANTITY = createField("QUANTITY", org.jooq.impl.SQLDataType.DECIMAL.precision(13, 4).nullable(false), this, "");

	/**
	 * The column <code>PUBLIC.ACCOUNT_JOURNAL.PRICE</code>.
	 */
	public final org.jooq.TableField<com.voodooloo.bsmart.generated.tables.records.AccountJournalRecord, java.math.BigDecimal> PRICE = createField("PRICE", org.jooq.impl.SQLDataType.DECIMAL.precision(9, 4).nullable(false), this, "");

	/**
	 * Create a <code>PUBLIC.ACCOUNT_JOURNAL</code> table reference
	 */
	public AccountJournal() {
		this("ACCOUNT_JOURNAL", null);
	}

	/**
	 * Create an aliased <code>PUBLIC.ACCOUNT_JOURNAL</code> table reference
	 */
	public AccountJournal(java.lang.String alias) {
		this(alias, com.voodooloo.bsmart.generated.tables.AccountJournal.ACCOUNT_JOURNAL);
	}

	private AccountJournal(java.lang.String alias, org.jooq.Table<com.voodooloo.bsmart.generated.tables.records.AccountJournalRecord> aliased) {
		this(alias, aliased, null);
	}

	private AccountJournal(java.lang.String alias, org.jooq.Table<com.voodooloo.bsmart.generated.tables.records.AccountJournalRecord> aliased, org.jooq.Field<?>[] parameters) {
		super(alias, com.voodooloo.bsmart.generated.Public.PUBLIC, aliased, parameters, "");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Identity<com.voodooloo.bsmart.generated.tables.records.AccountJournalRecord, java.lang.Integer> getIdentity() {
		return com.voodooloo.bsmart.generated.Keys.IDENTITY_ACCOUNT_JOURNAL;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.UniqueKey<com.voodooloo.bsmart.generated.tables.records.AccountJournalRecord> getPrimaryKey() {
		return com.voodooloo.bsmart.generated.Keys.CONSTRAINT_A;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.util.List<org.jooq.UniqueKey<com.voodooloo.bsmart.generated.tables.records.AccountJournalRecord>> getKeys() {
		return java.util.Arrays.<org.jooq.UniqueKey<com.voodooloo.bsmart.generated.tables.records.AccountJournalRecord>>asList(com.voodooloo.bsmart.generated.Keys.CONSTRAINT_A);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.util.List<org.jooq.ForeignKey<com.voodooloo.bsmart.generated.tables.records.AccountJournalRecord, ?>> getReferences() {
		return java.util.Arrays.<org.jooq.ForeignKey<com.voodooloo.bsmart.generated.tables.records.AccountJournalRecord, ?>>asList(com.voodooloo.bsmart.generated.Keys.CONSTRAINT_A1, com.voodooloo.bsmart.generated.Keys.CONSTRAINT_A16);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public com.voodooloo.bsmart.generated.tables.AccountJournal as(java.lang.String alias) {
		return new com.voodooloo.bsmart.generated.tables.AccountJournal(alias, this);
	}

	/**
	 * Rename this table
	 */
	public com.voodooloo.bsmart.generated.tables.AccountJournal rename(java.lang.String name) {
		return new com.voodooloo.bsmart.generated.tables.AccountJournal(name, null);
	}
}
