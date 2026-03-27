package cn.com.sinosoft.inf.dict.xmlmsg.getCode;

import cn.com.sinosoft.dms.model.PrpDnewCodeId;

public class PrpDcodeInfo {
	 private PrpDnewCodeId        id;
	    private String            codeCName;
	    private String            codeEName;
	    private String            oldCodeType;
	    private String            oldCodeCode;
	    private String            newCodeCode;
	    private String            validStatus;
	    private String            flag;
		public PrpDnewCodeId getId() {
			return id;
		}
		public void setId(PrpDnewCodeId id) {
			this.id = id;
		}
		public String getCodeCName() {
			return codeCName;
		}
		public void setCodeCName(String codeCName) {
			this.codeCName = codeCName;
		}
		public String getCodeEName() {
			return codeEName;
		}
		public void setCodeEName(String codeEName) {
			this.codeEName = codeEName;
		}
		public String getNewCodeCode() {
			return newCodeCode;
		}
		public void setNewCodeCode(String newCodeCode) {
			this.newCodeCode = newCodeCode;
		}
		public String getValidStatus() {
			return validStatus;
		}
		public void setValidStatus(String validStatus) {
			this.validStatus = validStatus;
		}
		public String getFlag() {
			return flag;
		}
		public void setFlag(String flag) {
			this.flag = flag;
		}
		public String getOldCodeCode() {
			return oldCodeCode;
		}
		public void setOldCodeCode(String oldCodeCode) {
			this.oldCodeCode = oldCodeCode;
		}
		public String getOldCodeType() {
			return oldCodeType;
		}
		public void setOldCodeType(String oldCodeType) {
			this.oldCodeType = oldCodeType;
		}
}
