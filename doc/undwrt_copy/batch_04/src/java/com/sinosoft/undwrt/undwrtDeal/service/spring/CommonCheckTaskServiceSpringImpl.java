package com.sinosoft.undwrt.undwrtDeal.service.spring;

import ins.framework.dao.GenericDaoHibernate;

import java.sql.ResultSet;//mantis： LIA0348，處理人員：DP0706，需求單編號：LIA0348_稽核議題處理
import java.sql.SQLException;//mantis： LIA0348，處理人員：DP0706，需求單編號：LIA0348_稽核議題處理
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;//mantis： LIA0348，處理人員：DP0706，需求單編號：LIA0348_稽核議題處理
import java.util.List;
import java.util.Map;//mantis： LIA0348，處理人員：DP0706，需求單編號：LIA0348_稽核議題處理

import com.sinosoft.common.schema.model.PrpTmain;
import com.sinosoft.prpall.dto.domain.PrpLclaimDto;
import com.sinosoft.prpall.dto.domain.PrpLcompensateDto;
import com.sinosoft.prpall.dto.domain.PrpLprepayDto;
import com.sinosoft.prpall.resource.dtofactory.domain.DBPrpLclaim;
import com.sinosoft.prpall.resource.dtofactory.domain.DBPrpLcompensate;
import com.sinosoft.prpall.resource.dtofactory.domain.DBPrpLprepay;
import com.sinosoft.reins.common.model.Prpduser;
import com.sinosoft.sysframework.reference.DBManager;
import com.sinosoft.undwrt.common.model.Prpdagent;
import com.sinosoft.undwrt.undwrtBase.model.PrpCPnote;
import com.sinosoft.undwrt.undwrtBase.model.PrpTnote;
import com.sinosoft.undwrt.undwrtBase.service.facade.WfLogService;
import com.sinosoft.undwrt.undwrtDeal.service.facade.CommonCheckTaskService;
import com.sinosoft.undwrt.undwrtDeal.vo.PolicyAbstractInfoVo;
import com.sinosoft.undwrt.undwrtDeal.vo.ZHInfoVo;

/**
 * 核保服務實現類.
 */
public class CommonCheckTaskServiceSpringImpl extends GenericDaoHibernate implements CommonCheckTaskService {

	/** 屬性工作流日誌接口. */
	private WfLogService wfLogService;

	/**
	 * 獲取保書簡要訊息.
	 * 
	 * @param businessNo
	 *            業務號
	 * @return 保書信息類
	 * @throws Exception
	 *             異常
	 * @see com.sinosoft.undwrt.undwrtDeal.service.facade.CommonCheckTaskService#getPolicyAbstractInfo(java.lang.String)
	 */
	public PolicyAbstractInfoVo getPolicyAbstractInfo(String businessNo)
			throws Exception {
		/*
		 * DBManager dbManager = new DBManager();
		 * dbManager.open("undwrtDataSource"); WfLogVo wfLogDto = new WfLogVo();
		 * Collection wfLogList = new ArrayList(); String strCertiType = "";
		 * QueryRule queryRule = QueryRule.getInstance();
		 * queryRule.addEqual("businessNo", businessNo); wfLogList =
		 * wfLogService.findByQueryRuleList(queryRule);
		 * 
		 * Iterator itwflog = wfLogList.iterator(); if (itwflog.hasNext()) {
		 * wfLogDto = (WfLogVo) itwflog.next(); strCertiType =
		 * wfLogDto.getBusinessType();
		 * 
		 * } String conditions = null; PolicyAbstractInfoVo
		 * policyAbstractInfoDto = new PolicyAbstractInfoVo(); if
		 * (strCertiType.equals("C")) { DBPrpLcompensate dbPrpLcompensate = new
		 * DBPrpLcompensate(dbManager); PrpLcompensateDto prpLcompensateDto =
		 * dbPrpLcompensate .findByPrimaryKey(businessNo);
		 * 
		 * policyAbstractInfoDto.setSumLoss(String.valueOf(prpLcompensateDto
		 * .getSumDutyPaid()));
		 * policyAbstractInfoDto.setSumPaid(String.valueOf(prpLcompensateDto
		 * .getSumPaid()));
		 * policyAbstractInfoDto.setOther(String.valueOf(prpLcompensateDto
		 * .getSumNoDutyFee())); DBPrpLcharge dbPrpLcharge = new
		 * DBPrpLcharge(dbManager); conditions = "CompensateNo='" + businessNo +
		 * "' and ChargeCode='11'"; // 共损/救助
		 * policyAbstractInfoDto.setSumSumRealPay11(dbPrpLcharge
		 * .selectSumSumRealPayByConditions(conditions)); conditions =
		 * "CompensateNo='" + businessNo + "' and ChargeCode='03'"; // 施救费
		 * policyAbstractInfoDto.setSumSumRealPay03(dbPrpLcharge
		 * .selectSumSumRealPayByConditions(conditions)); conditions =
		 * "CompensateNo='" + businessNo + "' and ChargeCode='04'"; // 查勘费
		 * policyAbstractInfoDto.setSumSumRealPay04(dbPrpLcharge
		 * .selectSumSumRealPayByConditions(conditions)); conditions =
		 * "CompensateNo='" + businessNo + "' and ChargeCode='13'"; // 公估费
		 * policyAbstractInfoDto.setSumSumRealPay13(dbPrpLcharge
		 * .selectSumSumRealPayByConditions(conditions)); conditions =
		 * "CompensateNo='" + businessNo + "' and ChargeCode='09'"; // 代理费
		 * policyAbstractInfoDto.setSumSumRealPay09(dbPrpLcharge
		 * .selectSumSumRealPayByConditions(conditions)); if
		 * (StringUtils.trimToEmpty(policyAbstractInfoDto.getSumLoss())
		 * .length() == 0) { policyAbstractInfoDto.setSumLoss("0"); } if
		 * (StringUtils.trimToEmpty(policyAbstractInfoDto.getSumPaid())
		 * .length() == 0) { policyAbstractInfoDto.setSumPaid("0"); } if
		 * (StringUtils.trimToEmpty(
		 * policyAbstractInfoDto.getSumSumRealPay03()).length() == 0) {
		 * policyAbstractInfoDto.setSumSumRealPay03("0"); } if
		 * (StringUtils.trimToEmpty(
		 * policyAbstractInfoDto.getSumSumRealPay11()).length() == 0) {
		 * policyAbstractInfoDto.setSumSumRealPay11("0"); } if
		 * (StringUtils.trimToEmpty(
		 * policyAbstractInfoDto.getSumSumRealPay04()).length() == 0) {
		 * policyAbstractInfoDto.setSumSumRealPay04("0"); } if
		 * (StringUtils.trimToEmpty(
		 * policyAbstractInfoDto.getSumSumRealPay13()).length() == 0) {
		 * policyAbstractInfoDto.setSumSumRealPay13("0"); } if
		 * (StringUtils.trimToEmpty(
		 * policyAbstractInfoDto.getSumSumRealPay09()).length() == 0) {
		 * policyAbstractInfoDto.setSumSumRealPay09("0"); } double doubleSumLoss
		 * = new Double( policyAbstractInfoDto.getSumLoss()).doubleValue();
		 * double doubleSumPaid = new Double(
		 * policyAbstractInfoDto.getSumPaid()).doubleValue(); double double11 =
		 * new Double(
		 * policyAbstractInfoDto.getSumSumRealPay11()).doubleValue(); double
		 * double03 = new Double(
		 * policyAbstractInfoDto.getSumSumRealPay03()).doubleValue(); double
		 * double04 = new Double(
		 * policyAbstractInfoDto.getSumSumRealPay04()).doubleValue(); double
		 * double13 = new Double(
		 * policyAbstractInfoDto.getSumSumRealPay13()).doubleValue(); double
		 * double09 = new Double(
		 * policyAbstractInfoDto.getSumSumRealPay09()).doubleValue(); } else if
		 * (strCertiType.equals("Y")) { DBPrpLprepay dbPrpLprepay = new
		 * DBPrpLprepay(dbManager); PrpLprepayDto prpLprepayDto = dbPrpLprepay
		 * .findByPrimaryKey(businessNo);
		 * 
		 * // 按照客户需求，保品金额显示本次赔付总和
		 * policyAbstractInfoDto.setSumPaid(String.valueOf(prpLprepayDto
		 * .getSumPrePaid())); // lijibin add 20050827 bug21033 其他费用金额不对
		 * DBPrpLcharge dbPrpLcharge = new DBPrpLcharge(dbManager); conditions =
		 * "CompensateNo='" + businessNo + "' and ChargeCode='11'"; // 共损/救助
		 * policyAbstractInfoDto.setSumSumRealPay11(dbPrpLcharge
		 * .selectSumSumRealPayByConditions(conditions)); conditions =
		 * "CompensateNo='" + businessNo + "' and ChargeCode='03'"; // 施救费
		 * policyAbstractInfoDto.setSumSumRealPay03(dbPrpLcharge
		 * .selectSumSumRealPayByConditions(conditions)); conditions =
		 * "CompensateNo='" + businessNo + "' and ChargeCode='04'"; // 查勘费
		 * policyAbstractInfoDto.setSumSumRealPay04(dbPrpLcharge
		 * .selectSumSumRealPayByConditions(conditions)); conditions =
		 * "CompensateNo='" + businessNo + "' and ChargeCode='13'"; // 公估费
		 * policyAbstractInfoDto.setSumSumRealPay13(dbPrpLcharge
		 * .selectSumSumRealPayByConditions(conditions)); conditions =
		 * "CompensateNo='" + businessNo + "' and ChargeCode='09'"; // 代理费
		 * policyAbstractInfoDto.setSumSumRealPay09(dbPrpLcharge
		 * .selectSumSumRealPayByConditions(conditions)); if
		 * (StringUtils.trimToEmpty(policyAbstractInfoDto.getSumLoss())
		 * .length() == 0) { policyAbstractInfoDto.setSumLoss("0"); } if
		 * (StringUtils.trimToEmpty(policyAbstractInfoDto.getSumPaid())
		 * .length() == 0) { policyAbstractInfoDto.setSumPaid("0"); } if
		 * (StringUtils.trimToEmpty(
		 * policyAbstractInfoDto.getSumSumRealPay03()).length() == 0) {
		 * policyAbstractInfoDto.setSumSumRealPay03("0"); } if
		 * (StringUtils.trimToEmpty(
		 * policyAbstractInfoDto.getSumSumRealPay11()).length() == 0) {
		 * policyAbstractInfoDto.setSumSumRealPay11("0"); } if
		 * (StringUtils.trimToEmpty(
		 * policyAbstractInfoDto.getSumSumRealPay04()).length() == 0) {
		 * policyAbstractInfoDto.setSumSumRealPay04("0"); } if
		 * (StringUtils.trimToEmpty(
		 * policyAbstractInfoDto.getSumSumRealPay13()).length() == 0) {
		 * policyAbstractInfoDto.setSumSumRealPay13("0"); } if
		 * (StringUtils.trimToEmpty(
		 * policyAbstractInfoDto.getSumSumRealPay09()).length() == 0) {
		 * policyAbstractInfoDto.setSumSumRealPay09("0"); } double doubleSumLoss
		 * = new Double( policyAbstractInfoDto.getSumLoss()).doubleValue();
		 * double doubleSumPaid = new Double(
		 * policyAbstractInfoDto.getSumPaid()).doubleValue(); double double11 =
		 * new Double(
		 * policyAbstractInfoDto.getSumSumRealPay11()).doubleValue(); double
		 * double03 = new Double(
		 * policyAbstractInfoDto.getSumSumRealPay03()).doubleValue(); double
		 * double04 = new Double(
		 * policyAbstractInfoDto.getSumSumRealPay04()).doubleValue(); double
		 * double13 = new Double(
		 * policyAbstractInfoDto.getSumSumRealPay13()).doubleValue(); double
		 * double09 = new Double(
		 * policyAbstractInfoDto.getSumSumRealPay09()).doubleValue(); } return
		 * policyAbstractInfoDto;
		 */
		return null;
	}

	/**
	 * 獲取立案號.
	 * 
	 * @param busiNo
	 *            業務號
	 * @param busiType
	 *            業務類型
	 * @return 立案號
	 * @throws Exception
	 *             異常
	 * @see com.sinosoft.undwrt.undwrtDeal.service.facade.CommonCheckTaskService#getClaimNo(java.lang.String,
	 *      java.lang.String)
	 */
	public String getClaimNo(String busiNo, String busiType) throws Exception {
		String claimNo = null;
		DBManager dbManager = new DBManager();
		try {
			dbManager.open("undwrtDataSource");
			if ("C".equals(busiType))// 计算书号
			{
				DBPrpLcompensate dbPrpLcompensate = new DBPrpLcompensate(
						dbManager);
				PrpLcompensateDto prpLcompensateDto = dbPrpLcompensate
						.findByPrimaryKey(busiNo);
				claimNo = prpLcompensateDto.getClaimNo();
			} else if ("Y".equals(busiType))// 预赔号
			{
				DBPrpLprepay dbPrpLprepay = new DBPrpLprepay(dbManager);
				PrpLprepayDto prpLprepayDto = dbPrpLprepay
						.findByPrimaryKey(busiNo);
				claimNo = prpLprepayDto.getClaimNo();
			}
		} finally {
			dbManager.close();
		}
		return claimNo;
	}

	/**
	 * 獲取報案號.
	 * 
	 * @param claimNo
	 *            立案號
	 * @return 報案號
	 * @throws Exception
	 *             異常
	 * @see com.sinosoft.undwrt.undwrtDeal.service.facade.CommonCheckTaskService#getRegistNo(java.lang.String)
	 */
	public String getRegistNo(String claimNo) throws Exception {
		String registNo = null;
		DBManager dbManager = new DBManager();
		try {
			dbManager.open("undwrtDataSource");
			DBPrpLclaim dbPrpLclaim = new DBPrpLclaim(dbManager);
			PrpLclaimDto prpLclaimDto = dbPrpLclaim.findByPrimaryKey(claimNo);
			registNo = prpLclaimDto.getRegistNo();
		} finally {
			dbManager.close();
		}
		return registNo;
	}

	/**
	 * 獲取屬性工作流日誌接口.
	 * 
	 * @return 屬性工作流日誌接口的值
	 */
	public WfLogService getWfLogService() {
		return wfLogService;
	}

	/**
	 * 設置屬性工作流日誌接口.
	 * 
	 * @param wfLogService
	 *            待設置的工作流日誌接口的值
	 */
	public void setWfLogService(WfLogService wfLogService) {
		this.wfLogService = wfLogService;
	}

	public List<PrpTnote> getPrpTnotelist(String businessNo) throws Exception {
		// TODO Auto-generated method stub
		List<PrpTnote> ls = this.findByHql("from PrpTnote where proposalNo=?", businessNo);
		return ls;
	}

	/**
	 *	
	 * @param businessType
	 * @param businessNo
	 * @return
	 * @throws Exception
	 *	
	 * @see com.sinosoft.undwrt.undwrtDeal.service.facade.CommonCheckTaskService#getZHInfoVolist(java.lang.String, java.lang.String)
	 */
	@Override
	public List<ZHInfoVo> getZHInfoVolist(String businessType, String businessNo) throws Exception {
		// TODO Auto-generated method stub
		List<ZHInfoVo> zhList = new ArrayList<ZHInfoVo>();
		if("T".equals(businessType)){
			List<PrpTnote> prpTnoteList = this.findByHql("from com.sinosoft.undwrt.undwrtBase.model.PrpTnote where proposalNo=?", businessNo);
			for(int i=0;i<prpTnoteList.size();i++){
				ZHInfoVo zh = new ZHInfoVo();
				zh.setZhNo(String.valueOf(prpTnoteList.get(i).getId().getSerialNo()));
				zh.setInsuredName(prpTnoteList.get(i).getInsuredName());
				zh.setZhCode(prpTnoteList.get(i).getNoteCode());
				zh.setZhText(prpTnoteList.get(i).getNoteName());
				zh.setZhDate(prpTnoteList.get(i).getNoteDate());
				zh.setFirstDate(prpTnoteList.get(i).getReplyDate());
				zh.setSecondDate(prpTnoteList.get(i).getSecondReplyDate());
				zh.setThirdDate(prpTnoteList.get(i).getThirdReplyDate());
				zh.setDealStatus(prpTnoteList.get(i).getFlag());
				zh.setReplyDate(prpTnoteList.get(i).getActualReplyDate());
				zhList.add(zh);
			}
		}else if ("E".equals(businessType)){
			List<PrpCPnote> prpCPnoteList = this.findByHql("from com.sinosoft.undwrt.undwrtBase.model.PrpCPnote where policyNo=?", businessNo);
			for(int i=0;i<prpCPnoteList.size();i++){
				ZHInfoVo zh = new ZHInfoVo();
				zh.setZhNo(String.valueOf(prpCPnoteList.get(i).getId().getSerialNo()));
				zh.setInsuredName(prpCPnoteList.get(i).getInsuredName());
				zh.setZhCode(prpCPnoteList.get(i).getNoteCode());
				zh.setZhText(prpCPnoteList.get(i).getNoteName());
				zh.setZhDate(prpCPnoteList.get(i).getNoteDate());
				zh.setFirstDate(prpCPnoteList.get(i).getReplyDate());
				zh.setSecondDate(prpCPnoteList.get(i).getSecondReplyDate());
				zh.setThirdDate(prpCPnoteList.get(i).getThirdReplyDate());
				zh.setDealStatus(prpCPnoteList.get(i).getFlag());
				zh.setReplyDate(prpCPnoteList.get(i).getActualReplyDate());
				zhList.add(zh);
			}
		}
		
		
		return zhList;
	}
	
	//mantis： CAR0051 ，處理人員： David ，需求單編號： CAR0051   start 原因  業務人員失效減核問題-核保系統檢核
	/**
	 * 檢核業務人員之有效性
	 * 
	 * @param handlerIdentifyNumber
	 *            
	 */
	@Override
	public boolean checkValidsTatus(PrpTmain prpTmain) {
		//mantis： LIA0195 ，處理人員： Dp0706 ，需求單編號： LIA0195核保檢核業務人員資格問題START
//		請將通路別的檢核改為"1%"才檢核。
		//當通路來源為 21 22 23 31 32 時不檢核業務人員
//		String[] chans = {"21","22","23","31","32"};
//		if(!Arrays.asList(chans).contains(prpTmain.getChannelType())){
		if(prpTmain.getChannelType() != null && prpTmain.getChannelType().startsWith("1")){
			String handlerIdentifyNumber = prpTmain.getHandlerIdentifyNumber();
			String hql = "from com.sinosoft.undwrt.common.model.Prpdagent where VALIDSTATUS != 0 and IdentifyNumber = ?";
			List<Prpdagent> list = super.findByHql(hql, handlerIdentifyNumber);
			return list != null && list.size() > 0;
		}else{
			return true;
		}
		//mantis： LIA0195 ，處理人員： Dp0706 ，需求單編號： LIA0195核保檢核業務人員資格問題END
	}
	
	/**
	 * 檢核服務人員之有效性
	 * 
	 * @param handlerIdentifyNumber
	 *            
	 */
	@Override
	public boolean checkPrpduser(String userCode) {
		String hql = "from com.sinosoft.undwrt.common.model.PrpDuser where validstatus != 0 and usercode  = ?";
		List<Prpduser> list = super.findByHql(hql, userCode);
		return list != null && list.size() > 0;
	}
	//mantis： CAR0051 ，處理人員： David ，需求單編號： CAR0051   end
	
	/**
	 * mantis： LIA0348，處理人員：DP0706，需求單編號：LIA0348_稽核議題處理
	 * 查詢AR險館藏品保險期間/參展品保險期間/運送品保險期間
	 */
	@Override
	public Map<String, Object> queryARStartDate(String businessNo){
		Map<String, Object> result = new HashMap<String, Object>();
		DBManager dbManager = new DBManager();
		ResultSet resultSet = null;
		try {
			dbManager.open("ddccDataSource");
			StringBuffer buffer = new StringBuffer(200);
		    buffer.append("SELECT ");
		    buffer.append("MUSEUMSTARTDATE,");
		    buffer.append("SHOWSTARTDATE,");
		    buffer.append("TRANSPORTSTARTDATE ");
		    buffer.append("FROM PRPTMAINPROP ");
		    buffer.append("WHERE ");
		    buffer.append("PROPOSALNO = ?");
		    dbManager.prepareStatement(buffer.toString());
		    //設置條件字段
		    dbManager.setString(1,businessNo);
		    resultSet = dbManager.executePreparedQuery();
		        if(resultSet.next()){
		        	result.put("MUSEUMSTARTDATE", dbManager.getString(resultSet,1));
		        	result.put("SHOWSTARTDATE", dbManager.getString(resultSet,2));
		        	result.put("TRANSPORTSTARTDATE", dbManager.getString(resultSet,3));
		           
		        }
		    resultSet.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			try {
				
				dbManager.close();
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
         	
		}
		
	             
	            
		return result;
	}
}
