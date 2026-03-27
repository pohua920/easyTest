package com.sinosoft.claim.certainLoss.service.spring;

import ins.framework.common.Page;
import ins.framework.common.QueryRule;
import ins.framework.dao.GenericDaoHibernate;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.TreeSet;

import org.hibernate.Session;

import com.sinosoft.app.common.util.StringUtil;
import com.sinosoft.claim.certainLoss.service.facade.CertainLossService;
import com.sinosoft.claim.certainLoss.vo.CertainLossDto;
import com.sinosoft.claim.check.service.facade.CheckService;
import com.sinosoft.claim.check.vo.CheckDto;
import com.sinosoft.claim.common.util.CommonUtils;
import com.sinosoft.claim.common.util.HibernateUtils;
import com.sinosoft.claim.schema.model.PrpLcarLoss;
import com.sinosoft.claim.schema.model.PrpLclaimStatus;
import com.sinosoft.claim.schema.model.PrpLclaimStatusId;
import com.sinosoft.claim.schema.model.PrpLcomponent;
import com.sinosoft.claim.schema.model.PrpLperson;
import com.sinosoft.claim.schema.model.PrpLpersonWound;
import com.sinosoft.claim.schema.model.PrpLprop;
import com.sinosoft.claim.schema.model.PrpLregistExt;
import com.sinosoft.claim.schema.model.PrpLrepairFee;
import com.sinosoft.claim.schema.model.PrpLthirdParty;
import com.sinosoft.claim.schema.model.PrpLthirdPartyId;
import com.sinosoft.claim.schema.model.PrpLverifyLoss;
import com.sinosoft.claim.schema.model.PrpLverifyLossExt;
import com.sinosoft.claim.schema.service.facade.PrpLcarLossService;
import com.sinosoft.claim.schema.service.facade.PrpLcheckLossService;
import com.sinosoft.claim.schema.service.facade.PrpLclaimStatusService;
import com.sinosoft.claim.schema.service.facade.PrpLcomponentService;
import com.sinosoft.claim.schema.service.facade.PrpLpersonService;
import com.sinosoft.claim.schema.service.facade.PrpLpersonWoundService;
import com.sinosoft.claim.schema.service.facade.PrpLpropService;
import com.sinosoft.claim.schema.service.facade.PrpLregistExtService;
import com.sinosoft.claim.schema.service.facade.PrpLrepairFeeService;
import com.sinosoft.claim.schema.service.facade.PrpLthirdPartyService;
import com.sinosoft.claim.schema.service.facade.PrpLthirdPropService;
import com.sinosoft.claim.schema.service.facade.PrpLverifyLossExtService;
import com.sinosoft.claim.schema.service.facade.PrpLverifyLossItemService;
import com.sinosoft.claim.schema.service.facade.PrpLverifyLossService;
import com.sinosoft.claim.workflow.service.facade.WorkFlowService;
import com.sinosoft.claim.workflow.vo.WorkFlowDto;
import com.sinosoft.one.bpm.aspect.ProcessTask;
import com.sinosoft.sysframework.common.datatype.DateTime;
import com.sinosoft.sysframework.common.util.DataUtils;

/**
 * 定损对象CertainLoss
 * <p>
 * Title: 车险理赔样本定损action
 * </p>
 * <p>
 * Description: 车险理赔样本定损action
 * </p>
 * <p>
 * Copyright: Copyright (c) 2013
 * </p>
 * <p>
 * Company: Sinosoft
 * @author 中科软
 * </p>
 */
@SuppressWarnings("unchecked")
public class CertainLossServiceSpringImpl extends GenericDaoHibernate<CertainLossDto, String> implements CertainLossService {
	/**
	 * 保存定损
	 * @param certainLossDto：自定义定损对象
	 * @throws SQLException
	 * @throws Exception
	 */
	public void save(CertainLossDto certainLossDto) throws SQLException, Exception {
		if (certainLossDto.getPrpLverifyLoss() == null) {
			throw new Exception();
		}
		// 报案号码 关键字
		PrpLverifyLoss prpLverifyLoss = certainLossDto.getPrpLverifyLoss();
		String registNo = prpLverifyLoss.getId().getRegistNo();
		String lossItemCode = prpLverifyLoss.getId().getLossItemCode();
		String nodeType = prpLverifyLoss.getId().getNodeType();
		// 首先删除原来的相关数据
		this.deleteSubInfo(registNo, lossItemCode,nodeType);
		// reason:在定损中加入三者车的车牌号
		// 人员伤亡明细信息表
		List<PrpLperson> prpLpersonList = certainLossDto.getPrpLpersonList();
		if ("certa".equals(nodeType)) {
			if(certainLossDto.getPrpLthirdParty()!=null){
				String licenseNo = certainLossDto.getPrpLthirdParty().getLicenseNo();
				prpLverifyLoss.setLossItemName(licenseNo);
				prpLverifyLoss.setLicenseNo(licenseNo);
			}
		}else if ("wound".equals(nodeType)) {
			if(prpLpersonList.size()>0){
				prpLverifyLoss.setLossItemName(prpLpersonList.get(0).getPersonName());
			}else{
				prpLverifyLoss.setLossItemName("人傷");
			}
		} else if ("propc".equals(nodeType)) {
			prpLverifyLoss.setLossItemName("財產");
		}
		this.prpLverifyLossService.saveOrUpdate(certainLossDto.getPrpLverifyLoss());
		// 定损车辆表
		List<PrpLcarLoss> prpLcarLossList = certainLossDto.getPrpLcarLossList();
		if (prpLcarLossList != null && !prpLcarLossList.isEmpty()) {
			this.prpLcarLossService.save(prpLcarLossList);
		}
		// 修理费用清单
		List<PrpLrepairFee> prpLrepairFeeList = certainLossDto.getPrpLrepairFeeList();
		if (prpLrepairFeeList != null && !prpLrepairFeeList.isEmpty()) {
			this.prpLrepairFeeService.save(prpLrepairFeeList);
		}
		// 换件项目清单
		List<PrpLcomponent> prpLcomponentList = certainLossDto.getPrpLcomponentList();
		if (prpLcomponentList != null && !prpLcomponentList.isEmpty()) {
			this.prpLcomponentService.save(prpLcomponentList);
		}
		// 定核损扩展信息
		List<PrpLverifyLossExt> prpLverifyLossExtList = certainLossDto.getPrpLverifyLossExtList();
		if (prpLverifyLossExtList != null && !prpLverifyLossExtList.isEmpty()) {
			this.prpLverifyLossExtService.save(prpLverifyLossExtList);
		}
		// 扩展信息
		List<PrpLregistExt> prpLregistExtList = certainLossDto.getPrpLregistExtList();
		if (prpLregistExtList != null && !prpLregistExtList.isEmpty()) {
			this.prpLregistExtService.save(prpLregistExtList);
		}
		
		if (prpLpersonList != null) {
			this.prpLpersonService.save(prpLpersonList);
		}
		// 财产核定损明细清单表
		List<PrpLprop> prpLpropList = certainLossDto.getPrpLpropList();
		if (prpLpropList != null && !prpLpropList.isEmpty()) {
			this.prpLpropService.save(prpLpropList);
		}
		// 定核损处理标的表
		// 伤情信息表
		List<PrpLpersonWound> prpLpersonWoundList = certainLossDto.getPrpLpersonWoundList();
		if (prpLpersonWoundList != null && !prpLpersonWoundList.isEmpty()) {
			this.prpLpersonWoundService.save(prpLpersonWoundList);
		}
		// 定损涉案车辆可修改，需保存三者车的信息
		PrpLthirdParty prpLthirdParty = certainLossDto.getPrpLthirdParty();
		if (prpLthirdParty != null) {
			PrpLthirdParty pre = this.prpLthirdPartyService.findPrpLthirdParty(prpLthirdParty.getId());
			String licenseNo = prpLthirdParty.getLicenseNo();
			if(!StringUtil.isBlank(licenseNo) && !licenseNo.equals(pre.getLicenseNo())){//有修改車牌號碼
				Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
				int serialNo = prpLthirdParty.getId().getSerialNo();
				//更新人傷定損車牌
				String statement = " UPDATE PrpLperson SET familyName = '" + StringUtil.PrtendNull(licenseNo) + "' where registno = '" + registNo + "' and familyName = '" + pre.getLicenseNo() + "' ";
				HibernateUtils.executeSql(session, statement);
				//更新車損 車牌訊息
				statement = " UPDATE PrpLthirdcarloss SET licenseNo = '" + StringUtil.PrtendNull(licenseNo) + "' where registno = '" + registNo + "' and serialNo = "+ serialNo +" and licenseNo = '" + pre.getLicenseNo() + "' ";
				HibernateUtils.executeSql(session, statement);
				//更新駕駛員 車牌訊息
				statement = " UPDATE PrpLdriver SET licenseNo = '" + StringUtil.PrtendNull(licenseNo) + "' where registno = '" + registNo + "' and licenseNo = '" + pre.getLicenseNo() + "' ";
				HibernateUtils.executeSql(session, statement);
				if(serialNo == 1){//修改標的車
					statement = " UPDATE PrpLcheck SET licenseNo = '" + StringUtil.PrtendNull(licenseNo) + "' where registno = '" + registNo + "' and licenseNo = '" + pre.getLicenseNo() + "' ";
					HibernateUtils.executeSql(session, statement);
				}
			}
			this.prpLthirdPartyService.delete(prpLthirdParty.getId());
			this.prpLthirdPartyService.saveOrUpdate(prpLthirdParty);
		}
		// 进行状态的改变
		this.updateClaimStatus(certainLossDto);
	}

	/**
	 * 定损删除子表信息
	 * @param registNo //报案号
	 * @throws SQLException
	 * @throws Exception
	 */
	private void deleteSubInfo(String registNo, String lossItemCode,String nodeType) throws SQLException, Exception {
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		String condition = " registNo = " + "'" + registNo.trim() + "'";
		// 示例未完成
		String statement = "";
		// 删除扩展信息
		statement = " DELETE FROM PrpLregistExt Where " + condition;
		HibernateUtils.executeSql(session, statement);
		if ("certa".equals(nodeType)) {
			// 删除定核损扩展信息
			String condition1 = " registNo = " + "'" + registNo.trim() + "' and  lossItemCode = '" + lossItemCode.trim() + "'";
			statement = " DELETE FROM PrpLverifyLossExt Where " + condition1;
			HibernateUtils.executeSql(session, statement);
			// 修理费用清单
			statement = " DELETE FROM prpLrepairFee Where " + condition + " and LossItemCode='" + lossItemCode + "'";
			HibernateUtils.executeSql(session, statement);
			// 换件项目清单
			statement = " DELETE FROM prpLcomponent Where " + condition + " and LossItemCode='" + lossItemCode + "'";
			HibernateUtils.executeSql(session, statement);
			// 定损车辆表
			statement = " DELETE FROM prpLcarLoss Where " + condition + " and LossItemCode='" + lossItemCode + "'";
			HibernateUtils.executeSql(session, statement);
		} else if ("wound".equals(nodeType)) {
			// 人员伤亡明细信息
			statement = " DELETE FROM prpLperson Where " + condition+" and personNo='" + lossItemCode + "'";
			HibernateUtils.executeSql(session, statement);
			// 伤情信息表
			statement = " DELETE FROM PrpLpersonWound Where " + condition+" and personNo='" + lossItemCode + "'";
			HibernateUtils.executeSql(session, statement);
		} else if ("propc".equals(nodeType)) {
			// 财产核定损明细
			statement = " DELETE FROM prpLprop Where " + condition;
			HibernateUtils.executeSql(session, statement);
		}
//		else if (i == -2) {
//		}
		this.prpLverifyLossService.delete(registNo, lossItemCode,nodeType);
	}

	/**
	 * 保存定损带工作流
	 * @param certainLossDto：自定义定损对象
	 * @throws SQLException
	 * @throws Exception
	 */
	public void save(CertainLossDto certainLossDto, WorkFlowDto workFlowDto) throws SQLException, Exception {
		this.save(certainLossDto);
		if (workFlowDto != null) {
			//修改工作流的牌照號碼
			PrpLthirdParty prpLthirdParty = certainLossDto.getPrpLthirdParty();
			if(prpLthirdParty!=null){
				String licenseNo = prpLthirdParty.getLicenseNo();
				if(!StringUtil.isBlank(licenseNo)){//定損保存后，同步車牌號碼到工作流任務
					workFlowDto.getParamMap().put("lossItemName", licenseNo);
				}
			} 
			List<PrpLperson> prpLpersonList = certainLossDto.getPrpLpersonList();
			if (prpLpersonList != null && !prpLpersonList.isEmpty()) {
				String personName = prpLpersonList.get(0).getPersonName();
				if(!StringUtil.isBlank(personName)){//人傷定損保存后，同步傷員名稱到工作流任務
					workFlowDto.getParamMap().put("lossItemName", personName);
				}
			}
			this.getWorkFlowService().deal(workFlowDto);
		}
	}

	/**
	 * 保存定损带工作流
	 * @param certainLossDto：自定义定损对象
	 * @throws SQLException
	 * @throws Exception定损
	 */
	@ProcessTask(processId = "claim_05",userId = "certa", businessBeanOffset = 0, businessIdAttributeName = "prpLverifyLoss.id.registNo")
	public void saveBpm(CertainLossDto certainLossDto, WorkFlowDto workFlowDto) throws SQLException, Exception {
		this.save(certainLossDto, workFlowDto);
	}

	/**
	 * 保存定损带工作流
	 * @param certainLossDto：自定义定损对象
	 * @throws SQLException
	 * @throws Exception, 人伤
	 */
	@ProcessTask(processId = "claim_05",userId = "wound", businessBeanOffset = 0, businessIdAttributeName = "prpLverifyLoss.id.registNo")
	public void saveBpm_wound(CertainLossDto certainLossDto, WorkFlowDto workFlowDto) throws SQLException, Exception {
		this.save(certainLossDto, workFlowDto);
	}

	/**
	 * 保存定损带工作流
	 * @param certainLossDto：自定义定损对象
	 * @throws SQLException
	 * @throws Exception, 财产
	 */
	@ProcessTask(processId = "claim_05",userId = "propc", businessBeanOffset = 0, businessIdAttributeName = "prpLverifyLoss.id.registNo")
	public void saveBpm_propc(CertainLossDto certainLossDto, WorkFlowDto workFlowDto) throws SQLException, Exception {
		this.save(certainLossDto, workFlowDto);
	}

	/**
	 * 保存定损带工作流
	 * @param certainLossDto：自定义定损对象
	 * @throws SQLException
	 * @throws Exception, 三者
	 */
	@ProcessTask(processId = "claim_05",userId = "certa_three", businessBeanOffset = 0, businessIdAttributeName = "prpLverifyLoss.id.registNo")
	public void saveBpm_certa_three(CertainLossDto certainLossDto, WorkFlowDto workFlowDto) throws SQLException, Exception {
		this.save(certainLossDto, workFlowDto);
	}

	/**
	 * 删除定损
	 * @param registNo
	 * @param dbManager 数据连接
	 * @throws SQLException
	 * @throws Exception
	 */
	public void delete(String registNo) throws SQLException, Exception {
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		// 创建数据库管理对象
		String condition = " registNo = " + "'" + registNo.trim() + "'";
		String condition1 = " businessNo = " + "'" + registNo.trim() + "' AND NodeType ='certa'";
		// 示例未完成
		String statement = "";
		// 修理费用清单
		statement = " DELETE FROM prpLrepairFee Where " + condition;
		HibernateUtils.executeSql(session, statement);
		// 换件项目清单
		statement = " DELETE FROM prpLcomponent Where " + condition;
		HibernateUtils.executeSql(session, statement);
		// 人员伤亡明细信息
		statement = " DELETE FROM prpLperson Where " + condition;
		HibernateUtils.executeSql(session, statement);
		// 财产核定损明细
		statement = " DELETE FROM prpLprop Where " + condition;
		HibernateUtils.executeSql(session, statement);
		// 定损车辆表
		statement = " DELETE FROM prpLcarLoss Where " + condition;
		HibernateUtils.executeSql(session, statement);
		// 伤情信息表
		statement = " DELETE FROM PrpLpersonWound Where " + condition;
		HibernateUtils.executeSql(session, statement);
		// 状态
		statement = " DELETE FROM prplclaimstatus Where " + condition1;
		HibernateUtils.executeSql(session, statement);
		// 定损主表
		statement = " DELETE FROM PrpLverifyLoss Where " + condition;
		HibernateUtils.executeSql(session, statement);
	}

	/**
	 * 获得定损
	 * @param registNo
	 * @param dbManager 数据连接
	 * @return 自定义定损对象
	 * @throws SQLException
	 * @throws Exception
	 */
	public CertainLossDto findByPrimaryKey(String registNo, String lossItemCode,String nodeType) throws SQLException, Exception {
		CertainLossDto certainLossDto = new CertainLossDto();
		certainLossDto.setPrpLverifyLoss(prpLverifyLossService.findPrpLverifyLoss(registNo,lossItemCode,nodeType));
		certainLossDto.setPrpLclaimStatus(this.prpLclaimStatusService.findPrpLclaimStatus(new PrpLclaimStatusId(registNo,nodeType, Integer.parseInt(DataUtils.nullToZero(lossItemCode)))));
		QueryRule queryRule = QueryRule.getInstance();
		queryRule.addEqual("id.registNo", registNo);
		certainLossDto.setPrpLverifyLossList(this.prpLverifyLossService.findPrpLverifyLoss(queryRule));
		QueryRule queryRulePerson = QueryRule.getInstance();
		queryRulePerson.addEqual("id.registNo", registNo);
		if("wound".equals(nodeType)){
			queryRulePerson.addEqual("id.personNo", Integer.valueOf(lossItemCode));
		}
		queryRulePerson.addAscOrder("id.personNo");
		certainLossDto.setPrpLpersonList(this.prpLpersonService.findPrpLperson(queryRulePerson));
		certainLossDto.setPrpLpersonWoundList(this.prpLpersonWoundService.findPrpLpersonWound(queryRulePerson));
		
		certainLossDto.setPrpLpropList(this.prpLpropService.findPrpLprop(queryRule));
		certainLossDto.setPrpLregistExtList(this.prpLregistExtService.findPrpLregistExt(queryRule));
		certainLossDto.setPrpLthirdPartyList(this.prpLthirdPartyService.findPrpLthirdParty(queryRule));
		certainLossDto.setPrpLthirdpropList(this.prpLthirdPropService.findPrpLthirdProp(queryRule));

		queryRule.addEqual("lossFeeType", "3");
		certainLossDto.setPrpLchecklossList(this.prpLcheckLossService.findPrpLcheckLoss(queryRule));

		queryRule = QueryRule.getInstance();
		queryRule.addEqual("id.registNo", registNo);
		queryRule.addEqual("id.lossItemCode", lossItemCode);
		certainLossDto.setPrpLverifyLossExtList(this.prpLverifyLossExtService.findPrpLverifyLossExt(queryRule));
		certainLossDto.setPrpLcarLossList(this.prpLcarLossService.findPrpLcarLoss(queryRule));

		queryRule.addAscOrder("id.serialNo");
		certainLossDto.setPrpLcomponentList(this.prpLcomponentService.findPrpLcomponent(queryRule));
		certainLossDto.setPrpLrepairFeeList(this.prpLrepairFeeService.findPrpLrepairFee(queryRule));

		certainLossDto.setPrpLthirdParty(this.prpLthirdPartyService.findPrpLthirdParty(new PrpLthirdPartyId(registNo, Integer.parseInt(lossItemCode))));
		return certainLossDto;
	}

	/**
	 * 获得定损
	 * @param registNo
	 * @param dbManager 数据连接
	 * @return 自定义定损对象
	 * @throws SQLException
	 * @throws Exception
	 */
	public CertainLossDto findByPrimaryKey(String registNo) throws SQLException, Exception {
		QueryRule queryRule = QueryRule.getInstance();
		queryRule.addEqual("id.registNo", registNo);
		CertainLossDto certainLossDto = new CertainLossDto();
		certainLossDto.setPrpLverifyLossList(this.prpLverifyLossService.findPrpLverifyLoss(queryRule));
		certainLossDto.setPrpLclaimStatus(this.prpLclaimStatusService.findPrpLclaimStatus(new PrpLclaimStatusId(registNo, "certa", 1)));
		certainLossDto.setPrpLcarLossList(this.prpLcarLossService.findPrpLcarLoss(queryRule));
		certainLossDto.setPrpLcomponentList(this.prpLcomponentService.findPrpLcomponent(queryRule));
		certainLossDto.setPrpLpropList(this.prpLpropService.findPrpLprop(queryRule));
		certainLossDto.setPrpLrepairFeeList(this.prpLrepairFeeService.findPrpLrepairFee(queryRule));
		certainLossDto.setPrpLverifyLossExtList(this.prpLverifyLossExtService.findPrpLverifyLossExt(queryRule));
		certainLossDto.setPrpLregistExtList(this.prpLregistExtService.findPrpLregistExt(queryRule));
		certainLossDto.setPrpLthirdPartyList(this.prpLthirdPartyService.findPrpLthirdParty(queryRule));
		
		QueryRule queryRulePerson = QueryRule.getInstance();
		queryRulePerson.addEqual("id.registNo", registNo);
		queryRulePerson.addAscOrder("id.personNo");
		certainLossDto.setPrpLpersonList(this.prpLpersonService.findPrpLperson(queryRulePerson));
		certainLossDto.setPrpLpersonWoundList(this.prpLpersonWoundService.findPrpLpersonWound(queryRulePerson));
		return certainLossDto;
	}
	/**
	 * 获得定损
	 * @param registNo
	 * * @param underWriteEndDate 核损时间
	 * @return 自定义定损对象
	 * @throws SQLException
	 * @throws Exception
	 */
	public CertainLossDto findByUnderWriteEndDate(String registNo,Date underWriteEndDate) throws SQLException, Exception {
		if(underWriteEndDate==null){
			return this.findByPrimaryKey(registNo);
		}
		CertainLossDto certainLossDto = new CertainLossDto();
		DateTime dateTime = new DateTime(underWriteEndDate,DateTime.YEAR_TO_SECOND);
		String conditions = " registNo = '"+registNo+"' and underWriteEndDate >=to_date('"+dateTime.toString()+"','yyyy-mm-dd hh24:mi:ss')";
		QueryRule queryRule = QueryRule.getInstance();
		queryRule.addSql(conditions);
		certainLossDto.setPrpLverifyLossList(prpLverifyLossService.findPrpLverifyLoss(queryRule));
		
//		certainLossDto.setPrpLclaimStatus(this.prpLclaimStatusService.findPrpLclaimStatus(new PrpLclaimStatusId(registNo,nodeType, Integer.parseInt(DataUtils.nullToZero(lossItemCode)))));
		
//		queryRule.addEqual("id.registNo", registNo);
		QueryRule queryRulePerson = QueryRule.getInstance();
		String personSql = "registNo='"+registNo+"' and personNo in (select lossItemCode from prpLverifyLoss where "+conditions+" and nodeType='wound')";
		queryRulePerson.addSql(personSql);
//		queryRulePerson.addEqual("id.registNo", registNo);
//		if("wound".equals(nodeType)){
//			queryRulePerson.addEqual("id.personNo", Integer.valueOf(lossItemCode));
//		}
//		queryRulePerson.addAscOrder("id.personNo");
		certainLossDto.setPrpLpersonList(this.prpLpersonService.findPrpLperson(queryRulePerson));
		certainLossDto.setPrpLpersonWoundList(this.prpLpersonWoundService.findPrpLpersonWound(queryRulePerson));
		queryRule = QueryRule.getInstance();
		String sql = "registNo='"+registNo+"' and exists(select lossItemCode from prpLverifyLoss where "+conditions+" and nodeType='propc')";
		queryRule.addSql(sql);
		certainLossDto.setPrpLpropList(this.prpLpropService.findPrpLprop(queryRule));
//		certainLossDto.setPrpLregistExtList(this.prpLregistExtService.findPrpLregistExt(queryRule));
//		certainLossDto.setPrpLthirdpropList(this.prpLthirdPropService.findPrpLthirdProp(queryRule));

//		queryRule.addEqual("lossFeeType", "3");
//		certainLossDto.setPrpLchecklossList(this.prpLcheckLossService.findPrpLcheckLoss(queryRule));
		
		queryRule = QueryRule.getInstance();
		sql = "registNo='"+registNo+"' and serialNo in (select lossItemCode from prpLverifyLoss where "+conditions+" and nodeType='certa')";
		queryRule.addSql(sql);
		certainLossDto.setPrpLthirdPartyList(this.prpLthirdPartyService.findPrpLthirdParty(queryRule));

		queryRule = QueryRule.getInstance();
		sql = "registNo='"+registNo+"' and lossItemCode in (select lossItemCode from prpLverifyLoss where "+conditions+" and nodeType='certa')";
		queryRule.addSql(sql);
		certainLossDto.setPrpLcarLossList(this.prpLcarLossService.findPrpLcarLoss(queryRule));
		certainLossDto.setPrpLcomponentList(this.prpLcomponentService.findPrpLcomponent(queryRule));
		certainLossDto.setPrpLrepairFeeList(this.prpLrepairFeeService.findPrpLrepairFee(queryRule));

//		certainLossDto.setPrpLthirdParty(this.prpLthirdPartyService.findPrpLthirdParty(new PrpLthirdPartyId(registNo, Integer.parseInt(lossItemCode))));
		return certainLossDto;
	}

	/**
	 * 根据条件查询定损主表信息
	 * @param conditions String
	 * @throws Exception
	 * @return Collection
	 */
	public List<PrpLverifyLoss> findByConditions(String conditions) throws Exception {
		QueryRule queryRule = QueryRule.getInstance();
		queryRule.addSql(conditions);
		return prpLverifyLossService.findPrpLverifyLoss(queryRule);
	}

	/**
	 * 保存定损带工作流
	 * @param certainLossDto：自定义定损对象
	 * @throws SQLException
	 * @throws Exception
	 */
	public void save(CertainLossDto certainLossDto, CheckDto checkDto) throws SQLException, Exception {
		this.save(certainLossDto);
		// 调查勘的接口
		// new BLCheckAction().save(dbManager, checkDto);

	}

	/**
	 * 保存定损带工作流
	 * @param certainLossDto：自定义定损对象
	 * @throws SQLException
	 * @throws Exception
	 */
	public void save(CertainLossDto certainLossDto, CheckDto checkDto, WorkFlowDto workFlowDto) throws SQLException, Exception {
		// 创建数据库管理对象
		this.save(certainLossDto);
		if (checkDto != null) {
			this.getCheckService().save(checkDto);
		}
		if (workFlowDto != null) {
			this.getWorkFlowService().deal(workFlowDto);
		}
	}

	/**
	 * 变更定损的操作状态的方法
	 * @param certainLossDto 立案对象
	 * @throws SQLException
	 * @throws Exception
	 * @return 无
	 */
	public void updateClaimStatus(CertainLossDto certainLossDto) throws SQLException, Exception {
		// 示例未完成
		String statement = "";
		PrpLclaimStatus prpLclaimStatus = certainLossDto.getPrpLclaimStatus();
		if (prpLclaimStatus != null) {
			String nodeType = prpLclaimStatus.getId().getNodeType();
			String condition3 = " BusinessNo='" + prpLclaimStatus.getId().getBusinessNo().trim() + "' " + " AND NodeType ='"+nodeType+"' and serialNo=" + prpLclaimStatus.getId().getSerialNo();
			statement = " DELETE FROM prpLclaimStatus Where " + condition3;
			Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
			HibernateUtils.executeSql(session, statement);
			//根据定损类型找到核损类型
			nodeType = CommonUtils.getVerifyNodeType(nodeType);
			String statement2 = " UPDATE prplclaimstatus SET status = '0' WHERE BusinessNo='" + prpLclaimStatus.getId().getBusinessNo().trim() + "' AND NodeType ='"+nodeType+"' AND status='5' AND serialNo=" + prpLclaimStatus.getId().getSerialNo();
			//如果PrpLclaimStatus表中存在该定损的核损的记录，则更新该核损的操作状态为0(未处理)
			HibernateUtils.executeSql(session, statement2);
			prpLclaimStatusService.save(prpLclaimStatus);
		}
	}

	@Override
	public Page findByQueryConditions(String conditions, int pageNo, int pageSize) throws Exception {
		String statement = " Select DISTINCT a.RegistNo,a.PolicyNo, b.HandlerCode, a.DefLossDate, a.LossitemCode, b.OperateDate, b.Status, a.RiskCode, a.lossItemName,a.nodeType " 
				+ " From (select * from PrpLClaimStatus) b Right JOIN PrpLverifyLoss a ON a.RegistNo = b.BusinessNo  left join prplregist c on a.RegistNo = c.RegistNo,prplregistrpolicy d " 
				+ " where a.RegistNo = d.RegistNo and a.nodeType=b.nodeType and a.lossitemcode = b.serialNo and " + conditions
				+ " order by b.OperateDate desc ";
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		Page page = HibernateUtils.findPagebySql(session, statement, pageNo, pageSize);
		List<PrpLverifyLoss> resultList = new ArrayList<PrpLverifyLoss>();
		PrpLverifyLoss prpLverifyLoss = null;
		Object[] object = null;
		List<?> tempListSub = null;
		for (Iterator<Object[]> it = page.getResult().iterator(); it.hasNext(); resultList.add(prpLverifyLoss)) {
			object = (Object[]) it.next();// 每行记录不在是一个对象 而是一个数组
			prpLverifyLoss = new PrpLverifyLoss();
			prpLverifyLoss.getId().setRegistNo((String) object[0]);
			prpLverifyLoss.setPolicyNo((String) object[1]);
			prpLverifyLoss.setHandlerCode((String) object[2]);
			prpLverifyLoss.setDefLossDate(new Date(((Timestamp) object[3]).getTime()));
			prpLverifyLoss.getId().setLossItemCode((String) object[4]);
			prpLverifyLoss.setOperateDate(new Date(((Timestamp) object[5]).getTime()));
			prpLverifyLoss.setStatus((String) object[6]);
			prpLverifyLoss.setRiskCode((String) object[7]);
			prpLverifyLoss.setLossItemName((String) object[8]);
			prpLverifyLoss.getId().setNodeType((String)object[9]);
			// reason:强三查询
			prpLverifyLoss.setRelatepolicyNo(new TreeSet<String>());
			statement = "select PolicyNo from prplregistrpolicy where RegistNo='" + (String) object[0] + "'";
			tempListSub = HibernateUtils.findbySql(session, statement, 0, 0);
			for (Iterator<?> itSub = tempListSub.iterator(); itSub.hasNext();) {
				prpLverifyLoss.getRelatepolicyNo().add((String) itSub.next());
			}
		}
		return new Page(page.getStart(), page.getTotalCount(), page.getPageSize(), resultList);
	}

	/** 定核损信息service */
	private PrpLverifyLossService prpLverifyLossService;
	/** 理赔报案信息补充说明service */
	private PrpLregistExtService prpLregistExtService;
	/** 理赔车辆信息service */
	private PrpLthirdPartyService prpLthirdPartyService;
	/** 理赔节点状态信息service */
	private PrpLclaimStatusService prpLclaimStatusService;
	/** 人员伤情信息service */
	private PrpLpersonWoundService prpLpersonWoundService;
	/** 人员伤亡明细信息service */
	private PrpLpersonService prpLpersonService;
	/** 车辆定损信息service */
	private PrpLcarLossService prpLcarLossService;
	/** 修理费用信息 */
	private PrpLrepairFeeService prpLrepairFeeService;
	/** 换件项目清单信息 */
	private PrpLcomponentService prpLcomponentService;
	/** PRPLVERIFYLOSSITEM */
	private PrpLverifyLossItemService prpLverifyLossItemService;
	/** PRPLVERIFYLOSSEXT */
	private PrpLverifyLossExtService prpLverifyLossExtService;
	/** 财产核定损明细清单 */
	private PrpLpropService prpLpropService;
	/** 查勘事故估损金额 */
	private PrpLcheckLossService prpLcheckLossService;
	/** 财产损失部位信息 */
	private PrpLthirdPropService prpLthirdPropService;
	/** WorkFlowService */
	private WorkFlowService workFlowService;
	private CheckService checkService;

	public PrpLthirdPartyService getPrpLthirdPartyService() {
		return prpLthirdPartyService;
	}

	public void setPrpLthirdPartyService(PrpLthirdPartyService prpLthirdPartyService) {
		this.prpLthirdPartyService = prpLthirdPartyService;
	}

	public PrpLregistExtService getPrpLregistExtService() {
		return prpLregistExtService;
	}

	public void setPrpLregistExtService(PrpLregistExtService prpLregistExtService) {
		this.prpLregistExtService = prpLregistExtService;
	}

	public PrpLclaimStatusService getPrpLclaimStatusService() {
		return prpLclaimStatusService;
	}

	public void setPrpLclaimStatusService(PrpLclaimStatusService prpLclaimStatusService) {
		this.prpLclaimStatusService = prpLclaimStatusService;
	}

	public PrpLpersonWoundService getPrpLpersonWoundService() {
		return prpLpersonWoundService;
	}

	public void setPrpLpersonWoundService(PrpLpersonWoundService prpLpersonWoundService) {
		this.prpLpersonWoundService = prpLpersonWoundService;
	}

	public PrpLpersonService getPrpLpersonService() {
		return prpLpersonService;
	}

	public void setPrpLpersonService(PrpLpersonService prpLpersonService) {
		this.prpLpersonService = prpLpersonService;
	}

	public PrpLcarLossService getPrpLcarLossService() {
		return prpLcarLossService;
	}

	public void setPrpLcarLossService(PrpLcarLossService prpLcarLossService) {
		this.prpLcarLossService = prpLcarLossService;
	}

	public PrpLrepairFeeService getPrpLrepairFeeService() {
		return prpLrepairFeeService;
	}

	public void setPrpLrepairFeeService(PrpLrepairFeeService prpLrepairFeeService) {
		this.prpLrepairFeeService = prpLrepairFeeService;
	}

	public PrpLcomponentService getPrpLcomponentService() {
		return prpLcomponentService;
	}

	public void setPrpLcomponentService(PrpLcomponentService prpLcomponentService) {
		this.prpLcomponentService = prpLcomponentService;
	}

	public PrpLverifyLossItemService getPrpLverifyLossItemService() {
		return prpLverifyLossItemService;
	}

	public void setPrpLverifyLossItemService(PrpLverifyLossItemService prpLverifyLossItemService) {
		this.prpLverifyLossItemService = prpLverifyLossItemService;
	}

	public PrpLverifyLossExtService getPrpLverifyLossExtService() {
		return prpLverifyLossExtService;
	}

	public void setPrpLverifyLossExtService(PrpLverifyLossExtService prpLverifyLossExtService) {
		this.prpLverifyLossExtService = prpLverifyLossExtService;
	}

	public PrpLpropService getPrpLpropService() {
		return prpLpropService;
	}

	public void setPrpLpropService(PrpLpropService prpLpropService) {
		this.prpLpropService = prpLpropService;
	}

	public PrpLcheckLossService getPrpLcheckLossService() {
		return prpLcheckLossService;
	}

	public void setPrpLcheckLossService(PrpLcheckLossService prpLcheckLossService) {
		this.prpLcheckLossService = prpLcheckLossService;
	}

	public PrpLthirdPropService getPrpLthirdPropService() {
		return prpLthirdPropService;
	}

	public void setPrpLthirdPropService(PrpLthirdPropService prpLthirdPropService) {
		this.prpLthirdPropService = prpLthirdPropService;
	}

	public PrpLverifyLossService getPrpLverifyLossService() {
		return prpLverifyLossService;
	}

	public void setPrpLverifyLossService(PrpLverifyLossService prpLverifyLossService) {
		this.prpLverifyLossService = prpLverifyLossService;
	}

	public WorkFlowService getWorkFlowService() {
		return workFlowService;
	}

	public void setWorkFlowService(WorkFlowService workFlowService) {
		this.workFlowService = workFlowService;
	}

	public CheckService getCheckService() {
		return checkService;
	}

	public void setCheckService(CheckService checkService) {
		this.checkService = checkService;
	}
}
