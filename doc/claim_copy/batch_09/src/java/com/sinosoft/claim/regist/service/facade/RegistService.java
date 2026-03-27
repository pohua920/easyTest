package com.sinosoft.claim.regist.service.facade;

import ins.framework.common.Page;

import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpSession;//mantis：CLM0259、CLM9001，處理人員：DP0713，需求單編號：新核心-多元理賠收件平台建置案
//mantis：CLM0282、CLM9009 ，處理人員： DP0713 ，需求單編號：多元理賠備案_非車全險種
import com.sinosoft.app.webservice.server.schema.model.regist.vo.ClaimExternalRiskSourceVo;
import com.sinosoft.app.webservice.server.schema.model.regist.vo.ClaimExternalSourceVo;//mantis：CLM0259、CLM9001，處理人員：DP0713，需求單編號：新核心-多元理賠收件平台建置案
import com.sinosoft.claim.common.vo.CaseRelateNodeDto;
import com.sinosoft.claim.compensate.vo.CompensateFeeDto;
import com.sinosoft.claim.dto.custom.UserDto;
import com.sinosoft.claim.regist.vo.RegistClaimInfoDto;
import com.sinosoft.claim.regist.vo.RegistDto;
import com.sinosoft.claim.schema.model.PrpCmain;//mantis：CLM0259、CLM9001，處理人員：DP0713，需求單編號：新核心-多元理賠收件平台建置案
import com.sinosoft.claim.schema.model.PrpLcallCenter;
import com.sinosoft.claim.schema.model.PrpLregist;
import com.sinosoft.claim.schema.model.PrpLregistExt;
import com.sinosoft.claim.workflow.vo.WorkFlowDto;

/**
 * 报案逻辑分发
 * <p>
 * Title: 车险理赔报案
 * </p>
 * <p>
 * Description: 车险理赔报案facade
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
public interface RegistService {
	/**
	 * 保存报案
	 * @param registDto：自定义报案对象
	 * @throws SQLException
	 * @throws Exception
	 */
	public void save(RegistDto registDto) throws SQLException, Exception;

	/**
	 * 获得报案
	 * @param registNo
	 * @param dbManager 数据连接
	 * @return 自定义报案对象
	 * @throws SQLException
	 * @throws Exception
	 */
	public RegistDto findByPrimaryKey(String registNo) throws SQLException, Exception;

	/**
	 * 获得相关的节点信息
	 * @param registNo
	 * @param dbManager 数据连接
	 * @return 自定义报案对象
	 * @throws SQLException
	 * @throws Exception
	 */
	public CaseRelateNodeDto relateNode(String registNo) throws SQLException, Exception;

	/**
	 * 保存报案注销信息带工作流处理的过程
	 * @param registDto：自定义报案对象
	 * @param workFlowDto：工作流对象
	 * @throws SQLException
	 * @throws Exception
	 */
	public List<RegistClaimInfoDto> findByPolicyNo(String policyNo) throws Exception;

	/**
	 * 报案的查询
	 * @param conditions
	 * @param pageNo
	 * @param rowsPerPage
	 * @return
	 * @throws Exception
	 */
	public Page findByQueryConditions(String conditions, int pageNo, int rowsPerPage) throws Exception;

	/**
	 * 报案的查询
	 * @param conditions
	 * @param pageNo
	 * @param rowsPerPage
	 * @return
	 * @throws Exception
	 */
	public Page findByQueryConditions(String conditions, String strPageNo, String rowsPerPage) throws Exception;

	/**
	 * 报案的查询
	 * @param conditions
	 * @param pageNo
	 * @param rowsPerPage
	 * @return
	 * @throws Exception
	 */
	public Page findByQueryConditions(String conditions) throws Exception;

	/**
	 * 查询工作流信息
	 * @param conditions
	 * @param pageNo
	 * @param rowsPerPage
	 * @return
	 * @throws Exception
	 */
	public List<?> getWorkFlowList(String conditions, int pageNo, int rowsPerPage) throws Exception;

	/**
	 * 查询工作流信息
	 * @param conditions
	 * @param pageNo
	 * @param rowsPerPage
	 * @return
	 * @throws Exception
	 */
	public List<?> getWorkFlowList(String conditions) throws Exception;

	/**
	 * 保存报案信息不带工作流
	 * @param registDto
	 * @throws Exception
	 */
	public void save(RegistDto registDto, WorkFlowDto workFlowDto) throws Exception;

	/**
	 * mantis：CLM0259、CLM9001，處理人員：DP0713，需求單編號：新核心-多元理賠收件平台建置案
	 * 保存报案信息不带工作流
	 * @param registDto
	 * @throws Exception
	 */
	public void save4Ws(RegistDto registDto, WorkFlowDto workFlowDto,	HttpSession session) throws Exception;
	/**
	 * mantis：CLM0259、CLM9001，處理人員：DP0713，需求單編號：新核心-多元理賠收件平台建置案
	 * 保存报案信息不带工作流
	 * @param registDto
	 * @throws Exception
	 */
	public void sendMail(String[] sendTo,String registNo,ClaimExternalSourceVo claimExternalSourceVo) throws Exception;
	/**
	 * mantis：CLM0282、CLM9009 ，處理人員： DP0713 ，需求單編號：多元理賠備案_非車全險種
	 * 保存报案信息不带工作流
	 * @param registDto
	 * @throws Exception
	 */
	public void sendMail4Risk(String[] sendTo,String registNo,ClaimExternalRiskSourceVo claimExternalSourceVo) throws Exception;
	/**
	 * 插入一条数据
	 * @param prpLcallCenterDto prpLcallCenterDto
	 * @throws Exception
	 */
	public void saveCallCenter(RegistDto registDto, List<PrpLregistExt> prpLregistExtList, PrpLcallCenter prpLcallCenter) throws Exception;

	/***************************************************************************
	 * 根据主键获取PrpLRegist对象
	 * @param registNo
	 * @return
	 * @throws SQLException
	 * @throws Exception
	 */
	public PrpLregist findByPrimaryKeyForPrpLRegist(String registNo) throws SQLException, Exception;

	/***************************************************************************
	 * 更新报案主对象PrpLregist
	 * @param prpLregist
	 * @throws SQLException
	 * @throws Exception
	 */
	public void updatePrpLRegist(PrpLregist prpLregist) throws SQLException, Exception;

	/**
	 * 保存报案注销信息带工作流处理的过程
	 * @param registDto：自定义报案对象
	 * @param workFlowDto：工作流对象
	 * @throws SQLException
	 * @throws Exception
	 */
	public void saveRegistCancel(RegistDto registDto, WorkFlowDto workFlowDto) throws SQLException, Exception;

	/**
	 * 根据条件查询报案信息
	 * @Description:
	 * @author 中科软
	 * @date Mar 20, 2013 3:20:46 PM
	 * @param policyNo
	 */
	public List<PrpLregist> findSamePolicyRegist(String policyNo) throws Exception;

	/**
	 * 获得报案信息列表
	 * @param policyno 保单号
	 * @return 报案对象集合
	 * @throws Exception
	 */

	public List<PrpLregist> findRegistsByPolicyno(String policyno) throws SQLException, Exception;

	/**
	 * 根据报案号获得该报案的已决未决金额
	 * @param registNo
	 * @return
	 * @throws Exception
	 */
	public CompensateFeeDto getCompensateFeeByRegistNo(String registNo) throws Exception;

	/**
	 * 保存报案信息带工作流的处理过程
	 * @param registDto
	 * @param workFlowDto
	 * @throws Exception
	 */
	public void saveBpm(RegistDto registDto, WorkFlowDto workFlowDto) throws Exception;

	/**
	 * 保存报案注销信息带工作流处理的过程
	 * @param registDto：自定义报案对象
	 * @param workFlowDto：工作流对象
	 * @throws SQLException
	 * @throws Exception
	 */
	public void saveBpmRegistCancel(RegistDto registDto, WorkFlowDto workFlowDto, String nodeType) throws SQLException, Exception;

	/**
	 * 创建jbpm的工作流 意见险流程
	 * @param registDto
	 * @param workFlowDto
	 * @throws Exception
	 */
	public void saveBpm_E(RegistDto registDto, WorkFlowDto workFlowDto) throws Exception;

	/**
	 * 创建jbpm的工作流 财产险流程
	 * @param registDto
	 * @param workFlowDto
	 * @throws Exception
	 */
	public void saveBpm_Q(RegistDto registDto, WorkFlowDto workFlowDto) throws Exception;

	/***
	 * 菜单：备案查询，查询函数入口
	 * @param condition
	 * @param pageNo
	 * @param rowsPerPage
	 * @return
	 */
	public Page findRegistByConditions(String condition, int pageNo, int rowsPerPage);

	public void updateDamageDate(String registNo, String damageDate, UserDto user) throws Exception;
}
