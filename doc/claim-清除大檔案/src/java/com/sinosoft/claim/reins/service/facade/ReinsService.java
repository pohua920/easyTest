package com.sinosoft.claim.reins.service.facade;

import java.util.Collection;

import com.sinosoft.claim.reins.vo.ReinsCaseStatus;
import com.sinosoft.claim.reins.vo.ReinsClaimMain;
import com.sinosoft.claim.reins.vo.ReinsClaimSummary;
import com.sinosoft.claim.reins.vo.ReinsLargeCase;
import com.sinosoft.claim.reins.vo.ReinsRepayCalResult;
import com.sinosoft.sysframework.common.datatype.DateTime;

/**
 * 再保信息接口
 * @author 中科软
 *
 */
public interface ReinsService {


	/**
	 * 根据保单号及出险日期得到出险当时临分总比例
	 * @param dbManager
	 * @param policyNo 保单号
	 * @param DamageDate 出险日期
	 * @return 临分总比例
	 */
//	public double getSumFacShare(String policyNo,DateTime DamageDate) throws Exception;
	
	/**
	 * 根据确定的总估损金额确定当前赔案是否重大赔案，是否需要现金赔款
	 * @param dbManager
	 * @param Collection<ReinsClaimSummary>出险业务估损情况集合（按危险单位分开）
	 * @return Collection<ReinsLargeCase>重大赔案情况
	 */
//	public Collection getLargeCashLoss(ReinsClaimSummary reinsClaimSummary) throws Exception;
	
	/**
	 * 根据理赔业务情况计算再保摊赔信息
	 * @param dbManager
	 * @param Collection<ReinsClaimSummary> 根据危险单位号汇总的出险业务已决未决情况集合
	 * @return Collection<ReinsRepayCalResult>分摊计算结果集合
	 * @throws Exception
	 */
//	public Collection repaySimulate(DBManager dbManager,ReinsClaimSummary reinsClaimSummary) throws Exception;
	
	/**
	 * 再保处理理赔结案/注销/拒赔以及重开赔案业务
	 * @param dbManager
	 * @param ReinsCaseStatus 
	 * @throws Exception
	 */
	public void changeCaseStatus(ReinsCaseStatus reinsCaseStatus) throws Exception;
	
	/**
	 * 根据保单号及出险日期得到出险当时危险单位划分情况
	 * @param dbManager
	 * @param policyNo 保单号
	 * @param DamageDate 出险日期
	 * @return Collection<ReinsDangerUnit>危险单位集合
	 * @throws Exception
	 */
//	public Collection getDangerUnit(DBManager dbManager,String policyNo,DateTime DamageDate) throws Exception;
	
	/**
	 * 理赔送再保数据进行分摊分赔处理
	 * @param dbManager
	 * @param ReinsClaimMainList	理赔集合
	 * @throws Exception
	 */
	public void repayCal(ReinsClaimMain reinsClaimMain) throws Exception;
	
	
	
	
	
	/**
	 * 根据保单号及出险日期得到出险当时临分总比例
	 * @param dbManager
	 * @param policyNo 保单号
	 * @param DamageDate 出险日期
	 * @return 临分总比例
	 */
	public double getSumFacShare(String policyNo,DateTime DamageDate) throws Exception;
	
	/**
	 * 根据确定的总估损金额确定当前赔案是否重大赔案，是否需要现金赔款
	 * @param dbManager
	 * @param Collection<ReinsClaimSummary>出险业务估损情况集合（按危险单位分开）
	 * @return Collection<ReinsLargeCase>重大赔案情况
	 */
	public Collection<ReinsLargeCase> getLargeCashLoss(ReinsClaimSummary reinsClaimSummary) throws Exception;
	
	/**
	 * 根据理赔业务情况计算再保摊赔信息
	 * @param dbManager
	 * @param Collection<ReinsClaimSummary> 根据危险单位号汇总的出险业务已决未决情况集合
	 * @return Collection<ReinsRepayCalResult>分摊计算结果集合
	 * @throws Exception
	 */
	public Collection<ReinsRepayCalResult> repaySimulate(ReinsClaimSummary reinsClaimSummary) throws Exception;
	
	
	/**
	 * 根据保单号及出险日期得到出险当时危险单位划分情况
	 * @param dbManager
	 * @param policyNo 保单号
	 * @param DamageDate 出险日期
	 * @return Collection<ReinsDangerUnit>危险单位集合
	 * @throws Exception
	 */
	public Collection<?> getDangerUnit(String policyNo,DateTime DamageDate) throws Exception;
	



}
