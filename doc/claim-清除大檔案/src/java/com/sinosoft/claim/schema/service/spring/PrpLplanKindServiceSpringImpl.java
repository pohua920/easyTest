package com.sinosoft.claim.schema.service.spring;
/**
 * 人伤跟踪信息接口实现类
 * @author 中科软
 */
import ins.framework.common.Page;
import ins.framework.common.QueryRule;
import ins.framework.common.ServiceFactory;
import ins.framework.dao.GenericDaoHibernate;
import ins.framework.utils.DataUtils;

import java.math.BigDecimal;//mantis：CLM0275 ，處理人員：DP0713，需求單編號：新核心-由 JDBC open db pool 連線 換成spring Hibernate方式query資料
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sinosoft.claim.common.ConstantCodes;
import com.sinosoft.claim.common.service.facade.CodeService;
import com.sinosoft.claim.common.util.HibernateUtils;
import com.sinosoft.claim.schema.model.PrpCitemCar;
import com.sinosoft.claim.schema.model.PrpCitemKind;
import com.sinosoft.claim.schema.model.PrpLclaim;
import com.sinosoft.claim.schema.model.PrpLcompensate;
import com.sinosoft.claim.schema.model.PrpLplanKind;
import com.sinosoft.claim.schema.model.PrpLplanKindId;
import com.sinosoft.claim.schema.service.facade.PrpCitemCarService;
import com.sinosoft.claim.schema.service.facade.PrpCitemKindService;
import com.sinosoft.claim.schema.service.facade.PrpLclaimService;
import com.sinosoft.claim.schema.service.facade.PrpLcompensateService;
import com.sinosoft.claim.schema.service.facade.PrpLplanKindService;
import com.sinosoft.claim.ui.control.action.UIExcludeClaimAction;
import com.sinosoft.sysframework.common.datatype.DateTime;
import com.sinosoft.sysframework.exceptionlog.UserException;
import com.sinosoft.utiall.blsvr.BLPrpDration;

public class PrpLplanKindServiceSpringImpl extends GenericDaoHibernate<PrpLplanKind, PrpLplanKindId> implements PrpLplanKindService {
	private PrpLcompensateService prpLcompensateService;
	private PrpLclaimService prpLclaimService;
	private PrpCitemKindService prpCitemKindService;
	private PrpCitemCarService prpCitemCarService;
	private CodeService codeService;

	@Override
	public void save(PrpLplanKind prpLPlanKind) throws Exception {
		logger.info("保存人伤跟踪信息");
		super.save(prpLPlanKind);

	}

	@Override
	public void save(List<PrpLplanKind> list) throws Exception {
		logger.info("保存人伤跟踪信息");
		for (int i = 0; i < list.size(); i++) {
			super.save(list.get(i));
		}
	}

	@Override
	public void delete(PrpLplanKindId prpLPlanKindId) throws Exception {
		logger.info("删除人伤跟踪信息编号为" + prpLPlanKindId + "的人伤跟踪信息");
		super.deleteByPK(PrpLplanKind.class, prpLPlanKindId);
	}

	@Override
	public PrpLplanKind findPrpLplanKind(PrpLplanKindId prpLPlanKindId) throws Exception {
		logger.info("查询人伤跟踪信息编号为" + prpLPlanKindId + "的人伤跟踪信息");
		return super.get(PrpLplanKind.class, prpLPlanKindId);
	}

	@Override
	public Page findPrpLplanKind(QueryRule queryRule, int pageNo, int pageSize) throws Exception {
		logger.info("获取人伤跟踪信息列表信息");
		return super.find(queryRule, pageNo, pageSize);
	}

	@Override
	public List<PrpLplanKind> findPrpLplanKind(QueryRule queryRule) throws Exception {
		return super.find(queryRule);
	}

	/**
	 * 根据人伤跟踪编号查询出人伤跟踪信息
	 * @param certiNo ：传入的人伤跟踪编号
	 * @return 返回人伤跟踪
	 */
	public PrpLplanKind findPrpLplanKind(String certiNo) throws Exception {
		PrpLplanKind prpLPlanKind = null;
		QueryRule queryRule = QueryRule.getInstance();
		queryRule.addEqual("id.certiNo", certiNo);
		List<PrpLplanKind> resultList = super.find(queryRule);
		if (resultList != null && resultList.size() > 0) {
			prpLPlanKind = resultList.get(0);
		}
		return prpLPlanKind;
	}

	@Override
	public List<PrpLplanKind> findPayLossByConditions(String compensateNo, Map<String,String> codeMap, String coinsType, String coinsFlag, double coinsRate, double sumPaid, Map<String, Object> serialNoMap, String damageDate, String isComBin) throws Exception {
		DateTime inputDate = DateTime.current();
		// 定义返回结果集合
		List<PrpLplanKind> prpLPlanKindList = new ArrayList<PrpLplanKind>();
		PrpLplanKind prpLPlanKind = null;
		double Exceptdeductiblepay = 0;
		int serialNo = 0;
		Map<String, PrpLplanKind> prpLPlanKindMap = new HashMap<String, PrpLplanKind>();
		// 损失标的
		String tempPayObjectSerial = "";//取自每条损失信息对应的赔付PayObjectSerialNo的值串(考虑1损失赔多人的情况)
		String[] tempKeyValue = null;//PayObjectSerialNo分割的（赔付对象：理赔金）值对
		String[] tempStr = null;//赔付对象序号，及其对应的理赔金（顺序存储）
		String tempKey = "";// 临时存储键值变量
		String prpccoins_serialno = ""; //联共保号码
		if(serialNoMap.get("PRPCCOINS_SERIALNO")!=null){
			prpccoins_serialno = String.valueOf(serialNoMap.get("PRPCCOINS_SERIALNO"));
		}
		PrpLcompensate prpLcompensate = getPrpLcompensateService().findPrpLcompensate(compensateNo);
		// 财产
		String bufferL = "select l.Compensateno,l.Policyno,m.Itemkindno,l.exchrate,"
				+ "l.Currency,l.Riskcode,l.Kindcode,l.PayObjectSerialNo,ROUND(l.Sumrealpay*l.exchRate,0) Sumrealpay,l.Exceptdeductiblepay Exceptdeductiblepay,c.Comcode,m.STARTDATE,m.ENDDATE  " + " from Prplloss l ,Prpcitemkind m,Prplcompensate c  "
				+ " where  m.Kindcode=l.Kindcode and m.Policyno = l.Policyno " + "and l.Compensateno= c.compensateNo and l.compensateNo='" + compensateNo + "' ";
		List<?> resultSetL = HibernateUtils.findbySql(super.getSession(), bufferL);
		for (int i = 0; i < resultSetL.size(); i++) {
			Object[] objs = (Object[]) resultSetL.get(i);
			DateTime startDate = new DateTime((Date) objs[11]);
			DateTime endDate = new DateTime((Date) objs[12]);
			if (startDate.compareTo(new DateTime(damageDate, DateTime.YEAR_TO_DAY)) > 0 || endDate.compareTo(new DateTime(damageDate, DateTime.YEAR_TO_DAY)) < 0) {
				PrpLclaim prpLclaim = getPrpLclaimService().findPrpLclaim(prpLcompensate.getClaimNo());
				if (new UIExcludeClaimAction().isExcluded(prpLclaim.getRegistNo())) {
					// 如果做过除外，则不再校验出险时间是否在保险期间内
				} else {
					throw new UserException(-98, -1149, "保單號：" + objs[1].toString() + "中的險別：" + objs[6].toString(), "出險時間不在保險期間！");
				}
			}
			// 数据规则 赔付对象序号1:赔付金额1;赔付对象序号2:赔付金额2;
			tempPayObjectSerial = DataUtils.getString(objs[7]);
			if (DataUtils.emptyToNull(tempPayObjectSerial) != null) {
				tempKeyValue = tempPayObjectSerial.split(";");// PayObjectSerialNo分割的（赔付对象：理赔金）值对
				for (int j = 0; j < tempKeyValue.length; j++) {
					tempStr = tempKeyValue[j].split(":");// 赔付对象序号,理赔金（顺序存储）
					prpLPlanKind = new PrpLplanKind();
					prpLPlanKind.getId().setCertiNo(objs[0].toString());
					prpLPlanKind.setRiskCode(objs[5].toString());
					prpLPlanKind.setPolicyNo(objs[1].toString());
					prpLPlanKind.getId().setItemKindNo(((Number) objs[2]).intValue());
					prpLPlanKind.setKindCode(String.valueOf(objs[6]));
					prpLPlanKind.setComCode(objs[10].toString());
					prpLPlanKind.setSubRiskCode(objs[5].toString());
					prpLPlanKind.setFlag("0");
					prpLPlanKind.getId().setCertiType("C");
					tempKey = "PRPLPAYOBJECTINFO_"+prpccoins_serialno+"_" + DataUtils.dbNullToEmpty(tempStr[0]);
					if (null != serialNoMap.get(tempKey)) {
						serialNo = (Integer) serialNoMap.get(tempKey);
					}
					prpLPlanKind.getId().setSerialNo(serialNo);
					if(serialNoMap.get("PAYREFREASON_"+serialNo)!=null){
						prpLPlanKind.getId().setPayRefReason(String.valueOf(serialNoMap.get("PAYREFREASON_"+serialNo)));
					}else{
						prpLPlanKind.getId().setPayRefReason("");
					}
					// transfCompensate prpLPlan 中是用的 PayRefReason + PayObjectSerialNo
					prpLPlanKind.setPlanFeeCurrency(objs[4].toString());
					/**update by chenjie 20130618 损失拆分，金额从PayObjectSerialNo取(之前取自Sumrealpay)start*/
					// prpLPlanKind.setKindFee(((Number) objs[8]).doubleValue()* coinsRate);
					prpLPlanKind.setKindFee(Double.valueOf(tempStr[1]));
					/** update by chenjie 20130618 损失拆分，金额从PayObjectSerialNo取 end */
					prpLPlanKind.setExchangeRate(objs[3]==null? 1 : ((Number) objs[3]).doubleValue());
					prpLPlanKind.setPlanFeeCNY(prpLPlanKind.getKindFee() * prpLPlanKind.getExchangeRate());
					prpLPlanKind.setPlanFeeCNY(DataUtils.round(prpLPlanKind.getPlanFeeCNY(), 0));
					if (sumPaid != 0) {
						prpLPlanKind.setKindFeeRate(prpLPlanKind.getPlanFeeCNY() / (sumPaid) * 100);
					}
					prpLPlanKind.setInputDate(inputDate);
					if (!prpLPlanKind.getKindCode().equals("M") && prpLPlanKind.getKindFee() != 0) {
						// 因为要拆分的赔付对象，所以加上赔付对象的serialNo最好
						tempKey = prpLPlanKind.getKindCode() + "-" + DataUtils.dbNullToEmpty(tempStr[0]);
						if (!prpLPlanKindMap.containsKey(tempKey)) {
							prpLPlanKindMap.put(tempKey, prpLPlanKind);
						} else {
							PrpLplanKind tempPrpLplanKind = (PrpLplanKind) prpLPlanKindMap.get(tempKey);
							double kindfee = tempPrpLplanKind.getKindFee();
							tempPrpLplanKind.setKindFee(kindfee + prpLPlanKind.getKindFee());
							tempPrpLplanKind.setPlanFeeCNY(tempPrpLplanKind.getKindFee() * tempPrpLplanKind.getExchangeRate());
							tempPrpLplanKind.setPlanFeeCNY(DataUtils.round(tempPrpLplanKind.getPlanFeeCNY(), 0));
							if (sumPaid != 0) {
								tempPrpLplanKind.setKindFeeRate(tempPrpLplanKind.getPlanFeeCNY() / (sumPaid) * 100);
							}
						}
					}
				}
			}
			Exceptdeductiblepay += ((Number) objs[9]).doubleValue();// 每条损失只加一次
		}
		// // 人伤
		String bufferP = null;
		if(ConstantCodes.RISKCODE_DAZ.equals(prpLcompensate.getRiskCode())){
			bufferP = "select l.Compensateno,l.Policyno,m.Itemkindno,c.Exchangerate,"
					+ "c.Currency,l.Riskcode,l.Kindcode,l.PayObjectSerialNo,sum(ROUND(l.Sumrealpay*l.exchRate,0)) Sumrealpay,sum(l.Exceptdeductiblepay) Exceptdeductiblepay,c.Comcode,m.STARTDATE,m.ENDDATE,l.PERSONNO" + " from Prplpersonloss l ,Prpcitemkind m,Prplcompensate c  "
					+ " where  m.Kindcode=l.Kindcode and m.Policyno = l.Policyno " + " and l.Compensateno= c.compensateNo and l.compensateNo='" + compensateNo + "' "
					+ " group by l.kindCode,l.PayObjectSerialNo,l.Compensateno,l.Riskcode,l.Policyno,m.Itemkindno,c.Exchangerate,c.Comcode,c.Currency,m.STARTDATE,m.ENDDATE,l.PERSONNO";
		//mantis：CLM0223 ，處理人員：DP0713，需求單編號：新核心-旅平險案件審核異常 START
		}else if(ConstantCodes.RISKCODE_ETA.equals(prpLcompensate.getRiskCode())){
			bufferP = "select l.Compensateno,l.Policyno,m.Itemkindno,l.exchRate,"
			+ "l.Currency,l.Riskcode,l.Kindcode,l.PayObjectSerialNo,ROUND(l.Sumrealpay*l.exchRate,0) Sumrealpay,l.Exceptdeductiblepay Exceptdeductiblepay,c.Comcode,m.STARTDATE,m.ENDDATE,l.PERSONNO" 
			+ " from Prplpersonloss l join Prplcompensate c on l.Compensateno= c.compensateNo "
			+ " left join Prpcitemkind m on l.FAMILYNO = m.FAMILYNO and l.KINDCODE = m.KINDCODE  and m.Policyno = l.Policyno  and l.ITEMKINDNO= m.ITEMKINDNO "
			+ " where l.compensateNo='" + compensateNo + "' ";
		//mantis：CLM0223 ，處理人員：DP0713，需求單編號：新核心-旅平險案件審核異常 END
		}else{
			bufferP = "select l.Compensateno,l.Policyno,m.Itemkindno,l.exchRate,"
			+ "l.Currency,l.Riskcode,l.Kindcode,l.PayObjectSerialNo,ROUND(l.Sumrealpay*l.exchRate,0) Sumrealpay,l.Exceptdeductiblepay Exceptdeductiblepay,c.Comcode,m.STARTDATE,m.ENDDATE,l.PERSONNO" + " from Prplpersonloss l ,Prpcitemkind m,Prplcompensate c  "
			+ " where  m.Kindcode=l.Kindcode and m.Policyno = l.Policyno " + " and l.Compensateno= c.compensateNo and l.compensateNo='" + compensateNo + "' ";
		}
		List<?> resultSetP = HibernateUtils.findbySql(super.getSession(), bufferP);
		// 人伤损失
		for (int i = 0; i < resultSetP.size(); i++) {
			Object[] objs = (Object[]) resultSetP.get(i);
			DateTime startDate = new DateTime((Date) objs[11]);
			DateTime endDate = new DateTime((Date) objs[12]);
			if (startDate.compareTo(new DateTime(damageDate, DateTime.YEAR_TO_DAY)) > 0 || endDate.compareTo(new DateTime(damageDate, DateTime.YEAR_TO_DAY)) < 0) {
				PrpLclaim prpLclaim = getPrpLclaimService().findPrpLclaim(prpLcompensate.getClaimNo());
				if (new UIExcludeClaimAction().isExcluded(prpLclaim.getRegistNo())) {
					// 如果做过除外，则不再校验出险时间是否在保险期间内
				} else {
					throw new UserException(-98, -1149, "保單號：" + objs[1].toString() + "中的險別：" + objs[6].toString(), "出險時間不在保險期間！");
				}
			}
			tempPayObjectSerial = DataUtils.getString(objs[7]);
			if (DataUtils.emptyToNull(tempPayObjectSerial) != null) {
				tempKeyValue = tempPayObjectSerial.split(";");// PayObjectSerialNo分割的（赔付对象：理赔金）值对
				for (int j = 0; j < tempKeyValue.length; j++) {
					tempStr = tempKeyValue[j].split(":");// 赔付对象序号,理赔金（顺序存储）
					prpLPlanKind = new PrpLplanKind();
					prpLPlanKind.setId(new PrpLplanKindId());
					prpLPlanKind.getId().setCertiNo(objs[0].toString());
					prpLPlanKind.setRiskCode(objs[5].toString());
					prpLPlanKind.setPolicyNo(objs[1].toString());
					prpLPlanKind.getId().setItemKindNo(((Number) objs[2]).intValue());
					prpLPlanKind.setKindCode(String.valueOf(objs[6]));
					prpLPlanKind.setComCode(objs[10].toString());
					prpLPlanKind.setSubRiskCode(objs[5].toString());
					prpLPlanKind.getId().setCertiType("C");
					prpLPlanKind.setFlag("0");
//					if (coinsType.equals("1")) {// 我方
//						prpLPlanKind.getId().setPayRefReason("P60");
//					} else {// 他方从联共
//						if (coinsFlag.equals("1")) {// 共保
//							prpLPlanKind.getId().setPayRefReason("S60");
//						} else if (coinsFlag.equals("3")) {// 联保
//							prpLPlanKind.getId().setPayRefReason("F60");
//						}
//					}
					tempKey = "PRPLPAYOBJECTINFO_"+prpccoins_serialno+"_" + DataUtils.dbNullToEmpty(tempStr[0]);
					if (null != serialNoMap.get(tempKey)) {
						serialNo = (Integer) serialNoMap.get(tempKey);
					}
					prpLPlanKind.getId().setSerialNo(serialNo);
					if(serialNoMap.get("PAYREFREASON_"+serialNo)!=null){
						prpLPlanKind.getId().setPayRefReason(String.valueOf(serialNoMap.get("PAYREFREASON_"+serialNo)));
					}else{
						prpLPlanKind.getId().setPayRefReason("");
					}
					
					prpLPlanKind.setPlanFeeCurrency(objs[4].toString());
					/**update by chenjie 20130618 损失拆分，金额从PayObjectSerialNo取(之前取自Sumrealpay)start*/
					// prpLPlanKind.setKindFee(((Number)objs[8]).doubleValue() * coinsRate);
					prpLPlanKind.setKindFee(Double.valueOf(tempStr[1]));
					/**update by chenjie 20130618 损失拆分，金额从PayObjectSerialNo取 end*/
					prpLPlanKind.setExchangeRate(objs[3]==null?  1 : ((Number) objs[3]).doubleValue());
					prpLPlanKind.setPlanFeeCNY(prpLPlanKind.getKindFee() * prpLPlanKind.getExchangeRate());
					prpLPlanKind.setPlanFeeCNY(DataUtils.round(prpLPlanKind.getPlanFeeCNY(), 0));
					if (sumPaid != 0) {
						prpLPlanKind.setKindFeeRate(prpLPlanKind.getPlanFeeCNY() / (sumPaid) * 100);
					}
					prpLPlanKind.setInputDate(inputDate);
					if (!prpLPlanKind.getKindCode().equals("M") && prpLPlanKind.getKindFee() != 0) {
						tempKey = prpLPlanKind.getKindCode() + "-" + DataUtils.dbNullToEmpty(tempStr[0]);
						if (!prpLPlanKindMap.containsKey(tempKey)) {
							prpLPlanKindMap.put(tempKey, prpLPlanKind);
						} else {
							PrpLplanKind tempPrpLplanKind = (PrpLplanKind) prpLPlanKindMap.get(tempKey);
							double kindfee = tempPrpLplanKind.getKindFee();
							tempPrpLplanKind.setKindFee(kindfee + prpLPlanKind.getKindFee());
							tempPrpLplanKind.setPlanFeeCNY(tempPrpLplanKind.getKindFee() * tempPrpLplanKind.getExchangeRate());
							tempPrpLplanKind.setPlanFeeCNY(DataUtils.round(tempPrpLplanKind.getPlanFeeCNY(), 0));
							if (sumPaid != 0) {
								tempPrpLplanKind.setKindFeeRate(tempPrpLplanKind.getPlanFeeCNY() / (sumPaid) * 100);
							}
						}
					}
				}
			}
			Exceptdeductiblepay += ((Number) objs[9]).doubleValue();
		}
		// 根据险别累加赔款後剩余集合加入列表
		if (!prpLPlanKindMap.isEmpty()) {
			prpLPlanKindList.addAll(prpLPlanKindMap.values());
		}
		// 费用
		String bufferC = "select l.Compensateno,l.Policyno,(select x.Itemkindno from prpcitemKind x where  " + "l.Policyno=x.Policyno and  x.kindcode=l.Kindcode and Rownum<2 ) Itemkindno" + ",l.exchRate,l.Exceptdeductiblepay,"
				+ "l.Currency,l.Riskcode,l.Kindcode,l.Chargecode,l.Chargeamount,l.Sumrealpay,c.Comcode,l.serialNo " + " from Prplcharge l ,Prplcompensate c " + " where l.Compensateno= c.compensateNo and l.compensateNo='" + compensateNo + "' ";
		
		// 费用处理
//		Map<String, String> chargeCodeMap = new HashMap<String, String>();
//		double coinsRate_0 = serialNoMap.get("coinsRate_0")==null ? 1D : new Double(serialNoMap.get("coinsRate_0").toString());
		List<?> resultSetC = HibernateUtils.findbySql(super.getSession(), bufferC);
		for (int i = 0; i < resultSetC.size(); i++) {
			Object[] objs = (Object[]) resultSetC.get(i);
			prpLPlanKind = new PrpLplanKind();
			prpLPlanKind.getId().setCertiNo(objs[0].toString());
			prpLPlanKind.setRiskCode(objs[6].toString());
			prpLPlanKind.setPolicyNo(objs[1].toString());
			prpLPlanKind.getId().setItemKindNo(((Number) objs[2]).intValue());
			prpLPlanKind.setKindCode(objs[7].toString());
			prpLPlanKind.setComCode(objs[11].toString());
			prpLPlanKind.setSubRiskCode(objs[6].toString());
			prpLPlanKind.getId().setCertiType("C");
			prpLPlanKind.setFlag("0");
			prpLPlanKind.setChargeCode(objs[8].toString());
			if(prpLcompensate.getCompensateNo().startsWith("D")){
				prpLPlanKind.getId().setPayRefReason(codeMap.get(prpLPlanKind.getChargeCode() + "D") + "");
			}else{
				if (coinsFlag.equals("2")){
						prpLPlanKind.getId().setPayRefReason(codeMap.get(prpLPlanKind.getChargeCode() + "S") + "");
				}else if (coinsFlag.equals("3")){
					prpLPlanKind.getId().setPayRefReason(codeMap.get(prpLPlanKind.getChargeCode() + "F") + "");
				}else{
					prpLPlanKind.getId().setPayRefReason(codeMap.get(prpLPlanKind.getChargeCode() + "P") + "");
				}
			}
			prpLPlanKind.setPlanFeeCurrency(objs[5].toString());
//			if("1".equals(coinsFlag)){
//				prpLPlanKind.setKindFee(((Number) objs[9]).doubleValue()/coinsRate_0);
//			}else {
//			}
			prpLPlanKind.setKindFee(((Number) objs[9]).doubleValue());
			prpLPlanKind.setExchangeRate(objs[3]==null? 1 : ((Number) objs[3]).doubleValue());
			prpLPlanKind.setPlanFeeCNY(prpLPlanKind.getKindFee() * prpLPlanKind.getExchangeRate());
			prpLPlanKind.setPlanFeeCNY(DataUtils.round(prpLPlanKind.getPlanFeeCNY(), 0));
			prpLPlanKind.setKindFeeRate(0D);
			prpLPlanKind.setInputDate(inputDate);
			tempKey = "PRPLCHARGE_"+prpccoins_serialno+"_" + ((Number) objs[12]).intValue();
			if (null != serialNoMap.get(tempKey)) {
				serialNo = (Integer) serialNoMap.get(tempKey);
			}
			prpLPlanKind.getId().setSerialNo(serialNo);
			
//			chargeCodeMap.put(objs[8].toString(), prpLPlanKind.getId().getPayRefReason() + prpLPlanKind.getId().getSerialNo());
			if (prpLPlanKind.getKindFee() != 0) {
				prpLPlanKindList.add(prpLPlanKind);
			}
		}
		// 不计免陪险处理
//		List<PrpCitemKind> prpCitemKindList = getPrpCitemKindService().findByConditions(" policyNo='" + prpLcompensate.getPolicyNo() + "' and kindCode='M' ");
//		if (Exceptdeductiblepay != 0 && null != prpCitemKindList && prpCitemKindList.size() > 0) {
//			prpLPlanKind = new PrpLplanKind();
//			prpLPlanKind.getId().setCertiNo(prpLcompensate.getCompensateNo());
//			prpLPlanKind.setRiskCode(prpLcompensate.getRiskCode());
//			prpLPlanKind.setPolicyNo(prpLcompensate.getPolicyNo());
//			prpLPlanKind.getId().setItemKindNo(((PrpCitemKind) prpCitemKindList.get(0)).getId().getItemKindNo());
//			prpLPlanKind.setKindCode("M");
//			prpLPlanKind.setComCode(prpLcompensate.getComCode());
//			prpLPlanKind.setSubRiskCode(prpLcompensate.getRiskCode());
//			prpLPlanKind.getId().setCertiType("C");
//			prpLPlanKind.setFlag("0");
//			if (coinsType.equals("1")) {// 我方
//				prpLPlanKind.getId().setPayRefReason("P60");
//			} else {// 他方从联共
//				if (coinsFlag.equals("1")) {// 共保
//					prpLPlanKind.getId().setPayRefReason("S60");
//				} else if (coinsFlag.equals("3")) {// 联保
//					prpLPlanKind.getId().setPayRefReason("F60");
//				}
//			}
//			if (null != serialNoMap.get(prpLPlanKind.getId().getPayRefReason())) {
//				serialNo = Integer.parseInt(serialNoMap.get(prpLPlanKind.getId().getPayRefReason()).toString());
//			}
//			prpLPlanKind.getId().setSerialNo(serialNo);
//			prpLPlanKind.setPlanFeeCurrency(prpLcompensate.getCurrency());
//			prpLPlanKind.setKindFee(Exceptdeductiblepay * coinsRate);
//			prpLPlanKind.setExchangeRate(prpLcompensate.getExchangeRate());
//			prpLPlanKind.setPlanFeeCNY(prpLPlanKind.getKindFee() * prpLcompensate.getExchangeRate());
//			if (sumPaid != 0) {
//				prpLPlanKind.setKindFeeRate(prpLPlanKind.getKindFee() / (sumPaid * coinsRate) * 100);
//			}
//			prpLPlanKind.setInputDate(inputDate);
//			if (prpLPlanKind.getKindFee() != 0) {
//				prpLPlanKindList.add(prpLPlanKind);
//			}
//		}
		prpLPlanKindList = this.getPrPccoinsKindList(prpLcompensate, prpLPlanKindList, serialNoMap, coinsType, isComBin);
		return prpLPlanKindList;
	}
	
	@Override
	public List<PrpLplanKind> findReplevyLossByConditions(String compensateNo, Map<String, String> codeMap, String coinsType, String coinsFlag, double coinsRate, double sumPaid, Map<String, Object> serialNoMap, String damageDate, String isComBin)
			throws Exception {
		DateTime inputDate = new DateTime(DateTime.current(), DateTime.YEAR_TO_SECOND);
		// 财产
		String bufferL = "select l.Compensateno,l.Policyno,m.Itemkindno,l.exchRate,"
				+ "l.Currency,l.Riskcode,l.Kindcode,l.PayObjectSerialNo,l.Sumrealpay,l.Exceptdeductiblepay Exceptdeductiblepay,c.Comcode,m.STARTDATE,m.ENDDATE  " + " from Prplloss l ,Prpcitemkind m,Prplcompensate c  "
				//mantis： CLM0101 ，處理人員：BK007 蘇哲，需求單編號：CLM0101.新核心-商綜[商火]
				+ " where  m.Kindcode=l.Kindcode and m.Policyno = l.Policyno " + "and l.Compensateno= c.compensateNo and l.compensateNo='" + compensateNo + "' and (l.ITEMCODE = m.ITEMCODE OR l.ITEMCODE IS NULL) ";
		List<?> resultSetL = HibernateUtils.findbySql(super.getSession(), bufferL);
		// 定义返回结果集合
		List<PrpLplanKind> prpLPlanKindList = new ArrayList<PrpLplanKind>();
		PrpLplanKind prpLPlanKind = null;
		int serialNo = 0;
		Map<String, PrpLplanKind> prpLPlanKindMap = new HashMap<String, PrpLplanKind>();
		// 损失标的
		String tempPayObjectSerial = "";//取自每条损失信息对应的赔付PayObjectSerialNo的值串(考虑1损失赔多人的情况)
		String[] tempKeyValue = null;//PayObjectSerialNo分割的（赔付对象：理赔金）值对
		String[] tempStr = null;//赔付对象序号，及其对应的理赔金（顺序存储）
		String tempKey = "";// 临时存储键值变量
		// 损失标的
		PrpLcompensate prpLcompensate = getPrpLcompensateService().findPrpLcompensate(compensateNo);
		String prpccoins_serialno = ""; //联共保号码
		if(serialNoMap.get("PRPCCOINS_SERIALNO")!=null){
			prpccoins_serialno = String.valueOf(serialNoMap.get("PRPCCOINS_SERIALNO"));
		}
		for(int i = 0; i<resultSetL.size();i++){
			Object[] objs = (Object[]) resultSetL.get(i);
			DateTime starDate = new DateTime((Date)objs[11]);
			DateTime endDate = new DateTime((Date)objs[12]);
			if (starDate.compareTo(new DateTime(damageDate, DateTime.YEAR_TO_DAY)) > 0 || endDate.compareTo(new DateTime(damageDate, DateTime.YEAR_TO_DAY)) < 0) {
				PrpLclaim prpLclaim = getPrpLclaimService().findPrpLclaim(prpLcompensate.getClaimNo());
				if (new UIExcludeClaimAction().isExcluded(prpLclaim.getRegistNo())) {
					// 如果做过除外，则不再校验出险时间是否在保险期间内
				} else {
					throw new UserException(-98, -1149, "保單號：" + objs[1].toString() + "中的險別：" + objs[6].toString(), "出險時間不在保險期間！");
				}
			}
			tempPayObjectSerial = DataUtils.getString(objs[7]);
			if (DataUtils.emptyToNull(tempPayObjectSerial) != null) {
				tempKeyValue = tempPayObjectSerial.split(";");// PayObjectSerialNo分割的（赔付对象：理赔金）值对
				for (int j = 0; j < tempKeyValue.length; j++) {
					tempStr = tempKeyValue[j].split(":");// 赔付对象序号,理赔金（顺序存储）
					prpLPlanKind = new PrpLplanKind();
					prpLPlanKind.getId().setCertiNo(objs[0].toString());
					prpLPlanKind.setRiskCode(objs[5].toString());
					prpLPlanKind.setPolicyNo(objs[1].toString());
					prpLPlanKind.getId().setItemKindNo(((Number) objs[2]).intValue());
					prpLPlanKind.setKindCode(objs[6].toString());
					prpLPlanKind.setComCode(objs[10].toString());
					prpLPlanKind.setSubRiskCode(objs[5].toString());
					prpLPlanKind.setFlag("0");
					prpLPlanKind.getId().setCertiType("C");
//					prpLPlanKind.getId().setPayRefReason("Z60");
					
					tempKey = "PRPLPAYOBJECTINFO_"+prpccoins_serialno+"_" + DataUtils.dbNullToEmpty(tempStr[0]);
					if (null != serialNoMap.get(tempKey)) {
						serialNo = (Integer) serialNoMap.get(tempKey);
					}
					prpLPlanKind.getId().setSerialNo(serialNo);
					
					if(serialNoMap.get("PAYREFREASON_"+serialNo)!=null){
						prpLPlanKind.getId().setPayRefReason(String.valueOf(serialNoMap.get("PAYREFREASON_"+serialNo)));
					}else{
						prpLPlanKind.getId().setPayRefReason("");
					}
//					tempKey = prpLPlanKind.getId().getPayRefReason() + DataUtils.dbNullToEmpty(tempStr[0]);
//					if (null != serialNoMap.get(tempKey)) {
//						serialNo = Integer.parseInt(serialNoMap.get(tempKey).toString());
//					}
					prpLPlanKind.setPlanFeeCurrency(objs[4].toString());
					prpLPlanKind.setKindFee((-1)*Double.valueOf(tempStr[1]));//追償金額取反，
					prpLPlanKind.setExchangeRate(objs[3]==null ? 1D : ((Number) objs[3]).doubleValue());
					prpLPlanKind.setPlanFeeCNY(prpLPlanKind.getKindFee() * prpLPlanKind.getExchangeRate());
					prpLPlanKind.setPlanFeeCNY(DataUtils.round(prpLPlanKind.getPlanFeeCNY(), 0));
					if (sumPaid != 0) {
						prpLPlanKind.setKindFeeRate(prpLPlanKind.getPlanFeeCNY() / (sumPaid) * 100);
					}
					prpLPlanKind.setInputDate(inputDate);
					if (!prpLPlanKind.getKindCode().equals("M") && prpLPlanKind.getKindFee() != 0) {
						tempKey = prpLPlanKind.getKindCode() + "-" + DataUtils.dbNullToEmpty(tempStr[0]);
						if (!prpLPlanKindMap.containsKey(tempKey)) {
							prpLPlanKindMap.put(tempKey, prpLPlanKind);
						} else {
							PrpLplanKind tempPrpLplanKind = (PrpLplanKind) prpLPlanKindMap.get(tempKey);
							double kindfee = tempPrpLplanKind.getKindFee();
							tempPrpLplanKind.setKindFee(kindfee + prpLPlanKind.getKindFee());
							tempPrpLplanKind.setPlanFeeCNY(tempPrpLplanKind.getKindFee() * tempPrpLplanKind.getExchangeRate());
							if (sumPaid != 0) {
								tempPrpLplanKind.setKindFeeRate(tempPrpLplanKind.getPlanFeeCNY() / (sumPaid) * 100);
							}
						}
					}
				}
			}
		}
		// 根据险别累加赔款後剩余集合加入列表
		if (!prpLPlanKindMap.isEmpty()) {
			prpLPlanKindList.addAll(prpLPlanKindMap.values());
		}
		// 费用
		String bufferC = "select l.Compensateno,l.Policyno,(select x.Itemkindno from prpcitemKind x where  " + "l.Policyno=x.Policyno and  x.kindcode=l.Kindcode and Rownum<2 ) Itemkindno"
				+ ",l.exchRate,sum(l.Exceptdeductiblepay) Exceptdeductiblepay," + "l.Currency,l.Riskcode,l.Kindcode,l.Chargecode,sum(l.Chargeamount) Chargeamount,sum(l.Sumrealpay) Sumrealpay,c.Comcode,l.serialNo " + " from Prplcharge l ,Prplcompensate c "
				+ " where l.Compensateno= c.compensateNo and l.compensateNo='" + compensateNo + "' " + " group by l.kindCode,l.Chargecode,l.Compensateno,l.Riskcode,l.Policyno,l.exchRate,c.Comcode,l.Currency,l.serialNo";
		// 费用处理
//		Map<String, String> chargeCodeMap = new HashMap<String, String>();
//		double coinsRate_0 = serialNoMap.get("coinsRate_0")==null ? 1D : new Double(serialNoMap.get("coinsRate_0").toString());
		List<?> resultSetC = HibernateUtils.findbySql(super.getSession(), bufferC);
		Object[] objs = null;
		for(int i = 0; i<resultSetC.size();i++){
			objs = (Object[]) resultSetC.get(i);
			prpLPlanKind = new PrpLplanKind();
			prpLPlanKind.getId().setCertiNo(objs[0].toString());
			prpLPlanKind.setRiskCode(objs[6].toString());
			prpLPlanKind.setPolicyNo(objs[1].toString());
			prpLPlanKind.getId().setItemKindNo(((Number) objs[2]).intValue());
			prpLPlanKind.setKindCode(objs[7].toString());
			prpLPlanKind.setComCode(objs[11].toString());
			prpLPlanKind.setSubRiskCode(objs[6].toString());
			prpLPlanKind.getId().setCertiType("C");
			prpLPlanKind.setFlag("0");
			prpLPlanKind.setChargeCode(objs[8].toString());
			prpLPlanKind.getId().setPayRefReason(codeMap.get(prpLPlanKind.getChargeCode() + "Z") + "");

//			if (null != serialNoMap.get(prpLPlanKind.getId().getPayRefReason() + objs[8].toString())) {
//				serialNo = Integer.parseInt(serialNoMap.get(prpLPlanKind.getId().getPayRefReason() + objs[8].toString()).toString());
//			}
			tempKey = "PRPLCHARGE_"+prpccoins_serialno+"_" + objs[12];
			if (null != serialNoMap.get(tempKey)) {
				serialNo = (Integer) serialNoMap.get(tempKey);
			}
			prpLPlanKind.getId().setSerialNo(serialNo);
			
//			if(serialNoMap.get("PAYREFREASON_"+serialNo)!=null){
//				prpLPlanKind.getId().setPayRefReason(String.valueOf(serialNoMap.get("PAYREFREASON_"+serialNo)));
//			}else{
//				prpLPlanKind.getId().setPayRefReason("");
//			}
			prpLPlanKind.setPlanFeeCurrency(objs[5].toString());
//			if("1".equals(coinsFlag)){
//				prpLPlanKind.setKindFee(((Number) objs[9]).doubleValue());
//			}else{
//			prpLPlanKind.setKindFee(((Number) objs[9]).doubleValue());
//			}
			prpLPlanKind.setKindFee(((Number) objs[9]).doubleValue());

			prpLPlanKind.setExchangeRate(objs[3]==null ? 1D : ((Number) objs[3]).doubleValue());
			prpLPlanKind.setPlanFeeCNY(prpLPlanKind.getKindFee() * prpLPlanKind.getExchangeRate());
			prpLPlanKind.setPlanFeeCNY(DataUtils.round(prpLPlanKind.getPlanFeeCNY(), 0));
			prpLPlanKind.setKindFeeRate(0D);
			prpLPlanKind.setInputDate(inputDate);
//			chargeCodeMap.put(objs[8].toString(), prpLPlanKind.getId().getPayRefReason() + prpLPlanKind.getId().getSerialNo());
			if (prpLPlanKind.getKindFee() != 0) {
				prpLPlanKindList.add(prpLPlanKind);
			}
		}
		prpLPlanKindList = this.getPrPccoinsKindList(prpLcompensate, prpLPlanKindList, serialNoMap, coinsType, isComBin);
		return prpLPlanKindList;
	}

	@Override
	public String findSubRiskCode(PrpLplanKind prpLPlanKind) throws Exception {
		String subRiskCode = "";
		try {
			String strRiskType = this.getCodeService().translateRiskCodetoRiskType(prpLPlanKind.getRiskCode());
			BLPrpDration blPrpDration = new BLPrpDration();
			if (ConstantCodes.CLASSCODE_D.equals(strRiskType)) {
				String policyNo = prpLPlanKind.getPolicyNo();
				QueryRule queryRule = QueryRule.getInstance();
				queryRule.addEqual("id.policyNo", policyNo);
				String riskCode = "";
				String kindCode = "";
				String useNatureCode = ConstantCodes.USERNATURECODE_2;
				int years = 1;
				List<PrpCitemCar> prpCitemCarList = getPrpCitemCarService().findPrpCitemCar(queryRule);
				QueryRule queryRule_1 = QueryRule.getInstance();
				queryRule_1.addEqual("id.policyNo", policyNo);
				queryRule_1.addEqual("riskCode", prpLPlanKind.getRiskCode());
				queryRule_1.addEqual("kindCode", prpLPlanKind.getKindCode());
				List<PrpCitemKind> prpCitemKindList = getPrpCitemKindService().findPrpCitemKind(queryRule_1);
				if (prpCitemCarList != null && prpCitemKindList != null && prpCitemCarList.size() > 0 && prpCitemKindList.size() > 0) {
					PrpCitemCar prpCitemCar = prpCitemCarList.get(0);
					riskCode = prpCitemCar.getRiskCode();
					PrpCitemKind prpCitemKind = prpCitemKindList.get(0);
					kindCode = prpCitemKind.getKindCode();
					useNatureCode = prpCitemCar.getUseNatureCode();
					years = new DateTime(prpCitemKind.getEndDate()).getYear() - new DateTime(prpCitemKind.getStartDate()).getYear();
				}
				if (riskCode != null && !"".equals(riskCode)) {
					if (!ConstantCodes.USERNATURECODE_1.equals(useNatureCode)) {
						useNatureCode = ConstantCodes.USERNATURECODE_2;
					}
					
					//mantis：CLM0275 ，處理人員：DP0713，需求單編號：新核心-由 JDBC open db pool 連線 換成spring Hibernate方式query資料 START
					try{
						//跑[整批理賠核賠任務]每月拖吊在這裡會發生 java.sql.SQLException: ORA-01000: maximum open cursors exceeded ，中科開發的時候另開了dp pool去QUERY DB，
						//導致cursors爆炸，DB目前設定300上限，當cursors炸裂後會切換成一般spring hibernate方式run，主要爆炸時間是[整批理賠核賠任務]
						blPrpDration.query(" RiskCode='" + riskCode + "' AND KindCode='" + kindCode + "' AND UseNatureCode='" + useNatureCode + "' AND Years=" + years);
						
					}catch(Exception e){
						System.out.println("CLM0275 PrpLplanKindService.findSubRiskCode start:"+e.getMessage());
						String sqlCondition = "SELECT SUBRISKCODE,USENATURECODE,YEARS FROM BUSINESS.PrpDration WHERE RiskCode='"+riskCode+"' AND kindCode = '"+kindCode+"'" ;
								//"AND UseNatureCode='"+useNatureCode+"' AND Years="+ years+"";
						List<?> resultSetCdata = HibernateUtils.findbySql(super.getSession(), sqlCondition);
						//Object[] objs = null;
						for(int i = 0; i<resultSetCdata.size();i++){
							Object obj = resultSetCdata.get(i);
						    System.out.println("第" + i + "筆資料型態: " + obj.getClass().getName());
						    
						    // 如果是 Object[] 陣列
						    if (obj instanceof Object[]) {
						        Object[] objs = (Object[]) obj;
						        System.out.println("陣列長度: " + objs.length);
						        for (int j = 0; j < objs.length; j++) {
						            subRiskCode = (String)objs[0];
							        String usNatureCode_ = (String)objs[1];
							        Integer year_ = null;
							        if (objs[2] != null) {
							            if (objs[2] instanceof BigDecimal) {
							                BigDecimal yearDecimal = (BigDecimal) objs[2];
							                year_ = yearDecimal.intValue();
							            } else if (objs[2] instanceof Integer) {
							                year_ = (Integer) objs[2];
							            } else {
							                // 其他數字型態的處理
							                year_ = Integer.valueOf(objs[2].toString());
							            }
							        }
							        if(usNatureCode_.equals(useNatureCode) && year_.equals(years)){//有比較到表示四項條件都達成，返回精準查詢結果
										System.out.println("CLM0275 PrpLplanKindService.findSubRiskCode(situation 1) :"+subRiskCode);//Exception situation 1
							        	return subRiskCode;
							        }
						        }
						    } else {
						        // 如果不是陣列，可能是單一物件
						        System.out.println("單一物件值: " + obj);
						        subRiskCode = (String)obj;
								System.out.println("CLM0275 PrpLplanKindService.findSubRiskCode(situation 2) :"+subRiskCode);//Exception situation 2
						    }
						    
						}
						System.out.println("CLM0275 PrpLplanKindService.findSubRiskCode(situation 3) :"+subRiskCode);//Exception situation 3
						if(null!=subRiskCode && !"".equals(subRiskCode)){//沒比較到表示兩項查詢達成，返回模糊(兩項條件)查詢結果
							return subRiskCode;
						}
					}
					//mantis：CLM0275 ，處理人員：DP0713，需求單編號：新核心-由 JDBC open db pool 連線 換成spring Hibernate方式query資料 END
				}
			// 火险，伤害险，用Kindcode查询 ，水工责 用险种查询
			} else if (ConstantCodes.CLASSCODE_E.equals(strRiskType) || ConstantCodes.CLASSCODE_Q.equals(strRiskType)) {
				blPrpDration.query(" RiskCode='" + prpLPlanKind.getRiskCode() + "' AND KindCode='" + prpLPlanKind.getKindCode() + "'");
			} else {//水工责 用险种查询
				blPrpDration.query(" RiskCode='" + prpLPlanKind.getRiskCode() + "' AND KindCode='" + prpLPlanKind.getRiskCode() + "'");
			}
			if (blPrpDration.getSize() > 0) {
				subRiskCode = blPrpDration.getArr(blPrpDration.getSize() - 1).getSubRiskCode();
			}
			if (subRiskCode == null || "".equals(subRiskCode)) {
				blPrpDration.query(" riskCode='" + prpLPlanKind.getRiskCode() + "' and kindCode='" + prpLPlanKind.getKindCode() + "'");
				if (blPrpDration.getSize() > 0) {
					subRiskCode = blPrpDration.getArr(blPrpDration.getSize() - 1).getSubRiskCode();
				}else{
					subRiskCode = prpLPlanKind.getRiskCode();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		//mantis：CLM0275 ，處理人員：DP0713，需求單編號：新核心-由 JDBC open db pool 連線 換成spring Hibernate方式query資料
		System.out.println("CLM0275 PrpLplanKindService.findSubRiskCode(situation regular) :"+subRiskCode);//situation regular
		return subRiskCode;
	}

	@Override
	public Collection<?> findByConditions(String conditions) throws Exception {
		QueryRule queryRule = QueryRule.getInstance();
		queryRule.addSql(conditions);
		return super.find(queryRule);
	}

	@Override
	public List<PrpLplanKind> findRemnantByConditions(String compensateNo,
			Map<String, String> codeMap, String coinsType, String coinsFlag, double coinsRate,
			double sumPaid, Map<String, Object> serialNoMap, String damageDate, String isComBin)
			throws Exception {
		DateTime inputDate = new DateTime(DateTime.current(), DateTime.YEAR_TO_SECOND);
		String bufferL = "select l.Compensateno,l.Policyno,m.Itemkindno,c.Exchangerate,"
			+ "c.Currency,l.Riskcode,l.Kindcode,l.PayObjectSerialNo,c.Comcode,m.STARTDATE,m.ENDDATE " + " from PrpLremnant l ,Prpcitemkind m,Prplcompensate c  "
			+ " where  m.Kindcode=l.Kindcode and m.Policyno = l.Policyno " + "and l.Compensateno= c.compensateNo and l.compensateNo='" + compensateNo + "' ";
		List<?> resultSetL = HibernateUtils.findbySql(super.getSession(), bufferL);
		// 定义返回结果集合
		List<PrpLplanKind> prpLPlanKindList = new ArrayList<PrpLplanKind>();
		PrpLplanKind prpLPlanKind = null;
		int serialNo = 0;
		Map<String, PrpLplanKind> prpLPlanKindMap = new HashMap<String, PrpLplanKind>();
		// 损失标的
		PrpLcompensate prpLcompensate = getPrpLcompensateService().findPrpLcompensate(compensateNo);
		String tempPayObjectSerial = "";//取自每条损失信息对应的赔付PayObjectSerialNo的值串(考虑1损失赔多人的情况)
		String[] tempKeyValue = null;//PayObjectSerialNo分割的（赔付对象：理赔金）值对
		String[] tempStr = null;//赔付对象序号，及其对应的理赔金（顺序存储）
		String tempKey = "";// 临时存储键值变量
		String prpccoins_serialno = ""; //联共保号码
		if(serialNoMap.get("PRPCCOINS_SERIALNO")!=null){
			prpccoins_serialno = String.valueOf(serialNoMap.get("PRPCCOINS_SERIALNO"));
		}
		if(sumPaid<0){
			sumPaid = -sumPaid;
		}
		for(int i = 0; i<resultSetL.size();i++){
			Object[] objs = (Object[]) resultSetL.get(i);
			DateTime starDate = new DateTime((Date)objs[9]);
			DateTime endDate = new DateTime((Date)objs[10]);
			if (starDate.compareTo(new DateTime(damageDate, DateTime.YEAR_TO_DAY)) > 0 || endDate.compareTo(new DateTime(damageDate, DateTime.YEAR_TO_DAY)) < 0) {
				PrpLclaim prpLclaim = getPrpLclaimService().findPrpLclaim(prpLcompensate.getClaimNo());
				if (new UIExcludeClaimAction().isExcluded(prpLclaim.getRegistNo())) {
					// 如果做过除外，则不再校验出险时间是否在保险期间内
				} else {
					throw new UserException(-98, -1149, "保單號：" + objs[1].toString() + "中的險別：" + objs[6].toString(), "出險時間不在保險期間！");
				}
			}
			tempPayObjectSerial = DataUtils.getString(objs[7]);
			if (DataUtils.emptyToNull(tempPayObjectSerial) != null) {
				tempKeyValue = tempPayObjectSerial.split(";");// PayObjectSerialNo分割的（赔付对象：理赔金）值对
				for (int j = 0; j < tempKeyValue.length; j++) {
					tempStr = tempKeyValue[j].split(":");// 赔付对象序号,理赔金（顺序存储）
					prpLPlanKind = new PrpLplanKind();
					prpLPlanKind.getId().setCertiNo(objs[0].toString());
					prpLPlanKind.setRiskCode(objs[5].toString());
					prpLPlanKind.setPolicyNo(objs[1].toString());
					prpLPlanKind.getId().setItemKindNo(((Number) objs[2]).intValue());
					prpLPlanKind.setKindCode(objs[6].toString());
					prpLPlanKind.setComCode(objs[8].toString());
					prpLPlanKind.setSubRiskCode(objs[5].toString());
					prpLPlanKind.setFlag("0");
					prpLPlanKind.getId().setCertiType("C");
					tempKey = "PRPLPAYOBJECTINFO_"+prpccoins_serialno+"_" + DataUtils.dbNullToEmpty(tempStr[0]);
					if (null != serialNoMap.get(tempKey)) {
						serialNo = (Integer) serialNoMap.get(tempKey);
					}
					prpLPlanKind.getId().setSerialNo(serialNo);
					if(serialNoMap.get("PAYREFREASON_"+serialNo)!=null){
						prpLPlanKind.getId().setPayRefReason(String.valueOf(serialNoMap.get("PAYREFREASON_"+serialNo)));
					}else{
						prpLPlanKind.getId().setPayRefReason("");
					}
					prpLPlanKind.setPlanFeeCurrency(objs[4].toString());
					prpLPlanKind.setKindFee(Double.valueOf(tempStr[1]));
					prpLPlanKind.setExchangeRate(((Number) objs[3]).doubleValue());
					if (sumPaid != 0) {
						prpLPlanKind.setKindFeeRate(prpLPlanKind.getKindFee() / (sumPaid) * 100);
					}
					prpLPlanKind.setPlanFeeCNY(prpLPlanKind.getKindFee() * prpLPlanKind.getExchangeRate());
					prpLPlanKind.setInputDate(inputDate);
					if (!prpLPlanKind.getKindCode().equals("M") && prpLPlanKind.getKindFee() != 0) {
						tempKey = prpLPlanKind.getKindCode() + "-" + DataUtils.dbNullToEmpty(tempStr[0]);
						if (!prpLPlanKindMap.containsKey(tempKey)) {
							prpLPlanKindMap.put(tempKey, prpLPlanKind);
						} else {
							PrpLplanKind tempPrpLplanKind = (PrpLplanKind) prpLPlanKindMap.get(tempKey);
							double kindfee = tempPrpLplanKind.getKindFee();
							tempPrpLplanKind.setKindFee(kindfee + prpLPlanKind.getKindFee());
							tempPrpLplanKind.setPlanFeeCNY(tempPrpLplanKind.getKindFee() * tempPrpLplanKind.getExchangeRate());
							if (sumPaid != 0) {
								tempPrpLplanKind.setKindFeeRate(tempPrpLplanKind.getKindFee() / (sumPaid) * 100);
							}
						}
					}
				}
			}
//			Exceptdeductiblepay += ((Number) objs[9]).doubleValue();
		}
		// 根据险别累加赔款後剩余集合加入列表
		if (!prpLPlanKindMap.isEmpty()) {
			prpLPlanKindList.addAll(prpLPlanKindMap.values());
		}
		// 费用
		String bufferC = "select l.Compensateno,l.Policyno,(select x.Itemkindno from prpcitemKind x where  " + "l.Policyno=x.Policyno and  x.kindcode=l.Kindcode and Rownum<2 ) Itemkindno"
				+ ",c.Exchangerate,sum(l.Exceptdeductiblepay) Exceptdeductiblepay," + "c.Currency,l.Riskcode,l.Kindcode,l.Chargecode,sum(ROUND(l.Chargeamount*l.exchRate,0)) Chargeamount,sum(l.Sumrealpay) Sumrealpay,c.Comcode,l.serialNo,l.exchRate " + " from Prplcharge l ,Prplcompensate c "
				+ " where l.Compensateno= c.compensateNo and l.compensateNo='" + compensateNo + "' " + " group by l.kindCode,l.Chargecode,l.Compensateno,l.Riskcode,l.Policyno,c.Exchangerate,c.Comcode,c.Currency,l.serialNo,l.exchRate";
		// 费用处理
//		Map<String, String> chargeCodeMap = new HashMap<String, String>();
		List<?> resultSetC = HibernateUtils.findbySql(super.getSession(), bufferC);
//		double coinsRate_0 = serialNoMap.get("coinsRate_0")==null ? 1D : new Double(serialNoMap.get("coinsRate_0").toString());
		Object[] objs = null;
		for(int i = 0; i<resultSetC.size();i++){
			objs = (Object[]) resultSetC.get(i);
			prpLPlanKind = new PrpLplanKind();
			prpLPlanKind.getId().setCertiNo(objs[0].toString());
			prpLPlanKind.setRiskCode(objs[6].toString());
			prpLPlanKind.setPolicyNo(objs[1].toString());
			prpLPlanKind.getId().setItemKindNo(((Number) objs[2]).intValue());
			prpLPlanKind.setKindCode(objs[7].toString());
			prpLPlanKind.setComCode(objs[11].toString());
			prpLPlanKind.setSubRiskCode(objs[6].toString());
			prpLPlanKind.getId().setCertiType("C");
			prpLPlanKind.setFlag("0");
			prpLPlanKind.setChargeCode(objs[8].toString());
			prpLPlanKind.getId().setPayRefReason(codeMap.get(prpLPlanKind.getChargeCode() + "Z") + "");

//			if (null != serialNoMap.get(prpLPlanKind.getId().getPayRefReason() + objs[8].toString())) {
//				serialNo = Integer.parseInt(serialNoMap.get(prpLPlanKind.getId().getPayRefReason() + objs[8].toString()).toString());
//			}
			tempKey = "PRPLCHARGE_"+prpccoins_serialno+"_" + objs[12];
			if (null != serialNoMap.get(tempKey)) {
				serialNo = (Integer) serialNoMap.get(tempKey);
			}
			prpLPlanKind.getId().setSerialNo(serialNo);
			
			prpLPlanKind.setPlanFeeCurrency(objs[5].toString());
//			if("1".equals(coinsFlag)){
//				prpLPlanKind.setKindFee(((Number) objs[9]).doubleValue()/coinsRate_0 * coinsRate);
//			}else{
//				prpLPlanKind.setKindFee(((Number) objs[9]).doubleValue() * coinsRate);
//			}
			prpLPlanKind.setKindFee(((Number) objs[9]).doubleValue());
			prpLPlanKind.setExchangeRate(((Number) objs[13]).doubleValue());
			prpLPlanKind.setPlanFeeCNY(prpLPlanKind.getKindFee() * prpLPlanKind.getExchangeRate());
			prpLPlanKind.setKindFeeRate(0D);
			prpLPlanKind.setInputDate(inputDate);
//			chargeCodeMap.put(objs[8].toString(), prpLPlanKind.getId().getPayRefReason() + prpLPlanKind.getId().getSerialNo());
			if (prpLPlanKind.getKindFee() != 0) {
				prpLPlanKindList.add(prpLPlanKind);
			}
		}
		prpLPlanKindList = this.getPrPccoinsKindList(prpLcompensate, prpLPlanKindList, serialNoMap, coinsType, isComBin);
		return prpLPlanKindList;
	}
	/**
	 * 获取联共保冲账险种
	 * @return
	 * @throws Exception
	 */
	public List<PrpLplanKind> getPrPccoinsKindList(PrpLcompensate prpLcompensate,List<PrpLplanKind> prpLPlanKindList,Map<String,Object> serialNoMap,String coinsType,String isComBin)throws Exception{
//		String riskCode = "";
//		if(prpLcompensate!=null){
//			riskCode = prpLcompensate.getRiskCode();
//		}
		// 组合险子险种获取,车险也从这里面获取
		for (int x = 0; x < prpLPlanKindList.size(); x++) {
			PrpLplanKind prpLPlanKindtmp = (PrpLplanKind) prpLPlanKindList.get(x);
			prpLPlanKindtmp.setSubRiskCode(this.findSubRiskCode(prpLPlanKindtmp));
		}

		// 他方从联共冲帳
//		if (!coinsType.equals("2")) {
//			String prpccoins_serialno = ""; //联共保号码
//			if(serialNoMap.get("PRPCCOINS_SERIALNO")!=null){
//				prpccoins_serialno = String.valueOf(serialNoMap.get("PRPCCOINS_SERIALNO"));
//			}
//			List<PrpLplanKind> prpLPlanKindListT = new ArrayList<PrpLplanKind>();
//			PrpLplanKind prpLPlanKindTemp = null;
//			PrpLplanKind prpLPlanKind = null;
//			PrpLplanKindId prpLPlanKindId = null;
//			String tempKey = null;
//			int serialNo = 1;
//			for (int i = 0; i < prpLPlanKindList.size(); i++) {
//				prpLPlanKind =  prpLPlanKindList.get(i);
//				prpLPlanKindTemp = new PrpLplanKind();
//				prpLPlanKindId = prpLPlanKindTemp.getId();
//				BeanUtils.copyProperties(prpLPlanKind, prpLPlanKindTemp);
//				BeanUtils.copyProperties(prpLPlanKind.getId(), prpLPlanKindId);
//				prpLPlanKindTemp.setId(prpLPlanKindId);
//				
//				tempKey = "PRPCCOINS_"+prpccoins_serialno+"_" + prpLPlanKind.getId().getSerialNo();
//				if(serialNoMap.get(tempKey)!=null){
//					serialNo = (Integer)serialNoMap.get(tempKey);
//				}
//				prpLPlanKindTemp.getId().setSerialNo(serialNo);
//				if(serialNoMap.get("PAYREFREASON_"+serialNo)!=null){
//					prpLPlanKindTemp.getId().setPayRefReason(String.valueOf(serialNoMap.get("PAYREFREASON_"+serialNo)));
//				}else{
//					prpLPlanKindTemp.getId().setPayRefReason("");
//				}
//				prpLPlanKindTemp.setKindFee(-prpLPlanKind.getKindFee());
//				prpLPlanKindTemp.setPlanFeeCNY(-prpLPlanKind.getPlanFeeCNY());
//				if (prpLPlanKindTemp.getKindFee() != 0) {
//					prpLPlanKindListT.add(prpLPlanKindTemp);
//				}
//			}
//			if (prpLPlanKindListT.size() > 0) {
//				prpLPlanKindList.addAll(prpLPlanKindListT);
//			}
//		}
		return prpLPlanKindList;
	}
	
	public PrpLcompensateService getPrpLcompensateService() {
		if (prpLcompensateService == null) {
			prpLcompensateService = (PrpLcompensateService) ServiceFactory.getService("prpLcompensateService");
		}
		return prpLcompensateService;
	}

	public void setPrpLcompensateService(PrpLcompensateService prpLcompensateService) {
		this.prpLcompensateService = prpLcompensateService;
	}

	public PrpLclaimService getPrpLclaimService() {
		if (prpLclaimService == null) {
			prpLclaimService = (PrpLclaimService) ServiceFactory.getService("prpLclaimService");
		}
		return prpLclaimService;
	}

	public void setPrpLclaimService(PrpLclaimService prpLclaimService) {
		this.prpLclaimService = prpLclaimService;
	}

	public PrpCitemKindService getPrpCitemKindService() {
		if (prpCitemKindService == null) {
			prpCitemKindService = (PrpCitemKindService) ServiceFactory.getService("prpCitemKindService");
		}
		return prpCitemKindService;
	}

	public void setPrpCitemKindService(PrpCitemKindService prpCitemKindService) {
		this.prpCitemKindService = prpCitemKindService;
	}

	public PrpCitemCarService getPrpCitemCarService() {
		if (prpCitemCarService == null) {
			prpCitemCarService = (PrpCitemCarService) ServiceFactory.getService("prpCitemCarService");
		}
		return prpCitemCarService;
	}

	public void setPrpCitemCarService(PrpCitemCarService prpCitemCarService) {
		this.prpCitemCarService = prpCitemCarService;
	}

	public CodeService getCodeService() {
		if(codeService==null){
			codeService = (CodeService) ServiceFactory.getService("codeService");
		}
		return codeService;
	}

	public void setCodeService(CodeService codeService) {
		this.codeService = codeService;
	}

}
