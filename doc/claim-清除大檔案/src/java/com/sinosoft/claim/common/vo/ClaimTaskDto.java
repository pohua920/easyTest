package com.sinosoft.claim.common.vo;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.sinosoft.claim.common.util.CommonUtils;

/***
 * 立案作业查询 数据 临时存储对象类
 * @author 理赔组
 *
 */
public class ClaimTaskDto {
	private static final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
	private static final DecimalFormat df = new DecimalFormat("#");
	/** 序号 */
	private Object serialNo = 0;
	/** 處理單位 */
	private Object comCode = "";
	private Object comCName = "";
	/** 立案日期 */
	private Object claimDate = "";
	/** 賠案號碼 */
	private Object claimNo = "";
	/** 保單號碼 */
	private Object policyNo = "";
	/** 被保險人 */
	private Object insuredName = "";
	/** 生效日期 */
	private Object startDate = "";
	/** 出險日期 */
	private Object damageStartDate = "";
	/** 出險地點 */
	private Object damageAddress = "";
	/** 分項險種 */
	private Object kindCode = "";
	/** 預估金額 */
	private Object claimLoss = 0;
	/** 核賠金額 */
	private Object sumRealPay = 0;
	/** 理賠員 */
	private Object handler1Code = "";
	private Object handler1Name = "";
	/** 業務經辦 */
	private Object handlerCode = "";
	private Object handlerName = "";
	/** 業務來源 */
	private Object businessNature = "";
	/** 備註 */
	private Object remark = "";

	public ClaimTaskDto(Map<String, Object> map) {
		CommonUtils.initMapToBean(this, FieldMap, map);
	}

	public ClaimTaskDto() {
	}

	public Object getSerialNo() {
		return serialNo;
	}

	public void setSerialNo(Object serialNo) {
		this.serialNo = serialNo;
	}

	public Object getComCode() {
		return comCode;
	}

	public void setComCode(Object comCode) {
		this.comCode = comCode;
	}

	public Object getComCName() {
		return comCName;
	}

	public void setComCName(Object comCName) {
		this.comCName = comCName;
	}

	public Object getClaimDate() {
		return claimDate;
	}

	public void setClaimDate(Object claimDate) {
		this.claimDate = CommonUtils.getMGDateStr(new Date(((Timestamp) claimDate).getTime()), format);
	}

	public Object getClaimNo() {
		return claimNo;
	}

	public void setClaimNo(Object claimNo) {
		this.claimNo = claimNo;
	}

	public Object getPolicyNo() {
		return policyNo;
	}

	public void setPolicyNo(Object policyNo) {
		this.policyNo = policyNo;
	}

	public Object getInsuredName() {
		return insuredName;
	}

	public void setInsuredName(Object insuredName) {
		this.insuredName = insuredName;
	}

	public Object getStartDate() {
		return startDate;
	}

	public void setStartDate(Object startDate) {
		this.startDate = CommonUtils.getMGDateStr(new Date(((Timestamp) startDate).getTime()), format);
	}

	public Object getDamageStartDate() {
		return damageStartDate;
	}

	public void setDamageStartDate(Object damageStartDate) {
		this.damageStartDate = CommonUtils.getMGDateStr(new Date(((Timestamp) damageStartDate).getTime()), format);
	}

	public Object getDamageAddress() {
		return damageAddress;
	}

	public void setDamageAddress(Object damageAddress) {
		this.damageAddress = damageAddress;
	}

	public Object getKindCode() {
		return kindCode;
	}

	public void setKindCode(Object kindCode) {
		this.kindCode = kindCode;
	}

	public Object getClaimLoss() {
		return claimLoss;
	}

	public void setClaimLoss(Object claimLoss) {
		this.claimLoss = df.format(((BigDecimal) claimLoss).doubleValue());
	}

	public Object getSumRealPay() {
		return sumRealPay;
	}

	public void setSumRealPay(Object sumRealPay) {
		this.sumRealPay = df.format(((BigDecimal) sumRealPay).doubleValue());
	}

	public Object getHandler1Code() {
		return handler1Code;
	}

	public void setHandler1Code(Object handler1Code) {
		this.handler1Code = handler1Code;
	}

	public Object getHandler1Name() {
		return handler1Name;
	}

	public void setHandler1Name(Object handler1Name) {
		this.handler1Name = handler1Name;
	}

	public Object getHandlerCode() {
		return handlerCode;
	}

	public void setHandlerCode(Object handlerCode) {
		this.handlerCode = handlerCode;
	}

	public Object getHandlerName() {
		return handlerName;
	}

	public void setHandlerName(Object handlerName) {
		this.handlerName = handlerName;
	}

	public Object getBusinessNature() {
		return businessNature;
	}

	public void setBusinessNature(Object businessNature) {
		this.businessNature = businessNature;
	}

	public Object getRemark() {
		return remark;
	}

	public void setRemark(Object remark) {
		this.remark = remark;
	}

	public static final Map<String, String> FieldMap = new HashMap<String, String>();
	static {
		Field[] fields = ClaimTaskDto.class.getDeclaredFields();
		for (Field f : fields) {
			FieldMap.put(f.getName().toUpperCase(), f.getName());
		}
	}

	/** 需要展示的excel文档名称 */
	public static String getDisPlayTitile() {
		return "立案作業查詢";
	}

	/** 需要展示的列中文名称 */
	public static String[] getDisPlayClumName() {
		return new String[] { "序號", "處理單位", "立案日期", "賠案號碼", "保單號碼", "被保險人", "生效日期", "出險日期", "出險地點", "分項險種", "預估金額", "核賠金額", "理賠員", "業務經辦", "業務來源", "備註" };
	}

	/** 需要展示的列属性名称 */
	public static String[] getDisPlayField() {
		return new String[] { "serialNo", "comCName", "claimDate", "claimNo", "policyNo", "insuredName", "startDate", "damageStartDate", "damageAddress", "kindCode", "claimLoss", "sumRealPay", "handler1Name", "handlerName", "businessNature",
				"remark" };
	}
}
