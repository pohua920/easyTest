package cn.com.sinosoft.inf.PMS.resDomains.prpDshortRateListResPacket;

import cn.com.sinosoft.inf.dict.xmlmsg.common.SchemaNode;


public class PrpDshortRateResInfo implements SchemaNode{
	private static final long serialVersionUID = 1L;
	private String SHORTRATEID="";
	private String SERIALNO="";
	private String SHORTRATENAME="";
	private String RATETYPE="";
	private String LOWEROPERATOR="";
	private String LOWER="";
	private String UPPEROPERATOR="";
	private String UPPER="";
	private String SHORTRATENUMERATOR="";
	private String SHORTRATEDENOMINATOR="";
	private String CREATORCODE="";
	private String CREATETIME="";
	private String UPDATERCODE="";
	private String UPDATETIME="";
	private String VALIDDATE="";
	private String INVALIDDATE="";
	private String VALIDIND="";
	private String FLAG="";
	private String REMARK="";
	public void validate() throws Exception {
		// TODO Auto-generated method stub
	}
	public String getSHORTRATEID() {
		return SHORTRATEID;
	}
	public void setSHORTRATEID(String shortrateid) {
		SHORTRATEID = shortrateid;
	}
	public String getSERIALNO() {
		return SERIALNO;
	}
	public void setSERIALNO(String serialno) {
		SERIALNO = serialno;
	}
	public String getSHORTRATENAME() {
		return SHORTRATENAME;
	}
	public void setSHORTRATENAME(String shortratename) {
		SHORTRATENAME = shortratename;
	}
	public String getRATETYPE() {
		return RATETYPE;
	}
	public void setRATETYPE(String ratetype) {
		RATETYPE = ratetype;
	}
	public String getLOWEROPERATOR() {
		return LOWEROPERATOR;
	}
	public void setLOWEROPERATOR(String loweroperator) {
		LOWEROPERATOR = loweroperator;
	}
	public String getLOWER() {
		return LOWER;
	}
	public void setLOWER(String lower) {
		LOWER = lower;
	}
	public String getUPPEROPERATOR() {
		return UPPEROPERATOR;
	}
	public void setUPPEROPERATOR(String upperoperator) {
		UPPEROPERATOR = upperoperator;
	}
	public String getUPPER() {
		return UPPER;
	}
	public void setUPPER(String upper) {
		UPPER = upper;
	}
	public String getSHORTRATENUMERATOR() {
		return SHORTRATENUMERATOR;
	}
	public void setSHORTRATENUMERATOR(String shortratenumerator) {
		SHORTRATENUMERATOR = shortratenumerator;
	}
	public String getSHORTRATEDENOMINATOR() {
		return SHORTRATEDENOMINATOR;
	}
	public void setSHORTRATEDENOMINATOR(String shortratedenominator) {
		SHORTRATEDENOMINATOR = shortratedenominator;
	}
	public String getCREATORCODE() {
		return CREATORCODE;
	}
	public void setCREATORCODE(String creatorcode) {
		CREATORCODE = creatorcode;
	}
	public String getCREATETIME() {
		return CREATETIME;
	}
	public void setCREATETIME(String createtime) {
		CREATETIME = createtime;
	}
	public String getUPDATERCODE() {
		return UPDATERCODE;
	}
	public void setUPDATERCODE(String updatercode) {
		UPDATERCODE = updatercode;
	}
	public String getUPDATETIME() {
		return UPDATETIME;
	}
	public void setUPDATETIME(String updatetime) {
		UPDATETIME = updatetime;
	}
	public String getVALIDDATE() {
		return VALIDDATE;
	}
	public void setVALIDDATE(String validdate) {
		VALIDDATE = validdate;
	}
	public String getINVALIDDATE() {
		return INVALIDDATE;
	}
	public void setINVALIDDATE(String invaliddate) {
		INVALIDDATE = invaliddate;
	}
	public String getVALIDIND() {
		return VALIDIND;
	}
	public void setVALIDIND(String validind) {
		VALIDIND = validind;
	}
	public String getFLAG() {
		return FLAG;
	}
	public void setFLAG(String flag) {
		FLAG = flag;
	}
	public String getREMARK() {
		return REMARK;
	}
	public void setREMARK(String remark) {
		REMARK = remark;
	}
	
}
