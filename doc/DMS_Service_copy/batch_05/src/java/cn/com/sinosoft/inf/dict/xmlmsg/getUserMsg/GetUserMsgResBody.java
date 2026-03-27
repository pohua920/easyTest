package cn.com.sinosoft.inf.dict.xmlmsg.getUserMsg;

import cn.com.sinosoft.inf.dict.xmlmsg.common.SchemaNode;

public class GetUserMsgResBody implements SchemaNode{

	private static final long serialVersionUID = 1L;

	public void validate() throws Exception {
	}
	
	private UserMsgResInfo USERMSGRESINFO = new UserMsgResInfo();

	public UserMsgResInfo getUSERMSGRESINFO() {
		return USERMSGRESINFO;
	}

	public void setUSERMSGRESINFO(UserMsgResInfo USERMSGRESINFO) {
		this.USERMSGRESINFO = USERMSGRESINFO;
	}
	
}
