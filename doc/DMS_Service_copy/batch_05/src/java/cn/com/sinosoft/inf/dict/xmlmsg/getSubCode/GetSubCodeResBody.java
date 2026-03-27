package cn.com.sinosoft.inf.dict.xmlmsg.getSubCode;

public class GetSubCodeResBody {

	private static final long serialVersionUID = 1L;

	public void validate() throws Exception {
	}

	private SubCodeList SUBCODELIST = new SubCodeList();

	public SubCodeList getSUBCODELIST() {
		return SUBCODELIST;
	}

	public void setSUBCODELIST(SubCodeList SUBCODELIST) {
		this.SUBCODELIST = SUBCODELIST;
	}

}
