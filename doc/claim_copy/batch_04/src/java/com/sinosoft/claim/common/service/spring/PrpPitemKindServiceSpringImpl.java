package com.sinosoft.claim.common.service.spring;

import ins.framework.common.QueryRule;
import ins.framework.dao.GenericDaoHibernate;
import ins.framework.utils.DataUtils;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;

import com.sinosoft.claim.common.service.facade.PrpPitemKindService;
import com.sinosoft.claim.common.util.CommonUtils;
import com.sinosoft.claim.common.util.HibernateUtils;
import com.sinosoft.claim.schema.model.PrpPitemKind;

public class PrpPitemKindServiceSpringImpl extends GenericDaoHibernate<PrpPitemKind, String> implements PrpPitemKindService {

	@Override
	public ArrayList<PrpPitemKind> findByConditions(String conditions, int pageNo, int rowsPerPage) throws Exception {
		String statement = "Select EndorseNo," + " PolicyNo," + " RiskCode," + " ItemKindNo," + " FamilyNo," + " FamilyName," + " KindCode," + " KindName," + " ItemNo," + " ItemCode," + " ItemDetailName," + " ModeCode," + " ModeName,"
				+ " StartDate," + " StartHour," + " EndDate," + " EndHour," + " Model," + " BuyDate," + " AddressNo," + " CalculateFlag," + " Currency," + " UnitAmount," + " Quantity," + " Unit," + " Value," + " Amount," + " RatePeriod," + " Rate,"
				+ " ShortRateFlag," + " ShortRate," + " BasePremium," + " BenchMarkPremium," + " Discount," + " AdjustRate," + " Premium," + " DeductibleRate," + " Deductible," + " Flag," + " ChgQuantity," + " ChgAmount,"
				+ " ChgPremium From PrpPitemKind Where " + conditions;
		PrpPitemKind prpPitemKind = null;
		ArrayList<PrpPitemKind> resultList = new ArrayList<PrpPitemKind>();
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		List<?> tempList = HibernateUtils.findbySql(session, statement.toString(), pageNo, rowsPerPage);
		for (int i = 0; i < tempList.size(); i++) {
			Object[] object = (Object[]) tempList.get(i);// 每行记录不在是一个对象
			// 而是一个数组
			prpPitemKind=new PrpPitemKind();
            prpPitemKind.getId().setEndorseNo(DataUtils.getString(object[0]));
            prpPitemKind.setPolicyNo(String.valueOf(object[1]));
            prpPitemKind.setRiskCode(String.valueOf(object[2]));
            prpPitemKind.getId().setItemKindNo(object[3]==null?0:DataUtils.getInteger(object[3]));
            prpPitemKind.setFamilyNo(object[4]==null?0:DataUtils.getInteger(object[4]));
            prpPitemKind.setFamilyName(DataUtils.getString(object[5]));
            prpPitemKind.setKindCode(String.valueOf(object[6]));
            prpPitemKind.setKindName(DataUtils.getString(object[7]));
            prpPitemKind.setItemNo(object[8]==null?0:DataUtils.getInteger(object[8]));
            prpPitemKind.setItemCode(DataUtils.getString(object[9]));
            prpPitemKind.setItemDetailName(DataUtils.getString(object[10]));
            prpPitemKind.setModeCode(DataUtils.getString(object[11]));
            prpPitemKind.setModeName(DataUtils.getString(object[12]));
            prpPitemKind.setStartDate(CommonUtils.toYearToSercondDate(String.valueOf(object[13])));
            prpPitemKind.setStartHour(object[14]==null?0:DataUtils.getInteger(object[14]));
            prpPitemKind.setEndDate(CommonUtils.toYearToSercondDate(String.valueOf(object[15])));
            prpPitemKind.setEndHour(object[16]==null?0:DataUtils.getInteger(object[16]));
            prpPitemKind.setModel(DataUtils.getString(object[17]));
            prpPitemKind.setBuyDate(CommonUtils.toYearToSercondDate(String.valueOf(object[18])));
            prpPitemKind.setAddressNo(object[19]==null?0:DataUtils.getInteger(object[19]));
            prpPitemKind.setCalculateFlag(DataUtils.getString(object[20]));
            prpPitemKind.setCurrency(DataUtils.getString(object[21]));
            prpPitemKind.setUnitAmount(DataUtils.getDouble(object[22]));
            prpPitemKind.setQuantity(DataUtils.getDouble(object[23]));
            prpPitemKind.setUnit(DataUtils.getString(object[24]));
            prpPitemKind.setValue(DataUtils.getDouble(object[25]));
            prpPitemKind.setAmount(DataUtils.getDouble(object[26]));
            prpPitemKind.setRatePeriod(DataUtils.getInteger(object[27]));
            prpPitemKind.setRate(DataUtils.getDouble(object[28]));
            prpPitemKind.setShortRateFlag(String.valueOf(object[29]));
            prpPitemKind.setShortRate(DataUtils.getDouble(object[30]));
            prpPitemKind.setBasePremium(DataUtils.getDouble(object[31]));
            prpPitemKind.setBenchMarkPremium(DataUtils.getDouble(object[32]));
            prpPitemKind.setDiscount(DataUtils.getDouble(object[33]));
            prpPitemKind.setAdjustRate(DataUtils.getDouble(object[34]));
            prpPitemKind.setPremium(DataUtils.getDouble(object[35]));
            prpPitemKind.setDeductibleRate(DataUtils.getDouble(object[36]));
            prpPitemKind.setDeductible(DataUtils.getDouble(object[37]));
            prpPitemKind.setFlag(String.valueOf(object[38]));
            prpPitemKind.setChgQuantity(DataUtils.getDouble(object[39]));
            prpPitemKind.setChgAmount(DataUtils.getDouble(object[40]));
            prpPitemKind.setChgPremium(DataUtils.getDouble(object[41]));
            resultList.add(prpPitemKind);
		}
		return resultList;
	}

	
	public List<PrpPitemKind> findByConditions(String conditions) throws Exception {
		return super.find(QueryRule.getInstance().addSql(conditions));
	}
}
