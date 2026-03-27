package com.sinosoft.claim.undwrt.service.spring;

import ins.framework.dao.GenericDaoHibernate;
import ins.framework.utils.DataUtils;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.sinosoft.claim.common.ConstantCodes;
import com.sinosoft.claim.common.service.facade.PrpDuserService;
import com.sinosoft.claim.common.service.facade.UtiCodeTransferService;
import com.sinosoft.claim.compensate.service.facade.CompensateService;
import com.sinosoft.claim.payment.util.PayMentServiceManager;
import com.sinosoft.claim.reins.service.ReinsServiceManager;
import com.sinosoft.claim.reins.vo.ReinsClaimDetail;
import com.sinosoft.claim.reins.vo.ReinsClaimMain;
import com.sinosoft.claim.replevy.util.ReplevyViewHelper;
import com.sinosoft.claim.schema.model.PrpCcoins;
import com.sinosoft.claim.schema.model.PrpCitemCarExt;
import com.sinosoft.claim.schema.model.PrpCmain;
import com.sinosoft.claim.schema.model.PrpDcode;
import com.sinosoft.claim.schema.model.PrpDuser;
import com.sinosoft.claim.schema.model.PrpLcharge;
import com.sinosoft.claim.schema.model.PrpLclaim;
import com.sinosoft.claim.schema.model.PrpLcompensate;
import com.sinosoft.claim.schema.model.PrpLloss;
import com.sinosoft.claim.schema.model.PrpLpersonLoss;
import com.sinosoft.claim.schema.model.PrpLprepay;
import com.sinosoft.claim.schema.service.facade.PrpCcoinsService;
import com.sinosoft.claim.schema.service.facade.PrpCitemCarExtService;
import com.sinosoft.claim.schema.service.facade.PrpCmainService;
import com.sinosoft.claim.schema.service.facade.PrpDcodeService;
import com.sinosoft.claim.schema.service.facade.PrpLchargeService;
import com.sinosoft.claim.schema.service.facade.PrpLclaimService;
import com.sinosoft.claim.schema.service.facade.PrpLcompensateService;
import com.sinosoft.claim.schema.service.facade.PrpLlossService;
import com.sinosoft.claim.schema.service.facade.PrpLpersonLossService;
import com.sinosoft.claim.schema.service.facade.PrpLprepayService;
import com.sinosoft.claim.undwrt.service.facade.PrpFeedBackService;
import com.sinosoft.claim.undwrt.util.ClaimCallBackServiceManager;
import com.sinosoft.prpall.pubfun.PubTools;
import com.sinosoft.sysframework.common.datatype.DateTime;
import com.sinosoft.sysframework.exceptionlog.UserException;
import com.sinosoft.utility.string.ChgDate;
/**
 * 双核回调处理接口实现类
 * @author 中科软
 *
 */
@SuppressWarnings("unchecked")
public class PrpFeedBackServiceSpringImpl extends GenericDaoHibernate implements PrpFeedBackService {

	private PrpDuserService prpDuserService;
	private PrpLcompensateService prpLcompensateService;
	private PrpLprepayService prpLprepayService;
	private PrpCmainService prpCmainService;
	private UtiCodeTransferService utiCodeTransferService;
	private PrpLclaimService prpLclaimService;
	private PrpCitemCarExtService prpCitemCarExtService;
	private PrpDcodeService prpDcodeService;
	private PrpLlossService prpLlossService;
	private PrpLpersonLossService prpLpersonLossService;
	private PrpLchargeService prpLchargeService;
	private PrpCcoinsService prpCcoinsService;
	private ReplevyViewHelper replevyViewHelper;
	private CompensateService compensateService;
	/**
	 * 双核回写业务入口方法（核保通过/不通过後）
	 * @param certiType 核赔业务类型
	 * @param businessNo 业务号
	 * @param status 审核状态
	 * @param underWriteCode 审核人代码
	 * @param underWriteDate 审核时间
	 * @param infoMap 审核通过送收付系统的数据
	 */
	public void echo(char certiType, String businessNo, String status, String underWriteCode, DateTime underWriteDate,Map<String,String> infoMap) throws UserException, SQLException, Exception {
			switch (certiType) {
			case 'Y':
				this.echoPreCompensate(businessNo, status, underWriteCode, underWriteDate);
				break;
			case 'C':
				this.echoCompensate(businessNo, status, underWriteCode, underWriteDate,infoMap);
				break;
			default:
				throw new UserException(-98, -1149, "BLPrpFeedBack.echo()", "無此單證類型");
			}
			// 核赔通过的後续处理 modify by luyang 2006-4-24 begin
			if (status.trim().equals("3") || status.trim().equals("1")) {
				String businessType = String.valueOf(certiType);
				PrpLcompensate prpLcompensate = null;
				PrpLprepay prpLprepay = null;
				PrpCmain prpCmain = null;
				String policyno = "";
				if (String.valueOf(certiType).equals("C")) {
					prpLcompensate = prpLcompensateService.findPrpLcompensate(businessNo);
					policyno = prpLcompensate.getPolicyNo();
				} else if (String.valueOf(certiType).equals("Y")) {
					prpLprepay = prpLprepayService.findPrpLprepay(businessNo);
					policyno = prpLprepay.getPolicyNo();
				}
				// 车险也需要送再保
				// if (!"D".equals(codeName)) {
				// 送再保
				sendReins(businessType, businessNo);
				// }
				prpCmain = prpCmainService.findByPrimaryKey(policyno);
				String businessflag = prpCmain.getBusinessFlag();
				System.out.println(businessflag + "开始--------businessType==" + businessType + "------------businessNo==" + businessNo);
				if (!("1".equals(businessflag))) {
					try {
						System.out.println("向理赔收付中间表送数开始--------businessType==" + businessType + "------------businessNo==" + businessNo);
						PayMentServiceManager.getService().transData( businessType, businessNo,infoMap);
						System.out.println("向理赔收付中间表送数结束--------businessType==" + businessType + "------------businessNo==" + businessNo);
					} catch (UserException e) {
						e.printStackTrace();
						throw new UserException(-98, -1149, "計算書號==" + businessNo, "送接口表數據出錯,出錯原因"+e.getErrorMessage());
					} catch (Exception e) {
						e.printStackTrace();
						throw new UserException(-98, -1149, "計算書號==" + businessNo, "送接口表數據出錯,出錯原因"+e.getMessage());
					}
				}
				// 回调理赔处理
			ClaimCallBackServiceManager.getService().callBack(businessType, businessNo);
		}
	}

	/**
	 * 
	 * @Description: 预赔计算书核赔回调
	 * @param businessNo 业务号码
	 * @param status 审核状态
	 * @param underwriteCode 审核人代码
	 * @param underwriteDate 审核时间
	 * @throws Exception
	 */
	private void echoPreCompensate(String businessNo, String status, String underwriteCode, DateTime underwriteDate) throws Exception {
		PrpLprepay prpLprepay = prpLprepayService.findPrpLprepay(businessNo);
		if (status.trim().equals("3")) {
			underwriteCode = prpLprepay.getApproverCode();
		}
		if (status.trim().equals("2")) {
			prpLprepay.setApproverCode("");
		}
		prpLprepay.setUnderWriteFlag(status);
		prpLprepay.setUnderWriteCode(underwriteCode);
		PrpDuser prpDuser = prpDuserService.findPrpDuser(underwriteCode);
		if (prpDuser != null) {
			prpLprepay.setUnderWriteName(prpDuser.getUserName());
		}
		prpLprepay.setUnderWriteEndDate(underwriteDate);
		prpLprepayService.update(prpLprepay);
	}

	/**
	 * 
	 * @Description:理算计算书核赔回调处理
	 * @param businessNo 业务号码
	 * @param status 审核状态
	 * @param underwriteCode 审核人代码
	 * @param underwriteDate 审核时间
	 * @throws Exception
	 */
	private void echoCompensate(String businessNo, String status, String underwriteCode, DateTime underwriteDate,Map<String,String>infoMap) throws Exception {
		// 自动结案标志 0/关闭 1/开启
		status = status.trim();
		PrpLcompensate prpLcompensate = prpLcompensateService.findPrpLcompensate(businessNo);
		if (status.trim().equals("3")) {
			underwriteCode = prpLcompensate.getApproverCode();
		}
		if (status.equals("2")) {
			prpLcompensate.setApproverCode("");
		}
		if (!("".equals(underwriteCode))) {
			prpLcompensate.setUnderWriteCode(underwriteCode);
			PrpDuser prpDuser = prpDuserService.findPrpDuser(underwriteCode);
			if (prpDuser != null) {
				prpLcompensate.setUnderWriteName(prpDuser.getUserName());
			}
		} else {
			prpLcompensate.setUnderWriteCode("");
			prpLcompensate.setUnderWriteName("");
		}
		prpLcompensate.setUnderWriteFlag(status);
		prpLcompensate.setUnderWriteEndDate(underwriteDate);
		prpLcompensate.setUnderWriteDeptCode(infoMap.get("comCode"));
		this.prpLcompensateService.update(prpLcompensate);
		if (status.equals("3") || status.equals("1")) {
			String replevyFlag = infoMap.get("replevyFlag");
			if("1".equals(replevyFlag)){//审核时选择的有追偿，则自动做追偿登录。
				this.compensateService.save(this.replevyViewHelper.autoReplevy(prpLcompensate.getClaimNo()));
			}
			String mutualCompensateNo = prpLcompensate.getMutualCompensateNo();
			if(DataUtils.emptyToNull(mutualCompensateNo)!=null){//审核通过，冲抵计算书，回写其冲抵账号码
				PrpLcompensate mutual = this.prpLcompensateService.findPrpLcompensate(mutualCompensateNo);
				mutual.setMutualCompensateNo(prpLcompensate.getCompensateNo());
				prpLcompensateService.update(mutual);
			}
		}
	}

	/**
	 * 理赔提交核赔时回写业务入口方法
	 * @param certiType 业务类型
	 * @param businessNo 业务号码
	 */
	public void echoSubmit(char certiType, String businessNo) throws UserException, SQLException, Exception {
		switch (certiType) {
		case 'Y':
			PrpLprepay prpLprepay = prpLprepayService.findPrpLprepay(businessNo);
			prpLprepay.setUnderWriteFlag("9");
			prpLprepayService.update(prpLprepay);
			break;
		case 'C':
			PrpLcompensate prpLcompensate = prpLcompensateService.findPrpLcompensate(businessNo);
			prpLcompensate.setUnderWriteFlag("9");
			prpLcompensateService.update(prpLcompensate);
			break;
		default:
			throw new UserException(-98, -1149, "WfLog.query()", "無此單證類型");
		}
	}

	/***
	 * 
	 * @Description: 理算计算书核赔送再保入口
	 * @param businessType 业务类型
	 * @param businessNo 业务号码
	 * @throws Exception
	 */
	private void sendReins(String businessType, String businessNo) throws Exception {
		if ("C".equals(businessType)) {
			sendReinsForPrpLCompensate(businessNo);
		}
	}

	/***
	 * 
	 * @Description: 计算书核赔组织送再保数据入口
	 * @param businessNo 业务号码
	 * @throws Exception
	 */
	private void sendReinsForPrpLCompensate(String businessNo) throws Exception {
		System.out.println("**********************into sendReinsForPrpLCompensate***********");
		ReinsClaimMain reinsClaimMain = new ReinsClaimMain();
		PrpLcompensate prpLcompensate = prpLcompensateService.findPrpLcompensate(businessNo);
		PrpLclaim prpLclaim = prpLclaimService.findPrpLclaim(prpLcompensate.getClaimNo());
		// 获取业务类型、渠道、车型、兑换率
		PrpCmain prpCmain = prpCmainService.findByPrimaryKey(prpLclaim.getPolicyNo());
		List prpCitemCarExt = prpCitemCarExtService.findByPolicyNo(prpLclaim.getPolicyNo());
		String businessNature = prpCmain.getBusinessNature();// 业务渠道
		String channelType = prpCmain.getChannelType();// 渠道类型
		String cartypeCode = "";
		if (null != prpCitemCarExt && prpCitemCarExt.size() > 0) {
			cartypeCode = ((PrpCitemCarExt) prpCitemCarExt.get(0)).getCartypeCode();// 车型
		}
		// 签单币别和本位币兑换率
		ChgDate thisDte = new ChgDate();
		double exchangeRate = PubTools.getExchangeRate(prpCmain.getCurrency(), ConstantCodes.LOCAL_CURRENCY, thisDte.getCurrentTime("yyyy-MM-dd"));
		// 获取业务类型、渠道、车型、兑换率end
//		String codetype = "DamageCode";
		// 得到出险原因
//		PrpDcode prpDcode = prpDcodeService.findByPrimaryKey(codetype, prpLclaim.getDamageCode(),prpLclaim.getRiskCode());

		// 得到是否结案 true已结/false未结
		boolean isEndCase = false;
		if (prpLclaim.getEndCaseDate() != null) {
			isEndCase = true;
		}

		// 得到制单机构
		// WfLogDto wfLogDto = new
		// DBWfLog(dbManager).findByMaxLognoAndBusinessNo(businessNo);
		// 送再保数据
		reinsClaimMain.setPolicyNo(prpLcompensate.getPolicyNo());
		reinsClaimMain.setDamageDate(new DateTime(prpLclaim.getDamageStartDate()));// 出险时间
		reinsClaimMain.setAddressName(prpLclaim.getAddressCode());
		reinsClaimMain.setCertiNo(businessNo);
		reinsClaimMain.setCertiType(ReinsClaimMain.CertiType.PAY);
		reinsClaimMain.setClaimNo(prpLclaim.getClaimNo());
		reinsClaimMain.setCreateDate(new DateTime(DateTime.current(), DateTime.YEAR_TO_SECOND));// 核赔通过时间，精确到秒
		reinsClaimMain.setCreaterCode(prpCmain.getOperatorCode()); // 制单员（预赔/实赔为核赔人员代码）
		reinsClaimMain.setDamageCode(DataUtils.dbNullToEmpty(prpLclaim.getDamageCode()).trim()); // 出险原因代码
		reinsClaimMain.setDamageReason(prpLclaim.getDamageName()); // 出险摘要
		reinsClaimMain.setEndCaseFlag(new Boolean(isEndCase)); // 结案标志
		reinsClaimMain.setMakeComCode(prpCmain.getMakeCom()); // 制单机构（预赔/实赔为核赔机构代码)
		reinsClaimMain.setPostCode(prpLclaim.getDamageAreaPostCode()); // 出险地邮编
		reinsClaimMain.setSumClaim(new Double(prpLclaim.getSumClaim()));// 估损金额
		// 获取业务类型、渠道、车型、兑换率begin
		reinsClaimMain.setBusinessNature(businessNature);
		reinsClaimMain.setExchangeRate(exchangeRate);
		reinsClaimMain.setChannelType(channelType);
		reinsClaimMain.setCartypeCode(cartypeCode);
		// 获取业务类型、渠道、车型、兑换率end
		Collection reinsClaimDetailList = new ArrayList<Object>();
		String querySql = "compensateno = '" + businessNo + "'";
		// Loss表明细
		// Collection prpLlossList = new
		// BLPrpLlossFacade().findByConditions(querySql);
		List prpLlossList = prpLlossService.findByConditions(querySql);
		for (Iterator iter = prpLlossList.iterator(); iter.hasNext();) {
			PrpLloss prpLloss = (PrpLloss) iter.next();
			ReinsClaimDetail reinsClaimDetail = new ReinsClaimDetail();
			reinsClaimDetail.setKindCode(prpLloss.getKindCode());
			reinsClaimDetail.setKindName(prpLloss.getKindName());
			reinsClaimDetail.setItemName(prpLloss.getItemCode());
			reinsClaimDetail.setCurrency(prpLloss.getCurrency4());
			reinsClaimDetail.setSumPaid(new Double(prpLloss.getSumRealPay() + prpLloss.getExceptDeductiblePay()));

			reinsClaimDetail.setPayType(ReinsClaimDetail.PayType.PAY);

			// 获取业务类型、渠道、车型、兑换率begin
			reinsClaimDetail.setBusinessNature(businessNature);
			reinsClaimDetail.setExchangeRate(exchangeRate);
			reinsClaimDetail.setChannelType(channelType);
			reinsClaimDetail.setCartypeCode(cartypeCode);
			// 获取业务类型、渠道、车型、兑换率end

			reinsClaimDetail.setDangerNo(new Integer(prpLloss.getDangerNo()));// 危险单位序号
			reinsClaimDetailList.add(reinsClaimDetail);
		}

		// PersonLoss表明细
		// Collection prpLpersonLossList = new
		// BLPrpLpersonLossFacade().findByConditions(querySql);
		List prpLpersonLossList = prpLpersonLossService.findByConditions(querySql);
		for (Iterator iter = prpLpersonLossList.iterator(); iter.hasNext();) {
			PrpLpersonLoss prpLpersonLoss = (PrpLpersonLoss) iter.next();
			ReinsClaimDetail reinsClaimDetail = new ReinsClaimDetail();
			reinsClaimDetail.setKindCode(prpLpersonLoss.getKindCode());
			reinsClaimDetail.setKindName(prpLpersonLoss.getKindName());

			reinsClaimDetail.setCurrency(prpLpersonLoss.getCurrency4());
			reinsClaimDetail.setSumPaid(new Double(prpLpersonLoss.getSumRealPay() + prpLpersonLoss.getExceptDeductiblePay()));
			reinsClaimDetail.setPayType(ReinsClaimDetail.PayType.PAY);

			// 获取业务类型、渠道、车型、兑换率begin
			reinsClaimDetail.setBusinessNature(businessNature);
			reinsClaimDetail.setExchangeRate(exchangeRate);
			reinsClaimDetail.setChannelType(channelType);
			reinsClaimDetail.setCartypeCode(cartypeCode);
			// 获取业务类型、渠道、车型、兑换率end

			reinsClaimDetail.setDangerNo(new Integer(prpLpersonLoss.getDangerNo()));// 危险单位序号
			reinsClaimDetailList.add(reinsClaimDetail);
		}

		// Charge表明细
		// Collection prpLchargeList = new
		// BLPrpLchargeFacade().findByConditions(querySql);
		List prpLchargeList = prpLchargeService.findByConditions(querySql);
		for (Iterator iter = prpLchargeList.iterator(); iter.hasNext();) {
			PrpLcharge PrpLcharge = (PrpLcharge) iter.next();
			ReinsClaimDetail reinsClaimDetail = new ReinsClaimDetail();
			reinsClaimDetail.setKindCode(PrpLcharge.getKindCode());
			reinsClaimDetail.setKindName(PrpLcharge.getKindName());

			reinsClaimDetail.setCurrency(PrpLcharge.getCurrency());
			double coinsRate = 1;
			if ("2".equals(prpCmain.getCoinsFlag()) || "3".equals(prpCmain.getCoinsFlag())) {
				List prpCcoinsList = prpCcoinsService.findByConditions(" policyNO='" + prpCmain.getPolicyNo() + "' and coinsType='2' ");
				// ArrayList PrpCcoinsDtoList = (ArrayList)new
				// BLPrpCcoinsAction().findByConditions(dbManager,
				// " policyNO='"+
				// prpCmainDto.getPolicyNo()+"' and coinsType='1' ");
				if (null != prpCcoinsList && prpCcoinsList.size() > 0) {
					PrpCcoins prpCcoins = (PrpCcoins) prpCcoinsList.get(0);
					coinsRate = prpCcoins.getCoinsRate() / 100;
				}
			}

//			if ("03".equals(PrpLcharge.getChargeCode())) {
//				reinsClaimDetail.setPayType(ReinsClaimDetail.PayType.PAY);
//				reinsClaimDetail.setSumPaid(new Double(PrpLcharge.getSumRealPay() / coinsRate + PrpLcharge.getExceptDeductiblePay() / coinsRate));
//			} else {
				reinsClaimDetail.setPayType(ReinsClaimDetail.PayType.CHARGE);
				reinsClaimDetail.setSumPaid(new Double(PrpLcharge.getChargeAmount() / coinsRate + PrpLcharge.getExceptDeductiblePay() / coinsRate));
//			}

			// 获取业务类型、渠道、车型、兑换率begin
			reinsClaimDetail.setBusinessNature(businessNature);
			reinsClaimDetail.setExchangeRate(exchangeRate);
			reinsClaimDetail.setChannelType(channelType);
			reinsClaimDetail.setCartypeCode(cartypeCode);
			// 获取业务类型、渠道、车型、兑换率end
			reinsClaimDetail.setDangerNo(new Integer(PrpLcharge.getDangerNo()));// 危险单位序号
			reinsClaimDetailList.add(reinsClaimDetail);
		}
		reinsClaimMain.setReinsClaimDetailList(reinsClaimDetailList);

		try {
			new ReinsServiceManager().getReinsService().repayCal(reinsClaimMain);
		} catch (Exception e) {
			throw e;
		}
	}

	public PrpDuserService getPrpDuserService() {
		return prpDuserService;
	}

	public void setPrpDuserService(PrpDuserService prpDuserService) {
		this.prpDuserService = prpDuserService;
	}

	public PrpLcompensateService getPrpLcompensateService() {
		return prpLcompensateService;
	}

	public void setPrpLcompensateService(PrpLcompensateService prpLcompensateService) {
		this.prpLcompensateService = prpLcompensateService;
	}

	public PrpLprepayService getPrpLprepayService() {
		return prpLprepayService;
	}

	public void setPrpLprepayService(PrpLprepayService prpLprepayService) {
		this.prpLprepayService = prpLprepayService;
	}

	public PrpCmainService getPrpCmainService() {
		return prpCmainService;
	}

	public void setPrpCmainService(PrpCmainService prpCmainService) {
		this.prpCmainService = prpCmainService;
	}

	public UtiCodeTransferService getUtiCodeTransferService() {
		return utiCodeTransferService;
	}

	public void setUtiCodeTransferService(UtiCodeTransferService utiCodeTransferService) {
		this.utiCodeTransferService = utiCodeTransferService;
	}

	public PrpLclaimService getPrpLclaimService() {
		return prpLclaimService;
	}

	public void setPrpLclaimService(PrpLclaimService prpLclaimService) {
		this.prpLclaimService = prpLclaimService;
	}

	public PrpCitemCarExtService getPrpCitemCarExtService() {
		return prpCitemCarExtService;
	}

	public void setPrpCitemCarExtService(PrpCitemCarExtService prpCitemCarExtService) {
		this.prpCitemCarExtService = prpCitemCarExtService;
	}

	public PrpDcodeService getPrpDcodeService() {
		return prpDcodeService;
	}

	public void setPrpDcodeService(PrpDcodeService prpDcodeService) {
		this.prpDcodeService = prpDcodeService;
	}

	public PrpLlossService getPrpLlossService() {
		return prpLlossService;
	}

	public void setPrpLlossService(PrpLlossService prpLlossService) {
		this.prpLlossService = prpLlossService;
	}

	public PrpLpersonLossService getPrpLpersonLossService() {
		return prpLpersonLossService;
	}

	public void setPrpLpersonLossService(PrpLpersonLossService prpLpersonLossService) {
		this.prpLpersonLossService = prpLpersonLossService;
	}

	public PrpLchargeService getPrpLchargeService() {
		return prpLchargeService;
	}

	public void setPrpLchargeService(PrpLchargeService prpLchargeService) {
		this.prpLchargeService = prpLchargeService;
	}

	public PrpCcoinsService getPrpCcoinsService() {
		return prpCcoinsService;
	}

	public void setPrpCcoinsService(PrpCcoinsService prpCcoinsService) {
		this.prpCcoinsService = prpCcoinsService;
	}

	public ReplevyViewHelper getReplevyViewHelper() {
		return replevyViewHelper;
	}

	public void setReplevyViewHelper(ReplevyViewHelper replevyViewHelper) {
		this.replevyViewHelper = replevyViewHelper;
	}

	public CompensateService getCompensateService() {
		return compensateService;
	}

	public void setCompensateService(CompensateService compensateService) {
		this.compensateService = compensateService;
	}

}
