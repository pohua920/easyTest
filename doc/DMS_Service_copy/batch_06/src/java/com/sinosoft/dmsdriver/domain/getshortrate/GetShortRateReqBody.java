package com.sinosoft.dmsdriver.domain.getshortrate;

import java.util.Date;

import com.sinosoft.dmsdriver.domain.common.SchemaNode;

public class GetShortRateReqBody implements SchemaNode {

	private static final long serialVersionUID = 1L;

	public void validate() throws Exception {
	}

	private String RISKCODE = "";
	private String CLAUSECODE = "";
	private String RATETYPE = "";
	private String RATIONCODE = "";//add by liufei 增加方案代码参数
	private String AREACODE = "";//add by liufei 增加区域代码参数
	private Date STARTDATE = null;//add by liufei 起保日期
	private Date ENDDATE = null;//add by liufei 终保日期
	private int STARTHOUR = 0;//add by liufei 起保小时
	private int ENDHOUR = 24;//add by liufei 终保小时
	private int SHORTTERM = 0;
	private int NEWSHORTTERM =0;
	private int OLDSHORTTERM = 0;

	public String getRISKCODE() {
		return RISKCODE;
	}

	public void setRISKCODE(String rISKCODE) {
		RISKCODE = rISKCODE;
	}

	public String getCLAUSECODE() {
		return CLAUSECODE;
	}

	public void setCLAUSECODE(String cLAUSECODE) {
		CLAUSECODE = cLAUSECODE;
	}

	public String getRATETYPE() {
		return RATETYPE;
	}

	public void setRATETYPE(String rATETYPE) {
		RATETYPE = rATETYPE;
	}

	public int getSHORTTERM() {
		return SHORTTERM;
	}

	public void setSHORTTERM(int sHORTTERM) {
		SHORTTERM = sHORTTERM;
	}

	public int getOLDSHORTTERM() {
		return OLDSHORTTERM;
	}

	public void setOLDSHORTTERM(int oldshortterm) {
		OLDSHORTTERM = oldshortterm;
	}

	public int getNEWSHORTTERM() {
		return NEWSHORTTERM;
	}

	public void setNEWSHORTTERM(int newshortterm) {
		NEWSHORTTERM = newshortterm;
	}

	public String getRATIONCODE() {
		return RATIONCODE;
	}

	public void setRATIONCODE(String rationcode) {
		RATIONCODE = rationcode;
	}

	public String getAREACODE() {
		return AREACODE;
	}

	public void setAREACODE(String areacode) {
		AREACODE = areacode;
	}

	public Date getSTARTDATE() {
		return STARTDATE;
	}

	public void setSTARTDATE(Date startdate) {
		STARTDATE = startdate;
	}

	public Date getENDDATE() {
		return ENDDATE;
	}

	public void setENDDATE(Date enddate) {
		ENDDATE = enddate;
	}

	public int getSTARTHOUR() {
		return STARTHOUR;
	}

	public void setSTARTHOUR(int starthour) {
		STARTHOUR = starthour;
	}

	public int getENDHOUR() {
		return ENDHOUR;
	}

	public void setENDHOUR(int endhour) {
		ENDHOUR = endhour;
	}

}
