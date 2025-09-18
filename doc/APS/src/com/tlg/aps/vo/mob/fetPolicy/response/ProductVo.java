package com.tlg.aps.vo.mob.fetPolicy.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.google.gson.annotations.SerializedName;

/** mantis：MOB0001，處理人員：CC009，需求單編號：MOB0001 遠傳手機保險投保&批改作業 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductVo {
	
	@SerializedName("PRODNO")
    private String prodno;

	@SerializedName("PRODNAME")
    private String prodname;

	@SerializedName("IMEI")
    private String imei;

	@SerializedName("TYPE")
    private String type;

	@SerializedName("BRAND")
    private String brand;

	@SerializedName("MODEL")
    private String model;

	@SerializedName("RRP")
    private String rrp;

	@SerializedName("PURCHASE_DATE")
    private String purchaseDate;

	@SerializedName("REPLACE_SERVICE_FEE")
    private String replaceServiceFee;

	public String getProdno() {
		return prodno;
	}

	public void setProdno(String prodno) {
		this.prodno = prodno;
	}

	public String getProdname() {
		return prodname;
	}

	public void setProdname(String prodname) {
		this.prodname = prodname;
	}

	public String getImei() {
		return imei;
	}

	public void setImei(String imei) {
		this.imei = imei;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getRrp() {
		return rrp;
	}

	public void setRrp(String rrp) {
		this.rrp = rrp;
	}

	public String getPurchaseDate() {
		return purchaseDate;
	}

	public void setPurchaseDate(String purchaseDate) {
		this.purchaseDate = purchaseDate;
	}

	public String getReplaceServiceFee() {
		return replaceServiceFee;
	}

	public void setReplaceServiceFee(String replaceServiceFee) {
		this.replaceServiceFee = replaceServiceFee;
	}
	
}
