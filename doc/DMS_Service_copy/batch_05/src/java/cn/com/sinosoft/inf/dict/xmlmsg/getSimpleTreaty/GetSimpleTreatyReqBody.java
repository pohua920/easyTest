package cn.com.sinosoft.inf.dict.xmlmsg.getSimpleTreaty;

public class GetSimpleTreatyReqBody {

	private static final long	serialVersionUID	= 1L;

	public void validate() throws Exception {
	}

	private String	classCode = "";
	private String	riskCode = "";
	private String  sectionNo = "";
	private String	startDate = "";
	private String  endDate = "";

	public String getClassCode() {
		return classCode;
	}
	public void setClassCode(String classCode) {
		this.classCode = classCode;
	}
	public String getRiskCode() {
		return riskCode;
	}
	public void setRiskCode(String riskCode) {
		this.riskCode = riskCode;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public static long getSerialVersionUID() {
		return serialVersionUID;
	}
	public String getSectionNo() {
		return sectionNo;
	}
	public void setSectionNo(String sectionNo) {
		this.sectionNo = sectionNo;
	}

	

}
