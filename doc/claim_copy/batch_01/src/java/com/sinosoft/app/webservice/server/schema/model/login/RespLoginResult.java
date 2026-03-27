package com.sinosoft.app.webservice.server.schema.model.login;

	import java.util.List;

	import javax.xml.bind.annotation.XmlAccessType;
	import javax.xml.bind.annotation.XmlAccessorType;
	import javax.xml.bind.annotation.XmlElement;
	import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import com.sinosoft.app.webservice.server.schema.model.common.Company;

	@XmlRootElement(name="respLoginResult")
	@XmlAccessorType(XmlAccessType.FIELD)
	public class RespLoginResult {
		private String returnCode;
		private String returnMsg;
		private String userName;
		
		@XmlElementWrapper(name="comList")
		@XmlElement(name="company")
		private List<Company> comList;

		public String getReturnCode() {
			return returnCode;
		}

		public void setReturnCode(String returnCode) {
			this.returnCode = returnCode;
		}

		public String getReturnMsg() {
			return returnMsg;
		}

		public void setReturnMsg(String returnMsg) {
			this.returnMsg = returnMsg;
		}

		public String getUserName() {
			return userName;
		}

		public void setUserName(String userName) {
			this.userName = userName;
		}

		public List<Company> getComList() {
			return comList;
		}

		public void setComList(List<Company> comList) {
			this.comList = comList;
		}
		
	}