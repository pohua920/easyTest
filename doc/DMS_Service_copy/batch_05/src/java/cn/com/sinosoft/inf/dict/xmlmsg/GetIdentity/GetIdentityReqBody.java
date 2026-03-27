package cn.com.sinosoft.inf.dict.xmlmsg.GetIdentity;

public class GetIdentityReqBody {

	private static final long serialVersionUID = 1L;

	public void validate() throws Exception {
	}
	
	private String identifierCode;

	private String identifierName;
	
	private String portCode;
	
	private String portName;
	
	private String countryCode;
	
	private String countryCName;
	
	private String CountryEName;
	
	private String identifierType;

	public String getIdentifierCode() {
		return identifierCode;
	}

	public void setIdentifierCode(String identifierCode) {
		this.identifierCode = identifierCode;
	}

	public String getIdentifierName() {
		return identifierName;
	}

	public void setIdentifierName(String identifierName) {
		this.identifierName = identifierName;
	}

	public String getPortCode() {
		return portCode;
	}

	public void setPortCode(String portCode) {
		this.portCode = portCode;
	}

	public String getPortName() {
		return portName;
	}

	public void setPortName(String portName) {
		this.portName = portName;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public String getCountryCName() {
		return countryCName;
	}

	public void setCountryCName(String countryCName) {
		this.countryCName = countryCName;
	}

	public String getCountryEName() {
		return CountryEName;
	}

	public void setCountryEName(String countryEName) {
		CountryEName = countryEName;
	}

	public String getIdentifierType() {
		return identifierType;
	}

	public void setIdentifierType(String identifierType) {
		this.identifierType = identifierType;
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}



}
