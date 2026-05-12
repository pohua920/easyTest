package com.sinosoft.claim.undwrt.service.spring;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.sinosoft.claim.schema.model.PrpCcoins;
import com.sinosoft.claim.schema.model.PrpCmain;
import com.sinosoft.claim.schema.model.PrpLclaim;
import com.sinosoft.claim.schema.model.PrpLcompensate;
import com.sinosoft.claim.schema.model.PrpLprepay;
import com.sinosoft.claim.schema.model.WfLog;
import com.sinosoft.claim.schema.service.facade.PrpCcoinsService;
import com.sinosoft.claim.schema.service.facade.PrpCmainService;
import com.sinosoft.claim.schema.service.facade.PrpLclaimService;
import com.sinosoft.claim.schema.service.facade.PrpLcompensateService;
import com.sinosoft.claim.schema.service.facade.PrpLprepayService;
import com.sinosoft.claim.schema.service.facade.WfLogService;
import com.sinosoft.claim.undwrt.service.facade.CommonCheckTaskService;
import com.sinosoft.undwrt.dto.custom.PolicyAbstractInfoDto;

/**
 * @author 中科软
 */
public class CommonCheckTaskServiceSpringImpl implements CommonCheckTaskService {
	private WfLogService wfLogService;
	private PrpLcompensateService prpLcompensateService;
	private PrpCmainService prpCmainService;
	private PrpCcoinsService prpCcoinsService;
	private PrpLprepayService prpLprepayService;
	private PrpLclaimService prpLclaimService;
	
	/**
	 * 获取业务号对应的损失讯息
	 * @param businessNo 业务计算书号
	 * @return 保单危险单位损失
	 */
	public PolicyAbstractInfoDto getPolicyAbstractInfo(	String businessNo) throws Exception{
		WfLog wfLogDto = new WfLog();
		List<WfLog> wfLogList = new ArrayList<WfLog>();
		String strCertiType = "";
		String strSQL = " BusinessNo='" + businessNo + "'";
		wfLogList = this.getWfLogService().findByConditions(strSQL);

		Iterator<WfLog> itwflog = wfLogList.iterator();
		if (itwflog.hasNext()) {
			wfLogDto = (WfLog) itwflog.next();
			strCertiType = wfLogDto.getBusinessType();

		}
		PolicyAbstractInfoDto policyAbstractInfoDto = new PolicyAbstractInfoDto();
		if (strCertiType.equals("C")) {
			PrpLcompensate prpLcompensateDto = this.getPrpLcompensateService().findPrpLcompensate(businessNo);

			//按照客户需求，保品金额显示本次赔付总和
			//modify by liuwei at 2011-07-27 从（联、共）保显示我司金额 start
			PrpCmain prpCmainDto = this.getPrpCmainService().findByPrimaryKey(prpLcompensateDto.getPolicyNo());
			if ("2".equals(prpCmainDto.getCoinsFlag()) || "3".equals(prpCmainDto.getCoinsFlag())) {
				List<PrpCcoins> prpCcoinsList = (ArrayList<PrpCcoins>)this.getPrpCcoinsService().findByConditions("policyNo='"+prpLcompensateDto.getPolicyNo()+"' and coinsType='2'");
				for (Iterator<PrpCcoins> iterator = prpCcoinsList.iterator(); iterator.hasNext();) {
					PrpCcoins prpCcoinsDto = iterator.next();
					BigDecimal bigCoinsRate = new BigDecimal(Double.toString(prpCcoinsDto.getCoinsRate()/100));
					BigDecimal bigSumLoss = new BigDecimal(Double.toString(prpLcompensateDto.getSumDutyPaid()));
					BigDecimal bigSumPaid = new BigDecimal(Double.toString(prpLcompensateDto.getSumPaid()));
					policyAbstractInfoDto.setSumLoss(bigSumLoss.multiply(bigCoinsRate).toString());
					policyAbstractInfoDto.setSumPaid(bigSumPaid.multiply(bigCoinsRate).toString());
				}
			} else {
				policyAbstractInfoDto.setSumLoss(String.valueOf(prpLcompensateDto.getSumDutyPaid()));
				policyAbstractInfoDto.setSumPaid(String.valueOf(prpLcompensateDto.getSumPaid()));
			}
			//modify by liuwei at 2011-07-27 从（联、共）保显示我司金额 end
			//lijibin add 20050827 bug21033 其他费用金额不对
			policyAbstractInfoDto.setOther(String.valueOf(prpLcompensateDto
					.getSumNoDutyFee()));
			if (StringUtils.trimToEmpty(policyAbstractInfoDto.getSumLoss())
					.length() == 0) {
				policyAbstractInfoDto.setSumLoss("0");
			}
			if (StringUtils.trimToEmpty(policyAbstractInfoDto.getSumPaid())
					.length() == 0) {
				policyAbstractInfoDto.setSumPaid("0");
			}

		} else if (strCertiType.equals("Y")) {
			PrpLprepay prpLprepayDto = this.getPrpLprepayService().findPrpLprepay(businessNo);
			//modify by liuwei at 2011-07-27 从（联、共）保显示我司金额 start
			PrpCmain prpCmainDto = this.getPrpCmainService().findByPrimaryKey(prpLprepayDto.getPolicyNo());
			if ("2".equals(prpCmainDto.getCoinsFlag()) || "3".equals(prpCmainDto.getCoinsFlag())) {
				List<PrpCcoins> prpCcoinsList = (ArrayList<PrpCcoins>)this.getPrpCcoinsService().findByConditions("policyNo='"+prpLprepayDto.getPolicyNo()+"' and coinsType='2'");
				for (Iterator<PrpCcoins> iterator = prpCcoinsList.iterator(); iterator.hasNext();) {
					PrpCcoins prpCcoinsDto = iterator.next();
					BigDecimal bigCoinsRate = new BigDecimal(Double.toString(prpCcoinsDto.getCoinsRate()/100));
					BigDecimal bigSumPaid = new BigDecimal(Double.toString(prpLprepayDto.getSumPrePaid()));
					policyAbstractInfoDto.setSumPaid(bigSumPaid.multiply(bigCoinsRate).toString());
				}
			} else {
				policyAbstractInfoDto.setSumPaid(String.valueOf(prpLprepayDto.getSumPrePaid()));
			}
			//modify by liuwei at 2011-07-27 从（联、共）保显示我司金额 end
			
			if (StringUtils.trimToEmpty(policyAbstractInfoDto.getSumLoss()).length() == 0) {
				policyAbstractInfoDto.setSumLoss("0");
			}
			if (StringUtils.trimToEmpty(policyAbstractInfoDto.getSumPaid()).length() == 0) {
				policyAbstractInfoDto.setSumPaid("0");
			}
		}
		return policyAbstractInfoDto;

	}


	/***
	 * 根据计算书号获取立案号码
	 * @param busiNo 业务号（计算书号）
	 * @param busiType 业务类型 理算、预赔
	 * @return 立案号码
	 */
	public String getClaimNo(String busiNo, String busiType)throws Exception{
		String claimNo = null;
		if ("C".equals(busiType))//计算书号
		{
			PrpLcompensate prpLcompensateDto = this.getPrpLcompensateService().findPrpLcompensate(busiNo);
			claimNo = prpLcompensateDto.getClaimNo();
		} else if ("Y".equals(busiType))//预赔号
		{
			PrpLprepay prpLprepayDto = this.getPrpLprepayService().findPrpLprepay(busiNo);
			claimNo = prpLprepayDto.getClaimNo();
		}
		return claimNo;
	}
	
	/**
	 * 根据立案号码获取备案号码
	 * @param claimNo 立案号码
	 * @return 备案号码
	 */
	public String getRegistNo(String claimNo)throws Exception{
		PrpLclaim prpLclaimDto = this.getPrpLclaimService().findPrpLclaim(claimNo);
		return prpLclaimDto.getRegistNo();
	}

	public WfLogService getWfLogService() {
		return wfLogService;
	}

	public void setWfLogService(WfLogService wfLogService) {
		this.wfLogService = wfLogService;
	}

	public PrpLcompensateService getPrpLcompensateService() {
		return prpLcompensateService;
	}

	public void setPrpLcompensateService(PrpLcompensateService prpLcompensateService) {
		this.prpLcompensateService = prpLcompensateService;
	}

	public PrpCmainService getPrpCmainService() {
		return prpCmainService;
	}

	public void setPrpCmainService(PrpCmainService prpCmainService) {
		this.prpCmainService = prpCmainService;
	}

	public PrpCcoinsService getPrpCcoinsService() {
		return prpCcoinsService;
	}

	public void setPrpCcoinsService(PrpCcoinsService prpCcoinsService) {
		this.prpCcoinsService = prpCcoinsService;
	}

	public PrpLprepayService getPrpLprepayService() {
		return prpLprepayService;
	}

	public void setPrpLprepayService(PrpLprepayService prpLprepayService) {
		this.prpLprepayService = prpLprepayService;
	}

	public PrpLclaimService getPrpLclaimService() {
		return prpLclaimService;
	}

	public void setPrpLclaimService(PrpLclaimService prpLclaimService) {
		this.prpLclaimService = prpLclaimService;
	}

}