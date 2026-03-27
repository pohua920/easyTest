package cn.com.sinosoft.inf.dict.xmlmsg.getresource;

public class GetResourceReqBody {

	private static final long	serialVersionUID	= 1L;

	public void validate() throws Exception {
	}

	private String	resourceCodeOrName;
	private String	comCode;
	private String	projectCode;
	private String	agentCode;

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

	public String getAgentCode() {
		return agentCode;
	}

	public void setAgentCode(String agentCode) {
		this.agentCode = agentCode;
	}

	public String getResourceCodeOrName() {
		return resourceCodeOrName;
	}

	public void setResourceCodeOrName(String resourceCodeOrName) {
		this.resourceCodeOrName = resourceCodeOrName;
	}

}
