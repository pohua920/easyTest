package com.sinosoft.claim.common.service.facade;

import java.sql.SQLException;
import java.util.List;

import com.sinosoft.claim.common.vo.PolicyCopyDto;
import com.sinosoft.claim.schema.model.PrpCopyItemKind;
import com.sinosoft.sysframework.exceptionlog.UserException;

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
public interface PolicyCopyService {

	/**
	 * 保单保存方法
	 *@param policyCopyDto 保单对象
	 * @throws SQLException
	 * @throws Exception
	 *@return 无
	 */
	public void save(PolicyCopyDto policyCopyDto) throws SQLException, Exception;

	/**
	 * 保单删除
	 * @param fcoPolicyNoticeNo
	 * @throws SQLException
	 * @throws Exception
	 */
	public void delete(String endorseNo) throws SQLException, Exception;

	/**
	 * 保单查询方法
	 * @param policyDto 保单对象
	 * @throws SQLException
	 * @throws Exception
	 * @return 无
	 */
	public PolicyCopyDto findByPrimaryKey(String endorseNo) throws SQLException, Exception;

	/**
	 * 有效保单查询方法
	 * @param policyDto 保单对象
	 * @throws SQLException
	 * @throws Exception
	 * @return 无
	 */
	public PolicyCopyDto findByPrimaryKey(String endorseNo, String strDamageDate) throws SQLException, Exception;

	/**
	 * 获取出险时保单信息对应copy表中批单号
	 * @param strPolicyNo 保单号码
	 * @param strDamageDate 出险日期
	 * @param strDamageHour 出险小时
	 * @return 出险时保单信息对应copy表中批单号
	 * @throws UserException
	 */
	public String getBackWardEndorseNo(String strPolicyNo, String strDamageDate, String strDamageHour) throws Exception;
	/**
	 * 保单查询方法
	 * @param policyCopyDto 保单对象
	 * @throws SQLException
	 * @throws Exception
	 * @return 无
	 */
	public List<PrpCopyItemKind> findPrpCopyItemKind(String familyNo,String endorseNo) throws SQLException, Exception;

}
