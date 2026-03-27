package cn.com.sinosoft.inf.dict.xmlmsg.productSYN;

import java.util.ArrayList;
import java.util.List;

import cn.com.sinosoft.dms.model.PrpDset;
import cn.com.sinosoft.dms.model.PrpDsetChannel;
import cn.com.sinosoft.dms.model.PrpDsetRationrelation;
import cn.com.sinosoft.dms.model.PrpDsetRenewal;
import cn.com.sinosoft.inf.dict.xmlmsg.common.SchemaNode;
import cn.com.sinosoft.inf.dict.xmlmsg.getPlanInfo.TranslateObj;


public class ProductSetObj implements SchemaNode{
	private static final long serialVersionUID = 1L;

	public void validate() throws Exception {
	}
	private List<PrpDset> prpDset = new ArrayList<PrpDset>();
	private List<PrpDsetChannel> prpDsetChannel = new ArrayList<PrpDsetChannel>();
	private List<PrpDsetRationrelation> prpDsetRationrelation = new ArrayList<PrpDsetRationrelation>();
	private List<PrpDsetRenewal> prpDsetRenewal = new ArrayList<PrpDsetRenewal>();
	private List<TranslateObj> translateObj = new ArrayList<TranslateObj>();

	public List<PrpDset> getPrpDset() {
		return prpDset;
	}
	public void setPrpDset(List<PrpDset> prpDset) {
		this.prpDset = prpDset;
	}
	public List<PrpDsetChannel> getPrpDsetChannel() {
		return prpDsetChannel;
	}
	public void setPrpDsetChannel(List<PrpDsetChannel> prpDsetChannel) {
		this.prpDsetChannel = prpDsetChannel;
	}
	public List<PrpDsetRationrelation> getPrpDsetRationrelation() {
		return prpDsetRationrelation;
	}
	public void setPrpDsetRationrelation(
			List<PrpDsetRationrelation> prpDsetRationrelation) {
		this.prpDsetRationrelation = prpDsetRationrelation;
	}
	public List<PrpDsetRenewal> getPrpDsetRenewal() {
		return prpDsetRenewal;
	}
	public void setPrpDsetRenewal(List<PrpDsetRenewal> prpDsetRenewal) {
		this.prpDsetRenewal = prpDsetRenewal;
	}
	public List<TranslateObj> getTranslateObj() {
		return translateObj;
	}
	public void setTranslateObj(List<TranslateObj> translateObj) {
		this.translateObj = translateObj;
	}
	
}
