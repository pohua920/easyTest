package com.sinosoft.claim.test;

import org.springframework.test.AbstractTransactionalDataSourceSpringContextTests;


public abstract class BaseSpringContextTests extends
		AbstractTransactionalDataSourceSpringContextTests {
	protected String[] getConfigLocations() {
		String[] extSpringConfigs = getExtSpringConfigs();

		String[] configs = new String[extSpringConfigs.length + 1];
		configs[0] = "test/dataAccessContext-hibernate-test.xml";
//		configs[1] = "spring/dataAccessContext-hibernate.xml";
		for (int i = 0; i < extSpringConfigs.length; i++) {
			configs[i + 1] = extSpringConfigs[i];
		}

		return configs;
	}

	public abstract String[] getExtSpringConfigs();

	protected void endTransaction() {
		super.transactionManager.commit(this.transactionStatus);
	}

}
