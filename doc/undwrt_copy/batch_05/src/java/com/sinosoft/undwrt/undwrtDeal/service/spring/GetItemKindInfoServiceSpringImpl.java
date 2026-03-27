package com.sinosoft.undwrt.undwrtDeal.service.spring;

import ins.framework.dao.GenericDaoHibernate;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import com.sinosoft.common.schema.model.PrpCPmain;
import com.sinosoft.common.schema.model.PrpPhead;
import com.sinosoft.common.schema.model.PrpTmain;
import com.sinosoft.prpall.blsvr.pg.BLPrpPhead;
import com.sinosoft.prpins.policy.service.facade.EndorseService;
import com.sinosoft.prpins.policy.service.facade.PolicyService;
import com.sinosoft.prpins.policy.service.facade.PrpCpMainService;
import com.sinosoft.undwrt.common.vo.CommonDangerItemInfoVo;
import com.sinosoft.undwrt.pub.InternationalizationUtil;
import com.sinosoft.undwrt.undwrtDeal.service.facade.GetItemKindInfoService;

/**
 * 查詢危險單位信息實現類.
 */
public class GetItemKindInfoServiceSpringImpl extends GenericDaoHibernate
		implements GetItemKindInfoService {

	
	private PolicyService policyService;
	
	private PrpCpMainService PrpCpMainService;
	
	private EndorseService EndorseService;

	public EndorseService getEndorseService() {
		return EndorseService;
	}

	public void setEndorseService(EndorseService endorseService) {
		EndorseService = endorseService;
	}

	public PrpCpMainService getPrpCpMainService() {
		return PrpCpMainService;
	}

	public void setPrpCpMainService(PrpCpMainService prpCpMainService) {
		PrpCpMainService = prpCpMainService;
	}

	public PolicyService getPolicyService() {
		return policyService;
	}

	public void setPolicyService(PolicyService policyService) {
		this.policyService = policyService;
	}

	/**
	 * 獲取危險單位信息.
	 * 
	 * @param businessNo
	 *            業務號
	 * @param riskCode
	 *            險種代碼
	 * @param businessType
	 *            業務類型
	 * @return 危險單位信息類
	 * @throws Exception
	 *             異常
	 * @see com.sinosoft.undwrt.undwrtDeal.service.facade.GetItemKindInfoService#getItemInfoMain(java.lang.String,
	 *      java.lang.String, java.lang.String)
	 */
	public Collection getItemInfoMain(String businessNo, String riskCode,
			String businessType) throws Exception {
		InternationalizationUtil internal = new InternationalizationUtil();
		String statement = null;
		CommonDangerItemInfoVo commonDangerItemInfoDto = null;
		// 意健险特殊处理 暂时不考虑危险单位拆分,如以后需要危险单位拆分再加上 add by luyang 2005-8-31
		if (riskCode.startsWith("26") || riskCode.startsWith("27")
				|| riskCode.equals("0301"))
			return getItemInfoForE(businessNo, riskCode, businessType);
		if (businessType.equals("T")) { // 投保单
			statement = this.getStatementT(businessNo, riskCode, businessType);
		} else if (businessType.equals("P")) { // 保单
			statement = this.getStatementP(businessNo, riskCode, businessType);
		} else if (businessType.equals("E")) { // 批单
			statement = this.getStatementE(businessNo, riskCode, businessType);
		}

		List<?> list = super.getSession().createSQLQuery(statement).list();
		Iterator<?> it = list.iterator();
		Collection<CommonDangerItemInfoVo> collection = new ArrayList<CommonDangerItemInfoVo>();
		if (!businessType.equals("E")) // 投保单和保单
		{
			while (it.hasNext()) {
				try {
					commonDangerItemInfoDto = new CommonDangerItemInfoVo();
					int index = 0;
					Object[] obj = (Object[]) it.next();
					commonDangerItemInfoDto.setBusinessNo((String) obj[index++]);
					commonDangerItemInfoDto.setRiskCode((String) obj[index++]);
					commonDangerItemInfoDto.setItemKindNo(((BigDecimal) obj[index++]).intValue());
					commonDangerItemInfoDto.setKindCode((String) obj[index++]);
					commonDangerItemInfoDto.setKindName((String) obj[index++]);
					commonDangerItemInfoDto.setItemCode((String) obj[index++]);
					commonDangerItemInfoDto.setItemDetailName((String) obj[index++]);
					
					List<?> prpDitemList = super.findBySql("select * from prpditem where riskcode=? and itemcode=?", 
							commonDangerItemInfoDto.getRiskCode(), commonDangerItemInfoDto.getItemCode());
					if(prpDitemList.size()>0 && prpDitemList.get(0)!=null)
					{
						commonDangerItemInfoDto.setItemDetailName(String.valueOf(((Object[])prpDitemList.get(0))[2]));
					}
					if (obj[index] != null) {
						commonDangerItemInfoDto.setAddressNo(((BigDecimal) obj[index++]).intValue());
					} else {
						index++;
					}
					commonDangerItemInfoDto.setPostCode((String) obj[index]);
					commonDangerItemInfoDto.setAddressCode((String) obj[index++]);
					index++;
					commonDangerItemInfoDto.setAddressName((String) obj[index++]);
					
					PrpTmain prpTmain = policyService.getPrpTmainByProposalNo(commonDangerItemInfoDto.getBusinessNo());
					if("1".equals((String)obj[15])){//主附險標誌（1主險 2附加險）
						for(int i=0;i<prpTmain.getPrpTmainProps().size();i++){
							if(prpTmain.getPrpTmainProps().get(i).getId().getBuildingNo().equals((String)obj[14])){
								for(int j=0;j<prpTmain.getPrpTaddresses().size();j++){
									if(String.valueOf((prpTmain.getPrpTaddresses().get(j).getId().getAddressNo())).equals((String)prpTmain.getPrpTmainProps().get(i).getAddressNo())){
										commonDangerItemInfoDto.setAddressCode(prpTmain.getPrpTaddresses().get(j).getAddressCode());
										commonDangerItemInfoDto.setAddressName(prpTmain.getPrpTaddresses().get(j).getAddressDetailInfo());
									}
								}
							}
						}
					}
					
					commonDangerItemInfoDto.setCurrency((String) obj[index++]);
					commonDangerItemInfoDto.setAmount(((BigDecimal) obj[index++]).doubleValue());
					commonDangerItemInfoDto.setPremium(((BigDecimal) obj[index++]).doubleValue());
					index++;
					//add by wangcan 2015/11/24   查询结果16列，主附险种标识  
					commonDangerItemInfoDto.setFlag((String) obj[index++]);
					commonDangerItemInfoDto.setCalculateFlag((String) obj[index++]);
					commonDangerItemInfoDto.setCurrency2((String) obj[index++]);
					commonDangerItemInfoDto.setExchangeRate(((BigDecimal) obj[index++]).doubleValue());
					commonDangerItemInfoDto.setTolPremium(((BigDecimal) obj[index++]).doubleValue());
					if (obj[index] != null) {
						commonDangerItemInfoDto.setLimitFee(((BigDecimal) obj[index++]).doubleValue());
					} else {
						index++;
					}
					if (obj[index] != null) {
						commonDangerItemInfoDto.setLimit03Fee(((BigDecimal) obj[index++]).doubleValue());
					}
					collection.add(commonDangerItemInfoDto);
				} catch (Exception e) {
					e.printStackTrace();
					throw new Exception(internal.getText("undwrt.action.commonDangerRisk.queryDataError"));
				}
			}

		} else // 批单
		{
			while (it.hasNext()) {
				try {
					commonDangerItemInfoDto = new CommonDangerItemInfoVo();
					int index = 0;
					Object[] obj = (Object[]) it.next();
					commonDangerItemInfoDto
							.setBusinessNo((String) obj[index++]);
					commonDangerItemInfoDto.setRiskCode((String) obj[index++]);
					commonDangerItemInfoDto
							.setItemKindNo(((BigDecimal) obj[index++])
									.intValue());
					commonDangerItemInfoDto.setKindCode((String) obj[index++]);
					commonDangerItemInfoDto.setKindName((String) obj[index++]);
					commonDangerItemInfoDto.setItemCode((String) obj[index++]);
					commonDangerItemInfoDto
							.setItemDetailName((String) obj[index++]);
					if (obj[index] != null) {
						commonDangerItemInfoDto
								.setAddressNo(((BigDecimal) obj[index++])
										.intValue());
					} else {
						index++;
					}
					commonDangerItemInfoDto.setPostCode((String) obj[index]);
					commonDangerItemInfoDto
							.setAddressCode((String) obj[index++]);
					commonDangerItemInfoDto
							.setAddressName((String) obj[index++]);
					commonDangerItemInfoDto.setCurrency((String) obj[index++]);
					// 需求变更，保额取原始保额和变化保额的和modify by wangjun20130905
					commonDangerItemInfoDto
							.setAmount(((BigDecimal) obj[index++])
									.doubleValue()
									+ ((BigDecimal) obj[index]).doubleValue());
					commonDangerItemInfoDto
							.setChgAmount(((BigDecimal) obj[index++])
									.doubleValue());
					commonDangerItemInfoDto
							.setPremium(((BigDecimal) obj[index++])
									.doubleValue());
					commonDangerItemInfoDto
							.setChgPremium(((BigDecimal) obj[index++])
									.doubleValue());
					commonDangerItemInfoDto
							.setCalculateFlag((String) obj[index++]);
					commonDangerItemInfoDto.setCurrency2((String) obj[index++]);
					commonDangerItemInfoDto
							.setExchangeRate(((BigDecimal) obj[index++])
									.doubleValue());
					commonDangerItemInfoDto
							.setTolPremium(((BigDecimal) obj[index++])
									.doubleValue());
					commonDangerItemInfoDto
							.setEndorseFlag((String) obj[index++]);
					// add by douzongxing 每次事故赔偿限额 20081126 begin
					if (obj[index] != null) {
						commonDangerItemInfoDto
								.setLimitFee(((BigDecimal) obj[index++])
										.doubleValue());
					} else {
						index++;
					}
					if (obj[index] != null) {
						commonDangerItemInfoDto
								.setChgLimitFee(((BigDecimal) obj[index++])
										.doubleValue());
					} else {
						index++;
					}
					if (obj[index] != null) {
						commonDangerItemInfoDto
								.setLimit03Fee(((BigDecimal) obj[index++])
										.doubleValue());
					} else {
						index++;
					}
					if (obj[index] != null) {
						commonDangerItemInfoDto
								.setChgLimit03Fee(((BigDecimal) obj[index++])
										.doubleValue());
					} else {
						index++;
					}
					// add by douzongxing 每次事故赔偿限额 20081126 begin
					collection.add(commonDangerItemInfoDto);
				} catch (Exception e) {
					e.printStackTrace();
					throw new Exception(
							internal.getText("undwrt.action.commonDangerRisk.queryDataError"));
				}
			}
		}// end if
		return collection;
	}

	// 为了去掉quoteDataSource而临时多加的方法
	/**
	 * 獲取危險單位信息.
	 * 
	 * @param businessNo
	 *            業務號
	 * @param riskCode
	 *            險種代碼
	 * @param businessType
	 *            業務類型
	 * @return 滿足條件的記錄集合
	 * @throws Exception
	 *             異常
	 * @see com.sinosoft.undwrt.undwrtDeal.service.facade.GetItemKindInfoService#getItemInfo(java.lang.String,
	 *      java.lang.String, java.lang.String)
	 */
	public Collection getItemInfo(String businessNo, String riskCode,
			String businessType) throws Exception {
		InternationalizationUtil internal = new InternationalizationUtil();
		String statement = null;
		CommonDangerItemInfoVo commonDangerItemInfoDto = null;
		// 意健险特殊处理 暂时不考虑危险单位拆分,如以后需要危险单位拆分再加上
		if (riskCode.startsWith("26") || riskCode.startsWith("27") || riskCode.equals("0301"))
			return getItemInfoForE(businessNo, riskCode, businessType);
		if (businessType.equals("T")) { // 投保单
			if (businessNo.startsWith("B")) {
				statement = this.getStatementQ(businessNo, riskCode,
						businessType);
			} else {
				statement = this.getStatementT(businessNo, riskCode,
						businessType);
			}
		} else if (businessType.equals("P")) { // 保单
			statement = this.getStatementP(businessNo, riskCode, businessType);
		} else if (businessType.equals("E")) { // 批单
			statement = this.getStatementE(businessNo, riskCode, businessType);
		}
		List<Object[]> list = this.getSession().createSQLQuery(statement).list();
		Iterator<Object[]> it = list.iterator();
		Collection<CommonDangerItemInfoVo> collection = new ArrayList<CommonDangerItemInfoVo>();
		if (!businessType.equals("E")) // 投保单和保单
		{
			while (it.hasNext()) {
				try {
					Object[] row = it.next();
					commonDangerItemInfoDto = new CommonDangerItemInfoVo();
					if ((String) row[0] != null) {
						commonDangerItemInfoDto.setBusinessNo((String) row[0]);
					}
					if ((String) row[1] != null) {
						commonDangerItemInfoDto.setRiskCode((String) row[1]);
					}
					if ((BigDecimal) row[2] != null) {
						commonDangerItemInfoDto
								.setItemKindNo(((BigDecimal) row[2]).intValue());
					}
					if ((String) row[3] != null) {
						commonDangerItemInfoDto.setKindCode((String) row[3]);
					}
					if ((String) row[4] != null) {
						commonDangerItemInfoDto.setKindName((String) row[4]);
					}
					if ((String) row[5] != null) {
						commonDangerItemInfoDto.setItemCode((String) row[5]);
					}
					if ((String) row[6] != null) {
						commonDangerItemInfoDto
								.setItemDetailName((String) row[6]);
					}
					if ((BigDecimal) row[7] != null) {
						commonDangerItemInfoDto
								.setAddressNo(((BigDecimal) row[7]).intValue());
					}
					if ((String) row[8] != null) {
						commonDangerItemInfoDto.setPostCode((String) row[8]);
					}
					if ((String) row[8] != null) {
						commonDangerItemInfoDto.setAddressCode((String) row[8]);
					}
					if ((String) row[9] != null) {
						commonDangerItemInfoDto.setAddressName((String) row[9]);
					}
					if ((String) row[10] != null) {
						commonDangerItemInfoDto.setCurrency((String) row[10]);
					}
					if ((BigDecimal) row[11] != null) {
						commonDangerItemInfoDto
								.setAmount(((BigDecimal) row[11]).doubleValue());
					}
					if ((BigDecimal) row[12] != null) {
						commonDangerItemInfoDto
								.setPremium(((BigDecimal) row[12])
										.doubleValue());
					}
					if ((String) row[13] != null) {
						commonDangerItemInfoDto
								.setCalculateFlag((String) row[13]);
					}
					if ((String) row[14] != null) {
						commonDangerItemInfoDto.setCurrency2((String) row[14]);
					}
					if ((BigDecimal) row[15] != null) {
						commonDangerItemInfoDto
								.setExchangeRate(((BigDecimal) row[15])
										.doubleValue());
					}
					if ((BigDecimal) row[16] != null) {
						commonDangerItemInfoDto
								.setTolPremium(((BigDecimal) row[16])
										.doubleValue());
					}
					if ((BigDecimal) row[17] != null) {
						// add by douzongxing 每次事故赔偿限额 20081126 begin
						commonDangerItemInfoDto
								.setLimitFee(((BigDecimal) row[17])
										.doubleValue());
					}
					if ((BigDecimal) row[18] != null) {
						commonDangerItemInfoDto
								.setLimit03Fee(((BigDecimal) row[18])
										.doubleValue());
					}
					// add by douzongxing 每次事故赔偿限额 20081126 begin
					collection.add(commonDangerItemInfoDto);
				} catch (Exception e) {
					e.printStackTrace();
					throw new Exception(
							internal.getText("undwrt.action.commonDangerRisk.queryDataError"));
				}
			}

		} else // 批单
		{
			while (it.hasNext()) {
				try {
					Object[] row = it.next();
					int index = 0;
					commonDangerItemInfoDto = new CommonDangerItemInfoVo();
					commonDangerItemInfoDto.setBusinessNo((String) row[index++]);
					commonDangerItemInfoDto.setRiskCode((String) row[index++]);
					if ((BigDecimal) row[index] != null) {
						commonDangerItemInfoDto.setItemKindNo(((BigDecimal) row[index++]).intValue());
					}else{
						index++;
					}
					commonDangerItemInfoDto.setKindCode((String) row[index++]);
					commonDangerItemInfoDto.setKindName((String) row[index++]);
					commonDangerItemInfoDto.setItemCode((String) row[index++]);
					commonDangerItemInfoDto.setItemDetailName((String) row[index++]);
					
					List<?> prpDitemList = super.findBySql("select * from prpditem where riskcode=? and itemcode=?", 
							commonDangerItemInfoDto.getRiskCode(), commonDangerItemInfoDto.getItemCode());
					if(prpDitemList.size()>0 && prpDitemList.get(0)!=null)
					{
						commonDangerItemInfoDto.setItemDetailName(String.valueOf(((Object[])prpDitemList.get(0))[2]));
					}
					if ((BigDecimal) row[index] != null) {
						commonDangerItemInfoDto.setAddressNo(((BigDecimal) row[index++]).intValue());
					}else{
						index++;
					}
					commonDangerItemInfoDto.setPostCode((String) row[index]);
					commonDangerItemInfoDto.setAddressCode((String) row[index++]);
					index++;
					commonDangerItemInfoDto.setAddressName((String) row[index++]);
					
					PrpPhead prpPhead = EndorseService.getPrpPheadByEndorseNo(commonDangerItemInfoDto.getBusinessNo());
					PrpCPmain prpCPmain = PrpCpMainService.getPrpCpMainByPolicyNo(prpPhead.getPolicyNo());
					if("1".equals((String)row[11])){//主附險標誌（1主險 2附加險）
						for(int i=0;i<prpCPmain.getPrpCPmainProps().size();i++){
							if(prpCPmain.getPrpCPmainProps().get(i).getId().getBuildingNo().equals((String)row[12])){
								for(int j=0;j<prpCPmain.getPrpCPaddresses().size();j++){
									if(String.valueOf((prpCPmain.getPrpCPaddresses().get(j).getId().getAddressNo())).equals((String)prpCPmain.getPrpCPmainProps().get(i).getAddressNo())){
										commonDangerItemInfoDto.setAddressCode(prpCPmain.getPrpCPaddresses().get(j).getAddressCode());
										commonDangerItemInfoDto.setAddressName(prpCPmain.getPrpCPaddresses().get(j).getAddressDetailInfo());
									}
								}
							}
						}
					}
					
					index++;	// [11]主附險標誌
					index++;	// [12]建築序號
					commonDangerItemInfoDto.setCurrency((String) row[index++]);
					// 需求变更，保额取原始保额和变化保额的和modify by wangjun20130905
					if ((BigDecimal) row[14] != null) {
						if ((BigDecimal) row[15] == null) {
							row[15] = new BigDecimal(0);
						}
						commonDangerItemInfoDto.setAmount(((BigDecimal) row[14]).doubleValue() + ((BigDecimal) row[15]).doubleValue());
					}
					index = 15;
					if ((BigDecimal) row[index] != null) {
						commonDangerItemInfoDto.setChgAmount(((BigDecimal) row[index++]).doubleValue());
					}else{
						index++;
					}
					if ((BigDecimal) row[index] != null) {
						commonDangerItemInfoDto
								.setPremium(((BigDecimal) row[index++])
										.doubleValue());
					}else{
						index++;
					}
					if ((BigDecimal) row[index] != null) {
						commonDangerItemInfoDto
								.setChgPremium(((BigDecimal) row[index++])
										.doubleValue());
					}else{
						index++;
					}
					commonDangerItemInfoDto.setCalculateFlag((String) row[index++]);
					commonDangerItemInfoDto.setCurrency2((String) row[index++]);
					if ((BigDecimal) row[index] != null) {
						commonDangerItemInfoDto
								.setExchangeRate(((BigDecimal) row[index++])
										.doubleValue());
					}else{
						index++;
					}
					if ((BigDecimal) row[index] != null) {
						commonDangerItemInfoDto
								.setTolPremium(((BigDecimal) row[index++])
										.doubleValue());
					}else{
						index++;
					}
					commonDangerItemInfoDto.setEndorseFlag((String) row[index++]);
					if ((BigDecimal) row[index] != null) {
						commonDangerItemInfoDto
								.setLimitFee(((BigDecimal) row[index++])
										.doubleValue());
					}else{
						index++;
					}
					if ((BigDecimal) row[index] != null) {
						commonDangerItemInfoDto
								.setChgLimitFee(((BigDecimal) row[index++])
										.doubleValue());
					}else{
						index++;
					}
					if ((BigDecimal) row[index] != null) {
						commonDangerItemInfoDto
								.setLimit03Fee(((BigDecimal) row[index++])
										.doubleValue());
					}else{
						index++;
					}
					if ((BigDecimal) row[index] != null) {
						commonDangerItemInfoDto
								.setChgLimit03Fee(((BigDecimal) row[index++])
										.doubleValue());
					}
					collection.add(commonDangerItemInfoDto);
				} catch (Exception e) {
					e.printStackTrace();
					throw new Exception(internal.getText("undwrt.action.commonDangerRisk.queryDataError"));
				}
			}
		}// end if
		return collection;
	}

	/**
	 * 要保書危險單位查詢聲明.
	 * 
	 * @param businessNo
	 *            業務號
	 * @param riskCode
	 *            險種代碼
	 * @param businessType
	 *            業務類型
	 * @return 聲明的sql
	 * @throws Exception
	 *             異常
	 * @see com.sinosoft.undwrt.undwrtDeal.service.facade.GetItemKindInfoService#getStatementT(java.lang.String,
	 *      java.lang.String, java.lang.String)
	 */
	public String getStatementT(String businessNo, String riskCode,
			String businessType) throws Exception {
		String statement = "";
		if (riskCode.startsWith("26") || riskCode.startsWith("27")
				|| riskCode.equals("0301")) {
			statement = "SELECT KindCode,KindName,ItemCode,ItemDetailName,Discount"
					+ ", SUM(quantity) AS SumQuantity"
					+ ", SUM(VALUE) AS SumValue, SUM(amount) AS SumAmount"
					+ ", SUM(premium) AS SumPremium FROM PrpTitemkind "
					+ " Where ProposalNo='"
					+ businessNo
					+ "'"
					+ " GROUP BY KindCode,KindName,ItemCode,ItemDetailName,Discount";
		} else {
			String limitstatment1 = "";
			String limitstatment2 = "";
			if (!"1598".equals(riskCode)) {
				limitstatment1 = " Prptlimit.Limitno=itemBaseInfo.itemkindNo and ";
				limitstatment2 = " tlimit.Limitno=itemBaseInfo.itemkindNo and ";
			}
			statement = "select itemBaseInfo.businessNo, " +		// 业务号
							"itemBaseInfo.riskCode, " +				// 险种代码
							"itemBaseInfo.itemKindNo, " +			// 標的物序號
							"itemBaseInfo.kindCode, " + 			// 险别代码
							"itemBaseInfo.kindName, " + 			// 险别名称
							"itemBaseInfo.itemCode, " + 			// 标的项目
							"itemBaseInfo.itemDetailName, " + 		// 标的名称
							"itemBaseInfo.addressno, " + 			// 地址序號
							"itemBaseInfo.addresscode, " + 			// 危险单位标的邮政编码
							"itemBaseInfo.addressName, " + 			// 危险单位地址名称
							"itemBaseInfo.addressdetailinfo, " + 	// 危险单位詳細地址名称
							"itemBaseInfo.currency, " + 			// 原币
							"itemBaseInfo.amount," + 				// 原币保额
							"itemBaseInfo.premium," + 				// 原币保费
							"itemBaseInfo.buildingno," +			// 建筑序号
							"itemBaseInfo.itemFlag," +				// 主附險標誌 1主險 2附加險
							"itemBaseInfo.calculateFlag," + 		// 是否計入總保額
							"exch.currency1," + 
							"exch.exchangeRate1," + 
							"(select sum(premium1) from prptfee where proposalno='" + businessNo + "') tolPremium, " +
							"Prptlimit.Limitfee, " + 				// 每次事故责任限额
							"tlimit.Limitfee AS Limit03Fee " + 		// 累计责任限额
						"from (select item.proposalno businessNo," + // 业务号
								"item.riskCode," + 					// 险种代码
								"itemKindNo," + 					// 標的物序號
								"kindCode," + 						// 险别代码
								"kindName," + 						// 险别名称
								"itemCode," + 						// 标的项目
								"itemDetailName," + 				// 标的名称
								"item.addressno," + 				// 地址序號
								"addresscode," + 					// 危险单位标的邮政编码
								"addressName, " + 					// 危险单位地址名称
								"addressdetailinfo, " + 			// 危险单位詳細地址名称
								"item.currency," + 					// 原币
								"item.amount," + 					// 原币保额
								"item.premium," + 					// 原币保费
								"item.buildingno," + 				// 建筑序号
								"substr(item.flag,2,1) itemFlag," +	// 主附險標誌 1主險 2附加險
								"item.calculateFlag " + 			// 是否计算保额
							"from prptitemkind item " +
							"left join  prptaddress address " + 
								"on item.proposalno = address.proposalno " + 
								"and item.addressno = address.addressno " + 	// item.premium != 0.00不要显示保费为0的标的
						"where item.proposalno ='" + businessNo + "') itemBaseInfo " + 	// "' and item.premium != 0.00) " +
						"left join prptfee exch " + 
							"on itemBaseInfo.currency = exch.currency " +
							"and itemBaseInfo.businessNo = exch.proposalno " +
						"left join Prptlimit on " + limitstatment1 + 
							" Prptlimit.Limittype='02' " +			// 累计责任限额
							"and Prptlimit.Proposalno='" + businessNo + "' " +
						"left join Prptlimit tlimit on " + limitstatment2 +
							" tlimit.Limittype='03' " +
							"and tlimit.Proposalno='" + businessNo + "' " +
						"ORDER BY itemkindNo,itemCode";
		}
		return statement;
	}

	/**
	 * 報價單審核的查詢聲明.
	 * 
	 * @param businessNo
	 *            業務號
	 * @param riskCode
	 *            險種代碼
	 * @param businessType
	 *            業務類型
	 * @return 報價單審核的查詢聲明
	 * @throws Exception
	 *             the exception
	 */
	public String getStatementQ(String businessNo, String riskCode,
			String businessType) throws Exception {
		String statement = "";
		if (riskCode.startsWith("26") || riskCode.startsWith("27")
				|| riskCode.equals("0301")) {
			statement = "SELECT KindCode,KindName,ItemCode,ItemDetailName,Discount"
					+ ", SUM(quantity) AS SumQuantity"
					+ ", SUM(VALUE) AS SumValue, SUM(amount) AS SumAmount"
					+ ", SUM(premium) AS SumPremium FROM PrpTitemkind "
					+ " Where ProposalNo='"
					+ businessNo
					+ "'"
					+ " GROUP BY KindCode,KindName,ItemCode,ItemDetailName,Discount";
		} else {
			// add by zhouhui 20090817 begin
			String limitstatment1 = "";
			String limitstatment2 = "";
			if (!"1598".equals(riskCode)) {
				limitstatment1 = " Prpqlimit.Limitno=itemBaseInfo.itemkindNo and ";
				limitstatment2 = " tlimit.Limitno=itemBaseInfo.itemkindNo and ";
			}
			// add by zhouhui 20090817 end
			statement = "select itemBaseInfo.businessNo,"
					+ // 业务号
					"itemBaseInfo.riskCode,"
					+ // 险种代码
					"itemBaseInfo.itemKindNo,"
					+ "itemBaseInfo.kindCode,"
					+ // 险别代码
					"itemBaseInfo.kindName,"
					+ // 险别名称
					"itemBaseInfo.itemCode,"
					+ // 标的项目
					"itemBaseInfo.itemDetailName,"
					+ // 标的名称
					"itemBaseInfo.addressno,"
					+ "itemBaseInfo.addresscode,"
					+ // 危险单位标的邮政编码
					"itemBaseInfo.addressName,"
					+ // 危险单位地址名称
					"itemBaseInfo.currency,"
					+ // 原币
					"itemBaseInfo.amount,"
					+ // 原币保额
					"itemBaseInfo.premium,"
					+ // 原币保费
					"itemBaseInfo.calculateFlag," + "exch.currency1,"
					+ "exch.exchangeRate1,"
					+ "(select sum(premium1) from prptfee where proposalno='"
					+ businessNo
					+ "') tolPremium "
					+
					// add by douzx 20081126 begin
					" ,Prpqlimit.Limitfee "
					+ // 每次事故责任限额
					" ,tlimit.Limitfee AS Limit03Fee "
					+ // 累计责任限额
						// add by douzx 20081126 end
					"from "
					+ "(select item.proposalno businessNo,"
					+ // 业务号
					"item.riskCode,"
					+ // 险种代码
					"itemKindNo,"
					+ "kindCode,"
					+ // 险别代码
					"kindName,"
					+ // 险别名称
					"itemCode,"
					+ // 标的项目
					"itemDetailName,"
					+ // 标的名称
					"item.addressno,"
					+ "addresscode,"
					+ // 危险单位标的邮政编码
					"addressName,"
					+ // 危险单位地址名称
					"item.currency,"
					+ // 原币
					"item.amount,"
					+ // 原币保额
					"item.premium,"
					+ // 原币保费
					"item.calculateFlag "
					+ // 是否计算保额
					"from prpqitemkind item left join  prpqaddress address "
					+ "on  item.proposalno = address.proposalno "
					+ "and item.addressno = address.addressno "
					+ // item.premium != 0.00不要显示保费为0的标的
					"where  item.proposalno ='"
					+ businessNo
					+ "') "
					+
					// "' and item.premium != 0.00) " +
					"itemBaseInfo  left join prptfee  exch "
					+ "on itemBaseInfo.currency = exch.currency and itemBaseInfo.businessNo = exch.proposalno "
					+
					// add by douzx 20081126 每次事故责任限额 begin
					// modify by zhouhui 20090817 begin
					" left join Prpqlimit on  "
					+ limitstatment1
					+ "  Prpqlimit.Limittype='02' and Prpqlimit.Proposalno='"
					+ businessNo
					+ "' "
					+
					// 累计责任限额
					" left join Prpqlimit tlimit on  "
					+ limitstatment2
					+
					// modify by zhouhui 20090817 end
					"  tlimit.Limittype='03' and tlimit.Proposalno='"
					+ businessNo + "' " +
					// add by douzx 20081126 每次事故责任限额 end
					" ORDER BY itemkindNo,itemCode";
			// System.out.println("<<<<<<<<<<<<_________"+statement);
		}
		return statement;
	}

	/**
	 * 保書危險單位查詢聲明.
	 * 
	 * @param businessNo
	 *            業務號
	 * @param riskCode
	 *            險種代碼
	 * @param businessType
	 *            業務類型
	 * @return 聲明的sql
	 * @throws Exception
	 *             異常
	 * @see com.sinosoft.undwrt.undwrtDeal.service.facade.GetItemKindInfoService#getStatementP(java.lang.String,
	 *      java.lang.String, java.lang.String)
	 */
	public String getStatementP(String businessNo, String riskCode,
			String businessType) throws Exception {
		String statement = "";
		if (riskCode.startsWith("26") || riskCode.startsWith("27")
				|| riskCode.equals("0301")) {
			statement = "SELECT KindCode,KindName,ItemCode,ItemDetailName,Discount"
					+ ", SUM(quantity) AS SumQuantity"
					+ ", SUM(VALUE) AS SumValue, SUM(amount) AS SumAmount"
					+ ", SUM(premium) AS SumPremium FROM PrpCitemkind "
					+ " WHERE PolicyNo='"
					+ businessNo
					+ "'"
					+ " GROUP BY KindCode,KindName,ItemCode,ItemDetailName,Discount";
		} else {
			// add by zhouhui 20090817 begin
			String limitstatment1 = "";
			String limitstatment2 = "";
			if (!"1598".equals(riskCode)) {
				limitstatment1 = " Prpclimit.Limitno=itemBaseInfo.itemkindNo  and ";
				limitstatment2 = "  climit.Limitno=itemBaseInfo.itemkindNo and ";
			}
			// add by zhouhui 20090817 end
			statement = "select itemBaseInfo.businessNo,"
					+ // 业务号
					"itemBaseInfo.riskCode,"
					+ // 险种代码
					"itemBaseInfo.itemKindNo,"
					+ "itemBaseInfo.kindCode,"
					+ // 险别代码
					"itemBaseInfo.kindName,"
					+ // 险别名称
					"itemBaseInfo.itemCode,"
					+ // 标的项目
					"itemBaseInfo.itemDetailName,"
					+ // 标的名称
					"itemBaseInfo.addressno,"
					+ "itemBaseInfo.addresscode,"
					+ // 危险单位标的邮政编码
					"itemBaseInfo.addressName,"
					+ // 危险单位地址名称
					"itemBaseInfo.currency,"
					+ // 原币
					"itemBaseInfo.amount,"
					+ // 原币保额
					"itemBaseInfo.premium,"
					+ // 原币保费
					"itemBaseInfo.calculateFlag," + "exch.currency1,"
					+ "exch.exchangeRate1,"
					+ "(select sum(premium1) from prpcfee where policyno='"
					+ businessNo
					+ "') tolPremium "
					+
					// add by douzx 20081222 begin
					" ,Prpclimit.Limitfee "
					+ // 每次事故责任限额
					" ,climit.Limitfee AS Limit03Fee "
					+ // 累计责任限额
						// add by douzx 20081222 end
					"from "
					+ "(select item.policyno businessNo,"
					+ // 业务号
					"item.riskCode,"
					+ // 险种代码
					"itemKindNo,"
					+ "kindCode,"
					+ // 险别代码
					"kindName,"
					+ // 险别名称
					"itemCode,"
					+ // 标的项目
					"itemDetailName,"
					+ // 标的名称
					"item.addressno,"
					+ "addresscode,"
					+ // 危险单位标的邮政编码
					"addressName,"
					+ // 危险单位地址名称
					"item.currency,"
					+ // 原币
					"item.amount,"
					+ // 原币保额
					"item.premium,"
					+ // 原币保费
					"item.calculateFlag "
					+ // 是否计算保额
					"from prpcitemkind item left join  prpcaddress address "
					+ "on  item.policyno = address.policyno "
					+ "and item.addressno = address.addressno "
					+ "where  item.policyno ='"
					+ businessNo
					+ "') "
					+ "itemBaseInfo  left join prpcfee  exch "
					+ "on itemBaseInfo.currency = exch.currency and itemBaseInfo.businessNo = exch.policyno "
					+
					// add by douzx 20081222 每次事故责任限额 begin
					// modify by zhouhui 20090817 begin
					" left join Prpclimit on "
					+ limitstatment1
					+ "  Prpclimit.Limittype='02' and Prpclimit.policyno='"
					+ businessNo
					+ "' "
					+
					// 累计责任限额
					" left join Prpclimit climit on  "
					+ limitstatment2
					+ "  climit.Limittype='03' and climit.policyno='"
					+ businessNo + "' " +
					// add by zhouhui 20090817 end
					// add by douzx 20081222 每次事故责任限额 end
					" ORDER BY itemkindNo,itemcode ";
		}
		return statement;
	}

	/**
	 *  批單危險單位查詢聲明.
	 * 
	 * @param businessNo
	 *            業務號
	 * @param riskCode
	 *            險種代碼
	 * @param businessType
	 *            業務類型
	 * @return 聲明的sql
	 * @throws Exception
	 *             異常
	 * @see com.sinosoft.undwrt.undwrtDeal.service.facade.GetItemKindInfoService#getStatementE(java.lang.String,
	 *      java.lang.String, java.lang.String)
	 */
	public String getStatementE(String businessNo, String riskCode,
			String businessType) throws Exception {
		String statement = "";
		String policyNo = "";
		BLPrpPhead blPrpPhead = new BLPrpPhead();
		blPrpPhead.getData(businessNo);
		if (blPrpPhead.getSize() > 0) {
			policyNo = blPrpPhead.getArr(0).getPolicyNo();
		}
		if (riskCode.startsWith("26") || riskCode.startsWith("27")
				|| riskCode.equals("0301")) {
			statement = " SELECT i1.KindCode,i1.KindName,i1.ItemCode,i1.ItemDetailName"
					+ " ,i1.Discount,SUM(i1.Quantity) as SumQuantity"
					+ " ,SUM(i1.value) as SumValue, sum(i1.Amount) as SumAmount"
					+ " ,SUM(i1.Premium) as SumPremium"
					+ " ,(select sum(i2.ChgQuantity) from Prppitemkind i2"
					+ " where i2.Policyno = i1.Policyno and i2.Endorseno ='"
					+ businessNo
					+ "'"
					+ " and i2.Kindcode = i1.Kindcode and (i2.Itemcode = i1.Itemcode or (i2.Itemcode is null and i1.Itemcode is null))) as SumChgQuantity"
					+ " ,(select SUM(i2.ChgAmount) from Prppitemkind i2"
					+ " where i2.Policyno = i1.Policyno and i2.Endorseno ='"
					+ businessNo
					+ "'"
					+ " and i2.Kindcode = i1.Kindcode and (i2.Itemcode = i1.Itemcode or (i2.Itemcode is null and i1.Itemcode is null))) as SumChgAmount"
					+ " ,(select SUM(i2.ChgPremium) from Prppitemkind i2"
					+ " where i2.Policyno = i1.Policyno and i2.Endorseno ='"
					+ businessNo
					+ "'"
					+ " and i2.Kindcode = i1.Kindcode and (i2.Itemcode = i1.Itemcode or (i2.Itemcode is null and i1.Itemcode is null))) as SumChgPremium"
					+ " FROM PrpCitemkind i1"
					+ " WHERE i1.PolicyNo='"
					+ policyNo
					+ "'"
					+ " GROUP BY i1.Policyno,i1.KindCode,i1.KindName,i1.ItemCode,i1.ItemDetailName,i1.Discount";
		} else {
			String limitstatment1 = "";
			String limitstatment2 = "";
			String limitstatment3 = "";
			String limitstatment4 = "";
			if (!"1598".equals(riskCode)) {
				limitstatment1 = " plimit.Limitno=itemBaseInfo.itemkindNo and  ";
				limitstatment2 = " climit.Limitno=itemBaseInfo.itemkindNo and ";
				limitstatment3 = " p03limit.Limitno=itemBaseInfo.itemkindNo and ";
				limitstatment4 = " c03limit.Limitno=itemBaseInfo.itemkindNo and ";
			}
			statement = "select itemBaseInfo.businessNo," + 	// 业务号
							"itemBaseInfo.riskCode," + 			// 险种代码
							"itemBaseInfo.itemKindNo," + 		// 地址序號	
							"itemBaseInfo.kindCode," + 			// 险别代码
							"itemBaseInfo.kindName," + 			// 险别名称
							"itemBaseInfo.itemCode," + 			// 标的项目
							"itemBaseInfo.itemDetailName," + 	// 标的名称
							"itemBaseInfo.addressno," + 		// 地址序號
							"itemBaseInfo.addresscode," + 		// 危险单位标的邮政编码
							"itemBaseInfo.addressName," + 		// 危险单位地址名称
							"itemBaseInfo.addressdetailinfo," +	// 詳細地址
							"itemBaseInfo.itemFlag, " +			// 主附險標誌
							"itemBaseInfo.buildingno, " +		// 建築序號
							"itemBaseInfo.currency," + 			// 原币
							"decode(itemBaseInfo.EndorseFlag,'I',0,itemBaseInfo.amount)," + // 原币保额
							"itemBaseInfo.chgAmount," + 		// 变化保额
							"decode(itemBaseInfo.EndorseFlag,'I',0,itemBaseInfo.premium)," + // 原币保费
							"itemBaseInfo.chgPremium," + 		// 变化保费
							"itemBaseInfo.calculateFlag," + "exch.currency1," + 
							"exch.exchangeRate1," + 
							"(select sum(premium1) from prppfee where endorseno='" + businessNo + "') tolPremium," + 
							"itemBaseInfo.endorseFlag," + 		// 批改标示
							"climit.limitfee," + 				// 每次事故责任限额
							"plimit.chglimitfee," + 			// 变化每次事故责任限额
							"c03limit.limitfee AS limit03fee," + 		// 累计责任限额
							"p03limit.chglimitfee AS chglimit03fee " + 	// 变化累计责任限额
						"from (select item.endorseno businessNo, " + 	// 业务号
								"item.riskCode, " + 						// 险种代码
								"itemKindNo, " +							// 險別序號
								"kindCode, " + 								// 险别代码
								"kindName, " + 								// 险别名称
								"itemCode, " + 								// 标的项目
								"itemDetailName, " + 						// 标的名称
								"item.addressno, " + 						// 地址序號
								"cpaddress.addresscode, " + 				// 危险单位标的邮政编码
								"cpaddress.addressName, " + 				// 危险单位地址名称
								"cpaddress.addressdetailinfo, " +			// 詳細地址
								"substr(item.Flag, 2, 1) itemFlag, " + 		// 主附險標誌
								"item.buildingno, " +						// 建築序號
								"item.currency," + 							// 原币
								"item.amount," + 							// 原币保额
								"item.chgAmount," + 						// 变化保额
								"item.premium," + 							// 原币保费
								"item.chgPremium," + 						// 變化保費
								"item.calculateFlag," + 					// 是否计算保额
								"substr(item.Flag,1,1) AS EndorseFlag " + 	// 批改标示（I－新增，D－删除，U－修改）
								"from prppitemkind item " +
								"left join  prppaddress address " +
									"on item.endorseno = address.endorseno " + 
									"and item.addressno = address.addressno " + 
								"left join prpcpaddress cpaddress " +
									"on item.policyno = cpaddress.policyno " +
									"and item.addressno = cpaddress.addressno " +
						"where item.endorseno ='" + businessNo + "' and item.policyno='" + policyNo + "') itemBaseInfo " +
							"left join prppfee exch " +
							"on itemBaseInfo.currency = exch.currency and itemBaseInfo.businessNo = exch.endorseno " +
							"left join Prpplimit plimit on " + limitstatment1 + 
							" plimit.Limittype='02' and plimit.endorseno='" + businessNo + "' " + 
							"left join Prpclimit climit on " + limitstatment2 + 
							" climit.Limittype='02' and climit.policyNo='" + policyNo + "' " +
							"left join Prpplimit p03limit on " + limitstatment3 + 
							" p03limit.Limittype='03' and p03limit.endorseno='" + businessNo + "' " + 
							"left join Prpclimit c03limit on " + limitstatment4 + 
							" c03limit.Limittype='03' and c03limit.policyNo='" + policyNo + "' " +
						"ORDER BY itemkindNo,itemcode";
		}
		return statement;
	}

	/**
	 * 獲取批單危險單位信息.
	 * 
	 * @param businessNo
	 *            業務號
	 * @param riskCode
	 *            the 險種代碼
	 * @param businessType
	 *            業務類型
	 * @return 滿足條件的記錄集合
	 * @throws Exception
	 *             異常
	 * @see com.sinosoft.undwrt.undwrtDeal.service.facade.GetItemKindInfoService#getItemInfoForE(java.lang.String,
	 *      java.lang.String, java.lang.String)
	 */
	public Collection getItemInfoForE(String businessNo, String riskCode,
			String businessType) throws Exception {
		InternationalizationUtil internal = new InternationalizationUtil();
		String statement = null;
		Collection collection = new ArrayList();
		CommonDangerItemInfoVo commonDangerItemInfoDto = null;
		if (businessType.equals("T")) { // 投保单
			statement = this.getStatementT(businessNo, riskCode, businessType);
		} else if (businessType.equals("P")) { // 保单
			statement = this.getStatementP(businessNo, riskCode, businessType);
		} else if (businessType.equals("E")) { // 批单
			statement = this.getStatementE(businessNo, riskCode, businessType);
		}
		List list = super.getSession().createSQLQuery(statement).list();
		Iterator it = list.iterator();
		if (!businessType.equals("E")) // 投保单和保单
		{
			while (it.hasNext()) {
				try {
					Object[] obj = (Object[]) it.next();
					int index = 0;
					commonDangerItemInfoDto = new CommonDangerItemInfoVo();
					commonDangerItemInfoDto.setBusinessNo(businessNo);
					commonDangerItemInfoDto.setRiskCode(riskCode);
					commonDangerItemInfoDto.setKindCode((String) obj[index++]);
					commonDangerItemInfoDto.setKindName((String) obj[index++]);
					commonDangerItemInfoDto.setItemCode((String) obj[index++]);
					commonDangerItemInfoDto
							.setItemDetailName((String) obj[index++]);
					commonDangerItemInfoDto
							.setQuantity(((BigDecimal) obj[index++])
									.doubleValue());
					if (((BigDecimal) obj[index++]).doubleValue() == 0) {
						commonDangerItemInfoDto.setValue(1);
					} else {
						commonDangerItemInfoDto
								.setValue(((BigDecimal) obj[index])
										.doubleValue());
					}
					commonDangerItemInfoDto
							.setDiscount(((BigDecimal) obj[index++])
									.doubleValue());
					commonDangerItemInfoDto
							.setAmount(((BigDecimal) obj[index++])
									.doubleValue());
					commonDangerItemInfoDto
							.setPremium(((BigDecimal) obj[index++])
									.doubleValue());
					collection.add(commonDangerItemInfoDto);
				} catch (Exception e) {
					e.printStackTrace();
					throw new Exception(
							internal.getText("undwrt.action.commonDangerRisk.queryDataError"));
				}
			}
		} else // 批单
		{
			while (it.hasNext()) {
				try {
					Object[] obj = (Object[]) it.next();
					int index = 0;
					commonDangerItemInfoDto = new CommonDangerItemInfoVo();
					commonDangerItemInfoDto.setBusinessNo(businessNo);
					commonDangerItemInfoDto.setRiskCode(riskCode);
					commonDangerItemInfoDto.setKindCode((String) obj[index++]);
					commonDangerItemInfoDto.setKindName((String) obj[index++]);
					commonDangerItemInfoDto.setItemCode((String) obj[index++]);
					commonDangerItemInfoDto
							.setItemDetailName((String) obj[index++]);
					commonDangerItemInfoDto
							.setQuantity(((BigDecimal) obj[index++])
									.doubleValue());
					if (((BigDecimal) obj[index++]).doubleValue() == 0) {
						commonDangerItemInfoDto.setValue(1);
					} else {
						commonDangerItemInfoDto
								.setValue(((BigDecimal) obj[index])
										.doubleValue());
					}
					commonDangerItemInfoDto
							.setDiscount(((BigDecimal) obj[index++])
									.doubleValue());
					commonDangerItemInfoDto
							.setAmount(((BigDecimal) obj[index++])
									.doubleValue());
					commonDangerItemInfoDto
							.setPremium(((BigDecimal) obj[index++])
									.doubleValue());
					commonDangerItemInfoDto
							.setChgAmount(((BigDecimal) obj[index++])
									.doubleValue());
					commonDangerItemInfoDto
							.setChgPremium(((BigDecimal) obj[index++])
									.doubleValue());
					commonDangerItemInfoDto
							.setChgQuantity(((BigDecimal) obj[index++])
									.doubleValue());
					collection.add(commonDangerItemInfoDto);
				} catch (Exception e) {
					e.printStackTrace();
					throw new Exception(
							internal.getText("undwrt.action.commonDangerRisk.queryDataError"));
				}
			}
		}// end if
		return collection;
	}

}
