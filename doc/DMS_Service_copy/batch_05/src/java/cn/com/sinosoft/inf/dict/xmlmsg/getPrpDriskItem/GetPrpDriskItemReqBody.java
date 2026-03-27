package cn.com.sinosoft.inf.dict.xmlmsg.getPrpDriskItem;


public class GetPrpDriskItemReqBody {
	private static final long serialVersionUID = 1L;

	public void validate() throws Exception {
		// TODO Auto-generated method stub
	}
	private String RISKCODE ;

	private String UPPERITEMCODE ;
	
	private String ITEMCODE;
	
	private String CLAUSECODE;
	
	private String EXTRAITEMCODE;
	
	public String getRISKCODE() {
		return RISKCODE;
	}

	public void setRISKCODE(String riskCode) {
		RISKCODE = riskCode;
	}

	public String getUPPERITEMCODE() {
		return UPPERITEMCODE;
	}

	public void setUPPERITEMCODE(String upperItemCode) {
		UPPERITEMCODE = upperItemCode;
	}

	public String getITEMCODE() {
		return ITEMCODE;
	}

	public void setITEMCODE(String itemcode) {
		ITEMCODE = itemcode;
	}

	public String getCLAUSECODE() {
		return CLAUSECODE;
	}

	public void setCLAUSECODE(String clausecode) {
		CLAUSECODE = clausecode;
	}

	public String getEXTRAITEMCODE() {
		return EXTRAITEMCODE;
	}

	public void setEXTRAITEMCODE(String extraitemcode) {
		EXTRAITEMCODE = extraitemcode;
	}
	
	
}
