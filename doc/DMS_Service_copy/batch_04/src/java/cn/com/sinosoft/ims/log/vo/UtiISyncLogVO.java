package cn.com.sinosoft.ims.log.vo;

public class UtiISyncLogVO {

	private String userCode;

	private String accCode;

	private String synSourceSvr;

	private String synDestSvr;

	private String synOccrTime;

	private String errorMsg;
	
	private String userName;

	public String getUserCode() {
		return userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	public String getAccCode() {
		return accCode;
	}

	public void setAccCode(String accCode) {
		this.accCode = accCode;
	}

	public String getSynSourceSvr() {
		return synSourceSvr;
	}

	public void setSynSourceSvr(String synSourceSvr) {
		this.synSourceSvr = synSourceSvr;
	}

	public String getSynDestSvr() {
		return synDestSvr;
	}

	public void setSynDestSvr(String synDestSvr) {
		this.synDestSvr = synDestSvr;
	}

	public String getSynOccrTime() {
		return synOccrTime;
	}

	public void setSynOccrTime(String synOccrTime) {
		this.synOccrTime = synOccrTime;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	

}
