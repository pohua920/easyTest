package com.sinosoft.dmsdriver.domain.productSYN;

import java.util.ArrayList;
import java.util.List;

import com.sinosoft.dmsdriver.domain.common.SchemaNode;
import com.sinosoft.dmsdriver.domain.getPlanInfo.TranslateObj;
import com.sinosoft.dmsdriver.model.PrpDset;
import com.sinosoft.dmsdriver.model.PrpDsetChannel;
import com.sinosoft.dmsdriver.model.PrpDsetRationrelation;
import com.sinosoft.dmsdriver.model.PrpDsetRenewal;

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
