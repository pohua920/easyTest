package cn.com.sinosoft.inf.dict.xmlmsg.getCompany;

import com.sinosoft.dmsdriver.domain.common.SchemaNode;
import com.sinosoft.dmsdriver.service.common.DictPage;

public class GetCompanyReqBody implements SchemaNode {
	private static final long serialVersionUID = 1L;
	private String condition = "";
	private DictPage dictPage = new DictPage();
	private String comCodeOrName = "";
	private String upperComCode = "";
	private String flag = "";
	private String validStatus;

	public String getCondition() {
		return condition;
	}

	public void setCondition(String condition) {
		this.condition = condition;
	}

	public DictPage getDictPage() {
		return dictPage;
	}

	public void setDictPage(DictPage dictPage) {
		this.dictPage = dictPage;
	}

	public void validate() throws Exception {
	}

	public String getComCodeOrName() {
		return comCodeOrName;
	}

	public void setComCodeOrName(String comCodeOrName) {
		this.comCodeOrName = comCodeOrName;
	}

	public String getUpperComCode() {
		return upperComCode;
	}

	public void setUpperComCode(String upperComCode) {
		this.upperComCode = upperComCode;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String getValidStatus() {
		return validStatus;
	}

	public void setValidStatus(String validStatus) {
		this.validStatus = validStatus;
	}
}
