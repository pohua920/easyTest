package cn.com.sinosoft.inf.dict.xmlmsg.getPrpDdisaster;

/*
 * 新请求报文头
 */
public class RequestHeadPacket {
	
	private String SYSTEMCODE;//系统代码
	private String REQUEST_TYPE;//请求类型
	
	private String VALIDSTATUS;
	private String RISKCODE;
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

	public String getVALIDSTATUS() {
		return VALIDSTATUS;
	}

	public void setVALIDSTATUS(String vALIDSTATUS) {
		VALIDSTATUS = vALIDSTATUS;
	}

	public String getRISKCODE() {
		return RISKCODE;
	}

	public void setRISKCODE(String rISKCODE) {
		RISKCODE = rISKCODE;
	}

	public int getPAGENO() {
		return PAGENO;
	}

	public void setPAGENO(int pAGENO) {
		PAGENO = pAGENO;
	}

	public int getPAGESIZE() {
		return PAGESIZE;
	}

	public void setPAGESIZE(int pAGESIZE) {
		PAGESIZE = pAGESIZE;
	}

}
