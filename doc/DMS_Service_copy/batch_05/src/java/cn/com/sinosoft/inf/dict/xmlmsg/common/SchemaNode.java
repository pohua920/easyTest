package cn.com.sinosoft.inf.dict.xmlmsg.common;

import java.io.Serializable;

public interface SchemaNode extends Serializable {
	public void validate() throws Exception;
}
