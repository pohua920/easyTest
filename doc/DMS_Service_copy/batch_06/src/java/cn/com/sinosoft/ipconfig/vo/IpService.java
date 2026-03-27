package cn.com.sinosoft.ipconfig.vo;

public class IpService {

	/** 服务类型 */
	private String serverType;
	/** 协议类型 */
	private String proteclType;
	/** 服务IP */
	private String serverIp;
	/** 服务端口 */
	private String serverPort;
	/** 服务应用名 */
	private String applicationName;
	/** 服务应用用户名 */
	private String appUserName;
	/** 服务应用密码 */
	private String appPassword;
	/** 环境类别代码 */
	private String environmentTypeCode;
	
	private String methods;
	/** 地区编码 */
	private String areaCode;
	
	public String getMethods() {
		return methods;
	}

	public void setMethods(String methods) {
		this.methods = methods;
	}

	public String getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	public String getServerIp() {
		return serverIp;
	}

	public void setServerIp(String serverIp) {
		this.serverIp = serverIp;
	}

	public String getServerPort() {
		return serverPort;
	}

	public void setServerPort(String serverPort) {
		this.serverPort = serverPort;
	}

	public String getApplicationName() {
		return applicationName;
	}

	public void setApplicationName(String applicationName) {
		this.applicationName = applicationName;
	}

	public String getAppUserName() {
		return appUserName;
	}

	public void setAppUserName(String appUserName) {
		this.appUserName = appUserName;
	}

	public String getAppPassword() {
		return appPassword;
	}

	public void setAppPassword(String appPassword) {
		this.appPassword = appPassword;
	}

	public String getServerType() {
		return serverType;
	}

	public void setServerType(String serverType) {
		this.serverType = serverType;
	}

	public String getEnvironmentTypeCode() {
		return environmentTypeCode;
	}

	public void setEnvironmentTypeCode(String environmentTypeCode) {
		this.environmentTypeCode = environmentTypeCode;
	}

	public String getProteclType() {
		return proteclType;
	}

	public void setProteclType(String proteclType) {
		this.proteclType = proteclType;
	}

}
