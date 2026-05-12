package com.sinosoft.claim.schema.service.facade;
/**
 * 呼叫中心接口
 * @author 中科软
 */
import java.sql.SQLException;
import java.util.List;

import ins.framework.common.Page;
import ins.framework.common.QueryRule;

import com.sinosoft.claim.schema.model.PrpLcallCenter;
import com.sinosoft.claim.schema.model.PrpLcallCenterId;

public interface PrpLcallCenterService {
	
	/**
	 * 呼叫中心信息
	 * @param PrpLcallCenter ：传入的呼叫中心
	 */
	public void save(PrpLcallCenter prpLcallCenter) throws Exception;
	
	/**
	 * 保存呼叫中心信息
	 * @param list  :传入的呼叫中心信息集合
	 * @throws Exceptionuan
	 */
	public void save(List<PrpLcallCenter> list) throws Exception;
	
	/**
	 * 删除呼叫中心信息
	 * @param policyNo ：传入的呼叫中心编号
	 */
	public void delete(PrpLcallCenterId prpLcallCenterId) throws Exception;

	/**
	 * 更新呼叫中心信息
	 * @param PrpLcallCenter :传入需要更新的呼叫中心
	 */
	public void update(PrpLcallCenter prpLcallCenter) throws Exception;

	/**
	 * 根据呼叫中心编号查询出呼叫中心信息
	 * @param policyNo ：传入的呼叫中心编号
	 * @return 返回呼叫中心
	 */
	public PrpLcallCenter findPrpLcallCenter(PrpLcallCenterId prpLcallCenterId) throws Exception;
	
	/**
	 * 根据查询对象获取Page对象的列表
	 * @param queryRule 查询对象
	 * @param pageNo 页面编号
	 * @param pageSize 页面大小
	 * @return 包含的呼叫中心页面信息
	 */
	public Page findPrpLcallCenter(QueryRule queryRule, int pageNo, int pageSize) throws Exception;
	/**
	 * 根据查询对象获取 呼叫中心 的列表
	 * @param queryRule 查询对象
	 * @return 包含的呼叫中心  的列表
	 */
	public List<PrpLcallCenter> findPrpLcallCenter(QueryRule queryRule) throws Exception;
	/**
	 * 更新呼叫中心信息
	 * @param PrpLcallCenter :传入需要更新的呼叫中心
	 */
	public void saveOrUpdate(PrpLcallCenter prpLcallCenter) throws Exception;
	/**
	 * 更新呼叫中心信息列表
	 * @param PrpLcallCenter :传入需要更新的呼叫中心列表
	 */
	public void saveOrUpdate(List<PrpLcallCenter> list) throws Exception;
	/**
	 * 根据查询业务号获取最大的序号
	 * @param registNo 业务号
	 * @return 最大的序号
	 */
	public int getMaxSerialNo(String registNo) throws SQLException, Exception;
}
