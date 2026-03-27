package com.sinosoft.dmsdriver.domain.getContractManageReqPacket;

public class GetContractManageReqBody {

	private static final long serialVersionUID = 1L;

	public void validate() throws Exception {
	}
	
	private String contractObjectCode;

	private String validStatus;

	public String getContractObjectCode() {
		return contractObjectCode;
	}

	public void setContractObjectCode(String contractObjectCode) {
		this.contractObjectCode = contractObjectCode;
	}

	public String getValidStatus() {
		return validStatus;
	}

	public void setValidStatus(String validStatus) {
		this.validStatus = validStatus;
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}


}
