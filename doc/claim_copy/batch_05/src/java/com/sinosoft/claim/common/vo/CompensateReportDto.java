package com.sinosoft.claim.common.vo;

import java.io.Serializable;
import java.util.List;

import com.sinosoft.claim.compensate.vo.CompensateDto;
import com.sinosoft.claim.schema.model.PrpCmain;

/**
 * 自定义保单数据传输对象
 * <p>
 * Title: 车险理赔保单DTO
 * </p>
 * <p>
 * Description: 车险理赔保单样本程序
 * </p>
 * <p>
 * Copyright: Copyright (c) 2013
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * @author 中科软
 * @version 1.0
 */
public class CompensateReportDto implements Serializable {
	/**
	 * 序号
	 */
	private static final long serialVersionUID = -2455394769509919586L;

	/** 保单主信息 */
	private PrpCmain prpCmain;
	/**理算对象 */
	private CompensateDto compensateDto;
	/** 第一段*/
	private String strFirstDuanluo = "";
	/** 第二段*/
	private String strSecondDuanluo = "";
	/**第三段 */
	private String strThirdDuanluo = "";
	/**保险期限 */
	private String strInsuredTerm = "";
	/**车架号 */
	private String strFrameNo = "";
	/**新车购置价 */
	private String dblPurchasePrice = "";
	/** 车辆行驶区域*/
	private String strRunAreaName = "";
	/**使用年限 */
	private String strUseYears = "";
	/**车辆使用性质 */
	private String strUseNature = "";
	/**险别名称 */
	private String strKindNameOutTem = "";
	/**合计 */
	private String strSumFeeOutTem = "";
	/**承保公司 */
	private String strCompany = "";
	/** 定损*/
	private List<SubReportPrintDto> certaInfoList;
	/** 理算*/
	private List<SubReportPrintDto> compensateInfoList;
	/**賠款總計 */
	private String strCSumPaidOut = "";

	public CompensateReportDto() {
	}

	public PrpCmain getPrpCmain() {
		return prpCmain;
	}

	public void setPrpCmain(PrpCmain prpCmain) {
		this.prpCmain = prpCmain;
	}

	public CompensateDto getCompensateDto() {
		return compensateDto;
	}

	public void setCompensateDto(CompensateDto compensateDto) {
		this.compensateDto = compensateDto;
	}

	public String getStrFrameNo() {
		return strFrameNo;
	}

	public void setStrFrameNo(String strFrameNo) {
		this.strFrameNo = strFrameNo;
	}

	public String getDblPurchasePrice() {
		return dblPurchasePrice;
	}

	public void setDblPurchasePrice(String dblPurchasePrice) {
		this.dblPurchasePrice = dblPurchasePrice;
	}

	public String getStrRunAreaName() {
		return strRunAreaName;
	}

	public void setStrRunAreaName(String strRunAreaName) {
		this.strRunAreaName = strRunAreaName;
	}

	public String getStrUseYears() {
		return strUseYears;
	}

	public void setStrUseYears(String strUseYears) {
		this.strUseYears = strUseYears;
	}

	public String getStrFirstDuanluo() {
		return strFirstDuanluo;
	}

	public void setStrFirstDuanluo(String strFirstDuanluo) {
		this.strFirstDuanluo = strFirstDuanluo;
	}

	public String getStrSecondDuanluo() {
		return strSecondDuanluo;
	}

	public void setStrSecondDuanluo(String strSecondDuanluo) {
		this.strSecondDuanluo = strSecondDuanluo;
	}

	public String getStrInsuredTerm() {
		return strInsuredTerm;
	}

	public void setStrInsuredTerm(String strInsuredTerm) {
		this.strInsuredTerm = strInsuredTerm;
	}

	public String getStrUseNature() {
		return strUseNature;
	}

	public void setStrUseNature(String strUseNature) {
		this.strUseNature = strUseNature;
	}

	public String getStrThirdDuanluo() {
		return strThirdDuanluo;
	}

	public void setStrThirdDuanluo(String strThirdDuanluo) {
		this.strThirdDuanluo = strThirdDuanluo;
	}

	public String getStrKindNameOutTem() {
		return strKindNameOutTem;
	}

	public void setStrKindNameOutTem(String strKindNameOutTem) {
		this.strKindNameOutTem = strKindNameOutTem;
	}

	public String getStrCompany() {
		return strCompany;
	}

	public void setStrCompany(String strCompany) {
		this.strCompany = strCompany;
	}

	public String getStrSumFeeOutTem() {
		return strSumFeeOutTem;
	}

	public void setStrSumFeeOutTem(String strSumFeeOutTem) {
		this.strSumFeeOutTem = strSumFeeOutTem;
	}

	public List<SubReportPrintDto> getCertaInfoList() {
		return certaInfoList;
	}

	public void setCertaInfoList(List<SubReportPrintDto> certaInfoList) {
		this.certaInfoList = certaInfoList;
	}

	public List<SubReportPrintDto> getCompensateInfoList() {
		return compensateInfoList;
	}

	public void setCompensateInfoList(List<SubReportPrintDto> compensateInfoList) {
		this.compensateInfoList = compensateInfoList;
	}

	public String getStrCSumPaidOut() {
		return strCSumPaidOut;
	}

	public void setStrCSumPaidOut(String strCSumPaidOut) {
		this.strCSumPaidOut = strCSumPaidOut;
	}

}
