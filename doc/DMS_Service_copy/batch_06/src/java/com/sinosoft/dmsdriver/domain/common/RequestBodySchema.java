package com.sinosoft.dmsdriver.domain.common;
import java.util.HashMap;
import java.util.Map;
public class RequestBodySchema implements SchemaNode{
	private static final long serialVersionUID = 1L;
	private Map values = new HashMap();
	public void validate() throws Exception {
	}
	public Map getValues() {
		return values;
	}
	public void setValues(Map values) {
		this.values = values;
	}
}
