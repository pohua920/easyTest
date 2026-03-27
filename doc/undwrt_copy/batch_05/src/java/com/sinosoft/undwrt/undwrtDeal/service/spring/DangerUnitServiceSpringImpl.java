package com.sinosoft.undwrt.undwrtDeal.service.spring;

import ins.framework.dao.GenericDaoHibernate;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import com.sinosoft.common.schema.model.PrpPmain;
import com.sinosoft.prpall.dto.domain.PrpPmainDto;
import com.sinosoft.prpall.resource.dtofactory.domain.DBPrpPmain;
import com.sinosoft.prpins.policy.service.facade.EndorseService;
import com.sinosoft.undwrt.common.model.PrpDcode;
import com.sinosoft.undwrt.common.model.PrpDcodeId;
import com.sinosoft.undwrt.common.vo.CommonDangerItemInfoVo;
import com.sinosoft.undwrt.pub.InternationalizationUtil;
import com.sinosoft.undwrt.undwrtDeal.service.facade.DangerUnitService;

/**
 * 獲取危險單位主信息實現類.
 */
public class DangerUnitServiceSpringImpl extends GenericDaoHibernate implements
		DangerUnitService {

	/** 屬性批單處理接口. */
	private EndorseService endorseService;

	/**
	 * 獲取危險單位主信息.
	 * 
	 * @param businessNo
	 *            業務號
	 * @param dangerNo
	 *            危險單位號
	 * @param businessType
	 *            業務類型
	 * @return 符合條件的危險單位信息類集合
	 * @throws Exception
	 *             異常
	 * @see com.sinosoft.undwrt.undwrtDeal.service.facade.DangerUnitService#getDangerUnitItemInfo(java.lang.String,
	 *      java.lang.String, java.lang.String)
	 */
	public Collection getDangerUnitItemInfo(String businessNo, String dangerNo,
			String businessType) throws Exception {
		InternationalizationUtil internal = new InternationalizationUtil();
		Collection dangerUnitItemInfo = null;
		String statementStr = null;
		CommonDangerItemInfoVo commonDangerItemInfoDto = null;
		if (businessType.equals("T")) {
			statementStr = "select item.proposalno businessNo,item.isfacultative,item.riskLevel,item.riskLevelDesc,item.riskClass,item.riskClassDesc,item.retentionValue,item.retCurrency,item.riskCode,item.dangerNo,item.serialno,"
					+ "item.kindFlag,item.kindCode,item.kindName,item.itemCode,item.itemDetailName,"
					+ "item.postCode,item.addressName,item.currency,"
					+ "item.amount,item.premium,item.calculateFlag,exch.currency2,exch.exchangeRate2,"
					+ " (select sum(amount2) from prptfee where proposalno='"
					+ businessNo
					+ "') tolAmount,"
					+ " (select sum(premium2) from prptfee where proposalno='"
					+ businessNo
					+ "') tolPremium,"
					+ "item.sameriskno"
					+ " from prptdangeritem item,prptfee  exch "
					+ " where item.proposalno = exch.proposalno"
					+ " and item.currency = exch.currency "
					+ " and item.dangerno ='"
					+ dangerNo
					+ "' and item.proposalno='"
					+ businessNo
					+ "'"
					// 以上部分是查询已存在的危险单位
					+ " union"
					+ " select itemkind.proposalno businessNo,'0','0','0','0','0',0,'0',itemkind.riskCode,0 as dangerNo,itemkind.itemkindno as serialno,"
					+ "'0',itemkind.kindCode,itemkind.kindName,itemkind.itemCode,itemkind.itemDetailName,"
					+ "addr.addresscode,addr.addressName,itemkind.currency,"
					+ "itemkind.amount,itemkind.premium,itemkind.calculateFlag,exch.currency2,exch.exchangeRate2,"
					+ " (select sum(amount2) from prptfee where proposalno='"
					+ businessNo
					+ "') tolAmount,"
					+ " (select sum(premium2) from prptfee where proposalno='"
					+ businessNo
					+ "') tolPremium,"
					+"''"
					+ " from prptitemkind itemkind,prptfee  exch,prptaddress addr "
					+ "where itemkind.proposalno = exch.proposalno "
					+ " and itemkind.currency = exch.currency "
					+ " and itemkind.proposalno = addr.proposalno(+) "
					+ " and itemkind.addressno = addr.addressno(+) "
					+ " and itemkind.itemkindno not  in ("
					+ " select item.serialno  from prptdangeritem item  "
					+ " where item.proposalno=itemkind.proposalno and item.dangerno ='"
					+ dangerNo
					+ "') "
					+ " and itemkind.proposalno='"
					+ businessNo + "'" + " order by serialno";
			// 以上部分是查询存在在prptitemkind，但是在prptdangeritem中没有 的该投保单的信息
		} else if (businessType.equals("P")) {
			statementStr = "select item.policyno businessNo,item.isfacultative,item.riskLevel,item.riskLevelDesc,"
					+ "item.riskClass,item.riskClassDesc,item.retentionValue,item.reTCurrency,"
					+ "item.riskCode,"
					+ // 险种代码
					"dangerNo,"
					+ // 危险单位主序号
					"serialNo,"
					+ // 危险单位标的子序号
					"kindFlag,"
					+ // 险别归类标志
					"kindCode,"
					+ // 险别代码
					"kindName,"
					+ // 险别名称
					"itemCode,"
					+ // 标的项目
					"itemDetailName,"
					+ // 标的名称
					"postCode,"
					+ // 危险单位标的邮政编码
					"addressName,"
					+ // 危险单位地址名称
					"item.currency,"
					+ // 原币
					"item.amount,"
					+ // 原币保额
					"item.premium,"
					+ // 原币保费
					"item.calculateFlag,"
					+ // 是否计算保额
					"currency2,"
					+ // 折币(与支付币种一致)
					"exchangeRate2, "
					+ // 兑换率
					"(select sum(amount2) from prpcfee where policyno='"
					+ businessNo
					+ "') tolAmount, "
					+ "(select sum(premium2) from prpcfee where policyno='"
					+ businessNo
					+ "') tolPremium, "
					+ "item.sameriskno "
					+ "from prpcdangeritem item, prpcfee  exch "
					+ "where item.policyno = exch.policyno  and item.currency = exch.currency "
					+ "and item.policyno='"
					+ businessNo
					+ "' and dangerno='"
					+ dangerNo + "'";
		} else if (businessType.equals("E")) {
			PrpPmain prpPmain = endorseService
					.getPrpPheadByEndorseNo(businessNo).getPrpPmains().get(0);
			String policyno = prpPmain.getPolicyNo();
			statementStr = // 当前危险单位未包含的变化的标的
			" select itemKind.endorseNo businessNo,'0','0' as riskLevel,'0' as riskLevelDesc,'0' as riskClass,'0' as riskClassDesc,0 as retentionValue,"
					+ "'0' as reTCurrency,itemKind.riskCode,0 as dangerno, itemKind.ItemKindNo serialno,'0' kindFlag,"
					+ " itemKind.kindCode,itemKind.kindName,itemKind.itemCode,itemKind.itemDetailName,addr.addresscode postCode,"
					+ " addr.addressName,itemKind.currency,decode(substr(itemkind.flag, 0, 1),'I',0,itemKind.amount) amount,itemKind.Chgamount,"
					+ " decode(substr(itemkind.flag, 0, 1),'I',0,itemKind.premium) premium,itemKind.Chgpremium,itemKind.calculateFlag,"
					+ " substr(itemkind.flag, 0, 1) flag, "
					+ " exch.currency2,exch.exchangerate2, "
					+ " (select sum(amount2) from prpcpfee  where policyno = '"
					+ policyno
					+ "') tolAmount,"
					+ " (select sum(premium2) from prpcpfee  where policyno = '"
					+ policyno
					+ "') tolPremium, "
					+ "''"
					+ " from   prppitemkind itemKind,Prpcpfee exch,prpcpaddress addr "
					+ " where  itemKind.Policyno = exch.policyno and itemKind.Currency = exch.currency and "
					+ " itemKind.Policyno = addr.policyno(+) and itemKind.AddressNo = addr.AddressNo(+) "
					+ " and ItemKindNo not in "
					+ " (select item.serialno from prppdangeritem item "
					+ " where item.endorseNo = '"
					+ businessNo
					+ "' and item.dangerno = '"
					+ dangerNo
					+ "') "
					+ " AND endorseNo = '"
					+ businessNo
					+ "' "
					+ " Union ALL "
					// 当前危险单位包含的变化的标的
					+ " select item.endorseNo businessNo,item.isfacultative,item.riskLevel,item.riskLevelDesc,item.riskClass,item.riskClassDesc,item.retentionValue,"
					+ "item.reTCurrency,item.riskCode,item.dangerNo,item.serialno, "
					+ " item.kindFlag,item.kindCode,item.kindName,item.itemCode,item.itemDetailName, "
					+ " item.postCode,item.addressName,item.currency,item.amount,item.chgAmount, "
					+ " item.premium,item.chgPremium,item.calculateFlag,substr(item.flag, 0, 1) flag, "
					+ " exch.currency2,exch.exchangeRate2, "
					+ " (select sum(amount2) from prpcpfee  where policyno = '"
					+ policyno
					+ "') tolAmount, "
					+ " (select sum(premium2)from prpcpfee  where policyno = '"
					+ policyno
					+ "') tolPremium, "
					+ "item.sameriskno"
					+ " from   prppdangeritem item, prpcpfee exch   "
					+ " where  item.currency = exch.currency "
					+ " and item.endorseNo = '"
					+ businessNo
					+ "' "
					+ " and dangerno = '"
					+ dangerNo
					+ "' "
					+ " and exch.policyno='"
					+ policyno
					+ "' "
					+ "Union ALL "
					// 当前危险单位未包含的未变化的标的
					+ " select '"
					+ businessNo
					+ "' businessNo,'0','0','0','0','0',0,'0',itemKind.riskCode,0, itemKind.ItemKindNo serialno,'0' kindFlag, "
					+ " itemKind.kindCode,itemKind.kindName,itemKind.itemCode,itemKind.itemDetailName,addr.addresscode postCode, "
					+ " addr.addressName,itemKind.currency,itemKind.amount,0 Chgamount,"
					+ " itemKind.premium,0 Chgpremium,itemKind.calculateFlag,"
					+ " ' ' Flag,exch.currency2,exch.exchangerate2,"
					+ " (select sum(amount2) from prpcpfee  where policyno = '"
					+ policyno
					+ "') tolAmount,"
					+ " (select sum(premium2) from prpcpfee  where policyno = '"
					+ policyno
					+ "') tolPremium, "
					+"''"
					+ " from   prpcpitemkind itemKind,Prpcpfee exch,prpcpaddress addr"
					+ " where  itemKind.Policyno = exch.policyno and itemKind.Currency = exch.currency and "
					+ " itemKind.Policyno = addr.policyno(+) and itemKind.AddressNo = addr.AddressNo(+)"
					+ " and itemKind.ItemKindNo not in"
					+ " (select itemKindNo from prppItemKind where endorseNo = '"
					+ businessNo
					+ "')"
					+ " and itemKind.ItemKindNo not in"
					+ " (select SerialNo from prppdangeritem where endorseNo = '"
					+ businessNo
					+ "' and dangerNo = '"
					+ dangerNo
					+ "')"
					+ " AND itemKind.policyno = '"
					+ policyno
					+ "' "
					+ " order by serialno ";
		}

		// System.out.println("==statementStr="+statementStr);
		List list = super.getSession().createSQLQuery(statementStr).list();
		Iterator it = list.iterator();
		Collection collection = new ArrayList();
		if (!businessType.equals("E")) // 投保单,保单
		{
			while (it.hasNext()) {
				try {
					Object[] obj = (Object[]) it.next();
					int index = 0;
					commonDangerItemInfoDto = new CommonDangerItemInfoVo();
					commonDangerItemInfoDto
							.setBusinessNo((String) obj[index++]);
					commonDangerItemInfoDto.setIsFacultative((String) obj[index++]);
					commonDangerItemInfoDto.setRiskLevel((String) obj[index++]);
					commonDangerItemInfoDto
							.setRiskLevelDesc((String) obj[index++]);
					commonDangerItemInfoDto.setRiskClass((String) obj[index++]);
					commonDangerItemInfoDto
							.setRiskClassDesc((String) obj[index++]);
					if(obj[index] != null){
						commonDangerItemInfoDto.setRetentionValue(((BigDecimal) obj[index++]).doubleValue());
					}else{
						index++;
					}
					commonDangerItemInfoDto
							.setRetCurrency((String) obj[index++]);
					commonDangerItemInfoDto.setRiskCode((String) obj[index++]);
					commonDangerItemInfoDto
							.setDangerNo(((BigDecimal) obj[index++]).intValue());
					commonDangerItemInfoDto
							.setItemKindNo(((BigDecimal) obj[index++])
									.intValue());
					commonDangerItemInfoDto.setKindFlag((String) obj[index++]);
					commonDangerItemInfoDto.setKindCode((String) obj[index++]);
					commonDangerItemInfoDto.setKindName((String) obj[index++]);
					commonDangerItemInfoDto.setItemCode((String) obj[index++]);
					commonDangerItemInfoDto
							.setItemDetailName((String) obj[index++]);
					commonDangerItemInfoDto.setPostCode((String) obj[index++]);
					commonDangerItemInfoDto
							.setAddressName((String) obj[index++]);
					commonDangerItemInfoDto.setCurrency((String) obj[index++]);
					commonDangerItemInfoDto
							.setAmount(((BigDecimal) obj[index++])
									.doubleValue());
					commonDangerItemInfoDto
							.setPremium(((BigDecimal) obj[index++])
									.doubleValue());
					commonDangerItemInfoDto
							.setCalculateFlag((String) obj[index++]);
					commonDangerItemInfoDto.setCurrency2((String) obj[index++]);
					commonDangerItemInfoDto
							.setExchangeRate(((BigDecimal) obj[index++])
									.doubleValue());
					commonDangerItemInfoDto
							.setTolAmount(((BigDecimal) obj[index++])
									.doubleValue());
					commonDangerItemInfoDto
							.setTolPremium(((BigDecimal) obj[index++])
									.doubleValue());
					commonDangerItemInfoDto.setSameRiskNo((String) obj[index++]);
					collection.add(commonDangerItemInfoDto);
				} catch (Exception e) {
					e.printStackTrace();
					throw new Exception(
							internal.getText("undwrt.action.commonDangerRisk.queryDataError"));
				}
			}
		} else if (businessType.equals("E"))// 批单
		{
			while (it.hasNext()) {
				try {
					Object[] obj = (Object[]) it.next();
					int index = 0;
					commonDangerItemInfoDto = new CommonDangerItemInfoVo();
					commonDangerItemInfoDto
							.setBusinessNo((String) obj[index++]);
					commonDangerItemInfoDto.setIsFacultative((String) obj[index++]);
					commonDangerItemInfoDto.setRiskLevel((String) obj[index++]);
					commonDangerItemInfoDto
							.setRiskLevelDesc((String) obj[index++]);
					commonDangerItemInfoDto.setRiskClass((String) obj[index++]);
					commonDangerItemInfoDto
							.setRiskClassDesc((String) obj[index++]);
					if(obj[index] != null){
						commonDangerItemInfoDto.setRetentionValue(((BigDecimal) obj[index++]).doubleValue());
					}else{
						index++;
					}
					
					commonDangerItemInfoDto
							.setRetCurrency((String) obj[index++]);
					commonDangerItemInfoDto.setRiskCode((String) obj[index++]);
					commonDangerItemInfoDto
							.setDangerNo(((BigDecimal) obj[index++]).intValue());
					commonDangerItemInfoDto
							.setItemKindNo(((BigDecimal) obj[index++])
									.intValue());
					commonDangerItemInfoDto.setKindFlag((String) obj[index++]);
					commonDangerItemInfoDto.setKindCode((String) obj[index++]);
					commonDangerItemInfoDto.setKindName((String) obj[index++]);
					commonDangerItemInfoDto.setItemCode((String) obj[index++]);
					commonDangerItemInfoDto
							.setItemDetailName((String) obj[index++]);
					commonDangerItemInfoDto.setPostCode((String) obj[index++]);
					commonDangerItemInfoDto
							.setAddressName((String) obj[index++]);
					commonDangerItemInfoDto.setCurrency((String) obj[index++]);
					commonDangerItemInfoDto
							.setAmount(((BigDecimal) obj[index++])
									.doubleValue());
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
					commonDangerItemInfoDto.setFlag((String) obj[index++]);
					commonDangerItemInfoDto.setCurrency2((String) obj[index++]);
					commonDangerItemInfoDto
							.setExchangeRate(((BigDecimal) obj[index++])
									.doubleValue());
					commonDangerItemInfoDto
							.setTolAmount(((BigDecimal) obj[index++])
									.doubleValue());
					commonDangerItemInfoDto
							.setTolPremium(((BigDecimal) obj[index++])
									.doubleValue());
					commonDangerItemInfoDto.setSameRiskNo((String)obj[index++]);
					collection.add(commonDangerItemInfoDto);
				} catch (Exception e) {
					e.printStackTrace();
					throw new Exception(
							internal.getText("undwrt.action.commonDangerRisk.queryDataError"));
				}
			}
		}
		if (collection.size() == 0) {
			throw new Exception(
					internal.getText("undwrt.service.DangerUnitService.noRelevantInfor"));
		}
		return collection;
	}

	/**
	 * 獲取屬性批單處理接口.
	 * 
	 * @return 屬性批單處理接口的值
	 */
	public EndorseService getEndorseService() {
		return endorseService;
	}

	/**
	 * 設置屬性批單處理接口.
	 * 
	 * @param endorseService
	 *            待設置的批單處理接口的值
	 */
	public void setEndorseService(EndorseService endorseService) {
		this.endorseService = endorseService;
	}

}
