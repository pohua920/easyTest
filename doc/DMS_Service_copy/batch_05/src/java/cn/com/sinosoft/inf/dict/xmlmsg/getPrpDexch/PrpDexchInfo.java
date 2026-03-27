package cn.com.sinosoft.inf.dict.xmlmsg.getPrpDexch;

import cn.com.sinosoft.inf.dict.xmlmsg.common.SchemaNode;

public class PrpDexchInfo implements SchemaNode {

	private static final long serialVersionUID = 1L;

	public void validate() throws Exception {
	}

	private String EXCHDATE="";
	private String BASE="";
	private String BASECURRENCY="";
	private String EXCHCURRENCY="";
	private String EXCHRATE="";
	private String BUYPRICE="";
	private String SALEPRICE="";
	private String CASHPRICE="";

	public String getEXCHDATE() {
		return EXCHDATE;
	}

	public void setEXCHDATE(String EXCHDATE) {
		this.EXCHDATE = EXCHDATE;
	}

	public String getBASE() {
		return BASE;
	}

	public void setBASE(String BASE) {
		this.BASE = BASE;
	}

	public String getBASECURRENCY() {
		return BASECURRENCY;
	}

	public void setBASECURRENCY(String BASECURRENCY) {
		this.BASECURRENCY = BASECURRENCY;
	}

	public String getEXCHCURRENCY() {
		return EXCHCURRENCY;
	}

	public void setEXCHCURRENCY(String EXCHCURRENCY) {
		this.EXCHCURRENCY = EXCHCURRENCY;
	}

	public String getEXCHRATE() {
		return EXCHRATE;
	}

	public void setEXCHRATE(String EXCHRATE) {
		this.EXCHRATE = EXCHRATE;
	}

	public String getBUYPRICE() {
		return BUYPRICE;
	}

	public void setBUYPRICE(String BUYPRICE) {
		this.BUYPRICE = BUYPRICE;
	}

	public String getSALEPRICE() {
		return SALEPRICE;
	}

	public void setSALEPRICE(String SALEPRICE) {
		this.SALEPRICE = SALEPRICE;
	}

	public String getCASHPRICE() {
		return CASHPRICE;
	}

	public void setCASHPRICE(String CASHPRICE) {
		this.CASHPRICE = CASHPRICE;
	}

}
