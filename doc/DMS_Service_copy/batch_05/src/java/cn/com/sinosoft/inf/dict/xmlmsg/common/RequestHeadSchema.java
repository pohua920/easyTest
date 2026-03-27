package cn.com.sinosoft.inf.dict.xmlmsg.common;



public class RequestHeadSchema implements SchemaNode{
	private static final long serialVersionUID = 1L;
	private String SYSTEMCODE;//系统代码
	private String REQUEST_TYPE;//请求类型
	
	private String VALIDSTATUS;
	private int PAGENO;
	private int PAGESIZE;
	
	public String getSYSTEMCODE() {
		return SYSTEMCODE;
	}

	public void setSYSTEMCODE(String SYSTEMCODE) {
		this.SYSTEMCODE = SYSTEMCODE;
	}

	public String getREQUEST_TYPE() {
		return REQUEST_TYPE;
	}

	public void setREQUEST_TYPE(String REQUEST_TYPE) {
		this.REQUEST_TYPE = REQUEST_TYPE;
	}

	public void validate() throws Exception {
	}

	public String getVALIDSTATUS() {
		return VALIDSTATUS;
	}

	public void setVALIDSTATUS(String validstatus) {
		VALIDSTATUS = validstatus;
	}

	public int getPAGENO() {
		return PAGENO;
	}

	public void setPAGENO(int pageno) {
		PAGENO = pageno;
	}

	public int getPAGESIZE() {
		return PAGESIZE;
	}

	public void setPAGESIZE(int pagesize) {
		PAGESIZE = pagesize;
	}

}
