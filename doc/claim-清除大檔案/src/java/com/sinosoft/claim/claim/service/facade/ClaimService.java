package com.sinosoft.claim.claim.service.facade;

import ins.framework.common.Page;

import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sinosoft.claim.claim.vo.ClaimDto;
import com.sinosoft.claim.regist.vo.RegistClaimInfoDto;
import com.sinosoft.claim.schema.model.PrpCmain;
import com.sinosoft.claim.schema.model.PrpLclaim;
import com.sinosoft.claim.workflow.vo.WorkFlowDto;
import com.sinosoft.sysframework.exceptionlog.UserException;

public interface ClaimService {
	/**
	 * 立案保存方法
	 *@param claimDto 立案对象
	 * @throws SQLException
	 * @throws Exception
	 *@return 无
	 */
	public void save(ClaimDto claimDto) throws SQLException, Exception;

	/**
	 * 立案注销拒赔保存方法
	 *@param claimDto 立案对象
	 * @throws SQLException
	 * @throws Exception
	 *@return 无
	 */
	public void saveCancel(ClaimDto claimDto) throws SQLException, Exception;

	/**
	 * 变更立案的操作状态的方法
	 *@param claimDto 立案对象
	 * @throws SQLException
	 * @throws Exception
	 *@return 无
	 */
	public void updateClaimStatus(ClaimDto claimDto) throws SQLException, Exception;

	/**
	 * 立案删除
	 * @param claimNo 立案号码
	 * @throws SQLException
	 * @throws Exception
	 */
	public void delete(String claimNo) throws SQLException, Exception;


	/**
	 * 根据立案号查询
	 * @param claimNo 立案号码
	 * @return 立案对象
	 * @throws SQLException
	 * @throws Exception
	 */
	public ClaimDto findByPrimaryKey(String claimNo) throws SQLException, Exception;

	/**
	 * 立案删除子表信息
	 * @param fcoClaimNoticeNo
	 * @throws SQLException
	 * @throws Exception
	 */
	public void UpdCaseType(String claimNo) throws Exception;
	

	public void UpdSumClaim(ClaimDto claimDto, String claimNo, double sumClaim) throws Exception;

	/**
	 * 添加一个方法同时获得立案信息和报案信息
	 * @param policyNo 保单号码
	 * @return
	 * @throws Exception
	 */
	public List<RegistClaimInfoDto> findByPolicyNo(String policyNo) throws Exception;

	/**
	 * 修改结束日期
	 * @param claimNo 立案号码
	 * @throws SQLException
	 * @throws Exception
	 */
	public void updateEndCaseDate(String claimNo) throws SQLException, Exception;

	/**
	 * 保存立案带工作流
	 * @param ClaimDto：立案对象
	 * @throws Exception
	 */
	public void save(ClaimDto claimDto, WorkFlowDto workFlowDto) throws SQLException, Exception;

	/***
	 * 立案查询
	 * @param conditions 查询条件
	 * @param pageNo 起始业
	 * @param rowsPerPage 每页显示条数
	 * @return
	 * @throws Exception
	 */
	public Page findByQueryConditions(String conditions, int pageNo, int rowsPerPage) throws Exception;

	/**
	 * 修改出险次数
	 * @param policyNo 保单号码
	 * @param length 次数
	 * @throws Exception
	 */
	public void updateClaimTimes(String policyNo, int length) throws Exception;

	/**
	 * 查询满足模糊查询条件的记录数
	 * @param conditions 查询条件
	 * @return 满足模糊查询条件的记录数
	 * @throws Exception
	 */
	public int getCount(String conditions) throws Exception;

	/**
	 * 查询调整估损金额信息
	 * @param request
	 * @param response
	 * @throws SQLException
	 * @throws Exception
	 */
	public Page findClaimInforByCondition(HttpServletRequest request, HttpServletResponse response) throws SQLException, Exception;

	/**
	 * 查询立案估损详细信息
	 * @param request
	 * @param response
	 * @throws SQLException
	 * @throws Exception
	 */
	public void findDetailByClaimNo(HttpServletRequest request, HttpServletResponse response) throws UserException, Exception;

	/**
	 * 保存估损金额信息
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void saveClaimLoss(HttpServletRequest request, HttpServletResponse response) throws Exception;

	/**
	 * 根据保单号取得保单信息
	 * @param policyNo 保单号
	 * @throws SQLException, Exception
	 * @return 返回一个保单信息
	 */
	public PrpCmain findByPolicyNoKey(String policyNo) throws SQLException, Exception;
	/**
	 * 判断赔案号是否存在
	 * @param claimNo 赔案号
	 * @return 是/否
	 * @throws Exception
	 */
	public boolean isExist(String claimNo) throws SQLException,Exception;
	/**
	 * 保存立案带工作流
	 * @param ClaimDto 立案对象
	 * @throws Exception
	 */
	public void saveBpm(ClaimDto claimDto, WorkFlowDto workFlowDto,String nodeType) throws SQLException, Exception;
	/**
	 * 保存注销拒赔的信息带工作流
	 * @param ClaimDto 立案对象
	 * @throws Exception
	 */
	public void saveCancelBpm(String businessNo,String nodeType,ClaimDto claimDto, WorkFlowDto workFlowDto,String swfLogFlowID,String swfLogLogNo) throws SQLException, Exception;
	/**
	 * 注销拒赔的申请
	 * 注销拒赔的信息带工作流
	 * @param ClaimDto：立案对象DTO
	 * @throws Exception
	 */
	public void saveRequestCancelBpm(String businessNo,ClaimDto claimDto, WorkFlowDto workFlowDto) throws SQLException, Exception;
	
	/**
	 * 1，判断是否是关联单的注销拒赔，2，如果是关联单的注销拒赔，判断是否是最后一个立案的注销
	 * @param claimNo 立案号码
	 * @return 返回节点号码
	 * @throws Exception
	 */
	public String findJbpmNodeType(String claimNo)throws Exception;
	/**
	 * 1，判断是否是关联单的立案，
	 * 2，判断这个报案号是否应经立案
	 * @param registNo
	 * @return
	 * @throws Exception
	 */
	public String findClaimJbpmNodeType(String registNo)throws Exception;

	/**
	 * 生成货运险相关信息
	 * @param policyNo
	 * @param endorseNo
	 * @return 立案对象
	 * @throws SQLException
	 * @throws Exception
	 */
	public PrpLclaim generateCargoInfo(String policyNo, String endorseNo) throws SQLException, Exception;
	/**
	 * 修改swfLog表数据，触发介接送数
	 * @param claimNo 立案号码
	 * @throws SQLException, Exception
	 * @return 
	 */
	public void updateSwflog(String claimNo) throws Exception;
	// mantis： CLM0197，處理人員：CD078，需求單編號：CLM0197 新核心-新增立案修改出險日期及出險地區功能Start
	public Page findBySpecialEditConditions(String conditions, int pageNo,int recordPerPage) throws Exception;
	
	public void updateSpecialEditCase(ClaimDto claimDto) throws SQLException, Exception;
	// mantis： CLM0197，處理人員：CD078，需求單編號：CLM0197 新核心-新增立案修改出險日期及出險地區功能End

}
