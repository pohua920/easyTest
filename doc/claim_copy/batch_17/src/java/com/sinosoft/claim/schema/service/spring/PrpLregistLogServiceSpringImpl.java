/*
 * @(#)PrpLregistLogServiceSpringImpl.java	Jan 30, 2013
 *
 * @Company < Technology Development Company LTD..>
 */
package com.sinosoft.claim.schema.service.spring;

import java.util.List;

import ins.framework.common.Page;
import ins.framework.common.QueryRule;
import ins.framework.dao.GenericDaoHibernate;

import com.sinosoft.claim.schema.model.PrpLregistLog;
import com.sinosoft.claim.schema.model.PrpLregistLogId;
import com.sinosoft.claim.schema.service.facade.PrpLregistLogService;

/**
 * 报案修改轨迹信息表接口
 * @Project <CL-Allocation tool>
 * @version <1.0>
 * @author 中科软
 * @description 
 */
public class PrpLregistLogServiceSpringImpl extends GenericDaoHibernate<PrpLregistLog, PrpLregistLogId> implements PrpLregistLogService {
	/**
	 * @param queryRule
	 * @return
	 * @throws Exception
	 * 根据条件查询
	 */
	public List<PrpLregistLog> findByQuery(QueryRule queryRule)throws Exception{
		return super.find(queryRule);
	}
	/**
	 * @param logId
	 * @param registNo
	 * @throws Exception
	 * 保存轨迹信息
	 */
	public void save(String logId,String registNo)throws Exception{
		StringBuffer buffer = new StringBuffer(200);
		buffer.append("insert into PrpLregistLog  "
						+ "(LogID,RegistNo,lFlag,ClassCode,RiskCode,"
						+ "PolicyNo,Language,InsuredCode,InsuredName,"
						+ "InsuredAddress,ClauseType,LicenseNo,LicenseColorCode,"
						+ "CarKindCode,ModelCode,BrandName,EngineNo,"
						+ "FrameNo,RunDistance,UseYears,Reportdate,"
						+ "ReportHour,ReportAddress,ReportorName,ReportType,"
						+ "Phonenumber,LinkerName,Damagestartdate,DamageStartHour,"
						+ "Damageenddate,DamageEndHour,DamageCode,DamageName,"
						+ "DamageTypeCode,DamageTypeName,FirstSiteFlag,DamageAreaCode,"
						+ "DamageAreaName,DamageAddressType,AddressCode,DamageAddress,"
						+ "DamageAreaPostCode,HandleUnit,LossName,LossQuantity,Unit,"
						+ "EstiCurrency,EstimateLoss,ReceiverName,HandlerCode,Handler1Code,ComCode,"
						+ "Inputdate,AcceptFlag,RepeatInsureFlag,ClaimType,Canceldate,"
						+ "DealerCode,Remark,OperatorCode,MakeCom,Flag,Reportorphonenumber,"
						+ "LinkerPostCode,LinkerAddress,EstimateFee,CatastropheCode1,CatastropheName1,"
						+ "CatastropheCode2,CatastropheName2,ReportFlag,IndemnityDuty,TERMFLAG,Altername,"
						+ "Alterphonenumber,AlterRelationType,alterTime,alterLocus,alterType)  "
						+ "select '"
						+ logId
						+ "',a.RegistNo,a.lFlag,a.ClassCode,a.RiskCode,a.PolicyNo,"
						+ "a.Language,a.InsuredCode,a.InsuredName,a.InsuredAddress,"
						+ "a.ClauseType,   a.LicenseNo,a.LicenseColorCode,a.CarKindCode,"
						+ "a.ModelCode,a.BrandName,a.EngineNo,a.FrameNo,a.RunDistance,"
						+ "a.UseYears,a.Reportdate,a.ReportHour,a.ReportAddress,a.ReportorName,"
						+ "a.ReportType,a.Phonenumber,a.LinkerName,a.Damagestartdate,"
						+ "a.DamageStartHour,a.Damageenddate,a.DamageEndHour,a.DamageCode,"
						+ "a.DamageName,a.DamageTypeCode,a.DamageTypeName,a.FirstSiteFlag,"
						+ "a.DamageAreaCode,a.DamageAreaName,a.DamageAddressType,a.AddressCode,"
						+ "a.DamageAddress,a.DamageAreaPostCode,a.HandleUnit,a.LossName,"
						+ "a.LossQuantity,a.Unit,a.EstiCurrency,a.EstimateLoss,a.ReceiverName,"
						+ "a.HandlerCode,a.Handler1Code,a.ComCode,a.Inputdate,a.AcceptFlag,"
						+ "a.RepeatInsureFlag,a.ClaimType,a.Canceldate,a.DealerCode,a.Remark,"
						+ "a.OperatorCode,a.MakeCom,a.Flag,a.Reportorphonenumber,a.LinkerPostCode,"
						+ "a.LinkerAddress,a.EstimateFee,a.CatastropheCode1,a.CatastropheName1,"
						+ "a.CatastropheCode2,a.CatastropheName2,a.ReportFlag,a.IndemnityDuty,TERMFLAG,Altername,"
						+ "Alterphonenumber,AlterRelationType,alterTime,alterLocus,alterType "
						+ "from PrpLregist a   where a.registNo = '" + registNo
						+ "'");
		super.getSession().createSQLQuery(buffer.toString()).executeUpdate();
	}
	@Override
	public void delete(PrpLregistLogId prpLregistLogId) throws Exception {
		super.deleteByPK(prpLregistLogId);
		logger.info("删除报案修改轨迹编号为" + prpLregistLogId + "的报案修改轨迹信息");
	}
	@Override
	public PrpLregistLog findPrpLregistLog(PrpLregistLogId prpLregistLogId)
			throws Exception {
		logger.info("查询报案修改轨迹编号为" + prpLregistLogId + "的报案修改轨迹信息");
		return super.get(PrpLregistLog.class,prpLregistLogId);
	}
	@Override
	public Page findPrpLregistLog(QueryRule queryRule, int pageNo, int pageSize)
			throws Exception {
		logger.info("获取报案修改轨迹列表信息");
		return super.find(queryRule, pageNo, pageSize);
	}
	@Override
	public List<PrpLregistLog> findPrpLregistLog(QueryRule queryRule)
			throws Exception {
		return super.find(queryRule);
	}
	@Override
	public void save(PrpLregistLog prpLregistLog) throws Exception {
		logger.info("保存报案修改轨迹信息");
		super.save(prpLregistLog);
	}
	@Override
	public void save(List<PrpLregistLog> list) throws Exception {
		for (int i = 0; i < list.size(); i++) {
			super.save(list.get(i));
		}
	}
}
