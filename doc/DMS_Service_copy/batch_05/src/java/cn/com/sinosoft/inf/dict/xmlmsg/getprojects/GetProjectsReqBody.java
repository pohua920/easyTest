package cn.com.sinosoft.inf.dict.xmlmsg.getprojects;

public class GetProjectsReqBody {

	private static final long	serialVersionUID	= 1L;

	public void validate() throws Exception {
	}

	private String	projectCode;
	private String	comCode;

	public String getProjectCode() {
		return projectCode;
	}

	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}

	public String getComCode() {
		return comCode;
	}

	public void setComCode(String comCode) {
		this.comCode = comCode;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
