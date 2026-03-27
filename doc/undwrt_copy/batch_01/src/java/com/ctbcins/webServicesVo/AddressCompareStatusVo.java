package com.ctbcins.webServicesVo;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * mantis： CAR0369，處理人員：BI086，需求單編號：CAR0369:核心車險地址正規化作業
 * @author bi086
 *
 */
@XmlRootElement
public class AddressCompareStatusVo extends BaseResponseVo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * T-要保
	 * B-報價
	 */
	private String businessType;
	
	private String businessNo;
	
	/**
	 * 0 - 沒有命中資料
	 * 1 - 有命中資料
	 */
	private String status;

	public String getBusinessNo() {
		return businessNo;
	}

	public void setBusinessNo(String businessNo) {
		this.businessNo = businessNo;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getBusinessType() {
		return businessType;
	}

	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}
}