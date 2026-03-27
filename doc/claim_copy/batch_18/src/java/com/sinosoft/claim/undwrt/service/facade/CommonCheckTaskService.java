package com.sinosoft.claim.undwrt.service.facade;

import com.sinosoft.undwrt.dto.custom.PolicyAbstractInfoDto;

/**
 * 核赔任务查询接口
 * @author 中科软
 * created on 2005-7-21
 */
public interface CommonCheckTaskService {

	public PolicyAbstractInfoDto getPolicyAbstractInfo(	String businessNo) throws Exception;
//	{
//		DBWfLog dbWfLog = new DBWfLog(dbManager);
//		WfLogDto wfLogDto = new WfLogDto();
//		Collection wfLogList = new ArrayList();
//		String strCertiType = "";
//		String strSQL = " BusinessNo='" + businessNo + "'";
//		wfLogList = dbWfLog.findByConditions(strSQL);
//
//		Iterator itwflog = wfLogList.iterator();
//		if (itwflog.hasNext()) {
//			wfLogDto = (WfLogDto) itwflog.next();
//			strCertiType = wfLogDto.getBusinessType();
//
//		}
//		PolicyAbstractInfoDto policyAbstractInfoDto = new PolicyAbstractInfoDto();
//		if (strCertiType.equals("C")) {
//			DBPrpLcompensate dbPrpLcompensate = new DBPrpLcompensate(dbManager);
//			PrpLcompensateDto prpLcompensateDto = dbPrpLcompensate.findByPrimaryKey(businessNo);
//
//			//按照客户需求，保品金额显示本次赔付总和
//			//modify by liuwei at 2011-07-27 从（联、共）保显示我司金额 start
//			DBPrpCmain dbPrpCmain = new DBPrpCmain(dbManager);
//			PrpCmainDto prpCmainDto = dbPrpCmain.findByPrimaryKey(prpLcompensateDto.getPolicyNo());
//			if ("2".equals(prpCmainDto.getCoinsFlag()) || "4".equals(prpCmainDto.getCoinsFlag())) {
//				DBPrpCcoins dbPrpCcoins = new DBPrpCcoins(dbManager);
//				List<PrpCcoinsDto> prpCcoinsList = (ArrayList<PrpCcoinsDto>)dbPrpCcoins.findByConditions("policyNo='"+prpLcompensateDto.getPolicyNo()+"' and coinsType='1'");
//				for (Iterator<PrpCcoinsDto> iterator = prpCcoinsList.iterator(); iterator.hasNext();) {
//					PrpCcoinsDto prpCcoinsDto = iterator.next();
//					BigDecimal bigCoinsRate = new BigDecimal(Double.toString(prpCcoinsDto.getCoinsRate()/100));
//					BigDecimal bigSumLoss = new BigDecimal(Double.toString(prpLcompensateDto.getSumDutyPaid()));
//					BigDecimal bigSumPaid = new BigDecimal(Double.toString(prpLcompensateDto.getSumPaid()));
//					policyAbstractInfoDto.setSumLoss(bigSumLoss.multiply(bigCoinsRate).toString());
//					policyAbstractInfoDto.setSumPaid(bigSumPaid.multiply(bigCoinsRate).toString());
//				}
//			} else {
//				policyAbstractInfoDto.setSumLoss(String.valueOf(prpLcompensateDto.getSumDutyPaid()));
//				policyAbstractInfoDto.setSumPaid(String.valueOf(prpLcompensateDto.getSumPaid()));
//			}
//			//modify by liuwei at 2011-07-27 从（联、共）保显示我司金额 end
//			//lijibin add 20050827 bug21033 其他费用金额不对
//			policyAbstractInfoDto.setOther(String.valueOf(prpLcompensateDto
//					.getSumNoDutyFee()));
//			if (StringUtils.trimToEmpty(policyAbstractInfoDto.getSumLoss())
//					.length() == 0) {
//				policyAbstractInfoDto.setSumLoss("0");
//			}
//			if (StringUtils.trimToEmpty(policyAbstractInfoDto.getSumPaid())
//					.length() == 0) {
//				policyAbstractInfoDto.setSumPaid("0");
//			}
//
//		} else if (strCertiType.equals("Y")) {
//			DBPrpLprepay dbPrpLprepay = new DBPrpLprepay(dbManager);
//			PrpLprepayDto prpLprepayDto = dbPrpLprepay.findByPrimaryKey(businessNo);
//			//modify by liuwei at 2011-07-27 从（联、共）保显示我司金额 start
//			DBPrpCmain dbPrpCmain = new DBPrpCmain(dbManager);
//			PrpCmainDto prpCmainDto = dbPrpCmain.findByPrimaryKey(prpLprepayDto.getPolicyNo());
//			if ("2".equals(prpCmainDto.getCoinsFlag()) || "4".equals(prpCmainDto.getCoinsFlag())) {
//				DBPrpCcoins dbPrpCcoins = new DBPrpCcoins(dbManager);
//				List<PrpCcoinsDto> prpCcoinsList = (ArrayList<PrpCcoinsDto>)dbPrpCcoins.findByConditions("policyNo='"+prpLprepayDto.getPolicyNo()+"' and coinsType='1'");
//				for (Iterator<PrpCcoinsDto> iterator = prpCcoinsList.iterator(); iterator.hasNext();) {
//					PrpCcoinsDto prpCcoinsDto = iterator.next();
//					BigDecimal bigCoinsRate = new BigDecimal(Double.toString(prpCcoinsDto.getCoinsRate()/100));
//					BigDecimal bigSumPaid = new BigDecimal(Double.toString(prpLprepayDto.getSumPrePaid()));
//					policyAbstractInfoDto.setSumPaid(bigSumPaid.multiply(bigCoinsRate).toString());
//				}
//			} else {
//				policyAbstractInfoDto.setSumPaid(String.valueOf(prpLprepayDto.getSumPrePaid()));
//			}
//			//modify by liuwei at 2011-07-27 从（联、共）保显示我司金额 end
//			
//			if (StringUtils.trimToEmpty(policyAbstractInfoDto.getSumLoss()).length() == 0) {
//				policyAbstractInfoDto.setSumLoss("0");
//			}
//			if (StringUtils.trimToEmpty(policyAbstractInfoDto.getSumPaid()).length() == 0) {
//				policyAbstractInfoDto.setSumPaid("0");
//			}
//		}
//		return policyAbstractInfoDto;
//
//	}

	public String getClaimNo(String busiNo, String busiType)throws Exception;
//	{
//		String claimNo = null;
//		if ("C".equals(busiType))//计算书号
//		{
//			DBPrpLcompensate dbPrpLcompensate = new DBPrpLcompensate(dbManager);
//			PrpLcompensateDto prpLcompensateDto = dbPrpLcompensate
//					.findByPrimaryKey(busiNo);
//			claimNo = prpLcompensateDto.getClaimNo();
//		} else if ("Y".equals(busiType))//预赔号
//		{
//			DBPrpLprepay dbPrpLprepay = new DBPrpLprepay(dbManager);
//			PrpLprepayDto prpLprepayDto = dbPrpLprepay.findByPrimaryKey(busiNo);
//			claimNo = prpLprepayDto.getClaimNo();
//		}
//		return claimNo;
//	}

	public String getRegistNo(String claimNo)throws Exception;
//	{
//		DBPrpLclaim dbPrpLclaim = new DBPrpLclaim(dbManager);
//		PrpLclaimDto prpLclaimDto = dbPrpLclaim.findByPrimaryKey(claimNo);
//		return prpLclaimDto.getRegistNo();
//	}
}