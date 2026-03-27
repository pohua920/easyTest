package com.sinosoft.claim.schema.service.facade;
/**
 * 保险地址接口
 * @author 中科软
 */
import ins.framework.common.Page;
import ins.framework.common.QueryRule;

import java.util.List;

import com.sinosoft.claim.schema.model.PrpCaddress;
import com.sinosoft.claim.schema.model.PrpCaddressId;
import com.sinosoft.claim.schema.model.PrpCitemKind;

public interface PrpCaddressService {
	
	/**
	 * 保存保险地址信息
	 * @param prpCaddress ：传入的保险地址
	 */
	public void save(PrpCaddress prpCaddress) throws Exception;
	
	/**
	 * 保险地址信息
	 * @param list  :传入的保险地址信息集合
	 * @throws Exceptionuan
	 */
	public void save(List<PrpCaddress> list) throws Exception;
	
	/**
	 * 删除保险地址信息
	 * @param prpCaddressId ：传入的保险地址编号
	 */
	public void delete(PrpCaddressId prpCaddressId) throws Exception;

	/**
	 * 更新保险地址信息
	 * @param prpCaddress :传入需要更新的保险地址
	 */
	public void update(PrpCaddress prpCaddress) throws Exception;

	/**
	 * 根据保险地址编号查询出保险地址信息
	 * @param prpCaddressId ：传入的保险地址编号
	 * @return 返回保险地址
	 */
	public PrpCaddress findPrpCaddress(PrpCaddressId prpCaddressId) throws Exception;
	
	/**
	 * 根据查询对象获取Page对象的列表
	 * @param queryRule 查询对象
	 * @param pageNo 页面编号
	 * @param pageSize 页面大小
	 * @return 包含的保险地址页面信息
	 */
	public Page findPrpCaddress(QueryRule queryRule, int pageNo, int pageSize) throws Exception;
	/**
	 * 根据查询对象获取对象的列表
	 * @param queryRule 查询对象
	 * @return 包含的保险地址全貌列表信息
	 */
	public List<PrpCaddress> findPrpCaddress(QueryRule queryRule) throws Exception;
	/**
	 * 查询保险标的的地址
	 * @param kindCode
	 * @param itemCode
	 * @return
	 * @throws Exception
	 */
	public PrpCaddress findPrpCaddress(String policyNo,PrpCitemKind prpCitemKind) throws Exception;
	/**
	 * 根据同险号码查询保单号
	 * @param sameAddressNo 查询对象
	 * @return 保单信息
	 */
	public List<String> findPolicyBySameAddressNo(String sameAddressNo) throws Exception;
}
