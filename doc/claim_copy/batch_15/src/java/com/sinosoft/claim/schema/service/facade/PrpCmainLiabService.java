package com.sinosoft.claim.schema.service.facade;

/**
 * 责任险保单信息接口
 * @author 中科软
 */
import ins.framework.common.Page;
import ins.framework.common.QueryRule;

import java.util.List;

import com.sinosoft.claim.schema.model.PrpCmainLiab;
import com.sinosoft.sysframework.common.datatype.DateTime;

public interface PrpCmainLiabService {

	/**
	 * 保存责任险保单信息
	 * @param prpLcheck ：传入的责任险保单信息
	 */
	public void save(PrpCmainLiab prpCmainLiab) throws Exception;

	/**
	 * 责任险保单信息
	 * @param list :传入的责任险保单信息集合
	 * @throws Exceptionuan
	 */
	public void save(List<PrpCmainLiab> list) throws Exception;

	/**
	 * 删除责任险保单信息
	 * @param prpCmainLiabId ：传入的责任险保单信息编号
	 */
	public void delete(String proposalNo) throws Exception;

	/**
	 * 更新责任险保单信息
	 * @param prpCmainLiab :传入需要更新的责任险保单信息
	 */
	public void update(PrpCmainLiab prpCmainLiab) throws Exception;

	/**
	 * 根据责任险保单信息编号查询出责任险保单信息
	 * @param prpCmainLiabId ：传入的责任险保单信息编号
	 * @return 返回责任险保单信息
	 */
	public PrpCmainLiab findPrpCmainLiab(String proposalNo) throws Exception;

	/**
	 * 根据查询对象获取Page对象的列表
	 * @param queryRule 查询对象
	 * @param pageNo 页面编号
	 * @param pageSize 页面大小
	 * @return 包含的责任险保单信息页面信息
	 */
	public Page findPrpCmainLiab(QueryRule queryRule, int pageNo, int pageSize) throws Exception;
	/**
	 * 根据查询对象获取责任险保单信息的列表
	 * @param queryRule 查询对象
	 * @return 包含的 责任险保单信息 的列表
	 */
	public List<PrpCmainLiab> findPrpCmainLiab(QueryRule queryRule) throws Exception;
	/**
	 * 根据保单号获取责任险保单的起始日期
	 * @param queryRule 查询对象
	 * @return 责任险保单的起始日期
	 */
	public DateTime findByPrimaryKeyStartDate(String policyNo) throws Exception;
}
