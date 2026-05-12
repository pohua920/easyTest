/*
 * @(#)BLStandardCheck.java	Feb 21, 2013
 *
 * @Company < Technology Development Company LTD..>
 */
package com.sinosoft.claim.undwrt.service.spring;

import ins.framework.dao.GenericDaoHibernate;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

import org.apache.log4j.Logger;

import com.sinosoft.claim.schema.model.UtiUwFactor;
import com.sinosoft.claim.schema.service.facade.UtiUwFactorService;
import com.sinosoft.claim.undwrt.service.facade.StandardCheckService;
import com.sinosoft.platform.dto.domain.UtiUwConditionDto;
import com.sinosoft.sysframework.exceptionlog.UserException;
import com.sinosoft.undwrt.dto.custom.UwFactorConstants;

/**
 * @Project <CL-Allocation tool>
 * @version <1.0>
 * @author 中科软
 * @description
 */
@SuppressWarnings("unchecked")
public class StandardCheckServiceSpringImpl extends GenericDaoHibernate implements StandardCheckService {

	/**
	 * 通过判处异常来将结果展示给用户
	 * @param title
	 * @param standardData
	 * @param businessData
	 * @throws UserException
	 */
	static Logger logger = Logger.getLogger(StandardCheckServiceSpringImpl.class);
	private UtiUwFactorService utiUwFactorService;

	/***
	 * 根据权限配置组织业务处理超权限提示讯息
	 * @param utiUwConditionDto 权限配置讯息
	 * @param businessData 业务处理讯息
	 */
	public void throwException(UtiUwConditionDto utiUwConditionDto, String businessData) throws Exception {
		String title = "超权限";
		String info = "";
		if (utiUwConditionDto != null) {
			UtiUwFactor utiUwFactor = utiUwFactorService.findByPrimaryKey(utiUwConditionDto.getUwType(), utiUwConditionDto.getClassCode(), utiUwConditionDto.getFactorCode());
			title = "審核項目：" + utiUwFactor.getFactorName();
			if (UwFactorConstants.MultiSelectFlag.COMPLEX.equalsIgnoreCase(utiUwFactor.getMultiSelectFlag())) {
				title += "（" + utiUwConditionDto.getRemark() + "）";
			}

			if (UwFactorConstants.Attr.BOOLEAN.equals(utiUwFactor.getFactorAttr())) {
				// info = "您无权审核" + utiUwFactorDto.getFactorName() + "；当前业务：" +
				// utiUwFactorDto.getFactorName();
				// reason:客户要求倒签单提示友好些
				if ("N".equals(utiUwConditionDto.getFactorValue().toString())) {
					if ("BackOperation".equals(utiUwFactor.getId().getFactorCode())) {
						info = "您没有审核倒签单的权限。";
					} else if ("RealPayFlag".equals(utiUwFactor.getId().getFactorCode())) {
						info = "您没有审核保费未实收案件的权限。";
					}
				} else {
					if (businessData == null) {
						businessData = "數據讀取錯誤";
					}
					info = "审核权限：" + utiUwConditionDto.getFactorValue().toString() + "天；当前业务：" + businessData.toString() + "天，已超过您的审核权限。";
				}
			} else {
				if (businessData == null) {
					businessData = "數據讀取錯誤";
				}
				info = "审核权限：" + utiUwConditionDto.getFactorValue().toString() + "；当前业务：" + businessData.toString() + "。";
			}
		} else {
			title = "未配置標準權限";
		}
		throw new UserException(2005, 829, title, info);

	}

	/***
	 * 
	 * 根据标准因子判断是否有处理业务的权限
	 * @param standardList 标准因子
	 * @param businessDataMap 业务数据
	 */
	public boolean checkHepei(Collection standardList, Map businessDataMap) throws Exception {
		if (standardList == null || standardList.size() == 0) {
			throwException(null, null);
		}
		if (businessDataMap == null) {
			throw new UserException(2005, 829, "讀取的業務數據對象是空值，請聯系系統管理員", "");
		}
		// 循环标准的List，根据标准的因子来比较
		for (Iterator<UtiUwConditionDto> iter = standardList.iterator(); iter.hasNext();) {
			UtiUwConditionDto utiUwConditionDto = (UtiUwConditionDto) iter.next();
			String factoryCodeKey = utiUwConditionDto.getFactorCode();
			// 根据标准的factorCode 从Map里得到业务数据的对象（Double,String,Map）根据得到的对象进行不同的比较
			Object businessValueObject = businessDataMap.get(factoryCodeKey);
			if (businessValueObject != null) {
				if (!(businessValueObject instanceof Map)) { // 如果得到的对象不是Map
					checkData(utiUwConditionDto, businessValueObject);
					businessDataMap.remove(factoryCodeKey);
				} else {
					// String businessComboKey =
					// utiUwConditionDto.getCodeTypeValue();
					String businessComboKey = utiUwConditionDto.getFactorValue();
					Map<?, ?> businessComboValueobject = (Map<?, ?>) businessValueObject;

					if (businessComboValueobject == null || businessComboValueobject.size() == 0) {
						// 通过
					} else {
						if (!UwFactorConstants.Sign.OTHER.equals(businessComboKey)) { // 如果得到的是Map且CodeTypeValue
							// 字断不=OtherValue
							Object value = businessComboValueobject.get(businessComboKey);
							checkData(utiUwConditionDto, value);
							businessComboValueobject.remove(businessComboKey);
						} else {
							for (Iterator<?> ite = businessComboValueobject.keySet().iterator(); ite.hasNext();) { // 如果是OtherValue则循环业务数据
								String key = (String) ite.next();
								Object value = businessComboValueobject.get(key);
								checkData(utiUwConditionDto, value);
								businessComboValueobject.remove(key);
							}
						}
					}
				}
			} else {
				throwException(utiUwConditionDto, null);
			}
		}
		return true;
	}

	/***
	 * 
	 * @Description: 同标准因子比较，校验数据是否满足审核通过权限
	 * @param utiUwConditionDto 标准业务权限
	 * @param businessValueObject 校验数据
	 * @throws Exception
	 */
	private void checkData(UtiUwConditionDto utiUwConditionDto, Object businessValueObject) throws Exception {
		if (businessValueObject instanceof Double) { // 如果是Double型，直接比大小
			double standardValue = Double.parseDouble(utiUwConditionDto.getFactorValue());
			double businessValue = ((Double) businessValueObject).doubleValue();
			if (Double.compare(businessValue, standardValue) > 0 && standardValue >= 0.0) {
				throwException(utiUwConditionDto, String.valueOf(businessValue));
			}
		} else if (businessValueObject instanceof String) { // 如果是String型，根据业务进行比较
			UtiUwFactor utiUwFactor = utiUwFactorService.findByPrimaryKey(utiUwConditionDto.getUwType(), utiUwConditionDto.getClassCode(), utiUwConditionDto.getFactorCode());
			if (UwFactorConstants.Attr.BOOLEAN.equals(utiUwFactor.getFactorAttr())) {
				if ("Y".equals(utiUwConditionDto.getFactorValue()) || "Y".equals((String) businessValueObject)) {
					// 通过
				} else if (!"N".equals(utiUwConditionDto.getFactorValue()) && (Integer.parseInt(utiUwConditionDto.getFactorValue()) >= Integer.parseInt((String) businessValueObject))) {
					// 倒签单天数判断
				} else {
					throwException(utiUwConditionDto, String.valueOf(businessValueObject));
				}
			} else {
				String[] standardValueArray = ((String) utiUwConditionDto.getFactorValue()).split(",");

				if (standardValueArray != null && standardValueArray.length > 0) {
					boolean isEqual = false;
					for (int i = 0; i < standardValueArray.length; i++) {
						String string = standardValueArray[i];
						if (((String) businessValueObject).equalsIgnoreCase(string)) {
							isEqual = true;
							break;
						}
					}
					if (!isEqual) {
						throwException(utiUwConditionDto, String.valueOf(businessValueObject));
					}
				}
			}

		}
	}

	public UtiUwFactorService getUtiUwFactorService() {
		return utiUwFactorService;
	}

	public void setUtiUwFactorService(UtiUwFactorService utiUwFactorService) {
		this.utiUwFactorService = utiUwFactorService;
	}

}
