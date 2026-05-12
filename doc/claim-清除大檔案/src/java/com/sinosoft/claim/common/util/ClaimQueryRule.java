package com.sinosoft.claim.common.util;

import ins.framework.common.QueryRule;

public class ClaimQueryRule {

	private QueryRule queryRule;

	public ClaimQueryRule() {
		queryRule = QueryRule.getInstance();
	}

	public void addQueryRuleWithSign(String propertyName, Object value, String sign) {
		String strPropertyName = propertyName.trim();
		String strSign = sign.trim();

		if ("=".equals(strSign)) {
			queryRule.addEqual(strPropertyName, value);
		}
		if (">".equals(strSign)) {
			queryRule.addGreaterThan(strPropertyName, value);
		}
		if ("<".equals(value)) {
			queryRule.addLessThan(strPropertyName, value);
		}
		if ("*".equals(strSign)) {
			queryRule.addIsNotEmpty(strPropertyName);
		}
		if (">=".equals(strSign)) {
			queryRule.addGreaterEqual(strPropertyName, value);
		}
		if ("<=".equals(strSign)) {
			queryRule.addLessEqual(strPropertyName, value);
		}
		if ("=*".equals(strSign)) {
			queryRule.addLike(strPropertyName, value);
		}
		if ("<>".equals(strSign)) {
			queryRule.addNotEqual(strPropertyName, value);
		}

	}

	public QueryRule getQueryRule() {
		return this.queryRule;
	}

}
