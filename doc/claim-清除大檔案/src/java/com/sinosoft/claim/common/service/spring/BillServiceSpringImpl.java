/**
 * <p>Title: BillServiceSpringImpl</p>
 * <p>Description:单号取号类 </p>
 * <p>Copyright: Copyright (c) 2013</p>
 * <p>Company: Sinosoft</p>
 * @author 中科软
 * @version 1.0
 */
package com.sinosoft.claim.common.service.spring;

import ins.framework.common.DateTime;
import ins.framework.common.QueryRule;
import ins.framework.dao.GenericDaoHibernate;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import com.sinosoft.claim.common.ConstantCodes;
import com.sinosoft.claim.common.service.facade.BillService;
import com.sinosoft.claim.common.service.facade.UtiCodeTransferService;
import com.sinosoft.claim.common.util.CommonUtils;
import com.sinosoft.claim.common.util.HibernateUtils;
import com.sinosoft.claim.schema.model.PrpCitemKind;
import com.sinosoft.claim.schema.model.PrpCmain;
import com.sinosoft.claim.schema.model.PrpGroup;
import com.sinosoft.claim.schema.model.PrpLcompensate;
import com.sinosoft.claim.schema.model.PrpLregist;
import com.sinosoft.claim.schema.model.PrpMaxNo;
import com.sinosoft.claim.schema.model.PrpMaxNoId;
import com.sinosoft.claim.schema.model.PrpMaxUse;
import com.sinosoft.claim.schema.model.PrpMaxUseId;
import com.sinosoft.claim.schema.model.UtiCodeTransfer;
import com.sinosoft.claim.schema.model.UtiKey;
import com.sinosoft.claim.schema.service.facade.PrpCitemKindService;
import com.sinosoft.claim.schema.service.facade.PrpCmainService;
import com.sinosoft.claim.schema.service.facade.PrpGroupService;
import com.sinosoft.claim.schema.service.facade.PrpLcompensateService;
import com.sinosoft.claim.schema.service.facade.PrpLregistService;
import com.sinosoft.claim.schema.service.facade.PrpMaxNoService;
import com.sinosoft.claim.schema.service.facade.PrpMaxUseService;
import com.sinosoft.claim.schema.service.facade.UtiKeyService;
import com.sinosoft.sysframework.common.util.StringUtils;
import com.sinosoft.sysframework.exceptionlog.UserException;
import com.sinosoft.sysframework.reference.AppConfig;

public class BillServiceSpringImpl extends GenericDaoHibernate<PrpLcompensate, String> implements BillService {
	/** 理算service*/
	private PrpLcompensateService prpLcompensateService;
	/** 最大单号service*/
	private PrpMaxNoService prpMaxNoService;
	/** 单号的配置service*/
	private UtiKeyService utiKeyService;
	/** 单号组信息service*/
	private PrpGroupService prpGroupService;
	/** 使用的最大单号service*/
	private PrpMaxUseService prpMaxUseService;
	/** 保单service*/
	private PrpCmainService prpCmainService;
	/** 险别service*/
	private PrpCitemKindService prpCitemKindService;
	private UtiCodeTransferService utiCodeTransferService;
	private PrpLregistService prpLregistService;
	/** 强制险险别集合*/
	private static List<String> BLKindList = new ArrayList<String>();
	/** 盗窃险险别集合*/
	private static List<String> TLKindList = new ArrayList<String>();
	/** 盗窃险出险原因*/
	private static List<String> DamageCodeTLList = new ArrayList<String>();
	private static boolean init = false;

	/**
	 * 年度单号初始化（批量初始化）
	 * @param iRiskSchemas 险种代码数组
	 * @param iCompanySchemas 机构代码数组
	 * @param iTableSchemas 数据表数组
	 * @param iYear 4位年份
	 * @param iInitNo 初始化序号
	 * @return true/false 批量初始化成功/失败
	 * @throws SQLException,Exception,UserException
	 */
	@Override
	public boolean batchInitNo(Vector<String> iRiskSchemas, Vector<String> iCompanySchemas, Vector<String> iTableSchemas, String iYear, String iInitNo) throws SQLException, Exception {
		int i, j, k;
		int intChgLength = 0;
		String strRiskCode = "";
		String strComCode = "";
		String strTableName = "";
		String strGroupNo = "";
		String strMaxNo = "";
		PrpMaxNo prpMaxNo = null;
		UtiKey utiKey = null;
		List<PrpMaxNo> billArray = null;
		if (iYear.length() != 4) {
			// System.out.println("年度必须为4位!");
			return false;
		}
		// 循环初始化单号
		for (i = 0; i < iRiskSchemas.size(); i++) {
			strRiskCode = (String) iRiskSchemas.get(i);
			for (j = 0; j < iCompanySchemas.size(); j++) {
				strComCode = (String) iCompanySchemas.get(j);
				billArray = new ArrayList<PrpMaxNo>();
				// 一个编组作为一个事务
				for (k = 0; k < iTableSchemas.size(); k++) {
					strTableName = (String) iTableSchemas.get(k);
					strGroupNo = getGroupNo(strTableName, strRiskCode, strComCode, iYear, null);
					utiKey = new UtiKey();
					utiKey = utiKeyService.findUtiKey(strTableName);
					if (utiKey == null)
						return false;
					intChgLength = utiKey.getColLength() - iInitNo.length();
					if (intChgLength < 0)
						continue;
					strMaxNo = StringUtils.newString("0", intChgLength) + iInitNo;
					prpMaxNo = new PrpMaxNo();
					prpMaxNo.getId().setGroupNo(strGroupNo);
					prpMaxNo.getId().setTableName(strTableName.trim());
					prpMaxNo.getId().setMaxNo(strMaxNo);
					prpMaxNo.setFlag("0");
					billArray.add(prpMaxNo); // 数组方式
				}
				prpMaxNoService.save(billArray);
			}
		}
		return true;
	}

	/**
	 * 更具保单年份生成单号
	 * @param iTableName 表名
	 * @param iRiskCode 险别
	 * @param infoMap 条件，包含出险原因，保单号码
	 * @return 返回生成的单号
	 * @throws Exception
	 */
	public String getNoByPolciyYear(String iTableName, String iRiskCode, Map<String,Object>infoMap) throws Exception {
			int iYear = 0;
			String iComCode = "";
			String policyNo = (String) infoMap.get("policyNo");
			try {
				PrpCmain prpCmain = prpCmainService.findByPrimaryKey(policyNo);
				if(null!=prpCmain.getComCode()&&!"".equals(prpCmain.getComCode())){
					iComCode = prpCmain.getComCode();
				}
				if("prplclaim".equals(iTableName)){
					if("D".equals(ConstantCodes.carClassMap.get(prpCmain.getRiskCode()))){
						DateTime dataTime = new DateTime(prpCmain.getStartDate());
						iYear = dataTime.getYear();
					}else{
						String registNo = (String) infoMap.get("registNo");
						PrpLregist prpLregist = prpLregistService.findPrpLregist(registNo);
						DateTime dataTime = new DateTime(prpLregist.getReportDate());
						iYear = dataTime.getYear();
					}
				}else{
					DateTime dataTime = new DateTime(prpCmain.getStartDate());
					iYear = dataTime.getYear();
				}
			} catch (Exception e) {
				iYear = DateTime.current().getYear();
			}
			if ("prplcompensate".equals(iTableName)||"prplReplevy".equals(iTableName)) {
				return this.getNo(iTableName, iRiskCode, iComCode, iYear, "", infoMap);
			}else{
				return this.getNo(iTableName, policyNo, iComCode, iYear, "", infoMap);
			}
	}

	/**
	 * 获取一个新号
	 * @param iTableName:单号数据表名
	 * @param iRiskCode:险种代码
	 * @param iComCode:出单部门
	 * @param iYear:业务年度
	 * @param damageCode:任意险出险原因
	 * @return 单号字符串
	 * @throws UserException
	 * @throws Exception
	 */
	@Override
	public String getNo(String iTableName, String iRiskCode, String iComCode, int iYear, String iSessionID, Map<String,Object>infoMap) throws SQLException, Exception {
		String strMaxNo = null, strMinNo = null, strNewNo = "";
		int intChgLength;
		String strCertiNoMax = "";
		int intMaxLength = 2;
		int intMaxNo;
		// 1)计算书的取号
		if (iTableName.equals("prplcompensate")) {
			String claimNo = iRiskCode;
			String chargeType = (String) infoMap.get("chargeType");
			// 当是计算书的情况下，IRiskCode传入的立案号码，所以可以根据立案号码来计算需要的序号。
			String strCondition = " 1=1 and claimNo='" + claimNo + "'";
			if("D".equals(chargeType)){
				strCondition += " and CompensateNo like 'D"+claimNo+"%' ";
			}else{
				strCondition += " and CompensateNo like 'C"+claimNo+"%' ";
			}
			strCondition +=  " ORDER BY CompensateNo DESC";
			List<PrpLcompensate> vecSchemas = prpLcompensateService.findByConditions(strCondition);
			if (vecSchemas.size() > 0) {
				PrpLcompensate prpLcompensate = (PrpLcompensate) vecSchemas.get(0);
				strCertiNoMax = prpLcompensate.getCompensateNo();
			}
			// 下面是改後的代码
			if (strCertiNoMax.length() > 0) {
				if("D".equals(chargeType)) {
					strMaxNo = strCertiNoMax.substring(claimNo.length() + 2);//延迟利息最后一位为序号
				} else {
					strMaxNo = strCertiNoMax.substring(claimNo.length() + 1);
				}
			} else {
				strMaxNo = "0";
			}
			intMaxNo = Integer.parseInt(strMaxNo) + 1;
			strNewNo = StringUtils.newString("0", intMaxLength - ("" + intMaxNo).length()) + intMaxNo;
			if (strNewNo.length() > intMaxLength){
				throw new Exception("too long!");
			}
			// 根据要求将计算书号改为"3"+（2，21）,去掉下划线,+序号
			if("D".equals(chargeType)){
				strNewNo = "D" + claimNo + strNewNo.replaceFirst("0", "X");//需要第一位为X，第二位为真正序号
			}else{
				strNewNo = "C" + claimNo + strNewNo;
			}
			return strNewNo;
		}
		if (iTableName.equals("prplReplevy")) {//追偿计算书
			String claimNo = iRiskCode;
			// 当情况下，IRiskCode传入的立案号码，所以可以根据立案号码来计算需要的序号。
			String strCondition = " 1=1 and claimNo='" + claimNo + "' and CompensateNo like 'R"+claimNo+"%' ORDER BY CompensateNo DESC";
			List<PrpLcompensate> vecSchemas = prpLcompensateService.findByConditions(strCondition);
			if (vecSchemas.size() > 0) {
				PrpLcompensate prpLcompensate = (PrpLcompensate) vecSchemas.get(0);
				strCertiNoMax = prpLcompensate.getCompensateNo();
			}
			// 下面是改後的代码
			if (strCertiNoMax.length() > 0) {
				strMaxNo = strCertiNoMax.substring(claimNo.length() + 1);
				intMaxNo = Integer.parseInt(strMaxNo) + 1;
			} else {
				strMaxNo = "0";
				intMaxNo = 0;
			}
			strNewNo = StringUtils.newString("0", intMaxLength - ("" + intMaxNo).length()) + intMaxNo;
			if (strNewNo.length() > intMaxLength) {
				throw new Exception("too long!");
			}
			strNewNo = "R" + claimNo + strNewNo;
			return strNewNo;
		}
		if (iTableName.equals("prpLremnant")) {//残余物计算书
			String claimNo = iRiskCode;
			// 当情况下，IRiskCode传入的立案号码，所以可以根据立案号码来计算需要的序号。
			String strCondition = " 1=1 and claimNo='" + claimNo + "' and compensateNo like 'S%' ORDER BY CompensateNo DESC";
			List<PrpLcompensate> vecSchemas = prpLcompensateService.findByConditions(strCondition);
			if (vecSchemas.size() > 0) {
				PrpLcompensate prpLcompensate = (PrpLcompensate) vecSchemas.get(0);
				strCertiNoMax = prpLcompensate.getCompensateNo();
			}
			// 下面是改後的代码
			if (strCertiNoMax.length() > 0) {
				strMaxNo = strCertiNoMax.substring(claimNo.length() + 1);
			} else {
				strMaxNo = "0";
			}
			intMaxNo = Integer.parseInt(strMaxNo) + 1;
			strNewNo = StringUtils.newString("0", intMaxLength - ("" + intMaxNo).length()) + intMaxNo;
			if (strNewNo.length() > intMaxLength) {
				throw new Exception("too long!");
			}
			strNewNo = "S" + claimNo + strNewNo;
			return strNewNo;
		}
		String[] strPickNo = new String[3];
		PrpMaxNo prpMaxNo = null;
		PrpMaxUse prpMaxUse = null;
		int i;
		String strYear = String.valueOf(iYear);
		// 拼接成组号
		String strGroupNo = getGroupNo(iTableName, iRiskCode, iComCode, strYear, infoMap);
		if (strGroupNo == null || strGroupNo.length() == 0) {
			return "";
		}
		// 根据表名获取单号描述信息
		UtiKey utiKey = utiKeyService.findUtiKey(iTableName.trim());
		if (utiKey == null) {
			return "";
		}
		int forCount = 0;
		while (true) {
			forCount++;
			if (forCount > 100) {
				throw new UserException(-1, -3, "獲取單號失敗", "獲取單號失敗,請聯系管理員");
			}
			strPickNo = prpMaxNoService.findByNewTransaction(strGroupNo, iTableName);
			if (strPickNo == null) {
				return "";
			} else {
				strMaxNo = strPickNo[0];
				strMinNo = strPickNo[1];
				if (strMaxNo.equals("") || strMinNo.equals("")) {
					//CLM0116，處理人員：Sam，需求單編號：CLM0116..新核心-傷害險修改理賠序號 start*/
					if ("prplclaim".equals(iTableName) && "HPL".equals(strGroupNo.substring(6,9))) {
						strMinNo = "5000";
					}
					else {
						strMinNo = "00000";
					}
					//CLM0116，處理人員：Sam，需求單編號：CLM0116..新核心-傷害險修改理賠序號 end*/
					strMaxNo = "";
					intMaxNo = Integer.parseInt(strMinNo) + 1;
					strMaxNo = String.valueOf(intMaxNo);
					intChgLength = utiKey.getColLength() - strMaxNo.length();
					if (intChgLength < 0)
						intChgLength = 0;
					strMaxNo = StringUtils.newString("0", intChgLength) + strMaxNo;
					// 向prpMaxNo表中插入新生成的单号
					prpMaxNo = new PrpMaxNo();
					prpMaxNo.getId().setGroupNo(strGroupNo);
					prpMaxNo.getId().setTableName(iTableName);
					prpMaxNo.getId().setMaxNo(strMaxNo);
					prpMaxNo.setFlag("0");
					try {
						//CLM0116，處理人員：Sam，需求單編號：CLM0116..新核心-傷害險修改理賠序號 start*/
						boolean getNoSuccess = prpMaxNoService.saveByNewTransaction(strMinNo , prpMaxNo);
						if(!getNoSuccess){
							continue;
						}
						//CLM0116，處理人員：Sam，需求單編號：CLM0116..新核心-傷害險修改理賠序號 end*/
					} catch (SQLException sqlex) {
						continue;
					} catch (Exception ex) {
						continue;
					}
					continue;
				}
			}
			if (strMaxNo.trim().equals(strMinNo.trim())) {
				//CLM0116，處理人員：Sam，需求單編號：CLM0116..新核心-傷害險修改理賠序號 start*/
				if ("prplclaim".equals(iTableName) && "HPL".equals(strGroupNo.substring(6,9))) {
					strMaxNo = genNextNum(strMinNo);										
				}else{
					intMaxNo = Integer.parseInt(strMinNo) + 1;
					strMaxNo = String.valueOf(intMaxNo);
					intChgLength = utiKey.getColLength() - strMaxNo.length();
					if (intChgLength < 0)
						intChgLength = 0;
					strMaxNo = StringUtils.newString("0", intChgLength) + strMaxNo;
				}
				//CLM0116，處理人員：Sam，需求單編號：CLM0116..新核心-傷害險修改理賠序號 end*/
				
				// 向prpMaxNo表中插入新生成的单号
				prpMaxNo = new PrpMaxNo();
				prpMaxNo.getId().setGroupNo(strGroupNo);
				prpMaxNo.getId().setTableName(iTableName);
				prpMaxNo.getId().setMaxNo(strMaxNo);
				prpMaxNo.setFlag("0");
				try {
					//CLM0116，處理人員：Sam，需求單編號：CLM0116..新核心-傷害險修改理賠序號 start*/
					boolean getNoSuccess = prpMaxNoService.saveByNewTransaction(strMinNo , prpMaxNo);
					if(!getNoSuccess){
						continue;
					}
					//CLM0116，處理人員：Sam，需求單編號：CLM0116..新核心-傷害險修改理賠序號 end*/
				} catch (SQLException sqlex) {
					continue;
				} catch (Exception ex) {
					continue;
				}
			}
			//CLM0116，處理人員：Sam，需求單編號：CLM0116..新核心-傷害險修改理賠序號 start*/
//			try {
//				PrpMaxNoId prpMaxNoId = new PrpMaxNoId();
//				prpMaxNoId.setGroupNo(strGroupNo);
//				prpMaxNoId.setTableName(iTableName);
//				prpMaxNoId.setMaxNo(strMinNo);
//				prpMaxNoService.deleteByNewTransaction(prpMaxNoId);
//			} catch (SQLException sqlex) {
//				continue;
//			} catch (Exception ex) {
//				continue;
//			}
			//CLM0116，處理人員：Sam，需求單編號：CLM0116..新核心-傷害險修改理賠序號 end*/
			// 向prpMaxUse表中插入最小的单号
			prpMaxUse = new PrpMaxUse();
			prpMaxUse.getId().setGroupNo(strGroupNo);
			prpMaxUse.getId().setTableName(iTableName);
			prpMaxUse.getId().setMaxNo(strMinNo);
			prpMaxUse.setTtyCode(iSessionID);
			prpMaxUse.setFlag("0");
			try {
				prpMaxUseService.saveByNewTransaction(prpMaxUse);
			} catch (SQLException sqlex) {
				continue;
			} catch (Exception ex) {
				continue;
			}
			// 拼号了呢。。
			strNewNo = this.pullNo(iTableName, strMinNo, strGroupNo, iRiskCode, infoMap);
			// 校验单号的有效性

			if (checkNo(iTableName, strNewNo, strGroupNo, "0", iRiskCode, infoMap)) {
				break;
			}
			for (i = 0; i < 5; i++) {
				// 删除PrpMaxUse表中的单号
				try {
					PrpMaxUseId prpMaxUseId = new PrpMaxUseId();
					prpMaxUseId.setGroupNo(strGroupNo);
					prpMaxUseId.setMaxNo(strNewNo);
					prpMaxUseId.setTableName(iTableName);
					prpMaxUseService.deleteByNewTransaction(prpMaxUseId);
				} catch (SQLException sqlex) {
					continue;
				} catch (Exception ex) {
					continue;
				}
			}
		}
		// 返回新生成的单号
		return strNewNo;
	}

	/**
	 * 获取一个新号
	 * @param iTableName:单号数据表名
	 * @param iRiskCode:险种代码
	 * @param iComCode:出单部门
	 * @param iYear:业务年度
	 * @return 单号字符串
	 * @throws UserException
	 * @throws Exception
	 */
	@Override
	public String getNo(String iTableName, String iRiskCode, String iComCode, int iYear) throws SQLException, Exception {
		// 返回新生成的单号
		return this.getNo(iTableName, iRiskCode, iComCode, iYear, "", null);
	}

	/**
	 * 拉长单号
	 * @param iTableName 数据表名
	 * @param iBillNo 单号
	 * @param iGroupNo 组号
	 * @param damageCode 任意险出险原因
	 * @return strBillNo 拉长後的单号
	 */
	public String pullNo(String iTableName, String iBillNo, String iGroupNo, String iRiskCode, Map<String,Object>infoMap) throws Exception {
		UtiKey utiKey = null;
		String strHeadID = "";
		int colLength = 0;
		String strBillNo = "";
		int intNoLength = 0;
		int intChgLength = 0;
		int intLength = 0;
		strBillNo = iBillNo;
		utiKey = utiKeyService.findUtiKey(iTableName);
		if (utiKey == null) {
			return strBillNo;
		}
		strHeadID = utiKey.getHeadID();
		if (strHeadID == null) {
			strHeadID = "";
		}
		colLength = utiKey.getColLength();
		if ("prplclaim".equals(iTableName)){
			PrpCmain prpCmain = prpCmainService.findByPrimaryKey(iRiskCode);
			UtiCodeTransfer utiCodeTransfer = utiCodeTransferService.findUtiCodeTransfer(prpCmain.getRiskCode());
			if("D".equals(utiCodeTransfer.getRiskType())){
				if (!"BL".equals(this.getRiskCode(iRiskCode, infoMap))) {
					colLength = colLength - 1;
				}
			}else if("Q".equals(utiCodeTransfer.getRiskType())){
				colLength = colLength - 1;
			}else if("E".equals(utiCodeTransfer.getRiskType())||"G".equals(utiCodeTransfer.getRiskType())||"Z".equals(utiCodeTransfer.getRiskType())||"Y".equals(utiCodeTransfer.getRiskType())){
				colLength = colLength - 2;
			}
		}
		// 单号的总长度
		intLength = iGroupNo.length() + colLength + strHeadID.length();
		if (iBillNo.length() >= intLength) {
			return iBillNo;
		}
		//CLM0116，處理人員：Sam，需求單編號：CLM0116..新核心-傷害險修改理賠序號 start*/
		if(iBillNo.matches("[+-]?\\d*(\\.\\d+)?")){
			iBillNo = String.valueOf(Integer.parseInt(iBillNo));
		}
		//CLM0116，處理人員：Sam，需求單編號：CLM0116..新核心-傷害險修改理賠序號 end*/
		intNoLength = iBillNo.length();
		intChgLength = colLength - intNoLength;
		iBillNo = StringUtils.newString("0", intChgLength) + iBillNo;
		iBillNo = strHeadID.trim() + iGroupNo.trim() + iBillNo.trim();
		return iBillNo;
	}

	/**
	 * 单号检查
	 * @param iTableName 数据表
	 * @param iBillNo 单号
	 * @param iGroupNo 组号
	 * @param iCheckFlag 检查类型
	 * @param damageCode 任意险出险原因
	 * @return true/false 单号检查成功（合法）/单号检查失败（不合法）
	 * @throws Exception
	 */
	public boolean checkNo(String iTableName, String iBillNo, String iGroupNo, String iCheckFlag, String iRiskCode,Map<String,Object>infoMap) throws Exception {
		UtiKey utiKey = null;
		PrpGroup prpGroup = null;
		int intCount = 0;
		boolean blnResult = false;
		String strFieldName = "", strWherePart = "";
		String strSql = "";
		String strGroupNo = "", strMaxNo = "", strBillNo = "";
		String[] strPickNo = new String[3];
		// 校验单号的合法性
		// utiKey = new UtiKey();
		utiKey = utiKeyService.findUtiKey(iTableName);
		if (utiKey == null) {
			blnResult = false;
			return blnResult;
		}
		// 将单号分离成单号头+分组+流水号
		strPickNo = pickNo(iTableName, iBillNo,infoMap);
		strGroupNo = strPickNo[1];
		strMaxNo = strPickNo[2];
		// 拉长单号
		iBillNo = pullNo(iTableName, iBillNo, strGroupNo, iRiskCode, infoMap);
		if (iCheckFlag.trim().equals("2")) {
			blnResult = true;
			return blnResult;
		}
		// 获取所有的分组
		strWherePart = "GroupNo ='" + strGroupNo.trim() + "'";
		QueryRule queryRule = QueryRule.getInstance();
		queryRule.addSql(strWherePart);
		List<PrpGroup> collection = prpGroupService.findPrpGroup(queryRule);
		strFieldName = utiKey.getFieldName();
		strSql = " SELECT COUNT(*) FROM " + iTableName.trim() + " WHERE " + strFieldName.trim() + "='" + iBillNo.trim() + "'";
		for (int i = 0; i < collection.size(); i++) {
			prpGroup = collection.get(i);
			strBillNo = strPickNo[0] + prpGroup.getId().getSubGroupNo().trim() + strMaxNo;
			strSql += " OR " + strFieldName.trim() + "='" + strBillNo.trim() + "'";
		}
		List<?> resultList = HibernateUtils.findbySql(getSession(), strSql);
		BigDecimal object = (BigDecimal) resultList.get(0);
		intCount = object.intValue();
		if (intCount > 1) {
			blnResult = false;
		} else if ((intCount == 1) && (iCheckFlag.trim().equals("0"))) {
			blnResult = false;
		} else if ((intCount == 0) && (iCheckFlag.trim().equals("1"))) {
			blnResult = false;
		} else if ((intCount == 0) && (iCheckFlag.trim().equals("2"))) {
			blnResult = false;
		} else {
			blnResult = true;
		}
		return blnResult;
	}

	/**
	 * 放回新单号
	 * @param strTableName 数据表名
	 * @param strBillNo 单号
	 * @return true/false 放号成功/放号失败
	 * @throws Exception,SQLException,UserException
	 */
	public boolean putNo(String iTableName, String iBillNo,Map<String,Object> infoMap) throws SQLException, Exception {
		String strGroupNo = "";
		String strMaxUse = "";
		String[] strMaxMinNo = new String[3];
		String[] strPickNo = new String[3];
		PrpMaxNo prpMaxNo = new PrpMaxNo();
		// 根据strBillNo的2~15位获取组号
		strPickNo = pickNo(iTableName, iBillNo,infoMap);
		strGroupNo = strPickNo[1];
		strMaxUse = strPickNo[2];
		strPickNo = prpMaxNoService.getMaxMinNo(strGroupNo, iTableName);
		if (strPickNo == null)
			return false;
		strMaxMinNo[0] = strPickNo[0];
		strMaxMinNo[1] = strPickNo[1];
		strMaxMinNo[2] = String.valueOf(strPickNo[2]);
		PrpMaxUseId prpMaxUseId = new PrpMaxUseId();
		prpMaxUseId.setGroupNo(strGroupNo);
		prpMaxUseId.setMaxNo(strMaxUse);
		prpMaxUseId.setTableName(iTableName);
		prpMaxUseService.delete(prpMaxUseId);
		if (Integer.parseInt(strMaxUse) + 1 == Integer.parseInt(strMaxMinNo[0])) {
			prpMaxNo = new PrpMaxNo();
			prpMaxNo.getId().setGroupNo(strGroupNo);
			prpMaxNo.getId().setTableName(iTableName);
			prpMaxNo.getId().setMaxNo(strMaxUse);
			PrpMaxNoId prpMaxNoId = new PrpMaxNoId();
			prpMaxNoId.setGroupNo(prpMaxNo.getId().getGroupNo());
			prpMaxNoId.setTableName(prpMaxNo.getId().getTableName());
			prpMaxNoId.setMaxNo(prpMaxNo.getId().getMaxNo());
			prpMaxNoService.delete(prpMaxNoId);
			prpMaxNoService.save(prpMaxNo);
		}
		if (Integer.parseInt(strMaxUse) + 1 < Integer.parseInt(strMaxMinNo[0])) {
			prpMaxNo = new PrpMaxNo();
			prpMaxNo.getId().setGroupNo(strGroupNo);
			prpMaxNo.getId().setTableName(iTableName);
			prpMaxNo.getId().setMaxNo(strMaxUse);
			prpMaxNo.setFlag("0");
			PrpMaxNoId prpMaxNoId = new PrpMaxNoId();
			prpMaxNoId.setGroupNo(prpMaxNo.getId().getGroupNo());
			prpMaxNoId.setTableName(prpMaxNo.getId().getTableName());
			prpMaxNoId.setMaxNo(prpMaxNo.getId().getMaxNo());
			prpMaxNoService.delete(prpMaxNoId);
			prpMaxNoService.save(prpMaxNo);
		}
		return true;
	}

	/**
	 * 成功後删除获取的单号
	 * @param strTableName 单号表名
	 * @param strBillNo 单证号
	 * @return true/false 删号成功/删号失败
	 * @throws Exception,SQLException,UserException
	 */
	public boolean deleteNo(String iTableName, String iBillNo,Map<String,Object> infoMap) throws SQLException, Exception {
		String strGroupNo = "";
		String strMaxUse = "";
		String[] strPickNo = new String[3];
		boolean blnResult = false;
		// 根据strBillNo的2~16位获取组号
		try {
			strPickNo = pickNo(iTableName, iBillNo,infoMap);
			strGroupNo = strPickNo[1];
			strMaxUse = strPickNo[2];
			// 删除maxuse中的最大号记录
			PrpMaxUseId prpMaxUseId = new PrpMaxUseId();
			prpMaxUseId.setGroupNo(strGroupNo);
			prpMaxUseId.setMaxNo(strMaxUse);
			prpMaxUseId.setTableName(iTableName);
			prpMaxUseService.delete(prpMaxUseId);
			blnResult = true;
		} catch (Exception ex) {
			blnResult = false;
			throw ex;
		} finally {

		}
		return blnResult;
	}

	/**
	 * 手工占号
	 * @param iTableName 单号表名
	 * @param iBillNo 单证号
	 * @return true/false 占号成功/占号失败
	 * @throws UserException
	 * @throws Exception
	 */
	public boolean occupy(String iTableName, String iBillNo,Map<String,Object> infoMap) throws SQLException, Exception {
		String strGroupNo = "";
		String strMaxNo = "";
		boolean blnResult = false;
		String[] strPickNo = new String[3];
		PrpMaxUse prpMaxUse = null;
		// 根据strBillNo的2~16位获取组号
		strPickNo = pickNo(iTableName, iBillNo,infoMap);
		strGroupNo = strPickNo[1];
		strMaxNo = strPickNo[2];
		// 向prpMaxUse表中插入最大的单号
		prpMaxUse = new PrpMaxUse();
		prpMaxUse.getId().setGroupNo(strGroupNo);
		prpMaxUse.getId().setTableName(iTableName);
		prpMaxUse.getId().setMaxNo(strMaxNo);
		prpMaxUse.setFlag("1");
		try {
			PrpMaxUseId prpMaxUseId = new PrpMaxUseId();
			prpMaxUseId.setGroupNo(strGroupNo);
			prpMaxUseId.setTableName(iTableName);
			prpMaxUseId.setMaxNo(strMaxNo);
			blnResult = true;
		} catch (Exception ex) {
			blnResult = false;
			throw ex;
		} finally {

		}
		return blnResult;
	}

	/**
	 * 获取组号
	 * @param iTableName 单号表名
	 * @param iRiskCode 险种代码
	 * @param iComCode 机构代码
	 * @param iYear 年份
	 * @return groupNo 组号
	 * @throws Exception
	 */
	public String getGroupNo(String iTableName, String iRiskCode, String iComCode, String iYear ,Map<String,Object>infoMap) throws Exception {
		String strGroupNo = "";
		// 根据单号规则获取单号编组
		strGroupNo = this.combineGroupNo(iTableName, iRiskCode, iComCode, iYear, infoMap);
		// 获取单号设定的实际编组
		strGroupNo = prpGroupService.getGroupNo(strGroupNo);
		return strGroupNo;
	}

	/**
	 * 获取组号
	 * @param iTableName 单号表名
	 * @param iRiskCode 险种代码
	 * @param iComCode 机构代码
	 * @param iYear 年份
	 * @param damageCode 任意险出险原因
	 * @return groupNo 组号
	 * @throws Exception
	 */
	public String combineGroupNo(String iTableName, String iRiskCode, String iComCode, String iYear,Map<String,Object>infoMap) throws Exception {
		String strGroupNo = "";
		iTableName = iTableName.toLowerCase();
		if (iTableName.trim().equalsIgnoreCase("fcorepolicy") || iTableName.trim().equalsIgnoreCase("fporeendor") || iTableName.trim().equalsIgnoreCase("florepay") || iTableName.trim().equalsIgnoreCase("fzacc")
				|| iTableName.trim().equalsIgnoreCase("fcirepolicy") || iTableName.trim().equalsIgnoreCase("fpireendor") || iTableName.trim().equalsIgnoreCase("flirepay")) {
			// 分保业务号：I/O+险类代码(1位)+业务年度+公司代码(4位)
			if (iTableName.trim().equalsIgnoreCase("fcirepolicy"))
				strGroupNo = "I" + iRiskCode.substring(0, 1) + iYear + iComCode.substring(0, 2);
			else
				strGroupNo = "O" + iRiskCode.substring(0, 1) + iYear + iComCode.substring(0, 2);
		} else if (iTableName.trim().equalsIgnoreCase("fjsettle")) {
			// 直接业务号:业务年度 + "R"
			strGroupNo = iYear + "R";
		} else if ("prplclaim".equals(iTableName)) {
			strGroupNo = this.getPrpLclaimGroupNo(iRiskCode,iComCode,iYear,infoMap);
		}else if("prplsms".equals(iTableName)){
			strGroupNo = getPrpLsmsGroupNo();
		}else {
			/**
			 * 報案號碼（各個位數含義）： 第1位：類型碼為6。 2-6位：險種代碼，剩餘為補零。 7-8位：出單公司代碼。
			 * 9-12位：全部為0。 13-14位：西元年後2碼，依保單 15-21位：序號從00001開始，任意險長度4碼，強制險5碼。
			 */
			String riskCode = this.getRiskCode(iRiskCode, infoMap);
			riskCode += StringUtils.newString("0", 5 - riskCode.length());
			strGroupNo = riskCode + iComCode.substring(iComCode.length() - 2, iComCode.length()) + "0000" + iYear.substring(iYear.length() - 2, iYear.length());
		}
		return strGroupNo;
	}

	/**
	 * 根据单号规则拆分（如果单号涉及编组，则拆分成编组后的组号）
	 * @param dbManager 数据库连接池
	 * @param iTableName 单号表名
	 * @param iRiskCode 单号
	 * @return pickNo[3] [0]号头 [1]组号 [2]流水号
	 * @throws Exception
	 */
	public String[] pickNo(String iTableName, String iBillNo,Map<String,Object> infoMap) throws UserException, SQLException, Exception {
		String[] strPickNo = new String[3];
		String strGroupNo = "";
		String strSerialNo = "";
		if (iTableName.trim().equalsIgnoreCase("fcorepolicy") || iTableName.trim().equalsIgnoreCase("fporeendor") || iTableName.trim().equalsIgnoreCase("florepay") || iTableName.trim().equalsIgnoreCase("fzacc")) {
			if (iBillNo.length() == 16) {
				strGroupNo = iBillNo.substring(1, 10);
				strSerialNo = iBillNo.substring(10);
			}
		} else if (iTableName.trim().equalsIgnoreCase("fjsettle")) {
			// 直接业务号:业务年度 + "R"
			if (iBillNo.length() == 8) {
				strGroupNo = iBillNo.substring(0, 4);
				strSerialNo = iBillNo.substring(4, 7);
			}
		} else if ("prplclaim".equals(iTableName)){
			int groupLen = 10;
			String policyNo = (String) infoMap.get("policyNo");
			PrpCmain prpCmain = prpCmainService.findByPrimaryKey(policyNo);
			if(prpCmain!=null){
				UtiCodeTransfer utiCodeTransfer = utiCodeTransferService.findUtiCodeTransfer(prpCmain.getRiskCode());
				if("Y".equals(utiCodeTransfer.getRiskType())){
					if(ConstantCodes.CARGO_RISKCODE.contains(prpCmain.getRiskCode())){
						groupLen = 9;//货运险8码
					}else{
						groupLen = 10;// 非货运险9码
					}
				}else if("D".equals(utiCodeTransfer.getRiskType())){
					groupLen = 9;
				}else if("E".equals(utiCodeTransfer.getRiskType())||"G".equals(utiCodeTransfer.getRiskType())||"Z".equals(utiCodeTransfer.getRiskType())){
					groupLen = 11;
				}
			}
			strGroupNo = iBillNo.substring(0,groupLen);
			strSerialNo = iBillNo.substring(groupLen);
			strPickNo[0] = "";
		}else if("prplsms".equals(iTableName)){
			strGroupNo = iBillNo.substring(0, 11);
			strSerialNo = iBillNo.substring(11);
			strPickNo[0] = "";
		}else {
			strGroupNo = iBillNo.substring(1, 14);
			strSerialNo = iBillNo.substring(14);
			strPickNo[0] = iBillNo.substring(0, 1);
		}
		strGroupNo = prpGroupService.getGroupNo(strGroupNo);
		strPickNo[1] = strGroupNo;
		strPickNo[2] = strSerialNo;
		return strPickNo;
	}

	/**
	 * 获取险种代码，强制险，任意险种 AL-任意險/ BL-強制險/ TL-任意險失竊車
	 * 非车险，直接用riskCode
	 * @param riskCode
	 * @param damageCode 任意险出险原因
	 * @return
	 * @throws Exception
	 */
	public String getRiskCode(String policyNo, Map<String,Object>infoMap) throws Exception {
		String codeType = null;
		PrpCmain prpCmain = prpCmainService.findByPrimaryKey(policyNo);
		if(prpCmain!=null){
			if("D".equals(ConstantCodes.carClassMap.get(prpCmain.getRiskCode()))){
				codeType = "AL";
				List<PrpCitemKind> prpCitemKinds = prpCitemKindService.findByConditions("policyno = '" + policyNo + "'");
				if (!CommonUtils.isEmpty(prpCitemKinds)) {
					for (PrpCitemKind prpCitemKind : prpCitemKinds) {
						String kindCode = prpCitemKind.getKindCode();
						if (getBLKindList().contains(kindCode)) {
							codeType = "BL";
							break;
						}else if(getTLKindList().contains(kindCode)) {
							codeType = "TL";
							break;
						}
					}
				}
				if("TL".equals(codeType)&&infoMap!=null){
					String damageCode = (String) infoMap.get("damageCode");
					if(!getDamageCodeTLList().contains(damageCode)){
						codeType = "AL";
					}
				}
			}else{
				codeType = prpCmain.getRiskCode();
			}
		}else{
			codeType = policyNo;
		}
		return codeType;
	}
	/**生成立案号的组号
	 * @param policyNo
	 * @param iComCode
	 * @param iYear
	 * @param infoMap
	 * @return
	 * @throws Exception
	 */
	public String getPrpLclaimGroupNo(String policyNo, String iComCode, String iYear,Map<String,Object>infoMap)throws Exception{
		/**车险
		 * n 立案號碼（各個位數含義）： u 1-2位：台壽保公司代碼。 u 3-4位：出單公司代碼。 u
		 * 5-6位：西元年後2碼，依保單年度。 u 7-8位：險種事故分類（AL-任意險/BL-強制險/TL-任意險失竊車）。 u
		 * 9位：新舊系統識別碼：400系統為1，新核心系統為0。 u 10-14位：序號從00001開始，任意險長度4碼，強制險5碼。
		 * //立案号的生成规则不一样，台壽保編碼規則
		 * 非车险编码规则
		 * 火险
		 * u 1-2位：台壽保公司代碼,u 3-4位：出單公司代碼。 5-6位：受理/備案     年度，西元年後2碼,
		 * 7,8位:出險主險種識別，分別有LF/AF/SF,9位:H
		 * 意键险
		 * u 1-2位：台壽保公司代碼,u 3-4位：出單單位代碼 (同保單編碼第 3 ; 4 碼), 5-6位：受理/備案    7-8位： 年度,出險主險種識別， (同保單編碼第 7 ; 8 碼)
		 * 9位：理賠件固定碼 "L" 10：識別碼 (同保單編碼第 9 碼)
		 * 工程險、責任險
		 * u 1-2位：公司代碼 (同保單編碼第 1 ; 2 碼)，u 3-4位：出單單位代碼 (同保單編碼第 3 ; 4 碼)，5-6位：受理/備案     年度，西元年後2碼	7-8位： 出險主險種識別， (同保單編碼第 7 ; 8 碼)
		 *	9位：理賠件固定碼 "L"	，10： 識別碼 (同保單編碼第 9 碼)
		 * 水险  - 非货物运输险
		 * u 1-2位：公司代碼 (同保單編碼第 1 ; 2 碼)，u 3-4位：出單單位代碼 (同保單編碼第 3 ; 4 碼)5-6位：出单年度，西元年後2碼， 7-8位： 出險主險種識別， (同保單編碼第 7 ; 8 碼)
		 * 9位：理賠件固定碼 "L"
		 * 水险 - 货物运输险
		 * u 1-2位：公司代碼 (同保單編碼第 1 ; 2 碼)，u 3-4位：出單單位代碼 (同保單編碼第 3 ; 4 碼)5-6位：出单年度，西元年後2碼， 7-8位： ML
		 */
		String strGroupNo = null;
		PrpCmain prpCmain = prpCmainService.findByPrimaryKey(policyNo);
		UtiCodeTransfer utiCodeTransfer = utiCodeTransferService.findUtiCodeTransfer(prpCmain.getRiskCode());
		if("D".equals(utiCodeTransfer.getRiskType())){
			//车险
			strGroupNo = "18" + iComCode.substring(iComCode.length() - 2, iComCode.length()) + iYear.substring(iYear.length() - 2, iYear.length()) + this.getRiskCode(policyNo, infoMap) + "0";
		}else if("Q".equals(utiCodeTransfer.getRiskType())){
			DateTime startDate = new DateTime(new DateTime(prpCmain.getStartDate()).addYear(1).toString()+" "+prpCmain.getStartHour(),DateTime.YEAR_TO_HOUR);
			DateTime endDate = new DateTime(new DateTime(prpCmain.getEndDate()).toString()+" "+prpCmain.getEndHour(),DateTime.YEAR_TO_HOUR);
			if(endDate.after(startDate)){
				strGroupNo ="LF";
			}else{
				if(policyNo.length()>8){
					strGroupNo = policyNo.substring(6,8);
				}
				if("R0".equals(strGroupNo)||"F0".equals(strGroupNo)){
					strGroupNo = "SF";
				}else{
					strGroupNo = "AF";
				}
			}
			
			strGroupNo += "H";
//			if("3".equals(prpCmain.getCoinsFlag())){
//				strGroupNo +="I";
//			}else{
//				strGroupNo +="0";
//			}
			strGroupNo = "18" + iComCode.substring(iComCode.length() - 2, iComCode.length()) + iYear.substring(iYear.length() - 2, iYear.length())+strGroupNo+"0";
			//火险
		}else if("E".equals(utiCodeTransfer.getRiskType())||"G".equals(utiCodeTransfer.getRiskType())||"Z".equals(utiCodeTransfer.getRiskType())){
			//意键险
			char []cPolicyNo = policyNo.toCharArray();
			//CLM0116，處理人員：BL061 張明財，需求單編號：CLM0116..新核心-傷害險修改理賠序號 start
			//CLM0114，處理人員：BL061 張明財，需求單編號：CLM0114.新核心-傷害險網投修改理賠序號 start
			//String strRiskCode = ""+cPolicyNo[6]+cPolicyNo[7]+cPolicyNo[8];
			//if ("HPW".equals(strRiskCode)){ */
			String strRiskCode = ""+cPolicyNo[6]+cPolicyNo[7];
			if ("HP".equals(strRiskCode)){	
			//CLM0116，處理人員：BL061 張明財，需求單編號：CLM0116..新核心-傷害險修改理賠序號 end	
				strGroupNo = "18" + cPolicyNo[2]+cPolicyNo[3] + iYear.substring(iYear.length() - 2, iYear.length())+cPolicyNo[6]+cPolicyNo[7]+"L"+cPolicyNo[8];
			}
			else {
				//CLM0114，處理人員：BL061 張明財，需求單編號：CLM0114.新核心-傷害險網投修改理賠序號  end
			    strGroupNo = "18" + cPolicyNo[2]+cPolicyNo[3] + iYear.substring(iYear.length() - 2, iYear.length())+cPolicyNo[6]+cPolicyNo[7]+"L"+cPolicyNo[8]+"5";
			}
		}else if("Y".equals(utiCodeTransfer.getRiskType())){
			//水险
			char []cPolicyNo = policyNo.toCharArray();
			//货物运输险
			if(ConstantCodes.CARGO_RISKCODE.contains(prpCmain.getRiskCode())){
				strGroupNo = "18" + cPolicyNo[2]+cPolicyNo[3] + cPolicyNo[4]+cPolicyNo[5]+"ML5";
			}else{
				strGroupNo = "18" + cPolicyNo[2]+cPolicyNo[3] + cPolicyNo[4]+cPolicyNo[5]+cPolicyNo[6]+cPolicyNo[7]+"L5";
			}
		}else{
			strGroupNo = "18" + iComCode.substring(iComCode.length() - 2, iComCode.length()) + iYear.substring(iYear.length() - 2, iYear.length()) + this.getRiskCode(policyNo, infoMap) + "L05";
		}
		return strGroupNo;
	}
	
	/**生成立案号的组号
	 * @param policyNo
	 * @param iComCode
	 * @param iYear
	 * @param infoMap
	 * @return
	 * @throws Exception
	 */
	public String getPrpLsmsGroupNo()throws Exception{
		DateTime dateTime = DateTime.current();
		dateTime = dateTime.addYear(-ConstantCodes.YEAROFFSET);
		DateFormat format = new SimpleDateFormat("yyyMMddHHmm");
		return format.format(dateTime);
	}
	
	/***
	 * 变量初始化
	 */
	private static void init() {
		try {
			String citemKindBL = AppConfig.get("sysconst.Kind_BL");
			String citemKindTL = AppConfig.get("sysconst.Kind_TL");
			String damageCode_TL = AppConfig.get("sysconst.DamageCode_TL");
			BLKindList.addAll(Arrays.asList(citemKindBL.split(",")));
			TLKindList.addAll(Arrays.asList(citemKindTL.split(",")));
			DamageCodeTLList.addAll(Arrays.asList(damageCode_TL.split(",")));
			init = true;
		} catch (Exception e) {
			init = false;
			System.err.println("险别配置項：sysconst.Kind_BL,sysconst.Kind_TL和sysconst.DamageCode_TL 配置異常！");
			e.printStackTrace();
		}
	}

	public PrpLcompensateService getPrpLcompensateService() {
		return prpLcompensateService;
	}

	public void setPrpLcompensateService(PrpLcompensateService prpLcompensateService) {
		this.prpLcompensateService = prpLcompensateService;
	}

	public PrpMaxNoService getPrpMaxNoService() {
		return prpMaxNoService;
	}

	public void setPrpMaxNoService(PrpMaxNoService prpMaxNoService) {
		this.prpMaxNoService = prpMaxNoService;
	}

	public UtiKeyService getUtiKeyService() {
		return utiKeyService;
	}

	public void setUtiKeyService(UtiKeyService utiKeyService) {
		this.utiKeyService = utiKeyService;
	}

	public PrpGroupService getPrpGroupService() {
		return prpGroupService;
	}

	public void setPrpGroupService(PrpGroupService prpGroupService) {
		this.prpGroupService = prpGroupService;
	}

	public PrpMaxUseService getPrpMaxUseService() {
		return prpMaxUseService;
	}

	public void setPrpMaxUseService(PrpMaxUseService prpMaxUseService) {
		this.prpMaxUseService = prpMaxUseService;
	}

	public PrpCmainService getPrpCmainService() {
		return prpCmainService;
	}

	public void setPrpCmainService(PrpCmainService prpCmainService) {
		this.prpCmainService = prpCmainService;
	}

	public PrpCitemKindService getPrpCitemKindService() {
		return prpCitemKindService;
	}

	public void setPrpCitemKindService(PrpCitemKindService prpCitemKindService) {
		this.prpCitemKindService = prpCitemKindService;
	}

	public UtiCodeTransferService getUtiCodeTransferService() {
		return utiCodeTransferService;
	}

	public void setUtiCodeTransferService(UtiCodeTransferService utiCodeTransferService) {
		this.utiCodeTransferService = utiCodeTransferService;
	}

	public PrpLregistService getPrpLregistService() {
		return prpLregistService;
	}

	public void setPrpLregistService(PrpLregistService prpLregistService) {
		this.prpLregistService = prpLregistService;
	}
	public static List<String> getBLKindList() {
		if(!init){
			init();
		}
		return BLKindList;
	}

	public static void setBLKindList(List<String> bLKindList) {
		BLKindList = bLKindList;
	}

	public static List<String> getTLKindList() {
		if(!init){
			init();
		}
		return TLKindList;
	}

	public static void setTLKindList(List<String> tLKindList) {
		TLKindList = tLKindList;
	}

	public static List<String> getDamageCodeTLList() {
		if(!init){
			init();
		}
		return DamageCodeTLList;
	}

	public static void setDamageCodeTLList(List<String> damageCodeTLList) {
		DamageCodeTLList = damageCodeTLList;
	}
	
	//CLM0116，處理人員：Sam，需求單編號：CLM0116..新核心-傷害險修改理賠序號 start*/
		public static String genNextNum(String s) throws Exception{
			try{
				int nextNum = new Integer(s);
				if(nextNum < 9999 ){
					nextNum ++;
				}else{
					throw new Exception("超出9999走下列轉換程式");
				}
				return nextNum+"";
			}catch(Exception e){
				
			}
			if("ZZ99".equals(s)){
				throw new Exception("已無法再取號");
			}
			String newStr = "";
			String chTmp = "";
			String numTmp = "";
			char c[] = s.toCharArray();
			//ascii 0~9 = 48~57 A~Z = 65~90
			int numberIndex = 0;
			for(int i = 0 ; i < c.length ; i++){
				int ascii = (int) c[i];
				if(ascii < 58){//是數字
					numTmp = numTmp + c[i];
				}else{
					numberIndex = i+1; 
					chTmp = chTmp + c[i];
				}
			}
			String tempNextValue = s.substring(numberIndex);
			int nextValue = new Integer(tempNextValue);
			if(nextValue == 9999 || nextValue == 999 || nextValue == 99 ){
				 if(tempNextValue.length() == 4){// only 9999
					 return "A001";
				 }else{
					 String numberWord = "";
					 if(numberIndex > 1 ){
						 numberWord = s.substring( 0 , numberIndex - 1);
					 }else{
						 if(nextValue < 999){
							 nextValue++;
							 if(nextValue < 100){
								 return s.substring(0 , numberIndex)+"0"+nextValue;	 
							 }else{
								 return s.substring(0 , numberIndex)+nextValue;
							 }
						 }
					 }
					 String newtWord = s.substring(numberIndex - 1 , numberIndex);
//					 System.out.println("newtWord = " + newtWord);
					 char c1[] = newtWord.toCharArray();
					 //ascii 0~9 = 48~57 A~Z = 65~90
					 int ascii = (int) c1[0];
					 if(ascii < 90){//Z以前
						 if("".equals(numberWord)){//Z999
							 return numberWord+ String.valueOf(Character.toString ((char) (ascii + 1)))+"001";
						 }else{
							 return numberWord+ String.valueOf(Character.toString ((char) (ascii + 1)))+"01";
						 }
					 }else{
						 if("".equals(numberWord)){//Z999
							 return "AA01";
						 }else{//?Z99 要進位
							 char c2[] = numberWord.toCharArray();
							 numberWord = String.valueOf(Character.toString ((char) ((int) c2[0] + 1)));
							 return numberWord+"A01";
						 }
					 }
				 }
			}else{
				nextValue++;
				newStr = nextValue+"";
				while((newStr.length()+ numberIndex) < 4){
					newStr = "0"+newStr;
				}
				return s.substring(0,numberIndex)+newStr;
			}
		}
		//CLM0116，處理人員：Sam，需求單編號：CLM0116..新核心-傷害險修改理賠序號 end*/
}
