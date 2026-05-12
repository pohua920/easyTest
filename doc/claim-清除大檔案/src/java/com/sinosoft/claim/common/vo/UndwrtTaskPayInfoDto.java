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
 * 已核赔赔付查询 数据存储临时对象类
 * @author 理赔组
 *
 */
public class UndwrtTaskPayInfoDto {
	private static final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
	private static final DecimalFormat df = new DecimalFormat("#");
	/** 序号 */
	private Object serialNo = 0;
	/** 處理單位 */
	private Object underWriteDeptCode = "";
	private Object underWriteDeptName = "";
	/** 立案日期 */
	private Object claimDate = "";
	/** 核賠通過日期 */
	private Object underWriteEndDate = "";
	/** 理算書號碼 */
	private Object compensateNo = "";
	/** 保單號碼 */
	private Object policyNo = "";
	/** 被保險人 */
	private Object insuredName = "";
	/** 出險日期 */
	private Object damageStartDate = "";
	/** 序號（賠付對象） */
	private Object payObjectSerialNo = "";
	/** 賠付對象 */
	private Object ownerName = "";
	/** 實賠金額 */
	private Object payAmount = 0;
	/** 理賠費用 */
	private Object payFee = 0;
	/** 理賠員 */
	private Object handler1Code = "";
	private Object handler1Name = "";
	/** 業務經辦 */
	private Object handlerCode = "";
	private Object handlerName = "";
	/** 業務來源 */
	private Object businessNature = "";
	/** 賠付日期 */
	private Object payDate = "";
	/** 備註 */
	private Object remark = "";

	public UndwrtTaskPayInfoDto(Map<String, Object> map) {
		CommonUtils.initMapToBean(this, FieldMap, map);
	}

	public UndwrtTaskPayInfoDto() {
	}

	public Object getUnderWriteDeptCode() {
		return underWriteDeptCode;
	}

	public void setUnderWriteDeptCode(Object underWriteDeptCode) {
		this.underWriteDeptCode = underWriteDeptCode;
	}

	public Object getUnderWriteDeptName() {
		return underWriteDeptName;
	}

	public void setUnderWriteDeptName(Object underWriteDeptName) {
		this.underWriteDeptName = underWriteDeptName;
	}

	public Object getClaimDate() {
		return claimDate;
	}

	public void setClaimDate(Object claimDate) {
		this.claimDate = CommonUtils.getMGDateStr(new Date(((Timestamp) claimDate).getTime()), format);
	}

	public Object getUnderWriteEndDate() {
		return underWriteEndDate;
	}

	public void setUnderWriteEndDate(Object underWriteEndDate) {
		this.underWriteEndDate = CommonUtils.getMGDateStr(new Date(((Timestamp) underWriteEndDate).getTime()), format);
	}

	public Object getCompensateNo() {
		return compensateNo;
	}

	public void setCompensateNo(Object compensateNo) {
		this.compensateNo = compensateNo;
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

	public Object getDamageStartDate() {
		return damageStartDate;
	}

	public void setDamageStartDate(Object damageStartDate) {
		this.damageStartDate = CommonUtils.getMGDateStr(new Date(((Timestamp) damageStartDate).getTime()), format);
	}

	public Object getPayObjectSerialNo() {
		return payObjectSerialNo;
	}

	public void setPayObjectSerialNo(Object payObjectSerialNo) {
		this.payObjectSerialNo = ((BigDecimal) payObjectSerialNo).intValue();
	}

	public Object getOwnerName() {
		return ownerName;
	}

	public void setOwnerName(Object ownerName) {
		this.ownerName = ownerName;
	}

	public Object getPayAmount() {
		return payAmount;
	}

	public void setPayAmount(Object payAmount) {
		this.payAmount = df.format(((BigDecimal) payAmount).doubleValue());
	}

	public Object getPayFee() {
		return payFee;
	}

	public void setPayFee(Object payFee) {
		this.payFee = df.format(((BigDecimal) payFee).doubleValue());
		;
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

	public Object getPayDate() {
		return payDate;
	}

	public void setPayDate(Object payDate) {
		this.payDate = CommonUtils.getMGDateStr(new Date(((Timestamp) payDate).getTime()), format);
	}

	public Object getRemark() {
		return remark;
	}

	public void setRemark(Object remark) {
		this.remark = remark;
	}

	public Object getSerialNo() {
		return serialNo;
	}

	public void setSerialNo(Object serialNo) {
		this.serialNo = serialNo;
	}

	public static final Map<String, String> FieldMap = new HashMap<String, String>();
	static {
		Field[] fields = UndwrtTaskPayInfoDto.class.getDeclaredFields();
		for (Field f : fields) {
			FieldMap.put(f.getName().toUpperCase(), f.getName());
		}
	}

	/** 需要展示的excel文档名称 */
	public static String getDisPlayTitile() {
		return "已核賠賠付查詢";
	}

	/** 需要展示的列中文名称 */
	public static String[] getDisPlayClumName() {
		return new String[] { "序號", "處理單位", "立案日期", "核賠通過日期", "理算書號碼", "保單號碼", "被保險人", "出險日期", "序號（賠付對象）", "賠付對象", "實賠金額", "理賠費用", "理賠員", "業務經辦", "業務來源", "賠付日期", "備註" };
	}

	/** 需要展示的列中文名称 */
	public static String[] getDisPlayField() {
		return new String[] { "serialNo", "underWriteDeptName", "claimDate", "underWriteEndDate", "compensateNo", "policyNo", "insuredName", "damageStartDate", "payObjectSerialNo", "ownerName", "payAmount", "payFee", "handler1Name", "handlerName",
				"businessNature", "payDate", "remark" };
	}
}
