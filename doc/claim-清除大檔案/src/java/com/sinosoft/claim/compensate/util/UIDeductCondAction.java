package com.sinosoft.claim.compensate.util;

import ins.framework.common.ServiceFactory;
import ins.framework.utils.DataUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.sinosoft.claim.common.vo.ExceptDeductibleRateDto;
import com.sinosoft.claim.dto.domain.PrpDcodeDto;
import com.sinosoft.claim.schema.model.PrpLdeductCond;
import com.sinosoft.claim.schema.service.facade.PrpDdeductCondService;
import com.sinosoft.claim.ui.control.action.UICodeAction;
import com.sinosoft.claim.ui.control.action.UIPrpDaccidentDeductAction;

/**
 * @Description 计算书免赔条件
 * @author 中科软
 */
public class UIDeductCondAction {

	private UIDeductCondAction() {

	}

	private static UIDeductCondAction uiDeductCondAction = new UIDeductCondAction();

	public static UIDeductCondAction getInstance() {
		return uiDeductCondAction;
	}

	/**
	 * 获取免赔条件list
	 * @param httpServletRequest
	 * @return prpLdeductCondList
	 */
	public List<PrpLdeductCond> getDeductCondList(HttpServletRequest httpServletRequest, boolean flag) {
		String[] deductCode = httpServletRequest.getParameterValues("deductConditionTemp"); // 绝对免赔条件名称代码
		String[] deductCodeTemp = httpServletRequest.getParameterValues("deductConditionTemp");
		String[] times = httpServletRequest.getParameterValues("Times"); // 输入的缺少单证个数或出险次数
		String[] deductName = httpServletRequest.getParameterValues("deductName");
		String driverDeductCode = httpServletRequest.getParameter("driverDeductCondition"); // 驾驶员免赔
		String driverTimes = httpServletRequest.getParameter("driverTimes");
		String driverDeductName = httpServletRequest.getParameter("driverDeductName");
		PrpLdeductCond prpLdeductCond = null;
		if (DataUtils.emptyToNull(driverDeductCode) != null) {
			prpLdeductCond = new PrpLdeductCond();
			prpLdeductCond.getId().setDeductCondCode(driverDeductCode);
			prpLdeductCond.setDeductCondName(driverDeductName);
			prpLdeductCond.setTimes(Integer.parseInt(driverTimes));
		}
		List<PrpLdeductCond> prpLdeductCondList = new ArrayList<PrpLdeductCond>();
		// reason:i从2开始会造成，第0，1个免赔条件无法存入库里
		for (int i = 0; times != null && i < times.length; i++) {
			PrpLdeductCond temp = new PrpLdeductCond();
			if (flag) {
				if (Integer.valueOf(times[i]).intValue() != 0) {
					temp.getId().setDeductCondCode(deductCode[i]);
					temp.setDeductCondName(deductName[i]);
					temp.setTimes(Integer.parseInt(times[i]));
					prpLdeductCondList.add(temp);
				}
			} else {
				temp.getId().setDeductCondCode(deductCodeTemp[i]);
				temp.setDeductCondName(deductName[i]);
				temp.setTimes(Integer.parseInt(times[i]));
				prpLdeductCondList.add(temp);
			}
		}
		if (prpLdeductCond != null) {
			prpLdeductCondList.add(prpLdeductCond);
		}
		return prpLdeductCondList;
	}

	/**
	 * 简易赔案获取免赔条件list
	 * @param httpServletRequest
	 * @return prpLdeductCondList
	 */
	public List<PrpLdeductCond> getQuickCaseDeductCondList(HttpServletRequest httpServletRequest, boolean flag) {
		// 绝对免赔条件名称代码
		String[] deductCode = httpServletRequest.getParameterValues("deductConditionTemp");
		String[] deductCodeTemp = httpServletRequest.getParameterValues("deductConditionTemp");
		// 输入的缺少单证个数或出险次数
		String[] times = httpServletRequest.getParameterValues("Times");
		String[] deductName = httpServletRequest.getParameterValues("deductName");
		// 驾驶员免赔
		String driverDeductCode = httpServletRequest.getParameter("driverDeductCondition");
		String driverTimes = httpServletRequest.getParameter("driverTimes");
		String driverDeductName = httpServletRequest.getParameter("driverDeductName");
		PrpLdeductCond prpLdeductCond = null;
		if (DataUtils.emptyToNull(driverDeductCode) != null) {
			prpLdeductCond = new PrpLdeductCond();
			prpLdeductCond.getId().setDeductCondCode(driverDeductCode);
			prpLdeductCond.setDeductCondName(driverDeductName);
			prpLdeductCond.setTimes(Integer.parseInt(driverTimes));
		}
		List<PrpLdeductCond> prpLdeductCondList = new ArrayList<PrpLdeductCond>();
		// i从2开始会造成，第0，1个免赔条件无法存入库里
		for (int i = 0; i < times.length; i++) {
			PrpLdeductCond temp = new PrpLdeductCond();
			if (flag) {
				if (Integer.valueOf(times[i]).intValue() != 0) {
					temp.getId().setDeductCondCode(deductCode[i]);
					temp.setDeductCondName(deductName[i]);
					temp.setTimes(Integer.parseInt(times[i]));
					prpLdeductCondList.add(temp);
				}
			} else {
				temp.getId().setDeductCondCode(deductCodeTemp[i]);
				temp.setDeductCondName(deductName[i]);
				temp.setTimes(Integer.parseInt(times[i]));
				prpLdeductCondList.add(temp);
			}
		}
		if (prpLdeductCond != null) {
			prpLdeductCondList.add(prpLdeductCond);
		}
		return prpLdeductCondList;
	}

	/**
	 * 获取事故责任免赔率
	 * @param riskCode 险种
	 * @param kindCode 险别
	 * @param indemnityDuty 责任
	 * @param dangerLevel 风险水平
	 * @param clauseType
	 * @param validDate 保单生效日期
	 * @return return
	 * @throws Exception
	 */
	public double getDeductibleRateOfAccident(String riskCode, String kindCode, String indemnityDuty, String dangerLevel, String clauseType, String validDate) throws Exception {
		double dblDutyDeductibleRate = 0d;
		UIPrpDaccidentDeductAction uiPrpDaccidentDeductAction = new UIPrpDaccidentDeductAction();
		dblDutyDeductibleRate = uiPrpDaccidentDeductAction.findAccidentDeductRate(riskCode, kindCode, indemnityDuty, dangerLevel, clauseType, validDate);
		return dblDutyDeductibleRate;
	}

	/**
	 * @Description: 
	 * @author 中科软
	 * @date Feb 28, 2013 11:26:23 AM
	 * @param riskCode // 险种
	 * @param kindCode 保单生效日期
	 * @param dangerLevel 风险水平
	 * @param clauseType
	 * @param validDate 保单生效日期
	 * @return
	 * @throws Exception
	 */
	public Map<String, Double> getDeductibleRateOfAccident(String riskCode, String kindCode, String dangerLevel, String clauseType, String validDate) throws Exception {
		Map<String, Double> deductibleRateOfAccident = new HashMap<String, Double>();
		Collection<PrpDcodeDto> indemnityDutyList = UICodeAction.getInstance().findByConditions("  codeType='IndemnityDuty'");
		Double dutyDeductibleRate;

		for (Iterator<PrpDcodeDto> iter = indemnityDutyList.iterator(); iter.hasNext();) {
			PrpDcodeDto prpDcodeDto = (PrpDcodeDto) iter.next();
			String indemnityDutyCode = prpDcodeDto.getCodeCode();
			dutyDeductibleRate = new Double(getDeductibleRateOfAccident(riskCode, kindCode, indemnityDutyCode, dangerLevel, clauseType, validDate));
			deductibleRateOfAccident.put(indemnityDutyCode, dutyDeductibleRate);
		}
		return deductibleRateOfAccident;
	}

	/**
	 * 获取绝对免赔额
	 * @param clauseType
	 * @param kindCode
	 * @param deductCond
	 * @param riskCode
	 * @param indemnityDuty
	 * @return
	 * @throws Exception
	 */
	public ExceptDeductibleRateDto getDeductibleRateOfAbsolute(String clauseType, String kindCode, List<PrpLdeductCond> deductConditionList, String riskCode, String validDate) throws Exception {
		PrpDdeductCondService prpDdeductCondService = (PrpDdeductCondService) ServiceFactory.getService("prpDdeductCondService");
		return prpDdeductCondService.findDeductibleRateOfAbsolute(clauseType, kindCode, deductConditionList, riskCode, validDate);
	}

	/**
	 * 获取驾驶员免赔率 parm clauseType parm kindCode parm deductCond parm riskCode
	 * return deductibleRateOfAbsolute
	 * @throws Exception
	 */
	public double getDeductibleRateOfDriver(String clauseType, String kindCode, String deductCond, String riskCode) throws Exception {
		double deductibleRateOfDriver = 0;
		return deductibleRateOfDriver;
	}

	/**
	 * 免赔条件常量类
	 * @author 中科软
	 */
	public static class DeductCondCode {
		/**
		 * 单方肇事
		 */
		public final static String NO_APPOINT_DRIVER = "170";

	}

}
