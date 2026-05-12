package com.sinosoft.claim.common.service.spring;

import ins.framework.common.Page;
import ins.framework.common.QueryRule;
import ins.framework.dao.GenericDaoHibernate;
import ins.framework.utils.DataUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;

import com.sinosoft.claim.common.service.facade.PrpPmainService;
import com.sinosoft.claim.common.util.HibernateUtils;
import com.sinosoft.claim.schema.model.PrpPmain;

public class PrpPmainServiceSpringImpl extends GenericDaoHibernate<PrpPmain,String> implements PrpPmainService {

	@Override
	public ArrayList<PrpPmain> findByConditions(String conditions, int pageNo, int rowsPerPage) throws Exception {
		String statement = "Select EndorseNo," + " PolicyNo," + " ClassCode," + " RiskCode," 
							  + " ProposalNo," + " ContractNo," + " PolicySort," + " PrintNo," 
							  + " BusinessNature," + " Language," + " PolicyType," + " AppliCode," 
							  + " AppliName," + " AppliAddress," + " InsuredCode," + " InsuredName," 
							  + " InsuredAddress," + " OperateDate," + " StartDate," + " StartHour," 
							  + " EndDate," + " EndHour," + " PureRate," + " DisRate," + " Discount," 
							  + " Currency," + " SumValue," + " SumAmount," + " SumDiscount," + " SumPremium," 
							  + " SumSubPrem," + " SumQuantity," + " JudicalScope," + " AutoTransRenewFlag," 
							  + " ArgueSolution," + " ArbitBoardName," + " PayTimes," + " EndorseTimes," 
							  + " ClaimTimes," + " MakeCom," + " OperateSite," + " ComCode," + " HandlerCode," 
							  + " Handler1Code," + " ApproverCode," + " UnderWriteCode," + " UnderWriteName," 
							  + " OperatorCode," + " InputDate," + " InputHour," + " UnderWriteEndDate,"
							  + " StatisticsYM," + " AgentCode," + " CoinsFlag," + " ReinsFlag," + " AllinsFlag," 
							  + " UnderWriteFlag," + " OthFlag," + " Flag," + " ChgAmount," + " ChgPremium," 
							  + " ChgSubPrem," + " ChgQuantity From PrpPmain Where " + conditions;
		PrpPmain prpPmain=null;
		ArrayList<PrpPmain> resultList = new ArrayList<PrpPmain>();
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		List<?> tempList = HibernateUtils.findbySql(session, statement.toString(), pageNo, rowsPerPage);
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		for (int i = 0; i < tempList.size(); i++) {
			Object[] object = (Object[]) tempList.get(i);// 每行记录不在是一个对象
			// 而是一个数组
			prpPmain=new PrpPmain();
            prpPmain.setEndorseNo(DataUtils.getString(object[0]));
            prpPmain.setPolicyNo(DataUtils.getString(object[1]));
            prpPmain.setClassCode(DataUtils.getString(object[2]));
            prpPmain.setRiskCode(DataUtils.getString(object[3]));
            prpPmain.setProposalNo(DataUtils.getString(object[4]));
            prpPmain.setContractNo(DataUtils.getString(object[5]));
            prpPmain.setPolicySort(DataUtils.getString(object[6]));
            prpPmain.setPrintNo(DataUtils.getString(object[7]));
            prpPmain.setBusinessNature(DataUtils.getString(object[8]));
            prpPmain.setLanguage(DataUtils.getString(object[9]));
            prpPmain.setPolicyType(DataUtils.getString(object[10]));
            prpPmain.setAppliCode(DataUtils.getString(object[11]));
            prpPmain.setAppliName(DataUtils.getString(object[12]));
            prpPmain.setAppliAddress(DataUtils.getString(object[13]));
            prpPmain.setInsuredCode(DataUtils.getString(object[14]));
            prpPmain.setInsuredName(DataUtils.getString(object[15]));
            prpPmain.setInsuredAddress(DataUtils.getString(object[16]));
           
            prpPmain.setOperateDate(object[17]==null?null:dateFormat.parse(DataUtils.getString(object[17])));
            prpPmain.setStartDate(object[18]==null?null:dateFormat.parse(DataUtils.getString(object[18])));
            prpPmain.setStartHour(DataUtils.getInteger(object[19]));
            prpPmain.setEndDate(object[20]==null?null:dateFormat.parse(DataUtils.getString(object[20])));
            prpPmain.setEndHour(DataUtils.getInteger(object[21]));
            prpPmain.setPureRate(DataUtils.getDouble(object[22]));
            prpPmain.setDisRate(DataUtils.getDouble(object[23]));
            prpPmain.setDiscount(DataUtils.getDouble(object[24]));
            prpPmain.setCurrency(DataUtils.getString(object[25]));
            prpPmain.setSumValue(DataUtils.getDouble(object[26]));
            prpPmain.setSumAmount(DataUtils.getDouble(object[27]));
            prpPmain.setSumDiscount(DataUtils.getDouble(object[28]));
            prpPmain.setSumPremium(DataUtils.getDouble(object[29]));
            prpPmain.setSumSubPrem(DataUtils.getDouble(object[30]));
            prpPmain.setSumQuantity(DataUtils.getInteger(object[31]));
            prpPmain.setJudicalScope(DataUtils.getString(object[32]));
            prpPmain.setAutoTransRenewFlag(DataUtils.getString(object[33]));
            prpPmain.setArgueSolution(DataUtils.getString(object[34]));
            prpPmain.setArbitBoardName(DataUtils.getString(object[35]));
            prpPmain.setPayTimes(DataUtils.getInteger(object[36]));
            prpPmain.setEndorseTimes(DataUtils.getInteger(object[37]));
            prpPmain.setClaimTimes(DataUtils.getInteger(object[38]));
            prpPmain.setMakeCom(DataUtils.getString(object[39]));
            prpPmain.setOperateSite(DataUtils.getString(object[40]));
            prpPmain.setComCode(DataUtils.getString(object[41]));
            prpPmain.setHandlerCode(DataUtils.getString(object[42]));
            prpPmain.setHandler1Code(DataUtils.getString(object[43]));
            prpPmain.setApproverCode(DataUtils.getString(object[44]));
            prpPmain.setUnderWriteCode(DataUtils.getString(object[45]));
            prpPmain.setUnderWriteName(DataUtils.getString(object[46]));
            prpPmain.setOperatorCode(DataUtils.getString(object[47]));
            prpPmain.setInputDate(object[48]==null?null:dateFormat.parse(DataUtils.getString(object[48])));
            prpPmain.setInputHour(DataUtils.getInteger(object[49]));
            prpPmain.setUnderWriteEndDate(object[50]==null?null:dateFormat.parse(DataUtils.getString(object[50])));
            prpPmain.setStatisticsYM(object[51] == null ? null : dateFormat.parse(DataUtils.getString(object[51])));
            prpPmain.setAgentCode(DataUtils.getString(object[52]));
            prpPmain.setCoinsFlag(DataUtils.getString(object[53]));
            prpPmain.setReinsFlag(DataUtils.getString(object[54]));
            prpPmain.setAllinsFlag(DataUtils.getString(object[55]));
            prpPmain.setUnderWriteFlag(DataUtils.getString(object[56]));
            prpPmain.setOthFlag(DataUtils.getString(object[57]));
            prpPmain.setFlag(DataUtils.getString(object[58]));
            prpPmain.setChgAmount(DataUtils.getDouble(object[59]));
            prpPmain.setChgPremium(DataUtils.getDouble(object[60]));
            prpPmain.setChgSubPrem(DataUtils.getDouble(object[61]));
            prpPmain.setChgQuantity(DataUtils.getInteger(object[62]));
            resultList.add(prpPmain);
		}
		return resultList;
	}

	@Override
	public PrpPmain findByPrimaryKey(String endorseNo) throws Exception {
		return super.get(PrpPmain.class, endorseNo);
	}

	@Override
	public void delete(String endorseNo) throws Exception {
		super.deleteByPK(endorseNo);
		logger.info("删除批改保单信息编号为" + endorseNo + "的批改保单信息信息");
	}

	@Override
	public Page findPrpPmain(QueryRule queryRule, int pageNo, int pageSize)
			throws Exception {
		logger.info("获取批改保单信息列表信息");
		return super.find(queryRule, pageNo, pageSize);
	}

	@Override
	public List<PrpPmain> findPrpPmain(QueryRule queryRule) throws Exception {
		return super.find(queryRule);
	}

	@Override
	public void save(PrpPmain prpPmain) throws Exception {
		logger.info("保存批改保单信息信息");
		super.save(prpPmain);
	}

	@Override
	public void save(List<PrpPmain> list) throws Exception {
		for (int i = 0; i < list.size(); i++) {
			super.save(list.get(i));
		}
	}

}
