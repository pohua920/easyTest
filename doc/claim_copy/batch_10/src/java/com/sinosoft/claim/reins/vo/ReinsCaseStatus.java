package com.sinosoft.claim.reins.vo;

import java.io.Serializable;

import com.sinosoft.sysframework.common.datatype.DateTime;

/**
 * 再保结案状态
 * @author 中科软
 *
 */
public class ReinsCaseStatus implements Serializable {
	private static final long serialVersionUID = 1L;

	public static class BusinessType {
		private String businessTypeCode;

		private BusinessType(String businessTypeCode) {
			this.businessTypeCode = businessTypeCode;
		}

		private String getBusinessTypeCode() {
			return this.businessTypeCode;
		}

		/**
		 * 结案
		 */
		public static BusinessType ENDCASE = new BusinessType("0");
		/**
		 * 注销
		 */
		public static BusinessType CANCLE = new BusinessType("1");
		/**
		 * 拒赔
		 */
		public static BusinessType REFUSE = new BusinessType("2");
		/**
		 * 重开赔案
		 */
		public static BusinessType REOPEN = new BusinessType("3");

	}

	private BusinessType businessType;
	private String claimNo;
	private DateTime operateDate;
	private String operateComCode;
	private String operaterCode;

	public String getClaimNo() {
		return claimNo;
	}

	public void setClaimNo(String claimNo) {
		this.claimNo = claimNo;
	}

	public String getOperateComCode() {
		return operateComCode;
	}

	public void setOperateComCode(String operateComCode) {
		this.operateComCode = operateComCode;
	}

	public DateTime getOperateDate() {
		return operateDate;
	}

	public void setOperateDate(DateTime operateDate) {
		this.operateDate = operateDate;
	}

	public String getOperaterCode() {
		return operaterCode;
	}

	public void setOperaterCode(String operaterCode) {
		this.operaterCode = operaterCode;
	}

	public BusinessType getBusinessType() {
		return this.businessType;
	}

	public void setBusinessType(BusinessType businessType) {
		this.businessType = businessType;
	}

	public String getBusinessTypeCode() {
		return getBusinessType().getBusinessTypeCode();
	}

}
