package com.sinosoft.claim.check.service.spring;

import ins.framework.common.QueryRule;
import ins.framework.dao.GenericDaoHibernate;

import java.sql.SQLException;
import java.util.List;

import com.sinosoft.claim.check.service.facade.AcciCheckService;
import com.sinosoft.claim.check.vo.AcciCheckDto;
import com.sinosoft.claim.dto.custom.CaseRelateNodeDto;
import com.sinosoft.claim.dto.custom.WorkFlowDto;
import com.sinosoft.claim.regist.service.facade.RegistService;
import com.sinosoft.claim.regist.vo.RegistDto;
import com.sinosoft.claim.schema.model.PrpLacciCheck;
import com.sinosoft.claim.schema.service.facade.PrpLacciCheckChargeService;
import com.sinosoft.claim.schema.service.facade.PrpLacciCheckService;
import com.sinosoft.claim.schema.service.facade.PrpLacciCheckTextService;
import com.sinosoft.claim.schema.service.facade.PrpLregistService;
import com.sinosoft.sysframework.exceptionlog.UserException;

/**
 * 报案对象Regist
 * <p>
 * Title: 车险理赔样本报案action
 * </p>
 * <p>
 * Description: 车险理赔样本报案action
 * </p>
 * <p>
 * Copyright: Copyright (c) 2013
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * @author 中科软
 * @version 1.0
 */
public class AcciCheckServiceSpringImpl extends GenericDaoHibernate<AcciCheckDto, String> implements AcciCheckService {
	RegistService registService;
	PrpLacciCheckService prpLacciCheckService;
	PrpLacciCheckTextService prpLacciCheckTextService;
	PrpLacciCheckChargeService prpLacciCheckChargeService;
	PrpLregistService prpLregistService;

	/**
	 * 保存报案
	 * @param RegistDto：报案对象DTO
	 * @throws Exception
	 */
	@Override
	public void save(RegistDto registDto) throws SQLException, Exception {
		// registService.save(registDto,null);
	}

	/**
	 * 保存报案带工作流
	 * @param RegistDto：报案对象DTO
	 * @throws Exception
	 */
	@Override
	public void save(RegistDto registDto, WorkFlowDto workFlowDto) throws SQLException, Exception {
		// RegistSaveCommand registSaveCommand = new
		// RegistSaveCommand(registDto,workFlowDto);
		// registSaveCommand.execute();

		// registService.save(registDto,workFlowDto);
	}

	/**
	 * 删除报案
	 * @param registNo：报案号
	 * @throws Exception
	 */
	public void delete(String registNo) throws SQLException, Exception {
		// RegistDeleteCommand registDeleteCommand = new
		// RegistDeleteCommand(registNo);
		// registDeleteCommand.execute();

		// registService.delete(registNo);
	}

	/**
	 * 获得报案信息
	 * @param registNo：报案号
	 * @return 意键险调查对象
	 * @throws Exception
	 */
	@Override
	public AcciCheckDto findByPrimaryKey(String registNo) throws SQLException, UserException, Exception {
		AcciCheckDto acciCheckDto = new AcciCheckDto();
		// 根据调查号查询调查信息 2005-08-16
		acciCheckDto.setPrpLacciCheck(prpLacciCheckService.findPrpLacciCheck(registNo));
		QueryRule queryRule = QueryRule.getInstance();
		queryRule.addEqual("id.checkNo", registNo);
		queryRule.addEqual("id.textType", "3");
		acciCheckDto.setPrpLacciCheckTextList(prpLacciCheckTextService.findPrpLacciCheckText(queryRule));
		queryRule = QueryRule.getInstance();
		queryRule.addEqual("id.checkNo", registNo);
		acciCheckDto.setPrpLacciCheckChargeList(prpLacciCheckChargeService.findPrpLacciCheckCharge(queryRule));

		// 需要判断acciCheckDto.getPrpLacciCheckDto()不为空
		if (acciCheckDto.getPrpLacciCheck() != null) {
			acciCheckDto.setPrpLregist(prpLregistService.findPrpLregist(acciCheckDto.getPrpLacciCheck().getRegistNo()));
		}
		return acciCheckDto;
	}

	/**
	 * 判断报案号是否存在
	 * @param registNo:报案号
	 * @return 是/否
	 * @throws Exception
	 */
	@Override
	public boolean isExist(String registNo) throws SQLException, Exception {
		return prpLregistService.isExist(registNo);
	}

	// /**
	// * 获得报案信息
	// * @param conditions：查询条件
	// * @return 报案对象
	// * @throws Exception
	// */
	// @Override
	// public Collection findByConditions(String conditions) throws
	// SQLException,Exception
	// {
	// // PrpLregistFindByConCommand prpLregistFindByConCommand = new
	// PrpLregistFindByConCommand(conditions);
	// // return (Collection)prpLregistFindByConCommand.execute();
	//		
	// return registService.findByConditions(conditions);
	// }
	//	
	// /**
	// * 获得报案查询信息
	// * @param conditions：查询条件
	// * @return 报案对象
	// * @throws Exception
	// * Add By sunhao 2004-08-24 Reason:增加新的查询条件
	// */
	// @Override
	// public Collection findByQueryConditions(String conditions) throws
	// SQLException,Exception
	// {
	//		
	// return registService.findByQueryConditions(conditions);
	// }
	// @Override
	// public Collection findSamePolicyRegist(String policyNo) throws Exception
	// {
	// String conditions = " prplregist.policyNo ='" + policyNo + "' order by
	// registNo";
	// // SamePolicyRegistFindByConCommand SamePolicyRegistFindByConCommand =
	// new
	// // SamePolicyRegistFindByConCommand(conditions);
	// // return (Collection) SamePolicyRegistFindByConCommand.execute();
	//		
	// return registService.findSamePolicyRegist(conditions);
	// }
	// /**
	// * 获得报案信息
	// * @param conditions：查询条件
	// * @return 报案对象
	// * @throws Exception
	// */
	// @Override
	// public Collection getWorkFlowList(String conditions) throws
	// SQLException,Exception
	// {
	// // PrpLregistFindforWorkFlowCommand prpLregistFindforWorkFlowCommand =
	// new PrpLregistFindforWorkFlowCommand(conditions);
	// // return (Collection)prpLregistFindforWorkFlowCommand.execute();
	//		 
	// return registService.getWorkFlowList(conditions);
	// }
	// /**
	// * 获得相关的节点信息
	// * @param registNo：报案号
	// * @return 报案对象
	// * @throws Exception
	// */
	// @Override
	// public CaseRelateNodeDto relateNode(String registNo) throws
	// SQLException,UserException,Exception
	// {
	// // RelateNodeFindByConCommand relateNodeFindByConCommand = new
	// RelateNodeFindByConCommand(registNo);
	// // CaseRelateNodeDto caseRelateNodeDto =
	// (CaseRelateNodeDto)relateNodeFindByConCommand.execute();
	// // return caseRelateNodeDto;
	//		
	// return registService.relateNode(registNo);
	// }
	//	
	/**
	 * 获得一个案件的所有调查信息(意健险独有的方法)
	 * @param conditions 查询条件
	 * @return 调查表对象集合
	 * @throws Exception
	 */
	@Override
	public List<PrpLacciCheck> findByConditionsAcciCheck(String conditions) throws SQLException, Exception {
		QueryRule queryRule = QueryRule.getInstance();
		queryRule.addSql(conditions);
		return this.prpLacciCheckService.findPrpLacciCheck(queryRule);
	}

	@Override
	public List<?> findByConditions(String conditions) throws SQLException, Exception {
		return null;
	}

	@Override
	public List<?> findByQueryConditions(String conditions) throws SQLException, Exception {
		return null;
	}

	@Override
	public List<?> findSamePolicyRegist(String policyNo) throws Exception {
		return null;
	}

	@Override
	public List<?> getWorkFlowList(String conditions) throws SQLException, Exception {
		return null;
	}

	@Override
	public CaseRelateNodeDto relateNode(String registNo) throws SQLException, UserException, Exception {
		return null;
	}

	public RegistService getRegistService() {
		return registService;
	}

	public void setRegistService(RegistService registService) {
		this.registService = registService;
	}

	public PrpLacciCheckService getPrpLacciCheckService() {
		return prpLacciCheckService;
	}

	public void setPrpLacciCheckService(PrpLacciCheckService prpLacciCheckService) {
		this.prpLacciCheckService = prpLacciCheckService;
	}

	public PrpLacciCheckTextService getPrpLacciCheckTextService() {
		return prpLacciCheckTextService;
	}

	public void setPrpLacciCheckTextService(PrpLacciCheckTextService prpLacciCheckTextService) {
		this.prpLacciCheckTextService = prpLacciCheckTextService;
	}

	public PrpLacciCheckChargeService getPrpLacciCheckChargeService() {
		return prpLacciCheckChargeService;
	}

	public void setPrpLacciCheckChargeService(PrpLacciCheckChargeService prpLacciCheckChargeService) {
		this.prpLacciCheckChargeService = prpLacciCheckChargeService;
	}

	public PrpLregistService getPrpLregistService() {
		return prpLregistService;
	}

	public void setPrpLregistService(PrpLregistService prpLregistService) {
		this.prpLregistService = prpLregistService;
	}

}
