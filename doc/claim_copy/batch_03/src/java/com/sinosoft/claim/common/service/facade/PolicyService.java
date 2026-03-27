package com.sinosoft.claim.common.service.facade;

import ins.framework.common.Page;

import java.sql.SQLException;

import com.sinosoft.claim.common.vo.PolicyDto;
import com.sinosoft.claim.schema.model.PrpCmain;

/**
 * UI保单逻辑
 * <p>
 * Title: 车险理赔样本程序 保单action
 * </p>
 * <p>
 * Description: 车险理赔样本程序 保单action
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
public interface PolicyService {

	/**
	 * 保单保存方法
	 *@param policyDto 保单对象
	 * @throws SQLException
	 * @throws Exception
	 *@return 无
	 */
	public void save(PolicyDto policyDto) throws SQLException, Exception;

	/**
	 * 保单删除
	 * @param fcoPolicyNoticeNo
	 * @throws SQLException
	 * @throws Exception
	 */
	public void delete(String policyNo) throws SQLException, Exception;

	/**
	 * 保单查询方法
	 * @param policyDto 保单对象
	 * @throws SQLException
	 * @throws Exception
	 * @return 无
	 */
	public PolicyDto findByPrimaryKey(String policyNo) throws SQLException, Exception;

	/**
	 * 有效保单查询方法
	 * @param policyDto 保单对象
	 * @throws SQLException
	 * @throws Exception
	 * @return 无
	 */
	public PolicyDto findByPrimaryKey(String policyNo, String strDamageDate) throws SQLException, Exception;

	/**
	 * 根据保单号获得保单主信息
	 * @param policyNo 保单号码
	 * @return 返回保单对象
	 * @throws SQLException
	 * @throws Exception
	 */
	public PrpCmain findPrpCmainDtoByPrimaryKey(String policyNo) throws SQLException, Exception;

	/**
	 * 根据条件查询prpLacciPerson对象的序号
	 * @param condition 查询条件
	 * @return prpLacciPerson表的序号
	 * @throws SQLException
	 * @throws Exception
	 */
	public int findBySeriaNo(String condition) throws SQLException, Exception;

	/**
	 * 变更立案的操作状态的方法
	 *@param claimDto 立案对象
	 *@throws SQLException
	 *@throws Exception
	 *@return 无
	 */
	public void updateClaimStatus(PolicyDto policyDto) throws SQLException, Exception;

	/**
	 * 判断保单通知号是否存在
	 * @param policyNo 保单号码
	 * @return 是/否
	 * @throws SQLException
	 * @throws Exception
	 */
	public boolean isExist(String policyNo) throws SQLException, Exception;

	/**
	 * 判断保单是否注销或退保
	 * @param wsRegistDto 报案Dto对象
	 * @return 19 注销保单;21 全单退保;空 正常保单
	 * @throws Exception
	 */
	public String isWithdraw(String PolicyNo, String DamageStartDate, String DamageStartHour) throws SQLException, Exception;

	/**
	 * 获得未缴费的期数
	 * @param conditions 查询条件
	 * @throws Exception
	 * @return Collection
	 */
	public int[] getDelinquentfeeTime(String conditions) throws Exception;

	/**
	 * 检查缴费情况
	 * @param conditions 查询条件
	 * @throws Exception
	 * @return Collection
	 */
	public int checkPay(String conditions) throws Exception;

	/**
	 * 按条件查询多条数据
	 * @param conditions 查询条件
	 * @param pageNo 开始页数
	 * @param rowsPerPage 每页显示条数
	 * @throws Exception
	 * @return Collection
	 */
	public Page findForRegistConditions(String conditions, int pageNo, int rowsPerPage) throws Exception;

}
