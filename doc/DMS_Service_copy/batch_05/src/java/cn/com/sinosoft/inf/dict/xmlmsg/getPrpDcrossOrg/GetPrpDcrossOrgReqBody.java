package cn.com.sinosoft.inf.dict.xmlmsg.getPrpDcrossOrg;

public class GetPrpDcrossOrgReqBody {

	private static final long serialVersionUID = 1L;

	public void validate() throws Exception {
	}
	
	private String orgcod;
	private String comp_cod;
	private String org_lvl;

	public String getOrgcod() {
		return orgcod;
	}
	public void setOrgcod(String orgcod) {
		this.orgcod = orgcod;
	}
	public String getComp_cod() {
		return comp_cod;
	}
	public void setComp_cod(String comp_cod) {
		this.comp_cod = comp_cod;
	}
	public String getOrg_lvl() {
		return org_lvl;
	}
	public void setOrg_lvl(String org_lvl) {
		this.org_lvl = org_lvl;
	}
	public static long getSerialVersionUID() {
		return serialVersionUID;
	}
	
}
