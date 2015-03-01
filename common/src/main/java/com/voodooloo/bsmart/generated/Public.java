/**
 * This class is generated by jOOQ
 */
package com.voodooloo.bsmart.generated;

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
public class Public extends org.jooq.impl.SchemaImpl {

	private static final long serialVersionUID = 64185972;

	/**
	 * The reference instance of <code>PUBLIC</code>
	 */
	public static final Public PUBLIC = new Public();

	/**
	 * No further instances allowed
	 */
	private Public() {
		super("PUBLIC");
	}

	@Override
	public final java.util.List<org.jooq.Sequence<?>> getSequences() {
		java.util.List result = new java.util.ArrayList();
		result.addAll(getSequences0());
		return result;
	}

	private final java.util.List<org.jooq.Sequence<?>> getSequences0() {
		return java.util.Arrays.<org.jooq.Sequence<?>>asList(
			com.voodooloo.bsmart.generated.Sequences.SYSTEM_SEQUENCE_05789911_C72F_4ACF_B80A_E17F1B69F1CB,
			com.voodooloo.bsmart.generated.Sequences.SYSTEM_SEQUENCE_20F02E57_36C2_408D_ABC0_D03C111868E1,
			com.voodooloo.bsmart.generated.Sequences.SYSTEM_SEQUENCE_7C91E9BC_A5F9_47BB_85E8_E50681EC7E0D,
			com.voodooloo.bsmart.generated.Sequences.SYSTEM_SEQUENCE_BC512FE7_8EF3_4B3F_8885_20D341BF4F3B,
			com.voodooloo.bsmart.generated.Sequences.SYSTEM_SEQUENCE_D4AA0304_E767_4B76_B6E6_6FD1AAF332E2,
			com.voodooloo.bsmart.generated.Sequences.SYSTEM_SEQUENCE_DBE089FF_4557_4ADB_B95B_53E3B0EC2275);
	}

	@Override
	public final java.util.List<org.jooq.Table<?>> getTables() {
		java.util.List result = new java.util.ArrayList();
		result.addAll(getTables0());
		return result;
	}

	private final java.util.List<org.jooq.Table<?>> getTables0() {
		return java.util.Arrays.<org.jooq.Table<?>>asList(
			com.voodooloo.bsmart.generated.tables.SchemaVersion.SCHEMA_VERSION,
			com.voodooloo.bsmart.generated.tables.Firm.FIRM,
			com.voodooloo.bsmart.generated.tables.Fund.FUND,
			com.voodooloo.bsmart.generated.tables.Portfolio.PORTFOLIO,
			com.voodooloo.bsmart.generated.tables.Account.ACCOUNT,
			com.voodooloo.bsmart.generated.tables.Investment.INVESTMENT,
			com.voodooloo.bsmart.generated.tables.PortfolioInvestment.PORTFOLIO_INVESTMENT,
			com.voodooloo.bsmart.generated.tables.Journal.JOURNAL);
	}
}
